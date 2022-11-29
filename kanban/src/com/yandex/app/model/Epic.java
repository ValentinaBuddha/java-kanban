package com.yandex.app.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Epic extends Task {

    private List<Integer> subtaskIds = new ArrayList<>();
    protected LocalDateTime endTime;

    public Epic(String title, String description, LocalDateTime startTime, long duration, List<Integer> subtaskIds) {
        super(title, description, startTime, duration);
        this.subtaskIds = subtaskIds;
    }

    public Epic(String title, String description) {
        super(title, description);
    }

    public Epic(String title, String description, int id, TaskStatus status) {
        super(title, description, id, status);
    }

    public Epic(String title, String description, int id, TaskStatus status, List<Integer> subtaskIds) {
        super(title, description, id, status);
        this.subtaskIds = subtaskIds;
    }

    public List<Integer> getSubtaskIds() {
        return subtaskIds;
    }

    @Override
    public TaskType getTaskType() {
        return TaskType.EPIC;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Epic epic = (Epic) o;
        return Objects.equals(title, epic.title) &&
                Objects.equals(description, epic.description) &&
                (id == epic.id) &&
                Objects.equals(status, epic.status) &&
                Objects.equals(subtaskIds, epic.subtaskIds)&&
                Objects.equals(startTime, epic.startTime) &&
                (duration == epic.duration);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, description, id, status, subtaskIds, duration, startTime);
    }

    @Override
    public String toString() {
        if (subtaskIds.isEmpty()) {
            return "Эпик{" +
                    "название='" + title + '\'' +
                    ", описание='" + description + '\'' +
                    ", id='" + id + '\'' +
                    ", статус='" + status + '}' + '\'';
        } else {
            return "Эпик{" +
                    "название='" + title + '\'' +
                    ", описание='" + description + '\'' +
                    ", id='" + id + '\'' +
                    ", статус='" + status + '\'' +
                    ", дата начала='" + startTime.format(formatter) + '\'' +
                    ", продолжительность='" + duration + '\'' +
                    ", id подзадач(и)='" + subtaskIds + '}' + '\'';
        }
    }
}
