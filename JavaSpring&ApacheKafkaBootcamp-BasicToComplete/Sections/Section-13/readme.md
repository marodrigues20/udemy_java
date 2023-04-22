# Section 13: Kafka Stream - Commodity

## 76. Kafka Stream Topology

- This is the high level topology that we will build for commodity application.
- We will learn step by step how to create this topology.
- The source processor will take data stream from this topic.
- The credit card number is confidential, so we will create one processor that mask credit card number for each order.
- At some cases, this processor can send masked credit card number to this topic.
- But this processor is not sink processor.
- We have requirement for pattern stream, there will be some cases we will use later.
- Let's call this as pattern processor.
- This is the first sink processor, will send pattern data into this topic.
- Our second sink processor will be reward processor, send transformed data to this topic.
- Our third sink processor will be storage processor, send data to this topic.
- Please note that topic name on detail implementation might not be exact match, but will be similar with this diagram.


![alt text](https://github.com/marodrigues20/udemy_java/blob/main/JavaSpring%26ApacheKafkaBootcamp-BasicToComplete/Sections/Section-13/pic_01.png?raw=true)


- Remember that each data in kafka consists of key and value.
- Original data from order topic has order number as key.
- So in its child processor, the same key will also has same value, unless we change the key, as you will see later.


![alt text](https://github.com/marodrigues20/udemy_java/blob/main/JavaSpring%26ApacheKafkaBootcamp-BasicToComplete/Sections/Section-13/pic_02.png?raw=true)


- If we want to see the key, we can run kafka-console-consumer and give this parameter.

![alt text](https://github.com/marodrigues20/udemy_java/blob/main/JavaSpring%26ApacheKafkaBootcamp-BasicToComplete/Sections/Section-13/pic_03.png?raw=true)


## 77. First Step - Commodity Stream

### Project Reference

- Project Reference: ../kafka-stream/kafka-ms-sample
  - Classes Added / Modified: 
    - MaskOrderStream.java
    - CommodityStreamUtil.java
    - OrderMessage.java
    - JsonConfig.java


### How to Run

1. Run ../kafka-stream/kafka-ms-sample
2. Open PostMan
   1. Send a post request using "Order 2 Random items" for 2 items.
3. Open a prompt and execute:
   1. $ kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic t-commodity-order-masked


## 78. Sink Processors

- In this lecture, we will create the sink processors, with this use case:
- Pattern processor will summarize item, it will remove item and quantity attribute, summarize them as new attribute : total item amount.
- Reward processor will only process data with large quantity, which is quantity above certain number then remove credit card number. Storage processor has no change,
  just sending order to sink stream.


![alt text](https://github.com/marodrigues20/udemy_java/blob/main/JavaSpring%26ApacheKafkaBootcamp-BasicToComplete/Sections/Section-13/pic_04.png?raw=true)


### Topology Detail

- We will have this topology in detail. This is the source processor.
- We then mask the credit card. For pattern, we will have processor to summarize total item value. For reward, we will filter large quantity only,
then remove credit card number.
- For storage no data transformation, send directly to sink stream.


![alt text](https://github.com/marodrigues20/udemy_java/blob/main/JavaSpring%26ApacheKafkaBootcamp-BasicToComplete/Sections/Section-13/pic_05.png?raw=true)


### Project Reference

- Project Reference: ../kafka-stream/kafka-ms-sample
  - Classes Added / Modified: 
    - 