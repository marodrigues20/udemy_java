# Section 1. Course Introduction

## 2. Course Objectives

    - We're going to build a pipeline to ingest data from Twitter in real time and sink them into ElasticSearch and PostgresSQL.

    - Learn about Kafka Connect:
        - Kafka Connect Architecture
        - Standalone, Distributed Mode
        - Source Connectors Configuration
        - Sink Connectors Configuration
        - Kafka Connect UI
        - Kafka Connect REST API

## 3. Course Structure

    1. Kafka Connect Concepts
    2. Setup and Launch Kafka Connect Cluster using Docker Compose
    3. Kafka Connect Source API - Hands on
        - Get data from a file into a Kafka Topic (FileStreamSourceConnect)
        - Get data from Twitter into a Kafka Topic (TwitterSourceConnector)
    4. Kafka Connect Sink API - Hands on
        - Store the data from Kafka into Elastic Search (ElasticSearchSinkConnector)
        - Store the data from Kafka into PostgreSQL (JDBCSinkConnector)
    
    (This course does not cover Avro as it isn't part of the native Kafka APIs)


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
