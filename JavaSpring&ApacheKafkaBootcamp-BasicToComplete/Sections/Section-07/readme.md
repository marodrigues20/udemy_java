# Section 07 - Working with JSON Messages

## 30. Why JSON?

- Use JSON as standard format
- Java etc has library to create & parse JSON
- Application focus on business logic instead of parsing data
- Java uses Jackson or GSON library
- This course will use Jackson

- Added two dependencies in build.gradle.kts in both projects
  
  ```
  implementation("com.fasterxml.jackson.core:jackson-databind:${jacksonVersion}") 
  implementation("com.fasterxml.jackson.core:jackson-annotations:${jacksonVersion}")
  ```

## 31. Producing JSON Message

- Reference Project: kafka-core-producer

- Created classes 
  - JsonConfig.java
  - Employee.java
  - EmployeeJsonProducer.java
  - Used the class KafkaCoreProducerApplication.java
    ```
    for(int i = 0; i < 5; i ++){
	var emp = new Employee("emp-" + i, "Employee " + i, LocalDate.now());
	producer.sendMessage(emp);
    }
    ```  

1. Run the Project
2. Consume the messages by the cli command bellow: 
  - $ $ kafka-console-consumer.sh --bootstrap-server localhost:9092 --offset earliest --partition 0 --topic t-employee

3. Next Lecture create in advance a new topic.
   1. $ kafka-topic.sh --bootstrap-server localhost:9092 --create --partitions 1 --replication-factor 1 --topic t-employee-2


