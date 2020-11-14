package com.javathlon.section13;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Employee implements Comparable<Employee>  {


  private int age;
  private double salary;
  private Date entraceDate;

  private static SimpleDateFormat sd = new SimpleDateFormat("dd/MM/yyyy");

  public Employee(int age, double salary, String entraceDate) {
    this.age = age;
    this.salary = salary;
    try {
      this.entraceDate = sd.parse(entraceDate);
    } catch (ParseException e) {
      e.printStackTrace();
    }
  }

  public int getAge() {
    return age;
  }

  public void setAge(int age) {
    this.age = age;
  }

  public double getSalary() {
    return salary;
  }

  public void setSalary(double salary) {
    this.salary = salary;
  }

  public Date getEntraceDate() {
    return entraceDate;
  }

  public void setEntraceDate(Date entraceDate) {
    this.entraceDate = entraceDate;
  }




  @Override
  public String toString() {
    return "Employee{" +
        "age=" + age +
        ", salary=" + salary +
        ", entraceDate=" + entraceDate +
        '}';
  }

  @Override
  public int compareTo(Employee o) {

      if (this.entraceDate.before(((Employee)o).entraceDate)) return -1;
      if (this.entraceDate.equals(((Employee)o).entraceDate)) return 0;
      else return 1;

  }
}
