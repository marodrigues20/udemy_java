package com.course.kafka.api.request;

/**
 * Section 11: 59. Discount Publisher
 */
public class DiscountRequest {

	private String discountCode;

	private int discountPercentage;

	public String getDiscountCode() {
		return discountCode;
	}

	public int getDiscountPercentage() {
		return discountPercentage;
	}

	public void setDiscountCode(String discountCode) {
		this.discountCode = discountCode;
	}

	public void setDiscountPercentage(int discountPercentage) {
		this.discountPercentage = discountPercentage;
	}

}
