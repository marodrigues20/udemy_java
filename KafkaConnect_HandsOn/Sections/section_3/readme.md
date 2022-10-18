## 6. What is Kafka Connect?

### Kafka Connect API - A brief history

    - (2013) Kafka 0.8.x:
        - Topic replication, Log compaction
        - Simplified producer client API
    - (Nov 2015) Kafka 0.9.x:
        - Simplified high level consumer APIs, without Zookeeper dependency
        - Added security (Encryption and Authentication)
        - Kafka Connect APIs
    - (May 2016): Kafka 0.10.0:
        - Kafka Streams APIs
    - (end 2016 - March 2017) Kafka 0.10.1, 0.10.2:
        - Improved Connect API, Single Message Transforms API

### Why Kafka Connect and Streams

    - Four Common Kafka Use Cases:

        Source => Kafka         Producer API                Kafka Connect Source
        Kafka  => Kafka         Consumer, Producer API      Kafka Stream
        Kafka  => Sink          Consumer API                Kafka Connect Sink
        Kafka  => App

    - Simplify and improve getting data in and out of Kafka


### Why Kafka Connect

    - Programmers always want to import data from the same sources:
        - Databases, JDBC, Couchbase, GoldenGate, SAP HANA, Blockchain, Cassandra, DynamoDB, FTP, IOT, MongoDB,
          MQTT, RethinkDB, Salesforce, Solr, SQS, Twitter, etc...
    
    - Programmers always want to store data in the same sinks:
        - S3, ElasticSearch, HDFS, JDBC, SAP HANA, DocumentDB, Cassandra, DynamoDB, HBase, MongoDB, Redis, Solr, Splunk, Twitter

    
    - It is tough to achieve Fault Tolerance, Exactly Once, Distribution, Ordering
    - Other programmers may already have done a very good job!

## 7. Kafka Connect Architecture Design

i.e: Architecture_Design.png


## 8. Connectors, Configuration, Tasks, Workers

### Kafka Connect - High Level

    - Source Connectors to get data from Common Data Sources
    - Sink Connectors to publish that data in Common Data Stores
    - Make it easy for non-experienced dev to quickly get their data reliably into Kafka
    - Part of your ETL pipeline
    - Scaling made easy from small pipelines to company-wide pipelines
    - Re-usable code! 


### Kafka Connect - Concepts

    - Kafka Connect Cluster has multiple loaded Connectors.
        - Each connector is a re-usable piece of code (java jars)
        - Many connectors exist in the open source world, leverage them!

    - Connectors + User Configuration => Tasks
        - A task is linked to a connector configuration
        - A job configuration may spawn multilple tasks

    - Tasks are executed by Kafka Connect Workers (servers)
        - A worker is a single java process 
        - A worker can be standalone or in a cluster



### Kafka Connect Workers - Standalone vs Distributed Mode

    - Standalone
        - A single process runs your connectors and tasks
        - Configuration is bundled with your process
        - Very easy to get started with, useful for development and testing
        - Not fault tolerant, no scalability, hard to monitor
    - Distributed:
        - Multiple workers run your connectors and tasks
        - Configuration is submitted using a REST API
        - Easy to scale, and fault tolerant (rebalancing in case a worker dies)
        - Useful for production deployment of connectors

### Kafka Connect Cluster - Distributed Architecture in details

i.e: KafkaConnectCluster- Distributed.png






