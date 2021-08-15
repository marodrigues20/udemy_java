package com.in28minutes.rest.webservices.restfulwebservices.user;

import java.util.Date;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;

@Entity
public class User {

    @Id
    @GeneratedValue
    private Integer Id;
    @Size(min=2, message = "Name should have at least 2 characters.")
    private String name;
    @Past
    private Date birthDate;
    @OneToMany(mappedBy = "user")
    private List<Post> postList;

    public User() {
    }

    public User(Integer id, String name, Date birthDate) {
        Id = id;
        this.name = name;
        this.birthDate = birthDate;
    }

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public List<Post> getPostList() {
        return postList;
    }

    public void setPostList(List<Post> postList) {
        this.postList = postList;
    }

    @Override
    public String toString() {
        return "User{" + "Id=" + Id + ", name='" + name + '\'' + ", birthDate=" + birthDate + '}';
    }
}
