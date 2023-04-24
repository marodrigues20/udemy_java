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


### How to Run

1. Open Postman
2. Expand on "Feedback"
3. Post a request using "Create Good Feedback"
4. Run ../kafka-stream/kafka-ms-order
5. Run ../kafka-stream/kafka-ms-sample
6. Open Command Prompt:
   1. $ kafka-console-consumer.sh --bootstrap-server localhost:9092 --property print.key=true --topic t-commodity-feedback-one-good

