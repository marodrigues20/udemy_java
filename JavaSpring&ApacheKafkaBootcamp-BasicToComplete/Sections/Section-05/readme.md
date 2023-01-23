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

![alt text](https://github.com/marodrigues20/udemy_java/blob/main/JavaSpring%26ApacheKafkaBootcamp-BasicToComplete/Sections/Section-05/Kafka_Terminology.png?raw=true)

### Synonyms

- Producer / Publisher
- Send / publish / producer message
- Subscriber / consumer / listener
- Subscribe / consume / listen
- Don't get confused with terminology synonyms


## 17. Kafka Analogy

### Topic, Partition & Offset

- The storage room in kafka analogy, consists of several parts:
- 1st, the storage room itself.
- 2nd, the storage counter where message comes.
- Each storage room can has one or more counters.
- Each counter has its own storage line to put the messages that comes.
- Messages are put in the same order according the sequence they come.
- Each message will have message order ID.
- This order ID is attached to each counter and always increment.
- So when three messages comes to counter 0, it will be something like this:
- 1st message, get ID 0
- 2nd message, get ID 1
- 3rd message, get ID 2
- And it keeps go on.
- The ID keeps incrementing and unique for each message in counter 0.
- So if we have 10 messages on counter 0, there will be 10 ID on counter 0.
- The same way goes with counter 1.
- It will store the messages and give ID start on 0, 1, 2 , 3, etc.
- And same thing on counter 2.
- Keep in mind that ID is unique for each counter, so message with ID 0 in counter 0 is different with ID 0 in counter 1 and 2.
- So the message in each counter is independent for each counter.
- Now let’s talk the storage room structure in Kafka.
- The storage room is Kafka "topic"
- The storage counter is Kafka "partition"
- Each topic can has one or more partition.
- This partition will be used for kafka concurrent processing.


![alt text](https://github.com/marodrigues20/udemy_java/blob/main/JavaSpring%26ApacheKafkaBootcamp-BasicToComplete/Sections/Section-05/Storage_Room.png?raw=true)

