package com.yandex.app.model;

import java.time.LocalDateTime;
import java.util.Objects;

public class Subtask extends Task {
    private int epicId;

    public Subtask(String title, String description, int epicId, LocalDateTime startTime, long duration) {
        super(title, description, startTime, duration);
        this.epicId = epicId;
    }

    public Subtask(String title, String description, int epicId) {
        super(title, description);
        this.epicId = epicId;
    }

    public Subtask(String title, String description, int id, TaskStatus status, int epicId, LocalDateTime startTime,
                   long duration) {
        super(title, description, id, status, startTime, duration);
        this.epicId = epicId;
    }

    public Subtask(String title, String description, int id, TaskStatus status, int epicId) {
        super(title, description, id, status);
        this.epicId = epicId;
    }

    public int getEpicId() {
        return epicId;
    }

    @Override
    public TaskType getTaskType() {
        return TaskType.SUBTASK;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Subtask subtask = (Subtask) o;
        return Objects.equals(title, subtask.title) &&
                Objects.equals(description, subtask.description) &&
                (id == subtask.id) &&
                Objects.equals(status, subtask.status) &&
                (epicId == subtask.epicId) &&
                Objects.equals(startTime, subtask.startTime) &&
                (duration == subtask.duration);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, description, id, status, startTime, duration, epicId);
    }

    @Override
    public String toString() {
        return "Подзадача{" +
                "название='" + title + '\'' +
                ", описание='" + description + '\'' +
                ", id='" + id + '\'' +
                ", статус='" + status + '\'' +
                ", дата начала='" + getStartTimeString() + '\'' +
                ", продолжительность='" + duration + '\'' +
                ", id эпика='" + epicId + '}' + '\'';
    }
}