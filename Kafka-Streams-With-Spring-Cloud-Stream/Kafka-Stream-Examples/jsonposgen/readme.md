# Section 05 - Producing Data to Kafka

## 15. Producting JSON Message

    - We use spring-kafka (Kafka Client API) - Publish / Subscribe Pattern.
    - We send a JSON Message to Kafka Topic using @JsonProperty to make the class definition a JSON friendly class.

### This project will produce msg to:
    - pos-topic in JSON format

### Who will consume the msg from "pos-topic"
    - jsonposfanout java project