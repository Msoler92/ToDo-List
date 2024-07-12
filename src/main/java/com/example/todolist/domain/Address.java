package com.example.todolist.domain;

import jakarta.persistence.Entity;
import lombok.Data;

@Entity
@Data
public class Address {
    private long id;
    private String street;
    private String city;
    private String zipcode;
    private String country;
}
