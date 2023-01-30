package com.yandex.app;

import com.yandex.app.model.Epic;
import com.yandex.app.model.Subtask;
import com.yandex.app.model.Task;

import com.yandex.app.http.KVServer;
import com.yandex.app.service.Managers;
import com.yandex.app.service.TaskManager;

import java.io.IOException;
import java.time.LocalDateTime;

public class Main {

    public static void main(String[] args) throws IOException, InterruptedException {
        KVServer kvServer = new KVServer();
        kvServer.start();
        TaskManager taskManager = Managers.getDefault();

        Task task1 = new Task("Задача", "description1",
                LocalDateTime.of(2023, 1, 1, 0, 0), 1000);
        Task task2 = new Task("Задача", "description2",
                LocalDateTime.of(2023, 1, 2, 0, 0), 1000);
        Epic epic3 = new Epic("Эпик", "description3");
        Epic epic4 = new Epic("Эпик", "description4");
        Subtask subtask5 = new Subtask("Подзадача", "description5", 3,
                LocalDateTime.of(2023, 1, 3, 0, 0), 1000);
        Subtask subtask6 = new Subtask("Подзадача", "description6", 3,
                LocalDateTime.of(2023, 1, 4, 0, 0), 1000);
        Subtask subtask7 = new Subtask("Подзадача", "description7", 4,
                LocalDateTime.of(2023, 1, 5, 0, 0), 1000);

        taskManager.addTask(task1);
        taskManager.addTask(task2);
        taskManager.addEpic(epic3);
        taskManager.addEpic(epic4);
        taskManager.addSubtask(subtask5);
        taskManager.addSubtask(subtask6);
        taskManager.addSubtask(subtask7);

        taskManager.getEpicById(4);
        taskManager.getTaskById(2);
        taskManager.getSubtaskById(6);

        System.out.println(taskManager.getListOfTasks());
        System.out.println(taskManager.getListOfEpics());
        System.out.println(taskManager.getListOfSubtasks());

        taskManager.removeAllTasks();
        taskManager.removeAllEpics();
        taskManager.removeAllSubtasks();

        System.out.println(taskManager.getListOfTasks());
        System.out.println(taskManager.getListOfEpics());
        System.out.println(taskManager.getListOfSubtasks());

        kvServer.stop();
    }
}

//        //проверка api
//        HttpTaskServer server = new HttpTaskServer();
//        server.start();

//        HttpClient client = HttpClient.newHttpClient();
//        Gson gson = new GsonBuilder()
//                .registerTypeAdapter(LocalDateTime.class, new LocalDateAdapter())
//                .create();

//        //получить задачу
//        URI urlGet = URI.create("http://localhost:8080/tasks/task/?id=1");
//        HttpRequest request = HttpRequest.newBuilder()
//                .uri(urlGet)
//                .header("Accept", "application/json")
//                .GET()
//                .build();
//        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
//        try {
//            if (response.statusCode() == 200) {
//                JsonElement jsonElement = JsonParser.parseString(response.body());
//                JsonObject jsonTask = jsonElement.getAsJsonObject();
//                Task taskDeserialized = gson.fromJson(jsonTask, Task.class);
//                System.out.println(taskDeserialized);
//            } else {
//                System.out.println("Что-то пошло не так. Сервер вернул код состояния: " + response.statusCode());
//            }
//        } catch (NullPointerException e) {
//            System.out.println("Во время выполнения запроса возникла ошибка.\n" +
//                    "Проверьте, пожалуйста, адрес и повторите попытку.");
//        }

//        //получить все задачи
//        URI url = URI.create("http://localhost:8080/tasks/task/");
//        HttpRequest request = HttpRequest.newBuilder()
//                .uri(url)
//                .header("Accept", "application/json")
//                .GET()
//                .build();
//        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
//        try {
//            if (response.statusCode() == 200) {
//                JsonElement jsonElement = JsonParser.parseString(response.body());
//                List<Task> tasksArray = gson.fromJson(jsonElement, new TypeToken<Collection<Task>>() {}.getType());
//                System.out.println(tasksArray);
//            } else {
//                System.out.println("Что-то пошло не так. Сервер вернул код состояния: " + response.statusCode());
//            }
//        } catch (NullPointerException e) {
//            System.out.println("Во время выполнения запроса возникла ошибка.\n" +
//                    "Проверьте, пожалуйста, адрес и повторите попытку.");
//        }

//        //создать задачу
//        URI url = URI.create("http://localhost:8080/tasks/task/");
//        String json = gson.toJson(task1);
//        final HttpRequest.BodyPublisher body = HttpRequest.BodyPublishers.ofString(json);
//        HttpRequest request = HttpRequest.newBuilder()
//                .uri(url)
//                .header("Accept", "application/json")
//                .POST(body)
//                .build();
//        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
//        System.out.println("Код ответа: " + response.statusCode());
//        System.out.println("Тело ответа: " + response.body());






/*{
	"title": "Задача",
	"description": "description1",
	"id": 1,
	"status": "NEW",
	"startTime": "01.01.23 00:00",
	"duration": 1000
}*/

/*[
	{
		"title": "Задача",
		"description": "description1",
		"id": 1,
		"status": "NEW",
		"startTime": "01.01.23 00:00",
		"duration": 1000
	},
	{
		"title": "Задача",
		"description": "description2",
		"id": 2,
		"status": "NEW",
		"startTime": "02.01.23 00:00",
		"duration": 1000
	},
	{
		"epicId": 3,
		"title": "Подзадача",
		"description": "description5",
		"id": 5,
		"status": "NEW",
		"startTime": "03.01.23 00:00",
		"duration": 1000
	},
	{
		"epicId": 3,
		"title": "Подзадача",
		"description": "description6",
		"id": 6,
		"status": "NEW",
		"startTime": "04.01.23 00:00",
		"duration": 1000
	},
	{
		"epicId": 4,
		"title": "Подзадача",
		"description": "description7",
		"id": 7,
		"status": "NEW",
		"startTime": "05.01.23 00:00",
		"duration": 1000
	}
]*/
