# Section 17: Kafka Stream - Feedback Rating

## 98. Average Rating

### Feedback Dashboard

- When we learn about feedback, there is a star rating there.
- Customer can give 1 to 5 star rating to each location.

![alt text](https://github.com/marodrigues20/udemy_java/blob/main/JavaSpring%26ApacheKafkaBootcamp-BasicToComplete/Sections/Section-17/pic_01.png?raw=true)

- To use this data, management decide to create simple dashboard like this for displaying every location and average rating.

![alt text](https://github.com/marodrigues20/udemy_java/blob/main/JavaSpring%26ApacheKafkaBootcamp-BasicToComplete/Sections/Section-17/pic_02.png?raw=true)

### Average Rating

- Average rating is calculated using simple math: sum of all rating submitted, divided by count of rating submitted.
- To process this simple math on kafka stream, we need to know all of the submitted data, not only current data. This is where we will use state store which we can access using processor API.

- average = sum(ratings)/count(ratings)
- Need to know all ratings
- Use processor API & state store


### High Level Topology

- The topology is simple, we only has one processor to calculate average rating.
- We need to know sum and count of existing ratings.
- In this case, we will use state store to save and retrieve these sum and count data.
- The average rating is calculated for each branch location.
- In state store, we will use location as key, and build a data structure that consists of sum and count rating as state store value.
- In the processor, we will use transformValue method so we can access state store.


![alt text](https://github.com/marodrigues20/udemy_java/blob/main/JavaSpring%26ApacheKafkaBootcamp-BasicToComplete/Sections/Section-17/pic_03.png?raw=true)


### State Store - How it works

- Here is to describe how it works.
- The first data is indonesia with rating 4. There is no state with key Indonesia, so we create new state in state store, with sum rating is 4 and count is 1.
- For each data, we will calculate the average using sum divided by count, so at this point, Indonesia average rating is 4.
- Second data is Singapore with rating 3. This is also a new state, with sum 3 and count 1.
- Next data is singapore with rating 5. At this point, the state store with key “Singapore” will be updated with sum rating 8 and count 2.
- Which makes the average rating for singapore is 4.
- Then we get Indonesia with rating 5. This will updates the state store with sum 9, count 2 and average rating 4.5. Then we get Indonesia with rating 3.
- This will updates the state store with sum 12, count 3 and average rating 4. Et cetera.


![alt text](https://github.com/marodrigues20/udemy_java/blob/main/JavaSpring%26ApacheKafkaBootcamp-BasicToComplete/Sections/Section-17/pic_04.png?raw=true)



### Project Reference - Using ValueTransformer and KeyValueStore


- Project Reference: ../kafka-stream/kafka-stream-sample
  - Classes Added / Modified: 
    - FeedbackRatingOneMessage.java
    - FeedbackRatingOneValueTransformer.java
    - FeedbackRatingOneStream.java
    


- Project Reference: ../kafka-stream/kafka-stream-order
  - Classes Added / Modified: 
      - FlashSaleVoteTwoStream.java
      - LocalDateTimeUtil.java
      - FlashSaleVoteTwoValueTransformer.java


```

    @Override
    public FeedbackRatingOneMessage transform(FeedbackMessage value) {
        var storeValue = Optional.ofNullable(ratingStateStore.get(value.getLocation()))
                .orElse(new FeedbackRatingOneStoreValue());

        var newSumRating = storeValue.getSumRating() + value.getRating();
        storeValue.setSumRating((newSumRating));

        var newCountRating = storeValue.getCountRating() + 1;
        storeValue.setCountRating(newCountRating);

        ratingStateStore.put(value.getLocation(), storeValue);

        var branchRating = new FeedbackRatingOneMessage();
        branchRating.setLocation(value.getLocation());

        double averageRating = Math.round((double) newSumRating / newCountRating * 10d) / 10d;
        branchRating.setAverageRating(averageRating);

        return branchRating;
    }



    @Bean
    public KStream<String, FeedbackMessage> kstreamFeedbackRating(StreamsBuilder builder) {
        var stringSerde = Serdes.String();
        var feedbackSerde = new JsonSerde<>(FeedbackMessage.class);
        var feedbackRatingOneSerde = new JsonSerde<>(FeedbackRatingOneMessage.class);
        var feedbackRatingOneStoreValueSerde = new JsonSerde<>(FeedbackRatingOneStoreValue.class);

        var feedbackStream = builder.stream("t-commodity-feedback", Consumed.with(stringSerde, feedbackSerde));

        var feedbackRatingStateStoreName = "feedbackRatingOneStateStore";
        var storeSupplier = Stores.inMemoryKeyValueStore(feedbackRatingStateStoreName);
        var storeBuilder = Stores.keyValueStoreBuilder(storeSupplier, stringSerde, feedbackRatingOneStoreValueSerde);

        builder.addStateStore(storeBuilder);

        feedbackStream.transformValues(() -> new FeedbackRatingOneValueTransformer(feedbackRatingStateStoreName), feedbackRatingStateStoreName)
                .to("t-commodity-feedback-rating-one", Produced.with(stringSerde, feedbackRatingOneSerde));

        return feedbackStream;
    }

```

1. Open PostMan
2. Expand "Feedback" folder
3. Click "01 - Indonisia (4)".
4. Click on "Send" button.
5. Click "02 - Indonisia (5)".
6. Click on "Send" button.
7. Click "03 - Indonisia (3)".
8. Click on "Send" button.
9. Click "04 - Indonisia (5)".
10. Click on "Send" button.
11. Click "05 - Indonisia (3)".
12. Click on "Send" button.




## 99. Detailed Rating

### Feedback Dashboard

