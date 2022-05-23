# Section 1: Kafka Introduction

## Why Apache Kafka

- Created by LinkedIn, now Open-Source Project mainly maintained by Confluent, IBM and, Cloudera.
- Distributed, resilient architecture, fault tolerant
- Horizontal scability:
     - Can scale to 100s of brokers
     - Can scale to millions of messages per second
- High performance ( latency of less than 10ms ) - real time
- Used by the 2000+ firms, 80% of the Fortune 100

## Apache Kafka: Use Cases

- Messaging System
- Activity Traking
- Gather metrics from many different locations
- Application Logs gathering 
- Stream processing (with the Kafka Stream API for example)
- De-coupling of system dependencies 
- Integration with Spark, Flink, Storm, Hadoop, and many other Big Data technologies
- Micro-services pub/sub

Note: Remember that Kafka is only used as a transportation mechanism!


## Kafka for Beginners - What we'll learn in this course

- Kafka Cluster
- Kafka Broker
- Producers
    - round robin
    - key based ordering
    - acks strategy
- Consumers
    - topics
    - partitions
    - replications
    - partitions leader & in-sync-replicas (ISR)
    - offset topic
    - consumer offset
    - consumer groups
    - at least once
    - at most once
- Zookeeper
    - leader followers
    - broker management

- Plus:
   - Intro to Conduktor
   - Intro to Kafka Connect
   - Intro to Kafka Streams
   - Intro to Confluent Schema Registry
   - Intro to Kafka Architecture in the enterprise
   - Real world use cases
   - Advanced API + Configurations
   - Topic Configurations

And much more!

## Course Structure

Part 1 Fundamentals - 4h
    - Kafka Theory
    - Starting Kafka
    - Kafka CLI
    - Kafka & Java 101

Part 2 Real World - 3h
    - Wikimedia Producer
    - OpenSearch Consumer
    - Extendend API Intro + Cases Studies + Kafka in the Enterprise

Part 3 Avanced & Annexes
    - Advanced Topic Configuration


- Apache Kafka Series - Welcome!
    1. Kafka For Beginners: get a strong base for Kafka, basics operations, write your first producer and consumer 
    2. Kafka Connect API: Understand how to import / export data to / from Kafka
    3. ksqlDB: Write Kafka Stream applications using SQL
    4. Confluent Components: REST Proxy and Schema Registry
    5. Kafka Security: Setup Kafka security in a Cluster and Integrate your applications with Kafka Security
    6. Kafka Monitoring and Operations: Use Prometheus and Grafana to monitor Kafka, learn operations
    7. Kafka Cluster Setup & Administration: Get a deep understanding of how Kafka & Zookeeper works, how to Setup Kafka and various administration tasks
    8. Confluent Certifications for Developers Practices Exams
    9. Confluent Certifications for Operators Practice Exam







