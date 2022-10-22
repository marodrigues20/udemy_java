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

## 14. Creating Retail POS Simulator

- We going to learn. How to send a complex Java Object to Kafka Topic.


- Requirements:
    - I want to create a simulator application to simulate a POS terminal of retail store.
    - You have a point of sale terminal to create invoices
    - You are creating invoices from the POS terminal, and these invoices are sent to a Kafka topic.
    - On the other side, we will be developing some stream processing applications to analyze the stream of invoices.
    - We want to create a Spring Boot Application that can continuously generate some realistic invoices.
    - Your invoice object will have some standard fields such as InvoiceNumber, CreditTime, StoreID, and so on.
    - The delivery type of your invoice could be HOME-DELIVERY or TAKEAWAY. 
    - A home delivery invoice will also have an address, but the takeaway invoice will not have the address field.
    - The last part of the invoice is an array of line items.
    - You may have one line item or maybe more.


- How to solve this requirement:

    - We going to have two services
        - Invoice Generator.
        - Kafka Producer Service


     

## 15. Producing JSON Messages

- GitHub Project Created: jsonposgen

There is some good comments inside the code explaining good points. Have a look! :-)

## 16. Producing AVRO Messages

- In this section we want to recrate the same POS Generator example, but we want to use AVRO Serialization.

- In this kind of project we will use:
  - Producer Template Configuration
  - Data Model





