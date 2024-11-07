$(function () {
    var startDate = null; // Начальная дата
    var endDate = null; // Конечная дата
    var selectedDates = []; // Массив для хранения выделенных дат
    let currentPage = 0; // Текущая страница
    const limit = 10; // Количество задач на странице

    // Инициализация календаря
    $("#calendar").datepicker({
        onSelect: function (dateText) {
            var date = $(this).datepicker("getDate");

            if (!startDate) {
                startDate = date;
                selectedDates.push(date);
                console.log("Выбрана начальная дата:", $.datepicker.formatDate("dd-mm-yy", startDate));
            } else {
                if (endDate) {
                    console.log("Сброс выбранной даты.");
                    startDate = date;
                    endDate = null;
                    selectedDates = [date];
                    console.log("Выбрана новая начальная дата:", $.datepicker.formatDate("dd-mm-yy", startDate));
                } else {
                    endDate = date;
                    highlightDates(startDate, endDate);
                    console.log("Выбрана конечная дата:", $.datepicker.formatDate("dd-mm-yy", endDate));
                    console.log("Выбранный диапазон дат:", $.datepicker.formatDate("dd-mm-yy", startDate), "по", $.datepicker.formatDate("dd-mm-yy", endDate));
                }
            }
            updateHighlightedDates();
            loadTasks(currentPage, "", startDate, endDate); // Загружаем задачи по датам
        }
    });

    // Функция для обновления фильтрации
    function getStatusFilter() {
        return $(".show-incomplete-tasks").prop("checked") ? "false" : "";
    }

    function updateHighlightedDates() {
        if (startDate && endDate) {
            highlightDates(startDate, endDate);
        } else {
            $("#calendar").datepicker("option", "beforeShowDay", function () {
                return [true, ""];
            });
            $("#calendar").datepicker("refresh");
        }
    }

    function highlightDates(startDate, endDate) {
        $("#calendar").datepicker("option", "beforeShowDay", function (date) {
            var highlightClass = "";
            if (date >= startDate && date <= endDate) {
                highlightClass = "highlighted";
            }
            return [true, highlightClass];
        });
        $("#calendar").datepicker("refresh");
    }

    // Функция для загрузки задач
    function loadTasks(page = 0, searchQuery = '', startDate = null, endDate = null) {
        let url = "";
        const statusFilter = getStatusFilter();

        // Если есть поиск по названию задачи
        if (searchQuery) {
            url = `http://localhost:8080/api/todos/find?q=${searchQuery}&page=${page}&limit=${limit}`;
        } else if (startDate && endDate) {
            // Преобразуем даты в нужный формат ISO 8601
            const formattedStartDate = startDate.toISOString();
            const formattedEndDate = endDate.toISOString();
            url = `http://localhost:8080/api/todos/date?from=${formattedStartDate}&to=${formattedEndDate}&page=${page}&limit=${limit}`;

            // Добавляем фильтрацию по статусу, если он выбран
            if (statusFilter) {
                url += `&status=${statusFilter}`;
            }
        } else {
            // Если нет поиска по названию или датам, стандартный запрос по статусу
            url = `http://localhost:8080/api/todos?page=${page}&limit=${limit}`;
        }

        // Если фильтрация по статусу (чекбокс "Показать только невыполненные задачи")
        if (statusFilter) {
            url += `&status=${statusFilter}`;
        }

        console.log("Запрашиваемый URL:", url); // Отладка запроса

        // Загружаем данные с сервера
        fetch(url)
            .then(response => response.json())
            .then(data => {
                console.log("Полученные данные:", data); // Отладка полученных данных
                displayTasks(data); // Отображаем задачи
                createPagination(data.total); // Создаем пагинацию
            })
            .catch(error => console.error("Ошибка при загрузке задач:", error));
    }

    // Функция для отображения задач в HTML
    function displayTasks(tasks) {
        const taskList = document.querySelector(".task-list");
        taskList.innerHTML = "";

        if (tasks.length === 0) {
            console.log("Нет задач для отображения"); // Отладка, если нет задач
        }

        // Отображаем каждую задачу
        tasks.forEach(task => {
            const taskDiv = document.createElement("div");
            taskDiv.classList.add("task");
            taskDiv.onclick = () => showTaskDetails(task.id);

            taskDiv.innerHTML = `
            <div class="task-info">
                <div class="task-title">${task.name}</div>
                <div class="task-desc">${task.shortDescription}</div>
            </div>
            <div class="status-area">
                <input type="checkbox" class="status-checkbox" data-task-id="${task.id}" ${task.status ? "checked" : ""}>
                <span class="creation-date">${new Date(task.date).toLocaleDateString()}</span>
            </div>
        `;

            taskList.appendChild(taskDiv);
        });

        // Добавляем обработчики для изменения статуса задач
        document.querySelectorAll(".status-checkbox").forEach(checkbox => {
            checkbox.addEventListener("change", function (event) {
                const taskId = event.target.dataset.taskId;
                const isChecked = event.target.checked;
                updateTaskStatus(taskId, isChecked);
            });
        });
    }

    // Обработчик для изменения состояния чекбокса фильтра
    $(".show-incomplete-tasks").change(function () {
        loadTasks(currentPage, "", startDate, endDate); // Перезагружаем задачи, учитывая фильтрацию по датам
    });

    function showTaskDetails(taskId) {
        fetch(`http://localhost:8080/api/todos/${taskId}`)
            .then(response => response.json())
            .then(task => {
                document.getElementById("taskTitle").textContent = task.name;
                document.getElementById("taskDate").textContent = new Date(task.date).toLocaleString();
                document.getElementById("taskDescription").textContent = task.fullDescription;
                document.getElementById("taskStatusCheckbox").checked = task.status;

                // Добавляем обработчик изменения состояния чекбокса
                document.getElementById("taskStatusCheckbox").onclick = function (event) {
                    const isChecked = event.target.checked;
                    updateTaskStatus(taskId, isChecked);  // Обновляем статус задачи
                };
                // Показать модальное окно
                document.getElementById("taskDetailsModal").style.display = "block";
            })
            .catch(error => console.error("Ошибка при получении задачи:", error));
    }

    // Закрытие модального окна при клике на кнопку «Готово»
    document.querySelector(".close-button").addEventListener("click", function () {
        document.getElementById("taskDetailsModal").style.display = "none";
    });

    // Закрываем модальное окно при клике вне его
    window.addEventListener("click", function (event) {
        const modal = document.getElementById("taskDetailsModal");
        if (event.target === modal) {
            modal.style.display = "none";
        }
    });

    // Функция для создания элементов пагинации
    function createPagination(totalTasks) {
        const pagination = document.getElementById("pagination");
        pagination.innerHTML = "";

        const totalPages = Math.ceil(totalTasks / limit);

        for (let i = 0; i < totalPages; i++) {
            const pageButton = document.createElement("button");
            pageButton.innerText = i + 1;
            pageButton.onclick = () => loadTasks(i);
            pagination.appendChild(pageButton);
        }
    }

    // Функция для обновления статуса задачи
    function updateTaskStatus(taskId, isChecked) {
        fetch(`http://localhost:8080/api/todos/${taskId}`, {
            method: "PUT",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify({status: isChecked})  // Отправляем статус
        })
            .then(response => {
                if (response.ok) {
                    console.log("Статус задачи успешно обновлен");
                    loadTasks(currentPage); // Перезагружаем задачи после обновления
                } else {
                    throw new Error("Ошибка обновления статуса");
                }
            })
            .catch(error => console.error("Ошибка при обновлении статуса задачи:", error));
    }

    // Добавление обработчика события на кнопку поиска
    $("#searchInput").on("input", function () {
        loadTasks(currentPage, this.value, startDate, endDate); // Запуск фильтрации при вводе
    });

    // Начальная загрузка задач
    loadTasks(currentPage);
});
