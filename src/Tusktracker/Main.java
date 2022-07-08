package Tusktracker;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Manager manager = new Manager();
        int userInput;

        while (true) {
            printMenu();
            userInput = scanner.nextInt();
            switch (userInput) {
                case 1:
                    manager.getListOfAllTasks();
                    break;
                case 2:
                    manager.removeAllTasks();
                    break;
                case 3:
                    manager.getTaskById();
                    break;
                case 4:
                    manager.createNewTask();
                    break;
                case 5:
                    manager.updateTheTask();
                    break;
                case 6:
                    manager.removeTaskById();
                    break;
                case 7:
                    manager.getAllSubtasksByOneEpic();
                    break;
                case 8:
                    manager.changeStatusOfTask();
                    break;
                case 9:
                    System.out.println("Выход.");
                    return;
                default:
                    System.out.println("Извините, такой команды пока нет.");
            }
        }
    }

    private static void printMenu() {
        System.out.println("Что вы хотите сделать? ");
        System.out.println("1 - Получить список всех задач.");
        System.out.println("2 - Удалить все задачи.");
        System.out.println("3 - Получить задачу по id.");
        System.out.println("4 - Создать новую задачу.");
        System.out.println("5 - Обновить задачу.");
        System.out.println("6 - Удалить задачу по id.");
        System.out.println("7 - Получить список всех подзадач определённого эпика.");
        System.out.println("8 - Изменить статус задачи.");
        System.out.println("9 - Выход.");
    }
}