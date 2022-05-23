# Section 7: CLI (Command Line Interface) 101

## Kafka CLI - how to invoke

    - They come bundled with the Kafka binaries
    - If you setup the $PATH variable correctly (from the Kafka setup part), then you should be able to invoke the CLI from anywhere on your computer.
    - If you installed Kafka using binaries, it should be either kafka-topics.sh (Linux, Mac, Windows), kafka-topic.bat (Windows non WSL2), 
    kafka-topics (homebrew, apt...)
    - Use the --bootstrap-server option everywhere, not --zookeeper

    // Correct option
    $ kafka-topics --bootstrap-server localhost:9092
    // Wrong option
    $ kafka-topics --zookeeper localhost:2181


## Kafka CLI: kafka-topics.sh

    - Kafka Topic Management

    1. Create Kafka Topics
    2. List Kafka Topics
    3. Describe Kafka Topics
    4. Increase Partitions in a Kafka Topics
    5. Delete a Kafka Topic


i.e: 0-kafka-topics.sh 

## Kafka CLI: kafka-console-producer.sh

    1. Produce without keys ( Data will distribute across all partitions)
    2. Produce with keys ( If you have the same key always go to the same partition)

    i.e: 1-kafka-console-producer.sh

## Kafka CLI: kafka-console-consumer.sh

    1. Consume from tail of the topic
    2. Consume from the beginning of the topic
    3. Show both key and values in the output

i.e: 2-kafka-console-consumer.sh

## CLI Consumer in Groups with kafka-console-consumer.sh

    - Learn about --group parameter
    - See how partitions read are divided amongst multiple CLI consumers

i.e: 3-kafka-console-consumer-in-groups.sh


## Consumer Group Management CLI - Kafka-consumer-group.sh


    1. List consumer groups
    2. Describe one consumer group
    3. Delete a consumer group

    i.e: 4-kafka-consumer-groups.sh


## Consumer Groups - Reset Offsets kafka-consumer-groups.sh

    1. Start / Stop Console Consumer
    2. Reset Offsets
    3. Start Console Consumer and see the outcome

    i.e: 5-reset-offsets.sh




