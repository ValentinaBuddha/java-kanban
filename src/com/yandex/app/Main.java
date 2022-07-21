package com.yandex.app;

import com.yandex.app.service.InMemoryTaskManager;
import com.yandex.app.model.Epic;
import com.yandex.app.model.Subtask;
import com.yandex.app.model.Task;
import com.yandex.app.model.TaskStatus;

public class Main {

    public static void main(String[] args) {
        InMemoryTaskManager inMemoryTaskManager = new InMemoryTaskManager();

        //создаем задачи
        Task task1 = new Task("Задача", "123");
        Task task2 = new Task("Задача", "123");
        inMemoryTaskManager.createNewTask(task1);
        inMemoryTaskManager.createNewTask(task2);

        //создаем эпики
        Epic epic3 = new Epic("Эпик", "123");
        Epic epic4 = new Epic("Эпик", "123");
        inMemoryTaskManager.createNewEpic(epic3);
        inMemoryTaskManager.createNewEpic(epic4);

        //создаем подзадачи
        Subtask subtask5 = new Subtask("Подзадача", "123", 3);
        Subtask subtask6 = new Subtask("Подзадача", "123", 3);
        Subtask subtask7 = new Subtask("Подзадача", "123", 4);
        inMemoryTaskManager.createNewSubtask(subtask5);
        inMemoryTaskManager.createNewSubtask(subtask6);
        inMemoryTaskManager.createNewSubtask(subtask7);

        inMemoryTaskManager.getTaskById(task1.getId());
        inMemoryTaskManager.getEpicById(epic4.getId());
        inMemoryTaskManager.getSubtaskById(subtask7.getId());

        System.out.println(inMemoryTaskManager.getHistory());

        inMemoryTaskManager.getListOfSubtasksByOneEpic(epic3.getId());

        //обновляем задачу(название и статус)
        Task updateTask3 = new Task("Task", "123", task1.getId(), TaskStatus.IN_PROGRESS);
        inMemoryTaskManager.updateTheTask(updateTask3);
        //обновляем эпик(название)
        Epic updateEpic3 = new Epic("Epic", "123", epic3.getId(), epic3.getStatus(), epic3.getSubtaskIds());
        inMemoryTaskManager.updateTheEpic(updateEpic3);
        //обновляем подзадачу(статус)
        Subtask updateSubtask5 = new Subtask("Подзадача",
                "123", subtask5.getId(), TaskStatus.IN_PROGRESS, 3);
        inMemoryTaskManager.updateTheSubtask(updateSubtask5);
        Subtask updateSubtask7 = new Subtask("Подзадача",
                "123", subtask7.getId(), TaskStatus.DONE, 4);
        inMemoryTaskManager.updateTheSubtask(updateSubtask7);

        inMemoryTaskManager.getListOfTasks();
        inMemoryTaskManager.getListOfEpics();
        inMemoryTaskManager.getListOfSubtasks();

        inMemoryTaskManager.removeTaskById(task1.getId());
        inMemoryTaskManager.removeEpicById(epic4.getId());
        inMemoryTaskManager.removeSubtaskById(subtask6.getId());

        inMemoryTaskManager.removeAllTasks();
        inMemoryTaskManager.removeAllSubtasks();
        inMemoryTaskManager.removeAllEpics();
    }
}