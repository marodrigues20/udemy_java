# Section 10 - Joins in Kafka Streams

## 42. Joins in Kafka Stream

- Joins are one of the most common and desirable features of any data system.
- You might be working with a batch system or a real-time system.
- In both cases, at some point, you would need to join two data sets.
- In a real-time stream processing framework offered by Apache Kafka, your datasets are abstracted into two categories.
  
  1. KStream
  2. KTable and GlobalKTable

- Hence, you would expect the capability to join a combination of these two types of datasets.
- I haven't talked about the GlobalKTable yet. But nothing to worry about. I will cover it when we want to use it for the first time.
- For now, you can think of it as variation of the KTable.
  
### Kafka Stream API Supports Following Join Operations

- Kafka Streams API support following join operations.

```
| Join Operation         | Result  |      Join Types           | Feature    |
| KStream - KStream      | KStream | Inner, Left, Right, Outer | Windowed   |
| KTable - KTable        | KTable  | Inner, Left, Right, Outer | Non-Window |
| KStream - KTable       | KStream | Inner, Left, Outer        | Non-Window |
| KStream - GlobalKTable | KStream | Inner, Left               | Non-Window |
```

- Before we learn the mechanics of these four types of joins, it is essential to refresh the folloing critical concepts. and preconditions for joining 
  two datasets in kafka.

  1. KStream/KTable must have a valid key.
  2. All topics of the join must have the same number of partitions.
  3. Data in topics must be co-partitioned. (All Applications that write to the input topics must have the same partitioning strategy, so that records with
     the same key are delivered to the same partition number)
  4. Co-partitioning is not mandatory for KStream-GlobalKTable joins.
  5. Non-Key based joins are allowed with KStream-GlobalKTable joins.
  6. KStream must be on the left side of the join. (In other words, swapping the orders as KTable-KStream, and KGlobalTable-KStream is not allowed)

### Refresh few critical things

- The join operation on two records will produce zero, one, or two new records depending upon the type of join operation.
- Kafka streams offer you the following join types.

    1. (Inner) Join
        - Inner Join operation will produce one record when there is a matching record on both sides.
        - This type of join does not procude a record when the keys are not matching.
        - Inner Joins are supported for all four types of join operations.
    2. Left (Outer) Join
        - The left-outer join will produce a record from the left table and matching records from the right table.
        - This type of join always produces a record from the left side even if there is no matching record on the right side.
        - A left join is also supported for all four join operations.
    3. Right (Outer) Join
        - A right-outer join is opposite to the left-outer join that it will always produce a record from the right-side even if there is no 
            matching record on the left side.
        - Kafka does not have a method for implementing right-outer join.
        - However, you can smoothly perform right-outer join by swapping left and right-side datasets.
        - This swapping is only allowed with KStream-KStream, and KTable-KTable joins.
    4. (Full) Outer Join
        - The Full-outer join results in a single record on the matching key.
        - If there is no matching record, a full-outer join will produce two records.
        - i.e., one from both sides.
        - Outer joins are not available with GlobalKTable

- In Summary Inner Joins and Left Outer Joins are fully supported.
- Right Outer joins are available through swapping objects when both sides are of the same type.
- Full outer joins are fully supported for all except GlobalKTable.

------------------------------------------------------------------------------------------------------------------------------------------------------------

## 43. KStream to KStream Joins

- KStream is an infinite, unbounded stream of records.
- So, if you try to join two endless streams, sooner or later, you will end up consuming all your resources and crash your system.
- Hence KStream-KStream joins are always windowed joins, and non-windowed join are not permitted.
- When joining two stream, you must specify a boundary condition using a JoinWindow.
- We ant to learn it by creating an example.
- Let's define a simple problem to learn the mechanics of implementing streaming joins.

### Defining the problem

- You want to pay bills for your Internet provider. Let's name it as iNet.
- iNet gives you an option to pay using your mobile wallet. Let's call it the mWallet.
- You log-into the iNet application and select the mWallet option to initiate the bill payment.
- The iNet would ask for your linked mobile number.
- Once you supply your cell number, the iNet would call the mWallet payment API.
- The mWallet payment API implement Apache Kafka based system, and for each payment request, it performs the following actions. 
- On receiving requests from iNet, The mWallet initiates a transaction and sends the transactions event to the Kafka cluster.

Here is a sample payment transaction event:

```
100001: {
    "TransactionID": "100001",
    "CreatedTime": 1550149860000",
    "TargetAccountID": "151837",
    "Amount": 3000,
    "OTP": 852960
}
```

- The mWallet will also send the transaction OTP to you.
- This OTP is valid for 5 minutes. 
- Finally, the mWallet would respond back to iNet with a transaction ID.
- Now, the iNet would wait for you to enter the OTP.
- When you supply the OTP, the iNet again calls the mWallet with the TransactionID and OTP.
- This second call would generate a new event, as shown here.

```
100001: {
    "TransactionID": "100001",
    "CreatedTime": 155015010000",
    "OTP": 852960
}
```

- The second event comes to mWallet and is pushed to Kafka.
- Now you need to write a streams-application that joins these two streams and validates the OTP.

Payment Request
```
| Request ID | Created Time            |  OTP   |
| 100001     | 2019-02-14T13:11:00.00Z | 852960 |
| 100002     | 2019-02-14T13:12:00.00Z | 931749 |
| 100003     | 2019-02-14T13:13:00.00Z | 591296 |
| 100004     | 2019-02-14T13:14:00.00Z | 283084 |
```

Payment Confirmation
```
| Request ID | Created Time            |  OTP   |
| 100001     | 2019-02-14T13:15:00.00Z | 852960 |
| 100002     | 2019-02-14T13:18:00.00Z | 931749 |
|            |                         |        |
| 100004     | 2019-02-14T13:14:00.00Z | 283086 |
```

- For the sake of simplicity, you can print the validation outcome on the console.
- Since the OTP is valid only for five minutes, this requirement makes an excellent use case for stream-stream join using a five-minute join window.


### Let's talk about Solution

- Java Reference Project: otpvalidation

