package com.example.TodoListApp.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
/**
 * Конфигурационный класс для настройки CORS (Cross-Origin Resource Sharing).
 * Этот класс реализует интерфейс {@link WebMvcConfigurer} и позволяет настроить разрешения на кросс-доменные запросы
 * для API приложения.
 *
 * Основные функции:
 * 1. Описание правил доступа к API с разных доменов, конкретно разрешается доступ с домена "http://localhost:63342".
 * 2. Настройка разрешённых HTTP-методов, заголовков и поддержку кросс-доменных запросов с учётом авторизации.
 *
 * Класс использует аннотацию {@link Configuration} для обозначения его как конфигурационный компонент Spring.
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    /**
     * Настроить CORS для API.
     * Этот метод добавляет правила CORS для всех URL-ов, начинающихся с "/api/**",
     * что позволяет клиентам на других доменах взаимодействовать с API.
     *
     * @param registry Регистратор для настройки CORS.
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**")
                .allowedOrigins("http://localhost:63342")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("*")
                .allowCredentials(true);
    }
}
