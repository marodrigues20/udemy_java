package com.example.entity;

import org.springframework.data.mongodb.core.mapping.Field;

//We don't need to mark it as a @Collection because how is the collection in this case
// is Student.
public class Department {

    @Field(name = "department_name")
    private String departmentName;
    @Field(name = "location")
    private String location;

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
