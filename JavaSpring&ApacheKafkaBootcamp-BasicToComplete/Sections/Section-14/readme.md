# Section 14: Feedback Stream

## 85. Are You Good Enough?

- The quality service department provides customer feedback form.
- The customer will fill rating and free-text feedback for certain branch location.
- Everybody loves 5 star rating and feedback, but in the commodity company, rating is not enough.
- The quality service team wants to analyze customer emotion from feedback text.
- The criteria is simple: We will have list of “good words”, and the more “good word” received, the better that branch will be.

![alt text](https://github.com/marodrigues20/udemy_java/blob/main/JavaSpring%26ApacheKafkaBootcamp-BasicToComplete/Sections/Section-14/pic_01.png?raw=true)


 - For this, we will create a feedback stream with this topology. The source is from feedback topic, and we will have stream processor that analyze feedback text to find out what are the “good words” in each feedback text.
  

![alt text](https://github.com/marodrigues20/udemy_java/blob/main/JavaSpring%26ApacheKafkaBootcamp-BasicToComplete/Sections/Section-14/pic_02.png?raw=true)


### Source Code for Feedback

- We will not create feedback user interface, just API and producer. At this point, I believe you can create the feedback API and producer.
- The feedback will have attributes : branch location, feedback date time, rating (which is 1 to 5), and free-text feedback.
- It will publish to t-commodity-feedback If you want a quick solution, please go to last section of the course and download the source code.

- Feedback*.java
- Package com.course.kafka
  - api.request
  - api.server
  - broker.message
  - broker.producer
  - command.action
  - command.service


### Project Reference

- Project Reference: ../kafka-stream/kafka-ms-order
  - Classes Added / Modified: 
    - FeedbackRequest.java
    - FeedbackApi.java
    - FeedbackAction.java
    - FeedbackService.java
    - FeedbackProducer.java
    - FeedbackMessage.java

- Project Reference: ../kafka-stream/kafka-stream-sample
  - Classes Added / Modified: 
    - FeedbackMessage.java
    - FeedbackOneStream.java


### Key Code - Kafka API

```
var goodFeedbackStream = builder.stream("t-commodity-feedback", Consumed.with(stringSerde, feedbackSerde))
                .flatMapValues(mapperGoodWords());
```

### How to Run

1. Open Postman
2. Expand on "Feedback"
3. Post a request using "Create Good Feedback"
4. Run ../kafka-stream/kafka-ms-order
5. Run ../kafka-stream/kafka-ms-sample
6. Open Command Prompt:
   1. $ kafka-console-consumer.sh --bootstrap-server localhost:9092 --property print.key=true --topic t-commodity-feedback-one-good



## 86. Who Owns This Feedback?

### Feedback Stream - Who owns This Feedback?

- The previous topology is working fine, but we don’t know who owns this good word.
- So this time, we will add branch location as key.

![alt text](https://github.com/marodrigues20/udemy_java/blob/main/JavaSpring%26ApacheKafkaBootcamp-BasicToComplete/Sections/Section-14/pic_03.png?raw=true)


- Project Reference: ../kafka-stream/kafka-stream-sample
  - Classes Added / Modified: 
    - FeedbackMessage.java
    - FeedbackTwoStream.java


### Key Code - Kafka API

```
var goodFeedbackStream = builder.stream("t-commodity-feedback", Consumed.with(stringSerde, feedbackSerde))
                .flatMap((key, value) -> Arrays
                .asList(value.getFeedback().replaceAll("[^a-zA-Z]", "").toLowerCase().split("\\s+")).stream()
                .filter(word -> GOOD_WORDS.contains(word)).distinct()
                .map(goodWord -> KeyValue.pair(value.getLocation(), goodWord)).collect(Collectors.toList())
                );
```


### Java Stream API

- Since Java 8
- Not kafka stream
- Same method names:
  - filter
  - flatMap
  - forEach
  - map
  - peek


### How to Run

- Now if I send another good feedback you will see that the sink stream contains transformed record with order location as key.

1. Open Postman
2. Expand on "Feedback"
3. Post a request using "Create Good Feedback"
4. Run ../kafka-stream/kafka-ms-order
5. Run ../kafka-stream/kafka-ms-sample
6. Open Command Prompt:
   1. $ kafka-console-consumer.sh --bootstrap-server localhost:9092 --property print.key=true --topic t-commodity-feedback-one-good


## 87. Good Feedback or Bad FeedBack?

### Bad Feedback?

![alt text](https://github.com/marodrigues20/udemy_java/blob/main/JavaSpring%26ApacheKafkaBootcamp-BasicToComplete/Sections/Section-14/pic_05.png?raw=true)

- Sometimes, branch get bad feedback.
- To improve branch service quality, we have to analyze that bad feedback too.
- We will split the text into words, without filtering them, just split it.
- This will be the first child processor. Then we will branch the words into good word or bad word, while removing the other words.
- So we will have this kind of topology.


![alt text](https://github.com/marodrigues20/udemy_java/blob/main/JavaSpring%26ApacheKafkaBootcamp-BasicToComplete/Sections/Section-14/pic_04.png?raw=true)


- In the good feedback processor, we already has list of good word, and we will do the same for bad words. We will have list of bad words.

### Project Reference

- Project Reference: ../kafka-stream/kafka-stream-sample
  - Classes Added / Modified: 
    - FeedbackThreeStream.java



### Key Code - Kafka API

```
sourceStream.flatMap(splitWords()).split()
                .branch(isGoodWord(), Branched.withConsumer(ks -> ks.to("t-commodity-feedback-three-good")))
                .branch(isBadWord(), Branched.withConsumer(ks -> ks.to("t-commodity-feedback-three-bad")));
```


### How to Run

1. Open Postman
2. Expand on "Feedback"
3. Post a request using "Create Good Feedback"
4. Post a request using "Create Bad Feedback"
5. Run ../kafka-stream/kafka-ms-order
6. Run ../kafka-stream/kafka-ms-sample
7. Open Command Prompt:
   1. $ kafka-console-consumer.sh --bootstrap-server localhost:9092 --property print.key=true --topic t-commodity-feedback-one-good


## 88. Group Using Table

- It is good to know the exact good or bad words, but quality service needs more.
- They need to know the count of words for each branch. So we need additional two informations : the count of bad word, and the count of good word
- In that case, we need additional processor to count the word based on branch, which currently is the record key.
- The group result will be branch name and count of good word or bad word.
- In this case, we will use KTable to grouping. So we will have four topics to send.
- Two topics to send each word. And two topics to send each count.
- Wait, we can’t send KTable directly to topic, so we need to convert the table back to stream before send to sink topic Copy class FeedbackThreeStream into FeedbackFourStream.
  

![alt text](https://github.com/marodrigues20/udemy_java/blob/main/JavaSpring%26ApacheKafkaBootcamp-BasicToComplete/Sections/Section-14/pic_06.png?raw=true)




### Project Reference

- Project Reference: ../kafka-stream/kafka-stream-sample
  - Classes Added / Modified: 
    - FeedbackFourStream.java



### Key Code - Kafka API

```
// groupByKey will produce KTable, in which we have count() method to do this functionality.
sourceStream.flatMap(splitWords()).split()
                .branch(isGoodWord(), Branched.withConsumer(ks -> {
                    ks.to("t-commodity-feedback-four-good");
                    ks.groupByKey().count().toStream().to("t-commodity-feedback-four-good-count");
                }))
                .branch(isBadWord(), Branched.withConsumer(ks -> {
                    ks.to("t-commodity-feedback-four-bad");
                    ks.groupByKey().count().toStream().to("t-commodity-feedback-four-bad-count");
                }));
```

### How to Run

1. Open Postman
2. Expand on "Feedback"
3. Post a request using "Create Good Feedback"
4. Post a request using "Create Bad Feedback"
5. Post a request using "Create Random Feedback"
6. Run ../kafka-stream/kafka-ms-order
7. Run ../kafka-stream/kafka-ms-sample
8. Open Command Prompt:
   1. $ kafka-console-consumer.sh --bootstrap-server localhost:9092 --property print.key=true --topic t-commodity-feedback-four-good
   2. $ kafka-console-consumer.sh --bootstrap-server localhost:9092 --property print.key=true --topic t-commodity-feedback-four-bad
   3. $ kafka-console-consumer.sh --bootstrap-server localhost:9092 --property print.key=true --topic t-commodity-feedback-four-good-count
   4. $ kafka-console-consumer.sh --bootstrap-server localhost:9092 --property print.key=true --topic t-commodity-feedback-four-bad-count


## 89. Delay on Table

### Feedback Stream - Delay on Table 

- If you see some delay when using KTable, between sending message to input topic and see the output topic from KTable, it is normal.
- Kafka cache output and send down the stream every certain interval.
- The default Kafka configuration will takes about 30 seconds to process this.
- The configuration is commit.interval.ms which controls how often kafka flush results down the stream.
- We can adjust this configuration
  - Default configuratin: 30 seconds
  - Cache and send
  - On commit.interval.ms
  - Adjust configuration


## 90. Send and Continue

### Feedback Stream - Send and Continue

- In previous feedback stream, we send the data to output topic, then process it again to be sent to second output topic. In other words, we process the input stream more than once.
- This pattern is so common that kafka stream provides through method, so let’s see it.


![alt text](https://github.com/marodrigues20/udemy_java/blob/main/JavaSpring%26ApacheKafkaBootcamp-BasicToComplete/Sections/Section-14/pic_07.png?raw=true)

### Project Reference

- Project Reference: ../kafka-stream/kafka-stream-sample
  - Classes Added / Modified: 
    - FeedbackFiveStream.java




### Key Code - Kafka API

```
//However, we use deprecated syntax on this file. So let’s see the newer alternative later:
var feedbackStream = sourceStream.flatMap(splitWords()).branch(isGoodWord(), isBadWord());

        feedbackStream[0].through("t-commodity-feedback-five-good").groupByKey().count().toStream()
                .to("t-commodity-feedback-five-good-count");
        feedbackStream[1].through("t-commodity-feedback-five-bad").groupByKey().count().toStream()
                .to("t-commodity-feedback-five-bad-count");
```


### How to Run

1. Open Postman
2. Click on "Feedback"
3. Check just "Create Random Feedback"
4. Fill "interaction" field using the value 10
5. Click on "Run Course - Spring Kafka 4"
6. Run ../kafka-stream/kafka-ms-order
7. Run ../kafka-stream/kafka-ms-sample
8. Open Command Prompt:
   1. $ kafka-console-consumer.sh --bootstrap-server localhost:9092 --property print.key=true --topic t-commodity-feedback-five-good
   2. $ kafka-console-consumer.sh --bootstrap-server localhost:9092 --property print.key=true --topic t-commodity-feedback-five-bad
   3. $ kafka-console-consumer.sh --bootstrap-server localhost:9092 --property print.key=true --topic t-commodity-feedback-five-good-count
   4. $ kafka-console-consumer.sh --bootstrap-server localhost:9092 --property print.key=true --topic t-commodity-feedback-five-bad-count


- NOTE: When you run Kafka Stream, you will see in the intellij logs a state.dir property. This is the folder where kafka use to save state.
  - If the result in your application different with expected, or error, delete all files within that state.dir folder.
  - Alternatively, you can create random directory, by modifying kafka stream config.
  - For the application ID, append current time millis, so it will always unique.
  - For the application ID, append current time millis, so it will always unique every time we run the application.

```
//props.put(StreamsConfig.APPLICATION_ID_CONFIG, "kafka-streams");
props.put(StreamsConfig.APPLICATION_ID_CONFIG, "kafka-streams-" + System.currentTimeMillis());
```

### Project Reference

- Project Reference: ../kafka-stream/kafka-stream-sample
  - Classes Added / Modified: 
    - FeedbackFiveV2Stream.java


### Key Code - Kafka API - Using Repartition Sintax

- And this is how we use repartition syntax, the newer alternative for through.
- This will achieve same functionality : sending word to a topic, then process and send the word count to another topic, but the syntax might be confusing.
- It is up to you, which one to use.

```
sourceStream.flatMap(splitWords()).split().branch(isGoodWord(), Branched.withConsumer(ks -> ks.repartition(Repartitioned.as("t-commodity-feedback-five-good"))
          .groupByKey().count().toStream().to("t-commodity-feedback-five-good-count")))
          .branch(isBadWord(), Branched.withConsumer(ks -> ks.repartition(Repartitioned.as("t-commodity-feedback-five-bad"))
          .groupByKey().count().toStream().to("t-commodity-feedback-five-bad-count")));
```

### How to Run

1. Open Postman
2. Click on "Feedback"
3. Check just "Create Random Feedback"
4. Fill "interaction" field using 10 as a value
5. Fill "Delay" field using the 1000 as a value
6. Click on "Run Course - Spring Kafka 4"
7. Run ../kafka-stream/kafka-ms-order
8. Run ../kafka-stream/kafka-ms-sample
9. Open Command Prompt:
   1. $ kafka-console-consumer.sh --bootstrap-server localhost:9092 --property print.key=true --topic t-commodity-feedback-five-good
   2. $ kafka-console-consumer.sh --bootstrap-server localhost:9092 --property print.key=true --topic t-commodity-feedback-five-bad
   3. $ kafka-console-consumer.sh --bootstrap-server localhost:9092 --property print.key=true --topic t-commodity-feedback-five-good-count
   4. $ kafka-console-consumer.sh --bootstrap-server localhost:9092 --property print.key=true --topic t-commodity-feedback-five-bad-count


- Note that when using repartition, the output topic is for internal kafka use, and the name we define is not the actual topic name.
- So message only sent to count topic.