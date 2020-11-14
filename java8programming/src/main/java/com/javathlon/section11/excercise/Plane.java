package com.javathlon.section11.excercise;

public class Plane extends Vehicle {

  public Plane() {
    seats = new Customer[4][5];
  }

  String[] labels = new String[]{"A","B","C","D","E"};

  protected void listSeats() {
    int i, j = 0;
    for (i = 0; i < seats.length; i++) {
      for (j = 0; j < seats[i].length; j++) {
        if (seats[i][j] == null) {
          System.out.println("Empty");
        } else {
          String label = (i+1) + labels[j];
          System.out.println("Seat:" + label + " " + seats[i][j].getName() + " " + seats[i][j].getSurname());
        }
      }
    }
  }

}
