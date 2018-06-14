package com.itglance.finalquerydsl.model;

import javax.persistence.Entity;

@Entity
public class Location extends BaseModel {

    private String address;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
