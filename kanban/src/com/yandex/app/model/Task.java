package com.yandex.app.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class Task {
    protected String title;
    protected String description;
    protected int id;
    protected TaskStatus status = TaskStatus.NEW;
    protected LocalDateTime startTime;
    protected long duration;

    public Task(String title, String description, LocalDateTime startTime, long duration) {
        this.title = title;
        this.description = description;
        this.startTime = startTime;
        this.duration = duration;
    }

    public Task(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public Task(String title, String description, int id, TaskStatus status) {
        this.title = title;
        this.description = description;
        this.id = id;
        this.status = status;
    }

    public Task(String title, String description, int id, TaskStatus status, LocalDateTime startTime,
                long duration) {
        this.title = title;
        this.description = description;
        this.id = id;
        this.status = status;
        this.startTime = startTime;
        this.duration = duration;
    }

    public int getId() {
        return id;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }

    public TaskType getTaskType() {
        return TaskType.TASK;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public LocalDateTime getEndTime() {
        return startTime.plusMinutes(duration);
    }

    public static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yy HH:mm");

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return Objects.equals(title, task.title) &&
                Objects.equals(description, task.description) &&
                (id == task.id) &&
                Objects.equals(status, task.status) &&
                Objects.equals(startTime, task.startTime) &&
                (duration == task.duration);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, description, id, status, duration, startTime);
    }

    @Override
    public String toString() {
        return "Задача{" +
                "название='" + title + '\'' +
                ", описание='" + description + '\'' +
                ", id='" + id + '\'' +
                ", статус='" + status + '\'' +
                ", дата начала='" + startTime.format(formatter) + '\'' +
                ", продолжительность='" + duration + '}' + '\'';
    }
}