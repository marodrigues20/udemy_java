# Section 11: Kafka Wikimedia Producer & Advanced Producer Configuration

## Introduction:
    We are going to get data from Wikipedia as a stream through a Kafka Producer, into Apache Kafka topics. And then,
    we're going to create a Kafka Consumer that will take this data and send it to OpenSearch.

Links:
    - https://stream.wikimedia.org/v2/stream/recentchange
    - https://codepen.io/Krinkle/pen/BwEKgW?editors=1010
    - https://esjewett.github.io/wm-eventsource-demo/

Note: OpenSearch is an open source fork of ElasticSearch.


## 60. Producer Acknowledgement Deep Dive

    - Producers can choose to receive acknowledgment of data writes:
        - acks=0: Producer won't wait for acknowledgment (possible data loss)
        - acks=1: Producer will wait for leader acknowledgment (limit data loss)
        - acks=all: Leader + replicas acknowledgment (no data loss)

### Producer: acks=0

    - When acks=0 producers consider messages as "written successfuly" the moment the message was sent without 
        waiting for the broker to accept it at all.
    - If the broker goes offline or an exception happens, we won't know and will lose data.
    - Useful for data where it's okay to potentially lose messages, such as metrics collection.
    - Producers the highest throughput setting because the network overhead is minimized.

### Procuder: acks=1
    
    - When acks=1, producers consider messages as "written successfully" when the message was acknowledgment by only 
        the leader.
    - Default for Kafka v1.0 to v2.8
    - Leader response is requested, but replication is not a guarantee as it happens in the background
    - If the leader broke goes offline unexpectedly but replicas haven't replicated the data yet, we have a data loss.
    - If an ack is not received, the producer may retry the request.

### Producer acks=all (acks=-1)

    - When acks=all, producers  consider messages as "written successfully" when the message is accepted by all 
        in-sync-replicas (ISR).
    - Default for Kafka 3.0+

### Producer acks=all & min.insync.replicas

    - The leader replica for a partition checks to see if there are enough in-sync replicas for safely written
        the message (controlled by the broker setting min.sync.replicas)
        - min.insync.replicas=1: only the broker leader needs to successfuly ack
        - min.insync.replicas=2: at least the broker leader and replica need to ack


### Kafka Topic Availability

    - Availability: (considering RF=3)
    - acks=0 & acks=1: if one partition is up and considered as ISR, the topic will be available for writes.
    - acks=all: 
        - min.insync.replicas=1 (default): the topic must have at least 1 partition up as an ISR (that includes
            the leader ) and so we can tolerate two brokers being down
        - min.insync.replicas=2: the topic must have at least 2 ISR up, and therefore we can tolerate at most one broke
            being down (in the case of replicas factor of 3), and we have the guarantee that for every write, the data
            will be at least writen once.
        - min.insync.replicas=3: this wouldn't make much sense for a corresponding replication factor of 3 and we 
            couldn't tolerate any broker going down.
        - in summary, when acks=all with a replication.factor=N and min.insync.replicas=N we can tolerate N-M brokers
            going down for topic availability purposes.

    - acks=all and min.insync.replicas=2 is the most popular option for data durability and availability and allows you
        to withstand at most the loss of one Kafka broker


## 61. Producer Retries

    - In case of trasient failures, developers are expected to handle expceptions, otherwise the data will be lost.
    - Example of transient failure:
        - NOT_ENOUGH_REPLICAS (due to min.insync.replicas setting)

    - There is a "retries" setting
        - defaults to 0 for Kafka <= 0
        - defaults to 2147483647 for Kafka >= 2.1

    - The retry.backoff.ms setting is by default 100ms

### Producer Timeouts

    - if retries > 0, for example retries=2147483647, retries are bounded by a timeout
    - Since Kafka 2.1, you can set: delivery.timeout.ms=120000==2 min
    - Records will be failed if then can't be acknowldegment within delivery.timeout.ms

### Producer Retries: Warning for old version of Kafka

    - If you are not using an idempotent producer (not recommended - old Kafka):
        - In case of retries, there is a chance that messages will be sent out of order (if a batch has failed to be sent)
        - If you rely on key-based ordering, that can be an issue.

    - For this, you can set the setting while controls how many producers requests can be made in parallel:
        max.in.flight.requests.per.connection
            - Default: 5
            - Set it to 1 if you need to ensure ordering (may impact throughtput)

    - In Kafka >= 1.0.0, there's a better solution with idempotent producers!


## 61. Idempotent Producer

    - The Producer can introduce duplicate messages in Kafka due to networks errors

### Idempotent Producer 

    - In Kafka >= 0.11, you can define a "idempotent producer" which won't introduce duplicates on network error.

## 62. Idempotent Producer

    - Idempotent producers are great to guarantee a stable and safe pipeline.
    - They are the default since Kafka 3.0, recommended to use them.
    
    - The come with:
        - retries=Integer.MAX_VALUE(2^31-1=2147483647)
        - max.in.flight.requests=1 (kafka == 0.11) or
        - max.in.flight.requests=5 (Kafka > 1.0 - higher performance & keep ordering - KAFKA-5494)
    
    -These settings are applied automatically after your producer has started if not manually set
    - Just set:
                producerProps.put("enable.idempotent", true);


## 63. Safe Kafka Producer Settings

    - Since Kafka 3.0, the producer is "safe" by default:
        - acks=all (-1)
        - enable.idepotence=true

    - With Kafka 2.8 and lower, the producer by default come with:
        - acks=1
        - enable.idempotence=false

    - I would recommend using a safe producer whenever possible!

    - Super important: always use upgraded Kafka Clients

### Safe Kafka Producer - Summary & Demo

    - Since Kafka 3.0, the producer is "safe" by default, otherwise, upgrade your clients or set the following settings
    
    - acks=all
        - Ensures data is properly replicated before an ack is received.
    - min.insync.replicas=2 (borker/topic level)
        - Ensures two brokers in ISR at least have data after an ack
    - enable.idempotence=true
        - Duplicates are not introduced due to network retries
    - retries=MAX_INT (producer level)
        - Retry until delivery.timeout.ms is reached
    - delivery.timeout.ms=120000
        - Fail after retrying for 2 minutes
    - max.in.flight.requests.per.connections=5
        - Ensure maximum performance while keeping message ordering

## 65. Kafka Message Compression at the Producer Level

    - Producer usually send that is text-based, for example with JSON data.
    - In this case, it is important to apply compression to the producer.
    - Compression can be enabled at the Producer level and doesn't require any configuration change in the Broker or
      in the Consumers.
    - compression.type can be none (default), gzip, lz4, snappy, zstd (Kafka 2.1)
    - Compression is more effective the bigger the batch of message being sent to Kafka!
    - Benchmarks here: https://blog.cloudflare.com/squeezing-the-firehose/

### Message Compression
    
    - The compressed batch has the following advantages:
        - Much smaller producer request size (compression ratio up to 4x!)
        - Faster to transfer data over the network => less latency
        - Better throughput
        - Better disk utilization in Kafka (store message on disk are smaller)
    - Disadvantages (very minor)
        - Producers must commit some CPU cycles to compression
        - Consumers must commit some CPU cycles to decompression
    - Overall
        - Consider testing snappy or lz4 for optional speed / compression ratio (test others too)
        - Consider tweaking linger.ms and batch.size to have bigger batches, and therefore more compression and 
            higher throughput

### Message Compression at the Broker / Topic level

    - There is also a setting you can set at the broker level (all topics) or topic-level
    
    - compression.type=producer (default), the broker takes the compressed batch from the producer client and writes it
        directly to the topic's log file without recompressing a data.
    - compression.type=none: all batches are decompressed by the broker
    - compression.type=lz4: (for example)
        - If it's matching the producer setting, data is stored on disk as is
        - If it's different compression setting, batches are decompressed by the broker and then recompresed using the
            compressed algorithm specified.
    
    - Warning: If you enable broker-side compression, it will consume extra CPU cycles 

### linger.ms & batch.size

    - By default, Kafka producers try to send records as soon as possible
        - It will have up to max.in.flight.requests.per.connection=5, meaning up to 5 message batches being in flight
            (being sent between the producer in the broker) at most.
        - After this, if more messages must be sent while others are in flight, Kafka is smart and will start batching 
            them before the next batch send 
    - This smart batching helps increase throughtout while mainting very low latency.
        - Added benefit: batches have higher compression ratio so better efficiency
    
    - Two settings to influence the batching mechanism
        - linger.ms: (default 0) how long to wait until we send a batch. Adding a small number for example 5 ms helps
            add more messages in the batch at the expense of latency.
        - batch.size: if a batch is filled before linger.ms, increase the batch size

### batch.size (default 16KB)

    - Maximum number of bytes that will be included in a batch

    - Increasing a batch size to something like 32KB or 64KB can help increasing the compression, throughput, and 
        efficiency of requests.
    - Any message that is bigger then the batch size will not be batched.
    - A batch is allocated per partition, so make sure that you don't set it to a number that's too high, otherwise 
        you'll run waste memory.
    - ( Note: You can monitor the average batch size metric using Kafka Producer Metrics)

### High Throughput Producer

    - Increase linger.ms and the producer will wait a few milliseconds for the batches to fill up before sending them.
    - If you are sending full batches and have memory to spare, you can increase batch.size and send large batches.
    - Introduce some producer-level compression for more efficiency in sends

    //high throughput producer (at the expense of a bit of latency and CPU usage)
    properties.setProperty(ProducerConfig.COMPRESSION_TYPE_CONFIG, "snappy");
    properties.setProperty(ProducerConfig.LINGER_MS_CONFIG, "20");
    properties.setProperty(ProducerConfig.BATCH_SIZE_CONFIG, Integer.toString(32 * 1024));

## 67. High Throughput Producer - Demo

    - We'll add snappy message compression in our producer
    - snappy is very helpful if your messages are text based, for example log lines or JSON documents 
    - snappy has a good balance of CPU / compression ratio
    - We'll also increase the batch.size to 32KB and introduce a small delay through  linger.ms (20 ms)
    - We'll check which partioner is being used

        /high throughput producer (at the expense of a bit of latency and CPU usage)
        properties.setProperty(ProducerConfig.LINGER_MS_CONFIG, "20");
        properties.setProperty(ProducerConfig.BATCH_SIZE_CONFIG, Integer.toString(32 * 1024));
        properties.setProperty(ProducerConfig.COMPRESSION_TYPE_CONFIG, "snappy");

## 68. Producer Default Partitioner when key != null

    - Key Hashing is the process of determining the mapping of a key to a partition 
    - In the default Kafka partitioner, the keys are hashed using the murmur2 algorithm

        targetPartition = Math.abs(Utils.murmur2(keyBytes)) % (numPartitions - 1)

    - This means that same key will go to the same partition (we alreday know this), and adding partitions to a topic
        will completely alter the formula

    It is most likely preferred to not override the behavior of the partitioner, but it is possible to do so using 
        partitioner.class


### Producer Default Partitioner when key=null

    - When key=null, the producer has a default partitioner that varies:
        - Round Robin: for Kafka 2.3 and below
        - Stick Partitioner: for Kafka 2.4 and above

    - Stick Partitioner improves the performance of the producer specially when high throughput when the key is null


### Producer Default Partitioner Kafka <= v2.3 Round Robin Partitioner
    
    - With Kafka <= 2.3, when there's no partition and no key specified, the default partitioner sends data in an 
        around-robin fashion.
    - This result in more batches (one batch per partition) and smaller batches (image with 100 partitions)
    - Smaller batches lead to more requests as well as higher latency.

### Producer Default Partitioner Kafka >= v2.4 Sticky Partitioner

    - It would be better to have all the records sent to a single partition and not multiple partitions to improve 
        batching.
    - The producer sticky partitioner:
        - We "stick" to a partition until the batch is full on linger.ms has elapsed
        - After sending the batch, the partition that is sticky changes
    
    - Larger batches and reduced latency (because larger requests, and batch.size more likely to be reached)

    - Over time, records are still spread evenly across partitions


## 69. [Advanced] max.block.ms and buffer.memory

    - If that buffer is full ( all 32MB), then the .send() method will start to block (won't return right away)

    - max.block.ms=60000: the time the .send() will block until throwing an exception.
        Exceptions are thrown when:
            - The producer has filled up its buffer
            - The broker is not accepting any new data
            - 60 seconds has elapsed

    - If you hit an exception hit that usually means your broker are down or overloaded as they can't respond to requests.

https://cwiki.apache.org/confluence/display/KAFKA/KIP-480%3A+Sticky+Partitioner











































    
    
        











































