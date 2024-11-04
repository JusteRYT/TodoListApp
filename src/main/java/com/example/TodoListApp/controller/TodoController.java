package com.example.TodoListApp.controller;

import com.example.TodoListApp.entity.TodoEntity;
import com.example.TodoListApp.service.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Контроллер для обработки запросов к API для работы с задачами.
 */
@RestController
@RequestMapping("/api/todos")
public class TodoController {
    private final TodoService todoService;

    @Autowired
    public TodoController(TodoService todoService){
        this.todoService = todoService;
    }

    /**
     * Получение списка всех задач.
     * @return список задач.
     */
    @GetMapping
    public List<TodoEntity> getAllTodos(){
        return todoService.getAllTodos();
    }
}
