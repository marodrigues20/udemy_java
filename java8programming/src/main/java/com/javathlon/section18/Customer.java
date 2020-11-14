package com.javathlon.section18;

import com.google.gson.annotations.SerializedName;
import java.util.Date;

public class Customer {

  private String name;
  private String surname;
  private Date registerDate;
  @SerializedName("shopping_cart")
  private ShoppingCart shoppingCart;



  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getSurname() {
    return surname;
  }

  public void setSurname(String surname) {
    this.surname = surname;
  }

  public ShoppingCart getShoppingCart() {
    return shoppingCart;
  }

  public void setShoppingCart(ShoppingCart shoppingCart) {
    this.shoppingCart = shoppingCart;
  }

  public Date getRegisterDate() {
    return registerDate;
  }

  public void setRegisterDate(Date registerDate) {
    this.registerDate = registerDate;
  }
}
