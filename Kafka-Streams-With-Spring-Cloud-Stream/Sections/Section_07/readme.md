# Section 7 - Working with KStream

## 23. Kafka Stream Exactly Once

- In the last section we implemented an application (e.i:. avroposfanout java project) that read one Stream and produce two Streams. Consume PosInvoice and generate two streams (HadoopRecord and Notification). We create two Input Channels and two Listeners Services.

- We have two problems in this implementation.
    1. Repeated Reading 
        -  We read the same Kafka Topic Twice. We created two channels but both point to the same kafka topic.
    2. Separate Transactions 
        - We implemented two different Listeners (HadoopRecordProcessorService and NotificationProcessorService). Both are these listeners are independent. Spring Framework, will start these two listeners in two different threads. So, the will running in parallel. In some scenarios this is what we want to achieve. We want to implement two independent parallel listeners doing different work. But in this scenarios, both the processes are light-weight, and both are reading the same input topic. So it makes sense to compine these two listeners services into one service.
        Can we do it? Yes, we can. Then, why we didn't do it in the first place? Well, we have two reason. I wanted to help you learn implementing two listeners in a single Spring Boot application. I also wanted you to understand that each listener needs a separate input and output channel configuration and you specify a different serializer for each channel. All that can be learned when we created an example for implementing two listeners. And I hope you learn all those details. I also wanted you to learn about the @SendTo annotation. We can use @SendTo annotation to one or more topics. In our example we just sent the output to just one topic. If you want to return to more than one topic, you can return an array to multiple topics. Next example we are going to cover it.
        However, I wanted to conver the limitation of the @SentTo annotation. If you want to send the output to multiple topics using the @SendTo annotation, all the output records should be the same type. In the previous example (e.i:. avroposfanout java project) In this example, we wanted to send the Notification and HadoopRecord. Both of these were of two different types of records. And the @SendTo annotation does not support this requirement. That's another reason for creating two listeners because we wanted to produce two different types of outputs, so we created two listeners.

## I want to combine these two listeners to achieve the following goals.

    1. Read Once Produce Multiple
    2. Exactly Once Transaction


### What is exactly Once Transaction

- In a Kafka Stream application, you will read input records, do some processing and produce output to one or more topics.

e.i.:

Input Topic --- Input ---> Application --- Send to Notification and HadoopRecord Topics --> Notification and Hadoop Sink Topics

That means, each input record is processed exactly once, even in case of failures. In fact, every application must show this behaviour.
If we start processing a single input record more than once, your output result is corrupted and it becomes incorrect. Right?

Implementing exactly once is super simple for a single-threaded non-distributed application. But in the case of a distributed application,
this simple and the most basic requirement is hard to achieve due to failure scenarios. Let me explain the challenges in a Kafka Ecosystem.

Let me start using this diagram:


e.i.:

Input Topic --- Input ---> Application --- Send to Notification and HadoopRecord Topics --> Notification and Hadoop Sink Topics


- In the example above, we are reading some records from the input topic.
- These records do not come to you one by one.
- The Kafka Listener internally pulls a packet of multiple records from the Kafka Cluster.
- Let's assume you pulled a packet of 10 records
- Now your listener will process all of those 10 records one by one.
- Now you will produce output for those 10 records and send it to these two topics Notification and (Hadoop Sink Topics)
- In this example, Notification receives one output for each input. Right? So you are sending 10 records to the notification topic.
- However, the Hadoop sink topic receives multiple output records for each input record. Because we used a flatmap() in the processing.
- So let me assume that the Hadoop sink topic receives 40 outputs records for 10 input records. Now, these records are not sent to kafka
topic one by one. These records are also sent to the Kafka topic as a packet of records. So the point is streight: 
    1. You listener pulls a packet of input messages from Kafka, process them one by one, 
    2. Send all the output back to the target topics as an output packet.
    3. The listener will also wait for the acknowledgement from the output topics that the packet is successfully and safely stored in the Kafka cluster.
    4. Once you get an acknowledgement from the output, the listener will also send back the commit acknowledgment to the input topic that the input
    messages were received and processed. This cycle is one transaction.
    5. If you notice the diagram, this one transaction involves one input and two output topics.
    
- A complex application requirement might extend this scenario to have a single transaction involving multiple input topics and multiple-output topics.
- In this example, we have one input and two outputs. But you may have 3 or 5 inputs and 5 or 10 output-channel. And still want to do all of the work
as a single atomic transaction.

- What do we mean by a single atomic transaction?
    - A: Either all of the activities of a transaction are successfuly commited, or all of this is rollbacked.
         This transaction is necessary to achieve a healthy and consistent state of your output data.
         In practice, this atomic transaction is difficult to achieve for a distributed system like Apache Kafka.

    - Why is difficult to achieve it. Let me explain.
        - Think of the scenario where your application sends data to the notification topic and sends some data to the Hadoop topic.
        - The acknowledgement from Hadoop topic came.
        - However, acknowledgment from the notification topic didn't come because of a millisecond network outage.
        - The messages were received on the Notification topic. The receiving brocker sent an acknowledgement also.
        - But the acknowledgment didn't reach your application because you had a network outage for a millisecond.
        - Now, what will happen?
            - Your application will wait for a while and get a time-out.
            - In case of a time-out, the application will retry sending the last packaet once again.
            - The previous message was already received.
            - This retry is also successful, but your data is now duplicate in the Hadoop topic.
            - So the retry in the case of not receiving an acknowledgment is one source of data duplication.
    
    Now let's consider the second problem.
        - I received a packet of input, processed it, sent the output to both the topics, also received an acknowledgment.
        - Now the last thing is to send a commit acknowledgment to the input topic.
        - However, my application crashed for some reason.
        - So the commit acknowledgment is not sent back to the input topic.
        - What will happen when I restart my application?
            - I'll read the same packat once again, process it and send the outputs.
            - But I processed it twice. Right?
            - I already processed the same record before failure but could not store the commit successfuly.
            - So I reprocessed the same input packet after the failure.
            - And that's going to corrupt all my output topics with duplicates.
            - So, the main problem of creating duplicates is caused in a failure scenarios. 
        - If everything runs without a failure, you may not see a problem at all.
        - But things will break, and failure is a common thing.
        - How do we protect our application with these problems and implement an atomic transaction in your Kafka application?
        - That's what I want to achieve by rewriting this example once again.

- Next Sections we will recreate the earlier example (e.i:. avroposfanout java project), and you will learn the following things.
    - Read Once Produce Multiple
    - Implement Exactly Once Transaction



## Section 24. Implementing Exactly Once






## 29. Handling Poisson Pills

    - When we are using XML, JSON and others types of message. You will be using a JSON Serializer to parse the input JSON and convert it into a Java object. However, the parsing of JSON input is performed by the framework. We do not need to parse it. We simply get a Java Object as input to the listener method. 
    - I have a question here.
    - What if you received a malformed JSON input?
    - The framework will try to parse it and deserialize the input JSON to a Java Object. But you received a malformed input, so the framework will fail to 
    parse and throw an exception.
    - These malformed input records are known as poison pills.
    - I mean, you are receiving hundreds of thousands of well-formed and input message all day. But in between, once a day, someone sends a malformed record, and boom. Your application throws an exception and crashes.
    - You are now running around for something which is not your mistake. How do we handle these malformed input deserialization exceptions?
    - Kafka Streams binder gives you three options:
        1. logAndFail (Default Behaviour - Log the error and stop your application)
        2. logAndContinue (log and Continuing process the next record)
        3. sendToDlq (Send to Kafka Topic and continue processing the next record - As known as DLQ - The dead letter queue)
            3.1 - The dead letter queue is the place to send unprocessed or undelivered messages for later investigation

### How to apply these options

    - Using these options is as simple as setting a deserializationExceptionHandler configuration in your YAML file. The deserializationExceptionHandler is a binder configuration. So you must set it for the binder.
    - Here is the full configuration value.

        - spring.cloud.stream.kafka.streams.binder.deserializationExceptionHandler: logAndFail ## Default
        - spring.cloud.stream.kafka.streams.binder.deserializationExceptionHandler: logAndContinue
        - spring.cloud.stream.kafka.streams.binder.deserializationExceptionHandler: sendToDlq ## Using this option we have to configure the topic name in yaml file as well.

    
    - This exception handler is used if you face a deserialization problem caused by poison bill records.

     
