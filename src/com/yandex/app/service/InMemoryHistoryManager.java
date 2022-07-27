package com.yandex.app.service;

import com.yandex.app.model.Task;

import java.util.ArrayList;
import java.util.List;

public class InMemoryHistoryManager implements HistoryManager {
    private final static int SIZE_OF_HISTORY = 10;
    private final List<Task> history = new ArrayList<>();


    @Override
    public List<Task> getHistory() {
        return List.copyOf(history);
    }

    @Override
    public void add(Task task) {
        if (history.size() >= SIZE_OF_HISTORY) {
            history.remove(0);
        }
        history.add(task);
    }
}
