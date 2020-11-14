package com.javathlon.section13;

import java.util.Comparator;

public class EntraceDateComparator implements Comparator<Employee> {


  @Override
  public int compare(Employee employee, Employee t1) {
    if (employee.getEntraceDate().before(t1.getEntraceDate())) return 1;
    if (employee.equals(t1.getEntraceDate())) return 0;
    else return -1;
  }
}
