# Section 9: Kafka Java Programming 101

## 44. Java Producer

Steps to run:
    1. Create a topic demo_java
    2. Use the CLI command to create a consumer
    3. Run ProducerDemo.java

    Result: You can see the consumer in action.

## 45. Java Producer Callbacks

### Kafka Producer: Java API - Callbacks

    - Confirm the partitionand offset the message was sent to using Callbacks
    - We'll look at the interesting behaviour of StickyPartitioner

Steps to run:
   1. Create a topic demo_java
   2. Use the CLI command to create a consumer
   3. Run ProducerDemoWithCallback.java

## 46. Java Producer with Keys

    - Send non-null keys to the Kafka topic
    - Same key = same partition, remember?

i.e: ProducerDemoKeys.java

## 47. Java Consumer

### Kafka Consumer: Java API - Basics

    - Learn how to write a basic consumer to receive data from Kafka
    - View basic configuration parameters
    - Confirm we receive the data from the Kafka Producer written in Java

i.e: ConsumerDemo.java

## 48. Java Consumer - Graceful Shutdown

    - Ensure we have code in place to respond to termination signals
    - Improve our Java code

i.e: ConsumerDemoWithShutdown.java

## 49. Java Consumer inside Consumer

### Kafka Consumer: Java API - Consumer Groups

    - Make your consumer in Java consume data data as part of a consumer group
    - Observe partition rebalance mechanisms

i.e: ConsumerDemoWithShutdown.java

## 50. Java Consumer Incremental Cooperative Rebalanced & Static Group

    - Moving partitions between consumers is called a rebalance
    - Reassignment of partitions happen when a consumer leaves or joins a group
    - It also happens if an administrator adds new partitions into a topic

### Eager Rebalance - Default Behavior

    - All consumers stop, give up their membership of partitions
    - They rejoin the consumer group and get a new partition assignment
    - During a short period of time, the entire consumer group stops processing.
    - Consumers don't necesserily "get back" the same partitions as they used to

#### Cooperative Rebalance (Incremental Rebalance)

    - Reassigning a small subset of the partitions from one consumer to another
    - Other consumers that don't have reassigned partitions can still process uninterrupted
    - Can go through several iterations to find a "stable" assignment (hence "incremental")
    - Avoids "stop-the-world" events where all consumers stop processing data 


#### Cooperative Rebalance, how to use?

    - Kafka Consumer:partition.assignment.strategy
        - RangeAssignor: assign partitions on a per-topic basis (can lead imbalance)
        - RoundRobin: assign partitions across all topics in round-robin fashion, optimal balance
        - SickyAssignor: balanced like RoundRobin, and then minimises partitions movements when consumer join/leave
            the group in order to minimize movements.
        - CooperativeStickyAssignor: rebalance strategy is identical to StickyAssignor but supports cooperative
            rebalances and therefore consumers can keep on consuming from the topic.
        - The default assignor is [ RangeAssignor, CooperativeStickyAssignor ], which will just a single rolling
            bounce that removes the RangeAssignor from the list.
    - Kafka Connect: already implemented (enabled by default)
    - Kafka Streams: turned on by default using StreamsPartitionsAssignor

### Static Group Membership

    - By default, when a consume leaves a group, its partitions are revoked and re-assigned 
    - If it joins back, it will have a new 'member ID' and new partitions assigned 
    - if you specify group.instance.id it makes the consumer a static member
    - Upon leaving, the consumer has up to session.timeout.ms to join back and get back its partitions (else they will
        be re-assigned), without triggering a rebalance.
    - This is helpful when consumers maintain local state and cache (to avoid re-building the cache)

## 51. Java Consumer Incremental Cooperative Rebalance - Practice

    i.e: ConsumerDemoCooperative.java

## 52. Java Consumer Auto Offset Commit Behavior

    - In the Java Consumer API, offset are regularly commited.
    - Enable at least once reading scenario by default (under conditions)
    - Offsets are committed when you call .poll() and auto.commit.interval.ms has elapsed
    - Example: auto.commit.interval.ms=5000 and enable.auto.commit=true will commit
    - Make sure messages are all successfuly processed before you call pool() again
        - If you don't, you will not be in at-least-once reading scenario
        - in that (rare) case, you must disable enable.auto.commit, and most likely most processing to a separate
            thread, and then from time-to-time call .commitSync() or .commitAsync() with the correct offsets 
            manually (advanced)

## 53. Programming - Advanced Tutorials
    
    If you are beginer and medium in Java. You don't need the advanced examples.

    i.e: ConsumerDemoAssignSeek.java
         ConsumerDemoRebalanceListener.java
         ConsumerDemoThreads.java
         ConsumerRebalanceListenerImpl.java










    

