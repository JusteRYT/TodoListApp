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

    /**
     * Конструктор для инъекции зависимости `TodoRepository`.
     *
     * @param todoRepository репозиторий для взаимодействия с базой данных задач.
     */
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

    /**
     * Поиск задач по названию.
     *
     * @param namePart строка поиска, подстрока, которую нужно найти в названии задачи.
     * @return список задач, название которых содержит заданную подстроку (без учета регистра).
     */
    public List<TodoEntity> searchTodosByName(String namePart) {
        return todoRepository.findByNameContainingIgnoreCase(namePart);
    }
}
