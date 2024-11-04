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

    /**
     * Конструктор для инъекции зависимости `TodoService`.
     *
     * @param todoService сервис для обработки логики задач.
     */
    @Autowired
    public TodoController(TodoService todoService) {
        this.todoService = todoService;
    }


    @GetMapping
    public List<TodoEntity> getAllTodos() {
        return todoService.getAllTodos();
    }

    /**
     * Получение задач по диапазону дат и статусу.
     * <p>
     * Эндпоинт `/api/todos/date` возвращает список задач, которые попадают в указанный временной
     * диапазон и соответствуют статусу выполнения.
     *
     * @param from начальная дата диапазона, должна быть в формате ISO.
     * @param to конечная дата диапазона, должна быть в формате ISO.
     * @param status статус задачи (`true` — выполнена, `false` — не выполнена).
     * @return список задач, соответствующих заданным критериям.
     */
    @GetMapping("/date")
    public List<TodoEntity> getTodosByDateAndStatus(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime from,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime to,
            @RequestParam Boolean status) {
        return todoService.getTodosByDateAndStatus(from, to, status);
    }

    /**
     * Поиск задач по ключевому слову в названии.
     * <p>
     * Эндпоинт `/api/todos/find` позволяет искать задачи, название которых содержит
     * указанное ключевое слово.
     *
     * @param q строка поиска, подстрока, которую нужно найти в названии задачи.
     * @return список задач, названия которых содержат заданную подстроку.
     */
    @GetMapping("/find")
    public List<TodoEntity> searchTodosByName(@RequestParam String q){
        return todoService.searchTodosByName(q);
    }
}
