# Section 08: Handling Exception

## 41. KafkaListener Error Handler

### What We Will Do

- Spring default: log exception
- Able to implement our own error handler
- Scenario
  - Publish food order
  - Exception: consume invalid food amount

### Java Project

Project Reference: kafka-core-producer
Classes Added / Modified: 
    - FoodOrder.java
    - FoodOrderProducer.java
    - FoodOrder.java

Project Reference: kafka-core-consumer
Classes Added / Modified: 
    - FoodOrderConsumer.java
    - FoodOrderErrorHandler.java
    - FoodOrder.java