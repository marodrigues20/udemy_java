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