package com.example.TodoListApp.repository;

import com.example.TodoListApp.entity.TodoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Репозиторий для операций CRUD с таблицей задач.
 */
@Repository
public interface TodoRepository extends JpaRepository<TodoEntity, Long> {
    List<TodoEntity> findAllByDateBetweenAndStatus(LocalDateTime from, LocalDateTime to, Boolean status);
}
