package com.javathlon.section16;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class CollectorExample {

    public static void main(String[] args) {

        List<Employee> employeeList = new ArrayList();

        employeeList.add(new Employee(5000, "ADMIN", "Joe"));
        employeeList.add(new Employee(4000, "ADMIN", "Mark"));
        employeeList.add(new Employee(3000, "IT", "Talha"));
        employeeList.add(new Employee(2000, "IT", "Julia"));

        double cost = employeeList
                .stream()
                .collect(Collectors.summingDouble(t -> t.getSalary()));

        System.out.println("total cost " + cost);

        employeeList.stream()
                .map(t -> t.getDepartment())
                .distinct()
                .forEach(System.out::println);

        List<String> departmentList = employeeList.stream()
                .map(t -> t.getDepartment())
                .distinct()
                .collect(Collectors.toList());

        System.out.println("We have " + departmentList.size() + " departments");

        HashSet<String> departmentSet = employeeList.stream()
                .map(t -> t.getDepartment())
                .collect(Collectors.toCollection(HashSet::new));

        System.out.println("We have " + departmentList.size() + " departments");


        Map<String, List<Employee>> groupedEmployeeMap
                = employeeList.stream()
                .collect(Collectors.groupingBy(Employee::getDepartment));

        System.out.println("We have " + groupedEmployeeMap.size() + " departments");


        Map<String, Double> salaryMap = employeeList.stream()
                .collect(Collectors.groupingBy(Employee::getDepartment,
                        Collectors.summingDouble(Employee::getSalary)));

        System.out.println("");

        Map<String, Double> averageSalaryMap = employeeList.stream()
                .collect(Collectors.groupingBy(Employee::getDepartment,
                        Collectors.averagingDouble(Employee::getSalary)));

        System.out.println("");

        List<DepartmentSalary>  departmentSalaries = employeeList.stream()
                .collect(Collectors.groupingBy(Employee::getDepartment,
                        Collectors.averagingDouble(Employee::getSalary))).entrySet().stream()
                .map(t -> new DepartmentSalary(t.getValue(),t.getKey()))
                .collect(Collectors.toList());

        System.out.println("");




    }
}
