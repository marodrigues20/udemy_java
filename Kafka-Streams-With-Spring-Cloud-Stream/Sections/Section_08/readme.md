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

## 31. Deep Dive into KTable

- I want to create a simple Kafka stream application that subscribes to a Kafka topic using the KTable API.

i.e:

computer --> stock-ticks --> Kafka Topic --> Initial KTable [Framework] --> Filtered KTable [Listener] --> Print --> Computer

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
    | Key     | Value |
    | hello   |   2   |
    | world   |   1   |
    | kafka   |   1   |
    | streams |   1   |


Reference Project: streamingaggregates

- There is one limitation to aggregate in Kafka Stream. It can be grouped only by a key.
- If your stream already come with the desired key, you can simply group your KStream using groupByKey()
- Alternatively, you can use the groupBy() method when your stream does not have a key

## 33. Aggregation Concepts

















