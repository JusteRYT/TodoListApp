$(document).ready(function () {
    let currentPage = 1;
    const limit = 10; // Количество задач на странице

    // Получение и отображение всех задач с пагинацией
    function loadTasks(page = 1) {
        $.ajax({
            url: "/api/todos",
            method: "GET",
            data: {
                limit: limit,
                offset: (page - 1) * limit
            },
            success: function (data) {
                displayTasks(data.tasks);
                updatePagination(data.totalCount, page);
            }
        });
    }

    // Отображение задач в таблице
    function displayTasks(tasks) {
        const tbody = $("#tasks-tbody");
        tbody.empty();
        tasks.forEach(task => {
            const row = `
                <tr data-id="${task.id}">
                    <td>${task.name}</td>
                    <td>${task.shortDescription}</td>
                    <td>${task.date}</td>
                    <td>${task.status ? "Выполнено" : "Не выполнено"}</td>
                </tr>
            `;
            tbody.append(row);
        });
    }

    // Обновление информации о пагинации
    function updatePagination(totalCount, page) {
        const totalPages = Math.ceil(totalCount / limit);
        $("#page-info").text(`Страница ${page} из ${totalPages}`);
        $("#prev-page").prop("disabled", page === 1);
        $("#next-page").prop("disabled", page === totalPages);
    }

    // Фильтрация задач по диапазону дат
    $("#filter-date-btn").click(function () {
        const from = $("#start-date").val();
        const to = $("#end-date").val();
        $.ajax({
            url: "/api/todos/date",
            method: "GET",
            data: { from: from, to: to, status: true },
            success: function (data) {
                displayTasks(data);
            }
        });
    });

    // Обработка кнопки "Предыдущая" для пагинации
    $("#prev-page").click(function () {
        if (currentPage > 1) {
            currentPage--;
            loadTasks(currentPage);
        }
    });

    // Обработка кнопки "Следующая" для пагинации
    $("#next-page").click(function () {
        currentPage++;
        loadTasks(currentPage);
    });

    // Инициализация загрузки задач при запуске страницы
    loadTasks();
});
