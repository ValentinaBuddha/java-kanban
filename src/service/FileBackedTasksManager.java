package service;

import exceptions.ManagerSaveException;
import model.*;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class FileBackedTasksManager extends InMemoryTaskManager {

    public static void main(String[] args) {
        TaskManager fileBackedTasksManager = Managers.getDefaultFile();

        Task task1 = new Task("Задача", "description1");
        fileBackedTasksManager.addTask(task1);
        Epic epic2 = new Epic("Эпик", "description2");
        fileBackedTasksManager.addEpic(epic2);
        Subtask subtask3 = new Subtask("Подзадача", "description3", 2);
        fileBackedTasksManager.addSubtask(subtask3);

        fileBackedTasksManager.getTaskById(task1.getId());
        fileBackedTasksManager.getEpicById(epic2.getId());
        fileBackedTasksManager.getSubtaskById(subtask3.getId());

//получился файл со следующим содержимым
//id,type,name,status,description,epic
//1,TASK,Задача,NEW,description1
//2,EPIC,Эпик,NEW,description2
//3,SUBTASK,Подзадача,NEW,description3,3
//
//1,2,3

        FileBackedTasksManager fileManager = FileBackedTasksManager.loadFromFile(
                new File("./resources/kanban.csv"));
        for (Map.Entry<Integer, Task> entry : fileManager.tasks.entrySet()) {
            System.out.println(entry.getKey() + " : " + entry.getValue());
        }
        for (Map.Entry<Integer, Epic> entry : fileManager.epics.entrySet()) {
            System.out.println(entry.getKey() + " : " + entry.getValue());
        }
        for (Map.Entry<Integer, Subtask> entry : fileManager.subtasks.entrySet()) {
            System.out.println(entry.getKey() + " : " + entry.getValue());
        }
        System.out.println(fileManager.getHistory());
    }

    private final File FILE;
    private static final String FIRST_LINE = "id,type,name,status,description,epic";

    public FileBackedTasksManager(File FILE) {
        this.FILE = FILE;
    }

    //метод который будет восстанавливать данные менеджера из файла при запуске программы+
    private static FileBackedTasksManager loadFromFile(File file) {
        FileBackedTasksManager fileManager = new FileBackedTasksManager(file);
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

    //для восстановления менеджера истории из CSV.+
    private static List<Integer> historyFromString(String value) {
        List<Integer> idsHistory = new ArrayList<>();
        String[] line = value.split(",");
        for (String id : line) {
            idsHistory.add(Integer.valueOf(id));
        }
        return idsHistory;
    }

    //метод создания задачи из строки+
    private static Task fromString(String[] line) {
        int id = Integer.parseInt(line[0]);
        TaskType taskType = TaskType.valueOf(line[1]);
        String title = line[2];
        TaskStatus status = TaskStatus.valueOf(line[3]);
        String description = line[4];
        switch (taskType) {
            case TASK:
                return new Task(title, description, id, status);
            case EPIC:
                return new Epic(title, description, id, status);
            case SUBTASK:
                int epicId = Integer.parseInt(line[5]);
                return new Subtask(title, description, id, status, epicId);
        }
        return null;
    }

    //после каждой операции сохраняет все задачи и историю в файл+
    private void save() {
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

    //метод записи всех текущих задач в файл+
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

    //создает строку для таски и эпика+
    private String toString(Task task) {
        return task.getId() + "," + task.getTaskType() + "," + task.getTitle() + "," + task.getStatus() + "," +
                task.getDescription();
    }

    //создает строку для сабтаски+
    private String toString(Subtask subtask) {
        return subtask.getId() + "," + subtask.getTaskType() + "," + subtask.getTitle() + "," + subtask.getStatus() +
                "," + subtask.getDescription() + "," + subtask.getEpicId();
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
}

