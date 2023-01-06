# Section 11: Kafka Stream in Functinal Style and Unit Testing

## 47. Stream Listener Manual Testing

- Let's learn about unit testing aspect of your Kafka Streams application.
- Testing a Kafka Streams application requires you to have a Kafka cluster.
- If you do no have a Kafka cluster, you cannot run your Kafka Stream application, and you cannot test it.
- However, the Spring framework offers you an Embedded Kafka cluster for implementing automated test cases.

### Project Example

Java Project: simpletest

### Which approach for testing

1. Manual Testing
2. Automated Testing

### Manual Testing

1. Start Kafka Cluster.
2. Create Input/Output Topics
3. Send some Input Messages to the input topic
4. Start your application
5. Consume messages from the output topic
6. Compare the output results with the expected results

--------------------------------------------------------------------------------------------------------------------