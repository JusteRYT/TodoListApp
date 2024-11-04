package com.example.TodoListApp.db_test;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.sql.DataSource;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

/**
 * Тестовый класс для проверки подключения к базе данных.
 * <p>
 * Использует Spring Boot для загрузки контекста приложения и проверки
 * корректности конфигурации подключения к базе данных. В тестах проверяется,
 * что DataSource успешно создан и соединение с базой данных может быть установлено.
 */
@SpringBootTest
public class DatabaseConnectionTest {

    /**
     * Объект DataSource, автоматически внедряемый Spring.
     * Используется для проверки конфигурации и установления соединения с БД.
     */
    @Autowired
    private DataSource dataSource;

    /**
     * Проверяет, что DataSource не является null, указывая на то, что
     * конфигурация для подключения к базе данных установлена корректно.
     */
    @Test
    public void testDataSourceIsNotNull() {
        assertThat(dataSource).isNotNull();
    }

    /**
     * Проверяет возможность установления соединения с базой данных.
     * <p>
     * Метод открывает соединение с помощью DataSource и проверяет его валидность.
     * Если соединение успешно, это означает, что конфигурация базы данных корректна.
     *
     * @throws Exception если не удается получить соединение с базой данных
     */
    @Test
    public void testDatabaseConnection() throws  Exception{
        try(var connection = dataSource.getConnection()){
            assertThat(connection.isValid(1)).isTrue();
        }
    }
}
