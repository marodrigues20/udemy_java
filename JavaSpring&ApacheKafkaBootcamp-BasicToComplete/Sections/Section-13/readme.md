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


