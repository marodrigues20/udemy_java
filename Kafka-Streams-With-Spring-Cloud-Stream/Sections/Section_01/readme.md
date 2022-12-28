# Section 01: Before you Begin

## 5. Creating your starter project

- Create a spring boot project via start.spring.io and add the follow dependencies:
    - Cloud Stream
    - Spring for Apache Kafka Stream
    - Lombok


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


