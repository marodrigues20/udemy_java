# Section 16. Kafka Stream - Flash Sale Vote

## 94. Most Recent Data Feed

- For marketing program, we will give customer list of item as flash sale candidate.
- Customer can vote from this list candidate during certain time range. The next flash sale will be held based on most voted item. One customer can only select one candidate, but they can change selection as long as still in time range.
- Since one customer can only select one flash sale candidate, but can change her choice, means we have to track latest selected item. This is a good candidate for Kafka Stream Table.

![alt text](https://github.com/marodrigues20/udemy_java/blob/main/JavaSpring%26ApacheKafkaBootcamp-BasicToComplete/Sections/Section-16/pic_01.png?raw=true)

- So we will publish record to kafka, using customer ID as key, and flash sale candidate as value. On first choice, we will insert the record. If customer change his mind, he can submit again.
- This means we will update the data on kafka table. In other words, we will do upsert.

## Stream or Table?

- Track latest selected item per customer
- Kafka Stream table
- Record
  - Key: Customer ID
  - Value: Flash sale candidate


- We will not create user interface, just API and producer. The flash sale vote will have attributes:
  - customerId and item name. It will publish to t-commodity-flashsale-vote.

## Source Code for Flash Sale Vote

Note: Download the code in the last section of the code from kafka-ms-order and copy the classes to your project. Don't forget to copy the message class to 
kafka-order and kafka-stream.

- FlashSaleVote*.java
- Package com.course.kafka
  - api.request
  - api.server
  - broker.message
  - broker.producer
  - command.action
  - command.service


## High Level Topology

- On sink processor, we will group the data by item name. We will see later when customer update his choice, it will affect candidate count.


![alt text](https://github.com/marodrigues20/udemy_java/blob/main/JavaSpring%26ApacheKafkaBootcamp-BasicToComplete/Sections/Section-16/pic_02.png?raw=true)


## Processing The Message

![alt text](https://github.com/marodrigues20/udemy_java/blob/main/JavaSpring%26ApacheKafkaBootcamp-BasicToComplete/Sections/Section-16/pic_03.png?raw=true)

- For this requirement, we only need to focus on two attributes on flash sale vote:
  - customer ID, which is the key, and item name. Since one customer can update his vote for more than once, we need to convert this original message into this format,
    where the key is customer ID and the value is item name. So every data will be treated as upsert to Kafka Stream Table.


## How Vote Works?


![alt text](https://github.com/marodrigues20/udemy_java/blob/main/JavaSpring%26ApacheKafkaBootcamp-BasicToComplete/Sections/Section-16/pic_04.png?raw=true)

- if we have X voter, the total vote count must be X, but the number can be distributed according to choice, which can be updated. For example, if the flash sale item is “cookies” and “cake”. The left side is the message on kafka topic, and the right side is the vote result.
- Anna choose cookies. So we have one cookies vote. Then, Olaf also choose cookies, so we have two cookies vote. But then Olaf change his mind and choose cake.
- This cake choice is followed by Anna, so we have another update.
- Then Elsa votes cookies, this means an insert since Elsa is new voter.
- So this is how upserts works.
  

