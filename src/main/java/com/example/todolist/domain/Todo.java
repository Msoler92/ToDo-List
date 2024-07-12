package com.example.todolist.domain;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Todo {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String title;
    private boolean completed;
    @ManyToOne(optional = false)
    @JoinColumn(name = "id", nullable = false)
    private User user;
}
