# Section 06 - Processing Kafka Stream

 ## 17. Real-Time Stream Processing - Requirement

 - In the previous Sections, we learned to produce a stream of events and bring it to the Kafka Topic. 
 - Not it's time to learn to process the stream in real-time.

 Requirements:

 - 1. Select Invoices where DeliveryType = "HOME-DELIVERY" and push them to the shipment service queue.

 Diagram:
    post-topic --> Listen --> Filter --> shipment-topic

- 2. Select Invoices where CustomerType = "PRIME" and create a notificaiton event for the Loyalty Managment Service

Diagram:

    post-topic --> Listen --> Filter --> Transform --> loyalty-topic

- 3. Select all invoices, mask the personal information, and create records for Trend Analytics. When the records are ready, persist them to Hadoop storage
for batch analytics.

Diagram:

    post-topic --> Listen --> mask (Transform) --> flatten (Transform) --> hadoop-sink-topic

### Implementations:

There are some way to implement the business requirement:

- JSON -> JSON
- JSON -> AVRO
- AVRO -> JSON
- AVRO -> AVRO

However, we just going to implement:

- JSON -> AVRO
- AVRO -> JSON

There are two videos showing the implementation:

- 1. The first implementation of Business Requirement will read a JSON invoice and producing an AVRO output.
     i.e: jsonposfanout

- 2. The second implementation of Business Requirement will read an AVRO invoice and producing JSON output.

### SERDES
    - In this project, we will be using Kafka Streams API. We wont' be using Kafka Producer API.
    - The Kafka Streams API does not need a serializer. Instead, the Stream will be using a serde.
    - So, We add the kafka-stream-avro-serde

## 18. Processing JSON Message Stream

i.e: jsonposfanout -> More details in readme.md file inside the Java Project.

## 19. Real-life Serialization Scenarios

- In a typical real-life project, you might be getting data from a bunch of sources.

- Let's assume you have a commercial portal. The portal generates some online transactions. You are also sending these transactions to a Kafka Topic for some real-life streaming operations. However, it is a web-based applications, and your team found it a little convenient to send the events to Kafka in a JSON format. You already have the system in place. On the other side, you also have some enterprise applications generating some transactions that go into Database. You wanted to bring those transactions also to a Kafka topic for performing some real-time streaming operations. But these are packaged enterprise
applications, and you cannot modify them to send data to Kafka. But fortunately, the applications offers you a Kafka connect connector.
- So you decide to implement Kafka Connect to bring data from these applications to your Kafka topic. Similarly, you may have other source that are generating real-time stream data, and you manage to bring them to a Kafka topic. You might have many stream processing applications processing these streams, and again generating inputs to the Kafka topic for other applications.
- So in a large enterprise, you will see a mesh of streaming events coming to a Kafka and going out of Kafka topics.
- Now, if you analyse these incoming events, you might find two broad categories fo input formats:
    - First type of inputs are created using some non-kafka techonologies. These inputs are more often JSON formated events, and they are not likely to use 
    a schema registry.
    - The second type of events are created using native Kafka techonologies. These inputs are often AVRO-formated events, and they will be using a confluent schema registry.
- And this is why We are playing around with the serialization formats and covering these two broad categories.




## 20. Processing AVRO message Stream

- This project receive AVRO message and send out JSON message.

- Project Java built as example: avroposfanout


- This example will be reading Invoice data from a Kafka Topic and producing Notification and HaddopRecord to some outgoing Kafka Topics.

    - Creating this example goes into four steps:
        - 1. Data Model
        - 2. Data Transformation Services
        - 3. Kafka Listners
        - 5. Kafka Channels and Streams Binder
    
    - Create AVRO friendly classes
        - 1. Create AVRO schema definition
        - 2. Include AVRO dependency
        - 3. Include Maven AVRO plugin
        - 4. Compile your project using Maven

- This project send outgoing JSON messages 
    - 1. Notification
    - 2. HadoopRecord

Note: We don't need to care about serialization/deserialization for JSON. This is default for Spring Cloud.
      So Spring Cloud was deserializing it correctly using the JSON serialization package.

Note 2: We do not need to specify a serialization package name for JSON input or JSON output in a typical case. In that case, Spring Cloud will be using a build-in default JSON serialization package. However, in this example, we wanted to use the confluent provided JSON serialization packages. 
They are not part of the Spring distribution, so we must include them in our pom.xml file.


## 21. Understanding Record Serialization


- Objective: Give a complete overview of the record serialization in Spring Cloud and Kafka Stream
    - Stream processing is all about data processing in real-time. These data events will come to a Kafka topic as a continuous stream of messages.
    - When the message is in transit over the network, it must be in a byte-array format. However, you and your listeners want to see those messages as a 
        Java Object.
    - The framework takes the byte-array and converts it to a PosInvoice object before passing it to the listener function. This work is deserialization.
        So, converting a byte-array to a Java object is known as deserialization.
    - When you send the java object to a Kafka Topic. The framework will take the outgoing object, convert it to a byte array and send it to the Kafka topic.
    - This work is known as serialization.
    - Both of these process are required because the Java Object are sent over the wire across the network.

### How the Serialization and Deserialization happen

1. Spring Cloud Stream
2. Kafka Stream (which is available as a Kafka Stream Binder)

- Both of the frameworks above are capable of doing the serialization and deserialization work. It is up to you decide who you want to use.
- You can configure your choice using the following properties.
    - Deserialization "spring.cloud.stream.bindings.your-input-channel-name.nativeDecoding:true"
    - Serialization   "spring.cloud.stream.bindings.your-output-channel-name.nativeEncoding:true"

- If you enable native enconding/deconding, then the serialization work is taken care of by the Kafka Stream framework.
- If you disable native enconding/deconding, then the serialization is taken care of by Spring Cloud Stream.
- By default, it is true. So the serialization work is performed by the Kafka Stream framework. That's is recomended.

- In this course, we are using default settings and let the Kafka Streams perform all the serialization work. However, in some older versions of Spring Cloud Stream, is settings defaults to false. So, if you are using an older version of Spring Cloud Stream, you must set these Native Enconding/Deconding properties to false. To use Kafka Stream serialization and deserialization. That's recommended.

### How the things happen in Kafka Stream

- Kafka Streams framework comes with some built-in classes to convert byte-array to the native Java Objects and types.
- These classes are known as Serde. Serde - Serialization/Deserialization
- We have some built-in Serde classes.
    - org.apache.kafka.common.serialization.Serdes
        - StringSerde
        - IntegerSerde
        - LongSerce
        - ShortSerde
        - ByteSerde
        - DoubleSerde
        - FloatSerde
        - ByteArraySerde
- All these Serde classes are available in common.serialization.Serdes package.
- Kafka Streams, already known how to translate simple value like String, Integer, or Double to a wire format.
    - These are standard Java Type, so Kafka framework comes with built-in support for these classes. 
- The problem comes when you are using complex and custom Java Objects.
    - For example, in our projects we use PosInvoice, Notification, HadoopRecord custom objects.
    - Kafka Stream does not know how to serialiaze or deserialize the above java classes.
    - Why? Because the Kafka Streams framework does not have a Serde class for these objects.
    - We can fix this problem by creating a custom Serde class for these objects. However, create a Serde class is an option, but Confluent Kafka
    offers you a straighforward method. They offered some standard Serde classes such as SpecificAvroSerde and KafkaJsonSchemaSerde.
    - We can use Confluent Kafak, Serde classes to serialize or deserialize any Java Object.
    - If you want to use SpecificAvroSerde, then you must to create an Avro-friendly class.
    - If your class definition is Json-friendly, you can use KafkaJsonSchemaSerde.
    - You must provide a Serde to Kafka Streams for performing the serialization.
    - You have two options:
        - Define a custom Serde for your data model
        - Or create Avro-friendly or JSON-friendly data model and use one of the standard Serde offered by Confluent Kafka
        - We recommended to prefer using standard Serde and avoid creating your custom Serde.
        - Create and maintaining custom Serdes could be a headache in the long term.

### Understanding some configuration about Serdes

- When Kafka Stream try to deserializer something. He tries to check that the object has in its built-in classes
- If Kafka Stream does not have it. It will try to find a class to be used to deserializer or serializer the object in application.yaml file.
- If Kafka Stream can't find the class. It will check in Spring Framework.

Like this other:

Custom Serde in Class Path --> Standard Serde Classes --> Application Configuration --> JsonSerde by Spring

## 22. KStream Methods

Objective: Overview about KStream Methods

- Kafka Stream API offers you two main abstractions to represent your real-time streams.
    1. KStream 
    2. KTable 

- KStream represent a record stream of Key/Value pairs
- You can look at it like this. KStream<K, V>

- When you create a Kafka Stream Listener, you can design your processor() methods to receive a KStream object, and you can also design it to return a KStream object.
- All KStream functions are designed to work using a functional programming approach.
- Most of the KStream functions  will accept a lambda function as an argument

- You can get the full list KStream of methods on here: http://kafka.apache.org
    - Go to Docks link
    - Go to APIS
    - Click on APIS item.
    - Looking for javadoc on the webpage loaded.
    - Site will open the JavaDoc web page.
    - Select "org.apache.kafka.stream.kstream"
    - Select "KStream" interface.
    - You will see all the methods about KStream.

### Sumarize list of KStream methods

    1. Filter & FilterNot               --> Filter out some records for the KStream
    
    2. Map & FlatMap                    --> Map to transform the key and value to KStream
    3. MapValues & FlatMapValues        --> MapValues just to tranform the value to KStream and leaving the key unchanged
                                        --> FlatMap and FlatMapValues: they receive one msg and return a list of msg.

    4. ForEach and Peek                 --> ForEach doesn't return anything. Peek Method return back the same KStream
    5. Print                            --> Print just used for debuging propose. Avoid use it in Production code.
    6. Branch & Merge                   --> Branch allows you to split one Stream into two Streams using some conditions.
    7. To                               --> Method responsible for sending message to a Kafka topic. It is the most essential methods in specific scenario. 
    8. ToTable                          --> Convert KStream to KTable    

    9. Repartition                      --> Skipping to comming lectures
    10. SelectKey                       --> Skipping to comming lectures
    11. GroupBy & GroupByKey            --> Skipping to comming lectures
    12. Join                            --> Skipping to comming lectures

    // Low Level API. Used to integrate Kafka Stream Processor APIs with Streams DSL. This course is primary focusing on Stream DSL, but 
    // it will conver some things about Processor API.
    13. Transform $ FlatTransform                
    14. TransformValues $ FlatTransformValues
    15. Process






