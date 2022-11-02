package service;

import java.nio.file.Paths;

public class Managers {

    private Managers() {
    }

    public static TaskManager getDefault(){
        return new InMemoryTaskManager();
    }

    public static HistoryManager getDefaultHistory() {
        return new InMemoryHistoryManager();
    }

//    public static TaskManager getDefaultFile() {
//        return new FileBackedTasksManager(Paths.get("./kanban.csv"));
//    }
}