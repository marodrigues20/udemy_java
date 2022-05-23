# Section 12: OpenSearch Consumer & Advanced Consumer

## Introduction:
    We are going to get data from Wikipedia as a stream through a Kafka Producer, into Apache Kafka topics. And then,
    we're going to create a Kafka Consumer that will take this data and send it to OpenSearch.


## 70. OpenSearch Consumer - Project Overview

    - Setup an OpenSearch (open-source fork of ElasticSearch) cluster:
        - Free tier / managed OpenSearch available at bonsai.io
        - Run locally using Docker for example

    - Use Java libraries
        - OpenSearch REST High Level Client

## 74. OpenSearch 101

    - Use the REST API (using OpenSearch Dashboard) or online console at bonsai.io
    - Tutorial at: https://opensearch.org/docs/latest/#docker-quickstart (version 1.X)
    

## 77. Consumer Delivery Semantics

    - At most once: offsets are commited as soon as the message batch is received. If the processing goes wrong, the 
        message will be lost (it won't be read again).
    - At least once: offsets are committed after the message is processed. If the processing goes wrong, the message
    will be read again. This can result in duplicate processing of messages. Make sure your processing is idempotent
    (i.e: processing again the messages won't impact your systems)
    - Exactly once: Can be achieved for Kafka => Kafka workflows using the Transactional API (easy with Kafka Stream API).
    For Kafka => Sink workflows, use an indepotent consumer.

    Button line: for most applications you should use at least once processing (we'll see in practice how to do it) and
    ensure your transformations / processing are idempotent

## 79. Consumer Offsets Commit Strategies

    - There are two most common patterns for commiting offsets in a consumer application.

    - 2 strategies:
        - (easy) enable.auto.commit=true & synchronous processing of batches
        - (medium) enable.auto.commit=false & manual commit of offsets

### Kafka Consumer - Auto Offset Commit Behavior

    - In the Java Consumer API, offsets are regularly committed.
    - Enable at-least once reading scenarios by default (undor conditions)
    - Offsets are commited when you call .pool() and auto.commit.intervals.ms has elapsed.
    - Example: auto.commit.interval.ms=5000 and enable.auto.commit=true will commit

    - Make sure messages are all successfuly processed before you call .poll() again
        - If you don't, you will not be in at-least-once reading scenario
        - In that (rare) case, you must disable enable.auto.commit, and most likely most processing to a separate thread,
            and then from time-to-time call .commitSync() or .commitAsync() with the correct offsets manually(advanced)

### Consumer Offsets Commit Strategies

    - enable.auto.commit=true & synchronous processing of batches

    while(true){
        List<Records> batch = consumer.pool(Duration.ofMillis(100))
        doSomethingSynchrnous(batch)
    }

    - With auto-commit, offsets will be commited automatically for you at regular interval 
        ( auto.commit.interval.ms=5000 by default) every-time you can call .poll()
    - If you don't use synchronous processing, you will be in "at-most-once" behaviour because offsets will be 
        commited before your data is processed

 ### Consumer Offset Commits Strategies

    - enable.auto.commit=false & synchronous processing of batches.

            while(true){
                batch += consumer.poll(Duration.ofMilles(100))
                if isReady(batch){
                    doSomethingSynchrnous(batch)
                    consumer.commitAsync();
                }
            }

    - You control when you commit offsets and what's the condition for commiting them.
    - Example: accumulating records into a buffer and then flushing the buffer to a database + commiting offsets
        asynchronously then.

### Consumer Offset Commits Strategies

    - enable.auto.commit=false & storing offsets externally

    - This is advanced:
    - You need to assign partitions to your consumers at launch manually using .seek() API
    - You need to model and store your offsets in a database table for example
    - You need to handle the cases where rebalances happen
        (ConsumerRebalanceListener interface)


    Example: If you need exactly once processing and can't find way to do indepotent processing, then you 
    "process data" + "commit offsets" as part of a single transaction.
    
    Note: I don't recommended using this strategy unless you exacly know what and why you're doing it


## 81. Consumer Offset Reset Behavior

    - A consumer is expected to read from a log continuously.
    - But if your application has a bug, your consume can be down
    - If Kafka has a retention of 7 days, and your consumer is down for more than 7 days, the offsets are "invalid"

## 82. Consumer Offset Reset Behavior

    - The behavior for the consumer is to them use
        - auto.offset.reset=latest: will read from the end of the log
        - auto.offset.reset=earliest: will read from the start of the log
        - auto.offset.reset=none: will throw exception if no offset is found

    - Additionally, consumer offsets can be lost 
        - If a consumer hasn't read new data in 1 day (Kafka < 2.0)
        - If a consumer hasn't read new data in 7 days (Kafka >= 2.0)
    
    - This can be controlled by the broker setting offset.retention.minutes

### Replaying data for Consumers
    
    - To replay data for a consumer group:
        - Take all the consumers from a specific group down
        - Use Kafka-consumer-groups command to set offset to what you want
        - Restart consumers

    - Bottom line:
        - Set proper data retention period & offset retention period
        - Ensure the auto offset reset behavior is the one you expect / want
        - Use replay capability in case of unexpected behaviour

## 83. OpenSearch Consumer  Implementation Part 6 - Replaying Data

    i.e: Command bellow
    $ kafka-consumer-groups --bootstrap-server localhost:9092 --group consumer-opensearch-demo --reset -offset --to-earliest -- execute --all-topics
        
## 84. Consumer Internal Threads

### Controlling Consumer Liveliness
    
    - Consumers in a Group talk to a Consumer Groups Coordinator
    - To detect consumers that are "down", there is a "heartbeat" mechanism and "pool" mechanism
    - To avoid issues, consumers are encouraged to process data fast and pool often

### Consumer hearbeat thread

    - heartbeat.interval.ms (default 3 seconds)
        - How often to send hearbeats
        - Usually set to 1/3rd of session.timeout.ms

    - session.timeout.ms (default 45 seconds Kafka 3.0+, before 10 seconds):
        - Heartbeats are sent periodically to the broker
        - If no hearbeat is sent during period, the consumer is considered dead
        - Set even lower to faster consumer rebalances

    - Take-away: This mechanism is used to detect a consumer application being down

### Consumer Poll Thread

    - max.poll.interval.ms (default 5 minutes)
        - Maximum amount of time between two  .pool() calls before declaring the consumer dead
        - This is relevant for Big Data framework like Spark in case the processing takes time

    - Take-away: This mechanism is used to detect a data processing issue with the consumer (consumer is "stuck")

    - max.poll. records (default 500)
        - Controls how many records to receive per poll request
        - Increase if your message are very small and have a lot of available RAM
        - Good to monitor how many records are polled per request
        - Lower if it takes you too much time to process records


### Consumer Poll Behavior

    - fetch.min.bytes (default 1)
        - Controls how much data you want to pull at least on each request
        - Helps improving throughput and decreasing request number
        - At the cost of latency

    - fetch.max.wait.ms (default 500)
        - The maximum amount of time the Kafka broker will block before answering the fetch request if there isn't 
            sufficient data to immediately satisfy the requirement given by fetch.min.bytes
        - This means that until the requirement of fetch.min.bytes to be satisfied, you will have up to 500ms of 
            latency before the fetch returns data to the consumer  (e.g introducing a potential delay to be more 
            efficient in requests)


### Consumer Poll Behavior

    - max.partition.fetch.bytes (default 1MB)
        - The maximum amount of data per partition the serve will return
        - If you read from 100 partitions, you'll need a lot of memory (RAM)

    - fetch.max.bytes (default 55MB)
        - Maximum data returned for each fetch request 
        - If you have available memory, try increasing fetch.max.bytes to allow the consumer to read more data in each
            request

    - Advanced: Change these settings only if your consumer maxes out on throughput already


## 85. Consumer Replica Fetching - Rack

### Default Consumer behavior with partition leaders

    - Kafka Consumers by default will read from the leader broker for a partition
    - Possibility higher latency (multiple data centre), + high network changes ($$$)
    - Examples: Data Centre === Availability Zone (AZ) in AWS, you pay for Cross AZ network changes

### Kafka Consumers Replica Fetching (Kafka v2.4+) 

    - Since Kafka 2.4, it is possible to configure consumers to read from the closest replica
    - This may help improve latency, and also decrease networks costs if using the cloud

### Consumer Rack Awareness (v2.4+) - How to Setup

    - Broker setting:
        - Must be version Kafka v2.4+
        - rack.id config must be set to the data centre ID (ex: AZ ID in AWS)
        - Example for AWS: AZ ID rack.id=asw2-az1

        - replica.selector.class must be set to
            org.apache.kafka.common.replica.RackAwareReplicaSelector

    - Consumer client setting:
        - Set client.rack to the data centre ID the consumer is launched on





    


