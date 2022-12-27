# Section 8 - KTable and Aggregations

## 30. Introducing KTable

So far in this course, I have been working with KStream. However, Kafka Stream API also offers you one more abstraction to work with streaming data. In this section we will deeper into the KTable and help you understand it better.

### Summarization of KTable

1. KTable has a primary key
2. KTable cannot have a null key
3. KTable Implements UPSERT (If a key doesn't exist it will be insert. If the key already exist it will be updated)
4. Null key implements a DELETE (Null values in KTable are used to implement a DELETE operation)

### How do we create a KTable?

- We can create a KTable in the same way that we create a KStream.
- We create a listener method and asked the framework to give us a KStream. The framework will internally create a Kafka Consumer, read the records from the Kafka Topic, and provide you a KStream of the income records. The same way, you can 
create a listener method and ask the framework to give you a KTable. The listener internally create a Kafka consumer, read all the incoming messages from the Kafka topic, and give you a KTable of records in the same way it gave you a KStream.
- Once you have a KTable, you can apply KTable transformation methods. Most of the KStream transformation methods that we learned so far are also available for KTable.
- We can apply filter(), mapValues(), map() and etc.
- The UPSERT and the DELETE features of the KTable give them extra power which we use for grouping and aggregating Kafka Streams.

### How to group and aggregate Kafka Streams?

- In this section we are going to study it.
<br>

## 31. Deep Dive into KTable

- I want to create a simple Kafka stream application that subscribes to a Kafka topic using the KTable API.

i.e:
```
computer --> stock-ticks --> Kafka Topic --> Initial KTable [Framework] --> Filtered KTable [Listener] --> Print --> Computer
```

Key Message is Stock Symbol
Value Message is the Current Price for the Stock Symbol

Reference Project: ktabledemo

### What is "materializedAs: stock-input-store" in application.yml file?

- Kafka Stream Framework is designed to use Rocks DB Local database to store the local copies of the KTable.
For this reason we have this kind of configuration.
- The incoming records to the KTable should be materialized in the following KTable. You can think of this as a KTable name in the local Rocks DB database.
- This behavior is different from KStream.


### Why KStream doesn't require Local Storage

- Because the framework will read a packet of messages and immediately start sending them to your listener. We don't need to store it anywhere. However, KTable behaviors differently.
- In the case of KTable, the framework will pull some records from the Kafka topic. Looks at the records and see if we have
duplicate records for the same key and add it into Initial KTable [Framework]

### What is "state.dir: state-store" in application yaml file?

- Framework will create a local state directory in my current directory.
- We will see this directory after running our application.

### Why do we need a KTable to buffer some records?

- Update them with the latest record and send us only the most recent records.
- We can have a use case where you only care about the most recent record.
- KTable help us to discard older records and optimizing our application to process only the most recent updates.
- KTable has a Key role in calculating aggregates.

### Send to Kafka Topic

- You have to convert a KTable to KStream to send message to Kafka Topic.
- foreach() and to() methods are not available in KTable.
<br>

## 32. Computing Streaming Aggregates

- KTable is mostly used in computing aggregates.
- Computing aggregates in real-time over a stream of data is a complex problem to solve.

### Problem Statement

-  I want to create a real-time streaming word count application. This can help us understand some core basic of KTable and Aggregation.
- What we will do:

1. We will create a Kafka Topic and use a command-line producer to push some text to the topic.
2. Let's assume you created a topic and sent two messages.
3. On the other side, we will create a Kafka Streams application to consume data from the topic in real time.
4. The Streams application should break the text message into words and count the frequency of unique words.
5. Finally, it should print the outcome to the console.
```
    | Key     | Value |
    | hello   |   2   |
    | world   |   1   |
    | kafka   |   1   |
    | streams |   1   |
```

Reference Project: streamingaggregates

- There is one limitation to aggregate in Kafka Stream. It can be grouped only by a key.
- If your stream already come with the desired key, you can simply group your KStream using groupByKey()
- Alternatively, you can use the groupBy() method when your stream does not have a key
<br>

## 33. Aggregation Concepts


- Computing aggregate is a two-step process.
    1. Group your data by key 
    2. Apply aggregation formula.

- Before we start getting deeper into aggregation, I want to spend some time talking about the following facts. 

1. Aggregation Key
    - The record key is the most critical element for an aggregation. Your thought process for aggregation must start with choosing an appropriate aggregation key.

2. Aggregation applies to KStream and KTable
    - The primary source of aggregation could be a KStream or a KTable. However, the outcome of aggregation is always a KTable.
    - Aggregate a KStream is less complicated and straighforward.
    - Aggregate a KTable is often complicated and requires extra care because KTable is an update stream.
    - Before you start applying the aggregation formula, you must determine your aggregation key and group your data. 
    - Group your data is straightfoward. You can group your KStream using groupBy() or groupByKey() method.


    1. KStream -> groupBy(), groupByKey() -----> KGroupedStream
    2. KTable -> groupBy() -----> KGroupedTable


    - KTable does not support the groupByKey() method because it does not make any sense for the KTable.
    - The grouped data in Kafka Streams is represented as a KGroupedStream.
    - KTable returns a KGroupedTable.

    - Once you have your data arranged in groups, you can apply the aggregation formula.

    - Kafka Stream API offers the following three methods on a KGroupedStream

    - Aggregation Formulas
        1. count() // Use it to count the number of records by the grouped key. Records on a KStream with a null key are ignored.
        2. reduce() 
        3. aggregate()


    - Note: reduce() and aggregate() methods are like one formula fits all type of aggregations. If you compare it with SQL, this thing might appear strange.

    - SQL Formulas
        1. count(*)
        2. sum(reward)
        3. max(reward)
        4. avg(reward)

    
    - Aggregation in SQL is offered through some predefined formulas such as sum(), max(), avg(), etc. However, Kafka Streams do not have that flexibility for a simple reason. The record structure that we use in the Kafka Streams application is not as primitive as SQL types. 
    - Every aggregation in Kafka Streams is created using a generalized framework such as reduce() and aggregate(). The count() is the only exception because it does not depend on the data type.


### Repartitioning or Shuffle/Sort

    Kafka is a distributed system. Your records are stored in a distributed Kafka cluster in the topic partitions. Now you are writing a stream processing application to read and process records from the Kafka topic. But your application is also a multithreaded and distributed application. You do not have to do anything to make your application multithreaded, and making it capable of running on distributed servers. The framework is designed to do it for you. So by default, all your Kafka Streams applications are multithreaded and distributed.

    Let's assume you are writing one listener that listens to a Kafka topic and aggregates them. For example, I am reading invoices from a Kafka topic.

    Key is the invoice number
    Value is the invoice

    I want to create a super simple application to calculate invoice-count by-store. The result should look like the following.

```
    |  Key   | Value |
    | STR001 | 3     |
    | STR002 | 2     |
    | STR003 | 1     |
```

    Your input records are stored in your Kafka Server, and your application will pull them from the Kafka topic. Let's assume your input topic is partitioned and you have three partitions. So these input records were stored in three different partitions. Now I created a Kafka Streams application and started it. The Frameworks knows that the input topic has got three partitions. How it knows?
    Simple! The framework will connect to the Kafka cluster and ask for the topic metadata. The metadata tells that the input topic is broken into three partitions. So the framework always knows that the input records are coming from three partitions. Now, the framework will try to take advantage of this fact and start three threads of your listener method. Each thread will receive records from one partition and process them. That's how your application is always multithreaded. And these three threads are implemented using Kafka Consumer Group. So, each thread is an independent consumer, and all three are part of the same consumer group. The consumer group is internally created and managed by the framework. As a developer, you are free from all this headache and simply focus on the business logic.


#### How Each Kafka Application is identified?
- Every Kafka Stream Application is identified by a unique id. You can assign an application Id to your application in your YAML file. However, if you do not set an application Id configuration, the framework will automatically assing a unique id to your application. However, the Kafka Stream Application will always have a unique and static Id. 

##### What is static id?
- That simply means your application Id will never change. You start your application, stop it and start it on a different machine or on the same machine, your application ID remains the same. 
- Every Kafka Streams Application must have a unique static ID. If you are setting a custom application ID, you should never change it. But you can leave it on the framework to automatically assing a static id.


#### How do we use this application ID?
- The application ID is used to form the consumer group. The consumer group ID and application ID are not the same. 
- The framework will create a consumer group and set the consumer group ID prefixing the application id. 
- So, in our example, the framework will start three listener threads in a single consumer group. Let's assume that your application Id is APP-01 and the consumer group id is APP-01-L1CG-1. And you have three listener threads running into an Instance/Computer machine. 
- Now, you want to start one more copy of the same application on a different Instance/Computer Machine. You can do that. But what will happen?

##### But what will happen?
- Will you reread the same records and reprocess them on a different machine? Answer: No!
- The framework does some magic here. As soon as you start another copy of the same application, the framework will detect that you started copy of the same application. You are trying to read data from the same input topic. Magical! Isn't ?

##### How does that magic happen?
- Super Simple.
- The unique static application Id. Your second copy of the application will request the data from the Kafka topic. The Kafka Cluster compares the application ID and identifies that you are running a second copy of the same application you already started earlier. The cluster will inform the first copy that we have another instance of the application trying to read the same data. So why don't you both start sharing the workload? And as a result, the broker will revoke one partition from the first copy of the application. The first copy will shutdown one thread. The second copy gets one partition to process and starts one thread to do it. The work was already being done in parallel using three threads. But all of that was happening on a single machine. Now the work is distributed over two computers. 

##### What if I start another copy on my third machine. 
- You know what will happen?
  Answer: The Framework will work with the Kafka Cluster and shift one thread from the first application instance to the third instance of my application.

##### Can I start one more copy of my application?
- Yes. But the fourth copy has nothing to do. We have only three partitions, so we can do it in three parallel threads. The fourth guy will be sitting idle and keep waiting. 
- But what if one of these four fails? 
- Answer: Let's assume the third application machine instance is gone. Your disk failed, or your OS crashed, or maybe you have a power failure or network failure. Whatever the reason, but this machine is gone. Now you have one free partition. No one is processing it. Right? So the framework will assign the free partition to this idle person.

##### If one more application/machine gone?
- The Framework will assign this partition to one of the remaining guys. They will start one more thread and do parallel processing. 


That's what we call a distributed application. You can scale your application to multiple system, and do not worry if some of those are failing. You will have some time to repair those systems and bring them up again.
But we started talking of multithreading and distribution in the context of the grouping and aggregation.

### What is the relation between grouping/aggregation and multithreading?

- Let's come back to our example.
- We have these six input records:

```
| Key    | Value |
| STR001 | { InvoiceNo: 12345, CustomerID: 453345, ...... } |
| STR002 | { InvoiceNo: 12356, CustomerID: 238547, ...... } |
| STR003 | { InvoiceNo: 16512, CustomerID: 453345, ...... } |
| STR001 | { InvoiceNo: 13498, CustomerID: 230230, ...... } |
| STR002 | { InvoiceNo: 10891, CustomerID: 453345, ...... } |
| STR001 | { InvoiceNo: 12398, CustomerID: 687127, ...... } |
```

- They are stored in 3 (Three) partitions.
- But in which record is stored where?
- The record partitions are assinged by the record key.
- We have 3 unique keys, than we have 3 partitions. So they might be equally distributed.


For example:

- These three are in the first partitions
```
| STR001 | { InvoiceNo: 12345, CustomerID: 453345, ...... } |
| STR001 | { InvoiceNo: 13498, CustomerID: 230230, ...... } |
| STR001 | { InvoiceNo: 12398, CustomerID: 687127, ...... } |
```

- These two are in the second partition.
```
| STR002 | { InvoiceNo: 12356, CustomerID: 238547, ...... } |
| STR002 | { InvoiceNo: 10891, CustomerID: 453345, ...... } |
```

- The last one was in the third partition.

```
| STR003 | { InvoiceNo: 16512, CustomerID: 453345, ...... } |
```

This is one arragement. It could be a little different.

-------------------------------------------------------------------------------------

- The last record that was in the third partition. Could be in the first partition. Like this:

```
| STR003 | { InvoiceNo: 16512, CustomerID: 453345, ...... } |
| STR001 | { InvoiceNo: 12345, CustomerID: 453345, ...... } |
| STR001 | { InvoiceNo: 13498, CustomerID: 230230, ...... } |
| STR001 | { InvoiceNo: 12398, CustomerID: 687127, ...... } |
```

- These two are in the second partition.

```
| STR002 | { InvoiceNo: 12356, CustomerID: 238547, ...... } |
| STR002 | { InvoiceNo: 10891, CustomerID: 453345, ...... } |
```

- We never know the actual arragement and you don't even care. All we care about is this.
- All the records of the same key are always placed in the same partition. I mean, all these three records have the same key. So, all of these will go to the same partition.
- They might go to the first partition or to the second partition, or to some other partion. But all of these will sit in the same partition.
-  This placement arrangement is guaranteed by Kafka if you are not changing the partition assignment logic.
- Kafka is a flexible system, so you can change this behavior. But you rarely do that.

- Let's assume that records are arranged in the partition like this.


- These three are in the first partitions

```
| STR001 | { InvoiceNo: 12345, CustomerID: 453345, ...... } |
| STR001 | { InvoiceNo: 13498, CustomerID: 230230, ...... } |
| STR001 | { InvoiceNo: 12398, CustomerID: 687127, ...... } |
```

- These two are in the second partition.

```
| STR002 | { InvoiceNo: 12356, CustomerID: 238547, ...... } |
| STR002 | { InvoiceNo: 10891, CustomerID: 453345, ...... } |
```

- The last one was in the third partition.
```
| STR003 | { InvoiceNo: 16512, CustomerID: 453345, ...... } |
```

- Your application is running three threads.
- Each Thread is responsible for processing one partition
- Let's assume that the first thread is processing the first partition.
- The second Thread is processing the second partition, and so on.

- So the first Thread will calculate the record count for STR001.
- The second Thread will count the STR002, and the third one is counting STR003.
- We do not have any conflict or data discrepancy.
- All the three threads are working in parallel, and they are responsible for couting invoices for some given stores.
- The data for a store never jumbles across the boundary of a partition.

- But What if you wanted to count the same records by the customer-id?
- Look at above the customer-id in these records and think about the problem.
- You cannot correctly count it by customer-id.
- Why? Because the data for a given customer is jumbled across the partition. Look at the customer-id=453345. It is spread across the threads.
- We cannot count it unless all these records are given to a single thread. And that's what you can do using the groupBy().
- You will do a groupBy(customer-id). Right?
- So, the group will shuffle/sort this whole data and arrange them by customer-id.
- Then the Framework will redistribute the data once again to these threads. This redistribution is to make sure that the data for a single customer goes to the same thread. Again Magical! Right?


### How This Magic is done?

- The groupBy() will send the data back to the Kafka cluster. 
- But now the record looks like this:

```
| 453345 | { InvoiceNo: 12345, CustomerID: 453345, ...... } |
| 238547 | { InvoiceNo: 13456, CustomerID: 238547, ...... } |
| 453345 | { InvoiceNo: 16512, CustomerID: 453345, ...... } |
| 230230 | { InvoiceNo: 13498, CustomerID: 230230, ...... } |
| 453345 | { InvoiceNo: 10891, CustomerID: 453345, ...... } |
| 687127 | { InvoiceNo: 12398, CustomerID: 687127, ...... } |
```

- The customer-id is the key, and the value remains the same.
- This data goes back to the Kafka cluster and sits into a new intermediate topic.
- This new intermediate topic will also have three partitions.
- Why Three Partitions? Because we initially had three partitions, and we also have three threads.
- So an intermediate topic with three partittions is automatically created by the framework.
- Data goes there, gets arranged by the new key - the customer-id.
- Then one of these partitions is assinged to each thread. 
- They will reread the data.
- But now, it is arranged and grouped by the customer-id.

- So, the groupBy will internally trigger a repartitioning.

- You can also think of it as a shuffle/sort operation of the other distributed system such as Apache Spark. But all this happen automatically, and the framework will manage it for you.
- We, as a developer, are not worried about it.
- All we do is to groupBy() our data by the desired key.
- Once grouped, you can apply the aggregation formula and compute the results. That's all!
- However, you must be aware of the existence and the need for suffle/sort or repartitioning.
- Why? Because the repartitioning is an expensive process. If you can avoid it in some cases, you will boost up your application performance.
- But How to avoid it?
- Simple! Plan your grouping key in advance and make sure your data is already coming with the same key.

- For example, in our first scenario, we wanted to count the invoices by the store-id. And our data is already coming with the store-id as a key. So, we do not need to repartition it.
- But in the second scenario, we wanted to count the invoices by the customer-id.
- So, we needed to use the groupBy() and change the key from the store-id to the customer-id. This change will trigger the repartitioning. If we know that we will compute the aggregates on the customer-id, we could have designed our input to come with the customer-id key.
- However, this is not always possible. But you should try to design your key based on your aggregation requirements and avoid repartition on the fly.
    

1. Group     -> Can trigger Repartitioning
2. Aggregate -> Can trigger Repartitioning

### One more critical concept for avoiding repartitioning

- Most of the Kafka Streams APIs are available in two variants.

Kafka Stream API
1. Key Preserving API
    - mapValues()
    - flatMapValues()
    - transformValues()
    - groupByKey()
2. Key Changing API
    - map()
    - flatMap()
    - transform()
    - groupBy

#### Key Preserving API

- Many APIs that we used so far including mapValues(), flatMapValues(), transformValues() and groupByKey() are key preserving APIs.
- These APIs are used to work with the message value without touching the message key.
- The message key remains the same, and we can tranform the value.
- We often prefer to use these key-preserving APIs unless we purposefully want to change the message key.
- So in our first scenario where we wanted to count the invoices over the store-id, we should be using groupByKey().
- Why? Because the groupByKey() method is a key-preserving API, it tells the Kafka framework that we want to group the data, but we are not changing the key.
- So, the Kafka Streams framework will not apply the repartitioning. Why? Because that is not needed.

#### Key Changing API

- Kafka Streams also provides an equivalent key chaning APIs. Such as map(), flatMap(), transform() and groupBy().
- These APIs are meant to be used when you intentionally want to change the key in the resulting stream.
- Changing the key of your stream shows an intention to repartition or shuffle/sort your data on the new message key.
- So, whenever we use a key-changing API, Kafka interally sets a Boolean flag that the new KStream instance requires repartitioning.
- Once this Boolean flat is set, if you perform an aggregate or a join operation, the repartitioning is handled for you automatically.
- The rule is simple. Plan you key in advance.

#### We cannot avoid the repartition all the time

- Kafka also gives you APIs to change the Key.
- Feel free to use them when you have a genuine requirmement for chaning the message key.

<br>

## 34. Reducing Kafka Stream








    






    

     





















