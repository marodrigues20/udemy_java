# Section 5: Starting Kafka

## Starting Kafka - a big challenge

    - No brainer solution: staring Kafka using Conduktor (FREE - all platform) using a UI
    - Alternatives for Mac OS X
        - Starting Kafka with Zookeeper (recommended)
        - Starting Kafka without Zookeeper - ZRaft mode (for development only)
        - Installing Kafka using brew
    - Alternatives for Linux
        - Starting Kafka with Zookeeper (recommended)
        - Starting Kafka without Zookeeper - ZRaft mode (for development only)
    - Alternatives for Windows
        - Windows WSL2: Starting Kafka with Zookeeper (recommended)
        - Windows WSL2: Starting Kafka without Zookeeper - KRaft mode (for development only)
        - Plain Windows: not recommended, there are caveats that I will show you, Conduktor helps

## Important: Starting Kafka

    - For the rest of the course, we will start Kafka...
    - Locally: It will be accessible on 127.0.0.1 (localhost)
    - Natively: We will use the native Kafka Binari from the website
    - With ONE Broker and (optionally) ONE Zookeeper only (perfect for development)

    - Note: The production-ready Kafka Cluster setup takes over 4 hours and is dedicated to another course in the Apache Kafka Series.


## Apache Kafka Installation Steps + Videos to Watch

Fully written instructions at https://conduktor.io/kafka/starting-kafka 

    - Install Kafka CLI Tools using Binaries / Install Kafka CLI Tools using "brew"
    - Start Kafka using Binaries

## Starting Kafka using Conduktor

    - Easiest way to get started with Apache Kafka

    - Free - works with Linux, Mac, Windows, all versions
    - Fixes many issues you have with Kafka on Windows
    - Start Apache Kafka any version (easy to switch)
    - Can add separate componenets such as Schema Registry

    - https://www.conduktor.io/download to get started


## Mac OS X: Setup Kafka Binaries

    - Necessary step regardless if you use Conduktor or not to start Kafka
    - This is so that we can start running Kafka CLI commands

    1. Install Java JDK version 11
    2. Download Apache Kafka from https://kafka.apache.org/download under Binary Downloads
    3. Extract the contents on you Mac
    4. Setup the $PATH environment variables for easy access to the kafka binaries

Note: This step can be replaced with "brew" which is demonstrated after all Mac setup videos


## Mac OS X: One Kafka Broker - with Zookeeper

    1. Start Zookeeper using the binaries
    2. Start Kafka using the binaries in another process

    $ zookeeper-server-start.sh ~/kafka_2.13-3.1.0/config/zookeeper.properties

    $ kafka-server-start.sh ~/kafka_2.13-3.1.0/config/server.properties


## Mac OS X: Start Kafka using brew

    1. Install brew
    2. Install Kafka using brew (will install Java JDK for you)
    3. Start Zookeeper using the binaries
    4. Start Kafka using the binaries in another process

    $ brew install kafka
    $ /usr/local/bin/zookeeper-server-start /usr/local/etc/zookeeper/zoo.cfg
    $ /usr/local/bin/kafka-server-start /usr/local/etc/kafka/server.properties

    Note: When you install kafka using brew, you don't need to add the extension .sh to run the cli commands.

