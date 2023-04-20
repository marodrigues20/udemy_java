package com.course.kafka.broker.message;

/**
 * Section 11: 58. Pattern App - Promotion Publisher
 */
public class PromotionMessage {

    private String promotionCode;

    public PromotionMessage() {
    }

    public PromotionMessage(String promotionCode) {
        this.promotionCode = promotionCode;
    }

    public String getPromotionCode() {
        return promotionCode;
    }

    public void setPromotionCode(String promotionCode) {
        this.promotionCode = promotionCode;
    }

    @Override
    public String toString() {
        return "PromotionMessage{" +
                "promotionCode='" + promotionCode + '\'' +
                '}';
    }
}
