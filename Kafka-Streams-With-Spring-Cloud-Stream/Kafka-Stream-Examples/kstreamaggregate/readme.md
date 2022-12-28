# Section 08 - KTable and Aggregations

## 35. Aggregating a Kafka Stream

- This Application will be reading employee records and producing department aggregates

### Steps to create the application

1. Create a Data Model
2. Create Your business logic
3. Define your Input-Output Channels
4. Create your Binding Interface
5. Create your Listener Method

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
$ docker-compose exec broker bash
```

7. In the new terminal that opens, enter the following command to run your Kafka Console Producer:

```
kafka-avro-console-producer --broker-list localhost:9092 --topic employees-topic \
--property value.schema='{"namespace": "guru.learningjournal.examples.kafka.model","type": "record","name": "Employee","fields": [{"name": "id","type": "string"},{"name": "name","type": "string"},{"name": "department","type": "string"},{"name": "salary","type":"int"}]}'
```