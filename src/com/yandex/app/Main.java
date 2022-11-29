package com.yandex.app;

import com.yandex.app.model.Epic;
import com.yandex.app.model.Subtask;
import com.yandex.app.model.Task;
import com.yandex.app.service.Managers;
import com.yandex.app.service.TaskManager;

import java.time.LocalDateTime;

public class Main {

    public static void main(String[] args) {
        TaskManager taskManager = Managers.getDefault();

        Task task1 = new Task("Задача", "description1",
                LocalDateTime.of(2023, 1, 1, 0, 0), 1000);
        Task task2 = new Task("Задача", "description2",
                LocalDateTime.of(2023, 1, 2, 0, 0), 1000);
        taskManager.addTask(task1);
        taskManager.addTask(task2);

        Epic epic3 = new Epic("Эпик", "description3");
        Epic epic4 = new Epic("Эпик", "description4");
        taskManager.addEpic(epic3);
        taskManager.addEpic(epic4);

        Subtask subtask5 = new Subtask("Подзадача", "description5", 3,
                LocalDateTime.of(2023, 1, 3, 0, 0), 1000);
        Subtask subtask6 = new Subtask("Подзадача", "description6", 3,
                LocalDateTime.of(2023, 1, 4, 0, 0), 1000);
        Subtask subtask7 = new Subtask("Подзадача", "description7", 4,
                LocalDateTime.of(2023, 1, 5, 0, 0), 1000);
        taskManager.addSubtask(subtask5);
        taskManager.addSubtask(subtask6);
        taskManager.addSubtask(subtask7);

        taskManager.removeSubtaskById(5);

//        System.out.println(taskManager.getListOfTasks());
//        System.out.println(taskManager.getListOfEpics());
//        System.out.println(taskManager.getListOfSubtasks());

        //System.out.println(taskManager.getPrioritizedTasks());

        taskManager.getEpicById(3);
        taskManager.getSubtaskById(6);
        taskManager.getTaskById(1);
        taskManager.getSubtaskById(7);
        taskManager.getTaskById(1);

        System.out.println(taskManager.getHistory());//2431  //3671

    }
}



//        //Получение списка всех подзадач определённого эпика
//        inMemoryTaskManager.getListOfSubtasksByOneEpic(epic3.getId());
//
//        //обновляем задачу(название и статус)
//        Task updateTask3 = new Task("Task", "description3", task1.getId(), TaskStatus.IN_PROGRESS);
//        inMemoryTaskManager.updateTheTask(updateTask3);
//
//        //обновляем эпик(название)
//        Epic updateEpic3 = new Epic("Epic", "description3", epic3.getId(), epic3.getStatus(), epic3.getSubtaskIds());
//        inMemoryTaskManager.updateTheEpic(updateEpic3);
//
//        //обновляем подзадачу(статус)
//        Subtask updateSubtask5 = new Subtask("Подзадача",
//                "description5", subtask5.getId(), TaskStatus.IN_PROGRESS, 3);
//        inMemoryTaskManager.updateTheSubtask(updateSubtask5);
//        Subtask updateSubtask7 = new Subtask("Подзадача",
//                "description7", subtask7.getId(), TaskStatus.DONE, 4);
//        inMemoryTaskManager.updateTheSubtask(updateSubtask7);

//        //получаем задачи для проверки истории
//        inMemoryTaskManager.getTaskById(task2.getId());
//        inMemoryTaskManager.getTaskById(task1.getId());
//        inMemoryTaskManager.getEpicById(epic4.getId());
//        inMemoryTaskManager.getEpicById(epic3.getId());
//        inMemoryTaskManager.getSubtaskById(subtask7.getId());
//        inMemoryTaskManager.getEpicById(epic4.getId());
//        inMemoryTaskManager.getEpicById(epic3.getId());
//        inMemoryTaskManager.getSubtaskById(subtask7.getId());
//        inMemoryTaskManager.getTaskById(task1.getId());
//        inMemoryTaskManager.getTaskById(task2.getId());
//        inMemoryTaskManager.getSubtaskById(subtask5.getId());
//        inMemoryTaskManager.getSubtaskById(subtask6.getId());
//        inMemoryTaskManager.getSubtaskById(subtask6.getId());
//        inMemoryTaskManager.getSubtaskById(subtask5.getId());
//        System.out.println(inMemoryTaskManager.getHistory());//4371265
//
//        //удаляем задачу для проверки истории
//        inMemoryTaskManager.removeTaskById(task1.getId());
//        System.out.println(inMemoryTaskManager.getHistory());//437265
//
//        //удаляем эпик для проверки истории
//        inMemoryTaskManager.removeEpicById(epic3.getId());
//        System.out.println(inMemoryTaskManager.getHistory());//42
