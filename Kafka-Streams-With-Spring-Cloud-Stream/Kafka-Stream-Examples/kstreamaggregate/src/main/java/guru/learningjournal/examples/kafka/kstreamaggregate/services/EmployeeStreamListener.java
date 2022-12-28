package guru.learningjournal.examples.kafka.kstreamaggregate.services;

import guru.learningjournal.examples.kafka.kstreamaggregate.bindings.EmployeeListenerBinding;
import guru.learningjournal.examples.kafka.model.Employee;
import lombok.extern.log4j.Log4j2;
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
     * How works the aggregate method:
     * First agument is the initializer lambda does not take any argument, and it should return the initial state of the
     * aggregate record.
     * What do I mean by the initial state of teh aggregate record?
     * We want to return a DepartmentAggregate record with initial values set to zero.
     * Second argument is the aggregator lambda.
     * The aggregator lambda takes three arguments: the key, the value, and the aggregated value.
     * What is the key? Department Id
     * We changed it using the GroupBy() method.
     * When the new records come for the first time, the framework will call the aggregator lambda and pass the
     * depatementId and the employee record to the first two arguments. That means the key is a departement id,
     * and the value is an employee record. The last argument is DepartmentAggregate.
     * Now, the aggregation is super simple.
     *
     *
     *
     */
    @StreamListener("employee-input-channel")
    public void process(KStream<String, Employee> input){

        input.peek((k, v) -> log.info("Key: {}, Value: {}", k, v))
                .groupBy((k, v) -> v.getDepartment())
                .aggregate(
                        () -> recordBuilder.init(),
                        (k, v, aggV) -> recordBuilder.aggregate(v, aggV)
                ).toStream()
                .foreach((k, v) -> log.info( "Key = " + k + " Value = " + v.toString()));

    }

}
