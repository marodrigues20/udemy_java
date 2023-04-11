# Section 10. Rabbitmq vs Kafka

## Popular Messaging System

- Kafka 
- Rabbitmq

## Message Retention

![alt text](https://github.com/marodrigues20/udemy_java/blob/main/JavaSpring%26ApacheKafkaBootcamp-BasicToComplete/Sections/Section-10/Pic_01.png?raw=true)

- In Kafka, message retention is based on policy.
- Every message in topic will be stored at your disk, up to certain time period that you can set.
- For example, if you need the data to be stored weekly, you can set the retention period of message to 7 days before it became unavailable.
- With such policy, when a kafka consumer process M1, M2, and M3, all of them will remains on disk for 7 days and another consumer -or even the same consumer- can process M1, M2, and M3.
- RabbitMQ store the message in queue, in memory or disk, based on your choice.
- The message will be stored until a consumer acknowledge, that is a consumer confirming that the message has been processed successfully.
- So one message, one successful process from one consumer, and the message is gone.
- If we need to process the same exact message, the publisher must re-publish the message.
- But since rabbitmq 3.9, there is a new feature called as stream, which behaviour is the same with kafka topic.

## Kafka - Message Routing

![alt text](https://github.com/marodrigues20/udemy_java/blob/main/JavaSpring%26ApacheKafkaBootcamp-BasicToComplete/Sections/Section-10/Pic_02.png?raw=true)

## RabbitMQ - Message Routing

![alt text](https://github.com/marodrigues20/udemy_java/blob/main/JavaSpring%26ApacheKafkaBootcamp-BasicToComplete/Sections/Section-10/Pic_03.png?raw=true)

## Kafka - Multiple Consumers

![alt text](https://github.com/marodrigues20/udemy_java/blob/main/JavaSpring%26ApacheKafkaBootcamp-BasicToComplete/Sections/Section-10/Pic_04.png?raw=true)


## RabbitMQ - Multiple Consumers

![alt text](https://github.com/marodrigues20/udemy_java/blob/main/JavaSpring%26ApacheKafkaBootcamp-BasicToComplete/Sections/Section-10/Pic_05.png?raw=true)