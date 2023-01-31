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

## 19. Producer

### Kafka Producer

- Producer send message to Kafka
- Sends to which topic + message content
- Kafka will automatically select partition (you can override this behaviour)
  
### Kafka - How Producer Workds


![alt text](https://github.com/marodrigues20/udemy_java/blob/main/JavaSpring%26ApacheKafkaBootcamp-BasicToComplete/Sections/Section-05/Kafka_Producer_1.png?raw=true)

- Basically this is how producer works if we use example from commodity monitoring system.
- We have a topic t-commodity-price with three partitions.
- A commodity fetcher software continuously fetch a commodity price from -let’s say-world trade center.
- When price fetched, we code kafka producer to send a message to kafka topic.
- Since we have three partitions, kafka will automatically select partition for us.
- Let’s just say kafka using round robin algorithm.
- So if a kafka publisher start fetching and publish price every minute, on first publish, it will publish to a partition.
- So it might start on partition 0, 1, or 2, we cannot know for sure.
- Let’s just say it will publish to partition 0.
- So first publish go to partition 0 offset 0
- On second publish, partition 1 offset 0.
- On 3rd publish, partition 2 offset 0.
- On 4th publish, back to partition 0, with incremental offset to 1.
- On 5th publish, partition 1 offset 1.
- Et cetera et cetera.


![alt text](https://github.com/marodrigues20/udemy_java/blob/main/JavaSpring%26ApacheKafkaBootcamp-BasicToComplete/Sections/Section-05/Kafka_Producer_2.png?raw=true)



- Using Spring, we has ability to select which partition we want to send message.
- The first way is straightforward, we tell publisher which partition we want to send.
- For example, we can tell publisher to write certain messages to partition 2.
- However, I recommend to send to multiple partitions if your topic has more than one partition.
- The reason for this is related to consuming process as we will see later.



![alt text](https://github.com/marodrigues20/udemy_java/blob/main/JavaSpring%26ApacheKafkaBootcamp-BasicToComplete/Sections/Section-05/Kafka_Producer_3.png?raw=true)


- The second way is by using key on message.
- A key can be anything, a string, a number.
- It’s up to you.
- A key is basically sent if we need message ordering for a specific criteria.
- For example, we need to keep message ordering based on commodity type -gold, copper, iron, etc.
- So we use commodity type as key.
- Kafka guarantee that all messages with same key will always go to same partition, as long as we do not add partition.
- So kafka might sent all messages with key "gold" and "copper" to partition 1, while all "iron" goes to partition 0.
- Technically, kafka will hash the key and send the message based on the hash value.


![alt text](https://github.com/marodrigues20/udemy_java/blob/main/JavaSpring%26ApacheKafkaBootcamp-BasicToComplete/Sections/Section-05/Kafka_Producer_4.png?raw=true)

- An important aspect is that the key can go to different partition if later on we decide to add partition.
- In kafka, we have to define partition number during topic creation.
- So if we decide to use 3 partitions, all messages with "gold" key might always go to partition 1.
- But later on when we add another 2 partitions, the next message with "gold" key might go to partition 3, we don’t know for sure.
- So this is the basic of producer in Kafka.
- Next, we will see the other side of coin: the Kafka consumer.


## 20. Consumer & Consumer Group

### Storage Room - Processing Message

- We have messages in storage room . 
- Each message needs to be processed, otherwise it will be no use.
- Using warehouse analogy, we can have warehouse worker that take and process messages.

![alt text](https://github.com/marodrigues20/udemy_java/blob/main/JavaSpring%26ApacheKafkaBootcamp-BasicToComplete/Sections/Section-05/Processing_Messages_1.png?raw=true)


### Kafka Terminology

- In kafka terminology, the warehouse worker is called as subscriber, consumer, or listener.
- Consumer is guaranteed to read data in order for each partition.

![alt text](https://github.com/marodrigues20/udemy_java/blob/main/JavaSpring%26ApacheKafkaBootcamp-BasicToComplete/Sections/Section-05/Processing_Messages_2.png?raw=true)

### Kafka Consumer

- Consumer is guaranteed to read data in order for each partition.
- Order is incrementing by offset (low offset to high), cannot reverse.
- Each partition maximum one consumer per consumer group.
- One consumer can read from more than one partition.

### Single Consumer for All Partitions

![alt text](https://github.com/marodrigues20/udemy_java/blob/main/JavaSpring%26ApacheKafkaBootcamp-BasicToComplete/Sections/Section-05/Single_Consumer_1.png?raw=true)

- For example, if we have one topic with 3 partitions.
- First case, if we have only single consumer.
- In this case, consumer will process messages from all partition.
- Kafka will arrange this thing for us, making sure that all messages is consumed by this single consumer.


### Consumer < Partition

![alt text](https://github.com/marodrigues20/udemy_java/blob/main/JavaSpring%26ApacheKafkaBootcamp-BasicToComplete/Sections/Section-05/Consumer_Less_Partitions.png?raw=true)

- We can add more consumer to speed up process
- So, we can have two consumers.
- In this case, number of consumer is less than number of partitions.
- Now, one consumer will read messages from one partition, and the other will read from two partitions.
- It can be like this, or like this.
- Or other possible combination.
- Kafka default configuration will handle message distribution for us.
- We cannot know for sure, which consumer assigned for two partitions.

### Consumer = Partition

![alt text](https://github.com/marodrigues20/udemy_java/blob/main/JavaSpring%26ApacheKafkaBootcamp-BasicToComplete/Sections/Section-05/Consumer_Equal_Partition.png?raw=true)

- In case consumer number is equals to partition number, it will be one-to-one mapping.
- Again, kafka default configuration will assign consumer to partition, we don’t need to worry about that task.

### Consumer > Partition

![alt text](https://github.com/marodrigues20/udemy_java/blob/main/JavaSpring%26ApacheKafkaBootcamp-BasicToComplete/Sections/Section-05/Consumer_Greater_Partition.png?raw=true)

- Now, what if we have more consumer than partition?
- Well, kafka will only allow maximum one consumer per partition per consumer group.
- So if we have four consumers and three partitions, one consumer will be idle.
- This is not always a bad idea, depends on the case.
- For example, if each of the consumer runs from different machine, when consumer 3 is suddenly down, 
  kafka will automatically re-assign idle consumer to replace consumer 3.


### Kafka Consumer Group

- Group consumers based on functionalities
- Partition & consumer group for parallel consume

### Kafka Consumer Group - Commodity example.

![alt text](https://github.com/marodrigues20/udemy_java/blob/main/JavaSpring%26ApacheKafkaBootcamp-BasicToComplete/Sections/Section-05/Consumer_Group_1.png?raw=true)

- Let’s re-look the commodity example.
- In here we have two functionalities : update dashboard and send notification.
- Assume we have only one partition in topic, and several commodity messages in that partition.
- In this example, we will have two consumers.


![alt text](https://github.com/marodrigues20/udemy_java/blob/main/JavaSpring%26ApacheKafkaBootcamp-BasicToComplete/Sections/Section-05/Consumer_Group_2.png?raw=true)

- Both will process same message, from same partition.
- However, each consumer belongs to different consumer group.
- First group is for update dashboard, while second group is for notification There can be more than one consumer accessing same partition, but only one consumer per consumer group Each consumer group is independent each other.
- So even if dashboard consumer takes more time to process each message or vice versa, it does not matter.
- The dashboard process will not block notification process, or the other way around The consuming process can become like this.
- Remember that consuming happens in order, so from offset 0 to 1, etc.
- Consumer group is independent each other.
- For example, if we have one topic with 3 partitions.

![alt text](https://github.com/marodrigues20/udemy_java/blob/main/JavaSpring%26ApacheKafkaBootcamp-BasicToComplete/Sections/Section-05/Consumer_Group_Independent.png?raw=true)

- We have two functionalities : update dashboard and send notification, so two consumer groups.
- As a start, we have one consumer for dashboard update, and one consumer for notification.
- In this state, each consumer will consumes from three partitions.
- Each consumer group is independent, so D1 process will not interfere N1 process, and vice versa.
- Later on, we realize that dashboard data update is slow, so we can add another dashboard consumer, but we don’t have to add notification consumer.
- So we can have this kind of condition.


## 21. Consumer Offset & Delivery Semantic

### Consumer Offset

- Remember that every message in kafka has offset.
- It is like an ID that indicates message position in partition, and it always in ordered sequence.
- When consumer read message, it will remember the offset for that message.
- This remembered offset commonly referred as consumer offset.
- It is unique per partition Kafka will save these consumer offset on it’s internal topic.
- We can use this auto-save feature, or we can save the consumer offset programmatically.
- It is like a checkpoint that indicates, where is the last time a consumer read a partition.
- So when consumer dies and restart, it will be able to know where is the last time it read message, and continue from that offset.



### Kafka Consumer Offset

![alt text](https://github.com/marodrigues20/udemy_java/blob/main/JavaSpring%26ApacheKafkaBootcamp-BasicToComplete/Sections/Section-05/Kafka_ConsumerOffSet_1.png?raw=true)

- When we have two consumer group reading same partition, let’s just say dashboard consumer already read up to offset 247, and notification consumer read up to offset 245. Kafka will store this offset.
  
![alt text](https://github.com/marodrigues20/udemy_java/blob/main/JavaSpring%26ApacheKafkaBootcamp-BasicToComplete/Sections/Section-05/Kafka_ConsumerOffSet_2.png?raw=true)

- When dashboard consumer is suddenly down, it is not a problem.
- Remember that consumer group is independent each other.
- Notification consumer will continue to read to offset 246, 247, etc.
- When the dashboard consumer comes up few hour later, it knows that the last offset it read is 247.
- So it will continue to read from offset 248 forward.

### Delivery Semantic

- Consumer can choose whether offset saving is happen automatically or manual.
- This is called delivery semantic.
- There are three kind of delivery semantics.
- The first is at-most-once, where offset is committed immediately after read, so only zero or once delivery happened.
- In this semantic, if something goes wrong during processing, the message will not be re-processed.

- The second is at-least-once, where offset commit happens after message processed.
- When something goes wrong during process, the offset should not committed, so consumer will re-process the message.
- In this semantic, there is a possibility that message is re-processed, so we must design an idempotent consumer.
- Idempotent means re-processing the message that already processed will not have impact on your system.
- For example, it will not create duplicate transaction.


- The last is exactly-once semantic, where message is processed only once. However, implementing consumer for this semantic is hard.

#### Summary

- Consumer choose when to saving (commit) offset
- At-most-once
  - Zero (unprocessed) or once (processed)
  - Possible message loss (on error process)
- At-least-once
  - One or more
  - Something wrong, able to re-process
  - Create idempotent consumer
- Exactly-once
  - Once only
  - Hard to implement


## 22. Zookeeper

- Kafka designed for highly available
- Kafka cluster: group of kafka brokers (servers)
- Zookeeper manage those brokers
  - Add broker
  - Broker dies
  - Synchronize data
- This course will not focus on zookeeper
- Zookeeper server must runs for kafka to start (old Kafka)
- No need zookeepers to start (version 2.8+)
