package com.yandex.app.service;

import com.yandex.app.model.Epic;
import com.yandex.app.model.Subtask;
import com.yandex.app.model.Task;

import java.util.List;
import java.util.Map;

public interface TaskManager {

    void addTask(Task newTask);

    void addSubtask(Subtask newSubtask);

    void addEpic(Epic newEpic);

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

    void updateTask(Task updateTask);

    void updateEpic(Epic updateEpic);

    void updateSubtask(Subtask updateSubtask);

    void checkEpicStatus(int epicId);

    List<Task> getHistory();

    void setEpicDateTime(int epicId);

    List<Task> getPrioritizedTasks();

    void validate(Task newTask);
}
