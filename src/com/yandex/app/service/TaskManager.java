package com.yandex.app.service;

import com.yandex.app.model.Epic;
import com.yandex.app.model.Subtask;
import com.yandex.app.model.Task;

import java.util.List;

public interface TaskManager {

    void createNewTask(Task newTask);

    void createNewSubtask(Subtask newSubtask);

    void createNewEpic(Epic newEpic);

    void removeAllTasks();

    void removeAllSubtasks();

    void removeAllEpics();

    List<Task> getListOfTasks();

    List<Epic> getListOfEpics();

    List<Subtask> getListOfSubtasks();

    Task getTaskById(int id);

    Epic getEpicById(int id);

    Subtask getSubtaskById(int id);

    List<Subtask> getListOfSubtasksByOneEpic(int id);

    void removeTaskById(int taskId);

    void removeEpicById(int epicId);

    void removeSubtaskById(int subtaskIdForRemove);

    void updateTheTask(Task updateTask);

    void updateTheEpic(Epic updateEpic);

    void updateTheSubtask(Subtask updateSubtask);

    void checkEpicStatus(int epicId);

    List<Task> getHistory();
}
