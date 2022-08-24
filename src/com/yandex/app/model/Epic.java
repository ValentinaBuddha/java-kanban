package com.yandex.app.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Epic extends Task {

    private List<Integer> subtaskIds = new ArrayList<>();

    public Epic(String title, String specification) {
        super(title, specification);
    }

    public Epic(String title, String specification, int id, TaskStatus status, List<Integer> subtaskIds) {
        super(title, specification, id, status);
        this.subtaskIds = subtaskIds;
    }

    public List<Integer> getSubtaskIds() {
        return subtaskIds;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Epic otherEpic = (Epic) obj;
        return Objects.equals(title, otherEpic.title) &&
                Objects.equals(specification, otherEpic.specification) &&
                (id == otherEpic.id) &&
                Objects.equals(status, otherEpic.status) &&
                Objects.equals(subtaskIds, otherEpic.subtaskIds);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, specification, id, status, subtaskIds);
    }

    @Override
    public String toString() {
        return "Эпик{" +
                "название='" + title + '\'' +
                ", описание='" + specification + '\'' +
                ", id='" + id + '\'' +
                ", статус='" + status + '\'' +
                ", id подзадач(и)='" + subtaskIds + '}' + '\'';
    }
}
