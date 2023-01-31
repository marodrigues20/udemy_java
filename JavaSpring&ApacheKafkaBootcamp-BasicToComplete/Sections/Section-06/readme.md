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





