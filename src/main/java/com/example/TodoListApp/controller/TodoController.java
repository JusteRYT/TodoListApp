package com.example.TodoListApp.controller;

import com.example.TodoListApp.entity.TodoEntity;
import com.example.TodoListApp.service.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

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

    /**
     * Получение полного списка задач с пагинацией.
     *
     * @param limit  максимальное количество задач для возврата.
     * @param offset смещение для пагинации.
     * @return список задач.
     */
    @GetMapping
    public List<TodoEntity> getTodos(
            @RequestParam(defaultValue = "10") int limit,
            @RequestParam(defaultValue = "0") int offset) {
        return todoService.getAllTodos(limit, offset);
    }

    /**
     * Получение задач по диапазону дат и статусу.
     *
     * @param from   начальная дата диапазона, должна быть в формате ISO.
     * @param to     конечная дата диапазона, должна быть в формате ISO.
     * @param status статус задачи (`true` — выполнена, `false` — не выполнена).
     * @param limit  максимальное количество задач для возврата.
     * @param offset смещение для пагинации.
     * @return список задач, соответствующих заданным критериям.
     */
    @GetMapping("/date")
    public List<TodoEntity> getTodosByDateAndStatus(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime from,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime to,
            @RequestParam(required = false) Boolean status,
            @RequestParam(defaultValue = "10") int limit,
            @RequestParam(defaultValue = "0") int offset) {
        return todoService.getTodosByDateAndStatus(from, to, status, limit, offset);
    }

    /**
     * Поиск задач по ключевому слову в названии.
     *
     * @param q строка поиска, подстрока, которую нужно найти в названии задачи.
     * @param limit  максимальное количество задач для возврата.
     * @param offset смещение для пагинации.
     * @return список задач, названия которых содержат заданную подстроку.
     */
    @GetMapping("/find")
    public List<TodoEntity> searchTodosByName(
            @RequestParam String q,
            @RequestParam(defaultValue = "10") int limit,
            @RequestParam(defaultValue = "0") int offset) {
        return todoService.searchTodosByName(q, limit, offset);
    }

    // Получение общего количества задач
    @GetMapping("/count")
    public long getTotalCount() {
        return todoService.getTotalCount();
    }

    /**
     * Поиск задачи по идентификатору.
     *
     * @param id идентификатор задачи.
     * @return задача с заданным идентификатором.
     */
    @GetMapping("/{id}")
    public TodoEntity getTodoById(@PathVariable Long id){
        return todoService.getEntityById(id);
    }

    @PutMapping("/{id}")
    public TodoEntity updateTodoEntityStatus(@PathVariable Long id, @RequestBody StatusUpdate statusUpdate){
        TodoEntity todoEntity = todoService.getEntityById(id);
        System.out.println(statusUpdate);
        if(todoEntity == null){
            throw new RuntimeException("Задача не найдена");
        }
        todoEntity.setStatus(statusUpdate.isStatus());

        return todoService.save(todoEntity);
    }
}
