package com.javathlon.section16;

public class DepartmentSalary {

    private double averageSalary;
    private String departments;

    public DepartmentSalary(double averageSalary, String departments) {
        this.averageSalary = averageSalary;
        this.departments = departments;
    }

    public double getAverageSalary() {
        return averageSalary;
    }

    public void setAverageSalary(double averageSalary) {
        this.averageSalary = averageSalary;
    }

    public String getDepartments() {
        return departments;
    }

    public void setDepartments(String departments) {
        this.departments = departments;
    }
}
