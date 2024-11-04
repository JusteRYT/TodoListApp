package com.example.TodoListApp.controller;

import com.example.TodoListApp.entity.TodoEntity;
import com.example.TodoListApp.service.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Контроллер для обработки запросов к API для работы с задачами.
 */
@RestController
@RequestMapping("/api/todos")
public class TodoController {
    private final TodoService todoService;

    @Autowired
    public TodoController(TodoService todoService) {
        this.todoService = todoService;
    }

    /**
     * Получение списка всех задач.
     *
     * @return список задач.
     */
    @GetMapping
    public List<TodoEntity> getAllTodos() {
        return todoService.getAllTodos();
    }

    @GetMapping("/date")
    public List<TodoEntity> getTodosByDateAndStatus(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime from,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime to,
            @RequestParam Boolean status) {
        return todoService.getTodosByDateAndStatus(from, to, status);
    }

    @GetMapping("/find")
    public List<TodoEntity> searchTodosByName(@RequestParam String q){
        return todoService.searchTodosByName(q);
    }
}
