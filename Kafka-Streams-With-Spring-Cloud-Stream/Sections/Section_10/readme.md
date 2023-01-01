# Section 10 - Joins in Kafka Streams

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

