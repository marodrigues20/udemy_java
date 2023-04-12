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
