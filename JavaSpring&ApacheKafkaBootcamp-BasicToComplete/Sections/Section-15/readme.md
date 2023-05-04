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
