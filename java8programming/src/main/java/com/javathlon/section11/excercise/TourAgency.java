package com.javathlon.section11.excercise;

public class TourAgency {

  public static void main(String[] args) {
    Customer customer = new Customer(1, "Talha", "Ocakci", "M", true);
    Customer customer1 = new Customer(2, "Josh", "Hollowy", "M", true);
    Customer customer2 = new Customer(3, "Marie", "Jane", "F", true);
    Customer customer3 = new Customer(4, "Natalie", "Portman", "F", true);
    Customer customer4 = new Customer(5, "Bred", "Pitt", "M", true);

    Car car = new Car();
    car.reservedSeats(customer);
    car.reservedSeats(customer1);
    car.reservedSeats(customer2);
    car.reservedSeats(customer3);
    car.reservedSeats(customer4);
    car.listSeats();

    System.out.println("-----------------");

    Bus bus = new Bus();
    bus.reservedSeats(customer);
    bus.reservedSeats(customer1);
    bus.reservedSeats(customer2);
    bus.reservedSeats(customer3);
    bus.reservedSeats(customer4);
    bus.listSeats();

    System.out.println("-----------------");

    Plane plane = new Plane();
    plane.reservedSeats(customer);
    plane.reservedSeats(customer1);
    plane.reservedSeats(customer2);
    plane.reservedSeats(customer3);
    plane.reservedSeats(customer4);
    plane.listSeats();

  }


}
