package com.javathlon.section13;

import java.util.Comparator;

public class SalaryComparator implements Comparator<Employee> {


  @Override
  public int compare(Employee employee, Employee t1) {
    return (int) (employee.getSalary() - t1.getSalary());
  }
}
