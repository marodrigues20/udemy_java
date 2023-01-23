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

