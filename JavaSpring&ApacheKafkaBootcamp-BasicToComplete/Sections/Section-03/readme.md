# Section 3 - Technology in This Course

## 6. Messaging System

- Messaging system is communication mechanism for "application-to-application", sometimes also referred as A2A-.
- Handles messages on organization
- Integration


### Examples of Use Cases

- Human Resource System [New employee, employee resign]
- Marketing System [Has its user management module]
- Active Directory [Used by other systems for store username/password]

### Without Messaging System

- Without messaging system, following scenario is commonly used:
- Data come to Human resource system (for example, Human Resource staff entry new employee or mark other employee as resign).
- Marketing system pull data from Human Resource database, or via API using scheduler that runs every hour And then marketing system process that data.
- Active Directory, on the other hand, has different characteristic.
- In this case, Human Resource System push data of new employee and resign employee as a text file.
- Pushing runs using scheduler every day at 1 o’clock.
- The estimated time for this push process is about 30-40 minutes, so Active directory has another scheduler to process text file every 2 o’clock.
- As you can see, there is a delay when using this scheduler mechanism.
- Messaging system can handle this kind of situation on almost real-time fashion. 
  

### With Messaging System

- Using Kafka as messaging system, we put Kafka as centralized message queue, and the process become like this: 
- Data come to Human resource system.
- Human Resource System directly send incoming data to Kafka.
- Both marketing system and active directory receive incoming data and each of them process it according to their requirement.
- This sending and receiving cycle happens in almost real-time fashion, starting from milliseconds for each processing cycle.
- Kafka is known for its excellent performance, and most likely will take only milliseconds for sending and receiving message, assuming no network error.

### A Little Note

- Previously, I mention long process where Human Resource System took 30-40 minutes to generate text file.
- I put extreme time interval to give a sample explanation.
- However, in real life -especially on production environment- you should avoid such long process from happening.

### Without Messaging System

- When data communication happens, there will be source application and target application.
- At first, we might start simple.
- One source application to one target application, maybe using direct database link for data transfer. But then after a while, you have another source application.
- This time communication using API.
- Then the company grows, and you got many more applications that needs to communicate each other.
- This diagram is oversimplified.
- In reality, one application might be a source and also target for another system and they have to exchange data one and another.


### Problems?

- Many Integrations point
- Different implementation
  - Data protocol: database link, API, file
  - Data format: binary? JSON? CSV? XML
- Maintain connection


- Well, assume you have p number of source applications and q number of target applications, where each source must communicate with each target, you need to define "p multiply q" integrations point and each integration point come with it’s own way.
- Like how the data access happens.
- Each will use different protocol.
- Some might use database link, some API, and the others are file.
- Of course, accessing data using database link will have different format with accessing data from API. So that’s another problem.

### Kafka as Messaging System

- To solve that problems, messaging system can helps.
- Messaging system allows you to decouple your application and data communication.
- So the source system will send data to messaging system, while target system takes data required from messaging system.
- When using a messaging system, we can define a standard to send data.
- For example, send data in JSON format.
- So all applications will talks in same format : JSON.
- The integration points now not as many as direct communication, and has same protocol using messaging system protocol.
- Messaging system is designed in such manner that they will have high throughput for data communication.


### Kafka = Messaging System?

- Kafka is not only a messaging system.
- It is an event streaming platform.
- What that means?
- We will see it at the next lecture.

## 7. Kafka Introduction

### What is it?


- On it’s website : kafka.apache.org, it says:
    - Apache Kafka is a distributed streaming plataform.
    - A streaming platform has three key capabilities:
      - Publish and subscribe to stream of records, similiar to a message queue or enterprise messaging system
      - Store streams of records in a fault-tolerant durable way.
      - Process streams of records as they occur.



- This lecture will provides basic introduction for each item.


### Publish and subscribe to stream of records, similiar to a message queue or enterprise messaging system

![alt text](https://github.com/marodrigues20/udemy_java/blob/main/JavaSpring%26ApacheKafkaBootcamp-BasicToComplete/Sections/Section-03/Publisher_Subscriber.png?raw=true)

- This is the diagram for kafka as messaging system.
- We have source application, the blue boxes, that will publish message to kafka, So we called them "publishers" or "producers".
- The target application, the red boxes, will subscribe for the message.
- This red nodes commonly known as "subscriber", "consumer", or "listener".

- The message, or in this statement is "records", will be continuously flowing as long as there is any transaction on publisher.
- That’s why we call it as stream of records.



### Store streams of records in a fault-tolerant durable way

![alt text](https://github.com/marodrigues20/udemy_java/blob/main/JavaSpring%26ApacheKafkaBootcamp-BasicToComplete/Sections/Section-03/Store_Streams.png?raw=true)


- Kafka, is more than just messaging system.
- Each message that comes to kafka, or the streams of records, is stored on disk.
- Kafka has great support to make those records highly available.
- We can create multiple kafka nodes as a cluster, in such way, that a message is replicated among nodes.
- When one broker is fail, for example when network unavailable for node2, the other two nodes will take over and the message will be saved.
- Publishers and consumers can still works as usual, without knowing that node 2 is down.
- The records will stay in kafka node for as long as you need.
- It can be weeks, or maybe months.
- And you only need to make sure the disk storage is enough.
- This is why we call it durable.


### Process streams of records as they occur.

![alt text](https://github.com/marodrigues20/udemy_java/blob/main/JavaSpring%26ApacheKafkaBootcamp-BasicToComplete/Sections/Section-03/Process_Streams.png?raw=true)

- Kafka has topic for storing messages.
- Consider kafka topic as a "queue" for messages.
- As with the queue, the FIFO (first in, first out) concept is valid.
- So each message that comes to each kafka topic will be stored in order.
- Kafka guarantees the order of records for both publisher and consumer.
- So if publisher publish the following messages. M1, M2, M3.
- The publisher will process the message in order they arrived, so it’s guaranteed for the subscriber to process M1 first, then M2, then M3.
- Please note that this diagram is only a basic concept of kafka ordered processing.
- You might see different diagram later when we learn more about kafka concept.


### Kafka Use Cases

- As a streaming platform, Kafka is very high performance and can work with high volume data, when configured properly.

- Central Messaging System
- Activity / application log
- Storing IoT data
- System decoupling
- Asynchronous processing
- Part of big data ecosystem


Kafka as data streaming mechanism
We need to write publisher & consumer

### Kafka Distribution

- Apache Kafka 
- Confluent (www.confluent.io)

- In short, Apache Kafka is the core, the car engine.
- Confluent is a platform.
- It has apache kafka as the core engine, and it also has additional modules around the core engine.
- It is not just the engine, but a complete car.
- So it has many additional tools: for monitoring, for managing kafka clusters, for advanced kafka programming, etc.


## Java & Spring

- One of most-used programming language
- Regular update
- Runs on multiple platforms: Windows, Mac, Linux, Android
- Oracle & Open source implementation (OpenJDK, Amazon Coretto)

### Spring Framework

- Spring framework helps a lot
- Testable
- Free & open source
- Has almost everything required for enterprise programming.
- Complex configuration on early days
- Spring boot simplify configurations a lot
- https://spring.io

### Spring Technology Stack

- Microservice & reactive support
- SQL & NoSQL database
- Spring cloud for microservice
- Messaging system (Kafka, Kafka)
- And many more... (https://spring.io/projects)
  
### Eclipse

- Integrated Development Environment
- Code completion
- Automate processes
- Easy debugging
- Build tools & dependency management using Gradle
  
### Eclipse & Spring Tools Suite

- Official project from Spring
- Provide ready-to-use features for Spring development
- Stand-alone or Eclipse plugin

### Gradle

- Build tools & dependency management
- Maintaining library manually is nightmare
- Gradle manages library dependencies
- Build distributed package
- Spring Boot generates initial Script.