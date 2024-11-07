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
 * Этот сервис взаимодействует с репозиторием `TodoRepository` для выполнения операций с задачами,
 * таких как поиск, фильтрация, и сохранение данных.
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
     * Этот метод возвращает все задачи, используя параметры пагинации для управления объемом данных.
     *
     * @param limit  максимальное количество задач для возврата.
     * @param offset смещение для пагинации.
     * @return список всех задач с учетом пагинации.
     */
    public List<TodoEntity> getAllTodos(int limit, int offset) {
        Pageable pageable = PageRequest.of(offset / limit, limit);
        return todoRepository.findAll(pageable).getContent();
    }

    /**
     * Фильтрация задач по диапазону дат и статусу с пагинацией.
     * Этот метод позволяет фильтровать задачи по дате и статусу (выполнена или нет), а также поддерживает пагинацию.
     *
     * @param from   начальная дата для фильтрации.
     * @param to     конечная дата для фильтрации.
     * @param status статус задачи (выполнена или нет).
     * @param limit  максимальное количество задач для возврата.
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
     * Этот метод позволяет искать задачи по части названия, игнорируя регистр, с учетом пагинации.
     *
     * @param namePart строка поиска для подстроки в названии задачи.
     * @param limit  максимальное количество задач для возврата.
     * @param offset смещение для пагинации.
     * @return список задач, название которых содержит указанную подстроку.
     */
    public List<TodoEntity> searchTodosByName(String namePart, int limit, int offset) {
        return todoRepository.findByNameContainingIgnoreCase(namePart, PageRequest.of(offset / limit, limit));
    }


    /**
     * Получение общего количества задач.
     * Этот метод возвращает общее количество задач в базе данных, что полезно для вычисления
     * количества страниц при пагинации.
     *
     * @return общее количество задач.
     */
    public long getTotalCount() {
        return todoRepository.count();
    }

    /**
     * Получение задачи по идентификатору.
     * Этот метод ищет задачу по её идентификатору и выбрасывает исключение, если задача не найдена.
     *
     * @param id идентификатор задачи.
     * @return задача с заданным идентификатором.
     */
    public TodoEntity getEntityById(Long id) {return todoRepository.findById(id).orElseThrow(() ->
            new RuntimeException("Задача не найдена"));
    }

    /**
     * Сохранение или обновление задачи.
     * Этот метод сохраняет задачу в базе данных, используя репозиторий для выполнения операции.
     *
     * @param todoEntity объект задачи для сохранения.
     * @return сохраненная задача.
     */
    public TodoEntity save(TodoEntity todoEntity){
        return todoRepository.save(todoEntity);
    }
}
