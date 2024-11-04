package com.example.TodoListApp.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * Класс-сущность, представляющий задачу в системе.
 * <p>
 * Поля:
 * - `id` — уникальный идентификатор задачи.
 * - `name` — название задачи.
 * - `shortDescription` — краткое описание задачи.
 * - `fullDescription` — полное описание задачи.
 * - `date` — дата и время, связанные с задачей.
 * - `status` — статус выполнения задачи (`true` — выполнена, `false` — не выполнена).
 */
@Setter
@Getter
@Entity
@Table(name = "todos")
public class TodoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;
    private String shortDescription;
    private String fullDescription;
    private LocalDateTime date;
    private Boolean status;


}
