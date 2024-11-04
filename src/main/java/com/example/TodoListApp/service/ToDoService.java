package com.example.TodoListApp.service;

import com.example.TodoListApp.entity.TodoEntity;
import com.example.TodoListApp.repository.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Сервис для обработки бизнес-логики, включая фильтрацию и поиск задач.
 */
@Service
public class ToDoService {
    private final TodoRepository todoRepository;

    @Autowired
    public ToDoService(TodoRepository todoRepository){
        this.todoRepository = todoRepository;
    }

    /**
     * Получить все задачи.
     * @return список задач.
     */
    public List<TodoEntity> getAllTodos(){
        return todoRepository.findAll();
    }
}
