# Section 05. Producing Data to Kafka

GitHub Project Developed: kafkaproducer

## 13. Simple RESTful Kafka Producer

- We want to create a Spring Boot microservices which exposes a RESTful post API. You should be able to access the REST API using the following URL.
- You can use this post API to send a JSON formatted data to the microservice.

This is the sample data:

```
http://localhost:8080/post
@Content-type: application/json
{"Topic": "users", "key":"101", "value":"Prashant Kumar Pendey"}
```

- The microservices will receive the the JSON object and send the key and value message to the given Kakfa topic.

- The Kafka Topic must already exist.
- We can programatically create the Kafka Topic, but that's not recommended for production systems.
- So we will create a Kafka Topic from the command line and send the JSON formatted message to the microservice.
- The microservices must send it further to the Kafka topic.


## Commands

- 1. https://hevodata.com/learn/kafka-console-producer/

- 2. Launch your Confluent platform using the following command:
    - $ docker-compose up -d

- 3. Create the Kafka Topic
    - $ docker-compose exec broker kafka-topics --create --topic users --bootstrap-server broker:9092

- 4. Start a Kafka Console Consumer
    - $ docker-compose exec broker bash
    - Now, enter the following command, within this new terminal to start the Kafka Console Consumer:
        - $ kafka-console-consumer --topic users --bootstrap-server broker:9092

- 5. Start a Consumer to display Key-Value Pairs
    - kafka-console-consumer --topic orders --bootstrap-server broker:9092 --from-beginning --property print.key=true --property key.separator=":"

- 6. Start the Microservice

- 7. Open the Postman and send the a post request or Curl Command

```
curl --location --request POST 'http://localhost:8080/post' \
--header 'Content-Type: application/json' \
--data-raw '{
    "topic": "users",
    "key": "101",
    "value": "Prashant Kumar Pendey"
}'
```

- You can see the Json message being consumed by consumer client.


     





