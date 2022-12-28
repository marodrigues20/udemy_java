# Section 08 - KTable and Aggregations

## 37. KTable Aggregation

- This project update the project reference: kstreamaggregate 
- This project fix the calculation of average salary for department using KTable.

### How to test this application

1. Run
```
$ mvn clean package
```

2. Start up Docker on your local machine
3. Download and Run Kafka Confluent. Use the link bellow:
``` 
https://docs.confluent.io/platform/current/platform-quickstart.html#cp-quickstart-step-1
```

4. Launch your Confluent platform using the following command:
```
$ docker-compose up -d
```

5. Create the Kafka Topic using the bellow command line or via http://localhost:9091
```
$ docker-compose exec broker kafka-topics --create --topic employees-topic --bootstrap-server broker:9092
```

6. Produce your Records using Kafka Console Producer
```
$ docker-compose exec schema-registry bash
```

7. In the new terminal that opens, enter the following command to run your Kafka Console Producer:

```
$ kafka-avro-console-producer --broker-list broker:29092 --topic employees-topic \
--property value.schema='{"namespace": "guru.learningjournal.examples.kafka.model","type": "record","name": "Employee","fields": [{"name": "id","type": "string"},{"name": "name","type": "string"},{"name": "department","type": "string"},{"name": "salary","type":"int"}]}'
```

8. Publish the messages in samples.txt under practice-resources folder.

9. Start this Java Application (kstreamaggregate)
10. Check the output console.

Note: This application doesn't publish any message to any topic.