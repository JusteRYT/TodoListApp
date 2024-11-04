$(document).ready(function () {

    // Получение и отображение всех задач
    function loadTasks() {
        $.ajax({
            url: "/api/todos",
            method: "GET",
            success: function (data) {
                displayTasks(data);
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

    // Поиск задач по названию
    $("#search-input").on("input", function () {
        const query = $(this).val();
        if (query) {
            $.ajax({
                url: `/api/todos/find`,
                method: "GET",
                data: { q: query },
                success: function (data) {
                    $("#search-results").empty();
                    data.forEach(task => {
                        $("#search-results").append(`<li data-id="${task.id}">${task.name}</li>`);
                    });
                }
            });
        } else {
            $("#search-results").empty();
        }
    });

    // Фильтрация задач по дате (сегодняшний день)
    $("#today-btn").click(function () {
        const today = new Date().toISOString().split("T")[0];
        $.ajax({
            url: "/api/todos/date",
            method: "GET",
            data: { from: today, to: today, status: true },
            success: function (data) {
                displayTasks(data);
            }
        });
    });

    // Фильтрация задач по текущей неделе
    $("#week-btn").click(function () {
        const today = new Date();
        const firstDayOfWeek = new Date(today.setDate(today.getDate() - today.getDay())).toISOString().split("T")[0];
        const lastDayOfWeek = new Date(today.setDate(today.getDate() - today.getDay() + 6)).toISOString().split("T")[0];
        $.ajax({
            url: "/api/todos/date",
            method: "GET",
            data: { from: firstDayOfWeek, to: lastDayOfWeek, status: true },
            success: function (data) {
                displayTasks(data);
            }
        });
    });

    // Открытие полного описания задачи в модальном окне
    $("#tasks-tbody").on("click", "tr", function () {
        const taskId = $(this).data("id");
        $.ajax({
            url: `/api/todos/${taskId}`,
            method: "GET",
            success: function (task) {
                $("#modal-title").text(task.name);
                $("#modal-full-desc").text(task.fullDescription);
                $("#modal").show();
            }
        });
    });

    // Закрытие модального окна
    $(".close").click(function () {
        $("#modal").hide();
    });

    // Инициализация загрузки задач при запуске страницы
    loadTasks();
});
