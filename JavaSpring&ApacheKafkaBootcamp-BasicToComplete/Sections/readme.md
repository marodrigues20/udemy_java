# Java Spring & Apache Kafka Bootcamp - Basic to Complete

## Summary

- [Section 1 - Introduction](https://github.com/marodrigues20/udemy_java/tree/main/JavaSpring%26ApacheKafkaBootcamp-BasicToComplete/Sections/Section-01)
- [Section 2 - Spring Boot Version](https://github.com/marodrigues20/udemy_java/tree/main/JavaSpring%26ApacheKafkaBootcamp-BasicToComplete/Sections/Section-02)
- [Section 3 - Technology in This Course](https://github.com/marodrigues20/udemy_java/tree/main/JavaSpring%26ApacheKafkaBootcamp-BasicToComplete/Sections/Section-03#section-3---technology-in-this-course)
- [Section 4 - Instalation](https://github.com/marodrigues20/udemy_java/tree/main/JavaSpring%26ApacheKafkaBootcamp-BasicToComplete/Sections/Section-04#section-4-instalation)
- [Section 5 - Kafka Basic Concepts](https://github.com/marodrigues20/udemy_java/tree/main/JavaSpring%26ApacheKafkaBootcamp-BasicToComplete/Sections/Section-05#section-5-kafka-basic-concepts)
- [Section 6 - Start Writing Codes](https://github.com/marodrigues20/udemy_java/tree/main/JavaSpring%26ApacheKafkaBootcamp-BasicToComplete/Sections/Section-06#section-6-start-writting-codes)
- [Section 7 - Working with JSON Messages](https://github.com/marodrigues20/udemy_java/tree/main/JavaSpring%26ApacheKafkaBootcamp-BasicToComplete/Sections/Section-07)
- [Section 8 - Handling Exception](https://github.com/marodrigues20/udemy_java/tree/main/JavaSpring%26ApacheKafkaBootcamp-BasicToComplete/Sections/Section-08)
- [Section 9 - Scheduling Consumer](https://github.com/marodrigues20/udemy_java/tree/main/JavaSpring%26ApacheKafkaBootcamp-BasicToComplete/Sections/Section-09)
- [Section 10: Rabbitmq vs Kafka](https://github.com/marodrigues20/udemy_java/tree/main/JavaSpring%26ApacheKafkaBootcamp-BasicToComplete/Sections/Section-10)
- [Section 11: Kafka in Microservice Architecture & Pattern](https://github.com/marodrigues20/udemy_java/tree/main/JavaSpring%26ApacheKafkaBootcamp-BasicToComplete/Sections/Section-11)
- [Section 12: Kafka Stream](https://github.com/marodrigues20/udemy_java/tree/main/JavaSpring%26ApacheKafkaBootcamp-BasicToComplete/Sections/Section-12)
- [Section 13: Kafka Stream - Commodity](https://github.com/marodrigues20/udemy_java/tree/main/JavaSpring%26ApacheKafkaBootcamp-BasicToComplete/Sections/Section-13)
- [Section 14: Feedback Stream](https://github.com/marodrigues20/udemy_java/tree/main/JavaSpring%26ApacheKafkaBootcamp-BasicToComplete/Sections/Section-14)
- [Section 15: Kafka Stream - Customer](https://github.com/marodrigues20/udemy_java/tree/main/JavaSpring%26ApacheKafkaBootcamp-BasicToComplete/Sections/Section-15)
- [Section 16. Kafka Stream - Flash Sale Vote](https://github.com/marodrigues20/udemy_java/tree/main/JavaSpring%26ApacheKafkaBootcamp-BasicToComplete/Sections/Section-16)
- [Section 17: Kafka Stream - Feedback Rating](https://github.com/marodrigues20/udemy_java/tree/main/JavaSpring%26ApacheKafkaBootcamp-BasicToComplete/Sections/Section-17)



## Instruction to Run the Code

- All those examples are being using Confluence Kafka compose file.

### Download and Run Kafka Confluent
- https://docs.confluent.io/platform/current/platform-quickstart.html#cp-quickstart-step-1


- curl --silent --output docker-compose.yml \
  https://raw.githubusercontent.com/confluentinc/cp-all-in-one/7.2.2-post/cp-all-in-one/docker-compose.yml


## Commands

- 1. https://hevodata.com/learn/kafka-console-producer/

- 2. Launch your Confluent platform using the following command: 
  - $ docker-compose up -d

- 3. Create the Kafka Topic
  - $ docker-compose exec broker kafka-topics --create --topic orders --bootstrap-server broker:9092

- 4. Start a Kafka Console Consumer
  - $ docker-compose exec broker bash
  - Now, enter the following command, within this new terminal to start the Kafka Console Consumer:
    - $ kafka-console-consumer --topic orders --bootstrap-server broker:9092

- 5. Produce your Records using Kafka Console Producer
  - $ docker-compose exec broker bash
    - In the new terminal that opens, enter the following command to run your Kafka Console Producer:
      - kafka-console-producer --topic orders --bootstrap-server broker:9092

- 6. Produce Records with Key-Value Pairs
  - $ kafka-console-producer --topic orders --bootstrap-server broker:9092 --property parse.key=true --property key.separator=":"

- 7. Start a Consumer to display Key-Value Pairs
  - kafka-console-consumer --topic orders --bootstrap-server broker:9092 --from-beginning --property print.key=true --property key.separator=":"

- 8. Produce Records using Avro Schema
  - $ docker-compose exec schema-registry bash
  - $ kafka-avro-console-producer --broker-list broker:29092 --topic employees-topic \
--property value.schema='{"namespace": "guru.learningjournal.examples.kafka.model","type": "record","name": "Employee","fields": [{"name": "id","type": "string"},{"name": "name","type": "string"},{"name": "department","type": "string"},{"name": "salary","type":"int"}]}'

- 9. Add Partitions
  - $ docker-compose exec broker kafka-topics --alter --topic t-rebalance --partitions 3 --bootstrap-server broker:9092

- 10. Consume Key and Value adding value deserializer
  - $ kafka-console-consumer --bootstrap-server broker:9092 --property print.key=true --property value.deserializer=org.apache.kafka.common.serialization.LongDeserializer --topic t-commodity-feedback-four-good-count --property key.separator":"