package com.yandex.app;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.yandex.app.http.HttpTaskManager;
import com.yandex.app.http.HttpTaskServer;
import com.yandex.app.model.Epic;
import com.yandex.app.model.LocalDateAdapter;
import com.yandex.app.model.Subtask;
import com.yandex.app.model.Task;

import com.yandex.app.http.KVServer;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDateTime;
import java.util.List;

public class Main {

    public static void main(String[] args) throws IOException, InterruptedException {
        KVServer kvServer = new KVServer();
        kvServer.start();

        HttpTaskManager taskManager = new HttpTaskManager(false);

        HttpTaskServer server = new HttpTaskServer(taskManager);
        server.start();

        Task task1 = new Task("Задача", "description1",
                LocalDateTime.of(2023, 1, 1, 0, 0), 1000);
        Epic epic2 = new Epic("Эпик", "description2");
        Subtask subtask3 = new Subtask("Подзадача", "description3", 2,
                LocalDateTime.of(2023, 1, 2, 0, 0), 1000);
        Subtask subtask4 = new Subtask("Подзадача", "description4", 2,
                LocalDateTime.of(2023, 1, 3, 0, 0), 1000);

        taskManager.addTask(task1);
        taskManager.addEpic(epic2);
        taskManager.addSubtask(subtask3);
        taskManager.addSubtask(subtask4);

        taskManager.getEpicById(2);
        taskManager.getTaskById(1);
        taskManager.getSubtaskById(4);

//        HttpTaskManager httpTaskManager = new HttpTaskManager(true);
//
//        System.out.println(httpTaskManager.getListOfTasks());
//        System.out.println(httpTaskManager.getListOfEpics());
//        System.out.println(httpTaskManager.getListOfSubtasks());
//        System.out.println(httpTaskManager.getHistory());

        Gson gson = new GsonBuilder()
                .registerTypeAdapter(LocalDateTime.class, new LocalDateAdapter())
                .create();
        HttpClient client = HttpClient.newHttpClient();

        //получить все задачи
        URI url = URI.create("http://localhost:8080/tasks/task/");
        HttpRequest request = HttpRequest.newBuilder().uri(url).GET().build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        Type taskType = new TypeToken<List<Task>>() {
        }.getType();
        List<Task> tasksList = gson.fromJson(response.body(), taskType);
        System.out.println(response.statusCode());
        System.out.println(response.body());
        System.out.println(taskManager.getListOfTasks());
        System.out.println(tasksList);

        kvServer.stop();
        server.stop();

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

//

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
