# Section 15: Kafka Stream - Customer

## 92. Web & Mobile

### Customer Stream - Mobile & Web

- Everytime a user purchase item, web application will send the data into kafka topic, containing purchase number, amount, browser and operating system used.
- Mobile team has just add the same feature. But instead sending to existing topic, mobile app send data to different topic. Also the data structure is somewhat different.
- For example, mobile app also send GPS location from user but not send browser, instead it will send mobile app version. The analytics team needs to analyze customer
behaviour based on those two data.
- The only thing required, is that they need both mobile and web app data in same kafka topic, in real time.

![alt text](https://github.com/marodrigues20/udemy_java/blob/main/JavaSpring%26ApacheKafkaBootcamp-BasicToComplete/Sections/Section-15/pic_01.png?raw=true)


- We can use merge to do this, so we have two topics as source, and merge them into one sink.

![alt text](https://github.com/marodrigues20/udemy_java/blob/main/JavaSpring%26ApacheKafkaBootcamp-BasicToComplete/Sections/Section-15/pic_02.png?raw=true)


- You can copy source code for customer API from project kafka-order.
- The transaction will publish to topic customer-purchase-web and customer-purchase-mobile.
- Don’t forget to copy the message class to kafka-order and kafka-stream.
- This is the source code for the customer, if you download them.

### Source Code for Customer Purchase

- CustomerPurchase*.java
- Package com.course.kafka
  - api.request
  - api.server
  - broker.message
  - broker.producer
  - command.action
  - command.service


### Project Reference - Using Merge Function

```
@Bean
    public KStream<String, String> kStreamCustomerPurchaseAll(StreamsBuilder builder){
        var customerPurchaseMobileStream = builder.stream("t-commodity-customer-purchase-mobile",
                Consumed.with(Serdes.String(), Serdes.String()));
        var customerPurchaseWebStream = builder.stream("t-commodity-customer-purchase-web",
                Consumed.with(Serdes.String(), Serdes.String()));

        customerPurchaseMobileStream.merge(customerPurchaseWebStream).to("t-commodity-customer-purchase-all");

        return customerPurchaseMobileStream;
    }
```

- Project Reference: ../kafka-stream/kafka-stream-sample
  - Classes Added / Modified: 
    - CustomerPurchaseOneStream.java


- Project Reference: ../kafka-stream/kafka-stream-order
  - Classes Added / Modified: 
    - CustomerPurchaseMobileRequest.java
    - CustomerPurchaseWebRequest.java
    - PurchaseResponse.java
    - CustomerPurchaseApi.java
    - CustomerPurchaseMobileMessage.java
    - CustomerPurchaseWebMessage.java
    - CustomerPurchaseProducer.java
    - CustomerPurchaseAction.java
    - CustomerPurchaseService.java


### How to Run - Using Repartition Sintax

1. Run ../kafka-stream/kafka-ms-order
2. Run ../kafka-stream/kafka-ms-sample
3. Open Postman:
4. Click on "Customer Purchase"
5. Click on "Run" button
6. Fill "interaction" field using 1000 as a value
6. Fill "Delay" field using the 1000 as a value
7. Click on "Run Course - Spring Kafka 4"
8. Open Command Prompt:
   1. $ kafka-console-consumer --bootstrap-server broker:9092 --property print.key=true --property value.deserializer=org.apache.kafka.common.serialization.LongDeserializer --topic t-commodity-customer-purchase-all --property key.separator":"


Note:
    - As you can see on the console consumer, this sink topic receives both web and mobile purchase.

### Alternatively, kafka stream can read from multiple topics, without using merge.

```
    @Bean
    public KStream<String, String> kStreamCustomerPurchaseAll(StreamsBuilder builder){
        var topics = List.of("t-commodity-customer-purchase-mobile", "t-commodity-customer-purchase-web");
        
        var customerPurchaseAllStream = builder.stream(topics,
                Consumed.with(Serdes.String(), Serdes.String()));
        
        customerPurchaseAllStream.to("t-commodity-customer-purchase-all");

        return customerPurchaseAllStream;
    }
```

### How to Run - Using Repartition Sintax

1. Run ../kafka-stream/kafka-ms-order
2. Run ../kafka-stream/kafka-ms-sample
3. Open Postman:
4. Click on "Customer Purchase"
5. Click on "Run" button
6. Fill "interaction" field using 1000 as a value
6. Fill "Delay" field using the 1000 as a value
7. Click on "Run Course - Spring Kafka 4"
8. Open Command Prompt:
   1. $ kafka-console-consumer --bootstrap-server broker:9092 --property print.key=true --property value.deserializer=org.apache.kafka.common.serialization.LongDeserializer --topic t-commodity-customer-purchase-all --property key.separator":"


## 93. Cart & Wishlist

### Customer Stream - Cart & Wishlist

- To know customer shopping preferences, analytics team need to analyze each customer behaviour. Every item added to shopping cart, will be sent to kafka topic. Also, every item added to wishlist will be sent to different topic.
- Once more, analytics team needs our help to send data into one topic.
- But this time, they asked us to form a new message : customer preference.
- Also, every change on either cart or wishlist should send the updated entity data to aggregate topic.
- The message key should be customer id. 
- Each message value will has map of shopping cart item, and map of wishlist item, where the map key is item name, and map value is most recent datetime when the item added.


![alt text](https://github.com/marodrigues20/udemy_java/blob/main/JavaSpring%26ApacheKafkaBootcamp-BasicToComplete/Sections/Section-15/pic_03.png?raw=true)


- We will group cart and wishlist based on customer, which is the key.
- From those two groups, we will co-group them, which forms a ktable of customer preference.
- Finally, we convert the ktable to stream and send it to sink topic.


![alt text](https://github.com/marodrigues20/udemy_java/blob/main/JavaSpring%26ApacheKafkaBootcamp-BasicToComplete/Sections/Section-15/pic_04.png?raw=true)


### Source Code for Customer Purchase

- You can copy source code for customer API from project kafka-order.
- The transaction will publish to topic shopping cart and wishlist.

- CustomerPreference*.java
- Package com.course.kafka
  - api.request
  - api.server
  - broker.message
  - broker.producer
  - command.action
  - command.service


### Project Reference - cogroup

- Project Reference: ../kafka-stream/kafka-stream-sample
  - Classes Added / Modified: 
    - CustomerPreferencesAggregateMessage.java
    - CustomerPreferenceShoppingCartMessage.java
    - CustomerPreferenceWishlistMessage.java
    - CustomerPreferenceShoppingCartAggregator.java
    - CustomerPreferenceWishlistAggregator.java
    - CustomerPreferenceOneStream.java



```
 public KStream<String, CustomerPreferenceAggregateMessage> kstreamCustomerPreferenceAll(StreamsBuilder builder){
        var stringSerde = Serdes.String();
        var shoppingCartSerde = new JsonSerde<>(CustomerPreferenceShoppingCartMessage.class);
        var wishlistSerde = new JsonSerde<>(CustomerPreferenceWishlistMessage.class);
        var aggregateSerde = new JsonSerde<>(CustomerPreferenceAggregateMessage.class);

        var groupedShoppingCartStream = builder.stream("t-commodity-customer-preference-cart",
                Consumed.with(stringSerde, shoppingCartSerde)).groupByKey();

        var groupedWishlistStream = builder.stream("t-commodity-customer-preference-wishlist",
                Consumed.with(stringSerde, wishlistSerde)).groupByKey();

        var customerPreferenceStream = groupedShoppingCartStream.cogroup(SHOPPING_CART_AGGREGATOR).cogroup(groupedWishlistStream, WISHLIST_AGGREGATOR)
                //This will return a ktable, which we can convert to stream
                .aggregate(() -> new CustomerPreferenceAggregateMessage(), Materialized.with(stringSerde, aggregateSerde))
                .toStream();

        customerPreferenceStream.to("t-commodity-customer-preference-all", Produced.with(stringSerde, aggregateSerde));

        return customerPreferenceStream;
    }
```

### Explanation of Code


![alt text](https://github.com/marodrigues20/udemy_java/blob/main/JavaSpring%26ApacheKafkaBootcamp-BasicToComplete/Sections/Section-15/pic_05.png?raw=true)

- For example, if we have this timeline and data at the top part, the aggregation result will be like in bottom part. The aggregation result will be updated
as soon as data arrives. At first data, eve add apple to shopping cart, and so the aggregate contains apple at shopping cart, and empty wishlist.
- 2nd data, eve add banana to shopping cart, so the aggregate result on shopping cart will contains apple and banana. Wishlist still empty.
- 3rd data, adam add tomato to wishlist. The aggregated data for adam will contains one item on tomato, and empty shopping cart.
- 4th data, eve add cherry to wishlist.
- Eve aggregated data now contains one item at wishlist.
- 5th data, adam add garlic to shopping cart. Adam’s aggregated data now contains one item at shopping cart. On 6th data, eve add apple to shopping cart.
- The aggregated data already contains apple, so the aggregated shopping cart on apple will be updated. The last timestamp on aggregated data will be updated to timestamp 6.




## How to Run

- For the sample, I will run a kafka console consumer for sink topic.
- I also provide sample postman collection based on previous diagram.
- If we run this postman collection, we can see the aggregation result at the console consumer. 
  
1. Run ../kafka-stream/kafka-ms-order
2. Run ../kafka-stream/kafka-ms-sample
3. Open Postman:
4. Click on "Customer Preference\Simulation"
5. Click on "Run" button
6. Fill "interaction" field using 1 as a value
6. Fill "Delay" field using the 2000 as a value
7. Click on "Run Course - Spring Kafka 4"
8. Open Command Prompt:
   1. $ kafka-console-consumer --bootstrap-server broker:9092 --property print.key=true --property value.deserializer=org.apache.kafka.common.serialization.LongDeserializer --topic t-commodity-customer-preference-all --property key.separator":"