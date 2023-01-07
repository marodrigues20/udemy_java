# Section 11: Kafka Stream in Functinal Style and Unit Testing

## 47. Stream Listener Manual Testing

- Let's learn about unit testing aspect of your Kafka Streams application.
- Testing a Kafka Streams application requires you to have a Kafka cluster.
- If you do no have a Kafka cluster, you cannot run your Kafka Stream application, and you cannot test it.
- However, the Spring framework offers you an Embedded Kafka cluster for implementing automated test cases.

### Project Example

Java Project: simpletest

### Which approach for testing

1. Manual Testing
2. Automated Testing

### Manual Testing

1. Start Kafka Cluster.
2. Create Input/Output Topics
3. Send some Input Messages to the input topic
4. Start your application
5. Consume messages from the output topic
6. Compare the output results with the expected results

--------------------------------------------------------------------------------------------------------------------

## 48. Stream Listener Automating Test Cases

- We created a super simple streaming application in the previous lecture and applied a manual testing approach to test it.
- Now we want to automate the testing so we can repead it without spending manual time and invervention.
  
Java Project: simpleTest

- For testing your Kafka Streams application, you will need some additional dependencies.

### Adding Dependencies.

- Your Spring starter project might come with the spring-cloud-stream test-binder
- However, we do not need this guy.
- I mean, a test binder is also an alternative for testing spring-cloud-stream application.
- However, we want to test Kafka specific project.
- So let's use Kafka specific things.
- Remove test binder for testing spring-cloud-stream dependency from pom.xml file.

```
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-stream</artifactId>
    <scope>test</scope>
    <classifier>test-binder</classifier>
    <type>test-jar</type>
</dependency>
```

- For testing Kafka Streams, you will need the following three dependencies:

1. junit
2. spring-kafka-test
3. kafka-streams-test-utils

- Other than these three, you should also have"

4. spring-boot-starter-test

- Which is already there as part of the default project setup.


### Embedded Web Server

- A typical spring boot application is expected to start an embedded web server.
- But we do not want that.
- We want to test Kafka Stream functionality, so we do not need a Web Server.
- So let me set that expectation using the @SpringBootTest parameters.
  
```
@SpringBootTest(
		webEnvironment = SpringBootTest.WebEnvironment.NONE,
		properties = {"server.port=0"})
```

### Test steps for Automated Tests

1. Start Kafka Cluster. 
    - For automated testing, we cannot start a local Kafka Cluter.
    - So, we will be using an Embedded kafka Cluster, which will start within the same JVM.


```
@ClassRule
	public static EmbeddedKafkaRule embeddedKafkaRule = new EmbeddedKafkaRule(1, true, 1,
			"input-topic", "output-topic");
	private static EmbeddedKafkaBroker embeddedKafka = embeddedKafkaRule.getEmbeddedKafka();
```

- We are using JUnit @ClassRule to create a static EmbeddedKafkaRule.
- This rule sets some initial configuration parameters for the embedded kafka cluster.
- These two lines, the SpringBootTest framework will work together with the JUnit framework and start an embedded Kafka cluster for you.

2. Create Input/Output Topics
   - This step is finished above as well.

3. Consume messages from the output topic
   - Let's set up a consumer to use it to consume and validate the output.


```
private static Consumer<String, String> consumer;

	@BeforeClass
	public static void setUp() {

		Map<String, Object> consumerProps = consumerProps("group", "false", embeddedKafka);
		consumerProps.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
		DefaultKafkaConsumerFactory<String, String> cf = new DefaultKafkaConsumerFactory<>(consumerProps);
		consumer = cf.createConsumer();
		embeddedKafka.consumeFromAnEmbeddedTopic(consumer, "output-topic");
	}
```

5. Start your application
   - Just use

```
@Autowired
	StreamsBuilderFactoryBean streamsBuilderFactoryBean;
```

- The Spring framework will start your application.



### Use System Variable to connect to Embedded Kafka

- My Spring Boot Application will start, and it will try to connect to the Kafka cluster which I defined in my application YAML.
- So, I defined the broker configuration as my localhost 

```
brokers: localhost:9092
```

- But we do not want our application to connect to the localhost when we are running our test case.
- Instead, we want it to connect to the embedded Kafka Cluster.
- And we also do not want to disturb our application YAML file.
- Then we create a new application YAML file inside resource folder in test folder.
- In this way, my tests just will use the application YAML file from the test folder.
- Now, you can change this YAML file to connect to the embedded Kafka cluster.
- You can do it using a system variable.
  
```
 brokers:  ${spring.embedded.kafka.brokers}
```

- The embedded Kafka broker starts on a random port.
- The framework will also set this system variable so we can use it to configure our application.
  
--------------------------------------------------------------------------------------------------------------------------------------

## 49. Functional Style of Coverting Stream Listeners

- I mentioned that the Spring Cloud Streams framework is envolving to support the functional style of conding a stream processing application
- Kafka Streams binder supports this style very well.
- Your current projects might be using the imperative programming style.
- In this leasson. we are going to learn the functional style of the Spring Cloud Stream application.

### Example Project

- Java Project: streamingtest

### Solution

- We created a super simple stream processing application to convert a streaming text to upper case.
- In this example, we are going to create the same application  using the functional style.


### Naming Convention to name our input/output channel in YAML file

- We named this channel process-in-0.

```
spring:
  cloud:
    stream:
      bindings:
        process-in-0:
          destination: input-topic
```

- Why?
- Because we planned to name my listener method as the process.
- The hyphen-in is to tell that the process method will be using this channel for reading input records.
- The hyphen-0 is the position index of this channel.
- I plan to read only one input channel in the process method, so I named it as process-in-0.
- If I wanted to use two input channels in the process method, I should create one more channel and name it as process-in-1.
- Similarly, the process-out-0 is my first output channel.

```
spring:
  cloud:
    stream:
      bindings:
        process-in-0:
          destination: input-topic
        process-out-0:
          destination: output-topic
```

- This naming convention is used by the spring cloud stream functional API.
- What does it mean?
- It means you can remove these channel definitions from your YAML file.
- Because the functional API will automatically create these channels.
- You do not need to create these channels manually in your application YAML.
- So, let me remove it.

```
spring:
  cloud:
    stream:
      bindings:
        #process-in-0:
        #  destination: input-topic
        #process-out-0:
        #  destination: output-topic
      kafka:
        streams:
          binder:
            brokers:  ${spring.embedded.kafka.brokers}
            configuration:
              commit.interval.ms: 100
              default:
                key.serde: org.apache.kafka.common.serialization.Serdes$StringSerde
                value.serde: org.apache.kafka.common.serialization.Serdes$StringSerde
```

- This YAML should work if you are using a functional approach to creating a streaming application.
- The input and output channels are automatically created by the framework.
- Great! Since we do not define the input/output channel, we also do not define the binding interface.

- Let's see the old project

- Here is my listener binding in the older project (Java Project: simpleTest)

```
import org.apache.kafka.streams.kstream.KStream;
import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;

public interface ListenerBinding {

    @Input("process-in-0")
    KStream<String, String> inputStream();

    @Output("process-out-0")
    KStream<String, String> outStream();

}
```

- This binding interface is not required for the functional approach.
- Why?
- Because the input/output channels and the required binding is automatically created by the framework.


### 5 Typical Steps to Create Kafka Stream Application

1. Define your Input-Output Channels
2. Create your Binding Interface
3. Create a Data Model
4. Create your business logic
5. Create your Listener Method

- if you are using a fucntional approach, the first two steps are gone.
- We do not need them

3. Create a Data Model
4. Create your business logic
5. Create your Listener Method

- The current example is super simple.
- We simply take a string, convert it to upper case and return.
- So we do not require defining a data model and a business logic.
- you can directly jump to the last step of defining a listener.

- The Imperative Approach requires you define a service class.
- However, the functional approach requires you to create a configuration class.


### Difference Inperative way and Fuctional Programming way

- Here is the older listener from previous example (Java Project: simpleTest)

```
@Log4j2
@Service
@EnableBinding(ListenerBinding.class)
public class ListenerService {

    @StreamListener("process-in-0")
    @SendTo("process-out-0")
    public KStream<String, String> process(KStream<String, String> input) {

        input.foreach((k,v) -> log.info("Received Input: {}",v));
        return input.mapValues(v -> v.toUpperCase());

    }
}
```

- This listener process() method takes a KStream and returns a KStream.
- And the body of the process() method implements the business logic.
- Now, let's jump to the functional approach.

```
@Configuration
@EnableAutoConfiguration
public class ListenerService {

    @Bean
    public Function<KStream<String, String>, KStream<String, String>> process() {

        return input -> input.mapValues(i -> i.toUpperCase());

    }
}
```

- In this approach, the process() method returns a function.
- So the process method will always start with a return statement and a lambda expression.
- The lambda takes one input argument.
- The input argument is nothing but your input KStream.
- The return of the lambda expression is also a KStream.
- Hence, we defined the functional signature here with two arguments.
- The first argument is the input type, which actually defines the type of the lambda input.
- The second argument is output type, which defines the lambda return type.
- The framework will automatically define the input channels and the binding interface for this bean.
- The naming is super simple.
- The input KStream will be mapped to the process-in-0.
- The output KStream is mapped with the process-out-0.
- However, we still have one missing information - the topics names for these channels?
- I mean, the framework will create two channels and name them process-in-0 and process-out-0.
- But the framework still doesn't know the Kafka topic name.
- So how do we supply the topic name?
- We have two options.

1. Create the topic name using the channel name.
2. Define the channel destination in your YAML.

- In the first approach, you must create the input topic using the input channel name.
- So for this example, we should create an input topic and name it as process-in-0.
- Similarly, we should create an output topic and name it process-out-0.
- However, creating such cryptic topic names might not be a good idea.
- So, you can take the second approach and define a topic mapping in your YAML file.



### Reading and Writing from multiple topics and join them

- You can get more examples and references for the functional approach in the documentation
- Where to refer to more information.

Go to:

1. https://spring.io
2. Click on Project and go to Spring Cloud
3. Inside the Spring Cloud, you should see a link to Spring Cloud Stream.
4. Click on it.
5. Click on "Learn" tab.
6. Click on "Reference Doc."
7. End.

- If you want to learn more about Spring Cloud framework, go to the overview link.
- But if you want to learn more about Kafka Stream, refer to the Kafka Streams Binder link.
- This full documentation offers you a wealth of information.
- I already covered most of it.
- But I recommend you to use this documentation for more information and learn few other things which I could not cover in this course.
- For example, the programming model and the functional style section will give you enough information to read multiple inputs and 
- write multilple outputs.
- If your project wants to implement a functional style, you must refer to this section at least once.