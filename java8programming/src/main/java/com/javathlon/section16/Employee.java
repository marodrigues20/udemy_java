package com.javathlon.section16;

public class Employee {

    private double salary;
    private String department;
    private String name;

    public Employee(double salary, String department, String name) {
        this.salary = salary;
        this.department = department;
        this.name = name;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
