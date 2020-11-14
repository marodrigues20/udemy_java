package com.javathlon.section11.excercise;

public class Bus extends Vehicle {

  public Bus() {
    seats = new Customer[4][2];
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

          if(j==1)
              if(seats[i][0] == null || seats[i][0].getGender().equals(c.getGender())){
                continue;
              }

          seats[i][j] = c;
          isFound = true;
          break;
        }
      }
    }

    int seat = (i - 1) * seats[0].length + j + 1;

    return seat + "";
  }
}
