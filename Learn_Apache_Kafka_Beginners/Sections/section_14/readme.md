# Section 14. Real World Insights and Case Studies (Big Data / Fast Data)

### 95.Choosing Partition Count & Replication Factor

    - The two most important parameters when creating a topic.
    - They impact performance and durability of the system overall.

    - It is best to get the parameters right the first time!
        - If the partitions count increases during a topic lifecycle, you will break your keys ordering guarantees
        - If the replication factor increases during a topic lifecycle, you put more pressure on your cluster, 
            which can lead to unexpected performance decrease.

### Choosing the Partitions Count

    - Each partition can handle a throughput of a few MB/s (measure it for your setup!)
    - More partitions implies:
        - Better parallelism, better throughput
        - Ability to run more consumers in a group to scale (max as many consumers per group as partitions)
        - Ability to leverage more brokers if you have a large cluster
        - BUT more elections to perform for Kookeeper (if using Zoopeer)
        - BUT more files opened on Kafka

    - Guideline:
        - Partitions per topic = MILLION DOLLAR QUESTION
            - (Intuitionj) Small cluster (< 6 brokers): 3x #brokers
        - Adjust for number of consumers you need to run in parallel at peak throughput
        - Adjust for producer throughput (increase if supor-high throughput or projected increase in the next 2 years)
    - TEST! Every Kafka cluster will have differet performance.
    - Don't systematically create topics with 1000 partitions!


### Choosing the Replication Factor

    - Should be at least 2, usually 3, maximum 4
    - The higher the replication factor (N):
        - Better durability of your system (N-1 brokers can fail)
        - Better availability of your system (N-min.insync.replicas if producer acks=all)
        - BUT more replication (higher latency if acks=all)
        - BUT more disk space on your system (50% more if RF is 3 instead of 2)

    - Guidelines:
        - Set it to 3 to get started (you must have at least 3 brokers for that)
        - If replication performance is an issue, get a better broker instead of less RF
        - Never set it to 1 in production

### Cluster guidelines

    - Total number of partitions in the cluster:
        - Kafka with Zookeeper: max 200,000 partitions (Nov2018) - Zookeepr Scaling limit 
            - Still recommend a maximum of 4,000 partitions per broker (soft limit)
        - Kafka with KRaft: potentially millions of partitions

    - If you need more partitions in your cluster, add brokers instead
    - If you need more than 200,000 partitions in your cluster (it will take time to get there!), follow the Netflix model and create more 
        Kafka clusters.

    - Overall, you don't need a topic with 1000 partitions to achieve high throughput. Start at a reasonable number and test the performance.


    ### Topic Naming Conventions

        - Naming a topic is "free-for-all"
        - It's better to enforce guidelines in your cluster to ease management
        - You are free to come up with your own guideline

        - Next is an example for a blog post: https://cnr.sh/essays/how-paint-bike-shed-kafka-topic-naming-conventions


    ### Topic Naming Convenstions

        - FROM: https://cnr.sh/essays/how-paint-bike-shed-kafka-topic-naming-conventions
        - <message type>.<dataset name>.<data name>.<data format>
        - Message Type:
            - logging: For logging data (sl4j, syslog, etc)
            - queuing: For classical queuing use cases,
            - tracking: For tracking events such as user clicks, page views, ad views, etc.
            - etl/db: For ETL and CDC use cases as database feeds.
            - streaming: For intermediate topics created by stream processing pipelines
            - push: For data that's being pushed from offline (batch computation) environments into online environments.
            - user: For user-specific data such as scratch and test topics.

        - The dataset name is analogous to a database name in traditional RDMBS system. It's used as a category to group topics
          together.
        - The data name field is analogous to table name in traditional RDBMS system, though it's fine to include futher dotted
            notation if developers wish to impose their own hierarchy within the dataset namespace.
        - The data format for example .avro, .json, .text, .protobuf, .csv, .log
        - Use snake_case


## 97.Case Study - MovieFlix

    - MovieFlix is a company that allow you to watch TV Shows and Movies on demand. The business wants the following cababilities:

    - Make sure the user can resume the video where they left it off
    - Build a user profile in real time
    - Recommend the next show to the user a real time
    - Store all the data in analytics store 

    - How would you implement this using Kafka?


### Video Analytics - MovieFlix - Architecture

    - Video Player (while playing)
    - Video Position Service (producer)
    - Resuming Service (Consumer)
    - Show Position Topic
    - Movies and TV Shows Portal / Website
    - Recommendations Service (Consumer)
    - Recommendations Topic
    - Recommendation Engine in Real Time ( Kafka Stream) - Generate content from Show Position Topic and send back to Recomendation Topic
    - Analytics consumer (Kafka Connect)
    - Analystics Store (Hadoop)



### Video Analystics - MovieFlix - Comments

    - show_position_topic
        - is a topic that can have multiple producers
        - Should be highly distributed if high volume > 30 partitions
        - If I were to choose a key, I would choose "user_id"
    
    - recommendations topic:
        - The Kafka streams recommendation engine may source data from the analytical store for historical training
        - May be a low volume topic
        - If I were to choose a key, I would choose "user_id"



## 98. IoT Example - GetTaxi

    - GetTaxi is a company that allows people to match with taxi drives on demand, right-away. The business wants the following 
      capabilities:

    - The user should match with a close by driver
    - The pricing should "surge" if the number of drivers of drivers are low or the number of users is high
    - All the position data before and during the ride should be stored in an analystics store so that the cost can be 
        computed accurately

    - How would you implement this Kafka?

### IoT Example - GetTaxi - Architecture

    - User Application 
    - User Position Service (Producer)
    - Taxi Driver Application 
    - Taxi Position Service (Producer)
    - Taxi Cost Service (Consumer)
    - user_position (topic)
    - taxi_position (topic)
    - Surge pricing (topic)
    - Surge Pricing computation model (Kafka Stream)
    - Analytics consumer (Kafka Connect)
    - Analytics Store (Amazon S3)

### IoT Example - GetTaxi - Comments

    - taxi_position, user_position:
        - Are topics that can have multiple producers
        - Should be highly distributed if high volume > 30 partitions
        - If I were to choose a key, I would choose "user_id", "taxi_id"
        - Data is ephemeral and probably doesn't need to be kept for a long time

    - surge_pricing topic:
        - The computation of Surge pricing comes from the Kafka Stream application
        - Surge pricing may be regional and therefore that topic may be high volume
        - Other topic such as "weather" or "events" etc can be included in the Kafka Streams application


## 99. Case Study - MySocialMedia

## CQRS (Command Query Responsability Segregation) - MySocialMedia

    - MySocialMedia is a company that allows you people to post images and others to react by using "likes" and "comments".
        The business wants the following capabilities.

    - Users should be able to post, like and comment
    - Users should see the total numbers of likes and comments per post in real time
    - High volume of data is expected on the first day of launch
    - Users should be able to see "trending" posts

    - How would you implement this using Kafka?

### CQRS - MySocialMedia - Architecture

    - posts (topic)
    - likes (topic)
    - comments (topic)
    - post_with_counts (topic)
    - Treding posts (topic)
    - User Posts
    - Posting Service (Producer) - Commands
    - User likes / comments
    - Like / Comment Service (Producer) - Commands
    - Website 
    - Refresh Feed Service (Consumer) - Reads
    - Treding Feed Service (Kafka Connect) - Reads
    - Total Likes / Comments Computation (Kafka Stream) - Queries
    - Treding posts in the past hour (Kafka Streams) - Queries


### CQRS - MySocialMedia - Comments

    - Responsibilities are "segregated" hence we can call the model CQRS (Command Query Responsibility Segregation)
    - Posts
        - Are topics that can have multiple producers
        - Should be highly distributed if high volume > 30 partitions
        - If I were to choose a key, I would choose "user_id"
        - We probably want a high retention period of data for this topic
    - Likes, Comments
        - Are topics with multiple producers
        - Should be highly distributed as the volume of data is expected to be much greater
        - If I were to choose a key, I would choose "post_id"
    - The data itself in Kafka should be formatted as "events":
        - User _123 created a post_id 456 at 2pm
        - User _234 liked post_id 456 at 3pm
        - User _123 deleted a post_id 456 at 6pm

## 100. Case Study - MyBank

### Finance application - MyBank

    - MyBank is a company that allows real-time banking for its users. It wants to deploy a brand-new capability to alert users in case of 
        large transactions.

    - The transaction data already exist in a database
    - Thresholds can be defined by the users
    - Alerts must be sent in real time to the users

    - How would you implement this using Kafka?

### Finance application - MyBank - Architecture

    - Database of Transactions
    - Kafka Connect Source CDC Connect (Debezium)
    - bank_transactions (topic)
    - Users set their threshold in Apps
    - App Threshold Service (Producer)
    - user_settings (topic)
    - Users see notifications in their apps
    - Notification Service (Consumer)
    - user_alerts (topic)
    - Real time Big Transactions Detection (Kafka Streams)

### Finance application - MyBank - Comments

    - Bank Transactions topics:
        - Kafka Connect Source is a great way to expose data from existing databases!
        - There are tons of CDC (change data capture) connects for technologies such as PostgreSQL, Oracle, MySQL, SQLServer, MongoDB etc.

    - Kafka Streams application:
        - When a user change their settings, alerts won't be triggered for past transactions

    - User threshold topics:
        - It is better to send events to the topic (User 123 enabled threshold at $1000 at 12pm on July 12th 2018) than sending the state of
            the user: (User 123:threshold $1000)


## 101. Case Study - Big Data Ingestion

    - It is common to have "generic" connectors or solutions to offload data from Kafka to HDFS, Amazon S3, and ElasticSearch for example

    - It is also common to have Kafka serve a "speed layer" for real time applications, while having a "slow layer" which helps with 
        data ingestion into stores for later analytics

    - Kafka as a front to Big Data Ingestion is a common pattern in Big Data to provide an "ingestion buffer" in front of some stores


## 102. Case Study - Loggin and Metrics Agregation

    - One of the first case of Kafka was to ingest logs and metrics from various applications
    
    - This kind of deployment usually wants high throughput, has less restriction regarding data loss, replication of data, etc

    - Application logs can end up in your favorite loggin solution such as Spluck, CloudWatch, ELK, etc...


### 102. Case Study - Loggin and Metrics Aggregattion - Architecture

    - Aplication 1
    - Aplication 2
    - Aplication 3
    - aplication_logs (topic)
    - aplication_metrics (topics)
    - Kafka Connect Sink
    - Splunk


    
    




    