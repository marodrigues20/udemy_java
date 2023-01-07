package guru.learningjournal.example.kafka.streamingtest.configs;

import org.apache.kafka.streams.kstream.KStream;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Function;

/**
 * - This class is now a configuration class, and the configuration is automatically enabled.
 * - In a spring project, the configuration class is used to supply spring beans.
 * - So we will be supplying a bean method.
 */
@Configuration
@EnableAutoConfiguration
public class ListenerService {

    /**
     * - Adding @Bean.
     * - Spring will create this bean and place it in the classpath for the required usage.
     * - The first argument of the function is an input, and the second argument is an output.
     * - I want to read a KStream from a Kafka topic, and I also want to write a KStream to a Kafka topic.
     * - So, my input and output are KStream.
     */
    @Bean
    public Function<KStream<String, String>, KStream<String, String>> process() {

        return input -> input.mapValues(i -> i.toUpperCase());

    }
}
