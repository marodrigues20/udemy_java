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


- Same Key goes to same partition
- As long as we don't change the partition number

### First, create a topic with three partitions in kafka.

- $ kafka-topics.sh --bootstrap-server localhost:9092 --create --partition 3 --replication-factor 1 --topic t-multi-partitions

### We can see that this topic has three partitions, so we can run up to three consumers for this topic.

- $ kafka-topics.sh --bootstrap-server localhost:9092 --describe --topic t-multi-partitions


### Project Reference

- Kafka-core-producer
  - Class: KafkaKeyProducer.java
  - Class: KafkaCoreProducerApplication.java


### How To Run Produce and Check Messages

1. Run the Application
2. Open Bash Shell prompt
3. Type the cli command to consume messages from partition 0.
   1. $ kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic t-multi-partitions --offset earliest --partition 0
4. Type the cli command to consume messages from partition 1.
   1. $ kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic t-multi-partitions --offset earliest --partition 1
5. Type the cli command to consume messages from partition 2.
   1. $ kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic t-multi-partitions --offset earliest --partition 2


- Now, if we run the producer again, data distribution will be same based on key.
- So certain key goes to partition 0, some goes to partition 1, and some others to partition 2.

- Let’s try to run the producer again, without stopping kafka console consumer.
- Now, we will send data starting from counter 0 to 10000 so we get some number of data to experiment with And pause the producer a while to get better visual on how the message arrives.
- Now, run the producer again and let’s inspect the kafka console consumers.
- As you can see, the key is consistently goes to same partition.
- In the next lecture, we will use this topic and partition to see how we can create multiple consumers from Spring.


## 29. Multiple Consumers for Each Topic

- Consider this scenario:
- You have a producer that put messages in a very short time, say every half second, and the producer keep running.
- In your consumer, it needs up to two seconds to process each message.
- If you only have single consumer, eventually a lot of messages will remain on topic, and process will become bottleneck.
- One solution for this is to put multiple concurrent consumers for the topic.
- Multiple consumers in kafka depends on how many partition a topic has.
- To find out more about the theory, please see the previous lectures about partition and consumer.
- In this lecture, I will show you that with Spring, it’s very easy to add multiple consumers.
- Spring will maintain how the consumers run and you can avoid low level thread programming.
- Spring already handles them.


- At this point we should have topic t-multi-partitions with three partitions, created from lecture about producing message with key.
- If you does not have it, please create the topic first.
- In this lecture, we will learn how can we tell Spring to create multiple consumers.

- Project Reference: kafka-core-consumer
  - Class: KafkaKeyConsumer.java


![alt text](https://github.com/marodrigues20/udemy_java/blob/main/JavaSpring%26ApacheKafkaBootcamp-BasicToComplete/Sections/Section-06/ConsumeOfSetOnFirst_2.png?raw=true)

- We have three partitions and four consumers, so the fourth consumer is currently idle.
- In this lecture, we will learn how to modify the partition so the fourth consumer can be utilized.
- After few moments, Kafka will automatically recognize the fourth partition and start sending and consuming using fourth partition.
- To modify topic, execute this command on kafka.

1. $ kafka-console-consumer.sh --bootstrap-server localhost:9092 --alter --topic t-multi-partitions --offset earliest --partition 4
2. $ kafka-console-consumer.sh --bootstrap-server localhost:9092 --describe --topic t-multi-partitions --offset earliest --partition 4

- Now if we see the topic, we will see that it has 4 partitions.
- Let’s restart the kafka producers and consumers.
- See the log and then you will see that all four consumers are currently working.
- We now process four messages per second.
- hopefully it clear now that partitioning is a way to achieve parallel processing in Kafka.
- One thing to remember : we can delete topic and all partitions in it, but we cannot delete individual partition in Kafka.
- Deleting partition can cause data loss and the data's keys might not be distributed correctly, since new messages would not get
  published to the same partitions as old existing messages with the same key.
- If you want to decrease partition, the only way is deleting the topic and then re-create it.
- Be careful though, deleting topic means deleting all data in that topic.
- Please note that in this course, we are not deleting topic, since I use kafka on top of docker windows, and sometimes it is problematic.
- In real life, kafka server is better runs as native installation on top of linux.

### What About Deleting Partition?

- Delete topic is OK
- Can't delete partition
- Can cause data loss
- Wrong key distribution
- Decrease partition > delete & recreate topic





