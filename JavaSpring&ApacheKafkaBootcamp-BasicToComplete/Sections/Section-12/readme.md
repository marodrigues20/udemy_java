# Section 12: Kafka Stream

## 64. Introducing Kafka Stream

### Data Transformation

- When we learn about pattern consumer, we do some process on the consumer.
- We listen each data, then transform the data by calculating total amount, as soon as data comes.
- This behaviour is so common, especially when we talk about big data.
- Kafka provides library to do transformation, within kafka itself.
- This library is kafka stream.

![alt text](https://github.com/marodrigues20/udemy_java/blob/main/JavaSpring%26ApacheKafkaBootcamp-BasicToComplete/Sections/Section-12/pic_01.png?raw=true)


- Kafka stream is a stream processing library.
- It is a small, lightweight java library.
- The idea is kafka stream take a topic as input.
- For every data, kafka stream consume it and transform it, then publish it to another different topic.
- Consider Kafka stream like a consumer that has many functions for data processing -transformation, enrichment, aggregation, et cetera-.
- Kafka stream at the same time is also producer that publish to another kafka topic.
- Then the consumer can then consume the transformed data directly from output topic 


![alt text](https://github.com/marodrigues20/udemy_java/blob/main/JavaSpring%26ApacheKafkaBootcamp-BasicToComplete/Sections/Section-12/pic_02.png?raw=true)


- Previously we have three consumer microservice built with spring kafka.
- Now with kafka stream, where should we put it?
- It depends.
- Kafka stream is just a library, we don’t need a separate application for deploying kafka stream application.
- So we can put it like here, as additional library for each consumer.
- Or we don’t want to disturb existing services, they are already busy and taking a lot of processing power.
- In that case we can create dedicated kafka stream application.
- We can use only kafka stream library, or we can combine it with spring kafka.

![alt text](https://github.com/marodrigues20/udemy_java/blob/main/JavaSpring%26ApacheKafkaBootcamp-BasicToComplete/Sections/Section-12/pic_03.png?raw=true)


- We can even mix it, let’s just say the storage app still has some computing power.
- We can put separate kafka-stream app for pattern and reward, while kafka-storage can has its own kafka-stream library for processing its own requirement Kafka stream is a stream processing library.

![alt text](https://github.com/marodrigues20/udemy_java/blob/main/JavaSpring%26ApacheKafkaBootcamp-BasicToComplete/Sections/Section-12/pic_04.png?raw=true)

### Kafka Stream 

- Stream processing framework
- Released on 2017
- Alternative for Apache Spark, Nifi, or Flink
- Stream & stream processing?

## 65. Stream Processing


### Data Stream

- A stream is sequence of data that comes in order.
- We usually represents stream with this kind of diagram, called as marble diagram.
- The arrow represents timeline, where left is earliest and right is latest.
- Each of circle represents one data.
- The data continuously comes over time, we don’t know when it will ends.
- Each data also called as event or record.
- Each data is immutable, cannot be changed.
- but we can be replay the sequence of data.
- Seems familiar?
- Kafka topic is one good example for stream.

![alt text](https://github.com/marodrigues20/udemy_java/blob/main/JavaSpring%26ApacheKafkaBootcamp-BasicToComplete/Sections/Section-12/pic_05.png?raw=true)


### Data Processing

- What do we use data for?
- We save those data in data store, this can be a simple text file, a database, or a big data cluster.

![alt text](https://github.com/marodrigues20/udemy_java/blob/main/JavaSpring%26ApacheKafkaBootcamp-BasicToComplete/Sections/Section-12/pic_06.png?raw=true)


- Then we process the data further, like transforming the data, doing some calculation, aggregating data, combining data, filtering bad data, et cetera.
- Usually we leave the original data intact and create a new file, table, or anything to keep the output.
- Usually we create batch depends on our need.
- For example, calculating interest rates can be done in daily basis, but checking whether material in warehouse still enough must be done in 30 minutes interval
to keep production line running.
- Lately, we also know the term microbatching, especially on big data when the data stream comes very fast.
- This is a batch that has very small interval, maybe in the matter of second.

![alt text](https://github.com/marodrigues20/udemy_java/blob/main/JavaSpring%26ApacheKafkaBootcamp-BasicToComplete/Sections/Section-12/pic_07.png?raw=true)


### Micro Batching

- Consider we have many runners on a marathon.
- Each runner has a sensor on the shoe.
- At every milestone, a reader reads the sensor and send data to storage.
- This is the data stream.
- Each data indicates who the runner is, when he passed, and at which milestone.
- We then process each data so we can know each runner’s position and updates the marathon dashboard.
- Micro batching runs on regular interval, in the example is every second.
- Why we need such “micro” time interval?
- Well, in case of marathon, we need to update dashboard.
- The audience will not wait for one minute to find out whether their favourite runner will wins.
- So our approach is a microbatch, which will only give small latency after the runner pass a milestone.


![alt text](https://github.com/marodrigues20/udemy_java/blob/main/JavaSpring%26ApacheKafkaBootcamp-BasicToComplete/Sections/Section-12/pic_08.png?raw=true)


- In batching, we process the data every certain time interval.
- In the daily batch, if we have 200 loan data to be calculated, we process all 200.
- If the next day we only have 170 loan data, that data will be processed., et cetera In the marathon example, if in one second we have three runners, we will process all three data after one second.
- When only one runner pass the milestone, we process that data, et cetera.
- No matter what the interval is, this is characteristic of batch processing.


![alt text](https://github.com/marodrigues20/udemy_java/blob/main/JavaSpring%26ApacheKafkaBootcamp-BasicToComplete/Sections/Section-12/pic_09.png?raw=true)


### Stream Processing 

- Stream processing is working with the data as soon as it arrives on data stream.
- Speaking about marathon example, the runner pass milestone and sensor reader on irregular interval.
- So the data also comes in irregular interval, in order.
- Sensor will send each data to data stream, and stream processing framework will process this data as soon as it arrives.
- So stream processing is different from microbatching.
- Say, for 10 seconds, no data arrives, then stream processing will not process anything during that time.
- On the other hand, when 5 data comes to stream, in millisecond delay each, stream processing will process them.
- Of course there is a latency during data arrives on stream and stream processing start.
- However this latency is very small, so we can say stream processing is near-real-time.


![alt text](https://github.com/marodrigues20/udemy_java/blob/main/JavaSpring%26ApacheKafkaBootcamp-BasicToComplete/Sections/Section-12/Pic_10.png?raw=true)

### Yes To Stream Processing 

- Yes, When:
  - (relatively) fast data flow
  - Application need to response quick to most recent data
- Example
  - Marathon
  - Credit card fraud
  - Stock trading
  - Log analysis

### No To Stream Processing

- Example
  - Daily interest
  - Forecasting


### To Stream or Not To Stream?

- Sample: check supply must be above threshold
- Depends on Service Level Agreement
- 30 minutes is good : batch
- Near real time : stream processing


## 66. Kafka Stream Concept

- In stream processing, we will see this kind of diagram.
- This is a DAG, or directed acyclic graph.
- In kafka stream term, we call these diagram as topology.
- Each circle represents a requirement for data processing -or commonly called as stream processor-, and each arrow is the stream.
- So topology is stream processors, chained together by streams.
- Stream processor process incoming data stream, data by data.
- The data stream itself is immutable, cannot be changed, but stream processor can create new output stream from it.
- Data always flows from the parent to the child nodes, never from child to parent.
- So in this topology, level 1 stream processor creates stream that processed by level 2 stream processor, but not vice versa.
- That’s why we called this is acyclic.
- Parent also known as upstream processor, while child also known as downstream processor.
- Each child node, in turn, can define its own child nodes, and so on.


![alt text](https://github.com/marodrigues20/udemy_java/blob/main/JavaSpring%26ApacheKafkaBootcamp-BasicToComplete/Sections/Section-12/pic_11.png?raw=true)


- This is the same topology, but has different colors, to explain kafka stream terminology.
- The blue nodes is source processor.
- It does not have upstream processor in topology.
- It consumes data from one or more kafka topics and forwarding them to its downstream processors.
- The red node is sink processor.
- It does not have downstream processor in topology.
- It sends received data from its upstream processors to a specified Kafka topic.

![alt text](https://github.com/marodrigues20/udemy_java/blob/main/JavaSpring%26ApacheKafkaBootcamp-BasicToComplete/Sections/Section-12/pic_12.png?raw=true)


### What We Will Learn

- Low Level Processor API
- This Course: Kafka Stream DSL (Domain Specific Language)
  - Simple
  - Provides common data transforming


### Serde (Serializer / Deserializer)

- In kafka stream, we have the term “Serde”, or Serializer/Deserializer.
- We already learn that every data in kafka topic contains key and value.
- In kafka stream, we must define the default serde for both key and value, although we can override serde for each stream.
- Kafka stream provides basic serdes like String, Long.
- They are available as static method from class Serdes.
- Since our value in this course is JSON, we can use JSON Serde provided by Spring.
- In case needed, we can also create custom serde.
- For example if the data format is on comma separated text, we can create our own CsvSerde.

![alt text](https://github.com/marodrigues20/udemy_java/blob/main/JavaSpring%26ApacheKafkaBootcamp-BasicToComplete/Sections/Section-12/pic_13.png?raw=true)



## 67. Prepare For Kafka Stream

### What We Will Have

![alt text](https://github.com/marodrigues20/udemy_java/blob/main/JavaSpring%26ApacheKafkaBootcamp-BasicToComplete/Sections/Section-12/pic_14.png?raw=true)

### Preparation

- When we learn about promotion and discount, we put multiple message types on one topic.
- Spring will automatically serialize or deserialize kafka message to java class.
- This feature is not available out-of-the-box in kafka stream.
- So we need to stop kafka containers, and delete kafka docker data, which is subfolder data on same folder with docker compose scripts.
- Then we re-create kafka containers.
- For other scripts, like other kafka topics or kafka-console-consumer, I provide them on last lecture of this course on reference section.
- Please download the script, extract and execute it when needed.
- I believe at this point you already know how to start them for debugging purpose, or see the data stream.
- Many code of kafka stream using Java 8 lambda style and functional programming.
- That knowledge is not in this course coverage, and there are many resources on the internet that teach about this.


### Download Source Code

- Available on last section of the course
- Source code, postman, kafka script
- Open kafka stream scripts, then execute all of it on kafka console.

### Spring Initializr

- Go to start.spring.io and generate java project with gradle.
- Generate 1 java / gradle project from start.spring.io
  - Group: com.course.kafka
  - Artifact: kafka-stream-sample
  - Package name: com.course.kafka (remove any suffix)
  - Dependency: Spring for Apache Kafka, Spring for Apache Kafka Stream, Spring Boot Devtools
- Spring boot 2.x

### Additional Projects

- To simulate transaction, we will use previous projects on lesson kafka microservice.
- To make the sample code more structured, on the downloaded source code, I will use these names for kafka microservices.
- But basically they are the same code with previous lesson.
- However we might add something later to simulate certain transaction for kafka stream.
- Copy paste all files from package broker.message on project kafka order, into kafka stream


## 68. Notes for Windows User

### Kafka on Docker

- At the next lesson, we will learn more concepts. If you run kafka using docker under windows, you might encounter error, where kafka broker, suddenly dead.
- This seems windows problem when use docker volume mounting.
- To handle that, remove the volume mounting when using docker.
- In real life, kafka is usually installed on linux.
- Administering kafka is not in this course scope and it is quite hard.
- There are several kafka product available as cloud service, but comes at price.
- Confluent is the most complete, but it is expensive.
- Amazon provides MSK, which is managed kafka cluster.
- Google does not has built-in kafka, but there is packaged kafka on google cloud marketplace.

## 69. Hello Kafka Stream

- Let’s start quick coding introduction to kafka stream. Make sure you already run the preparation, including creating topics. On the kafka-order promotion API,
  we can put any promotion code. The product owner decide that it will be better if the promotion code is on uppercase. For our first kafka stream application we will
  transform promotion code to uppercase. Follow along the code, don’t worry if you feel confuse.


### Promotion


![alt text](https://github.com/marodrigues20/udemy_java/blob/main/JavaSpring%26ApacheKafkaBootcamp-BasicToComplete/Sections/Section-12/pic_15.png?raw=true)


### Kafka Stream Basic Usage

- I will give kafka stream core concept after this lecture.
- To use kafka streams, there are several things to be done: Add the kafka stream library in the application. Define configurations. Create a topology. Start kafka stream.
- Stop kafka stream at some point, usually on application shutdown hook.


![alt text](https://github.com/marodrigues20/udemy_java/blob/main/JavaSpring%26ApacheKafkaBootcamp-BasicToComplete/Sections/Section-12/pic_16.png?raw=true)

### Kafka Stream With Spring

- To use kafka streams with spring, there are several things to be done:
- Add the kafka stream library in the application.
- Enable kafka stream and define kafka stream configuration. 
- Then build the topology. 
- Spring will manage the stream lifecycle, starting and stopping it.

![alt text](https://github.com/marodrigues20/udemy_java/blob/main/JavaSpring%26ApacheKafkaBootcamp-BasicToComplete/Sections/Section-12/pic_17.png?raw=true)

### Project Reference

- Project Reference: ../kafka-stream/kafka-ms-sample
  - Classes Added / Modified: 
    - application.yml
    - build.gradle.kts
    - PromotionUppercaseStream.java
    - KafkaStreamConfig.java


- Project Reference: ../kafka-stream/kafka-ms-order



### Topology

- This is the topology that we will build for uppercase promotion.
- The source processor will take data from this topic. We will have one sink processor to make promotion code uppercase, and send transformed data to this topic.

![alt text](https://github.com/marodrigues20/udemy_java/blob/main/JavaSpring%26ApacheKafkaBootcamp-BasicToComplete/Sections/Section-12/pic_18.png?raw=true)


### How To Run

1. Start ../kafka-stream/kafka-ms-order
2. Start ../kafka-stream/kafka-ms-sample
3. Go to Postman and create several new promotions
   1. Click on Promotion Collection.
      1. Click on the button "Run"
      2. Postman show a form
      3. Fill with 1000 interactions on the postman form
      4. Fill with 500 Delay ms on the postman form
      5. Click on "Run Course - Spring Kafka 4"
   
   
- Note: 
   - To make sure that kafka-stream sample application is started, see the console and you should see log lines which indicates state transition to RUNNING. Something like this.
   - Since we use method print for debugging, there will be logs in eclipse for every data in stream and you can see that the uppercase stream now contains uppercase promotion code.


### Creating a new Listener to check UPPERCASE 


- Project Reference: ../kafka-stream/kafka-ms-storage
  - PromotionUppercaseListener.java

### How To Run

1. Start ../kafka-stream/kafka-ms-order
2. Start ../kafka-stream/kafka-ms-sample
3. Start ../kafka-stream/kafka-ms-storage
4. Go to Postman and create several new promotions
   1. Click on Promotion Collection.
      1. Fill with 1000 interactions on the postman form
      2. Fill with 500 Delay ms on the postman form
      3. Click on "Run Course - Spring Kafka 4"
   

- Then check out the intellij logs on kafka-storage.
- Wait a second, something seems weird. This is our original data. The key is null, that’s OK, the value is valid json string with promotion code like this sample.
- This is our transformed data. The key is still null, and the value is valid json string, all uppercase.
- This happened because we use String serde, which takes all original value as string, and convert all of the string to uppercase. I think this is risky.
- If the kafka consumer is built using some framework which is json case sensitive, and we tell the developer that the JSON attribute is on camelCase, the kafka consumer might not be able to parse this uppercase json string. I think it will be better if we only make the json value uppercase, but attribute itself remains intact. There are several approach to do this, let’s see them on next lecture. Before we move on, I will comment the @Configuration annotation
on existing kafka stream classes, so we can start fresh.

![alt text](https://github.com/marodrigues20/udemy_java/blob/main/JavaSpring%26ApacheKafkaBootcamp-BasicToComplete/Sections/Section-12/pic_19.png?raw=true)


## 70. String Serde

- Project Reference: ../kafka-stream/kafka-ms-order
- Project Reference: ../kafka-stream/kafka-ms-storage
- Project Reference: ../kafka-stream/kafka-ms-sample
  - Classes Added / Modified: 
    - PromotionMessage.java
    - PromotionUppercaseJsonStream.java


### How To Run

1. Start ../kafka-stream/kafka-ms-order
2. Start ../kafka-stream/kafka-ms-sample
3. Start ../kafka-stream/kafka-ms-storage
4. Go to Postman and create several new promotions
   1. Click on Promotion Collection.
      1. Fill with 5 interactions on the postman form
      2. Fill with 0 Delay ms on the postman form
      3. Click on "Run Course - Spring Kafka 4"


- Note: Try it, go to postman and create several new promotions.
  - Then check out the eclipse logs on kafka-stream. Now you will see that the json attribute is as-is, but the value is uppercase. A little bit better, but we have to manually convert using Object Mapper. Well, the good news, Spring provides built-in Json Serde that we will learn on the next lecture.
  - Before we move on, I will comment the @Configuration annotation on existing kafka stream classes, so we can start fresh.


## 71. Spring JSON Serde

- Project Reference: ../kafka-stream/kafka-ms-sample
  - Classes Added / Modified: 
    - PromotionUppercaseSpringJsonStream.java


## 72. Custom JSON Serde

- Custom data format : comma separated, tab separated, ||| separated, etc
- Take message as string  & convert
- Custom serde
- Serializer + Deserializer + implements Serde<T>
- Example : Custom JSON serde


### Project Reference

- Project Reference: ../kafka-stream/kafka-ms-sample
  - Classes Added / Modified: 
    - PromotionUppercaseSpringJsonStream.java
    - CustomJsonDeserializer.java
    - CustomJsonSerde.java
    - CustomJsonSerializer.java
    - PromotionSerde.java
    - PromotionUppercaseCustomJsonStream.java



### How To Run

1. Start ../kafka-stream/kafka-ms-order
2. Start ../kafka-stream/kafka-ms-sample
3. Start ../kafka-stream/kafka-ms-storage
4. Go to Postman
   1. Post "Create Promotion"


- Note: Check microservices logs.

## 73. Stream & Table


### Stream & Table

- Stream in Kafka Stream is represented by class KStream.
- It is ordered sequence of messages, similar to kafka topic.
- It is unbounded, means it does not have ends.
- Data will keep flowing to the stream as there is any.
- In stream, all data are Inserts.
- Kafka stream table is represented with KTable class.
- It is also unbounded, but it has different behaviour.
- It upserts data.
- This means it will insert or update a value, depends on the key.
- If the value is null, it will deletes data.
- The analogy is database table, where kafka key is table primary key, and it only has one other column, which is kafka value.


![alt text](https://github.com/marodrigues20/udemy_java/blob/main/JavaSpring%26ApacheKafkaBootcamp-BasicToComplete/Sections/Section-12/pic_20.png?raw=true)


### KStream: Inserts Data

- For example, see this data flow.
- First, came alpha with value 10, so the stream will contains alpha-10.
- Second data is beta with value 61, this data will be inserted into stream, so stream will contains two data like this.
- Third data has existing key : alpha, with value 20.
- Third data will be also inserted to stream.
- The same thing with fourth and fifth data, all data will be inserted to stream.
- So number of data in data flow will be same with stream.
- Each data will be just inserted to stream.


![alt text](https://github.com/marodrigues20/udemy_java/blob/main/JavaSpring%26ApacheKafkaBootcamp-BasicToComplete/Sections/Section-12/pic_21.png?raw=true)


### KTable: Upserts / Delete Data

- Same data flow will produce table like this.
- On first data, this will be inserted to table, as well as second data.
- On third data, it will have existing key, so the content of KTable with key alpha will be updated to new value.
- On fourth data, this will also updates existing key.
- On fifth data, value is null, so it will deletes existing data on KTable with key alpha.

![alt text](https://github.com/marodrigues20/udemy_java/blob/main/JavaSpring%26ApacheKafkaBootcamp-BasicToComplete/Sections/Section-12/pic_22.png?raw=true)


### When To Use KStream / KTable

- Use KStream when we read from topic that is not log-compacted.
- We will see what is log compaction in the next lecture.
- Use KStream if data is partial information, like bank transaction, where we need to know each debit or credit transaction.
- Use KTable if we read from log-compacted topic. And data is self-sufficient, means that data represents most updated value, like total amount of bank balance.


## 74. Log Compaction

- Log compaction is more kafka administrative process rather than spring programming.
- This lecture is just the very basic idea of it.
- Log compaction means Kafka will make that a partition contains at least the latest value of a record.
- It will delete the older versions based on record key on that partition.
- It is useful if we need only the latest data snapshot, instead of whole history.
- We can define log compaction configuration when creating topics.

### For Example


- For example, when we have kafka topic with this data, then after log compaction runs, it will has something like this.
- For each key, at least one record with latest value exists, and older record deleted.
- Notice that I mention “at least”, depends on configuration and kafka storage internals, we can still have something like this, where two latest omega keys exists, but not the earliest.

![alt text](https://github.com/marodrigues20/udemy_java/blob/main/JavaSpring%26ApacheKafkaBootcamp-BasicToComplete/Sections/Section-12/pic_23.png?raw=true)


### Log Compaction

- Keep the order
- Not change offset
- Not duplication validator
- Can fail (for example if there is not enough memory to runs.)
  

### 75. Kafka Stream Operations (Stateless)

### About This Lecture

- Kafka streams operations
- This lecture, then code
- Code, then this lecture

### Diagram

- You will see this kind of diagram.
- The upper arrow is input data stream.
- The box in the middle is operation.
- The lower arrow is output data stream.
- For each data, it will has two rectangle.
- The left rectangle represents key, and right rectangle represents value.

![alt text](https://github.com/marodrigues20/udemy_java/blob/main/JavaSpring%26ApacheKafkaBootcamp-BasicToComplete/Sections/Section-12/pic_24.png?raw=true)


### Intermediate & Terminal Operation

- Later we will see intermediate or terminal operation.
- Intermediate operation is an operation that produces another Kstream or KTable to be processed further.
- Terminal operation is returning void, and cannot be processed further.
- Consider terminal operation as “final” operation.

#### Summary

- Intermediate
  - KStream -> KStream
  - KTable -> KTable
- Terminal
  - KStream -> void
  - KTable -> void
  - "Final" operation


### Reminder : Key & Partition

- Remember that partition and key is related.
- If we have more than one partition, data will go to partition according to key.
- In some diagrams, you will see “Repartitioning”, this means data can change from partition A in input stream to partition X in output stream if key is changed.


### MapValues

![alt text](https://github.com/marodrigues20/udemy_java/blob/main/JavaSpring%26ApacheKafkaBootcamp-BasicToComplete/Sections/Section-12/pic_25.png?raw=true)


### map

![alt text](https://github.com/marodrigues20/udemy_java/blob/main/JavaSpring%26ApacheKafkaBootcamp-BasicToComplete/Sections/Section-12/pic_26.png?raw=true)

### filter

![alt text](https://github.com/marodrigues20/udemy_java/blob/main/JavaSpring%26ApacheKafkaBootcamp-BasicToComplete/Sections/Section-12/pic_27.png?raw=true)

### filterNot

![alt text](https://github.com/marodrigues20/udemy_java/blob/main/JavaSpring%26ApacheKafkaBootcamp-BasicToComplete/Sections/Section-12/pic_28.png?raw=true)