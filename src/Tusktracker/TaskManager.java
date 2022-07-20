package Tusktracker;

import java.util.ArrayList;
import java.util.List;

public interface TaskManager {

    void createNewTask(Task newTask);

    void createNewSubtask(Subtask newSubtask);

    void createNewEpic(Epic newEpic);

    void removeAllTasks();

    void removeAllSubtasks();

    void removeAllEpics();

    ArrayList<Task> getListOfTasks();

    ArrayList<Epic> getListOfEpics();

    ArrayList<Subtask> getListOfSubtasks();

    Task getTaskById(int id);

    Epic getEpicById(int id);

    Subtask getSubtaskById(int id);

    ArrayList<Subtask> getListOfSubtasksByOneEpic(int id);

    void removeTaskById(int taskId);

    void removeEpicById(int epicId);

    void removeSubtaskById(int subtaskIdForRemove);

    void updateTheTask(Task updateTask);

    void updateTheEpic(Epic updateEpic);

    void updateTheSubtask(Subtask updateSubtask);

    void checkEpicStatus(int epicId);

    List<Task> getHistory();
}
