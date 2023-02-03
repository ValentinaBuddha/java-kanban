package com.yandex.app.http;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import com.yandex.app.model.Epic;
import com.yandex.app.model.LocalDateAdapter;
import com.yandex.app.model.Subtask;
import com.yandex.app.model.Task;
import com.yandex.app.service.Managers;
import com.yandex.app.service.TaskManager;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.stream.Collectors;

public class HttpTaskServer {

    private static final int PORT = 8080;

    private final HttpServer httpServer;
    private final TaskManager taskManager;
    private final Gson gson = new GsonBuilder()
            .registerTypeAdapter(LocalDateTime.class, new LocalDateAdapter())
            .create();

    public HttpTaskServer(TaskManager taskManager) throws IOException {
        this.taskManager = taskManager;
        httpServer = HttpServer.create(new InetSocketAddress("localhost", PORT), 0);
        httpServer.createContext("/tasks", new TasksHandler());
    }

    public void start() {
        httpServer.start();
        System.out.println("HTTP-сервер запущен на " + PORT + " порту.");
    }

    public void stop() {
        httpServer.stop(0);
        System.out.println("HTTP-сервер на порту " + PORT + " остановлен.");
    }

    class TasksHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            System.out.println("Началась обработка /tasks запроса от клиента.");

            String query = exchange.getRequestURI().getQuery();
            int id;

            Endpoint endpoint = getEndpoint(exchange.getRequestURI().getPath(), exchange.getRequestMethod(), query);

            switch (endpoint) {
                case POST_TASK:
                    InputStream inputStreamTask = exchange.getRequestBody();
                    String bodyTask = new String(inputStreamTask.readAllBytes(), StandardCharsets.UTF_8);
                    if (bodyTask.isEmpty()) {
                        writeResponse(exchange, "Необходимо заполнить все поля задачи", 400);
                        return;
                    }
                    try {
                        Task task = gson.fromJson(bodyTask, Task.class);
                        if (task.getId() == null) {
                            taskManager.addTask(task);
                            writeResponse(exchange, "Задача добавлена", 201);
                        } else {
                            taskManager.updateTask(task);
                            writeResponse(exchange, "Задача обновлена", 201);
                        }
                    } catch (JsonSyntaxException e) {
                        writeResponse(exchange, "Получен некорректный JSON", 400);
                    }
                    break;
                case POST_EPIC:
                    InputStream inputStreamEpic = exchange.getRequestBody();
                    String bodyEpic = new String(inputStreamEpic.readAllBytes(), StandardCharsets.UTF_8);
                    if (bodyEpic.isEmpty()) {
                        writeResponse(exchange, "Необходимо заполнить все поля задачи", 400);
                        return;
                    }
                    try {
                        Epic epic = gson.fromJson(bodyEpic, Epic.class);
                        if (epic.getId() == null) {
                            taskManager.addEpic(epic);
                            writeResponse(exchange, "Эпик добавлен", 201);
                        } else {
                            taskManager.updateEpic(epic);
                            writeResponse(exchange, "Эпик обновлен", 201);
                        }
                    } catch (JsonSyntaxException e) {
                        writeResponse(exchange, "Получен некорректный JSON", 400);
                    }
                    break;
                case POST_SUBTASK:
                    InputStream inputStreamSubtask = exchange.getRequestBody();
                    String bodySubtask = new String(inputStreamSubtask.readAllBytes(), StandardCharsets.UTF_8);
                    if (bodySubtask.isEmpty()) {
                        writeResponse(exchange, "Необходимо заполнить все поля задачи", 400);
                        return;
                    }
                    try {
                        Subtask subtask = gson.fromJson(bodySubtask, Subtask.class);
                        if (subtask.getId() == null) {
                            taskManager.addSubtask(subtask);
                            writeResponse(exchange, "Подзадача добавлена", 201);
                        } else {
                            taskManager.updateSubtask(subtask);
                            writeResponse(exchange, "Подзадача обновлена", 201);
                        }
                    } catch (JsonSyntaxException e) {
                        writeResponse(exchange, "Получен некорректный JSON", 400);
                    }
                case GET_TASK:
                    id = getId(query);
                    if (id == -1) {
                        writeResponse(exchange, "Некорректный id", 400);
                        return;
                    }
                    Task task = taskManager.getTaskById(id);
                    if (task != null) {
                        writeResponse(exchange, gson.toJson(task), 200);
                    } else {
                        writeResponse(exchange, "Задача с id " + id + " не найдена", 404);
                    }
                    break;
                case GET_EPIC:
                    id = getId(query);
                    if (id == -1) {
                        writeResponse(exchange, "Некорректный id", 400);
                        return;
                    }
                    Epic epic = taskManager.getEpicById(id);
                    if (epic != null) {
                        writeResponse(exchange, gson.toJson(epic), 200);
                    } else {
                        writeResponse(exchange, "Эпик с id " + id + " не найден", 404);
                    }
                    break;
                case GET_SUBTASK:
                    id = getId(query);
                    if (id == -1) {
                        writeResponse(exchange, "Некорректный id", 400);
                        return;
                    }
                    Subtask subtask = taskManager.getSubtaskById(id);
                    if (subtask != null) {
                        writeResponse(exchange, gson.toJson(subtask), 200);
                    } else {
                        writeResponse(exchange, "Подзадача с id " + id + " не найдена", 404);
                    }
                    break;
                case GET_SUBTASKS_EPIC:
                    id = getId(query);
                    if (id == -1) {
                        writeResponse(exchange, "Некорректный id", 400);
                        return;
                    }
                    if (taskManager.getEpicById(id) != null) {
                        writeResponse(exchange, gson.toJson(taskManager.getListOfSubtasksByOneEpic(id)), 200);
                    } else {
                        writeResponse(exchange, "Эпик с id " + id + " не найден", 404);
                    }
                    break;
                case DELETE_TASK:
                    id = getId(query);
                    if (id == -1) {
                        writeResponse(exchange, "Некорректный id", 400);
                        return;
                    }
                    if (taskManager.getTaskById(id) != null) {
                        taskManager.removeTaskById(id);
                        writeResponse(exchange, "Задача удалена", 200);
                    } else {
                        writeResponse(exchange, "Задача с id " + id + " не найдена", 404);
                    }
                    break;
                case DELETE_EPIC:
                    id = getId(query);
                    if (id == -1) {
                        writeResponse(exchange, "Некорректный id", 400);
                        return;
                    }
                    if (taskManager.getEpicById(id) != null) {
                        taskManager.removeEpicById(id);
                        writeResponse(exchange, "Эпик удален", 200);
                    } else {
                        writeResponse(exchange, "Эпик с id " + id + " не найден", 404);
                    }
                    break;
                case DELETE_SUBTASK:
                    id = getId(query);
                    if (id == -1) {
                        writeResponse(exchange, "Некорректный id", 400);
                        return;
                    }
                    if (taskManager.getSubtaskById(id) != null) {
                        taskManager.removeSubtaskById(id);
                        writeResponse(exchange, "Подзадача удалена", 200);
                    } else {
                        writeResponse(exchange, "Подзадача с id " + id + " не найдена", 404);
                    }
                    break;
                case DELETE_TASKS:
                    taskManager.removeAllTasks();
                    if (taskManager.getListOfTasks().isEmpty()) {
                        writeResponse(exchange, "Все задачи удалены", 200);
                    }
                    break;
                case DELETE_EPICS:
                    taskManager.removeAllEpics();
                    if (taskManager.getListOfEpics().isEmpty()) {
                        writeResponse(exchange, "Все эпики удалены", 200);
                    }
                    break;
                case DELETE_SUBTASKS:
                    taskManager.removeAllSubtasks();
                    if (taskManager.getListOfSubtasks().isEmpty()) {
                        writeResponse(exchange, "Все подзадачи удалены", 200);
                    }
                    break;
                case GET_TASKS:
                    writeResponse(exchange, gson.toJson(taskManager.getListOfTasks()), 200);
                    break;
                case GET_EPICS:
                    writeResponse(exchange, gson.toJson(taskManager.getListOfEpics()), 200);
                    break;
                case GET_SUBTASKS:
                    writeResponse(exchange, gson.toJson(taskManager.getListOfSubtasks()), 200);
                    break;
                case GET_HISTORY:
                    writeResponse(exchange, gson.toJson(taskManager.getHistory()), 200);
                    break;
                case GET_PRIORITY:
                    writeResponse(exchange, gson.toJson(taskManager.getPrioritizedTasks()), 200);
                    break;
                default:
                    writeResponse(exchange, "Такого эндпоинта не существует", 404);
                    break;
            }
        }

        private Endpoint getEndpoint(String path, String method, String query) {
            String[] pathParts = path.split("/");
            if (pathParts.length == 2) {
                return Endpoint.GET_PRIORITY;
            }
            String map = pathParts[2];

            if (query != null) {
                if (method.equals("GET")) {
                    if (map.equals("task")) {
                        return Endpoint.GET_TASK;
                    }
                    if (map.equals("epic")) {
                        return Endpoint.GET_EPIC;
                    }
                    if (map.equals("subtask")) {
                        if (pathParts.length == 3) {
                            return Endpoint.GET_SUBTASK;
                        } else {
                            return Endpoint.GET_SUBTASKS_EPIC;
                        }
                    }
                }
                if (method.equals("DELETE")) {
                    if (map.equals("task")) {
                        return Endpoint.DELETE_TASK;
                    }
                    if (map.equals("epic")) {
                        return Endpoint.DELETE_EPIC;
                    }
                    if (map.equals("subtask")) {
                        return Endpoint.DELETE_SUBTASK;
                    }
                }
            } else {
                if (method.equals("GET")) {
                    if (map.equals("task")) {
                        return Endpoint.GET_TASKS;
                    }
                    if (map.equals("epic")) {
                        return Endpoint.GET_EPICS;
                    }
                    if (map.equals("subtask")) {
                        return Endpoint.GET_SUBTASKS;
                    }
                    if (map.equals("history")) {
                        return Endpoint.GET_HISTORY;
                    }
                }
                if (method.equals("POST")) {
                    if (map.equals("task")) {
                        return Endpoint.POST_TASK;
                    }
                    if (map.equals("epic")) {
                        return Endpoint.POST_EPIC;
                    }
                    if (map.equals("subtask")) {
                        return Endpoint.POST_SUBTASK;
                    }
                }
                if (method.equals("DELETE")) {
                    if (map.equals("task")) {
                        return Endpoint.DELETE_TASKS;
                    }
                    if (map.equals("epic")) {
                        return Endpoint.DELETE_EPICS;
                    }
                    if (map.equals("subtask")) {
                        return Endpoint.DELETE_SUBTASKS;
                    }
                }
            }
            return Endpoint.UNKNOWN;
        }

        private void writeResponse(HttpExchange exchange, String response, int code) throws IOException {
            byte[] bytes = response.getBytes(StandardCharsets.UTF_8);
            exchange.sendResponseHeaders(code, bytes.length);
            try (OutputStream os = exchange.getResponseBody()) {
                os.write(bytes);
            }
            exchange.close();
        }

        private int getId(String query) {
            try {
                return Optional.of(Integer.parseInt(query.replaceFirst("id=", ""))).get();
            } catch (NumberFormatException exception) {
                return -1;
            }
        }

        enum Endpoint {
            DELETE_TASKS, DELETE_SUBTASKS, DELETE_EPICS, GET_TASKS, GET_EPICS, GET_SUBTASKS, GET_TASK, GET_EPIC,
            GET_SUBTASK, GET_SUBTASKS_EPIC, DELETE_TASK, DELETE_EPIC, DELETE_SUBTASK, POST_TASK, POST_SUBTASK,
            POST_EPIC, GET_HISTORY, GET_PRIORITY, UNKNOWN
        }
    }
}

