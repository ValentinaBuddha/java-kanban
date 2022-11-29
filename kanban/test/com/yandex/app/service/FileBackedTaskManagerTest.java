package com.yandex.app.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import java.io.File;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class FileBackedTaskManagerTest extends TaskManagerTest<FileBackedTasksManager> {
    private File file;

    @BeforeEach
    void setUp() {
        file = new File("./resources/test.csv");
        super.taskManager = new FileBackedTasksManager(file);
    }

    @AfterEach
    void tearDown() {
        assertTrue(file.delete());
    }

}
