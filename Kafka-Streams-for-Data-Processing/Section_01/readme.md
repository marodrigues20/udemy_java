# Section 1: Kafka Stream - First Look

## What is Kafka Stream ?

- Easy data processing and transformation library within Kafka

- Data Transformation
- Data Enrichment
- Fraud Detection
- Monitoring and Alerting

Benefits:
- Standard Java Application
- No need to create a separate cluster
- Highly scalable, elastic and fault tolerant
- Exactly Once Capabilities
- One Record at a time processing (no batching)
- Works for any application size

i.e.: What_Kafka_Stream.png

## Kafka Streams Architecture Design

i.e: Architecture_Kafka_Design.png

Note: Have a look on Kafka_Connect Course. The picture above will get more clear.

## Kafka Stream History

- This API / Library was introduced as part of Kafka 0.10 (XX 2016) and is fully mature as part of Kafka 0.11.0.0 (June 2017)
- It's the only library at this time of writing that can leverage the new exacly once capabilities from Kafka 0.11
- It's a serious contender to other processing framework such as Apache Spark, Flink, or NiFi
- New Library so prone to some changes in the future.


## Course Objectives

- Write and package Kafka Stream Apps (Hands-On & End-to-End)
    - WordCount to get familiar with the concepts
    - FavoriteColour to practice more Kafka Streams transformations API (& Scala version)
    - BankBalance to use aggregation and the power of Exactly Once Semantics
    - StreamEnrich to join and enrich a KStream using GlobalKTable

- Learn the Kafka Stream Fundamentals
    - Difference vs other libraries (Spark Streaming, Flink, NiFi ...)
    - Underlying concepts (topologies, processors ...)
    - Basic and Advanced transformations API - Stateless and Stateful
    - Grouping, Aggregations, Joins
    - Exactly Once Semantics and Capabilities
    - Production Settings and Configurations


### Pre-requisites - This is a challenging course

- Knowledge of Kafka (see my Learning Kafka for Beginners course)
- Strong Knowledge of Java 8 (or Scala)
- Previous experience with data processing libraries is helpful (Spark, Flink, MapReduce, Scalding, Akka Stream, etc...)

### Who is this course for?

- This course is for developers and devops who would like to learn how to write, package, deploy and run a Kafka Stream applications

- This course is for architects who would like to understand how Kafka Streams works and its position in the Kafka -centered data pipeline 
and enterprise archicture

- This course is intended for people who fully understand the basic of Kafka. Please see my course on Learning Apache Kafka for Beginners.

### Apache Kafka Series

1. Kafka For Beginners: get a strong base for Kafka, basic operations, write your first producers and consumers
2. Kafka Connect API: Understand how to import / export data to / from Kafka
3. Kafka Streams API: Learn how to process and transform data within Kafka
4. Kafka Cluster Setup & Administration: Get a deep understanding of how Kafka & Zookeeper works, how to Setup Kafka and various administration tasks
5. Confluent Components: REST Proxy and Schema Registry
6. Kafka Security: Setup Kafka security in a Cluster and Integrate your applications with Kafka Security

## Running our first Kafka Streams application

1. Download Kafka binaries 
2. Start Zookeeper and Kafka
3. Create input and output topics using 'Kafka-topics'
4. Publish data to the input topic
5. Run the WordCount example
6. Stream the output topic using 'kafka-console-consumer'

