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

- Project Reference: ../kafka-stream/kafka-ms-sample
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


- Project Reference: ../kafka-stream/kafka-ms-sample
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

- Project Reference: ../kafka-stream/kafka-ms-order
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