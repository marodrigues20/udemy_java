# Section 6: Confluent Schema Registry and Kafka

## 33. Confluent Schema Registry

    - Now that  we've seen about how to create Avro Schemas, was if there was a central place to store and retrieve them?

    - That's the Confluent Schema Registry

    - It's an open source project maintained by Confluent (private company behind Kafka) and it's been around for many years.

    - We will dedicate this whole section to using it.

### Confluent Schema Registry Purpose

    - Store and retrieve schemas for Producers / Consumers
    - Enforce Backward / Forward / Full compatibility on topics
    - Decrease the size of the payload of data sent to Kafka


### Confluent Schema Registry Operations

    - We can add schemas 
    - We can retrieve a schema
    - We can update a schema
    - We can delete a schema (as of 3.3.0)
    - All of this through a REST API

    - Schemas can be applied to key and / or values!


### Confluent Schema Registry First Touch

    - Let's go in the schema registry UI by Landoop and view existing schemas

    - Let's also go ahead and register a new schema for a topic

    Note: Up the docker compose in /code/2-start-kafka/docker-compose.yml and open localhost:3030


## 34. Kafka Avro Console Producer & Consumer

    - The Avro Console Producer allows us to quickly send data to Kafka manually by specifying the schema as an argument.

    - The binaries come with the Confluent Distribution of Kafka (accessible through Docker or accessible through the Confluent Binaries)

    - Let's learn how to use the Kafka Avro Console Producer & Consumer

    Note: Commands in /code/3-schema-registry/1-kafka-avro-console-producer


## 35. Writing a Kafka Avro Producer in Java

    - Now that we have a feeling for the console producer and consumer (they're very useful for debugging!), let's do something in Java!

    - This is how you will write your consumer and producer for your applications that will go in production

    - We will leverage all the knowledge we've had from before, using the SpecificRecord as our way to create Avro objects. 

    i.e: KafkaAvroJavaProducerV1Demo.java

## 37. Reminder on Schema Evolution

    - Let's go through a code demo of schema evolution and see how we go!

    - We'll see how Avro handles schema evolution seamlessly for us.

        Write with Old Schema (V1) ------------> Read with New Schema (V2) - Backward compatible change

        Write with New Schema (V2) ------------> Read with Old Schema (V1) - Forward compatible change

        i.e: KafkaAvroJavaConsumerV1Demo.java


## 38. Writing a V2 producer

    - Let's write a Producer V2 that sends data to Kafka using a forward compatible schema.

    - We'll see how the Consumer V I still is able to read data from Kafka.

    i.e: KafkaAvroJavaProducerV2Demo.java


## 39. Writing a V2 Kafka Consumer

    - Let's write a V2 consumer that will read data from Kafka with the v1 schema.

    - The code is exactly the same, only the schema changes!

    i.e: KafkaAvroJavaConsumerV2Demo.java

## 40. Summary on Compatibility Changes

    - Two patterns 

    - Write a forward compatible change (very common)
        - update your producer to V2, you won't break your consumers
        - take your time to update your consumers to V2

    - Write a backward compatible change (less common)
        - update all consumers to V2, you will still be able to read v1 producer data
        - when all are updated, update producer to V2


## 41. Kafka Schema Registry Deep Dive

### What actually happens with the schema registry? (1/3)

    - Fresh reminder


### What actually happens with the schema registry? (2/3)

    Avro Bytes contains ->  | Avro Schema | Avro Content |


    Kafka Avro Serializer
        - 1. Register schema "Avro Schema" if not registered already Get ID ( Schema Registry + schema ID (4 bytes) )

        - 2. Avro Content - Prepend Magic Byte (version)
        - 3. Avro Content - Prepend Schema ID (version)
        - 4 Send to Kafka

### What actually happens with the schema registry? (3/3)

    - Deserializer does the inverse ooperation of Serializer

    - We now better understand how the schema registry works
        - It externalises the schema (the schema doesn't live in Kafka)
        - It reduces the message size by a lot as the schema is never sent
        - If the schema registry is not available, this could break producers / consumers
    
    - Always write fully compatible schema evolutions.

    - The schema registry becomes a critical component in your infrastructure


## 42. Managing Schemas Efficiently & Section Summary

    - Create a repository that holds your schema and generate your SpecificRecord classes. Publish that schema using CICD once deemed valid and 
    compatible with the current schemas.

### Managing Schemas efficiently (2/2)

    - In your project, references the published classes for your schema using maven for example.
    - And write your normal producer / consumer

    - Always aim for FULL compatibility

    








    