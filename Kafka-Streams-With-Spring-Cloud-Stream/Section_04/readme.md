# Section 04: Understanding Kafka Support in Spring

## 10. Understanding Kafka Support in Spring

- Kafka offer two types of APIs:
    - Kafka Client APIs -> Allows create Kafka Producer and Consumer Applications
    - Kafka Stream APIs -> Allows create Stream Process Applications.

- Spring Implement Kafka in two ways:
    - 1. Spring for Apache Kafka -> Spring started implementing Kafka support in the Spring Framework. (Core-Features) 
    - 2. Spring Cloud
        - 1. Spring Cloud Streams
            - 1. Kafka Binder
            - 2. Kafka Stream Binder

Note: https://spring.io/projects/spring-kafka : Alined with Kafka Client API
      https://spring.io/projects/spring-cloud : 
      https://spring.io/projects/spring-cloud-stream : We have to Binder Implementation. 
        - Apache Kafka is a traditional Binder. We do not recomend or use 
          traditional Kafka Binder because of lacks the KStream and KTable features of Kafka Stream.
        - Kafka Stream binder is the most advanced Kafka binder API, and this course is primary focused on the same.


- So we are left with the two options to work with Kafka:
    - 1. Spring for Apache Kafka
    - 2. Spring Cloud Streams
        - Kafka Streams Binder

- Simple question: Which one to use? Answer is simple: Both
    - A tipical Kafka project will use both methods

    - Kafka Stream is a specialized framework to implement a stream processor pattern. In this pattern, you will read a data stream from Kafka, process it,
    and write it back to Kafka. There are two limitations:
        - Kafka Stream microservices will consume data from a Kafka topic only.
        - kafka Stream don't support read data from another source.

- WARNING: We use the Spring for Kafka project in the following scenarios
    - You need a producer-only or a consumer-only and also want to interact with other non-Kafka service (REST/SOAP/DB).
    - You are most likely to use the Kafka producer-consumer API.

- WARNING: You use Kafka Streams in the following scenarios:
    - You have the requirement to read a data stream from Kafka, implement some stream processing techiniques as agregattion, summarization, windowing, 
    and send the summarized results to another Kafka topic.
    - Kafka Streams does not limit you on the output side. You can send the result to Kafka Topic, or you can also send it to some other third-party 
    system such as a database or a RESTful service. But you are most likely to send it to a Kafka Topic to implement a simplified architecture.
    - Kafka Streams is much more potent for computing real-time aggregates and summaries.

- The Main foucus in this course is on Spring Cloud Streams but it will cover some aspects of Spring for Apache Kafka.

## 11. Introduction to Spring Cloud Streams

- Spring framework always aspired to simplify enterprise Java development. They offered the Spring Boot framework to simplify creating Java-based microservices. Similarly, the Spring integration framework was designed to provide a solution for simplifing application integration. However, some modern enterprise application started to implement distributed event-driven streaming architecture. These applications showed two two common traits:
    - They run in a cloud platform and follow a microservice architecture.
    - They continuously operate on a stream of events or messages.


### Understanding Spring Integration - Application Architecture

i.e: Spring-Integration-Architecture.png

- Spring Integration framework already offered a similar solution for enterprise integration patterns. In this pattern, one application publishes a message to a common message channel. Other application can read the message from the channel at a later time. In all this, the application must agree on a
shared channel and the format of the message. And the communication remains asynchronous. Spring realized that this framework could be extended and simplified further to support the modern demand for event-driven streaming architecture. As a result, Spring came up with a new framework - Spring Cloud Streams.

i.e: Spring-Cloud-Stream.png

- Spring Cloud Streams is a new framework that combines the power of two Spring Frameworks. Spring Boot and Spring Integration. So, the Spring Cloud Stream builds upon Spring Boot to create standalone, production-grade microservice application and uses Spring Integration to provide connectivity to message brokes. As a result, Spring Cloud Stream turns out to be a framework for building event-driven stream processing microservices.

### Understand Spring Cloud Stream - Application Architecuture

- A Spring Cloud Stream application is a message-driven microservice application. All we do in a Spring Cloud Stream application is to consume message events, process it to take necessary business actions, process it to take necessary business actions and produce events for other microservices. And a typical Spring Cloud Stream Application is internally architectured like this.


i.e.: Spring-Cloud-Stream-Architecture.png

- We have the main application code for processing message events received through one or more input channels.
- The core of your application implements all the business logic and takes the necessary business actions.
- Finally, if needed, you can produce message events to one or more output channels.

- An application developer is focused on processing message events, implementing necessary business actions, and producing new messages. Rest all is taken care of by the framework. Spring Cloud Stream allows you to create and configure input/output message channels using simple and straighfoward configurations. These channel configurations are also known as spring-cloud-stream-bindings. And these bindings are picked up the binder to implement the 
input/output channel and communicate with the external messaging system. So, the binder is a third party Spring component responsible for providing integration with the external messaging systems. As of date, we have a bunch of open sources and third party binders.

 - 1. Apache Kafka
 - 2. Kafka Stream
 - 3. RabbitMQ
 - 4. Amazon Kinesis
 - 5. Google Pubsub
 - 6. Azure Event Hubs

- The binder is reponsible for the connectivity and routing of messages from the respective messaging systems. They are responsible for data type conversions and a few other things. But the most essential feature of the binder is this:
    - They will collect the income message events from the messaging system, do the necessary data type conversions, call the user code, and pass each message to you for processing.
    - Similarly, they will take up your outgoing message events, do the necessary data type conversion, and send the message to the underlying message system.

- Most part of headache of interacting with the messaging system is taken care of by the binder. However, you will also need to configure the binders according to your requirements. So, in short, A Spring Cloud Stream developer is responsible for the following things:
    - 1. Configure input/output channel bindings
    - 2. Configure the binder
    - 3. Implementing the business logic


- Many of the channel binding configurations and the binder configurations are defined in your application YAML. However, several things are configured using Spring Cloud Stream annotations.

- We don't need to read messages from the Kafka Cluster, all is done by the framework.

### Kafka Binders

- Spring Cloud Stream supports two Kafka Binders:
    - 1. Apache Kafka Binder - that internally implements Kafka Producer and Consumer Client APIs.
    - 2. Kafka Streams Binders 

Note: 1. This course is about Kafka Stream implementation.
      2. Kafka Stream is an extention of the Producer/Consumer Client APIs, and it offers you out of the box stream processing capabilites that are not offered by the Kafka Client APIs.  



## 12. Introduction to Kafka Stream

- Kafka Stream is a client library for processing and analysing data that is arriving from Kafka Topics.
- Kafka Stream is just a Java library, and its core functionality is available as Streams DSL.

### What is Streams DSL

- Streams DSL is a high-level API that offers standard data transforming functions using two main abstractions.
    - KStream 
        - It is the most essential abstraction offered by Kafka Stream API. 
        - Insert-only table with two columns - key and value
        - KStream represents a key/value table where data is comming from Kafka, and it is being continuously inserted
    - KTable
        - It is the second most crucial abstraction
        - Think of the KTable as an insert/update table with key and value columns.
        - The KTable-key acts as a primary key
        - When a new record comes to the KTable, Kafka will check for the primary-key existence. If the key already exist, the old record is updated with a new value. If the key not exist, a new record is inserted.

- Whereas the KStream is a table where data is comming from Kafka and getting inserted to KStream even if the key already exists.

### How Kafka Streams DSL helps implementing business logic?

- Kafka Streams DSL is a very well thought API set that allows you to handle most stream processing needs.

    - You can compute aggregates on KStream and KTable
    - You can join them together like you will join a database table
    - You can create time-based windows and compute windowing agregates on these tables.

Note: All such transformations and operations are available to us as methods on the KStream and KTable.

### What is the role of two technologies?

- We use Spring Cloud Streams to create a microservices application and communicate with the Kafka Cluster
- Kafka Streams will help us to process the incoming data, apply transformations, and solve the business problem.