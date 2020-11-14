package com.javathlon.section18;

import java.util.List;

public class ShoppingCart {

  private List<String> items;

  private List<ItemInformation> informations;

  public List<String> getItems() {
    return items;
  }

  public void setItems(List<String> items) {
    this.items = items;
  }

  public List<ItemInformation> getInformations() {
    return informations;
  }

  public void setInformations(List<ItemInformation> informations) {
    this.informations = informations;
  }
}
