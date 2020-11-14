package com.javathlon.section11.excercise;

public class Vehicle {

  private String manufacturer;
  protected String plate;
  protected String destination;

  protected Customer seats[][] = new Customer[0][0];

  protected void listSeats() {
    int i, j = 0;
    for (i = 0; i < seats.length; i++) {
      for (j = 0; j < seats[i].length; j++) {
        if (seats[i][j] == null) {
          System.out.println("Empty");
        } else {
          int seat = (i) * seats[0].length + j + 1;
          System.out.println("Seat:" + seat + " " + seats[i][j].getName() + " " + seats[i][j].getSurname());
        }
      }
    }
  }


  protected String reservedSeats(Customer c) {
    int i, j = 0;
    boolean isFound = false;

    for (i = 0; i < seats.length; i++) {
      if (isFound) {
        break;
      }
      for (j = 0; j < seats[i].length; j++) {
        if (seats[i][j] == null) {
          seats[i][j] = c;
          isFound = true;
          break;
        }
      }
    }

    int seat = (i - 1) * seats[0].length + j + 1;

    return seat + "";
  }

  public String getManufacturer() {
    return manufacturer;
  }

  public void setManufacturer(String manufacturer) {
    this.manufacturer = manufacturer;
  }
}
