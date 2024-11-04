package com.example.TodoListApp.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * Entity-класс для хранения информации о задачах.
 */
@Entity
@Table(name = "todos")
public class TodoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Getter @Setter
    private String name;
    @Getter @Setter
    private String shortDescription;
    @Getter @Setter
    private String fullDescription;
    @Getter @Setter
    private LocalDateTime date;
    @Getter @Setter
    private Boolean status;


}
