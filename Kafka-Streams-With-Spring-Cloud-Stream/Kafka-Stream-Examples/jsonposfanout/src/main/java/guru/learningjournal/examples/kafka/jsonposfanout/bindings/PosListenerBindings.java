package guru.learningjournal.examples.kafka.jsonposfanout.bindings;

import guru.learningjournal.examples.kafka.jsonposfanout.model.PosInvoice;
import guru.learningjournal.examples.kafka.model.HadoopRecord;
import guru.learningjournal.examples.kafka.model.Notification;
import org.apache.kafka.streams.kstream.KStream;
import org.springframework.cloud.stream.annotation.Input;

public interface PosListenerBindings {

    @Input("notification-input-channel")
    KStream<String, PosInvoice> notificationInputStream();
    @Input("notification-output-channel")
    KStream<String, Notification> notificationOutputStream();
    @Input("hadoop-input-channel")
    KStream<String, PosInvoice> hadoopInputStream();
    @Input("hadoop-output-channel")
    KStream<String, HadoopRecord> hadoopOutputStream();
}
