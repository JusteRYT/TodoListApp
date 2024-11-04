package com.example.TodoListApp.repository;

import com.example.TodoListApp.entity.TodoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Репозиторий для операций CRUD с таблицей задач.
 */
@Repository
public interface TodoRepository extends JpaRepository<TodoEntity, Long> {
    /**
     * Получение задач в указанном диапазоне дат с определенным статусом.
     *
     * @param from   начальная дата диапазона.
     * @param to     конечная дата диапазона.
     * @param status статус задачи.
     * @return список задач, которые соответствуют указанным критериям.
     */
    List<TodoEntity> findAllByDateBetweenAndStatus(LocalDateTime from, LocalDateTime to, Boolean status);

    /**
     * Поиск задач по названию с учетом подстроки (без учета регистра).
     *
     * @param namePart подстрока для поиска в названии задачи.
     * @return список задач, название которых содержит подстроку.
     */
    List<TodoEntity> findByNameContainingIgnoreCase(String namePart);
}
