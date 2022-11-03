package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Epic extends Task {

    private List<Integer> subtaskIds = new ArrayList<>();

    //конструктор для создания эпика
    public Epic(String title, String description) {
        super(title, description);
    }

    //конструктор для создания эпика
    public Epic(String title, String description, int id, TaskStatus status) {
        super(title, description, id, status);
    }

    //конструктор для обновления эпика
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

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Epic otherEpic = (Epic) obj;
        return Objects.equals(title, otherEpic.title) &&
                Objects.equals(description, otherEpic.description) &&
                (id == otherEpic.id) &&
                Objects.equals(status, otherEpic.status) &&
                Objects.equals(subtaskIds, otherEpic.subtaskIds);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, description, id, status, subtaskIds);
    }

    @Override
    public String toString() {
        return "Эпик{" +
                "название='" + title + '\'' +
                ", описание='" + description + '\'' +
                ", id='" + id + '\'' +
                ", статус='" + status + '\'' +
                ", id подзадач(и)='" + subtaskIds + '}' + '\'';
    }
}
