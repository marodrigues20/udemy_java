FAQ for Setup Problems
Please refer to this lecture first if you have set up problems.


We recommend starting Kafka with Conduktor if you're having issues even after troubleshooting



=====================

> Zookeeper - java.net.BindException: Address already in use

Something is already occupying your port 2181. Figure out which application it is and stop it

> Kafka - org.apache.kafka.common.KafkaException: Socket server failed to bind to 0.0.0.0:9092: Address already in use.

Something is already occupying your port 9092. Figure out what it is and stop it.
Otherwise, if you really insist, you can change the Kafka port by adding the following line to server.properties

# example for port 9093
listeners=PLAINTEXT://:9093
> My topics are losing their data after a while

This is how Kafka works. Data is only retained for 7 days.

> The topics list is disappearing

Make sure you have changed the Zookeeper dataDir=/path/to/data/zookeeper , and Kafka log.dirs=/path/to/data/kafka

> I have launched Kafka in a VM or in the Cloud, and I can't produce to Kafka

If you can't produce to Kafka, it's possible you are using a VM and this can break the Kafka behaviour. Please look at the annex lectures for solutions of how to deal with that. I strongly recommend doing this tutorial using the Kafka binaries and localhost