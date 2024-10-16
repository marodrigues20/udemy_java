# Section 07 - Working with JSON Messages

## 30. Why JSON?

- Use JSON as standard format
- Java etc has library to create & parse JSON
- Application focus on business logic instead of parsing data
- Java uses Jackson or GSON library
- This course will use Jackson

- Added two dependencies in build.gradle.kts in both projects
  
  ```
  implementation("com.fasterxml.jackson.core:jackson-databind:${jacksonVersion}") 
  implementation("com.fasterxml.jackson.core:jackson-annotations:${jacksonVersion}")
  ```

## 31. Producing JSON Message

- Reference Project: kafka-core-producer

- Created classes 
  - JsonConfig.java
  - Employee.java
  - EmployeeJsonProducer.java
  - Used the class KafkaCoreProducerApplication.java
    ```
    for(int i = 0; i < 5; i ++){
	var emp = new Employee("emp-" + i, "Employee " + i, LocalDate.now());
	producer.sendMessage(emp);
    }
    ```  

1. Run the Project
2. Consume the messages by the cli command bellow: 
  - $ $ kafka-console-consumer.sh --bootstrap-server localhost:9092 --offset earliest --partition 0 --topic t-employee

3. Next Lecture create in advance a new topic.
   1. $ kafka-topic.sh --bootstrap-server localhost:9092 --create --partitions 1 --replication-factor 1 --topic t-employee-2


## 32. Customize JSON Format


- By default, Jackson will generate JSON string with variable names as json attribute and local date in complex format.
- We can customize this items by using annotation.
- For example, we can have custom format for attribute names and date format.
- Customization handy especially if you works a lot with JSON.
- Different teams might have different standards, but we need to keep the standard to avoid miscommunication among team members.
- For example, In some teams I’ve worked, the standard for date format is ISO 8601.
- That way, we know how to send and receive date string, and convert it into date variable.
- In a REST API or messaging system that uses JSON for data exchange this kind of standards should be used.
- If you want to know more about REST API and jackson annotation, please refer to last section of the course.


- JSON Customization
  - Default:
    - someAttribute
    - Local date: verbose format
  - Customize by Jackson annotations
  - Different teams, different standards (some using snake_case, some using kebab-case)
  - Avoid miscommunication
  - Useful in data exchanges


- Reference Project: kafka-core-producer

- Created classes
  - Employee2JsonProducer.java
  - JsonConfig.java added 
    ```
     // By default, jackson will not write LocalDate as String. If you want it as String, add the bellow line
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
    ```    
  - Used the class KafkaCoreProducerApplication.java
    ```
    private Employee2JsonProducer producer;
    ```
  - Added in application.yaml file
    ```
    spring:
      jackson:
        date-format: yyyy-MM-dd
    ```  

## 33. Consuming JSON Message

Project Reference: kafka-core-consumer

Classes Created 
- config/JsonConfig.java
- entity/Employee
- EmployeeJsonConsumer.java

File changed

- application.yml
  ```
  spring:
  jackson:
    date-format: yyyy-MM-dd
  ```

## 34. Consuming with Consumer Groups - Create Producer

- We will have a producer that publish commodity data.
- There will be two functionalities that will access same commodity data.
- One functionality is for update dashboard, and the other is for send notification.
- So we will have same messages read by two consumers, each has different functionality.
- If you need to remember more detail, please see lecture at the beginning of this course about kafka theory.

- In Summary: What We will create
  - Consumer with consumer group
  - Producer: publish commodity data
  - Functionalities
    - 1: Update dashboard
    - 2: Send notification
  - Read same message, different functionality

  ### This is the schema that we will use. - Kafka Schema

- We will have topic t-commodity, where we will begin with one partition and add more later.
- For the producer, we will create a scheduler that pull random commodity data from API and publish them to kafka.
- This scheduler will send data to "t-commodity" every specified interval.
- We will create dummy API to generate commodity list, and also create the scheduler.

### Now for the consumer

- We will have two consumer groups consuming from t-commodity, one is cg-dashboard and one is cg-notification.
- This diagram is nearly identical with what we have previously in theory.

![alt text](https://github.com/marodrigues20/udemy_java/blob/main/JavaSpring%26ApacheKafkaBootcamp-BasicToComplete/Sections/Section-07/KafkaSchema.png?raw=true)



### Creating a topic

- $ kafka-topics.sh --bootstrap-server localhost:9092 --create --partition 1 --replication-factor 1 --topic t-commodity

### Creating the code

Project Reference: kafka-core-producer

- Java Classes Created:
  - Commodity.java
  - service/CommodityService.java
  - api/CommodityApi.java
  - producer/CommodityProducer.java
  - scheduler/CommodityScheduler.java

- Dependencies Added:
  ```
  implmentation("org.springframework.boot:spring-boot-starter-web")
  ```

### To Check that application is sending data to kafka topic

- $ kafka-console-consumer.sh --bootstrap-server localhost:9092 --offset earliest --partition 0 --topic t-commodity



## 35. Consuming with Consumer Groups - Creating Consumer

Project Reference: kafka-core-consumer

Classes added:
  - CommodityDashboardConsumer.java
  - CommodityNotificationConsumer.java
  - Commodity.java


### Test Consumer Group via Cli Command

- Below command let's us see the cg-dashboard works on topic t-commodity
  
  $ kafka-consumer-groups.sh --bootstrap-server localhost:9092 --group cg-dashboard --describe

  - We have many columns produced by this above command:
    - GROUP
    - TOPIC
    - PARTITION
    - CURRENT-OFFSET
    - LOG-END-OFFSET
    - LAG -> Messages not processed by the group yet.
    - CONSUMER-ID
    - HOST
    - CLIENT-ID

### How to reset off-set via command line

  - This command will reset the off-set to 10 in t-commodity topic and partition 0.
  $ kafka-consumer-groups.sh --bootstrap-server localhost:9092 --group cg-dashboard --execute --reset-offsets --to-offset 10 --topic t-commodity:0

## 36. Rebalancing

Project Reference: kafka-core-producer
Classes created:
  - RebalanceProducer.java
  - Note: Enable in the "KafkaCoreProducerApplication.java" @EnableScheduling

Project Reference: kafka-core-consumer
Classes created:
  - RebalanceConsumer.java

- On previous lecture about multi consumer, when I add partition to topic, I restart the kafka producer and consumer. 
- If I did not restart the producer and consumer, kafka will need some times to recognize the new partition. This process is called as rebalancing. Let’s try it.
- Add a new kafka topic "t-rebalance" with one partition. 
- OK, topic is created.
- Go to producer project and create a "RebalanceProducer" under package "producer".
- This will send some string into t-rebalance every specified one second.
- Annotate the class as service.
- We need KafkaTemplate and a counter.
- Create a method that increment a counter every second and send the counter value into t-rebalance.
- Mark the method as @Scheduled with interval of one second.
- Now, go to KafkaProducerApplication and make sure that @EnableScheduling is enabled.
- Don’t start the producer yet, we will create the consumer.
- On consumer, create a new class "RebalanceConsumer" under package "consumer".
- Mark the class as @Service.
- We need a logger.
- Create a listener method, using ConsumerRecord as parameter, since we will need to see partition information.
- I will log several items : partition, offset, and message content.
- Annotate this method to listen from t-rebalance, using 3 concurrency.
- At this point, 2 concurrent listener will be idle since we only have one partition.
- That’s ok.
- Now add second partition to t-rebalance.
- Before we add partition, make sure consumer and producer are running.
- Open kafka command and execute this to alter t-rebalance into two partitions.
- Check again, now it has two partitions.
- When you done executing the script, go back to eclipse logs.
- Here, we can see that consumer still take messages only from partition 0.
- The counter is in correct sequence, incremented by one.
- So basically, no messages go to partition one, which is the new partition.
- This is the default behaviour of kafka, we will wait for about few minutes and see what happened later.
- Don’t turn off producer or consumer.
- OK, at this point, I’ve stopped the consumer and producer so we can examine logs.
- Since we schedule producer to publish every second, we can examine the time based on logs or based on counter, Notice that around 5 minutes after
  started, the consumer project will have logs regarding coordinator.
- And then there is a log that indicates "partitions assigned" on t-rebalance.
- Also, producer starts sending message to partition one after around 5 minute.


### Command to add a new Kafka Topic t-rebalance

  $ kafka-topics.sh --bootstrap-server localhost:9092 --create --partitions 1 --replication-factor 1 --topic t-rebalance

### Command to add one more partition

  $ docker-compose exec broker kafka-topics --alter --topic t-rebalance --partitions 2 --bootstrap-server broker:9092


## 37. Kafka Configuration

Project Reference: kafka-core-producer
Classed added:
  - KafkaConfig.java

Project Reference: kafka-core-consumer
Classed added:
  - KafkaConfig.java

- Run the Application (Consumer and Producer)
- Add one more partition
- $ docker-compose exec broker kafka-topics --alter --topic t-rebalance --partitions 3 --bootstrap-server broker:9092

### Result of implementation

  - After 3 minutes the producer will start send message to partition 3
  - After 2 minutes the consumer will start consume from partition 3
  

## 38. Message Filter

- Filtering Messages
  - Match criteria: processed
  - Not match criteria: not processed but still on topic
  - Filter for each listener


### Scenario

- In this lecture, we will use this scenario.
- We will have fleet of cars that constantly send distance to kafka.
- We will have topic t-location for storing location of car.
- The message body will contains car ID, timestamp, and current car distance from base.
- We will simulate three cars running and send distance every few moments.
- We will create two listeners: One listener will process all messages, while the other will filter message and process only cars with location more 
  than 100. Let’s start.


![alt text](https://github.com/marodrigues20/udemy_java/blob/main/JavaSpring%26ApacheKafkaBootcamp-BasicToComplete/Sections/Section-07/message_filter_1.png?raw=true)


### Producer Explained

Project Reference: kafka-core-producer
Classes Added:
    - CarLocation.java
    - CarLocationProducer.java
    - CarLocationScheduler.java


- For the producer itself, we will create a simulation of three cars, sending location data to kafka every ten seconds.
- For simplicity, we will use this scenario:
  - First car is start from 0 kilometers and increasing by 1 kilometer every ten second,
  - Second car start from 110 kilometers, decreasing by 1 every ten second,
  - Third car start from 95 kilometers, increasing by 1 every ten second. 
  - First listener will displays all location data.
  - Second listener will filter data, displaying only data with distance more than 100.


![alt text](https://github.com/marodrigues20/udemy_java/blob/main/JavaSpring%26ApacheKafkaBootcamp-BasicToComplete/Sections/Section-07/message_filter_2.png?raw=true)


### Consumer Explained

Project Reference: kafka-core-consumer
Classes Added/Modified:
  - CarLocationConsumer.java
  - KafkaConfig.java


## 39. Idempotency - Handle Duplicate Message

### In the earlier section we talked about Delivery Semantic. When our Delivery Semantic is "At-Least-Once".

  - Message guaranteed to be published
  - Message might be published more than once

### There are several possibilities for this. First the Consumer is Idempotent.

  - Duplicate message is OK:
    - Outcome of processing message always be the same even for duplicate messages.
    - Example: update search engine index.
  - Duplicate message is dangerous:
    - Duplicate transaction
    - Example: create (duplicate) payment
    - Filter out duplicate message(s)

### Gray Area

  - It might be dangerous, or not
  - Example: send promotion email to user
  - Can either OK, or bad user experience
  - Techinical should filter out duplicate messages
  - Double output is not idempotent

### How to Deduplicate?

  - Unique value attached to each message
  - Consumer check unique value when receive record
    - Never processed -> store unique value, then process message
    - Has been processed > skip message
  - Use database for permanent unique value
  - Use cache for temporary unique value
  - Cache (normally perform better than database)
    - Better performance
    - Automatically remove data after certain time
    - Example: Redis
  - Database
    - Might publish duplicate after longer period
  - This lesson uses cache


### Project Reference

- name: kafka-core-producer
- Classes Added/Modified:
  - PurchaseRequest.java
  - PurchaseRequestProducer.java
  - KafkaCoreProducerApplication.java

### One Question

- when publishing, why use purchase request number, and not ID, as message key?
- This is depends on the use case,but sometimes, a primary key might not fit in scenario.
- For example, if the id represents event ID attached to certain purchase request.
- While purchase request number is something that human readable.
- Assume when user click button “Submit purchase request” for PR-one, it actually has 3 events to be processed in sequence reserving financial budget, trigger approval workflow, and push notification to the approver.
- This has to be in order. On this table, you can see that the event id is different, and purchase request number is the same, since user actually only click once.
- In this case, using purchase request ID, or the primary key event ID, can cause problem.
- Suppose that the kafka topic has multiple partitions, which usually happened on production.
- We learn that kafka will hash the key and send the message to partition, based on the hashed value.
- So it can be like this when we use purchase request ID.
- Event 1 and event 2 go to partition 0, while event 3 go to partition 1.
- Assume we have one consumer for each partition.
- If the consumer need one second to process each record, this can happen.
- Event 1 or event 3 will be processed and finished before event 2, since event 1 and event 3 is on lower offset.
- But if we use purchase request number as the key, all events will go to same partition.
- Then the consumer will process the messages in correct order.
- If you remember, this is guaranteed by kafka: message will be processed in same sequence they arrived in same partition.
- On kafka core producer application, autowire purchase request producer.
- Create 3 purchase request, with different id.
- Send all three to kafka.
- And, pretend that there is something wrong, so the producer accidentally publish same message for first purchase request.


![alt text](https://github.com/marodrigues20/udemy_java/blob/main/JavaSpring%26ApacheKafkaBootcamp-BasicToComplete/Sections/Section-07/OneQuestion.png?raw=true)


### Project Reference

Project Reference: kafka-core-consumer
Classes Added/Modified:
  - CacheConfig.java
  - PurchaseRequest.java
  - PurchaseRequestConsumer.java
  -  build.gradle.kts

Noted: Added val caffeineVersion = "3.0.6"
  

## 40. Idempotency Alternative

- Unfortunately, in many cases, a unique value is not exists on the message.
- So how can we filter the duplicate message? One alternative is by put the object itself as unique value. Serialize the object and put it as cache or database key.

### No Unique Value

- No unique value on message
- Alternative: use object as unique value
  - Bad idea, if the object contains large data
  - Eat up canche memory / slow database
- Derive key from combination of fields
  - Combination must be unique
- Don't use java hashCode()
  - Not guaranteed to be unique for different object


### Project Referece

- kafka-core-producer
- Classes added / modified
  - PaymentRequest.java
  - PaymentRequestProducer.java


- kafka-core-consumer
- Classes added / modified
  - PaymentRequest.java
  - PaymentRequestCacheKey.java
  - CacheConfig.java
  - PaymentRequestConsumer.java

 