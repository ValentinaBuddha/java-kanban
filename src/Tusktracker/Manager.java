package Tusktracker;

import java.util.ArrayList;
import java.util.HashMap;

public class Manager {
    private int counter = 0;
    private HashMap<Integer, Task> tasks = new HashMap<>();
    private HashMap<Integer, Subtask> subtasks = new HashMap<>();
    private HashMap<Integer, Epic> epics = new HashMap<>();

    protected void createNewTask(Task newTask) {
        int taskId = ++counter;
        newTask.setId(taskId);
        tasks.put(taskId, newTask);
    }

    protected void createNewSubtask(Subtask newSubtask) {
        int subtaskId = ++counter;
        newSubtask.setId(subtaskId);
        subtasks.put(subtaskId, newSubtask);
    }

    protected void createNewEpic(Epic newEpic) {
        int epicId = ++counter;
        newEpic.setId(epicId);
        epics.put(epicId, newEpic);
    }

    protected void removeAllTasks() {
        tasks.clear();
    }

    protected void removeAllSubtasks() {
        subtasks.clear();
    }

    protected void removeAllEpics() {
        epics.clear();
    }

    protected void getListOfTasks() {
        for (Task task : tasks.values()) {
            System.out.println(task);
        }
    }

    protected void getListOfEpics() {
        for (Epic epic : epics.values()) {
            System.out.println(epic);
        }
    }

    protected void getListOfSubtasks() {
        for (Subtask subtask : subtasks.values()) {
            System.out.println(subtask);
        }
    }

    protected void getTaskById(int id) {
        if (tasks.containsKey(id)) {
            System.out.println(tasks.get(id));
        }
    }

    protected void getEpicById(int id) {
        if (epics.containsKey(id)) {
            System.out.println(epics.get(id));
        }
    }

    protected void getSubtaskById(int id) {
        if (subtasks.containsKey(id)) {
            System.out.println(subtasks.get(id));
        }
    }

    protected  HashMap<Integer, Subtask> getListOfSubtasksByOneEpic(int id) {
        ArrayList<Integer> subtaskIds = epics.get(id).getSubtaskIds();
        HashMap<Integer, Subtask> subtasksByOneEpic = new HashMap<>();
        for (int subtaskId : subtaskIds) {
            subtasksByOneEpic.put(subtaskId, subtasks.get(subtaskId));
        }
            return subtasksByOneEpic;
    }

    protected void removeTaskById(int taskId) {
        tasks.remove(taskId);
    }

    protected void removeEpicById(int epicId) {
        ArrayList<Integer> subtaskIds = epics.get(epicId).getSubtaskIds();
        for (Integer subtaskId : subtaskIds) {
            subtasks.remove(subtaskId);
        }
        epics.remove(epicId);
    }

    protected void removeTSubtaskById(int subtaskIdForRemove) {
        int epicId = subtasks.get(subtaskIdForRemove).getEpicId();
        ArrayList<Integer> subtaskIds = epics.get(epicId).getSubtaskIds();
        subtaskIds.remove((Integer) subtaskIdForRemove);
        subtasks.remove(subtaskIdForRemove);
        //проверка(и обновление) статуса эпика
        for (Integer subtaskId : subtaskIds) {
            if (subtasks.get(subtaskId).getStatus().equals("NEW") || subtaskIds.isEmpty()) {
                Epic epicWithUpdateStatus = new Epic(epics.get(epicId).getTitle(),
                        epics.get(epicId).getSpecification(),
                        epics.get(epicId).getId(),
                        "NEW",
                        epics.get(epicId).getSubtaskIds());
                epics.put(epicId, epicWithUpdateStatus);
            } else if (subtasks.get(subtaskId).getStatus().equals("DONE")) {
                Epic epicWithUpdateStatus = new Epic(epics.get(epicId).getTitle(),
                        epics.get(epicId).getSpecification(),
                        epics.get(epicId).getId(),
                        "DONE",
                        epics.get(epicId).getSubtaskIds());
                epics.put(epicId, epicWithUpdateStatus);
            } else {
                Epic epicWithUpdateStatus = new Epic(epics.get(epicId).getTitle(),
                        epics.get(epicId).getSpecification(),
                        epics.get(epicId).getId(),
                        "IN_PROGRESS",
                        epics.get(epicId).getSubtaskIds());
                epics.put(epicId, epicWithUpdateStatus);
            }
        }
    }

    protected void updateTheTask(Task updateTask, Integer idOfUpdateTask) {
        tasks.put(idOfUpdateTask, updateTask);
    }

    protected void updateTheEpic(Epic updateEpic, Integer idOfUpdateTEpic) {
        epics.put(idOfUpdateTEpic, updateEpic);
    }

    protected void updateTheSubtask(Subtask updateSubtask, Integer idOfUpdateSubtask) {
        subtasks.put(idOfUpdateSubtask, updateSubtask);
        //проверка(и обновление) статуса эпика
        int epicId = subtasks.get(idOfUpdateSubtask).getEpicId();
        ArrayList<Integer> subtaskIds = epics.get(epicId).getSubtaskIds();
        for (Integer subtaskId : subtaskIds) {
            if (subtasks.get(subtaskId).getStatus().equals("NEW") || subtaskIds.isEmpty()) {
                Epic epicWithUpdateStatus = new Epic(epics.get(epicId).getTitle(),
                        epics.get(epicId).getSpecification(),
                        epics.get(epicId).getId(),
                        "NEW",
                        epics.get(epicId).getSubtaskIds());
                epics.put(epicId, epicWithUpdateStatus);
            } else if (subtasks.get(subtaskId).getStatus().equals("DONE")) {
                Epic epicWithUpdateStatus = new Epic(epics.get(epicId).getTitle(),
                        epics.get(epicId).getSpecification(),
                        epics.get(epicId).getId(),
                        "DONE",
                        epics.get(epicId).getSubtaskIds());
                epics.put(epicId, epicWithUpdateStatus);
            } else {
                Epic epicWithUpdateStatus = new Epic(epics.get(epicId).getTitle(),
                        epics.get(epicId).getSpecification(),
                        epics.get(epicId).getId(),
                        "IN_PROGRESS",
                        epics.get(epicId).getSubtaskIds());
                epics.put(epicId, epicWithUpdateStatus);
            }
        }
    }
}