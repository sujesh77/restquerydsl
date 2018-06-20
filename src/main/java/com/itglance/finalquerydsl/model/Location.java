package com.itglance.finalquerydsl.model;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
public class Location extends BaseModel {

    private String address;

    @ManyToOne
    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
