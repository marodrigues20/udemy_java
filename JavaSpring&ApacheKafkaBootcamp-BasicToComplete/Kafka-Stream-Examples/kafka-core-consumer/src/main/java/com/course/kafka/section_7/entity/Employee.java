package com.course.kafka.section_7.entity;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import java.time.LocalDate;

/**
 * 31. Producing JSON Message
 */
@JsonNaming(value = PropertyNamingStrategy.SnakeCaseStrategy.class)
public class Employee {

    private String employeeId;
    private String name;
    private LocalDate birthDate;


    public Employee() {
    }

    public Employee(String employeeId, String name, LocalDate birthDate) {
        this.employeeId = employeeId;
        this.name = name;
        this.birthDate = birthDate;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public String toString() {
        return "Employee [employeeId=" + employeeId + ", name=" + name + ", birthDate=" + birthDate + "]";
    }
}
