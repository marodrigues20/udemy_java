package guru.learningjournal.example.kafka.rewards.services;


import guru.learningjournal.examples.kafka.model.Notification;
import guru.learningjournal.examples.kafka.model.PosInvoice;
import org.springframework.stereotype.Service;

@Service
public class RecordBuilder {

    public Notification getNotification(PosInvoice invoice){
        Notification notification = new Notification();
        notification.setInvoiceNumber(invoice.getInvoiceNumber());
        notification.setCustomerCardNo(invoice.getCustomerCardNo());
        notification.setTotalAmount(invoice.getTotalAmount());
        notification.setEarnedLoyaltyPoints(invoice.getTotalAmount() * 0.02);
        notification.setTotalLoyaltyPoints(notification.getEarnedLoyaltyPoints());
        return notification;
    }
}
