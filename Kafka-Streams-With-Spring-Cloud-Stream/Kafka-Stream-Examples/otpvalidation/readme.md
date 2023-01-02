# Section 10 - Joins in Kafka Streams

## 43. KStream to KStream Joins

- Description of the problem check in the "SECTION"/section_10/readme.md

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
$ docker-compose exec broker kafka-topics --create --topic payment_request --bootstrap-server broker:9092
$ docker-compose exec broker kafka-topics --create --topic payment_confirmation --bootstrap-server broker:9092
```

6. Produce your Records using Kafka Console Producer
```
$ docker-compose exec broker bash
```

7. In the new terminal that opens, enter the following command to run your Kafka Console Producer:

```
$ docker-compose exec broker bash
$ kafka-console-producer --topic payment_request --bootstrap-server broker:9092 --property parse.key=true --property key.separator=":"
```

8. Publish those messages into "payment_request" topic

```
100001:{"TransactionID": "100001", "CreatedTime": 1550149860000, "SourceAccountID": "131100", "TargetAccountID": "151837", "Amount": 3000, "OTP": 852960}
100002:{"TransactionID": "100002", "CreatedTime": 1550149920000, "SourceAccountID": "131200", "TargetAccountID": "151837", "Amount": 2000, "OTP": 931749}
100003:{"TransactionID": "100003", "CreatedTime": 1550149980000, "SourceAccountID": "131300", "TargetAccountID": "151837", "Amount": 5000, "OTP": 591296}
100004:{"TransactionID": "100004", "CreatedTime": 1550150100000, "SourceAccountID": "131400", "TargetAccountID": "151837", "Amount": 1000, "OTP": 283084}
```

9. Open a new terminal, enter the following command to run your Kafka Console Producer:
```
$ docker-compose exec broker bash
$ kafka-console-producer --topic payment_confirmation --bootstrap-server broker:9092 --property parse.key=true --property key.separator=":"
```

10. Publish those messages into "payment_confirmation" topic

```

```


11. Start this Java Application (otpvalidation)

12. Check the output console.

Note: This application doesn't publish any message to any topic.