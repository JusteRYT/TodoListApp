# TodoListApp

**TodoListApp** — это приложение для управления списком задач, позволяющее пользователям добавлять, редактировать, искать и фильтровать задачи. Оно использует Spring Boot для реализации серверной части и взаимодействия с базой данных, а также предоставляет REST API для работы с данными задач.

## 🚀 Технологии

- **Spring Boot** — фреймворк для создания серверных приложений на языке Java.
- **JPA (Hibernate)** — для работы с базой данных и выполнения операций CRUD.
- **MySQL** — система управления базами данных для хранения информации о задачах.
- **REST API** — для взаимодействия между клиентом и сервером.
- **Thymeleaf** — шаблонизатор для генерации веб-страниц (если используется для фронтенда).

## 📸 Интерфейс приложения

![Интерфейс TodoListApp](https://github.com/user-attachments/assets/e241b1cf-a2a7-467c-87d1-5b51d28b1610)

![Окно задачи](https://github.com/user-attachments/assets/58f41553-67f1-42b7-9129-34220ce1295e)


## 📦 Установка и запуск

### 1. Клонируйте репозиторий

```bash
git clone https://github.com/yourusername/TodoListApp.git
cd TodoListApp
```
### 2. Настройка базы данных

Прежде чем запустить приложение, настройте MySQL:

- Создайте базу данных для приложения. Например:
```sql  
CREATE DATABASE todo_app;
```

- Обновите настройки подключения к базе данных в `src/main/resources/application.properties`:
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/todo_app  
spring.datasource.username=your_username  
spring.datasource.password=your_password  
spring.jpa.hibernate.ddl-auto=update
```

### 3. Запуск приложения

Запустите приложение с помощью команд:

#### Через Maven:
```maven
mvn spring-boot:run
```

#### Или скачайте RELEASE и запустите start.cmd:

```cmd
java -jar TodoListApp.jar
```

После этого приложение будет доступно по адресу `http://localhost:8080`.

### 4. Использование API

Приложение предоставляет следующие эндпоинты:

#### Получение всех задач:
```
GET /api/todos
```

- Ответ: Список всех задач.

#### Поиск задач по названию:

```http
GET /api/todos/find?q=Test
```

- Ответ: Список задач, название которых содержит "task".

#### Фильтрация задач по дате и статусу:

```http
GET /api/todos/date?from=2024-11-01T00:00:00&to=2024-11-07T23:59:59&status=true
```

- Ответ: Список задач, которые находятся в указанном диапазоне дат и имеют статус "выполнено".

### 5. Пагинация

Для выполнения запросов с пагинацией используйте параметры `limit` и `offset`:

- Пример запроса с пагинацией для получения задач:

```
GET /api/todos?offset=11&limit=1
```

- Ответ: 10 задач, начиная с 11-й.

## 🔧 Структура проекта

- **`src/main/java/com/example/TodoListApp`** — основной код приложения.
  - **`controller`** — контроллеры для обработки HTTP-запросов.
  - **`entity`** — сущности (модели данных), которые отражают структуру базы данных.
  - **`repository`** — репозитории для взаимодействия с базой данных.
  - **`service`** — бизнес-логика приложения.
- **`src/main/resources`** — ресурсы для конфигурации, шаблонов и стилей.
  - **`application.properties`** — конфигурация подключения к базе данных.
  - **`static`** — статические ресурсы (CSS, JS и изображения).
  - **`templates`** — шаблоны для генерации HTML-страниц (если используется).
- **`src/test/java`** — тесты для приложения.

## 🤖 Тестирование

Для тестирования приложения можно использовать инструменты, такие как **JUnit** для тестов сервиса и контроллеров, а также **Postman** или **curl** для тестирования API.

Пример теста:

```java
@Test  
public void testGetAllTodos() {  
    List<TodoEntity> todos = todoService.getTodos(10, 0);  
    assertNotNull(todos);  
    assertTrue(todos.size() > 0);  
}
```

## ⚙️ Конфигурация

Для конфигурации параметров приложения, таких как база данных или сервер, используйте файл `application.properties`:

```properties
# Настройка базы данных
spring.datasource.url=jdbc:mysql://localhost:3306/todo_app  
spring.datasource.username=your_username  
spring.datasource.password=your_password  

#Настройка JPA
spring.jpa.hibernate.ddl-auto=update
```
