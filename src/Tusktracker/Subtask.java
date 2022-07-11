package Tusktracker;

import java.util.Objects;

public class Subtask extends Task {
    private int epicId;

    public Subtask(String title, String specification, int epicId) {
        super(title, specification);
        this.epicId = epicId;
    }

    public Subtask(String title, String specification, String status, int epicId) {
        super(title, specification, status);
        this.epicId = epicId;
    }

    public Subtask(String title, String specification, int id, String status, int epicId) {
        super(title, specification, id, status);
        this.epicId = epicId;
    }

    public int getEpicId() {
        return epicId;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Subtask otherSubtask = (Subtask) obj;
        return Objects.equals(title, otherSubtask.title) &&
                Objects.equals(specification, otherSubtask.specification) &&
                (id == otherSubtask.id) &&
                Objects.equals(status, otherSubtask.status) &&
                (epicId == otherSubtask.epicId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, specification, id, status, epicId);
    }

    @Override
    public String toString() {
        return "Подзадача{" +
                "название='" + title + '\'' +
                ", описание='" + specification + '\'' +
                ", id='" + id + '\'' +
                ", статус='" + status + '\'' +
                ", id эпика='" + epicId + '\'' + '}';
    }
}
