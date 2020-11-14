package com.javathlon.section11.inheritance;

public class Vehicle {

  private String manufacturer;
  protected String plate;

  protected String seats[][] = new String[0][0];

  protected String reservedSeats(){
    return "SEAT RESERVED ON VEHICLE";
  }

  public String getManufacturer() {
    return manufacturer;
  }

  public void setManufacturer(String manufacturer) {
    this.manufacturer = manufacturer;
  }
}
