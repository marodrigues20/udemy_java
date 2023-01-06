package guru.learningjournal.examples.kafka.simpletest.services;

import guru.learningjournal.examples.kafka.simpletest.bindings.ListenerBinding;
import lombok.extern.log4j.Log4j2;
import org.apache.kafka.streams.kstream.KStream;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Service;

/**
 * - I created a channel name using the listener method name.
 * - My Listener method name is the process.
 * - So I created an input channel for this processing method and named it process-in.
 * - I also suffixed the channel name using a 0 (zero), because this is the first input channel for this method.
 * - If I use two input channels for this process-method, I can name the second channel as process-in-1.
 * - Similarly, If I am using three inputs for the same method, I can name it as process-in-2
 * - The point is straight.
 * - You can give whatever name you want to your channels. However, the convetion is to use the method name for your
 * channel name.
 * - Then you can suffix the channel name with -in or -out depending on the purpose of the channel.
 * - Finally, you will suffix the channel name with an -index-number.
 * -
 */
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
