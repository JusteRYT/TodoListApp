package com.example.TodoListApp.service;

import com.example.TodoListApp.entity.TodoEntity;
import com.example.TodoListApp.repository.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Сервис для обработки бизнес-логики, включая фильтрацию и поиск задач.
 */
@Service
public class TodoService {
    private final TodoRepository todoRepository;

    @Autowired
    public TodoService(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    /**
     * Получить все задачи.
     *
     * @return список задач.
     */
    public List<TodoEntity> getAllTodos() {
        return todoRepository.findAll();
    }

    /**
     * Фильтрация задач по диапазону дат и статусу.
     *
     * @param from   начальная дата.
     * @param to     конечная дата.
     * @param status статус задачи.
     * @return список задач, соответствующих критериям.
     */
    public List<TodoEntity> getTodosByDateAndStatus(LocalDateTime from, LocalDateTime to, Boolean status) {
        return todoRepository.findAllByDateBetweenAndStatus(from, to, status);
    }

    public List<TodoEntity> searchTodosByName(String namePart) {
        return todoRepository.findByNameContainingIgnoreCase(namePart);
    }
}
