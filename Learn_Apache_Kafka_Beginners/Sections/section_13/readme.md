# Section 13. Kafka Extended APIs for Developers

## 86. Kafka Extended APIs - Overview 
### What will we see in this section?
    - Kafka Consumers and Producers have existed for a long time, and they're considered low-level
    - Kafka & ecosystem has introduced over time some new API that are higher level that solves specific sets of 
        problems.
        - Kafka Connect solves External Source -> Kafka and Kafka -> External Sink
        - Kafka Streams solves transformations Kafka -> Kafka
        - Schema Registry helps using Schema in Kafka

    - This is an introduction meant to give you an idea of the depth of the Kafka Ecosystem

### Kafka Connect Introduction

    - Do you feel you're not the first person in the world to write a way to get data out of Twitter?
    - Do you feel like you're not the first person in the world to send data from Kafka to PostgreSQL / ElasticSearch /
        MongoDB
    - Additionally, the bugs you'll have, won't someone have fixed them already?

    - Kafka Connect is all about code & connectors re-use!

### Why Kafka Connect 

    - Programmers always want to import data from same sources:
        - Databases, JDBC, Couchbase, GoldenGate, SAP HANA, Blockchain, Cassandra, DynamoDB, FTP, IOT, MongoDB, MQTT,
            RethinkDB, Salesforce, Solr, SQS, Twitter ...

    - Programmers always want to store data in the same sinks:
        - S3, ElasticSearch, HDFS, JDBC, SAP HANA, DocumentDB, Cassandra, DynamoDB, HBase, MongoDB, Redis, Solr, Splunk,
            Twitter ...

### Kafka Connect - High level

    - Source Connectors to get data from Common Data Sources
    - Sink Connectors to publish that data in Common Data Stores
    - Make it easy for non-experienced dev to quickly get their data reliably into Kafka
    - Part of your ETL pipeline
    - Scaling made easy from small pipelines to company-wide pipelines
    - Other programmers may already have done a very good job: re-usable code!
    - Connectors achieve fault tolerance, idempotence, distribution, ordering

## 90. Kafka Streams Introduction

    - You want to do the following from the wikimedia.recentchange topic:
        - Count the number of times a change was created by a bot versus a human
        - Analyze number of changes per website (ru.wikimedia.org vs en.wikipedia.org)
        - Numbers of edits per 10-seconds as a time series

    - With a Kafka Producer and Consumer, you can achieve that but it's very slow level and not developer friendly

### What is Kafka Streams?

    - Easy data processing and transformation library within Kafka
        - Data Transformation
        - Data Enrichment
        - Fraud Delection
        - Monitoring and Alerting

    - Standard Java Application
    - No need to create a separate cluster
    - Highly scalable, elastic and fault tolerance
    - Exactly-Once Capabilities
    - One record at a time processing (no batching)
    - works for any application size

## 92. Kafka Schema Registry Introduction

### The need for a schema registry

    - Kafka takes bytes as an input and publishes them to consumers.
    - No data verification
    
    
    - What if the producer sends bad data? (different formats)
    - What if a field gets renamed?
    - What if the data format changes from one day to another?
    The consumers break!!!

### The need for a schema registry

    - We need data to be self describable 
    - We need to be able to evolve data without breaking downstream consumers
    - We need schemas... and a schema registry!

    - What if the Kafka Brokers were verifying the messages they receive?
    - It would break what makes Kafka so good:
        - Kafka doesn't parse or even read your data (no CPU usage)
        - Kafka takes bytes as an input without even loading them into memory (that's called zero copy)
        - Kafka distributes bytes
        - As far as Kafka is concern, it doesn't even know if your data is an integer, a string etc

### The need for a schema registry

    - The Schema Registry must be a separate component
    - Producers and Consumers need be able to talk to it
    - The Schema Registry must be able to reject bad data
    - A common data format must be agreed upon:
        - It needs to support schemas
        - It needs to support evolution
        - It needs to be lightweight
    
    - Enter... the Schema Registry
    - And Apache Avro as the data format (Protobuf, JSON Schema also supported)

### Schema Registry: gotchas

    - Utilizing a schema registry has a lot of benefits
    - BUT it implies you need to 
        - Set it up well
        - Make sure it's highly available
        - Partially change the producer and consumer code
    - Apache Avro as a format is awesome but has a learning curve
    - Other formats include Protobuf and JSON format
    - The Confluent Shema Registry is free and source-available
    - Other open-source alternatives may exist

    - As it takes time to setup, we won't cover the usage in this course


### Schema Registry - DEMO

    - Start Kafka with Schema Registry using Conduktor
    - Create a Schema 
    - Send data using a Producer
    - Consume data using a Consumer

### Which Kafka?

    - Kafka Conect Source to get data from a DataBase
    - Kafka Connect Sink to send data to a target database.
    - Kafka Stream to get data from a Kafka Topic, Transform the data and send the data to another topic
    - Kafka KSQL to query on the top of Kafka
    - Kafka Registry to define schemas and use it on the top of Kafka
