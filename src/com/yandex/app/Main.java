//package com.yandex.app;
//
//import com.yandex.app.model.Epic;
//import com.yandex.app.model.Subtask;
//import com.yandex.app.model.Task;
//import com.yandex.app.model.TaskStatus;
//import com.yandex.app.service.Managers;
//import com.yandex.app.service.TaskManager;
//
//import java.time.LocalDateTime;
//
//public class Main {
//
//    public static void main(String[] args) {
//        TaskManager taskManager = Managers.getDefault();
//
//        Task task1 = new Task("Задача", "description1",
//                LocalDateTime.of(2023, 1, 4, 0, 0), 1000);
//        Task task2 = new Task("Задача", "description2",
//                null, 1000);
//        Task task3 = new Task("Задача", "description2",
//                null, 1000);
//        Task task4 = new Task("Задача", "description2",
//                LocalDateTime.of(2023, 1, 1, 0, 0), 1000);
////        Epic epic3 = new Epic("Эпик", "description3");
////        Epic epic4 = new Epic("Эпик", "description4");
//        taskManager.addTask(task1);
//        taskManager.addTask(task2);
//        taskManager.addTask(task3);
//        taskManager.addTask(task4);
////        taskManager.addEpic(epic3);
////        taskManager.addEpic(epic4);
//
//        //для печати списка
//       // taskManager.getPrioritizedTasks().stream().forEach(task -> System.out.println(task.getStartTime() + " " + task.getId()));
//
//        Task taskPriority = taskManager.getPrioritizedTasks().stream()
//                .filter(task -> task.getId() == 1)
//                .findFirst()
//                .orElse(null);
//        System.out.println(taskPriority);
//
//
////        Subtask subtask5 = new Subtask("Подзадача", "description5", 3,
////                LocalDateTime.of(2023, 1, 3, 0, 0), 1000);
////        Subtask subtask6 = new Subtask("Подзадача", "description6", 3,
////                LocalDateTime.of(2023, 1, 4, 0, 0), 1000);
////        Subtask subtask7 = new Subtask("Подзадача", "description7", 4,
////                LocalDateTime.of(2023, 1, 5, 0, 0), 1000);
////        taskManager.addSubtask(subtask5);
////        taskManager.addSubtask(subtask6);
////        taskManager.addSubtask(subtask7);
//
////        System.out.println(taskManager.getListOfTasks());
////        System.out.println(taskManager.getListOfEpics());
////        System.out.println(taskManager.getListOfSubtasks());
//
////        Subtask updateSubtask6 = new Subtask("Подзадача",
////                "description5", subtask6.getId(), TaskStatus.IN_PROGRESS, 3,
////                LocalDateTime.of(2023, 1, 4, 0, 0), 1000);
////        taskManager.updateSubtask(updateSubtask6);
////
////        taskManager.removeSubtaskById(5);
////
////        System.out.println(taskManager.getListOfTasks());
////        System.out.println(taskManager.getListOfEpics());
////        System.out.println(taskManager.getListOfSubtasks());
//
//
//
////        taskManager.getEpicById(3);
////        taskManager.getSubtaskById(6);
////        taskManager.getTaskById(1);
////        taskManager.getSubtaskById(7);
////        taskManager.getTaskById(1);
////
////        System.out.println(taskManager.getHistory());//2431  //3671
//
//    }
//}
//
//
//
//
//
////    public static void main(String[] args) {
////        TaskManager fileBackedTasksManager = Managers.getDefaultFile();
////
////        Task task1 = new Task("Задача", "description1");
////        fileBackedTasksManager.addTask(task1);
////        Epic epic2 = new Epic("Эпик", "description2");
////        fileBackedTasksManager.addEpic(epic2);
////        Subtask subtask3 = new Subtask("Подзадача", "description3", 2);
////        fileBackedTasksManager.addSubtask(subtask3);
////
////        fileBackedTasksManager.getTaskById(task1.getId());
////        fileBackedTasksManager.getEpicById(epic2.getId());
////        fileBackedTasksManager.getSubtaskById(subtask3.getId());
////
//////получился файл со следующим содержимым
//////id,type,name,status,description,epic
//////1,TASK,Задача,NEW,description1
//////2,EPIC,Эпик,NEW,description2
//////3,SUBTASK,Подзадача,NEW,description3,3
//////
//////1,2,3
////
////        FileBackedTasksManager fileManager = FileBackedTasksManager.loadFromFile(
////                new File("./resources/kanban.csv"));
////        for (Map.Entry<Integer, Task> entry : fileManager.tasks.entrySet()) {
////            System.out.println(entry.getKey() + " : " + entry.getValue());
////        }
////        for (Map.Entry<Integer, Epic> entry : fileManager.epics.entrySet()) {
////            System.out.println(entry.getKey() + " : " + entry.getValue());
////        }
////        for (Map.Entry<Integer, Subtask> entry : fileManager.subtasks.entrySet()) {
////            System.out.println(entry.getKey() + " : " + entry.getValue());
////        }
////        System.out.println(fileManager.getHistory());
////    }
//
