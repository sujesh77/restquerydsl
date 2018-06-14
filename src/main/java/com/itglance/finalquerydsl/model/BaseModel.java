package com.itglance.finalquerydsl.model;


import javax.persistence.*;

@MappedSuperclass
public class BaseModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

}
