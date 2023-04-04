# Section 08: Handling Exception

## 41. KafkaListener Error Handler

### What We Will Do

- Spring default: log exception
- Able to implement our own error handler
- Scenario
  - Publish food order
  - Exception: consume invalid food amount

### Java Project

Project Reference: kafka-core-producer
Classes Added / Modified: 
    - FoodOrder.java
    - FoodOrderProducer.java
    - FoodOrder.java

Project Reference: kafka-core-consumer
Classes Added / Modified: 
    - FoodOrderConsumer.java
    - FoodOrderErrorHandler.java
    - FoodOrder.java


## 42. Global Error Handler

### What We Will Do

- Global error handler: works for all kafka consumers
- Error handler on Spring container
- Scenario
  - Publish random number
  - Consume odd number throws exception
  - Handle using global error handler


### Java Project

Project Reference: kafka-core-producer
Classes Added / Modified: 
  - SimpleNumber.java
  - SimpleNumberProducer.java
  - KafkaCoreProducerApplication_Section8.java
  
Project Reference: kafka-core-consumer
Classes Added / Modified: 
  - SimpleNumber.java
  - SimpleNumberConsumer.java
  - GlobalErrorHandler.java
  - KafkaConfig


### Explanation how Global Exception vs KafkaListener Error Handler

- The global error handler is triggerred for simple number.
- This is interesting, the global error handler is not triggered for food order.
- What this means?
- It should be "global", that works for all consumers, right?
- So this is what happened.
- On simple number consumer, we did not write any specific error handler.
- So when this consumer throws error, it will go directly to GlobalErrorHandler.
- But with FoodOrderConsumer, we define FoodOrderErrorHandler, so the exception go first to it.
- And it stops there, because -well- the FoodOrderErrorHander already handle it.


![alt text](https://github.com/marodrigues20/udemy_java/blob/main/JavaSpring%26ApacheKafkaBootcamp-BasicToComplete/Sections/Section-08/Global_Error_Handler.png?raw=true)


- If we want to continue this error, or any specific error, to global error handler, what we need to do is re-throw the exception, so the next error handler in chain which is GlobalErrorHandler- can handle it.
- To re-throw the exception, go back to FoodOrderErrorHandler.java.
- In the error handler, we need to re-throw the exception.
- It is up to you, what kind of exception you want to re-throw.
- In this case, I will re-throw all RuntimeException



## 43. Why Retrying Mechanism?

- Spring Kafka will log failed messages
- Our own ErrorHandler
- Case:
  - Service temporarily unavailable
  - Retry hit service without trigger from consumer
  - Retry for n times only


### Scenario

- Topic: t-image, 2 partitions
- Publish message represents image
- Consumer
  - Simulate API call to convert image
  - Simulate failed API call
  - Retry after 10 seconds
  - Retry 4 times


### Project Reference

1. Create a topic with 2 partition
   1. $ kafka-topic.sh --bootstrap-server localhost:9092 --create --partitions 2 --replication-factor 1 --topic t-image
   2. $ kafka-topic.sh --bootstrap-server localhost:9092 --describe --topic t-image

Project Reference: kafka-core-producer
Classes Added / Modified: 
- Image.java
- ImageProducer.java
- ImageService.java
- KafkaCoreProducerApplication_Section8.java