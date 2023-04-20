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