package guru.learningjournal.examples.kafka.hellostreams.services;


import guru.learningjournal.examples.kafka.hellostreams.binding.KafkaListenerBinding;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.streams.kstream.KStream;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.annotation.StreamRetryTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@EnableBinding(KafkaListenerBinding.class)// We want to bind this class with the Spring Cloud Stream
// This class will trigger the Spring Cloud Stream Framework, connect to the Kafka input Channel/Binding using the
// Kafka Stream API and start consuming the input messages as a KStream
public class KafkaListenerService {

    // Spring Cloud Stream Framework will call this method and pass in the KStream
    @StreamListener("input-channel-1")
    public void process(KStream<String, String> input){
        input.foreach((k,v) -> log.info(String.format("key: %s, Value: %s", k, v)));
    }
}
