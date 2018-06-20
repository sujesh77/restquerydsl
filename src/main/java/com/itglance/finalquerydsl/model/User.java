package com.itglance.finalquerydsl.model;

import javax.persistence.*;
import java.util.LinkedList;
import java.util.List;

@Entity
public class User extends BaseModel {

    private String firstname;
    private String lastname;
    private String email;
    private int age;

    @OneToMany(mappedBy = "user")
    private List<Location> location = new LinkedList<>();

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

}
