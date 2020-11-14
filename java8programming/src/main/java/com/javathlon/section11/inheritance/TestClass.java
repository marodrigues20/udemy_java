package com.javathlon.section11.inheritance;

public class TestClass {

  public static void main(String[] args) {

    Vehicle vehicle = new Vehicle();
    vehicle.plate = "34 AR 90";
    //private method cannot be ac
    //vehicle.manufacturer = "";
    vehicle.getManufacturer();

    Car car = new Car();
    car.plate = "34 AR 90";
    System.out.println(car.reservedSeats());
    System.out.println(car.plate);

    
    System.out.println(car.reservedSeats());
    System.out.println(car.seats.length);


  }

}
