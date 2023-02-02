package com.yandex.app.service;

import com.yandex.app.model.Task;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class FileBackedTaskManagerTest extends TaskManagerTest<FileBackedTasksManager> {
    private File file;

    @BeforeEach
    void setUp() {
        file = new File("./resources/test.csv");
        super.taskManager = new FileBackedTasksManager();
        initTasks();
        taskManager.getTaskById(1);
        taskManager.getEpicById(2);
        taskManager.getSubtaskById(3);
        taskManager.getSubtaskById(4);
    }

    @Test
    void loadFromFile() {
        FileBackedTasksManager fileManager = FileBackedTasksManager.loadFromFile(file);
        assertEquals(1, fileManager.tasks.size(), "Количество задач после выгрузки не совпадает");
        assertEquals(taskManager.getListOfTasks(), fileManager.getListOfTasks(),
                "Список задач после выгрузки не совпададает");
        assertEquals(1, fileManager.epics.size(), "Количество эпиков после выгрузки не совпадает");
        assertEquals(taskManager.getListOfEpics(), fileManager.getListOfEpics(),
                "Список эпиков после выгрузки не совпадает");
        assertEquals(2, fileManager.subtasks.size(),
                "Количество подзадач после выгрузки не совпадает");
        assertEquals(taskManager.getListOfTasks(), fileManager.getListOfTasks(),
                "Список подзадач после выгрузки не совпадает");
        List<Task> history = taskManager.getHistory();
        List<Task> historyFromFile = fileManager.getHistory();
        assertEquals(4, historyFromFile.size(), "Список истории сформирован неверно");
        assertEquals(history, historyFromFile, "Список истории после выгрузки не совпадает");
        assertEquals(taskManager.getPrioritizedTasks(), fileManager.getPrioritizedTasks(),
                "Отсортированный список после выгрузки не совпадает");
        assertEquals(4, taskManager.generatorId,
                "Идентификатор последней добавленной задачи после выгрузки не совпадает");
    }

//    @AfterEach
//    void tearDown() {
//        if ((file.exists())) {
//            assertTrue(file.delete());
//        }
//    }
}