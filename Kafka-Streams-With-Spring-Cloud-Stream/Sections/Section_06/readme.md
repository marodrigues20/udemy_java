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



