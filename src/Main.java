import model.Epic;
import model.Subtask;
import model.Task;
import service.Managers;
import service.TaskManager;

public class Main {

//    public static void main(String[] args) {
//        TaskManager inMemoryTaskManager = Managers.getDefault();
//
//        //создаем задачи
//        Task task1 = new Task("Задача", "description1");
//        Task task2 = new Task("Задача", "description2");
//        inMemoryTaskManager.addTask(task1);
//        inMemoryTaskManager.addTask(task2);
//
//        //создаем эпики
//        Epic epic3 = new Epic("Эпик", "description3");
//        Epic epic4 = new Epic("Эпик", "description4");
//        inMemoryTaskManager.addEpic(epic3);
//        inMemoryTaskManager.addEpic(epic4);
//
//        //создаем подзадачи
//        Subtask subtask5 = new Subtask("Подзадача", "description5", 3);
//        Subtask subtask6 = new Subtask("Подзадача", "description6", 3);
//        Subtask subtask7 = new Subtask("Подзадача", "description7", 3);
//        inMemoryTaskManager.addSubtask(subtask5);
//        inMemoryTaskManager.addSubtask(subtask6);
//        inMemoryTaskManager.addSubtask(subtask7);

//        //Получение списка всех подзадач определённого эпика
//        inMemoryTaskManager.getListOfSubtasksByOneEpic(epic3.getId());
//
//        //обновляем задачу(название и статус)
//        Task updateTask3 = new Task("Task", "description3", task1.getId(), TaskStatus.IN_PROGRESS);
//        inMemoryTaskManager.updateTheTask(updateTask3);
//
//        //обновляем эпик(название)
//        Epic updateEpic3 = new Epic("Epic", "description3", epic3.getId(), epic3.getStatus(), epic3.getSubtaskIds());
//        inMemoryTaskManager.updateTheEpic(updateEpic3);
//
//        //обновляем подзадачу(статус)
//        Subtask updateSubtask5 = new Subtask("Подзадача",
//                "description5", subtask5.getId(), TaskStatus.IN_PROGRESS, 3);
//        inMemoryTaskManager.updateTheSubtask(updateSubtask5);
//        Subtask updateSubtask7 = new Subtask("Подзадача",
//                "description7", subtask7.getId(), TaskStatus.DONE, 4);
//        inMemoryTaskManager.updateTheSubtask(updateSubtask7);

//        //получаем задачи для проверки истории
//        inMemoryTaskManager.getTaskById(task2.getId());
//        inMemoryTaskManager.getTaskById(task1.getId());
//        inMemoryTaskManager.getEpicById(epic4.getId());
//        inMemoryTaskManager.getEpicById(epic3.getId());
//        inMemoryTaskManager.getSubtaskById(subtask7.getId());
//        inMemoryTaskManager.getEpicById(epic4.getId());
//        inMemoryTaskManager.getEpicById(epic3.getId());
//        inMemoryTaskManager.getSubtaskById(subtask7.getId());
//        inMemoryTaskManager.getTaskById(task1.getId());
//        inMemoryTaskManager.getTaskById(task2.getId());
//        inMemoryTaskManager.getSubtaskById(subtask5.getId());
//        inMemoryTaskManager.getSubtaskById(subtask6.getId());
//        inMemoryTaskManager.getSubtaskById(subtask6.getId());
//        inMemoryTaskManager.getSubtaskById(subtask5.getId());
//        System.out.println(inMemoryTaskManager.getHistory());//4371265
//
//        //удаляем задачу для проверки истории
//        inMemoryTaskManager.removeTaskById(task1.getId());
//        System.out.println(inMemoryTaskManager.getHistory());//437265
//
//        //удаляем эпик для проверки истории
//        inMemoryTaskManager.removeEpicById(epic3.getId());
//        System.out.println(inMemoryTaskManager.getHistory());//42
//    }
}