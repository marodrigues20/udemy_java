# Section 11: Kafka Stream in Functinal Style and Unit Testing

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
  