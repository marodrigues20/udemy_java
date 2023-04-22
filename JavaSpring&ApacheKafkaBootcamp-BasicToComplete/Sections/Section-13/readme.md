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
    - OrderPatternMessage.java
    - OrderRewardMessage.java
    - CommodityStreamUtil.java
    - CommodityOneStream.java


### How to Run

1. Open 3 different Command prompt to consume each topic and display for you see the messages in each topic.
2. Type in each Command prompt:
3. $ kafka-console-consumer.sh --bootstrap-server localhost:9092 --property print.key=true --topic -t-commodity-pattern-one
4. $ kafka-console-consumer.sh --bootstrap-server localhost:9092 --property print.key=true --topic -t-commodity-reward-one
5. $ kafka-console-consumer.sh --bootstrap-server localhost:9092 --property print.key=true --topic -t-commodity-storage-one
6. Run ../kafka-stream/kafka-ms-sample
7. Run ../kafka-stream/kafka-ms-order
8. Open Postman
9. Click on "Commodity Order" and click on "Run" button.
10. Select 3 entry end points: "Order 1 Random Item"; "Order 2 Random Items"; Order 3 Random Items"
11. Fill in Iterations field: 1000
12. Fill in Delay field: 1000 ms
13. Click on "Run Course - Spring Kafka 4"
14. You can see in 3 Command Prompt all the messages being consumed and displayed.



## 79. Additional Requirement

### Commodity Stream - Additional Requirements

- In this lecture, we will learn that stream can has multiple processes on topology.
- After some times, we have additional requirements for commodity stream.
- We need to split pattern stream into plastic items and non plastic items, so if we have 10 items, 3 of which are plastic material, we have to split the stream.
- 3 plastic items go into one stream, and the other 7 go into different stream.
- On reward stream, management only interested giving reward on item with certain price, the business call it “not cheap”.
- On storage, it happens that the nosql database needs base64 encoded key, but we cannot change the data structure. In this case, we will change the data key into base64 of order number, but leave the value as-is.


![alt text](https://github.com/marodrigues20/udemy_java/blob/main/JavaSpring%26ApacheKafkaBootcamp-BasicToComplete/Sections/Section-13/pic_06.png?raw=true)


### Topology Detail

- So we will have this additional topology in detail. In pattern, we will split -or in other word-branch the stream into plastic and non plastic.
-In reward, we add additional filter for price, then remove the credit card attribute.
- In storage, we will change the key to base64.
- Since we will add requirement, copy paste CommodityOneStream and rename it to CommodityTwoStream.

![alt text](https://github.com/marodrigues20/udemy_java/blob/main/JavaSpring%26ApacheKafkaBootcamp-BasicToComplete/Sections/Section-13/pic_07.png?raw=true)


### Project Reference

- Project Reference: ../kafka-stream/kafka-ms-sample
  - Classes Added / Modified: 
    - CommodityOneStream2.java
    - CommodityStreamUtil.java


### How To Run

- For trying, I provide postman collection. In there, find endpoint to create plastic and non plastic order. If you examine the body, you will find certain items, price, and quantity. With these sample data, you will get the sink stream and data like this.
- For trying, open postman collection. Execute endpoint to create plastic and non plastic order.
- You can watch the sink topic and find out sample data.


![alt text](https://github.com/marodrigues20/udemy_java/blob/main/JavaSpring%26ApacheKafkaBootcamp-BasicToComplete/Sections/Section-13/pic_08.png?raw=true)


1. Run ../kafka-stream/kafka-ms-sample
2. Run ../kafka-stream/kafka-ms-order
3. Open PostMan "Course - Spring Kafka 4"
4. Select "Create Plastic & Non Plastic Order"
5. Post a Request.
6. Open 4 Command Prompts. One for each command below:
  7. $ kafka-console-consumer.sh --bootstrap-server localhost:9092 --property print.key=true --topic -t-commodity-pattern-two-plastic
  8. $ kafka-console-consumer.sh --bootstrap-server localhost:9092 --property print.key=true --topic -t-commodity-pattern-two-notplastic
  8. $ kafka-console-consumer.sh --bootstrap-server localhost:9092 --property print.key=true --topic -t-commodity-reward-two
  9. $ kafka-console-consumer.sh --bootstrap-server localhost:9092 --property print.key=true --topic -t-commodity-storage-two
10. You can watch the sink topic and find out sample data.


## 80. Branching Alternatives

### Commodity Streams - Branching Alternatives

- Spring Kafka provides alternative syntax for branching.
- instead of generating array of Kstream, Spring kafka uses lambda expression.
- Copy paste CommodityTwoStream and rename it to CommodityThreeStream.

### Project Reference

- Project Reference: ../kafka-stream/kafka-ms-sample
  - Classes Added / Modified: 
    - CommodityThreeStream.java


### How To Run

1. Run ../kafka-stream/kafka-ms-sample
2. Run ../kafka-stream/kafka-ms-order
3. Open PostMan "Course - Spring Kafka 4"
4. Select "Create Plastic & Non Plastic Order"
5. Post a Request.
6. Open 4 Command Prompts. One for each command below:
  7. $ kafka-console-consumer.sh --bootstrap-server localhost:9092 --property print.key=true --topic -t-commodity-pattern-three-plastic
  8. $ kafka-console-consumer.sh --bootstrap-server localhost:9092 --property print.key=true --topic -t-commodity-pattern-three-notplastic
  8. $ kafka-console-consumer.sh --bootstrap-server localhost:9092 --property print.key=true --topic -t-commodity-reward-three
  9. $ kafka-console-consumer.sh --bootstrap-server localhost:9092 --property print.key=true --topic -t-commodity-storage-three
10. You can watch the sink topic and find out sample data.



## 81. Newer Branch Syntax

- Project Reference: ../kafka-stream/kafka-ms-sample
  - Classes Added / Modified: 
    - CommodityTwo_v2_Stream.java


### How To Run

1. Run ../kafka-stream/kafka-ms-sample
2. Run ../kafka-stream/kafka-ms-order
3. Open PostMan "Course - Spring Kafka 4"
4. Select "Create Plastic & Non Plastic Order"
5. Post a Request.
6. Open 4 Command Prompts. One for each command below:
  7. $ kafka-console-consumer.sh --bootstrap-server localhost:9092 --property print.key=true --topic -t-commodity-pattern-three-plastic
  8. $ kafka-console-consumer.sh --bootstrap-server localhost:9092 --property print.key=true --topic -t-commodity-pattern-three-notplastic
  8. $ kafka-console-consumer.sh --bootstrap-server localhost:9092 --property print.key=true --topic -t-commodity-reward-three
  9. $ kafka-console-consumer.sh --bootstrap-server localhost:9092 --property print.key=true --topic -t-commodity-storage-three
10. You can watch the sink topic and find out sample data.


## 82. Reward Each Location

### Commodity Stream - Reward Each Location

- Currently we have reward message with key is order number.
- This is because order number is original key from order input stream.
- In previous stream, we use mapValues which only change the value, not the key.


![alt text](https://github.com/marodrigues20/udemy_java/blob/main/JavaSpring%26ApacheKafkaBootcamp-BasicToComplete/Sections/Section-13/pic_09.png?raw=true)


- For reward, the key should be based on location, so this kind of structure is better.
- To transform the value and the key, we can use map method instead of mapValues.


![alt text](https://github.com/marodrigues20/udemy_java/blob/main/JavaSpring%26ApacheKafkaBootcamp-BasicToComplete/Sections/Section-13/pic_10.png?raw=true)


### Project Reference

- Project Reference: ../kafka-stream/kafka-ms-sample
  - Classes Added / Modified:
    - CommodityFourStream.java
    - CommodityStreamUtil.java


### How To Run

1. Run ../kafka-stream/kafka-ms-sample
2. Run ../kafka-stream/kafka-ms-order
3. Open PostMan "Course - Spring Kafka 4"
4. Select "Order 1 Random Item"
5. Post a Request.
6. Open 4 Command Prompts. One for each command below:
  7. $ kafka-console-consumer.sh --bootstrap-server localhost:9092 --property print.key=true --topic -t-commodity-pattern-four-plastic
  8. $ kafka-console-consumer.sh --bootstrap-server localhost:9092 --property print.key=true --topic -t-commodity-pattern-four-notplastic
  8. $ kafka-console-consumer.sh --bootstrap-server localhost:9092 --property print.key=true --topic -t-commodity-reward-four
  9. $ kafka-console-consumer.sh --bootstrap-server localhost:9092 --property print.key=true --topic -t-commodity-storage-four
10. You can watch the sink topic and find out sample data.



## 83. Calling API or Other Process

### Commodity Stream - Calling API or Other Process

