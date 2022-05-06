# Library Events Producer Micro-service

This micro-service is responsible for receiving two types of requests. One is for save a new book and the another 
one to update book information.



## How to Test a POST method to save a new book.
POST WITH-OUT STATUS
---------------------
curl -i \
-d '{"libraryEventId":null,"book":{"bookId":456,"bookName":"Kafka Using Spring Boot","bookAuthor":"Dilip"}}' \
-H "Content-Type: application/json" \
-X POST http://localhost:8080/v1/libraryevent

## Nice Plugin

Install Spring Boot Assistance

## Section 8: Build SpringBoot Kafka Producer - Hands On

### 26. How Spring Boot AutoConfiguration Works?

Spring Boot create the templates based on properties.properties / properties.yml files description. KafkaTemplate is
not different. All the factors responsible to create templates are in spring-boot-autoconfigure jar file.
Inside the spring.factories file we can see a list of Factories classes described in this file. In case of Kafka,
KafkaAutoConfiguration is the class responsible to create KafkaTemplate.

In KafkaAutoConfiguration there is @ConditionalOnClass(KafkaTemplate.class) annotations where it allows configuration 
to be skipped based on the presence or absence of specific classes on our class path. If the KafkaTemplate exist the
configuration will be enabled and evaluated to true.

There is another annotation @ConditionalOnMissingBean(KafkaTemplate.class). We are not configuration anything in our
classes specifically for KafkaTemplate. Basically, We didn't created any Bean explicitly in our application until now.
If we don't have any Bean created by us then @ConditionalOnMissingBean(KafkaTemplate.class) will be evaluated to false.
So in that case, the block is going to get executed and this block takes in three properties KafkaProducerFactory, 
KafkaProducerListener and messageConverter.

If you check the method that create the KafkaProducerFactory. You can see this.properties.buildProducerProperties() 
inside the method. The buildProducerProperties method is defined in KafkaProperties.java class where exists an
class level annotation @ConfigurationProperties(prefix = "spring.kafka"). That, matches with our definiation in 
properties.yml file. And inside the kafka property there is a producer properties where is defined inside
KafkaProperties.java class.


### 27. AutoCreate TOPIC using KafkaAdmin

The option used to create a topic in this video is not recommended for production.

Kafka has a Spring class called KafkaAdmin used to create a Topic programmatically. We have to create two Beans.

- KafkaAdmin
- NewTopic 

We add this properties as well in properties.yml file

admin:
properties:
bootstrap.servers: localhost:9092,localhost:9093,localhost:9094

To check if the topic has been created type:
- ./kafka-topics.sh --bootstrap-server localhost:9092 --list


### 28. Build LibraryEvents Producer using KafkaTemplate - Approach 1

Asynchronous call

When we use KafkaTemplate.sendDefault(K,V) we don't need to specify the topic value. Most Use Cases this method
is enough.

However, we have to add this properties in properties.yml file.

kafka:
template:
default-topic: library-events


The KafkaTemplate.sendDefault(K,V) return ListenableFuture interface. This is associated to RecordAccumulator more 
specific RecordBach. When the buffer is full send all request in one shot to Kafka Topic. Gives more performance.
Search more after.

i.e: LibraryEventProducer.sendLibraryEvent

### 31. Build LibraryEvents Producer using KafkaTemplate - Approach 2

Synchronous call

sendResult = kafkaTemplate.sendDefault(key, value).get();

i.e: LibraryEventProducer.sendLibraryEventSynchronous


### 32. Build LibraryEvents Producer using KafkaTemplate - Approach 2

        //ListenableFuture<SendResult<Integer, String>> listenableFuture = kafkaTemplate.send(topic, key, value); Use different topic from default defined in properties.yaml file
        ListenableFuture<SendResult<Integer, String>> listenableFuture = kafkaTemplate.send(producerRecord); // ProduceRecord has all information needed to send a msg to kafka topic.


### 34. Kafka Headers

In the Kafka Headers we don't have option to read the Headers in Kafka Console. To read we have to create a Kafka Java 

Consumer. That we show in upcoming lectures.

private ProducerRecord<Integer, String> buildProducerRecord(Integer key, String value, String topic) {

        List<Header> recordHeaders = List.of(new RecordHeader("event-source", "scanner".getBytes()));
        return new ProducerRecord<>(topic, null, key, value, recordHeaders);
    }



## Section 11. Building Spring Boot Kafka Producer - Sending Message With Key - Hands On

### 46. Create the PUT endpoint - "v1/libraryEvent"

If you want your message ordering you have to send the same key. Otherwise, the partition where Kafka will save your 
message will be in different partition. Before your message be saved there is a hashcode layer where will be used in 
each message.


