package Tusktracker;

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

        manager.getTaskById(task1.getId());
        manager.getEpicById(epic4.getId());
        manager.getSubtaskById(subtask7.getId());

        manager.getListOfSubtasksByOneEpic(epic3.getId());

        //обновляем задачу(название и статус)
        Task updateTask3 = new Task("Task", "123", task1.getId(), "IN_PROGRESS");
        manager.updateTheTask(updateTask3);
        //обновляем эпик(название)
        Epic updateEpic3 = new Epic("Epic", "123", epic3.getId(), epic3.getStatus(), epic3.getSubtaskIds());
        manager.updateTheEpic(updateEpic3);
        //обновляем подзадачу(статус)
        Subtask updateSubtask5 = new Subtask("Подзадача",
                "123", subtask5.getId(),"IN_PROGRESS", 3);
        manager.updateTheSubtask(updateSubtask5);

        manager.getListOfTasks();
        manager.getListOfEpics();
        manager.getListOfSubtasks();

        manager.removeTaskById(task1.getId());
        manager.removeEpicById(epic4.getId());
        manager.removeSubtaskById(subtask6.getId());

        manager.removeAllTasks();
        manager.removeAllSubtasks();
        manager.removeAllEpics();
    }
}