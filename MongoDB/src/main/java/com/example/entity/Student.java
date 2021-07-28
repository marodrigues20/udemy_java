package com.example.entity;

import java.util.List;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "student")
public class Student {

    @Id //We don't need to map the name to _id. Spring identify it for us.
    private String id;
    @Field(name = "name")
    private String name;
    @Field(name = "mail")
    private String email;
    private Department department;
    private List<Subject> subjects;
    @Transient
    private double percentage;

    /*Whenever you have more than one construtor defined we have to mark one of them
    as a @PersistenceConstructor. Just for classes represent a Collection */
    /*@PersistenceConstructor
    public Student(String id, String name, String email, Department department, List<Subject> subjects) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.department = department;
        this.subjects = subjects;
    }
    public Student(String id, Department department) {
        this.id = id;
        this.department = department;
    }*/

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public List<Subject> getSubjects() {
        return subjects;
    }

    public void setSubjects(List<Subject> subjects) {
        this.subjects = subjects;
    }

    public double getPercentage() {

        if (subjects != null && subjects.size() > 0) {
            int total = 0;
            for (Subject subject : subjects) {
                total += subject.getMarksObtained();
            }
            return total / subjects.size();
        }
        return 0.00;
    }

    public void setPercentage(double percentage) {
        this.percentage = percentage;
    }
}
