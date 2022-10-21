package guru.learningjournal.examples.kafka.hellostreams.binding;

import org.apache.kafka.streams.kstream.KStream;
import org.springframework.cloud.stream.annotation.Input;

// The primary propose of this interface is to define input and output bindings/channels.
// We want to listen to an input topic
public interface KafkaListenerBinding {

    // This method will read from a Kafka Topic and return a KStream
    // The KStream is a Kafka Message stream made up of a string key and a string value
    @Input("input-channel-1") // It's deprecated in favor of the functional programming model. The library that we are
    // using want's to force us to use functional programming model. If I use lower versions the decricated annotation will disappear.
    KStream<String, String> inputStream();
}
