package guru.learningjournal.examples.kafka.otpvalidation.services;

import guru.learningjournal.examples.kafka.otpvalidation.bindings.OTPListenerBinding;
import guru.learningjournal.examples.kafka.otpvalidation.model.PaymentConfirmation;
import guru.learningjournal.examples.kafka.otpvalidation.model.PaymentRequest;
import lombok.extern.log4j.Log4j2;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.kstream.JoinWindows;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.StreamJoined;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.kafka.support.serializer.JsonSerde;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.time.ZoneOffset;


@Log4j2
@Service
@EnableBinding(OTPListenerBinding.class)
public class OTPValidationService {

    @Autowired
    private RecordBuilder recordBuilder;

    /**
     * This process-method should receive two inputs.
     * So, we do not define the input channel name using the stream listener annotation.
     * Instead, we defined the @Input annotations to the process.
     * - The join() method takes three mandatory arguments.
     * - The first argument is obviously the other stream that we want to join.
     * - Te second argument is a ValueJoiner lambda of two arguments.
     *  - The first lambda argument is a record from the left side, i.e., the PaymentRequest.
     *  - The second lambda argument is a record from the right side, i.e., the PaymentConfirmation.
     *  - The lambda method is triggered by the framework only if the matching record is found.
     *  - The body of the lambda method is the place where you would create an output record.
     *  - The getTransactionStatus will return a TransactionStatus record, and we will return it back.
     *  - The third argument is the JoinWindow that we use to define the time constraint. We are setting a 5-minute window
     *    for the join operation.
     *       - Explanation:
     *       - If the payment request and payment confirmation records arrive within a five-minute window. The ValueJoiner is
     *         triggered, and you will get a transaction status record with success or failure.
     *       - The success and failure depend upon the OTP matching, and the record is produced only if the ValueJoiner
     *         is triggered.
     *       - This means, if we receive both the records within a five-minute window we will get a transaction status
     *       record. If one side of the record is missing or they are not failling within the 5-minutes window, ValueJoiner
     *       is not triggered, and you won't see a transaction status record.
     *  - The last argument is to define the required Serdes. We already defined the default Serdes in the application
     *  YAML. However, the join operation often fails to infer the correct Serdes, so I defined it explicitly.
     *  - The first Serdes is for the common key among the two events.
     *  - The second and the third Serdes are for the two events.
     */
    @StreamListener
    public void process(@Input("payment-request-channel") KStream<String, PaymentRequest> request,
                        @Input("payment-confirmation-channel") KStream<String, PaymentConfirmation> confirmation) {

        request.foreach((k, v) -> log.info("Request Key = " + k + " Created Time = "
                + Instant.ofEpochMilli(v.getCreatedTime()).atOffset(ZoneOffset.UTC)));

        confirmation.foreach((k, v) -> log.info("Confirmation Key = " + k + " Created Time = "
                + Instant.ofEpochMilli(v.getCreatedTime()).atOffset(ZoneOffset.UTC)));


        request.join(confirmation,
                (r, c) -> recordBuilder.getTransactionStatus(r, c),
                JoinWindows.of(Duration.ofMinutes(5)),
                StreamJoined.with(Serdes.String(),
                        new JsonSerde<>(PaymentRequest.class),
                        new JsonSerde<>(PaymentConfirmation.class)))
                .foreach((k, v) -> log.info("Transaction ID = " + k + " Status = " + v.getStatus()));

    }
}
