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
   0. $ docker-compose exec broker bash 

   1. $ kafka-console-consumer --topic t-commodity-flashsale-vote --bootstrap-server broker:9092 --property print.key=true

   2. $ kafka-console-consumer --topic t-commodity-flashsale-vote-user-item --bootstrap-server broker:9092 --property parse.key=true


   3. $ kafka-console-consumer --bootstrap-server broker:9092 --property print.key=true --property value.deserializer=org.apache.kafka.common.serialization.LongDeserializer --topic t-commodity-flashsale-vote-one-result --property key.separator":"

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


1. Producer produces original data, which processed by kafka stream.
2. During processing, processor communicates with state store to retrieve existing state, and use it to process the original data.


### Important Aspects

- State stores fulfill two important aspects:
- Data locality, which means state store is available on same machine as processing node.
- This means no network communication overhead and give significant performance.
- Data locality also means that there's no sharing state store across processes or threads in processor.
- Second aspect is fault tolerance.
- This means if kafka stream applications somehow crash, we can recover state store quickly.
- To do this, state store use kafka changelog topic for backup and recover the states.

- Data locality
  - Same machine with processing node
  - No network overhead
  - No sharing store
- Fault tolerance
  - Recover quickly in case application failure
  - Use changelog topic

  ## 96. Kafka Stream Stateful Operations

  ### About this Lecture

    - This lecture will has references to kafka streams stateful operations, some of them we will use in code.
    - You can follow this lecture and learn the code later.
    - Or you can skip this lecture, directly go to code, and when you find some operation regarding kafka stream, you can go back to this lecture and see the reference.
    - You can combine this lecture with previous kafka stream operations lecture, which talks about stateless operations.

    - This diagram shows relationships among kafka streams type and stateful operations.
    - The black nodes are kafka stream data type.
    - Refer to this diagram to know which kafka stream type owns certain operation.
    - The green text are stateless operations, and the red text are stateful operations.


  ![alt text](https://github.com/marodrigues20/udemy_java/blob/main/JavaSpring%26ApacheKafkaBootcamp-BasicToComplete/Sections/Section-16/pic_06.png?raw=true)


    - This will counts the number of records based on key.
    - Records with null keys or values are ignored.

  ![alt text](https://github.com/marodrigues20/udemy_java/blob/main/JavaSpring%26ApacheKafkaBootcamp-BasicToComplete/Sections/Section-16/pic_07.png?raw=true)


    - Aggregate will aggregate record value based on key.
    - To aggregate stream, we need an initializer, which is initial value of aggregation, and adder, which is aggregator function to be called when new record comes.
    - Result of aggregation can be different type with input type.
    - In example above, the input type is a string, and the aggregation result is long.
    - The aggregation result from sample above is sum of kafka value string length.
    - Input with null keys are ignored.
    - When a record key is received for the first time, the initializer is called, and then the adder is called.
    - Whenever a record with a non-null value is received, the adder is called.

    ![alt text](https://github.com/marodrigues20/udemy_java/blob/main/JavaSpring%26ApacheKafkaBootcamp-BasicToComplete/Sections/Section-16/pic_08.png?raw=true)


    - Reduce is a specialized form of aggregate.
    - It is shorter syntax for aggregate, when the result and input type is not change.
    - For example, concating string, where the input type and aggregation result are all string.
    - We need to define reducer for this method.

    ![alt text](https://github.com/marodrigues20/udemy_java/blob/main/JavaSpring%26ApacheKafkaBootcamp-BasicToComplete/Sections/Section-16/pic_09.png?raw=true)
  


## 97. Timestamp

### Flash Sale Stream - Timestamp


- One of the flash sale vote requirement, is that customer can vote only during certain time range.
- Unfortunately, the current front end cannot handle this yet.
- The API itself does not have vote timestamp that we can use Updating the application will need some times and requires major effort on android, iphone, and web platform. Fortunately, all of the app publish vote to kafka topic.

![alt text](https://github.com/marodrigues20/udemy_java/blob/main/JavaSpring%26ApacheKafkaBootcamp-BasicToComplete/Sections/Section-16/pic_10.png?raw=true)


- We already seen how to publish message on ProducerRecord when we learn about message header. We can also add timestamp on Producer record using one of its constructor.
- If we did not provide a timestamp, the producer will add current timestamp to the record.
- This means, other than key and value, we already has timestamp embedded on each kafka record.

![alt text](https://github.com/marodrigues20/udemy_java/blob/main/JavaSpring%26ApacheKafkaBootcamp-BasicToComplete/Sections/Section-16/pic_11.png?raw=true)


- For this, we must use kafka stream processor API. It provides class that we can use to extract timestamp. When we talk about stateful operation, this processor API will be useful for other things, not only limited to timestamp. 
- In this course, we will learn the basic of using processor API.


### Processor API

- Use processor API
- Extract timestamp
- Useful not only for timestamp
- Learn basic processor API


### Value Transformer

- We already learn about mapValues operation, which change kafka record value.
- While mapValues is useful, it is stateless operation and we cannot use it to access processor API. For accessing processor API,
that which be useful for stateful operation, we can use transformValues.
- transformValues requires a ValueTransformer class, that can be provided by either using lambda expression, or using ValueTransformerSupplier class.
- We will use first approach: stream.transformValues()

![alt text](https://github.com/marodrigues20/udemy_java/blob/main/JavaSpring%26ApacheKafkaBootcamp-BasicToComplete/Sections/Section-16/pic_12.png?raw=true)


### LocalDateTime to TimeStamp

- In java, timestamp is represented as epoch time, which is millisecond since 1 january 1970.
- Most of time data type in this lecture is using LocalDateTime, as well as this lecture.
- Since kafka timestamp data type is epoch time, I provide class LocalDateTimeUtil to convert LocalDateTime to epoch time. Please copy it from package util on project kafka-stream sample. Or you can create your own by using this syntax to convert LocalDateTime to epoch time.
- Please copy it from package util on project kafka-stream sample. Or you can create your own by using this syntax to convert LocalDateTime to epoch time.


![alt text](https://github.com/marodrigues20/udemy_java/blob/main/JavaSpring%26ApacheKafkaBootcamp-BasicToComplete/Sections/Section-16/pic_13.png?raw=true)


### Project Reference - Using Table and TimeStamp


- Project Reference: ../kafka-stream/kafka-stream-sample
  - Classes Added / Modified: 
    - FlashSaleVoteOneStream.java
    - FlashSaleVoteMessage.java


- Project Reference: ../kafka-stream/kafka-stream-order
  - Classes Added / Modified: 
      - FlashSaleVoteTwoStream.java
      - LocalDateTimeUtil.java
      - FlashSaleVoteTwoValueTransformer.java

```

  @Override
    public FlashSaleVoteMessage transform(FlashSaleVoteMessage value) {
        var recordTime = processorContext.timestamp();
        return (recordTime >= voteStartTime && recordTime <= voteEndTime) ? value : null;
    }


    @Bean
    public KStream<String, String> flashSaleVote(StreamsBuilder builder){

        var stringSerde = Serdes.String();
        var flashSaleVoteSerde = new JsonSerde<>(FlashSaleVoteMessage.class);

        var voteStart = LocalDateTime.of(LocalDate.now(), LocalTime.of(5, 30));
        var voteEnd = LocalDateTime.of(LocalDate.now(), LocalTime.of(6, 30));

        //We need to convert the json value into new key-value pair, with user id as key and item name as value. Use map for this.
        var flashSaleVoteStream = builder
                .stream("t-commodity-flashsale-vote", Consumed.with(stringSerde, flashSaleVoteSerde))
                .transformValues(() -> new FlashSaleVoteTwoValueTransformer(voteStart, voteEnd))
                .filter(((key, transformedValue) -> transformedValue != null ))
                .map((key, value) -> KeyValue.pair(value.getCustomerId(), value.getItemName()));

        //Now that we have stream with user id and item name,
        // Now that we have stream with user id and item name, stream this into intermediary topic,
        // so we can build a table from it.
        flashSaleVoteStream.to("t-commodity-flashsale-vote-user-item");


        // At this point, we have table, now we need to group the table.
        // Group by is grouping by key, so we must convert the key, which is currently user id on intermediary stream, to itemName.
        // Now we can count the group elements.
        builder.table("t-commodity-flashsale-vote-user-item", Consumed.with(stringSerde, stringSerde))
                .groupBy((user, votedItem) -> KeyValue.pair(votedItem, votedItem)).count().toStream()
                .to("t-commodity-flashsale-vote-two-result");

        return flashSaleVoteStream;
    }

```





