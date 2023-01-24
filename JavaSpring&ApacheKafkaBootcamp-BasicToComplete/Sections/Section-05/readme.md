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

## 18. Topic, Partition & Offset

![alt text](https://github.com/marodrigues20/udemy_java/blob/main/JavaSpring%26ApacheKafkaBootcamp-BasicToComplete/Sections/Section-05/Storage_Room.png?raw=true)

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


### Storage Room Structure in Kafka

![alt text](https://github.com/marodrigues20/udemy_java/blob/main/JavaSpring%26ApacheKafkaBootcamp-BasicToComplete/Sections/Section-05/Storage_Room_In_Kafka.png?raw=true)



- Now let’s talk the storage room structure in Kafka.
- The storage room is Kafka "topic"
- The storage counter is Kafka "partition"
- Each topic can has one or more partition.
- This partition will be used for kafka concurrent processing.
- We will see it on a few moments.
- Each partition will save the incoming messages in same order according the sequence they come.
- We can let Kafka handle partition assigning for us, or we can instruct Kafka which partition
- we want to save this message.
- The ID on previous analogy, is called as "offset" in kafka.
- Offset is maintained for each partition, so current offset value in each partition can be different each other.
  
  ### Topic

  - You can have many topic as you need
  - Messages stored for certain retention period
    - Default is 7 days
    - Able to set value
  - Each topic has name
  - Message is immutable

### Partition & Offset

- 1 topic: 1 or more partition(s)
- Partition is a way to achieve parallelism
- Messages stored in order for each partition (guaranteed per partition)
- Order across partition not guaranteed 
- Offset per partition
  - Partition 0, offset 0|1|2|3|...
  - Partition 2, offset 0|1|2|3|...
  - Partition 3, offset 0|1|2|3|...
- Each partition is independent
- Define partition when creates topic
- Can add partition later
- Can't delete partition later
- Because delete partition = delete data (cause data loss)

### Example

![alt text](https://github.com/marodrigues20/udemy_java/blob/main/JavaSpring%26ApacheKafkaBootcamp-BasicToComplete/Sections/Section-05/Example.png?raw=true)


- For example, suppose we have a system for monitoring commodity price.
- This can be gold, iron, copper, silver, etc.
- So we will have kafka in the middle.
- We will have real-time dashboard for showing price, and a notification service that will send messages when price change exceed a limit.
- We can fetch price list every minute and send it to kafka topics t-commodity-price.
- Every message will contains timestamp, commodity type, and price.
- In this case, we will have three partitions.
- We define the number of partitions during create topic.
- Since we only have several commodities to monitor, currently three partitions is enough to update dashboard and send notification in almost real-time.
- Later on, if we decide to monitor more commodities, like non-metal commodities: wood, coffee, cotton, et cetera, we might add another partition, thus we can add another consumer.
- One consumer will works on one partition.
- Actually, one consumer per functionality, or called as "consumer group", as we will see later.
- Since we have three partition and two functionalities, we can use up until three consumers for each functionality.
- So we can have six consumers: three consumer for updating dashboard, and three consumer for notification.
- This is a very high level example of kafka usage.
