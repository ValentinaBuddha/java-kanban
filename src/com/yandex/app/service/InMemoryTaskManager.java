package com.yandex.app.service;

import com.yandex.app.exceptions.CollisionTaskException;
import com.yandex.app.model.Epic;
import com.yandex.app.model.Subtask;
import com.yandex.app.model.Task;
import com.yandex.app.model.TaskStatus;

import java.time.LocalDateTime;
import java.util.*;

public class InMemoryTaskManager implements TaskManager {
    public int generatorId = 0;
    protected final Map<Integer, Task> tasks = new HashMap<>();
    protected final Map<Integer, Epic> epics = new HashMap<>();
    protected final Map<Integer, Subtask> subtasks = new HashMap<>();
    protected final HistoryManager historyManager = Managers.getDefaultHistory();

    final static Comparator<Task> COMPARATOR = Comparator.comparing(Task::getStartTime,
                    Comparator.nullsLast(Comparator.naturalOrder())).thenComparing(Task::getId);

    protected Set<Task> prioritizedTasks = new TreeSet<>(COMPARATOR);

    @Override
    public void addTask(Task newTask) {
        validate(newTask);
        int taskId = ++generatorId;
        newTask.setId(taskId);
        tasks.put(taskId, newTask);
        prioritizedTasks.add(newTask);
    }

    @Override
    public void addSubtask(Subtask newSubtask) {
        validate(newSubtask);
        int newSubtaskId = ++generatorId;
        newSubtask.setId(newSubtaskId);
        subtasks.put(newSubtaskId, newSubtask);
        int epicId = newSubtask.getEpicId();
        List<Integer> subtaskIds = epics.get(epicId).getSubtaskIds();
        subtaskIds.add(newSubtaskId);
        checkEpicStatus(epicId);
        setEpicDateTime(epicId);
        prioritizedTasks.add(newSubtask);
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
            prioritizedTasks.remove(tasks.get(id));
        }
        tasks.clear();
    }

    @Override
    public void removeAllSubtasks() {
        for (Epic epic : epics.values()) {
            epic.getSubtaskIds().clear();
            checkEpicStatus(epic.getId());
            setEpicDateTime(epic.getId());
        }
        for (Integer id : subtasks.keySet()) {
            historyManager.remove(id);
            prioritizedTasks.remove(subtasks.get(id));
        }
        subtasks.clear();
    }

    @Override
    public void removeAllEpics() {
        for (Epic epic : epics.values()) {
            int epicId = epic.getId();
            List<Integer> subtaskIds = epics.get(epicId).getSubtaskIds();
            for (Integer subtaskId : subtaskIds) {
                prioritizedTasks.remove(subtasks.get(subtaskId));
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
        Task task = tasks.get(id);
        if (task != null) {
            historyManager.add(task);
        }
        return task;
    }

    @Override
    public Epic getEpicById(int id) {
        Epic epic = epics.get(id);
        if (epic != null) {
            historyManager.add(epic);
        }
        return epic;
    }

    @Override
    public Subtask getSubtaskById(int id) {
        Subtask subtask = subtasks.get(id);
        if (subtask != null) {
            historyManager.add(subtask);
        }
        return subtask;
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
        prioritizedTasks.remove(tasks.get(id));
        tasks.remove(id);
        historyManager.remove(id);
    }

    @Override
    public void removeEpicById(int epicId) {
        List<Integer> subtaskIds = epics.get(epicId).getSubtaskIds();
        for (Integer subtaskId : subtaskIds) {
            prioritizedTasks.remove(subtasks.get(subtaskId));
            subtasks.remove(subtaskId);
            historyManager.remove(subtaskId);
        }
        epics.remove(epicId);
        historyManager.remove(epicId);
    }

    @Override
    public void removeSubtaskById(int subtaskIdForRemove) {
        prioritizedTasks.remove(subtasks.get(subtaskIdForRemove));
        int epicId = subtasks.get(subtaskIdForRemove).getEpicId();
        List<Integer> subtaskIds = epics.get(epicId).getSubtaskIds();
        subtaskIds.remove((Integer) subtaskIdForRemove);
        subtasks.remove(subtaskIdForRemove);
        checkEpicStatus(epicId);
        setEpicDateTime(epicId);
        historyManager.remove(subtaskIdForRemove);
    }

    @Override
    public void updateTask(Task updateTask) {
        int id = updateTask.getId();
        validate(updateTask);
        prioritizedTasks.remove(tasks.get(id));
        tasks.put(id, updateTask);
        prioritizedTasks.add(updateTask);
    }

    @Override
    public void updateEpic(Epic updateEpic) {
        epics.put(updateEpic.getId(), updateEpic);
    }

    @Override
    public void updateSubtask(Subtask updateSubtask) {
        int id = updateSubtask.getId();
        validate(updateSubtask);
        prioritizedTasks.remove(subtasks.get(id));
        subtasks.put(id, updateSubtask);
        int epicId = subtasks.get(id).getEpicId();
        checkEpicStatus(epicId);
        setEpicDateTime(epicId);
        prioritizedTasks.add(updateSubtask);
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

    @Override
    public void setEpicDateTime(int epicId) {
        List<Integer> subtaskIds = epics.get(epicId).getSubtaskIds();//берем список подзадач эпика
        if (subtaskIds.isEmpty()) { // если список пустой устанавливаем нулевые значения для эпика
            epics.get(epicId).setDuration(0L);
            epics.get(epicId).setStartTime(null);
            epics.get(epicId).setEndTime(null);
            return;
        }
        LocalDateTime epicStartTime = null;
        LocalDateTime epicEndTime = null;
        long epicDuration = 0L;
        for (Integer subtaskId : subtaskIds) {
            Subtask subtask = subtasks.get(subtaskId);
            LocalDateTime subtaskStartTime = subtask.getStartTime();
            LocalDateTime subtaskEndTime = subtask.getEndTime();
            if (subtaskStartTime != null) {
                if (epicStartTime == null || subtaskStartTime.isBefore(epicStartTime)) {
                    epicStartTime = subtaskStartTime;
                }
            }
            if (subtaskEndTime != null) {
                if (epicEndTime == null || subtaskEndTime.isAfter(epicEndTime)) {
                    epicEndTime = subtaskEndTime;
                }
            }
            epicDuration += subtasks.get(subtaskId).getDuration();
        }
        epics.get(epicId).setStartTime(epicStartTime);
        epics.get(epicId).setEndTime(epicEndTime);
        epics.get(epicId).setDuration(epicDuration);
    }

    @Override
    public List<Task> getPrioritizedTasks() {
        return new ArrayList<>(prioritizedTasks);
    }

    @Override
    public void validate(Task newTask) {
        List<Task> prioritizedTasks = getPrioritizedTasks();
        for (Task existTask : prioritizedTasks) {
            if (newTask.getStartTime() == null || existTask.getStartTime() == null) {
                return;
            }
            if (newTask.getId() == existTask.getId()) {
                continue;
            }
            if ((!newTask.getEndTime().isAfter(existTask.getStartTime())) ||
                    (!newTask.getStartTime().isBefore(existTask.getEndTime()))) {
                continue;
            }
            throw new CollisionTaskException("Время выполнения задачи пересекается со временем уже существующей " +
                    "задачи. Выберите другую дату.");
        }
    }
}