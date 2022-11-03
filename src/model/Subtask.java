package model;

import java.util.Objects;

public class Subtask extends Task {
    private int epicId;

    //конструктор для создания подзадачи
    public Subtask(String title, String description, int epicId) {
        super(title, description);
        this.epicId = epicId;
    }

    //конструктор для обновления подзадачи
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
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Subtask otherSubtask = (Subtask) obj;
        return Objects.equals(title, otherSubtask.title) &&
                Objects.equals(description, otherSubtask.description) &&
                (id == otherSubtask.id) &&
                Objects.equals(status, otherSubtask.status) &&
                (epicId == otherSubtask.epicId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, description, id, status, epicId);
    }

    @Override
    public String toString() {
        return "Подзадача{" +
                "название='" + title + '\'' +
                ", описание='" + description + '\'' +
                ", id='" + id + '\'' +
                ", статус='" + status + '\'' +
                ", id эпика='" + epicId + '}' + '\'';
    }
}
