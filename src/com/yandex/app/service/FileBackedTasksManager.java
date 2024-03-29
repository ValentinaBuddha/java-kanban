package com.yandex.app.service;

import com.yandex.app.model.*;
import com.yandex.app.exceptions.ManagerSaveException;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class FileBackedTasksManager extends InMemoryTaskManager {

    private final File FILE = new File("./resources/kanban.csv");
    private static final String FIRST_LINE = "id,type,name,status,description,startTime,endTime,duration,epic";

    public FileBackedTasksManager() {
    }

    //метод который будет восстанавливать данные менеджера из файла при запуске программы
    protected static FileBackedTasksManager loadFromFile(File file) {
        FileBackedTasksManager fileManager = new FileBackedTasksManager();
        Map<Integer, Task> fileHistory = new HashMap<>();
        List<Integer> idsHistory = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(file, StandardCharsets.UTF_8))) {
            //читаем файл и складываем в список построчно
            List<String> taskLines = reader.lines().collect(Collectors.toList());
            for (int i = 1; i < taskLines.size(); i++) {
                //если строка пустая, и следующая строка не пустая, формируем список с идентификаторами истории
                if (taskLines.get(i).isEmpty() && !taskLines.get(i + 1).isEmpty()) {
                    idsHistory = historyFromString(taskLines.get(i + 1));
                    break;
                }
                String[] line = taskLines.get(i).split(",");
                Task task = fromString(line);//получаем задачу
                fileHistory.put(task.getId(), task); //складываем в мапу для истории
                switch (task.getTaskType()) { //складываем в соответстующую мапу задач
                    case TASK:
                        fileManager.tasks.put(task.getId(), task);
                        fileManager.prioritizedTasks.add(task);
                        break;
                    case EPIC:
                        fileManager.epics.put(task.getId(), (Epic) task);
                        break;
                    case SUBTASK:
                        fileManager.subtasks.put(task.getId(), (Subtask) task);
                        //добавляем подзадачу в список идентификаторов подзадач в эпике
                        int epicId = ((Subtask) task).getEpicId();
                        List<Integer> subtaskIds = fileManager.epics.get(epicId).getSubtaskIds();
                        subtaskIds.add(task.getId());
                        fileManager.checkEpicStatus(epicId);
                        fileManager.prioritizedTasks.add(task);
                        break;
                }
                if (task.getId() > fileManager.generatorId) {
                    fileManager.generatorId = task.getId();
                }
            }
            //после того, как задачи прочитаны, заполняем ими историю
            for (Integer id : idsHistory) {
                fileManager.historyManager.add(fileHistory.get(id));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return fileManager;
    }

    //для восстановления менеджера истории из CSV
    private static List<Integer> historyFromString(String value) {
        List<Integer> idsHistory = new ArrayList<>();
        String[] line = value.split(",");
        for (String id : line) {
            idsHistory.add(Integer.valueOf(id));
        }
        return idsHistory;
    }

    //метод создания задачи из строки
    private static Task fromString(String[] line) {
        int id = Integer.parseInt(line[0]);
        TaskType taskType = TaskType.valueOf(line[1]);
        String title = line[2];
        TaskStatus status = TaskStatus.valueOf(line[3]);
        String description = line[4];
        LocalDateTime startTime;
        LocalDateTime endTime;
        if (!line[5].equals("null")) {
            startTime = LocalDateTime.parse(line[5], LocalDateAdapter.formatter);
            endTime = LocalDateTime.parse(line[6], LocalDateAdapter.formatter);
        } else {
            startTime = null;
            endTime = null;
        }
        long duration = Long.parseLong(line[7]);
        switch (taskType) {
            case TASK:
                return new Task(title, description, id, status, startTime, duration);
            case EPIC:
                return new Epic(title, description, id, status, startTime, duration, endTime);
            case SUBTASK:
                int epicId = Integer.parseInt(line[8]);
                return new Subtask(title, description, id, status, epicId, startTime, duration);
        }
        return null;
    }

    //после каждой операции сохраняет все задачи и историю в файл
    protected void save() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE, StandardCharsets.UTF_8))) {
            writer.write(FIRST_LINE);
            writer.newLine();
            addTasksToFile(writer);
            writer.newLine();
            List<String> ids = new ArrayList<>(); //сохраняем историю просмотров
            for (Task task : getHistory()) {
                ids.add(String.valueOf(task.getId()));
            }
            writer.write(String.join(",", ids));
        } catch (IOException e) {
            throw new ManagerSaveException();
        }
    }

    //метод записи всех текущих задач в файл
    private void addTasksToFile(BufferedWriter writer) throws IOException {
        for (Task task : getListOfTasks()) {
            writer.write(toString(task));
            writer.newLine();
        }
        for (Epic epic : getListOfEpics()) {
            writer.write(toString(epic));
            writer.newLine();
        }
        for (Subtask subtask : getListOfSubtasks()) {
            writer.write(toString(subtask));
            writer.newLine();
        }
    }

    //создает строку для таски
    private String toString(Task task) {
        return task.getId() + "," + task.getTaskType() + "," + task.getTitle() + "," + task.getStatus() + "," +
                task.getDescription() + "," + task.getStartTimeString() + "," + task.getEndTimeString() + "," +
                task.getDuration();
    }

    //создает строку для эпика
    private String toString(Epic epic) {
        return epic.getId() + "," + epic.getTaskType() + "," + epic.getTitle() + "," + epic.getStatus() + "," +
                epic.getDescription() + "," + epic.getStartTimeString() + "," + epic.getEndTimeString() + "," +
                epic.getDuration();
    }

    //создает строку для сабтаски
    private String toString(Subtask subtask) {
        return subtask.getId() + "," + subtask.getTaskType() + "," + subtask.getTitle() + "," + subtask.getStatus() +
                "," + subtask.getDescription() + "," + subtask.getStartTimeString() + "," + subtask.getEndTimeString() +
                "," + subtask.getDuration() + "," + subtask.getEpicId();
    }

    @Override
    public void addTask(Task newTask) {
        super.addTask(newTask);
        save();
    }

    @Override
    public void addSubtask(Subtask newSubtask) {
        super.addSubtask(newSubtask);
        save();
    }

    @Override
    public void addEpic(Epic newEpic) {
        super.addEpic(newEpic);
        save();
    }

    @Override
    public void removeAllTasks() {
        super.removeAllTasks();
        save();
    }

    @Override
    public void removeAllSubtasks() {
        super.removeAllSubtasks();
        save();
    }

    @Override
    public void removeAllEpics() {
        super.removeAllEpics();
        save();
    }

    @Override
    public Task getTaskById(int id) {
        Task task = super.getTaskById(id);
        save();
        return task;
    }

    @Override
    public Epic getEpicById(int id) {
        Epic epic = super.getEpicById(id);
        save();
        return epic;
    }

    @Override
    public Subtask getSubtaskById(int id) {
        Subtask subtask = super.getSubtaskById(id);
        save();
        return subtask;
    }

    @Override
    public void removeTaskById(int id) {
        super.removeTaskById(id);
        save();
    }

    @Override
    public void removeEpicById(int epicId) {
        super.removeEpicById(epicId);
        save();
    }

    @Override
    public void removeSubtaskById(int subtaskIdForRemove) {
        super.removeSubtaskById(subtaskIdForRemove);
        save();
    }

    @Override
    public void updateTask(Task updateTask) {
        super.updateTask(updateTask);
        save();
    }

    @Override
    public void updateEpic(Epic updateEpic) {
        super.updateEpic(updateEpic);
        save();
    }

    @Override
    public void updateSubtask(Subtask updateSubtask) {
        super.updateSubtask(updateSubtask);
        save();
    }

    @Override
    public void checkEpicStatus(int epicId) {
        super.checkEpicStatus(epicId);
        save();
    }

    @Override
    public void setEpicDateTime(int epicId) {
        super.setEpicDateTime(epicId);
        save();
    }
}




