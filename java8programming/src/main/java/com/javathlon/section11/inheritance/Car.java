package com.javathlon.section11.inheritance;

public class Car extends Vehicle {

  protected String reservedSeats(){
    return "SEAT RESERVED ON CAR";
  }

  public Car() {
    seats = new String[4][4];
  }


}
