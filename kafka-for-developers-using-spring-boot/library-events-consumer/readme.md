# Section 13. Build Spring Boot Kafka Consumer - Hands-on

## 50. Introduction to Spring Kafka Consumer - Theory


## 53. How Spring Boot AutoConfiguration Works - Kafka Consumer




## Section 14. Consumer Groups and Consumer Offset Management

Consumer Groups
Multiple instances of the same application with the same group id. (Watch this lecture "Consumer Groups")

Rebalance
Changing the partition ownership from one consumer to another

Building the application
% ./gradlew build

Lauching the application
% java -jar build/libs/library-events-consumer-0.0.1-SNAPSHOT.jar

Lauching the second instante application
% java -jar -Dserver.port=8082 build/libs/library-events-consumer-0.0.1-SNAPSHOT.jar

When you lauch two instances of the same application the Kafka partitions will be redistributed between the application
instance.

I.e: If you lunch one instance. Kafka will assing 3 partitions for just one application instance.
     If you lunch the sencond instance. Kafka will reassing and redistribute the partition between two instances of the
  same application that are using the same groupId.


Committing Offsets

When the Kafka Consumer pool the messages from the topic the Kafka Consumer does Offsets Committed over __consumer_offsets.
For this reason the Kafka Consumer doesn't retrieve the same msgs again.
It is being using:
BATCH: Commit the offset when all the records returned by the poll() have been processed.

However, we can change this behavior too.


https://docs.spring.io/spring-kafka/reference/html/#committing-offsets

Most used mode is Batch and Manual

### 56. Manual Consumer Offset Management

Committing Offsets - Manual


# Section 15. Persisting Library Events in DB - Using H2 inMemory

Access H2 Console
http://localhost:8081/h2-console


# Section 17: Error Handling, Retry and Recovery in Kafka Consumer

## How to customize error handler behavior that comes with Spring Kafka Consumer. (Using Spring Boot 2.6.x)

Default behavior is retry 10 times.

1) Change the LibraryEventsConsumeConfig.java
2) Add factory.setCommonErrorHandler(); to define the custom error handler that you gonna define.
3) Implement the DefaultErrorHandler


We can create a list of exceptions that we don't want to retry consume the messages.

## How to work ExponentialBackOffWithMaxRetries
  
  - ExponentialBackOffWithMaxRetries try to retry give the exponential interval between retries.

i.e: var expBackOff = new ExponentialBackOffWithMaxRetries(2);


##71. Recovery in Kafka Consumer

After retries fail. We can try to consume again a message in future time using the Recovery feature.

Recovery Types:

  Approach 1: Reprocess the failed the record again.
    Example: Services the Consumer interacts with is temporary down.

  Approach 2: Discard the message and move on
    Invalid message: Parsing error, Invalid Event


In Approach 1 we have two options
  - Option 1: Publish the failed message to a Retry Topic.
  - Option 2: Saved the fail message in a DB and retry with a Scheduler


In Approach 2 we have two options
  - Option 1: Publish the failed record in to DeadLetter Topic for Tracking Purposes.
  - Option 2: Saved the failed record into a DB for Traking Purpose

## 75. Recovery: Publish the message to the DeadLetter Topic

If you want to check that the record has been posted to letter topic use this command.

  
$ ./kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic library-events.DLT --from-beginning --property print.headers=true --property print.timestamp=true

## 76. Recovery: Save the failed message to the DB

Option 2: Saved the failed message in a DB and try with a Scheduler

We have to implement the functional interface ConsumerRecordRecoverer.java 












