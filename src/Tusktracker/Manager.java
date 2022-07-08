package Tusktracker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Manager {
    HashMap<Integer, Task> tasks = new HashMap<>();
    HashMap<Integer, SubTask> subtasks = new HashMap<>();
    HashMap<Integer, EpicTask> epics = new HashMap<>();
    Scanner scanner = new Scanner(System.in);

    protected void getListOfAllTasks() {
        for (Task task : tasks.values()) {
            System.out.println(task);
        }
        for (SubTask subtask : subtasks.values()) {
            System.out.println(subtask);
        }
        for (EpicTask epic : epics.values()) {
            System.out.println(epic);
        }
    }

    protected void removeAllTasks() {
        tasks.clear();
        subtasks.clear();
        epics.clear();
        System.out.println("Все задачи удалены.");
    }

    protected void getTaskById() {
        System.out.println("Введите id задачи.");
        int id = scanner.nextInt();
        if (tasks.containsKey(id)) {
            System.out.println(tasks.get(id));
        } else if (subtasks.containsKey(id)) {
            System.out.println(subtasks.get(id));
        } else if (epics.containsKey(id)) {
            System.out.println(epics.get(id));
        } else {
            System.out.println("Задачи нет в списках, проверьте id.");
        }
    }

    protected void createNewTask() {
        //!!!!!!!!!!!!если в названии 2 слова то не дает вставить описание
        System.out.println("Введите название задачи.");
        String title = scanner.next();
        System.out.println("Введите описание задачи");
        String specification = scanner.next();
        System.out.println("Какой тип задачи вы хотите создать:" +
                " 1 - Задача, 2 - Эпик, 3 - Подзадача?");
        int typeOfTask = scanner.nextInt();
        switch (typeOfTask) {
            case 1:
                Task task = new Task(title, specification, "NEW");
                tasks.put(task.id, task);
                System.out.println(task.id);//для проверки (какой айди присвоен)
                break;
            case 2:
                ArrayList<Integer> arrayOfSubtaskId = new ArrayList<>();
                while (true) {
                    System.out.println("Введите id подзадачи, относящейся к данному эпику, " +
                            "для выхода нажмите 0.");
                    int id = scanner.nextInt();
                    if (id > 0) {
                        arrayOfSubtaskId.add(id);
                    } else if (id == 0) {
                        break;
                    } else {
                        System.out.println("Введите число больше 0 для ввода id или 0 для выхода.");
                    }
                }
                EpicTask epicTask = new EpicTask(title, specification, "NEW", arrayOfSubtaskId);
                epics.put(epicTask.id, epicTask);
                System.out.println(epicTask.id);//для проверки (какой айди присвоен)
                break;
            case 3:
                System.out.println("Введите id эпика, к которому относится данная подзадача.");
                int epicId = scanner.nextInt();
                SubTask subTask = new SubTask(title, specification, "NEW", epicId);
                subtasks.put(subTask.id, subTask);
                System.out.println(subTask.id);//для проверки (какой айди присвоен)
                break;
            default:
                System.out.println("Введите значение из предложенных выше.");
        }
    }

    protected void updateTheTask() {
        System.out.println("Введите id задачи, которую вы хотите обновить.");
        int id = scanner.nextInt();
        if (tasks.containsKey(id)) {
            System.out.println("Вы хотите обновить следующую задачу:");
            System.out.println(tasks.get(id));
            while (true) {
                System.out.println("Какое поле задачи вы хотите обновить:" +
                        " 1 - Название, 2 - Описание? Нажмите 0 для выхода.");
                int userInput = scanner.nextInt();
                if (userInput == 1) {
                    System.out.println("Введите новое название задачи.");
                    String title = scanner.next();
                    Task updateTask = new Task(title,
                            tasks.get(id).getSpecification(),
                            tasks.get(id).getStatus());
                    tasks.put(id, updateTask);
                } else if (userInput == 2) {
                    System.out.println("Введите новое описание задачи.");
                    String specification = scanner.next();
                    Task updateTask = new Task(tasks.get(id).getTitle(),
                            specification,
                            tasks.get(id).getStatus());
                    tasks.put(id, updateTask);
                } else if (userInput == 0) {
                    break;
                } else {
                    System.out.println("Введите значение из предложенных выше.");
                }
            }
        } else if (subtasks.containsKey(id)) {
            System.out.println("Вы хотите обновить следующую задачу:");
            System.out.println(subtasks.get(id));
            while (true) {
                System.out.println("Какое поле задачи вы хотите обновить:" +
                        " 1 - Название, 2 - Описание, 3 - Id эпика? Нажмите 0 для выхода.");
                int userInput = scanner.nextInt();
                if (userInput == 1) {
                    System.out.println("Введите новое название задачи.");
                    String title = scanner.next();
                    SubTask updateSubtask = new SubTask(title,
                            subtasks.get(id).getSpecification(),
                            subtasks.get(id).getStatus(),
                            subtasks.get(id).getEpicId());
                    subtasks.put(id, updateSubtask);
                } else if (userInput == 2) {
                    System.out.println("Введите новое описание задачи.");
                    String specification = scanner.next();
                    SubTask updateSubtask = new SubTask(subtasks.get(id).getTitle(),
                            specification,
                            subtasks.get(id).getStatus(),
                            subtasks.get(id).getEpicId());
                    subtasks.put(id, updateSubtask);
                } else if (userInput == 3) {
                    System.out.println("Введите новый id эпика.");
                    int epicId = scanner.nextInt();
                    SubTask updateSubtask = new SubTask(subtasks.get(id).getTitle(),
                            subtasks.get(id).getSpecification(),
                            subtasks.get(id).getStatus(),
                            epicId);
                    subtasks.put(id, updateSubtask);
                } else if (userInput == 0) {
                    break;
                } else {
                    System.out.println("Введите значение из предложенных выше.");
                }
            }
        } else if (epics.containsKey(id)) {
            System.out.println("Вы хотите обновить следующую задачу:");
            System.out.println(epics.get(id));
            while (true) {
                System.out.println("Какое поле задачи вы хотите обновить:" +
                        " 1 - Название, 2 - Описание, 3 - Изменить список Id подзадач? Нажмите 0 для выхода.");
                int userInput = scanner.nextInt();
                if (userInput == 1) {
                    System.out.println("Введите новое название задачи.");
                    String title = scanner.next();
                    EpicTask updateEpic = new EpicTask(title,
                            epics.get(id).getSpecification(),
                            epics.get(id).getStatus(),
                            epics.get(id).getArrayOfSubtaskId());
                    epics.put(id, updateEpic);
                } else if (userInput == 2) {
                    System.out.println("Введите новое описание задачи.");
                    String specification = scanner.next();
                    EpicTask updateEpic = new EpicTask(epics.get(id).getTitle(),
                            specification,
                            epics.get(id).getStatus(),
                            epics.get(id).getArrayOfSubtaskId());
                    epics.put(id, updateEpic);
                } else if (userInput == 3) {
                    System.out.println("Вы хотите 1 - удалить или 2 - добавить Id подзадачи? Для выхода нажмите 0.");
                    int command = scanner.nextInt();
                    while (true) {
                        if (command == 1) {
                            System.out.println("Введите Id подзадачи.");
                            int idOfSubtask = scanner.nextInt();
                            ArrayList<Integer> arrayOfSubtaskId = epics.get(id).getArrayOfSubtaskId();
                            arrayOfSubtaskId.remove(idOfSubtask);
                            EpicTask updateEpic = new EpicTask(epics.get(id).getTitle(),
                                    epics.get(id).getSpecification(),
                                    epics.get(id).getStatus(),
                                    arrayOfSubtaskId);
                            epics.put(id, updateEpic);
                        } else if (command == 2) {
                            System.out.println("Введите Id подзадачи.");
                            int idOfSubtask = scanner.nextInt();
                            ArrayList<Integer> arrayOfSubtaskId = epics.get(id).getArrayOfSubtaskId();
                            arrayOfSubtaskId.add(idOfSubtask);
                            EpicTask updateEpic = new EpicTask(epics.get(id).getTitle(),
                                    epics.get(id).getSpecification(),
                                    epics.get(id).getStatus(),
                                    arrayOfSubtaskId);
                            epics.put(id, updateEpic);
                        } else if (command == 0) {
                            break;
                        } else {
                            System.out.println("Введите значение из предложенных выше.");
                        }
                    }
                } else if (userInput == 0) {
                    break;
                } else {
                    System.out.println("Введите значение из предложенных выше.");
                }
            }
        } else {
            System.out.println("Задачи нет в списках, проверьте id.");
        }
    }

    protected void removeTaskById() {
        System.out.println("Введите id задачи, которую хотите удалить");
        int id = scanner.nextInt();
        if (tasks.containsKey(id)) {
            tasks.remove(id);
            System.out.println("Задача удалена.");
        } else if (subtasks.containsKey(id)) {
            subtasks.remove(id);
            ArrayList<Integer> arrayOfSubtaskId = epics.get(id).getArrayOfSubtaskId();
            arrayOfSubtaskId.remove(id);
            epics.get(id).setArrayOfSubtaskId(arrayOfSubtaskId);
            System.out.println("Подзадача удалена.");
        } else if (epics.containsKey(id)) {
            epics.remove(id);
            ArrayList<Integer> arrayOfSubtaskId = epics.get(id).getArrayOfSubtaskId();
            for (Integer subtaskId : arrayOfSubtaskId) {
                subtasks.remove(subtaskId);
            }
            System.out.println("Эпик и его подзадачи удалены.");
        } else {
            System.out.println("Задачи нет в списках, проверьте id.");
        }
    }

    protected void getAllSubtasksByOneEpic() {
        System.out.println("Введите id эпика.");
        int id = scanner.nextInt();
        if (epics.containsKey(id)) {
            EpicTask epicTask = epics.get(id);
            ArrayList<Integer> subtaskId = epicTask.getArrayOfSubtaskId();
            for (int idS : subtaskId) {
                System.out.println(subtasks.get(idS));
            }
        } else {
            System.out.println("Задачи нет в списках, проверьте id.");
        }
    }

    protected void changeStatusOfTask() {
        System.out.println("Введите id задачи, статус которой вы хотите обновить.");
        int id = scanner.nextInt();
        if (tasks.containsKey(id)) {
            System.out.println("Введите новый статус задачи: 1 - IN_PROGRESS, 2 - DONE. ");
            int command = scanner.nextInt();
            if (command == 1) {
                Task epicWithUpdateStatus = new Task(tasks.get(id).getTitle(),
                        tasks.get(id).getSpecification(),
                        "IN_PROGRESS");
                tasks.put(id, epicWithUpdateStatus);
            } else if (command == 2) {
                Task epicWithUpdateStatus = new Task(tasks.get(id).getTitle(),
                        tasks.get(id).getSpecification(),
                        "DONE");
                tasks.put(id, epicWithUpdateStatus);
            } else {
                System.out.println("Введите значение из предложенных выше.");
            }
        } else if (epics.containsKey(id)) {
            ArrayList<Integer> arrayOfSubtaskId = epics.get(id).getArrayOfSubtaskId();
            for (Integer subtaskId : arrayOfSubtaskId) {
                if (subtasks.get(subtaskId).getStatus().equals("NEW") || arrayOfSubtaskId.isEmpty()) {
                    EpicTask epicWithUpdateStatus = new EpicTask(epics.get(id).getTitle(),
                            epics.get(id).getSpecification(),
                            "NEW",
                            epics.get(id).getArrayOfSubtaskId());
                    epics.put(id, epicWithUpdateStatus);
                } else if (subtasks.get(subtaskId).getStatus().equals("DONE")) {
                    EpicTask epicWithUpdateStatus = new EpicTask(epics.get(id).getTitle(),
                            epics.get(id).getSpecification(),
                            "DONE",
                            epics.get(id).getArrayOfSubtaskId());
                    epics.put(id, epicWithUpdateStatus);
                } else {
                    EpicTask epicWithUpdateStatus = new EpicTask(epics.get(id).getTitle(),
                            epics.get(id).getSpecification(),
                            "IN_PROGRESS",
                            epics.get(id).getArrayOfSubtaskId());
                    epics.put(id, epicWithUpdateStatus);
                }
            }
        } else if (subtasks.containsKey(id)) {
            System.out.println("Введите новый статус задачи: 1 - IN_PROGRESS, 2 - DONE. ");
            int command = scanner.nextInt();
            if (command == 1) {
                SubTask subtaskWithUpdateStatus = new SubTask(subtasks.get(id).getTitle(),
                        subtasks.get(id).getSpecification(),
                        "IN_PROGRESS",
                        subtasks.get(id).getEpicId());
                subtasks.put(id, subtaskWithUpdateStatus);
            } else if (command == 2) {
                SubTask subtaskWithUpdateStatus = new SubTask(subtasks.get(id).getTitle(),
                        subtasks.get(id).getSpecification(),
                        "DONE",
                        subtasks.get(id).getEpicId());
                subtasks.put(id, subtaskWithUpdateStatus);
            } else {
                System.out.println("Введите значение из предложенных выше.");
            }
        } else {
            System.out.println("Задачи нет в списках, проверьте id.");
        }
    }
}