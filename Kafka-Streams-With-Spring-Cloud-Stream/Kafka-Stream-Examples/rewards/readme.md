# Section 08 - KTable and Aggregation

## 34. Reducing Kafka Stream

### How can we implement the sum() operation

- Simple! We can implement the sum() operation for this field.
- However, Kafka Stream API does not offer a sum() function.
- Instead, Kafka Stream API offers you only two generic formulas:
- Reduce and Aggregate
- So, how do we implement it using the reduce method?

More information check out on Section folder in subSection 34.

### Which steps to create the application

1. Create a Data Model
2. Create your business logic
3. Define your Input-Output Channels
4. Create your Binding Interface
5. Create your Listener Method

### Assumption

- This application will be reading invoices sent by the Avro invoice generator example (project java: avroposgen).
- So we need three input data model classes that we used in the earlier example.
- we will be processing the input invoices and generating notification messages to the loyalty topic.
- We will read Avro messages and also produce Avro Messages

### What do we need to Pom.xml file

1. avro
2. kafka-streams-avro-serde
3. avro-maven-plugin
4. confluent repository

### Result of Aggregations

- The result of aggregation is always a KTable
- Every Kafka Streams aggregation project will internally create one or more KTables using the local Rocks DB database.
- Hence, you must configure these two configuration for your aggregation project
- If you don't, the framework will take some default values, confusing at later stages.

Snipped properties.YAML file
```
state.dir: state-store
```

- The aggregation is a complex internal process. 
- So your application will be writing intermediate data to one or more KTable state stores.
- It might reread it from the internal KTable.
- You may also need to use the groupBy() and change the grouping key.
- In that case, your data will go back to an internally created Kafka topic and come back again after repartitioning.
- In fact, every legal KTable is also backed up in some internally created Kafka topics.
- This is necessary to achieve fault tolerance.
- If your machine fails, and you are restarting your application on a different machine. Then the framework will recreate
the local KTable from the backup copy.
- Hence, every local KTable also creates a backup copy in the Kafka cluster
- Your application will read/write data to Kafka cluster and local Rocks DB storage and all of these operations will go
  through the serialization and deserialization process. You must specify a Serde for each of those operations.
- As a best practice, you must define a default Serde for your application.

```
default:
    key:
      serde: org.apache.kafka.common.serialization.Serdes$StringSerde
    value:
      serde: io.confluent.kafka.streams.serdes.avro.SpecificAvroSerde

```

- If you are not defining a default Serde, then you will be forced to define a Serde at each step.
- Defining Serdes at various places in your application will create code-clutter and open possibilities of multiple 
serialization-related defects.
- The best practice is to use one single serialization format for all your input and output data models and configure a 
default Serde for the same.


### Earlier Examples

- We tried to mix and match JSON and AVRO Serdes within the same application in the earlier examples.
- However, going foward, we will be following the best practice, use a single type of serialization and avoid dealing with a combination of different Serdes.
- That is why I am setting the default Serde in this example.



