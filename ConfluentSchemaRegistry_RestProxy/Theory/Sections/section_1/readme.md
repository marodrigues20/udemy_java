# Section 1: Course Introduction

## The need fro a schema registry

    - Kafka takes bytes as an input and publishes them
    - No data verification

    - What if the producer sends bad data?
    - What if a field get renamed?
    - What if the data format changes from one day to another?

    - The Consumers Break!!!

## The need for a schema registry

    - We need data to be self describable
    - We need to be able to evolve data without breaking downstream consumers.

    - we need schemas... and a schema registry!


    - What if the Kafka Brokers were verifying the messages they receive?

    - It would break what makes Kafka so good:
        - Kafka doesn't parse or even read your data (no CPU usage)
        - Kafka takes bytes as an input without even loading them into memory (that's called zero copy)
        - Kafka distributes bytes
        - As far as kafka is concerned, it doesn't even know if your data is an integer, a string etc.


## The need for a schema registry

    - The Schema Registry has to be a separate components
    - Producers and Consumers need to be able to talk to it
    - the Schema Registry must be able to reject bad data
    - A common data format must be agreed upon
        - It needs to support schemas
        - It needs to support evolution
        - It needs to be lightweight
    - Enter ... the Confluent Schema Registry
    - And Apache Avro as the data format.


## 2. Course Structure & Objectives

### Course Objectives

    1. Learn about Avro
        - Understand and write schemas, primitive and complex types
        - Create Avro objects in Java and serialize / deserialize them
        - Learn about schema evolution and developer workflow
    2. Learn about Schema Registry
        - Architecture and purpose of the Schema Registry
        - Usage for Kafka Producer, Consumer, Kafka Streams, Kafka Connect
        - Understand backward, forward and full compatibility
    3. Learn about the REST Proxy
        - Architecture, learn about all the APIs
        - Produce / Consumer data in binary, json or Avro format

    
## 4. Architecture for Kafka with the Schema Registry and REST Proxy

### Kafka Ecosystem Kafka Core

i.e: Kafka Ecosystem - Kafka Core.png


### Kafka Ecosystem: Confluent Schema Registry

i.e: Confluent Schema Registry.png

### Kafka Ecosystem: Confluent REST Proxy

i.e: Confluent REST Proxy.png

