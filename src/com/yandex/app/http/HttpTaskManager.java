package com.yandex.app.http;

import com.google.gson.*;
import com.yandex.app.http.KVTaskClient;
import com.yandex.app.model.Epic;
import com.yandex.app.model.LocalDateAdapter;
import com.yandex.app.model.Subtask;
import com.yandex.app.model.Task;
import com.yandex.app.service.FileBackedTasksManager;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

public class HttpTaskManager extends FileBackedTasksManager {

    private static final String TASKS = "/tasks";
    private static final String EPICS = "/epics";
    private static final String SUBTASKS = "/subtasks";
    private static final String HISTORY = "/history";

    private final KVTaskClient client;

    private final Gson gson = new GsonBuilder()
            .registerTypeAdapter(LocalDateTime.class, new LocalDateAdapter())
            .create();

    public HttpTaskManager() {
        client = new KVTaskClient();
    }

    public void save() {
        client.put(TASKS, gson.toJson(getListOfTasks()));
        client.put(EPICS, gson.toJson(getListOfEpics()));
        client.put(SUBTASKS, gson.toJson(getListOfSubtasks()));
        client.put(HISTORY, gson.toJson(getHistory().stream().map(Task::getId).collect(Collectors.toList())));
    }

    public void load() {
        JsonElement tasks = JsonParser.parseString(client.load(TASKS));
        JsonArray tasksArray = tasks.getAsJsonArray();
        for (JsonElement jsonTask : tasksArray) {
            Task task = gson.fromJson(jsonTask, Task.class);
            this.addTask(task);
        }

        JsonElement epics = JsonParser.parseString(client.load(EPICS));
        JsonArray epicsArray = epics.getAsJsonArray();
        for (JsonElement jsonEpic : epicsArray) {
            Epic epic = gson.fromJson(jsonEpic, Epic.class);
            this.addEpic(epic);
        }

        JsonElement subtasks = JsonParser.parseString(client.load(SUBTASKS));
        JsonArray subtasksArray = subtasks.getAsJsonArray();
        for (JsonElement jsonSubtask : subtasksArray) {
            Subtask subtask = gson.fromJson(jsonSubtask, Subtask.class);
            this.addSubtask(subtask);
        }

        JsonElement historyList = JsonParser.parseString(client.load(HISTORY));
        JsonArray historyArray = historyList.getAsJsonArray();
        for (JsonElement jsonId : historyArray) {
            int id = jsonId.getAsInt();
            if (this.tasks.containsKey(id)) {
                this.getTaskById(id);
            }
            if (this.epics.containsKey(id)) {
                this.getEpicById(id);
            }
            if (this.subtasks.containsKey(id)) {
                this.getSubtaskById(id);
            }
        }
    }
}
