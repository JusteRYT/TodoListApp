package com.example.TodoListApp.service;

import com.example.TodoListApp.entity.TodoEntity;
import com.example.TodoListApp.repository.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

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
     * Получить все задачи с пагинацией.
     *
     * @param limit  максимальное количество задач.
     * @param offset смещение для пагинации.
     * @return список задач.
     */
    public List<TodoEntity> getTodos(int limit, int offset) {
        return todoRepository.findAll(PageRequest.of(offset / limit, limit)).getContent();
    }

    /**
     * Фильтрация задач по диапазону дат, статусу и строке поиска с пагинацией.
     *
     * @param limit  максимальное количество задач.
     * @param offset смещение для пагинации.
     * @return список задач, соответствующих критериям.
     */
    public List<TodoEntity> getAllTodos(int limit, int offset) {
        Pageable pageable = PageRequest.of(offset / limit, limit);
        return todoRepository.findAll(pageable).getContent();
    }

    /**
     * Фильтрация задач по диапазону дат и статусу с пагинацией.
     *
     * @param from   начальная дата.
     * @param to     конечная дата.
     * @param status статус задачи.
     * @param limit  максимальное количество задач.
     * @param offset смещение для пагинации.
     * @return список задач, соответствующих критериям.
     */
    public List<TodoEntity> getTodosByDateAndStatus(LocalDateTime from, LocalDateTime to, Boolean status, int limit, int offset) {
        Pageable pageable = PageRequest.of(offset / limit, limit);
        if (status != null) {
            return todoRepository.findAllByDateBetweenAndStatus(from, to, status, pageable);
        } else {
            return todoRepository.findAllByDateBetween(from, to, pageable);
        }
    }

    /**
     * Поиск задач по названию с пагинацией.
     *
     * @param namePart строка поиска, подстрока, которую нужно найти в названии задачи.
     * @param limit  максимальное количество задач.
     * @param offset смещение для пагинации.
     * @return список задач, названия которых содержат заданную подстроку (без учета регистра).
     */
    public List<TodoEntity> searchTodosByName(String namePart, int limit, int offset) {
        return todoRepository.findByNameContainingIgnoreCase(namePart, PageRequest.of(offset / limit, limit));
    }


    /**
     * Получение общего количества задач.
     *
     * @return общее количество задач.
     */
    public long getTotalCount() {
        return todoRepository.count();
    }

    public TodoEntity getEntityById(Long id) {return todoRepository.findById(id).orElseThrow(() ->
            new RuntimeException("Задача не найдена"));
    }

    public TodoEntity save(TodoEntity todoEntity){
        return todoRepository.save(todoEntity);
    }
}
