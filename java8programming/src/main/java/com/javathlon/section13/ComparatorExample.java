package com.javathlon.section13;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class ComparatorExample {

  public static void main(String[] args) {

    List<Employee> employeeList = new ArrayList<>();

    employeeList.add(new Employee(30, 3000,"01/01/2017"));
    employeeList.add(new Employee(28, 4000,"01/01/2016"));
    employeeList.add(new Employee(34, 1000,"01/01/2019"));
    employeeList.add(new Employee(30, 3000,"01/01/2020"));

    //Collections.sort(employeeList);
    //Collections.sort(employeeList, new EntraceDateComparator());
    //Collections.sort(employeeList, new AgeComparator());
    Collections.sort(employeeList, new SalaryComparator());

    for(Employee employee : employeeList){
      System.out.println(employee);
    }

    /////////////// BINARY SEARCH EXAMPLE /////////////////////////

    //int index = Collections.binarySearch(employeeList, new Employee(30, 0,"01/01/2016"),new AgeComparator());
    int index = Collections.binarySearch(employeeList, new Employee(0, 0.0, "01/01/2016"));

    if(index >= 0){
      Employee employee = employeeList.get(index);
      System.out.println("Employee is xxx: " +  employee);
    }










  }

}
