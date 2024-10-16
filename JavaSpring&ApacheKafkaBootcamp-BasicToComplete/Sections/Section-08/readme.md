# Section 08: Handling Exception

## 41. KafkaListener Error Handler

### What We Will Do

- Spring default: log exception
- Able to implement our own error handler
- Scenario
  - Publish food order
  - Exception: consume invalid food amount

### Java Project

Project Reference: kafka-core-producer
Classes Added / Modified: 
    - FoodOrder.java
    - FoodOrderProducer.java
    - FoodOrder.java

Project Reference: kafka-core-consumer
Classes Added / Modified: 
    - FoodOrderConsumer.java
    - FoodOrderErrorHandler.java
    - FoodOrder.java


## 42. Global Error Handler

### What We Will Do

- Global error handler: works for all kafka consumers
- Error handler on Spring container
- Scenario
  - Publish random number
  - Consume odd number throws exception
  - Handle using global error handler


### Java Project

Project Reference: kafka-core-producer
Classes Added / Modified: 
  - SimpleNumber.java
  - SimpleNumberProducer.java
  - KafkaCoreProducerApplication_Section8.java
  
Project Reference: kafka-core-consumer
Classes Added / Modified: 
  - SimpleNumber.java
  - SimpleNumberConsumer.java
  - GlobalErrorHandler.java
  - KafkaConfig


### Explanation how Global Exception vs KafkaListener Error Handler

- The global error handler is triggerred for simple number.
- This is interesting, the global error handler is not triggered for food order.
- What this means?
- It should be "global", that works for all consumers, right?
- So this is what happened.
- On simple number consumer, we did not write any specific error handler.
- So when this consumer throws error, it will go directly to GlobalErrorHandler.
- But with FoodOrderConsumer, we define FoodOrderErrorHandler, so the exception go first to it.
- And it stops there, because -well- the FoodOrderErrorHander already handle it.


![alt text](https://github.com/marodrigues20/udemy_java/blob/main/JavaSpring%26ApacheKafkaBootcamp-BasicToComplete/Sections/Section-08/Global_Error_Handler.png?raw=true)


- If we want to continue this error, or any specific error, to global error handler, what we need to do is re-throw the exception, so the next error handler in chain which is GlobalErrorHandler- can handle it.
- To re-throw the exception, go back to FoodOrderErrorHandler.java.
- In the error handler, we need to re-throw the exception.
- It is up to you, what kind of exception you want to re-throw.
- In this case, I will re-throw all RuntimeException



## 43. Why Retrying Mechanism?

- Spring Kafka will log failed messages
- Our own ErrorHandler
- Case:
  - Service temporarily unavailable
  - Retry hit service without trigger from consumer
  - Retry for n times only


### Scenario

- Topic: t-image, 2 partitions
- Publish message represents image
- Consumer
  - Simulate API call to convert image
  - Simulate failed API call
  - Retry after 10 seconds
  - Retry 4 times


### Project Reference

1. Create a topic with 2 partition
   1. $ kafka-topic.sh --bootstrap-server localhost:9092 --create --partitions 2 --replication-factor 1 --topic t-image
   2. $ kafka-topic.sh --bootstrap-server localhost:9092 --describe --topic t-image

Project Reference: kafka-core-producer
Classes Added / Modified: 
- Image.java
- ImageProducer.java
- ImageService.java
- KafkaCoreProducerApplication_Section8.java


Project Reference: kafka-core-consumer
Classes Added / Modified: 
- Image.java
- ImageConsumer.java
- KafkaConfig.java


### Retry Consumer

- We will use error handler for imageRetryContainerFactory, so basically we can copy paste the kafkaListenerContainerFactory from previous lecture.
- I will copy paste it and rename both the method and bean name into "imageRetryContainerFactory".
- Remove the global error handler.
- For the retry, spring already provide method setCommonErrorHandler on factory.
- If we want to retry 3 times with fixed interval 10 seconds between retry, we can use default error handler with fixed back off, like this.
- OK, remember that we have 6 messages, 3 in each partition.
- On partition 0, second message is throwing exception.
- Start consumer application and see what happened.
- Notice the log for partition 0.
- Consumer will process 1st message on partition 0.
- On 2nd message, it throws exception, so consumer will wait for 10 seconds, then retry. Then consumer try to process 2nd message again, up to three times, with 10 second delay between each retry.
- After fourth attempt still failed, consumer will continue to 3rd message on partition 0.
- On partition 1, all images processed without error.
- This kind of retry is called as blocking retry.
- When consumer encounter error on message 2, it will block the consume process, so message 3 will not be processed. The consumer will keep retry on message 2
according to error handler, until it success, or the error handler exhausted and failed.
- Only after then, the consumer will process to message 3.
- Blocking retry is a good fit if the message must be processed in sequence.
- Like updating transaction status, which must be done in order.
- However it has drawback, that the consume process is halted until retry finished, and can cause bottleneck.
- To mitigate the drawback, create a retry policy that just enough not too short, and not too fast. Note that the blocking is just on one partition that has error. So if we have multiple consumers, one for each partition, the partition without error will stil consume message.

### Blocking Retry

- Good when message must be processed in sequence
- Drawback: process halted (might bottleneck)
- Mitigate: retry "just enought"
- Block only on partition which has error
- Other partititon (no error) keep consuming


## 44. Handling Exception - Dead Letter Topic

- Message process keep failing
  - Non technical issue
  - Permanent techinical issue
  - Process message differently
- Send such message to dead letter topic
- Dead letter record

### DeadLetterPublishingRecoverer

![alt text](https://github.com/marodrigues20/udemy_java/blob/main/JavaSpring%26ApacheKafkaBootcamp-BasicToComplete/Sections/Section-08/DeadLetterPublishingRecoverer.png?raw=true)

- In this lecture, we will learn how to send dead letter record to custom topic.
- In this lecture, I will create dead letter topic with same partition as original topic, but it is not mandatory to do so. This is the scenario that we will use.
- We will simulate invoice publishing, sending message to t-invoice.
- If invoice amount is less than one, we will throw an exception. In such case, we will retry but only five times. After 5 failed retry attempts,
we will publish the message into .t-invoice-dead where another consumer will consume from it and process with different logic.

### Scenario

- Publish to t-invoice
- If amount is less than one, throw exception
- Retry 5 times
- After 5 failed retry attempts, publish to t-invoice-dead
- Another consumer will consume from t-invoice-dead

### Project & Reference

1. Create a topic with 2 partition
   1. $ kafka-topic.sh --bootstrap-server localhost:9092 --create --partitions 2 --replication-factor 1 --topic t-invoice
   2. $ kafka-topic.sh --bootstrap-server localhost:9092 --create --partitions 2 --replication-factor 1 --topic t-invoice-dead
   3. $ kafka-topic.sh --bootstrap-server localhost:9092 --describe --topic t-invoice


Project Reference: kafka-core-producer
Classes Added / Modified: 
   - Invoice.java
   - InvoiceProducer.java
   - InvoiceService.java
   - KafkaCoreProducerApplication_Section8


Project Reference: kafka-core-consumer
Classes Added / Modified: 
  - Invoice.java
  - InvoiceConsumer.java
  - KafkaConfig.java


### Dead Letter Records

- Dead letter record: message sent to DLT
- Just regular topic / message
- Create consumer to listen from DLT


![alt text](https://github.com/marodrigues20/udemy_java/blob/main/JavaSpring%26ApacheKafkaBootcamp-BasicToComplete/Sections/Section-08/DeadLetterRecoverer.png?raw=true)

- You can also create kafka listener from java code, to process the dead letter records.
- For example, in our sample case, we have t-invoice as main topic.
- The consumer for t-invoice might process the invoice for payment process.
- However, for failed invoices, finance supervisor must get notification.
- All invoices that cannot be processed should go to t-invoice-dead as dead letter topic, and another consumer can consume the dead letter invoice records and send notification to finance supervisor. This ends our lesson about dead letter topic.


## 45. Non Blocking Retry

- Message can be processed independently
- Message retry still needed
- Spring non blocking retry


- At some cases, we might not need blocking retry, as message can be processed independently. However, message retry still needed.
- In that case, Spring provides a way to do non-blocking retry.
- In non blocking retry, when consumer got error on message 2, it will keep processing message 3.
- The retry process, up until success or failed, will also continue on the background.
- So when message 3 not error, or the next messages, consumer will keep processing, while message 2 still on retry.

![alt text](https://github.com/marodrigues20/udemy_java/blob/main/JavaSpring%26ApacheKafkaBootcamp-BasicToComplete/Sections/Section-08/NonBlockingRetry.png?raw=true)


1. Create a topic with 2 partition
   1. $ kafka-topic.sh --bootstrap-server localhost:9092 --create --partitions 2 --replication-factor 1 --topic t-image-2
   2. $ kafka-topic.sh --bootstrap-server localhost:9092 --describe --topic t-image-2


Project Reference: kafka-core-producer
Classes Added / Modified: 
   - Image2Producer.java
   - KafkaCoreProducerApplication_Section8.java


Project Reference: kafka-core-consumer
Classes Added / Modified: 
  - Image2Consumer.java
  - KafkaCoreConsumerApplication.java