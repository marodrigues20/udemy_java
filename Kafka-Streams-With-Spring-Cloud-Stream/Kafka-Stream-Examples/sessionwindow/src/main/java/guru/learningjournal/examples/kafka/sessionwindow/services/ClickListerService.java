package guru.learningjournal.examples.kafka.sessionwindow.services;

import guru.learningjournal.examples.kafka.sessionwindow.bindings.ClickListenerBinding;
import guru.learningjournal.examples.kafka.sessionwindow.models.UserClick;
import lombok.extern.log4j.Log4j2;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.SessionWindows;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.time.ZoneOffset;

@Log4j2
@Service
@EnableBinding(ClickListenerBinding.class)
public class ClickListerService {

    /**
     * - This method reads all the user clicks, and now we are ready to process them.
     * - What do we want to do?
     * - We want to group the click events by the user id.
     * - We will create a session window of five minutes and count the records.
     * - This example, looks like similar to the tumbling window example. The only difference is the window type
     * "SessionWindow" class.
     * - In the tumbling window example, we used TimeWindow class. However, this small change do a huge difference.
     * - TimeWindow are fixed in size. However, SessionWindow is variable in length. And the size of the window is
     * dependent on the user activity.
     *
     */
    @StreamListener("click-input-channel")
    public void process(KStream<String, UserClick> input) {

        input.peek((k, v) -> log.info("Key = " + k + " Created Time = "
                + Instant.ofEpochMilli(v.getCreatedTime()).atOffset(ZoneOffset.UTC)))
                .groupByKey()
                .windowedBy(SessionWindows.with(Duration.ofMinutes(5)))
                .count()
                .toStream()
                .foreach((k, v) -> log.info(
                        "UserID: " + k.key() +
                                " Window start: " +
                                Instant.ofEpochMilli(k.window().start())
                                        .atOffset(ZoneOffset.UTC) +
                                " Window end: " +
                                Instant.ofEpochMilli(k.window().end())
                                        .atOffset(ZoneOffset.UTC) +
                                " Count: " + v +
                                " Window#: " + k.window().hashCode()
                ));

    }
}
