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
        FileBackedTasksManager fileManager = FileBackedTasksManager.loadFromFile(
                new File("./resources/test.csv"));
        assertEquals(1, fileManager.tasks.size(), "Количество задач после выгрузки не совпададает");
        assertEquals(taskManager.getListOfTasks(), fileManager.getListOfTasks(),
                "Список задач после выгрузки не совпададает");
        assertEquals(1, fileManager.epics.size(), "Количество эпиков после выгрузки не совпададает");
        assertEquals(taskManager.getListOfEpics(), fileManager.getListOfEpics(),
                "Список эпиков после выгрузки не совпададает");
        assertEquals(2, fileManager.subtasks.size(),
                "Количество подзадач после выгрузки не совпададает");
        assertEquals(taskManager.getListOfTasks(), fileManager.getListOfTasks(),
                "Список подзадач после выгрузки не совпададает");
        List<Task> history = taskManager.getHistory();
        List<Task> historyFromFile = fileManager.getHistory();
        assertEquals(4, historyFromFile.size(), "Список истории сформирован неверно");
        assertEquals(history, historyFromFile, "Список истории после выгрузки не совпададает");
        assertEquals(taskManager.getPrioritizedTasks(), fileManager.getPrioritizedTasks(),
                "Отсортированный список после выгрузки не совпададает");
        assertEquals(4, taskManager.generatorId,
                "Идентификатор последней добавленной задачи после выгрузки не совпададает");
    }

    @AfterEach
    void tearDown() {
        if ((file.exists())) {
            assertTrue(file.delete());
        }
    }
}