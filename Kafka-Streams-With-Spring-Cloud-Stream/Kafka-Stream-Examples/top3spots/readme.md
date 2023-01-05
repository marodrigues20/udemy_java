# Section 10: Joins in Kafka Streams

## 46. Implementing Complex Aggregation

## How to test this application

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
$ docker-compose exec broker kafka-topics --create --topic active-inventories --bootstrap-server broker:9092
$ docker-compose exec broker kafka-topics --create --topic ad-clicks --bootstrap-server broker:9092
```

6. Produce your Records using Kafka Console Producer
```
$ docker-compose exec broker bash
```

7. In the new terminal that opens, enter the following command to run your Kafka Console Producer:

```
$ docker-compose exec broker bash
$ kafka-console-producer --topic active-inventories --bootstrap-server broker:9092 --property parse.key=true --property key.separator=":"
```

8. Publish those messages into "active-inventories" topic

```
1001:{"InventoryID": "1001", "NewsType": "Sports"}
1002:{"InventoryID": "1002", "NewsType": "Politics"}
1003:{"InventoryID": "1003", "NewsType": "LocalNews"}
1004:{"InventoryID": "1004", "NewsType": "WorldNews"}
1005:{"InventoryID": "1005", "NewsType": "Health"}
1006:{"InventoryID": "1006", "NewsType": "Lifestyle"}
1007:{"InventoryID": "1007", "NewsType": "Literature"}
1008:{"InventoryID": "1008", "NewsType": "Education"}
1009:{"InventoryID": "1009", "NewsType": "Social"}
1010:{"InventoryID": "1010", "NewsType": "Business"}
```

9. Open a new terminal, enter the following command to run your Kafka Console Producer:
```
$ docker-compose exec broker bash
$ kafka-console-producer --topic ad-clicks --bootstrap-server broker:9092 --property parse.key=true --property key.separator=":"
```

10. Start this Java Application (top3spots)

11. Publish those messages into "ad-clicks" topic to simulate the clicks.

```
1001:{"InventoryID": "1001"}
1002:{"InventoryID": "1002"}
1003:{"InventoryID": "1003"}
1004:{"InventoryID": "1004"}
1004:{"InventoryID": "1004"}
1002:{"InventoryID": "1002"}
```




12. Check the output console.

Note: This application doesn't publish any message to any topic.