package com.javathlon.section13;

import java.util.Comparator;

public class AgeComparator implements Comparator<Employee> {


  @Override
  public int compare(Employee employee, Employee t1) {
    return employee.getAge() - t1.getAge();
  }
}
