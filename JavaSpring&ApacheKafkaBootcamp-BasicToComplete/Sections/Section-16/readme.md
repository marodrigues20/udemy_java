# Section 16. Kafka Stream - Flash Sale Vote

## 94. Most Recent Data Feed

- For marketing program, we will give customer list of item as flash sale candidate.
- Customer can vote from this list candidate during certain time range. The next flash sale will be held based on most voted item. One customer can only select one candidate, but they can change selection as long as still in time range.
- Since one customer can only select one flash sale candidate, but can change her choice, means we have to track latest selected item. This is a good candidate for Kafka Stream Table.

![alt text](https://github.com/marodrigues20/udemy_java/blob/main/JavaSpring%26ApacheKafkaBootcamp-BasicToComplete/Sections/Section-16/pic_01.png?raw=true)

- So we will publish record to kafka, using customer ID as key, and flash sale candidate as value. On first choice, we will insert the record. If customer change his mind, he can submit again.
- This means we will update the data on kafka table. In other words, we will do upsert.

## Stream or Table?

- Track latest selected item per customer
- Kafka Stream table
- Record
  - Key: Customer ID
  - Value: Flash sale candidate


- We will not create user interface, just API and producer. The flash sale vote will have attributes:
  - customerId and item name. It will publish to t-commodity-flashsale-vote.

## Source Code for Flash Sale Vote

Note: Download the code in the last section of the code from kafka-ms-order and copy the classes to your project. Don't forget to copy the message class to kafka-order and kafka-stream.

- FlashSaleVote*.java
- Package com.course.kafka
  - api.request
  - api.server
  - broker.message
  - broker.producer
  - command.action
  - command.service


## High Level Topology

- On sink processor, we will group the data by item name. We will see later when customer update his choice, it will affect candidate count.


![alt text](https://github.com/marodrigues20/udemy_java/blob/main/JavaSpring%26ApacheKafkaBootcamp-BasicToComplete/Sections/Section-16/pic_02.png?raw=true)


## Processing The Message

![alt text](https://github.com/marodrigues20/udemy_java/blob/main/JavaSpring%26ApacheKafkaBootcamp-BasicToComplete/Sections/Section-16/pic_03.png?raw=true)

- For this requirement, we only need to focus on two attributes on flash sale vote:
  - customer ID, which is the key, and item name. Since one customer can update his vote for more than once, we need to convert this original message into this format,
    where the key is customer ID and the value is item name. So every data will be treated as upsert to Kafka Stream Table.


## How Vote Works?


![alt text](https://github.com/marodrigues20/udemy_java/blob/main/JavaSpring%26ApacheKafkaBootcamp-BasicToComplete/Sections/Section-16/pic_04.png?raw=true)

- if we have X voter, the total vote count must be X, but the number can be distributed according to choice, which can be updated. For example, if the flash sale item is “cookies” and “cake”. The left side is the message on kafka topic, and the right side is the vote result.
- Anna choose cookies. So we have one cookies vote. Then, Olaf also choose cookies, so we have two cookies vote. But then Olaf change his mind and choose cake.
- This cake choice is followed by Anna, so we have another update.
- Then Elsa votes cookies, this means an insert since Elsa is new voter.
- So this is how upserts works.
  

### Project Reference - Using Table

```
@Bean
    public KStream<String, String> flashSaleVote(StreamsBuilder builder){

        var stringSerde = Serdes.String();
        var flashSaleVoteSerde = new JsonSerde<>(FlashSaleVoteMessage.class);

        //We need to convert the json value into new key-value pair, with user id as key and item name as value. Use map for this.
        var flashSaleVoteStream = builder.stream("t-commodity-flashsale-vote",
                Consumed.with(stringSerde, flashSaleVoteSerde))
                .map((key, value) -> KeyValue.pair(value.getCustomerId(), value.getItemName()));

        //Now that we have stream with user id and item name,
        // Now that we have stream with user id and item name, stream this into intermediary topic,
        // so we can build a table from it.
        flashSaleVoteStream.to("t-commodity-flashsale-vote-user-item");


        // At this point, we have table, now we need to group the table.
        // Group by is grouping by key, so we must convert the key, which is currently user id on intermediary stream, to itemName.
        // Now we can count the group elements.
        builder.table("t-commodity-flashsale-vote-user-item", Consumed.with(stringSerde, stringSerde)) //builder.table -> Create a Ktable
                .groupBy((user, votedItem) -> KeyValue.pair(votedItem, votedItem)).count()
                .toStream().to("t-commodity-flashsale-vote-one-result");

        return flashSaleVoteStream;
    }
```

- Project Reference: ../kafka-stream/kafka-stream-sample
  - Classes Added / Modified: 
    - FlashSaleVoteOneStream.java
    - FlashSaleVoteMessage.java


- Project Reference: ../kafka-stream/kafka-stream-order
  - Classes Added / Modified: 
      - FlashSaleVoteRequest.java
      - FlashSaleVoteMessage.java
      - FlashSaleVoteProducer.java
      - FlashSaleVoteAction.java
      - FlashSaleVoteService.java


### How to Run

1. Start kafka-console consumer. This is the source topic, intermediary topic, and vote result.
  - Open Command Prompt:
   1. $ kafka-console-consumer --bootstrap-server broker:9092 --property print.key=true --property --topic t-commodity-flashsale-vote
   2. $ kafka-console-consumer --bootstrap-server broker:9092 --property print.key=true --topic t-commodity-flashsale-vote-user-item
   3. $ kafka-console-consumer --bootstrap-server broker:9092 --property print.key-true --property value.deserializer=org.apache.kafka.common.serialization.LongDeserializer --topic t-commodity-flashsale-vote-one-result
2. Open Postman:
3. Click on "Flash Sale"
4. Click on "Simulation"
5. Select "01 - Anna (Cookies)" 
6. Click on "Send" button
7. Select "02 - Olaf (Cookies)"
8. Click on "Send" button
9. Select "03 - Olaf (Cake)"
10. Click on "Send" button
11. Select "04 - Anna (Cake)"
12. Click on "Send" button
13. Select "05 - Elsa (Cookies)"
14. Click on "Send" button

Note: Keep cheking in each console-consumer to see the messages and compare with timeline explained above.

- You can also select "Flash Sale" folder to run vote for many times.

1. Select "Flash Sale" folder.
2. Select on left panel "Create Random Flash Sale Vote".
3. Fill in "Interation" Field with 10000.
4. Fill in "Delay" Field with 200ms delay.
5. Click on "Run Course - Spring Kafka 4" button.


## 95. Stream & State

- When we learn about commodity stream, and feedback stream, we care about the current data.
- This means each data does not need to know what are previous data about, they are independent.
- This is different in flash sale vote, where we need to know what user currently choose, or previously choose.
- Generally speaking, user choice in the example is a record “state” in kafka stream.
- Each record can have a state, and kafka stream processor will look at the state.
- In commodity and feedback example, we don’t care about the state, so we use stateless kafka stream operations when we work with those examples.
- On the flash sale, we use count() which is one of stateful operation.


### Flash Sale Stream - Stream & State

  - Care about current data
  - No need to know previous data
  - Flash sale example: need to know current / previous choice
  - "User choice" is kafka stream state
  - Stateless operations (commodity & feedback)
  - Stateful operations (Flash sale vote)


  ### State Store 

- State is ability to see information that exists before, and connect it to current data.
- This information need to be placed somewhere, which is state store.
- State store is key-value data storage that can be accessed from processor.
- Kafka streams provides in memory and persistent state stores that we can use.


- See existing information & connect it
- Kept in state store
- Key-value data storage
- Accessed from processor
- Kafka stream state stores:
  - In-memory
  - Persistent (disk based)


  ![alt text](https://github.com/marodrigues20/udemy_java/blob/main/JavaSpring%26ApacheKafkaBootcamp-BasicToComplete/Sections/Section-16/pic_05.png?raw=true)


