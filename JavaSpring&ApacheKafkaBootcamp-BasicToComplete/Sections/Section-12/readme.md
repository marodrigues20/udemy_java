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
- Previously we have three consumer microservice built with spring kafka.

![alt text](https://github.com/marodrigues20/udemy_java/blob/main/JavaSpring%26ApacheKafkaBootcamp-BasicToComplete/Sections/Section-12/pic_02.png?raw=true)