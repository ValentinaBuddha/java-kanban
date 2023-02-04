package com.yandex.app.http;

import com.yandex.app.service.TaskManagerTest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class HttpTaskManagerTest extends TaskManagerTest<HttpTaskManager> {

    private KVServer server;

    @BeforeEach
    void setUp() {
        try {
            server = new KVServer();
            server.start();
            super.taskManager = new HttpTaskManager();
            initTasks();
            taskManager.getTaskById(1);
            taskManager.getEpicById(2);
            taskManager.getSubtaskById(4);
            taskManager.getSubtaskById(3);
        } catch (IOException e) {
            System.out.println("Ошибка при создании менеджера");
        }
    }

    @AfterEach
    void tearDown() {
        server.stop();
    }

    @Test
    void saveAndLoad() {
        HttpTaskManager httpTaskManager = new HttpTaskManager(true);

        assertEquals(taskManager.getListOfTasks(), httpTaskManager.getListOfTasks(),
                "Список задач после выгрузки не совпадает");
        assertEquals(taskManager.getListOfEpics(), httpTaskManager.getListOfEpics(),
                "Список эпиков после выгрузки не совпадает");
        assertEquals(taskManager.getListOfSubtasks(), httpTaskManager.getListOfSubtasks(),
                "Список подзадач после выгрузки не совпадает");
        assertEquals(taskManager.getPrioritizedTasks(), httpTaskManager.getPrioritizedTasks(),
                "Список приоритизации после выгрузки не совпадает");
        assertEquals(taskManager.getHistory(), httpTaskManager.getHistory(),
                "Список истории после выгрузки не совпадает");

        assertEquals(1, httpTaskManager.getListOfTasks().get(0).getId(),
                "Id после выгрузки не совпадает");
        assertEquals(2, httpTaskManager.getListOfEpics().get(0).getId(),
                "Id после выгрузки не совпадает");
        assertEquals(3, httpTaskManager.getListOfSubtasks().get(0).getId(),
                "Id после выгрузки не совпадает");
        assertEquals(4, httpTaskManager.getListOfSubtasks().get(1).getId(),
                "Id после выгрузки не совпадает");

        assertEquals(httpTaskManager.generatorId, taskManager.generatorId,
                "Идентификатор последней добавленной задачи после выгрузки не совпадает");
    }
}