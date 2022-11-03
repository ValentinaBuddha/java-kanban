package service;

import model.Epic;
import model.Subtask;
import model.Task;
import model.TaskStatus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InMemoryTaskManager implements TaskManager {
    protected int generatorId = 0;
    protected final Map<Integer, Task> tasks = new HashMap<>();
    protected final Map<Integer, Epic> epics = new HashMap<>();
    protected final Map<Integer, Subtask> subtasks = new HashMap<>();
    protected final HistoryManager historyManager = Managers.getDefaultHistory();

    @Override
    public void addTask(Task newTask) {
        int taskId = ++generatorId;
        newTask.setId(taskId);
        tasks.put(taskId, newTask);
    }

    @Override
    public void addSubtask(Subtask newSubtask) {
        int newSubtaskId = ++generatorId;
        newSubtask.setId(newSubtaskId);
        subtasks.put(newSubtaskId, newSubtask);
        int epicId = newSubtask.getEpicId();
        List<Integer> subtaskIds = epics.get(epicId).getSubtaskIds();
        subtaskIds.add(newSubtaskId);
        checkEpicStatus(epicId);
    }

    @Override
    public void addEpic(Epic newEpic) {
        int epicId = ++generatorId;
        newEpic.setId(epicId);
        epics.put(epicId, newEpic);
    }

    @Override
    public void removeAllTasks() {
        for (Integer id : tasks.keySet()) {
            historyManager.remove(id);
        }
        tasks.clear();
    }

    @Override
    public void removeAllSubtasks() {
        for (Epic epic : epics.values()) {
            epic.getSubtaskIds().clear();
            checkEpicStatus(epic.getId());
        }
        for (Integer id : subtasks.keySet()) {
            historyManager.remove(id);
        }
        subtasks.clear();
    }

    @Override
    public void removeAllEpics() {
        for (Epic epic : epics.values()) {
            int epicId = epic.getId();
            List<Integer> subtaskIds = epics.get(epicId).getSubtaskIds();
            for (Integer subtaskId : subtaskIds) {
                subtasks.remove(subtaskId);
                historyManager.remove(subtaskId);
            }
        }
        for (Integer id : epics.keySet()) {
            historyManager.remove(id);
        }
        epics.clear();
    }

    @Override
    public List<Task> getListOfTasks() {
        return new ArrayList<>(tasks.values());
    }

    @Override
    public List<Epic> getListOfEpics() {
        return new ArrayList<>(epics.values());
    }

    @Override
    public List<Subtask> getListOfSubtasks() {
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
    public List<Subtask> getListOfSubtasksByOneEpic(int id) {
        List<Integer> subtaskIds = epics.get(id).getSubtaskIds();
        List<Subtask> subtasksByOneEpic = new ArrayList<>();
        for (int subtaskId : subtaskIds) {
            subtasksByOneEpic.add(subtasks.get(subtaskId));
        }
        return subtasksByOneEpic;
    }

    @Override
    public void removeTaskById(int id) {
        tasks.remove(id);
        historyManager.remove(id);
    }

    @Override
    public void removeEpicById(int epicId) {
        List<Integer> subtaskIds = epics.get(epicId).getSubtaskIds();
        for (Integer subtaskId : subtaskIds) {
            subtasks.remove(subtaskId);
            historyManager.remove(subtaskId);
        }
        epics.remove(epicId);
        historyManager.remove(epicId);
    }

    @Override
    public void removeSubtaskById(int subtaskIdForRemove) {
        int epicId = subtasks.get(subtaskIdForRemove).getEpicId();
        List<Integer> subtaskIds = epics.get(epicId).getSubtaskIds();
        subtaskIds.remove((Integer) subtaskIdForRemove);
        subtasks.remove(subtaskIdForRemove);
        checkEpicStatus(epicId);
        historyManager.remove(subtaskIdForRemove);
    }

    @Override
    public void updateTask(Task updateTask) {
        tasks.put(updateTask.getId(), updateTask);
    }

    @Override
    public void updateEpic(Epic updateEpic) {
        epics.put(updateEpic.getId(), updateEpic);
    }

    @Override
    public void updateSubtask(Subtask updateSubtask) {
        subtasks.put(updateSubtask.getId(), updateSubtask);
        int epicId = subtasks.get(updateSubtask.getId()).getEpicId();
        checkEpicStatus(epicId);
    }

    @Override
    public void checkEpicStatus(int epicId) {
        int counterNEW = 0;
        int counterDONE = 0;
        List<Integer> subtaskIds = epics.get(epicId).getSubtaskIds();
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
}