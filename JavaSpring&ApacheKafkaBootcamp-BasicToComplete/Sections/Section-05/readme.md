# Section 5: Kafka Basic Concepts

## 16. Kafka Basic Concepts

- In This Section
  - Kafka Theory
  - Recommend: follow all lectures in this section to avoid confusion

## 17. Kafka Analogy

- Kafka Analogy (A very simplified Explanation)
  
![alt text](https://github.com/marodrigues20/udemy_java/blob/main/JavaSpring%26ApacheKafkaBootcamp-BasicToComplete/Sections/Section-05/Unique_Warehouse.png?raw=true)

- In a way, we can say that Kafka is like a "unique warehouse" that stores messages.
- Person A come to this warehouse, and publish a message.
- Instead of sending the message to receiver, this "unique warehouse" store the message into specific "storage room" based on publisher instruction.
- Person X is interested to specific room, so Person X subscribe himself for particular "storage room".
- Whenever a new message comes, person X will knows, so he can take the message and process it.

### Kafka Terminology

- Person A is producer or publisher, application that send message to kafka Message is any kind of information.
-  It could, for example, an information about a process or task that should start on another application (that could be on another server), or it could be just a simple text message.
- "Storage room" represents topic, partition, and offset in kafka.
- We will learn more about this soon.
- Person X is subscriber, or sometime called as consumer, or listener, application that interested on specific room and consistently monitor it for new message.
- It will get the message from storage room, using specific mechanism that we will discuss later.

![alt text](https://github.com/marodrigues20/udemy_java/blob/main/JavaSpring%26ApacheKafkaBootcamp-BasicToComplete/Sections/Section-05/Kafka_Terminology.png)

### Synonyms

- Producer / Publisher
- Send / publish / producer message
- Subscriber / consumer / listener
- Subscribe / consume / listen
- Don't get confused with terminology synonyms