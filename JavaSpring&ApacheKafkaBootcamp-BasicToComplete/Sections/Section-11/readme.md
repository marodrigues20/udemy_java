# Section 11.  Kafka in Microservice Architecture & Pattern

## Application Overview

- Oversimplified application 
- Introducing possible kafka usage on real life
- Microservice architecture & pattern
- Microservice reference on last lecture

## Use Case

- Commodity trading company with multiple branches
- Branch submit purchase order to head office 
- After process:
  - Pattern analysis
  - Reward
  - Big data storage
- Speed is the key
- Branch office submit each order once

## Application Architecture

![alt text](https://github.com/marodrigues20/udemy_java/blob/main/JavaSpring%26ApacheKafkaBootcamp-BasicToComplete/Sections/Section-11/pic_1.png?raw=true)

## Layer Frame for Order Screen

- This UI will call the API from another system.

![alt text](https://github.com/marodrigues20/udemy_java/blob/main/JavaSpring%26ApacheKafkaBootcamp-BasicToComplete/Sections/Section-11/pic_2.png?raw=true)


## App in The Course

- Use postman to simulate API call
- In memory H2 database
  - Data gone every restart
  - Database is not focus
  - Algorithm & source code structure

## Microservice & Client

- Spring Boot
  - Kafka Order : port 9091
  - Kafka Pattern
  - Kafka Reward
  - Kafka Storage
- Client
  - Postman : download from postman.com


# Section 49. Organizing Source Code

## The Need of Organizing

- Many codebase
- Applications
  - Easy to work with
  - Easy future changes
- Easy transfer knowledge & employee onboarding
- Multiple coding & source code organizations
- Patterns for code structure & organization

## Hexagonal Architecture

- Hexagonal architecture also known as a ports-and-adapter architecture.
- Hexagonal architecture organizes the application logical view -or source code- in such a way, so business logic is placed in center of hexagon.
- Hexagon represents the application.
- Business logic will need to communicate with nodes outside hexagon, like database, message broker, or other application’s API.
- To communicate with outside world, business logic does not do it directly on its core code.
- Instead, its using port, or adapter.
- Communication can happened in two ways: Inbound, means outside world invoke the business logic, or outbound, where the business logic invoke outer world.
- An example of inbound port, is when client triggers business logic to save data, through REST API.
- In this case, REST API is the inbound adapter.
- Another example of inbound adapter, is when changes happened from other system are published to message broker.
- The application listen to message broker through listener adapter, and execute business logic.
- The other nodes drawn in this diagram will be needed by business logic for outbound communication.
- For example, writing data or reading data from database, invoked by business logic through database repository.
- Executing other application API should be done by creating API client.
- And publishing message to broker done by writingpublisher adapter.
- All of this code, business logic and adapters, are organized in such way that functionality that adapter represents does not directly written in business logic code.


![alt text](https://github.com/marodrigues20/udemy_java/blob/main/JavaSpring%26ApacheKafkaBootcamp-BasicToComplete/Sections/Section-11/pic_3.png?raw=true)

## Hexagonal Architecture - Benefits

- The major benefit of hexagonal architecture, is that it decouples business logic from data access logic in adapters.
- This means it will be much easier to test, or change, either business logic or adapters.
- For example, when the organization switch from kafka to RabbitMQ message broker, the business logic does not need to change.
- We need to focus the change on listener and publisher, which is inbound and outbound adapters to message broker.
- We can focus to test the adapters to make sure that it works fine, then when all is good, we can then continue to test the business logic, which in this case does not change.
- On the other hand, changing business logic, say changing interest rate formula, does not need to change data access logic code.
- The business logic can also communicate with multiple adapters, each of which is an adapter to different outside nodes.
- Consider instead of changing kafka, the organization needs to works with system X that rely heavily on RabbitMQ, so there are two message brokers.
- An invoice cancelled message can be published by existing system to Kafka, or by system X to RabbitMQ.
- In this case, the application need to write another inbound listener adapter for RabbitMQ, for specific case, where both listeners then invoke same “cancel invoice” business logic.

## Command Query Separation (CQS)

- Command Query Separation, or CQS pattern, has been there for long time, and its proven good.
- The CQS principle is that every method should either be a command that called to performs data change, or a query that returns data to the caller, but never both.

![alt text](https://github.com/marodrigues20/udemy_java/blob/main/JavaSpring%26ApacheKafkaBootcamp-BasicToComplete/Sections/Section-11/Pic_4.png?raw=true)

- In some organization, I found the command side can also known as “transaction”, “modifiers”, or “mutators”.
- While the query side also known as “view”.
- Whatever the term, the idea is the same :
  - organize the code to separate data change and data read, never mix both.
- This has benefit since reading or modifying might not just a simple statement or query, but in most cases, we only need to either write or read.
- Separating both concern can make easier maintenance and change on source code.

## Application source code

![alt text](https://github.com/marodrigues20/udemy_java/blob/main/JavaSpring%26ApacheKafkaBootcamp-BasicToComplete/Sections/Section-11/Pic_05.png?raw=true)


# 50. Setting Up The Projects

## Spring Initializr

- Generate 4 java / gradle project from start.spring.io
  - Group: com.course.kafka
  - Artifact: 
    - kafka-ms-order
    - kafka-ms-pattern
    - kafka-ms-reward
    - kafka-ms-storage
  - Package name: com.course.kafka (remove any suffix)
- Spring boot 2.x
- Java 17

## Dependencies

- Kafka-ms-order
  - Web
  - Spring Kafka
  - Devtools
  - JPA
  - H2 database
- Other 3 projects
  - Spring kafka
  - Devtools


## Spring Application Config Listener

- We have spring.kafka.listener.missing-topics-fatal set to false.
- This allows application to start even if application has configured topic that not present on the kafka broker.
- For example, if your kafka has topic t-one and t-two, but an application has listener that listens t-nine, by default Spring kafka application will not runs because topic t-nine is not exists.
- Such behaviour is good, but at some cases you might want to disable that behaviour.
- The common case is on development phase where certain kafka topic is not exists, but you need to write code for your own part, then setting this value to false is good idea because you can start your job without waiting kafka topics created.

![alt text](https://github.com/marodrigues20/udemy_java/blob/main/JavaSpring%26ApacheKafkaBootcamp-BasicToComplete/Sections/Section-11/pic_06.png?raw=true)


## Message Converter


![alt text](https://github.com/marodrigues20/udemy_java/blob/main/JavaSpring%26ApacheKafkaBootcamp-BasicToComplete/Sections/Section-11/pic_07.png?raw=true)


![alt text](https://github.com/marodrigues20/udemy_java/blob/main/JavaSpring%26ApacheKafkaBootcamp-BasicToComplete/Sections/Section-11/pic_08.png?raw=true)


## Spring Application Config - Producer

![alt text](https://github.com/marodrigues20/udemy_java/blob/main/JavaSpring%26ApacheKafkaBootcamp-BasicToComplete/Sections/Section-11/pic_09.png?raw=true)


## Spring Application Config - Consumer

![alt text](https://github.com/marodrigues20/udemy_java/blob/main/JavaSpring%26ApacheKafkaBootcamp-BasicToComplete/Sections/Section-11/pic_10.png?raw=true)

## Variable Declaration

![alt text](https://github.com/marodrigues20/udemy_java/blob/main/JavaSpring%26ApacheKafkaBootcamp-BasicToComplete/Sections/Section-11/pic_11.png?raw=true)


## 51. Create Topic Programmatically

- Spring Kafka has helper class to create topic from code.
- This lecture will show you how to do it, as a reference.
- However, Iâ€™m not recommend to create topic programmatically in production environment.
- In production, let the kafka administrator create topic with correct configuration, like partition numbers.

### Project Reference

Project Reference: ../kafka-microservice/kafka-ms-order
- Classes Added / Modified: 
  - KafkaConfig.java

## 52. Order App - Database

Project Reference: ../kafka-microservice/kafka-ms-order
Classes Added / Modified: 
  - OrderItem.java
  - Order.java
  - OrderItemRepository.java
  - OrderRepository.java

## 53. Order App - Kafka Producer


![alt text](https://github.com/marodrigues20/udemy_java/blob/main/JavaSpring%26ApacheKafkaBootcamp-BasicToComplete/Sections/Section-11/pic_12.png?raw=true)

![alt text](https://github.com/marodrigues20/udemy_java/blob/main/JavaSpring%26ApacheKafkaBootcamp-BasicToComplete/Sections/Section-11/pic_13.png?raw=true)

### Project Reference

Project Reference: ../kafka-microservice/kafka-ms-order
Classes Added / Modified: 
  - OrderMessage.java
  - OrderProducer.java

## 54. Handle Kafka Publish Result - Kafka Producer Callback

- So far, we publish with kafkaTemplate.send.
- If you see the return type of kafkaTemplate.send, it will return ListenableFuture object.
- In reality, publishing might not always smooth.
- Broker might not be available, or network having latency.
- In such case, we can add callback to ListenableFuture object, to handle publishing success or failure.
- Failure can be happened if kafka is not available and message cannot be published after certain time.

![alt text](https://github.com/marodrigues20/udemy_java/blob/main/JavaSpring%26ApacheKafkaBootcamp-BasicToComplete/Sections/Section-11/pic_14.png?raw=true)

![alt text](https://github.com/marodrigues20/udemy_java/blob/main/JavaSpring%26ApacheKafkaBootcamp-BasicToComplete/Sections/Section-11/pic_15.png?raw=true)

### Project Reference

Project Reference: ../kafka-microservice/kafka-ms-order
- Classes Added / Modified: 
  - OrderProducer.java



## 55. Order App - API & Finalize App

- What We Will Build
  - API for submit order
  - Service & action for business logic
  - API is entry pointfor business logic service & action

### Service? Action?

![alt text](https://github.com/marodrigues20/udemy_java/blob/main/JavaSpring%26ApacheKafkaBootcamp-BasicToComplete/Sections/Section-11/pic_16.png?raw=true)


### Create Topic Programmaticatlly

- Database change? Then publish message 
  - Communicate with other system
  - Data analytics
- We called "Change Data Capture (CDC)"
- Database operation + message publishing
- Manually writing code
  - Techinical error
  - Human error

### Consistence Publishing 

  - Make sure database transaction & kafka publish is atomic
  - Database log tailing
  - Transactional outbox
  - See last lecture for "Microservice Architecture & Pattern" Reference


### Project Reference

Project Reference: ../kafka-microservice/kafka-ms-order
- Classes Added / Modified: 
  - OrderApi.java
  - OrderResponse.java
  - OrderRequest.java
  - OrderItemRequest.java
  - OrderService.java
  - OrderAction.java


## 56. Order App - Test the App

- Open kafka and create console consumer that listens from t-commodity-order.
  - $ kafka-console-consumer.sh --bootstrap-server.sh --bootstrap-server localhost:9092 --from-beginning --topic t-commodity-order
- We will not create this form for testing API. To test the API, we will use Postman.
- If you are not familiar with Postman, it is a development environment, specific for API.
- Download it from postman.com and open it. Download and extract zip file that contains/
  - Download from: postman.com

![alt text](https://github.com/marodrigues20/udemy_java/blob/main/JavaSpring%26ApacheKafkaBootcamp-BasicToComplete/Sections/Section-11/pic_17.png?raw=true)

- Run the project that you have been built till now
  - Project Reference: ../kafka-microservice/kafka-ms-order

- Open postman
- Import the file: spring-kafka-postman-collection.json
- Run "Order 1 Random Item" via postman.
- Check the Intellij Console
- Check that message has been generated in "t-commodity-order"
- Open the browser and use the url: localhost:9001/h2-console/
  - Check tables: ORDERS and ORDER_ITEMS

Note: Use url in application.yml and username: sa and password doesn't required.


## 57. Pattern App - Kafka Consumer

### Project Reference

Project Reference: ../kafka-microservice/kafka-ms-order
- Classes Added / Modified: 
  - OrderConsumer.java
  - OrderMessage.java


## 58. Order App - Promotion Publisher

### Create another topic

- $ kafka-topics.sh --bootstrap-server localhost:9092 --create --partitions 1 --replication-factor 1 --topic t-commodity-promotion

### Project Reference

Project Reference: ../kafka-microservice/kafka-ms-pattern
- Classes Added / Modified: 
  - PromotionMessage.java


Project Reference: ../kafka-microservice/kafka-ms-order
- Classes Added / Modified: 
  - PromotionRequest.java
  - PromotionProducer.java
  - PromotionService.java
  - PromotionAction.java
  - PromotionApi.java


### How to Run

1. Run the "afka-ms-order".
2. Open a terminal and type:
   1. $ kakfa-console-consumer.sh --bootstrap-server localhost:9092 --from-beginning --topic t-commodity-promotion
3. Open Postman and post request
   1. /Course-Spring Kafka 4/Create Promotion (Post)



### Notes

- Previously, we learn that the kafka template send method is asynchronous.
- What if we want to do synchronous send?
- It is possible, although I’m not recommend it.
- But just for your knowledge, we can do synchronous send and block until send result is received by using method get after send. Like this.
- This might throw error, so wrap it on try-catch block and log the error.
- The send method will return SendResult, so we can put that return value in variable and process further. Like this.
- Remember, for production, it is better to use asynchronous publish with callback, otherwise you risk your publisher to be blocked.

```
public void publish(PromotionMessage message){
        try {
            var sendResult = kafkaTemplate.send("t-commodity-promotion", message).get();
            LOG.info("Send result success for message {}", sendResult.getProducerRecord().value());
        } catch (InterruptedException | ExecutionException e){
            LOG.error("Error publishing {}, because {}", message, e.getMessage());
        }
    }
```


## 59. Promotion & Discount


### Explanation

![alt text](https://github.com/marodrigues20/udemy_java/blob/main/JavaSpring%26ApacheKafkaBootcamp-BasicToComplete/Sections/Section-11/pic_18.png?raw=true)

### Source Code for Discount

![alt text](https://github.com/marodrigues20/udemy_java/blob/main/JavaSpring%26ApacheKafkaBootcamp-BasicToComplete/Sections/Section-11/pic_19.png?raw=true)


## 60. Storage App - Kafka Consumer

![alt text](https://github.com/marodrigues20/udemy_java/blob/main/JavaSpring%26ApacheKafkaBootcamp-BasicToComplete/Sections/Section-11/pic_20.png?raw=true)


### Project Reference

Project Reference: ../kafka-microservice/kafka-ms-order
- Classes Added / Modified: 
  - DiscountRequest.java
  - DiscountApi.java
  - DiscountMessage.java
  - DiscountProducer.java
  - DiscountAction.java
  - DiscountService.java



### Project Reference

Project Reference: ../kafka-microservice/kafka-ms-storage
- Classes Added / Modified: 
  - OrderMessage.java
  - PromotionMessage.java
  - DiscountMessage.java
  - PromotionConsumer.java
  

### How to Run

1. Run the "kafka-ms-order".
2. Run the "kafka-ms-storage".
3. Open postman and post a request bellow:
   1. Create Promotion
   2. Create Discount
4. Check the logs in "kafka-ms-storage". 