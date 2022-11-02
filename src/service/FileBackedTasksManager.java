package service;

import exceptions.ManagerSaveException;
import model.*;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FileBackedTasksManager extends InMemoryTaskManager {
//    private final Path FILE;
//
//    public FileBackedTasksManager(Path file) {
//        this.FILE = file.getFileName();
//    }
//
//    //метод который будет восстанавливать данные менеджера из файла при запуске программы
//    static FileBackedTasksManager loadFromFile(Path file) {
//        FileBackedTasksManager fileManager = new FileBackedTasksManager(file);
//        Map<Integer, Task> tasks = new HashMap<>();
//        try (BufferedReader reader = new BufferedReader(new FileReader(String.valueOf(file), StandardCharsets.UTF_8))) {
//            //читаем файл и складываем в список построчно
//            List<String> linesFromFile = new ArrayList<>();
//            while (reader.ready()) {
//                String line = reader.readLine();
//                linesFromFile.add(line);
//            }
//            //строки с индексами от 1 до (длина-2) это задачи
//            for (int i = 1; i < (linesFromFile.size() - 2); i++) { //обходим каждую задачу
//                String[] line = linesFromFile.get(i).split(",");
//                Task task = fromString(line);//получаем задачу
//                tasks.put(task.getId(), task);//складываем в мапу
//            }
//            //последняя строка списка - история просмотров,отправляем ее в метод и получаем список id истории
//            historyFromString(linesFromFile.get(linesFromFile.size()-1));
//            HistoryManager historyManager = new InMemoryHistoryManager();
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return fileManager;
//    }
//
//    //для восстановления менеджера истории из CSV.
//    static List<Integer> historyFromString(String value) {
//        List<Integer> idsHistory = new ArrayList<>();
//        String[] line = value.split(",");
//        for (String id : line) {
//            idsHistory.add(Integer.valueOf(id));
//        }
//        return idsHistory;
//    }
//
//    //для сохранения менеджера истории из CSV.
//    static String historyToString(HistoryManager manager) {
//
//    }
//
//    //метод создания задачи из строки
//    private static Task fromString(String[] line) {
//        int id = Integer.parseInt(line[0]);
//        TaskType taskType = TaskType.valueOf(line[1]);
//        String title = line[2];
//        TaskStatus status = TaskStatus.valueOf(line[3]);
//        String description = line[4];
//        switch (taskType) {
//            case TASK:
//                return new Task(title, description, id, status);
//            case EPIC:
//                return new Epic(title, description, id, status);
//            case SUBTASK:
//                int epicId = Integer.parseInt(line[5]);
//                return new Subtask(title, description, id, status, epicId);
//        }
//        return null;
//    }
//
//    //после каждой операции сохраняет все задачи и историю в файл
//    private void save() {
//        try (BufferedWriter writer = new BufferedWriter(new FileWriter(String.valueOf(FILE), StandardCharsets.UTF_8))) {
//            writer.write("id,type,name,status,description,epic");
//            writer.newLine();
//            addTasksToFile(writer);
//            writer.newLine();
//            List<String> ids = new ArrayList<>(); //сохраняем историю просмотров
//            for (Task task : getHistory()) {
//                ids.add(String.valueOf(task.getId()));
//            }
//            writer.write(String.join(",", ids));
//        } catch (IOException e) {
//            throw new ManagerSaveException();
//        }
//    }
//
//    //метод записи всех текущих задач в файл
//    private void addTasksToFile(BufferedWriter writer) throws IOException {
//        for (Task task : getListOfTasks()) {
//            writer.write(toString(task));
//            writer.newLine();
//        }
//        for (Epic epic : getListOfEpics()) {
//            writer.write(toString(epic));
//            writer.newLine();
//        }
//        for (Subtask subtask : getListOfSubtasks()) {
//            writer.write(toString(subtask));
//            writer.newLine();
//        }
//    }
//
//    //создает строку для таски и эпика
//    private String toString(Task task) {
//        return task.getId() + "," + task.getTaskType() + "," + task.getStatus() + "," + task.getDescription();
//    }
//
//    //создает строку для сабтаски
//    private String toString(Subtask subtask) {
//        return subtask.getId() + "," + subtask.getTaskType() + "," + subtask.getStatus() + "," +
//                subtask.getDescription() + "," + subtask.getEpicId();
//    }
//
//    @Override
//    public void addTask(Task newTask) {
//        super.addTask(newTask);
//        save();
//    }
//
//    @Override
//    public void addSubtask(Subtask newSubtask) {
//        super.addSubtask(newSubtask);
//        save();
//    }
//
//    @Override
//    public void addEpic(Epic newEpic) {
//        super.addEpic(newEpic);
//        save();
//    }
//
//    @Override
//    public void removeAllTasks() {
//        super.removeAllTasks();
//        save();
//    }
//
//    @Override
//    public void removeAllSubtasks() {
//        super.removeAllSubtasks();
//        save();
//    }
//
//    @Override
//    public void removeAllEpics() {
//        super.removeAllEpics();
//        save();
//    }
//
//    @Override
//    public List<Task> getListOfTasks() {
//        return super.getListOfTasks();
//    }
//
//    @Override
//    public List<Epic> getListOfEpics() {
//        return super.getListOfEpics();
//    }
//
//    @Override
//    public List<Subtask> getListOfSubtasks() {
//        return super.getListOfSubtasks();
//    }
//
//    @Override
//    public Task getTaskById(int id) {
//        Task task = super.getTaskById(id);
//        save();
//        return task;
//    }
//
//    @Override
//    public Epic getEpicById(int id) {
//        Epic epic = super.getEpicById(id);
//        save();
//        return epic;
//    }
//
//    @Override
//    public Subtask getSubtaskById(int id) {
//        Subtask subtask = super.getSubtaskById(id);
//        save();
//        return subtask;
//    }
//
//    @Override
//    public List<Subtask> getListOfSubtasksByOneEpic(int id) {
//        return super.getListOfSubtasksByOneEpic(id);
//    }
//
//    @Override
//    public void removeTaskById(int id) {
//        super.removeTaskById(id);
//        save();
//    }
//
//    @Override
//    public void removeEpicById(int epicId) {
//        super.removeEpicById(epicId);
//        save();
//    }
//
//    @Override
//    public void removeSubtaskById(int subtaskIdForRemove) {
//        super.removeSubtaskById(subtaskIdForRemove);
//        save();
//    }
//
//    @Override
//    public void updateTask(Task updateTask) {
//        super.updateTask(updateTask);
//        save();
//    }
//
//    @Override
//    public void updateEpic(Epic updateEpic) {
//        super.updateEpic(updateEpic);
//        save();
//    }
//
//    @Override
//    public void updateSubtask(Subtask updateSubtask) {
//        super.updateSubtask(updateSubtask);
//        save();
//    }
//
//    @Override
//    public void checkEpicStatus(int epicId) {
//        super.checkEpicStatus(epicId);
//        save();
//    }
//
//    @Override
//    public List<Task> getHistory() {
//        return super.getHistory();
//    }
//
//    public static void main(String[] args) {
//        TaskManager fileBackedTasksManager = Managers.getDefaultFile();
//
//        //создаем задачи
//        Task task1 = new Task("Задача", "description1");
//        Task task2 = new Task("Задача", "description2");
//        fileBackedTasksManager.addTask(task1);
//        fileBackedTasksManager.addTask(task2);
//
//        //создаем эпики
//        Epic epic3 = new Epic("Эпик", "description3");
//        Epic epic4 = new Epic("Эпик", "description4");
//        fileBackedTasksManager.addEpic(epic3);
//        fileBackedTasksManager.addEpic(epic4);
//
//        //создаем подзадачи
//        Subtask subtask5 = new Subtask("Подзадача", "description5", 3);
//        Subtask subtask6 = new Subtask("Подзадача", "description6", 3);
//        Subtask subtask7 = new Subtask("Подзадача", "description7", 3);
//        fileBackedTasksManager.addSubtask(subtask5);
//        fileBackedTasksManager.addSubtask(subtask6);
//        fileBackedTasksManager.addSubtask(subtask7);
//
////        //получаем задачи для проверки истории
////        fileBackedTasksManager.getTaskById(task2.getId());
////        fileBackedTasksManager.getTaskById(task1.getId());
////        fileBackedTasksManager.getEpicById(epic4.getId());
////        fileBackedTasksManager.getEpicById(epic3.getId());
////        fileBackedTasksManager.getSubtaskById(subtask7.getId());
////        fileBackedTasksManager.getEpicById(epic4.getId());
////        fileBackedTasksManager.getEpicById(epic3.getId());
////        fileBackedTasksManager.getSubtaskById(subtask7.getId());
////        fileBackedTasksManager.getTaskById(task1.getId());
////        fileBackedTasksManager.getTaskById(task2.getId());
////        fileBackedTasksManager.getSubtaskById(subtask5.getId());
////        fileBackedTasksManager.getSubtaskById(subtask6.getId());
////        fileBackedTasksManager.getSubtaskById(subtask6.getId());
////        fileBackedTasksManager.getSubtaskById(subtask5.getId());
////        System.out.println(fileBackedTasksManager.getHistory());//4371265
////
////        //удаляем задачу для проверки истории
////        fileBackedTasksManager.removeTaskById(task1.getId());
////        System.out.println(fileBackedTasksManager.getHistory());//437265
////
////        //удаляем эпик для проверки истории
////        fileBackedTasksManager.removeEpicById(epic3.getId());
////        System.out.println(fileBackedTasksManager.getHistory());//42
//
//        //создаем подзадачу
//        Subtask subtask8 = new Subtask("Подзадача", "description8", 4);
//        fileBackedTasksManager.addSubtask(subtask8);
//
//
//    }
}
