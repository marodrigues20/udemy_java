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

## Pre-Requisite

    1. Start kafka services by docker-compose
    2. Create kafka topics
    3. Start avroposgen java project
    4. 