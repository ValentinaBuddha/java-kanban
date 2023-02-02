package com.yandex.app.service;

import com.yandex.app.http.HttpTaskManager;

public class Managers {

    private Managers() {
    }

    public static TaskManager getDefault(){
        return new HttpTaskManager();
    }

    public static HistoryManager getDefaultHistory() {
        return new InMemoryHistoryManager();
    }
}