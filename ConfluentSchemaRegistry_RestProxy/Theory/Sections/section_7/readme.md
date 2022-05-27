# Section 7. Confluent REST Proxy 

## 74. Kafka REST Proxy Introduction and Purpose

### Introduction

    - Kafka is great for Java based consumers / producers, but sometimes clients are lacking for other languages

    - Although things are getting better

    - Additionally, sometimes Avro support for some languages isn't great, whereas JSON/HTTP requests are great.

    - For all these reasons, Confluent created the REST Proxy
    - It's an open source project created by Confluent.

### Confluent REST Proxy

    - It's integrated with the schema registry so that consumers and producers can easly read and write to Avro topics.

    - There's a performance hit to using HTTP instead of Kafka's native protocol and it's been estimated that the 
      throughput decrease is 3-4x

    - It is up to the producing application to batch events
    - The Confluent REST Proxy is already installed on our Docker Kafka Cluster!


### Confluent REST Proxy

    - In this section, we'll learn about

        - REST Proxy calls and versions
        - Topic operations
        - Producing & Consuming in Binary
        - Producing & Consuming in JSON
        - Producing & Consuming in Avro
        - Deployment and Scaling the REST Proxy

### V1 vs V2 API

    - Apache Kafka has the "old consumer" and "old producer" API that were valid until 0.8
    - After 0.8 Apache Kafka released the "new consumer" and "new producer" API (that's the one I teach in my course).
    - The REST Proxy has been around for a long time, and historically it was using the "old" API, named VI in the rest Proxy.
    - The REST Proxy now has support for the "new" API, named V2 in the REST Proxy.
    - Use the V2 API, Don't even think about it!

### Making a request to the REST Proxy

    - Content Type has to be specified in a Header (plus an Accept header)

    application/vnd.kafka[.embedded_format].[api_version]+[serialization_format]
                                    |           |               |--> Always json
                                    |           |------------------> Always v2
                                    |------------------------------> json, binary or avro


Example:
    Content-Type: application/vnd.kafka.avro.v2+json
    Accept: application/vnd.kafka.v2+json


## 45. Insomnia Setup (REST Client)

    - https://insomnia.rest/download

## 46. Topic Operations

    - Getting a list of topics (GET /topics)
    - Getting a specific topic (GET /topics/topic_name)
    - You can't create / configure a topic with the REST Proxy.
    - It has to be done outside of the REST Proxy.

## 47. Producing with the REST Proxy

    - You have 3 choices the REST Proxy to produce data:
        - Binary (raw bytes encoded with base64)
        - JSON (plain json)
        - Avro (JSON encoded)

    - When in doubt, always refer to the documentation:

        - https://docs.confluent.io/platform/current/kafka-rest/index.html
        - https://docs.confluent.io/platform/current/kafka-rest/index.html

    
### Producing in Binary with the Kafka REST Proxy

    - Binary data has to be base64 encoded before sending it off to Kafka.
    - Base64 is a smart way invented to safely transfer bytes over the internet, in a way that won't break protocols (only by using 64 different characteres)
    - This way, any binary array can be sent (image data, string with weird chacacters, etc).
    - There are many libraries for each language to base64 encode your binary data. You can learn about base64 here: https://en.wikipedia.org/wiki/Base64

    - Let's get hands on!

## 48. Consuming in Binary with the Kafka REST Proxy

### Consuming with the REST Proxy

    - To consumer with the REST Proxy, you first need to create a consumer in a specific consumer group.

    - Once you open a consumer, the REST Proxy returns a URL to directly hit in order to keep on consuming from the same REST Proxy
        instance.
    
    - If the REST Proxy shuts down, it will try to gracefull close the consumers

    - You can set auto.offset.reset (latest or earliest)
    - You can set auto.commit.enable (true or false)


### Consuming with the REST Proxy Binary

    - Steps are (for any data format):
        1. Create consumer
        2. Subscribe to a topic (or topic list)
        3. Get records
        4. Process records (your app)
        5. Commit offsets (once in a while)

    - Binary data will be read in base64
    - Let's get hands on to understand how to consuming using the REST Proxy


### Producing with the REST Proxy JSON

    - JSON data does not need to be transformed before being sent to the REST Proxy, as our POST request takes JSON as an input.
    - Any kind of valid JSON can be sent, there are no restrictions!
    - Each language has support for JSON so that's a very easy way to send semi structured data.

    - It is the same as before, except the header changes

### Consuming with the REST Proxy JSON

    - Steps are (for any data format):
        1. Create consumer
        2. Subscribe to a topic (or topic list)
        3. Get records
        4. Process records (your app)
        5. Commit offset (once in a while)

    - JSON data will get read as is
    - The steps are the exact same as before, only the output and header changes


## 51. Producing in Avro with the Kafka REST Proxy

    - The REST Proxy has primary support for Avro as it's directly connected to the Schema Registry
    - You send the schema in JSON (string field), and you send the Avro payload encoded in JSON.
    - After the first produce call, you can get a schema id to re-use in the next requests to make them smaller
    - The header changes as well.

## 52. Consuming in Avro with Kafka REST Proxy

    - Steps are (for any data format):
        1. Create consumer
        2. Subscribe to a topic (or topic list)
        3. Get records
        4. Process records (your app)
        5. Commit offsets (once in a while)

    - Avro data will get read JSON encoded (like with avro-tools)
    - The steps are the exact same as before, only the output and header changes








