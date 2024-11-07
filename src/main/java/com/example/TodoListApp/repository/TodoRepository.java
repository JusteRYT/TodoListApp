package com.example.TodoListApp.repository;

import com.example.TodoListApp.entity.TodoEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.time.LocalDateTime;
import java.util.List;

/**
 * Репозиторий для операций CRUD с таблицей задач.
 * Этот репозиторий использует Spring Data JPA для работы с сущностью `TodoEntity` и поддерживает
 * такие операции как поиск, фильтрация и пагинация задач.
 */
@Repository
public interface TodoRepository extends JpaRepository<TodoEntity, Long> {
    /**
     * Получение задач в указанном диапазоне дат с определенным статусом.
     * Используется для фильтрации задач по дате и статусу с учетом пагинации.
     *
     * @param from   начальная дата диапазона.
     * @param to     конечная дата диапазона.
     * @param status статус задачи (`true` — выполнена, `false` — не выполнена).
     * @param pageable параметры пагинации.
     * @return список задач, которые соответствуют указанным критериям.
     */
    List<TodoEntity> findAllByDateBetweenAndStatus(LocalDateTime from, LocalDateTime to, Boolean status, Pageable pageable);

    /**
     * Поиск задач по названию с учетом подстроки.
     * Метод выполняет поиск задач, название которых содержит указанную подстроку,
     * не обращая внимания на регистр символов.
     *
     * @param namePart подстрока для поиска в названии задачи.
     * @param pageable параметры пагинации.
     * @return список задач, название которых содержит подстроку.
     */
    List<TodoEntity> findByNameContainingIgnoreCase(String namePart, Pageable pageable);

    /**
     * Получение задач в указанном диапазоне дат без фильтрации по статусу.
     * Метод используется для получения задач в пределах определенного временного диапазона,
     * с возможностью использования пагинации.
     *
     * @param from начальная дата диапазона.
     * @param to конечная дата диапазона.
     * @param pageable параметры пагинации.
     * @return список задач, которые соответствуют диапазону дат.
     */
    List<TodoEntity> findAllByDateBetween(LocalDateTime from, LocalDateTime to, Pageable pageable);
}
