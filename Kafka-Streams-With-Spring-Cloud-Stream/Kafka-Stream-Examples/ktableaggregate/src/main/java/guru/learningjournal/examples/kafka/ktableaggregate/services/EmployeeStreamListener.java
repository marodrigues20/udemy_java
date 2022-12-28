package guru.learningjournal.examples.kafka.ktableaggregate.services;

import guru.learningjournal.examples.kafka.ktableaggregate.bindings.EmployeeListenerBinding;
import guru.learningjournal.examples.kafka.model.Employee;
import lombok.extern.log4j.Log4j2;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.kstream.KStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.stereotype.Service;

@Log4j2
@Service
@EnableBinding(EmployeeListenerBinding.class)
public class EmployeeStreamListener {

    @Autowired
    RecordBuilder recordBuilder;

    /**
     * The reason why I don't read as a KTable. My input data does not come with a key.
     * The key to the input data stream is null and we know that the KTable must have a key.
     * So, even if I want to, I cannot read this input stream as a KStream.
     *
     * aggregate() method explained:
     * First Argument:
     * Takes no input and returns the initial aggregate.
     * Second Argument:
     * It is an adder lambda. When an employee joins a department, his record will be passed here.
     * Last Argument:
     * It is a subtractor lambda. When an employee leaves a department, his record will be passed here.
     *
     */

    @StreamListener("employee-input-channel")
    public void process(KStream<String, Employee> input) {

        input.map((k, v) -> KeyValue.pair(v.getId(), v))
                .peek((k, v) -> log.info("Key = " + k + " Value = " + v))
                .toTable()
                .groupBy((k, v) -> KeyValue.pair(v.getDepartment(), v))
                .aggregate(
                        () -> recordBuilder.init(),
                        (k, v, aggV) -> recordBuilder.aggregate(v, aggV),
                        (k, v, aggV) -> recordBuilder.subtract(v, aggV)
                ).toStream()
                .foreach((k, v) -> log.info("Key = " + k + " Value = " + v));
    }
}
