package com.example.todolist.domain;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String name;
    private String username;
    private String password;
    @ManyToOne(optional = false)
    @JoinColumn(name = "id", nullable = false)
    private Address address;
}
