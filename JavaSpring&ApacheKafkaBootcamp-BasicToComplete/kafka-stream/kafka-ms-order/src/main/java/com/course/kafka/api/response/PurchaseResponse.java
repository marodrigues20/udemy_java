package com.course.kafka.api.response;

/**
 * Section 15: 92. Web & Mobile
 */
public class PurchaseResponse {

	private String purchaseNumber;

	public PurchaseResponse(String purchaseNumber) {
		super();
		this.purchaseNumber = purchaseNumber;
	}

	public String getPurchaseNumber() {
		return purchaseNumber;
	}

	public void setPurchaseNumber(String purchaseNumber) {
		this.purchaseNumber = purchaseNumber;
	}

	@Override
	public String toString() {
		return "PurchaseResponse [purchaseNumber=" + purchaseNumber + "]";
	}

}
