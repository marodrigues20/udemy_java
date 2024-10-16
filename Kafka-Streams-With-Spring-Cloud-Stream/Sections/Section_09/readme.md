# Section 09 - Timestamp and Windowing Aggregates

## 38. Kafka Time Semantis

------------------------------------------------------------------------------------------------------------------------------------------------------------
1. KStream Aggregates
2. KTable Aggregates

------------------------------------------------------------------------------------------------------------------------------------------------------------

- The methods that we learned are good to compute aggregates on an infinite stream of data.
- However, you often need to create smaller chunks or buckets of data and calculate aggregates on the bucket only.
- Such buckets are more often termed as a window.
- For example, you might want to compute sales over the past hour.
- In this case, you may have to bucket your data into an hourly window and calculate aggregates on each window.
- Time is the most commonly used attribute for windowing. 

------------------------------------------------------------------------------------------------------------------------------------------------------------
### Windowing Aggregate

- You might want to compute sales by a minute, page views per second, or ad clicks per day.
- However, Kafka offers the following two types of stream windows.
    - Time Window
    - Session Window

- The notion of windowing is often used with aggregates and joins.
- In this section, we will learn about creating different types of windows and apply them to aggregates.
- We will also learn about joins and use the notion of windowing over joins.


------------------------------------------------------------------------------------------------------------------------------------------------------------

### How Kafka understand the time

- Apache Kafka allows you to work with the following timestamps.

#### Time Semantics

- Event Time ( It is the exact time when the event was generated at the source system ) 
    - For example, the event time can be stamped by the POS system and the time becomes an integral part of the invoice.
    - Most of the time, we represent the event time using a field in the event object itself.
    - For example, our POS simultor generate invoices with a filed CreatedTime as defined by the schema shown here:
        ```
        {"InvoiceID":100540, "CreatedTime":"25-Nov-2020:10:30AM IST", ...}
        ```
    - CreatedTime in your event is the best thing that you can have.
    - However, in many cases, your event generating system may not already have a timestamp field.
    - It may not be feasible to modify such applications to add a timestamp field.
    - In some of those cases, you can suse the timestamp metadata in the message record.
    - If you are using Kafka Producer to send data to the Kafka Cluster, the API always includes a timestamp in the record metadata.
    - You can safely use the timestamp from the metadata as your event time.
  

- Ingestion Time
    - You may have a business case to ignore the event time but consider the time when the event reached your Kafka Broker. 
    - Ingestion time is the time when the event reaches your system.
    - Obviously, you can't explicitly set ingestion time.
    - It must be assinged by the Kafka Brocker automatically.
    - You can achieve the ingestion timestamp by setting
      - ```
        message.timestamp.type=LogAppendTime
        ```
        configuration at the topic level.
    - When the topic is configured to use LogAppendTime, the timestamp in the message record metadata will be overwritten by the brocker using the brocker system time.
    - That means you can either create a timestamp metadata using the producer time or the brocker time, but you can't have both.


- Processing time
  - When you are processing the event, and it is the simplest thing to implement.
  - You can get the processing time as the current wall-clock timestamp from your local machine and use it for your time-based windowing.


- You must configure your application to use one of the three timestamps.

### How to setup to use one of the thress Timestamps above

- You can do it by creating a TimestampExtractor and configure it for your input channel.
- Kafka offers you some built-in timestamp extractors.


#### Timestamp Extractors

- WallclockTimestampExtractor
- FailOnInvalidTimestamp
- LogAndSkipOnInvalidTimestamp
- UsePreviousTimeOnInvalidTimestamp

#### Which Extractor use with each Timestamp

- Processing Time -> WallclockTimestampExtractor for your input channel.
- IngestionTime -> FailOnInvalidTimestamp; LogAndSkipOnInvalidTimestamp; UsePreviousTimeOnInvalidTimestamp
- Event Time -> You must create a custom timestamp extractor and use it.


### Note: 

- If you want to implement time-based operations such as window aggregates or windowing joins, you must configure and use a timestamp extractor.
- If you are not implementing a time-based processing logic, you do not need and care about the timestamp extractors.

------------------------------------------------------------------------------------------------------------------------------------------------------------

## 39. Windowing Aggregates

### Problem Definition

- We want to compute the number of invoices generated by each store in a five-minute window.
- You can create this example to simulate the scenario in a controlled environment using the steps defined here.

1. Create a Kafka Topic as a simple-invoice-topic
2. Send the following three Invoices to the Kafka Topic.
3. Each invoice comes as key/value pair with StoreID being a key and the invoice itself as a JSON serialized message.
    ```
    STR1534:{"InvoiceNumber":101, "CreatedTime":"2019-02-05 10:01:00 AM", ...}
    STR1535:{"InvoiceNumber":102, "CreatedTime":"2019-02-05 10:01:40 AM", ...}
    STR1536:{"InvoiceNumber":103, "CreatedTime":"2019-02-05 10:03:19 AM", ...}
    ```
4. The event time of the invoice is recorded in the CreatedTime field of the SJON message.
5. Event Time is normalized to UTC epoch milliseconds.
6. This format of representing time is not human readable.
7. However, most real-time applications will follow a time format normalized to a standard time zone, so we are using the same.
8. We want to develop a stream processing application that uses time semantics to compute the number of invoices by StoreID in a five-minute window.
9. For three invoices sent earlier, you can expect the following outcome.
10. We have two stores, so you will get two records in the output.
11. The first store created two invoices in the first five minutes.
12. So you will see two records for the first store.
13. The second store has got only one record.
14. Now we can send some more messages.
    ```
    STR1536:{"InvoiceNumber":104, "CreatedTime":"2019-02-05 10:06:00 AM", ...}
    STR1534:{"InvoiceNumber":105, "CreatedTime":"2019-02-05 10:07:50 AM", ...}
    STR1536:{"InvoiceNumber":106, "CreatedTime":"2019-02-05 10:09:30 AM", ...}
    ```
15. We expected these new messages to land in a different window.
16. After sending the above messages, our outcome should update to the following.
17. The STR1534 produce one more invoice, but this invoice was produced in the next 5-minutes slot.
18. So this new window will count one record.

### Straight to the point

- Our application should group the record by the store ID. Then for each store, you will again subgroup the records in a five-minute window and count the number of invoices for each window.

### Reference Java Project

- windowcount

------------------------------------------------------------------------------------------------------------------------------------------------------------


## 40. Tumbling Window Vs Hopping Time Window

- The last leasson, we created a windowing aggregate.
- The Window Count was an example of a Tumbling time window.

### What is Tumbling Window

#### Tumbling Windows

  - Fixed in size
  - No Overlap
  - No Gaps

- Tumbling windows are fixed-size, non-overlapping, gap-less windows.
- You can define a tumbling window using the window size as we did in the TimeWindows.of() method.
- Since tumbling windows never overlap, a data record will belong to one and only one window.
- Every record exactly falls in a single time window and gets aggregated.
- You cannot configure the window start time. The Framework will automatically determine the window start time based on your first event time.
- Once the first window's start time is set by framework, all other windows will follow the fixed interval from that first window.
  

### Hopping Window or Sliding Windows

- Hopping Window sometimes called Sliding Window
- However, Kafka terminology refers to them as a hopping window.


#### What is Hopping Window

- Hopping time windows are also fixed-size but overlapping windows.
- Unlike Tumbling Window a hopping window is defined by two properties:
  - Window's size
  - Advance Interval
- Sample code to define a Hopping window
  ```
    input.groupByKey()
        .windowedBy( TimeWindows.of(Duration.ofMinutes(5)))
            .advanceBy(Duration.ofMinutes(1))
        .count()
  ```
- The advance interval specifies how much a window moves forward relative to the previous one.
- For example, you can configure a hopping window of 5 minuts and an advance interval of 1 minute.
- Hopping windows are used to compute moving aggregates for a fixed size window.
- For example, you might want to use hopping windows to calculate page views in the most recent five minutes reported every minute.


### Summarizing

- Kafka offers you two types of time-based windowing.
- Tumbling time window and Hopping time window.
- The method for creating both types of windowing is the same.
- The tumbling time window will not have an advance interval.
- The Hopping time window will set an advance interval.


------------------------------------------------------------------------------------------------------------------------------------------------------------

## 41. Session Windows

### What is Session?

- Session are a period of activity followed by a defined gap of inactivity.
- For that reason, session windows are also represented by a period of actitivy rather than being a fixed size window.

### Examplifying Session

- You have a website, and you added a feature to your website that generates user click events and sends them back to your server. Your click events are JSON messages, as shown here.

```
USR101: {"UserID": USR101, "CreatedTime": "15993608600000","CurrentLink":"NULL","NextLink":"Home"}
USR102: {"UserID": USR102, "CreatedTime": "15993609200000","CurrentLink":"NULL","NextLink":"Home"}
USR101: {"UserID": USR101, "CreatedTime": "15993610400000","CurrentLink":"NULL","NextLink":"Home"}
USR101: {"UserID": USR101, "CreatedTime": "15993610400000","CurrentLink":"NULL","NextLink":"Home"}
USR102: {"UserID": USR101, "CreatedTime": "15993610400000","CurrentLink":"NULL","NextLink":"Home"}
USR101: {"UserID": USR101, "CreatedTime": "15993610400000","CurrentLink":"NULL","NextLink":"Home"}
USR101: {"UserID": USR101, "CreatedTime": "15993610400000","CurrentLink":"NULL","NextLink":"Home"}
```

- You planned to stream those events to your Kafka cluster.
- You wanted all the events for the same user to flow to a designed partition, and hence you implement UserID as a message key.
- Such a continuous flow of click events is often known as clickstream.
- All the events are for Feb 05, 2019, which I have normalized to minutes for simplicity.
- Now, for the given sample clickstream, we want to count the number of clicks per user session.
- However, we have a problem here.

### How do we define a user session?

- We can make some assumptions.
- A new session starts with the first click, and it ends if a user is idle for five minutes.
- With this definition of an event and the session, let's try to compute the clicks per visit.

Java Project Reference: sessionwindow

### Implementation explanation

- This method reads all the user clicks, and now we are ready to process them.
- What do we want to do?
- We want to group the click events by the user id.
- We will create a session window of five minutes and count the records.
- This example, looks like similar to the tumbling window example. The only difference is the window type
"SessionWindow" class.
- In the tumbling window example, we used TimeWindow class. However, this small change do a huge difference.
- TimeWindow are fixed in size. However, SessionWindow is variable in length. And the size of the window is dependent on the user activity.
- So, the session for the first user is a 30-minutes session. But you have another user who leaves your website in 10 minutes. So his session is a 10-minute session.



