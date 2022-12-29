package guru.learningjournal.example.kafka.windowcount.bindings;

import guru.learningjournal.example.kafka.windowcount.model.SimpleInvoice;
import org.apache.kafka.streams.kstream.KStream;
import org.springframework.cloud.stream.annotation.Input;

public interface InvoiceListenerBinding {

    @Input("invoice-input-channel")
    KStream<String, SimpleInvoice> invoiceInputStream();

}
