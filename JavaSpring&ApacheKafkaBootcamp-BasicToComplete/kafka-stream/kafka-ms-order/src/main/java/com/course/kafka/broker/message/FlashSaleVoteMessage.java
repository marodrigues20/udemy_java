package com.course.kafka.broker.message;


/**
 * Section 16. 94. Most Recent Data Feed
 */
public class FlashSaleVoteMessage {

	private String customerId;

	private String itemName;

	public String getCustomerId() {
		return customerId;
	}

	public String getItemName() {
		return itemName;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	@Override
	public String toString() {
		return "FlashSaleVoteMessage [customerId=" + customerId + ", itemName=" + itemName + "]";
	}

}
