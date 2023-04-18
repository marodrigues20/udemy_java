package com.course.kafka.broker.message;

/**
 * Section 11. 63. Request - Reply in Kafka
 */
public class OrderReplyMessage {

    private String replyMessage;

    public String getReplyMessage() {
        return replyMessage;
    }

    public void setReplyMessage(String replyMessage) {
        this.replyMessage = replyMessage;
    }

    @Override
    public String toString() {
        return "OrderReplyMessage{" +
                "replyMessage='" + replyMessage + '\'' +
                '}';
    }
}
