package Tusktracker;

import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {
        Manager manager = new Manager();

        //создаем задачи
        Task task1 = new Task("Задача", "123");
        Task task2 = new Task("Задача", "123");
        manager.createNewTask(task1);
        manager.createNewTask(task2);

        //создаем эпики
        Epic epic3 = new Epic("Эпик", "123");
        Epic epic4 = new Epic("Эпик", "123");
        manager.createNewEpic(epic3);
        manager.createNewEpic(epic4);

        //создаем подзадачи
        Subtask subtask5 = new Subtask("Подзадача", "123", 3);
        Subtask subtask6 = new Subtask("Подзадача", "123", 3);
        Subtask subtask7 = new Subtask("Подзадача", "123", 4);
        manager.createNewSubtask(subtask5);
        manager.createNewSubtask(subtask6);
        manager.createNewSubtask(subtask7);

        //добавляем идентификаторы подзадач в списки эпиков
        ArrayList<Integer> subtaskIdsForEpic3 = epic3.getSubtaskIds();
        subtaskIdsForEpic3.add(subtask5.getId());
        subtaskIdsForEpic3.add(subtask6.getId());
        ArrayList<Integer> subtaskIdsForEpic4 = epic4.getSubtaskIds();
        subtaskIdsForEpic4.add(subtask7.getId());

        //смотрим списки всех задач всех типов
        manager.getListOfTasks();
        manager.getListOfEpics();
        manager.getListOfSubtasks();

        //смотрим задачу/эпик/подзадачу по идентификатору
        manager.getTaskById(task1.getId());
        manager.getEpicById(epic4.getId());
        manager.getSubtaskById(subtask7.getId());

        //смотрим по идентификатору эпика список его подзадач
        System.out.println(manager.getListOfSubtasksByOneEpic(epic3.getId()));

        //обновляем задачу(название и статус)
        Task updateTask3 = new Task("Task", "123", task1.getId(), "IN_PROGRESS");
        manager.updateTheTask(updateTask3, task1.getId());
        //обновляем эпик(название)
        Epic updateEpic3 = new Epic("Epic", "123", epic3.getId(), epic3.getStatus(), epic3.getSubtaskIds());
        manager.updateTheEpic(updateEpic3, epic3.getId());
        //обновляем подзадачу(статус)
        Subtask updateSubtask5 = new Subtask("Подзадача",
                "123", subtask5.getId(),"IN_PROGRESS", 3);
        manager.updateTheSubtask(updateSubtask5, subtask5.getId());

        //удаление задачи/эпика/подзадачи по идентификатору
        manager.removeTaskById(task1.getId());
        manager.removeEpicById(epic4.getId());
        manager.removeTSubtaskById(subtask6.getId());

        //удаляем все задачи
        manager.removeAllTasks();
        manager.removeAllSubtasks();
        manager.removeAllEpics();
    }
}