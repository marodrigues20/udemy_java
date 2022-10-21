# How to Test This Application Using Confluent Kafka Docker

### Download and Run Kafka Confluent
- https://docs.confluent.io/platform/current/platform-quickstart.html#cp-quickstart-step-1


- curl --silent --output docker-compose.yml \
  https://raw.githubusercontent.com/confluentinc/cp-all-in-one/7.2.2-post/cp-all-in-one/docker-compose.yml

- Launch your Confluent platform using the following command:
    - $ docker-compose up -d

- Create the Kafka Topic
    - $ docker-compose exec broker kafka-topics --create --topic orders --bootstrap-server broker:9092

- Start a Kafka Console Consumer
    - $ docker-compose exec broker bash
    - Now, enter the following command, within this new terminal to start the Kafka Console Consumer:
        - $ kafka-console-consumer --topic orders --bootstrap-server broker:9092 --from-beginning

- Produce your Records using Kafka Console Producer
    - $ docker-compose exec broker bash
        - In the new terminal that opens, enter the following command to run your Kafka Console Producer:
            - kafka-console-producer --topic orders --bootstrap-server broker:9092


### Examples of messages to be used in the test

```
{"name": "Kristine Cole", "age": 34, "gender": "female"}
{"name": "Marsh Mccall", "age": 28, "gender": "male"}
```