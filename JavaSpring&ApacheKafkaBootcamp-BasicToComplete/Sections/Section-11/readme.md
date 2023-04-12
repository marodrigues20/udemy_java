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

