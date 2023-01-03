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


### Explanation of Result

- We submit some Payment Requests using the payment_request topic.

```
100001:{"TransactionID": "100001", "CreatedTime": 1550149860000, "SourceAccountID": "131100", "TargetAccountID": "151837", "Amount": 3000, "OTP": 852960}
100002:{"TransactionID": "100002", "CreatedTime": 1550149920000, "SourceAccountID": "131200", "TargetAccountID": "151837", "Amount": 2000, "OTP": 931749}
100003:{"TransactionID": "100003", "CreatedTime": 1550149980000, "SourceAccountID": "131300", "TargetAccountID": "151837", "Amount": 5000, "OTP": 591296}
100004:{"TransactionID": "100004", "CreatedTime": 1550150100000, "SourceAccountID": "131400", "TargetAccountID": "151837", "Amount": 1000, "OTP": 283084}
```

- The console print the following messages:

```
Request Key = 100001 Created Time = 2019-02-14T13:11Z
Request Key = 100002 Created Time = 2019-02-14T13:12Z
Request Key = 100003 Created Time = 2019-02-14T13:13Z
Request Key = 100004 Created Time = 2019-02-14T13:14Z
```

- All the messages are time stamped for Feb 14, 2019 with event time as 13:11, 13:12, 13:13, and 13:15.
- After sending these messages, the application does not show any outcome in the beginning.
- Because there are no matching confirmation records on the other topic yet.
- Let's throw some confirmation messages.

### Send message to payment_confirmatin topic

```
100001:{"TransactionID": "100001", "CreatedTime": 1550150100000, "OTP": 852960}
100002:{"TransactionID": "100002", "CreatedTime": 1550150280000, "OTP": 931749}
100004:{"TransactionID": "100004", "CreatedTime": 1550150040000, "OTP": 283086}
```

- All the confirmation messages are also timestamped for Feb 14, 2019 with events timestamps as 13:15, 13:18 and 13:14.

- Output:
```
Confirmation Key = 100001 Created Time = 2019-02-14T13:15Z
Confirmation Key = 100002 Created Time = 2019-02-14T13:18Z
Confirmation Key = 100004 Created Time = 2019-02-14T13:14Z
```

- We received four payment requests, but only two of them appeared in the outcome.
- The request 100001 was sent at 13:11 and confirmed at 13:15 with a matching OTP.
- Hence, it comes as a success.

```
Transaction ID = 100001 Status = Success
```

- The request 100002 was sent at 13:12 but confirmed outside the five-minute window at 13:18.
- But confirmed outside the five-minute window at 13:18.
- Hence, the ValueJoiner for that pair didn't trigger, and status does not appear in the outcome.
- The request 100003 was not confirmed, so that one also doesn't appear in the out come.
- Finally, request 100004 is an odd one.
- I was generated at 13:15
- However, somehow, it was confirmed even earlier at 13:14.
- This record appears in the result because both the records fall in the five-minute window with a matching key.
- However, the result is failed because their OTP didn't match

```
Transaction ID = 10004 Status = Failure
```

-------------------------------------------------------------------------------------------------------------------------------------------------

## 44. KTable to KTable Join

- We are going to learn the mechanics of implementing KTable to a KTable join.
- These joins are always non-windowed joins, and offer the same result as in the case of standard database table joins.
- The join result is another KTable.
- KTable - KTable is the most straighforward join and simplest to implement.
- Let's create a simple scenario to understand the use case and implementation mechanics.

### Problem Description

- You are a global bank, and you have a vast user base.
- However, you have already streamted all your customer records to your Kafka Cluster.
- All the user data is now available for any real-time operation.

1. All your user data is streamed to a Kafka Topic.
2. Some sample records are given below.

```
100001: {"UserName":"Prashant", "LoginID": "100001", "LastLogin": "2019-01-01T00:00:00.00Z"}
100009: {"UserName":"Alisha", "LoginID": "100009", "LastLogin": "2019-01-01T00:00:00.00Z"}
100087: {"UserName":"Abdul", "LoginID": "100087", "LastLogin": "2019-01-01T00:00:00.00Z"}
```

3. Every time a new user register, her details are also streamed to the system.
4. Every time a user successfully logs-in to your application, you send an event to your Kafka cluster.
5. Some sample records are given below.

```
100001: {"LoginID": "100001", "CreatedTime": "2019-02-14T13:01:15.00Z"}
100087: {"LoginID": "100087", "CreatedTime": "2019-02-14T13:01:18.00Z"}
```

### What we will do

- We want to create a simple application that updates the last login timestamp whenever a user logs into the system.
- Java Project Reference: lastlogin

---------------------------------------------------------------------------------------------------------------------------------------

## 45. KStream to KTable Join

- This join is also non-windowed.
- A typical use case of such join is to implement lookups and stream enrichment.
- For example, if you have a KStream of user activities and you are looking to enrich this stream with the latest user profile information, from a KTable
- You may want to implement a KStream to KTable join.
- A similar use case is often implemented using KStream to GlobalKTable join.
- I haven't talked about the GlobalKTable yet. So let me cover it up.


### Let's talk about GlobalKTable

- We learned about KTables. However, KTables are local.
- What it means is simple. Each streams task would have its own local copy of the KTable and a local copy of the state store where the KTable data is persisted. This local tables are greate because they allow you to work on a partition of the data in parallel.
- In this sceanrio, each stream task can work independently without relying on other tasks.
- However, in some cases, you may need a global table. 
- A global table is something available to all streams threads.
- It is a common data set which anyone can read.
- Any changes to this global table should be available to all stream tasks, even if they run on different machiens.
- Kafka Streams offers this capability as GlobalKTable.


### GlobalKTable

- Like a KTable, a GlobalKTable is also an update stream, where each data record represents an update or insert.
- However, there is one fundamental difference between these two strucutures.
- A standard KTable is local in nature, whereas a GlobalKTable is global in nature.
- Let's try to understand what it means by being local and how it differs from being a global table.

### How Local Table differs from Global Table

- Assume you have a topic T5 with five partitions.
- You want to read this topic into a table.
- We already learned that partitions are the main idea behind the parallel processing in Kafka.
- You can run five instances of your application on topic T5 to achieve a maximum degree of parallelism.
- Assume you started five instances of the same application.
- In this scenario, each instance of the application will be assined one partition.
- The local KTable will be able to process data only from one assigned partition.
- This scenario is perfectly fine for the parallel processing of data. 
- However, suppose you read the topic into GlobalKTable.
- In that case, each instance of the application will be assigned all five partitions
- The GlobalKTable at each instance will read data from all the partitions and hence, all of them will possess all the data.
- This scenario is problematic for parallel processing because it causes duplicate processing. 
- However, GlobalKTable makes perfect sense for broadcast and lookup tables.
- GlobalKTable is mainly used in star-joins, foreing key lookups, and broadcast information.
- However, you must be careful in using GlobalKTable as they require local storage on each application's instance.
- They also increase the network traffic and broker workload because all instances read the entire data.
- GlobalKTable is excellent for a small set of information that you want to be available to all your instances.


### Let's move back Implementing Joins 

- Since the use case for KStream to KTable join and the KStream to GlobalKTable joins are the same, we will implement an example for GlobalKTable.
- Implementing KStream to KTable join is the same as we are going to do it for a GlobalKTable.
  
### Problem Description - Compute the Advert Clicks by News Type

- You are a popular news website, and you directly sell advertising campaigns to your customers.
- You have created predefined locations across your website where you can place advertisements at runtime.
- These advert placement locations are often known as advert inventories.
- Advert inventories are tagged with a bunch of associated information, such as the following.

```
Advert Inventories
- Age - I mean, how old is the news around the inventory
- Type of Content - What is the type of content - I mean whether the story around them is business news or sports news
- Placement Location - What is the placement location - left side, right side, top, bottom, and in-between content
```

- It is a common requirement to measure the performance of such inventories in terms of impressions and clicks.
- These measurements are critical to adjust the campaign in real-time.
- We want to create a Kafka Streams application to compute the add clicks by news type.
- In other words, we want to know the number of ads clicked for sports news, business news, political news, and so on for all available categories.
- The first step to achieve this goal is to bring all your advert inventories into your-real streaming infrastructure.
- For the sake of simplicity, let us model the inventory using a JSON message as shown here and publish them to your Kafka cluster.

Sample Inventory List
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

- The second step is to bring all your ad-clicks as a real-time streaming event into your Kafka cluster.
- Assume your website is sending all the clicks using a JSON, even as shown here.

Sample Click Events
```
1001:{"InventoryID": "1001"}
1002:{"InventoryID": "1002"}
1003:{"InventoryID": "1003"}
1004:{"InventoryID": "1004"}
1005:{"InventoryID": "1005"}
1006:{"InventoryID": "1006"}
1007:{"InventoryID": "1007"}
1008:{"InventoryID": "1008"}
1009:{"InventoryID": "1009"}
1010:{"InventoryID": "1010"}
```

- Each event represents a click for the given inventory id.
- Once you have these two topics and data starts flowing into them, we want to create a real-time application to count the add clicks by the news type.
- Based on the sample data given here, you can expect the following outcome. 

```
| News Type  | Total Clicks |
| Sports     |      3       |
| Politics   |      2       |
| Local News |      4       |
| Health     |      1       |
```

### Solution

Java Reference Project: advertclicks


