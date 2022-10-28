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

- Reescrever perdi!!!!


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


