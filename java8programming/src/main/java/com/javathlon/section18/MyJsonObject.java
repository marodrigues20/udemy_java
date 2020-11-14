package com.javathlon.section18;

import com.google.gson.annotations.SerializedName;

public class MyJsonObject {

  private String product;
  private String description;
  @SerializedName("duration_ms")
  private long durationMs;
  @SerializedName("start_time_ms")
  private long startTimeMs;
  private String metadata;

  //Type of attribute don't need to be the same but name has to be the same for Gson.
  private Customer customer;

  public String getProduct() {
    return product;
  }

  public void setProduct(String product) {
    this.product = product;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public long getDurationMs() {
    return durationMs;
  }

  public void setDurationMs(long durationMs) {
    this.durationMs = durationMs;
  }

  public long getStartTimeMs() {
    return startTimeMs;
  }

  public void setStartTimeMs(long startTimeMs) {
    this.startTimeMs = startTimeMs;
  }

  public String getMetadata() {
    return metadata;
  }

  public void setMetadata(String metadata) {
    this.metadata = metadata;
  }

  public Customer getCustomer() {
    return customer;
  }

  public void setCustomer(Customer customer) {
    this.customer = customer;
  }
}
