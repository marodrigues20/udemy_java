# Section 6: Start Writting Codes

## 23. Spring Boot

### What is Spring Boot?

- Accelerate coding process
- Handles most of configurations
- Predefined configurations on application.properties / application.yml
- Automatic dependency management
- Online project generator

### Spring Initializr

- Spring Boot project generator
- Go to: https://start.spring.io/
- Generate two projects: producer and consumer

### Create Consumer and Producer projects

Java Project Reference: kafka-core-consumer
Java Project Reference: kafka-core-producer

### Spring Boot Configuration

- Under src/main/resources
- Filename: application.properties or application.yml
- Prefer yml format for readability (optional)

![alt text](https://github.com/marodrigues20/udemy_java/blob/main/JavaSpring%26ApacheKafkaBootcamp-BasicToComplete/Sections/Section-06/properties_1.png?raw=true)

## 24. Hello Kafka - Topic & Partition

### Executing Kafka Commands

- Kafka server is console-based, no UI
- Execute kafka commands is different for native install/docker
- I use Docker installation on top of Windows
- Installation
  - Docker: follow this lecture
  - Linux: [kafka-home]/bin/[command].sh
  - Windows:[kafka-home]/bin/windows/[command].bat

### Create topics command

- kafka-topics.sh --bootstrap-server localhost:9092 --create --topic t-hello --partition 1 --replication-factor 1

### Check available topics

- kafka-topics.sh --bootstrap-server localhost:9092 --list

### Learn more about one specific topic

- kafka-topics.sh --bootstrap-server localhost:9092 --describe --topic t-hello


## 25. Hello Kafka - Java Spring Code

- In this lecture, we will create a HelloKafkaProducer.

Java Reference Project: kafka-core-producer

## 26. Consumer is Real Time Indeed

- In this lecture, we will prove that consumer keep monitoring topic and process incoming message in real time.

### Create a Topic
- $ kafka-topics.sh --bootstrap-server localhost:9092 --create --topic t-fixedrate --partition 1 --replication-factor 1

### List Topic
- kafka-topics.sh --bootstrap-server localhost:9092 --list

## 27. "Fixing" Consumer

### Consumer Offset on First Run

- When we start the consumer for the first time, there is no committed offset position.
- In that case, we need to tell the consumer where to start consuming message by setting the "auto.offset.reset" configuration.
- There are two values that commonly used : earliest, and latest.
- Latest is the default value, which means if there is no commited position, consumer will start receiving and processing messages that are sent to kafka after consumer started.
- So in this illustration, it will consumes only the blue messages, which are messages arrived after consumer started.

![alt text](https://github.com/marodrigues20/udemy_java/blob/main/JavaSpring%26ApacheKafkaBootcamp-BasicToComplete/Sections/Section-06/ConsumeOfSetOnFirst.png?raw=true)

- If you need to process all messages that arrived even before consumer started, set the auto.offset.reset parameter to "earliest".
- So the consumer will consume all messages, including the brown one, which arrived before consumer started.
- This is optional, you can choose depends on your need.

![alt text](https://github.com/marodrigues20/udemy_java/blob/main/JavaSpring%26ApacheKafkaBootcamp-BasicToComplete/Sections/Section-06/ConsumeOfSetOnFirst_2.png?raw=true)

- For example, if the message triggers important transaction, like financial API call, better to set the value to "earliest".
- Other than those two values, you can also set offset manually.
- For now, let's set the value to earliest.

### Creating one more topic "t-fixedrate-2"

- $ kafka-topics.sh --bootstrap-server localhost:9092 --create --topic t-fixedrate-2 --partition 1 --replication-factor 1

### List all topics to check if it has been created.
- kafka-topics.sh --bootstrap-server localhost:9092 --list

### Java Project Reference

- kafka-core-consumer
  - Add "FixedRate2Consumer.java"
- kafka-core-producer
  - Add "FixedRate2Producer.java"

## 28. Producing Message With Key


