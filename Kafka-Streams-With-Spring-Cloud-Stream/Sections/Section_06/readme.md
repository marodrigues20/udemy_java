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