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


## 32. Customize JSON Format


- By default, Jackson will generate JSON string with variable names as json attribute and local date in complex format.
- We can customize this items by using annotation.
- For example, we can have custom format for attribute names and date format.
- Customization handy especially if you works a lot with JSON.
- Different teams might have different standards, but we need to keep the standard to avoid miscommunication among team members.
- For example, In some teams I’ve worked, the standard for date format is ISO 8601.
- That way, we know how to send and receive date string, and convert it into date variable.
- In a REST API or messaging system that uses JSON for data exchange this kind of standards should be used.
- If you want to know more about REST API and jackson annotation, please refer to last section of the course.


- JSON Customization
  - Default:
    - someAttribute
    - Local date: verbose format
  - Customize by Jackson annotations
  - Different teams, different standards (some using snake_case, some using kebab-case)
  - Avoid miscommunication
  - Useful in data exchanges


- Reference Project: kafka-core-producer

- Created classes
  - Employee2JsonProducer.java
  - JsonConfig.java added 
    ```
     // By default, jackson will not write LocalDate as String. If you want it as String, add the bellow line
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
    ```    
  - Used the class KafkaCoreProducerApplication.java
    ```
    private Employee2JsonProducer producer;
    ```
  - Added in application.yaml file
    ```
    spring:
      jackson:
        date-format: yyyy-MM-dd
    ```  

33. Consuming JSON Message

Project Reference: kafka-core-consumer

Classes Created 
- config/JsonConfig.java
- entity/Employee
- EmployeeJsonConsumer.java

File changed

- application.yml
  ```
  spring:
  jackson:
    date-format: yyyy-MM-dd
  ```

  34. Consuming with Consumer Groups - Create Producer

  