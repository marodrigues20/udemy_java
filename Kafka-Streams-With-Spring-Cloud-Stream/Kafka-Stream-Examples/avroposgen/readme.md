# Section 5: Producing Data to Kafka

- This example Produce AVRO Message and use Schema Registry
  - We add avro-maven-plugin
  - We add a new repository: https://packages.confluent.io/maven/ to be able to download kafka-avro-serializer dependency.
  - The kafka-avro-serializer dependency is needed to use in serializer the value of message. 