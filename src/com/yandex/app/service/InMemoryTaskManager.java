package com.yandex.app.service;

import com.yandex.app.model.Epic;
import com.yandex.app.model.Subtask;
import com.yandex.app.model.Task;
import com.yandex.app.model.TaskStatus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InMemoryTaskManager implements TaskManager {
    private int counter = 0;
    private final Map<Integer, Task> tasks = new HashMap<>();
    private final Map<Integer, Subtask> subtasks = new HashMap<>();
    private final Map<Integer, Epic> epics = new HashMap<>();
    private final HistoryManager historyManager = Managers.getDefaultHistory();

    @Override
    public void createNewTask(Task newTask) {
        int taskId = ++counter;
        newTask.setId(taskId);
        tasks.put(taskId, newTask);
    }

    @Override
    public void createNewSubtask(Subtask newSubtask) {
        int newSubtaskId = ++counter;
        newSubtask.setId(newSubtaskId);
        subtasks.put(newSubtaskId, newSubtask);
        int epicId = newSubtask.getEpicId();
        ArrayList<Integer> subtaskIds = epics.get(epicId).getSubtaskIds();
        subtaskIds.add(newSubtaskId);
        checkEpicStatus(epicId);
    }

    @Override
    public void createNewEpic(Epic newEpic) {
        int epicId = ++counter;
        newEpic.setId(epicId);
        epics.put(epicId, newEpic);
    }

    @Override
    public void removeAllTasks() {
        tasks.clear();
    }

    @Override
    public void removeAllSubtasks() {
        for (Epic epic : epics.values()) {
            epic.getSubtaskIds().clear();
            checkEpicStatus(epic.getId());
        }
        subtasks.clear();
    }

    @Override
    public void removeAllEpics() {
        for (Epic epic : epics.values()) {
            int epicId = epic.getId();
            ArrayList<Integer> subtaskIds = epics.get(epicId).getSubtaskIds();
            for (Integer subtaskId : subtaskIds) {
                subtasks.remove(subtaskId);
            }
        }
        epics.clear();
    }

    @Override
    public ArrayList<Task> getListOfTasks() {
        return new ArrayList<>(tasks.values());
    }

    @Override
    public ArrayList<Epic> getListOfEpics() {
        return new ArrayList<>(epics.values());
    }

    @Override
    public ArrayList<Subtask> getListOfSubtasks() {
        return new ArrayList<>(subtasks.values());
    }

    @Override
    public Task getTaskById(int id) {
        if (tasks.get(id) != null) {
            historyManager.add(tasks.get(id));
        }
        return tasks.get(id);
    }

    @Override
    public Epic getEpicById(int id) {
        if (epics.get(id) != null) {
            historyManager.add(epics.get(id));
        }
        return epics.get(id);
    }

    @Override
    public Subtask getSubtaskById(int id) {
        if (subtasks.get(id) != null) {
            historyManager.add(subtasks.get(id));
        }
        return subtasks.get(id);
    }

    @Override
    public ArrayList<Subtask> getListOfSubtasksByOneEpic(int id) {
        ArrayList<Integer> subtaskIds = epics.get(id).getSubtaskIds();
        ArrayList<Subtask> subtasksByOneEpic = new ArrayList<>();
        for (int subtaskId : subtaskIds) {
            subtasksByOneEpic.add(subtasks.get(subtaskId));
        }
        return subtasksByOneEpic;
    }

    @Override
    public void removeTaskById(int taskId) {
        tasks.remove(taskId);
    }

    @Override
    public void removeEpicById(int epicId) {
        ArrayList<Integer> subtaskIds = epics.get(epicId).getSubtaskIds();
        for (Integer subtaskId : subtaskIds) {
            subtasks.remove(subtaskId);
        }
        epics.remove(epicId);
    }

    @Override
    public void removeSubtaskById(int subtaskIdForRemove) {
        int epicId = subtasks.get(subtaskIdForRemove).getEpicId();
        ArrayList<Integer> subtaskIds = epics.get(epicId).getSubtaskIds();
        subtaskIds.remove((Integer) subtaskIdForRemove);
        subtasks.remove(subtaskIdForRemove);
        checkEpicStatus(epicId);
    }

    @Override
    public void updateTheTask(Task updateTask) {
        tasks.put(updateTask.getId(), updateTask);
    }

    @Override
    public void updateTheEpic(Epic updateEpic) {
        epics.put(updateEpic.getId(), updateEpic);
    }

    @Override
    public void updateTheSubtask(Subtask updateSubtask) {
        subtasks.put(updateSubtask.getId(), updateSubtask);
        int epicId = subtasks.get(updateSubtask.getId()).getEpicId();
        checkEpicStatus(epicId);
    }

    @Override
    public void checkEpicStatus(int epicId) {
        int counterNEW = 0;
        int counterDONE = 0;
        ArrayList<Integer> subtaskIds = epics.get(epicId).getSubtaskIds();
        for (Integer subtaskId : subtaskIds) {
            if (subtasks.get(subtaskId).getStatus().equals(TaskStatus.NEW)) {
                counterNEW++;
            } else if (subtasks.get(subtaskId).getStatus().equals(TaskStatus.DONE)) {
                counterDONE++;
            }
        }
        if (subtaskIds.size() == counterNEW || subtaskIds.isEmpty()) {
            epics.get(epicId).setStatus(TaskStatus.NEW);
        } else if (subtaskIds.size() == counterDONE) {
            epics.get(epicId).setStatus(TaskStatus.DONE);
        } else {
            epics.get(epicId).setStatus(TaskStatus.IN_PROGRESS);
        }
    }

    @Override
    public List<Task> getHistory() {
        return historyManager.getHistory();
    }

    //дополнительный для проверки
    void print() {
        System.out.println(tasks);
        System.out.println(epics);
        System.out.println(subtasks);
    }
}