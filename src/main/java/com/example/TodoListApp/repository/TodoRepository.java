package com.example.TodoListApp.repository;

import com.example.TodoListApp.entity.TodoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Репозиторий для операций CRUD с таблицей задач.
 */
@Repository
public interface TodoRepository extends JpaRepository<TodoEntity, Long> {

}
