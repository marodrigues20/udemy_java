# Section 15: Kafka in the Enterprise for Admins

## 103. Kafka Cluster Setup High Level Architecture Overview

### Kafka Cluster Setup - High Level Architecture

    - You want multiple brokers in different data centers (racks) to distribute your load. You also want a cluster of at least 3 Zookeeper 
        (if using Zookeeper)

    - In AWS

        - us-east-1a
            - ZooKeeper1
            - Kafka Broker1
            - Kafka Broker4
        
        - us-east-1b
            - ZooKeeper2
            - Kafka Broker2
            - Kafka Broker5

        - us-east-1c
            - ZooKeeper3
            - Kafka Broker3
            - Kafka Broker6


### Kafka Cluster Setup Gotchas

    - It's not easy to setup a cluster
    - You want to isolate each Zookeeper & Broker on separate servers
    - Monitoring needs to be implemented
    - Operations must be mastered
    - You need an excellent Kafka Admin
    - Alternative: managed "Kafka as a Service" offerings from various companies
        - Amazon MSK, Confluent Cloud, Aiven, CloudKarafka, Instaclustr, Upstash, etc...
        - No operational burdens (updates, monitoring, setup, etc)
    
    - How many brokers?
        - Compute your throughput, data retention, and replication factor
        - Then test for your use case

### Other components to properly setup

    - Kafka Connect Clusters
    - Kafka Schema Registry: make sure to run two for high availability
    - UI tools for ease of administration
    - Admin Tools for automated workflows

    - Automate as much as you can your insfrastructure when you've understood how processes work!


### Kafka Monitoring and Operations

    - Kafka exposes metrics through JMX.
    - These metrics are highly important for monitoring Kafka, and ensuring the system are behaving correctly under load.
    - Common places to host the kafka metrics:
        - ELK (ElasticSeach + Kibana)
        - Datadog
        - NewRelic
        - Confluent Control Centre
        - Promotheus

### Kafka Monitoring

    - Some of the most important metrics are:

    - Under Replicated Partitions: Number of partitions are have problems with the ISR (in-sync replicas). May indicate a high load on the system

    - Request Handlers: Utilization of threads for IO, network, etc... overall utilization of an Apache Kafka broker.

    - Request timing: How long it takes to reply to requests. Lower is better, as latency will be improved.


### Kafka Monitoring - References

    - Reference documentation here:

    - https://kafka.apache.org/documentation/#monitoring

    - https://docs.confluent.io/platform/current/kafka/monitoring.html

    - https://www.datadoghq.com/blog/monitoring-kafka-performance-metrics/


### Kafka Operations

    - Kafka Operations team must be able to perform the following tasks:

        - Rolling Restart of Brokers
        - Updating Configurations
        - Rebalancing Partitions
        - Increasing replication factor
        - Adding a Broker
        - Replacing a Broker
        - Removing a Broker
        - Upgrading a Kafka Cluster with zero downtime


    - It is important to remember that managing your own cluster comes with all these responsibilities and more.


## 105. Kafka Security

### The need for security in Apache Kafka

    - Currently, any client can access your Kafka cluster (authentication)
    - The clients can publish / consume any topic data (authorization)
    - All the data being sent is fully visible on the network (encryption)

    - Someone could intercept data being sent
    - Someone could publish bad data / steal data
    - Someone could delete topics

    - All these reasons push for more security and an authorization model

### In-flight encryption in Kafka

    - Encryption in Kafka ensures that the data exchanged between and brokers is secret to routers on the way.
    - This is similar concept to an https website
    - Performance improvement but negligible if using Java JDK 11


### Authentication (SSL & SASL) in Kafka

    - Authentication in Kafka ensures that only clients that can prove their identity can connect to our Kafka Cluster
    - This is similar concept to a login (username/password)

     - SSL Authentication: clients authentication to Kafka using SSL certificates
     - SASL/PLAINTEXT
        - clients authentication using username / password (weak - easy to setup)
        - Must enable SSL encryption broker-side as well
        - Changes in password require brokers reboot (good for dev only)
     - SASL/SCRAM
        - Username/password with a challenge (salt), more secure
        - Must enable SSL encryption broker-side as well
        - Authentication data is in Zookeeper (until removed) - add without restarting brokers
     - SASL/GSSAPI (Kerberos)
        - Keberos: such as Microsoft Active Directory (strong - hard to setup)
        - Very secure and enterprise friendly
     - SASL/OAUTHBEARER
        - Leverage OAUTH2 tokens for authentication

### Authorisation in Kafka

    - Once a client is authenticated, Kafka can verify its identity
    - it still needs to be combined with authorisation, so that Kafka knows that
    - ACL (Access Control List) must be maintained by administrator to onboard new users

### Kafka Security - Putting it all together

    - You can mix Encryption, Authentication & Authorization
    - This allow your Kafka clients to:
        - Communicate securely to Kafka
        - Clients would authenticate against Kafka
        - Kafka can authorise clients to read / write to topics
    - It's hard to setup security in Kafka and requires significant understanding of security

    - Best support for Kafka Security is with the Java client although other clients based on librdkafka have good support for security now.


## 106. Kafka Multi Cluster & MirrorMaker

### Kafka Multi Cluster & Replication

    - A replication application at its core is just a consumer + a producer

    - There are different tools to perform it:
        - Mirror Maker 2 - open-source Kafka Connector connector that ships with Kafka
        - Netflix uses Flink - they wrote their own application
        - Uber uses uReplicator - address performance and operations issues with MM
        - Comcast has their own open-source Kafka Connect Source
        - Confluent has their own Kafka Connect Source (paid)

    - Overall, try these and see if it works for your use case before writing your own

    - Replication doesn't preserve offsets, just data! Data at an offset in one cluster is not the same
        as the data at same offset in another cluster

    
### Kafka Multi Cluster & Replication - Active / Active

    - Advantages
        - The advantages of this architecture are:
        - Ability to serve users from a nearby data center, which typically has all the functionality, if one data center is unavailable
          you can direct to a remaining data center.

    - Disadvantages
        - The main drawback of this architecture is the challenges in avoiding conflicts when data is read and updated aynchronously
            in multiple locations.


### Kafka Multi Cluster & Replication - Active / Passive

    - Advantages
        - Simplicity in setup and the fact that it can be used in pretty much any use case
        - No need to worry about access to data, handling conflicts, and other architectural complexities
        - Good for cloud migrations as well

    - Disadvantages
        - Waste of a good cluster
        - The fact that it is currently not possible to perform cluster failover in Kafka without either losing data or having duplicate events.


## 107. Advertise Listeners: Kafka Client & Server Communication Protocol

### Understanding communication between Client and with Kafka

    - Advertised listeners is the most important setting of Kafka

    - If the Kafka Client is one the same private network, OKAY
    - If the Kafka Client is not on the same network, can't find IP

Note: Kafka broker ask always to Kafka Client use its advertised host. After one request happen to use its Public IP.

### But what if I put localhost? It works on my machine!

    - If the Kafka Client is one the same machine as the broker, OKAY
    - if the Kafka Client is not on the same machine and you have 2 or more brokers, NOT OKAY
    
Note: Kafka broker ask always to Kafka Client use its advertised host. After one request happen to use its Public IP.

### What if I use the public IP?

    - If the public IP is the same that advertised host. Kafka broker will accept the connection.

### So, what do I set for advertised.listeners?

    - If your clients are on your private network, set either:
        - the internal private IP
        - the internal private DNS hostname
    - Your clients should be able to resolve the internal IP or hostname

    - If your clients are on a public network, set either:
        - The external public IP
        - The external public hostname pointing to the public IP
    - Your clients must be able to resolve the public hostname


## Section 17: Advanced Topics Configurations

### Why should I care about topic configuration?

    - Brokers have defaults for all topic configuration parameters
    - These parameters impact performance and topic behavior

    - Some topics may need different values than the defaults
        - Replication Factor
        - # of Partitions
        - Message size
        - Compression level
        - Log Cleanup Policy
        - Min Insync Replicas
        - Other configurations

    - A list of configuration can be found at
    https://kafka.apache.org/documentation/#brokerconfigs

    - Check kafka config commands
        $ kafka-configs

    
## 110. Partitions and Segments
    - Topics are made of partitions (we already know that)
    - Partitions are made of ... segments (files)
    - Only one segment is ACTIVE (the one dta is being writen to)
    - Two segments settings:
        - log.segment.bytes: the max size of a single segment in bytes (default 1GB)
        - log.segment.ms: the time Kafka will wait before committing the segment if not full (1 week)

    - Segments come with two indexes (files):
        - An offset to position index: helps Kafka find where to read from to find a message
        - A timestamp to offset index: helps Kafka find messages with a specific timestamp

### Segments: Why should I care?

    - A smaller log.segments.bytes (size, default: 1GB) means:
        - More segments per partitions
        - Log Compaction happens more often
        - BUT Kafka must keep more files opened (Error: Too many open files)
    - Ask yourself: how fast will I have new segments based on throughput?

    - A smaller log.segment.ms (time, default 1 week) means:
        - You set a max frequency for log compaction (more frequent triggers)
        - Maybe you want daily compaction instead of weekly?
    - Ask yourself: how often do I need log compaction to happen?


## 111. Log Cleanup Policies

    - Many Kafka clusters make data expire, according to a policy
    - That concept is called log cleanup.

    Policy 1: log.cleanup.policy=delete (Kafka default for all user topics)
        - Delete based on age of data (default is a week)
        - Delete based on max size of log ( default is -1 == infinite)

    Policy 2: log.cleanup.policy=compact (Kafka default for topic__consumer_offsets)
        - Delete based on keys of your messages
        - Will delete old duplicate keys after the active segment is commited
        - Infinite time and space retention

    $ kafka-topics --bootstrap-server localhost:9092 --describe --topic __consumer_offsets


### Log Cleanup: Why and When?

    - Deleting data from Kafka allows you to:
        - Control the size of the data on the disk, delete obsolete data
        - Overall: Limit maintenance work on the Kafka Cluster

    - How often does log cleanup happen?
        - Log cleanup happens on your partition segments!
        - Smaller / More segments means that log cleanup will happen more often!
        - Log cleanup should't happen too often => takes CPU and RAM resources
        - The cleaner checks for work every 15 seconds (log.cleaner.backoff.ms)


## 112. Log Cleanup Delete

### Log Cleanup Policy: Delete

    - log.retention.hours:
        - number of hours to keep data for (default is 168 - one week)
        - Higher number means that less data is retained (if your consumers are down for too long, they can miss data)
        - Other parameters allowed: log.retention.ms, log.retention.minutes (smaller unit has precedence)

    - log.retention.bytes
        - Max size in Bytes for each partition (default is -1 - infinite)
        - Useful to keep the size of a log under a threshold

    
    Use cases: two common pair of options:
        - One week of retention:
            - log.retention.hours=168 and log.retention.bytes=-1
        - Infinite time retention bounded by 500MB:
            - log.retention.ms=-1 and log.retention.bytes=524288000


## 113. Log Compaction Theory

### Log Cleanup Policy: Compact

    - Log compaction ensures that your log contains at least the last known value for a specific key within a partition.
    - Very useful if we just require a SNAPSHOT instead of full history (such as for a data table in a database)
    - The idea is that we only keep the latest "update" for a key in our log


### Log Compaction: Example

    - Our topic is: employee-salary
    - We want to keep the most recent salary for our employees


### Log Compaction Guarantees
    - Any consumer that is reading from the tail of a log (most current data) will still see all the messages sent to the topic

    - Ordering of messages it kept, log compaction only removes some messages, but does not re-order them

    - The offset of a message is immutable (it never changes). Offsets are just skipped if a message is missing

    - Deleted records can still be seen by consumers for a period of delete.retention.ms (default is 24 hours)


### Log Compaction Myth Busting

    - It doesn't prevent you from pushing duplicate data to Kafka
        - De-duplication is done after a segment is commited
        - Your consumers will still read from tail as soon as the data arrives

    - It doesn't prevent you from reading duplicate data from Kafka
        - Same points as above
    
    - Log Compaction can fail from time to time
        - It is an optimization and it the compation thread might crash
        - Make sure you assign enough memory to it and that it gets triggered
        - Restart Kafka if log compaction is broken

    - You can't trigger Log Compaction using an API call (for now...)


### Log Compaction - How it works

    - Log compaction log.cleanup.policy=compact is impacted by:
        - segment.ms (default 7 days): Max amount of time to wait to close active segment
        - segment.bytes (default 1G): Max size of a segment
        - min.compaction.lag.ms (defaul 0): how long to wait before a message can be compacted
        - delete.retention.ms (default 24 hours): wait before deleting data market for compaction
        - min.cleanable.dirty.ratio (default 0.5): higher => less, more efficient cleaning. Lower => opposite


## 115. Unclean Leader Election

### unclean.leader.election.enable

    - If all your in Sync Replicas go offline (but you still have out of sync replicas up), you have the following option:
        - Wait for an ISR to come back online (default)
        - Enable unclean.leader.election.enable=true and start production to non ISR partitions

    - If you enable unclean.leader.election.enable=true, you improve availability, but you will lose data because other messages
        other messages on ISR will be discarded when they come back online and replicate data from the new leader.

    - Overall, this is a very dangerous setting, and its implication must be understood fully before enabling it.

    - Use cases include metrics collection, log collection, and other cases where data loss is somewhat acceptable,
        at the trade-off of availability.


## 116. Large Messages in Kafka

    - Kafka has a default of 1 MB per messages in topics, as large messages are considered inefficient and an anti-pattern.

    - Two approaches to sending large messages:

        1.  Using an external store: store messages in HDFS, Amazon S3, Google Cloud Storage, etc... and send a reference of that message
        to Apache Kafka
    
        2. Modifying Kafka parameters: must change broker, producer and consumer settings


### Option 1: Large Messages using External Store

    - Store the large message (e.g video, archieve file, etc ...) outside of Kafka
    - Send a reference of that message to Kafka
    - Write custom code at the producer / consumer level to handle this pattern


### Option 2: Sending large message in Kafka (ex: 10MB)

    - Topic-wise, Kafka-side, set max message size to 10MB:
        - Broker side: modify message.max.bytes
        - Topics side: modify max.message.bytes
        - Warning: the settings have similiar but different name; this is not a type!

    - Broker-wise, set max replication fetch size to 10MB
        - replica.fetch.max.bytes=10485880 (in server.properties)

    - Consumer-side, must increase fetch size of the consumer will crash:
        - max.partition.fetch.byte=10485880
    
    - Producer-side, must increase the max request size
        - max.request.size=10485880








    
