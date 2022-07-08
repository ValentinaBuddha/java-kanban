package Tusktracker;

import java.util.Objects;

public class SubTask extends Task {
    private int epicId;

    public SubTask(String title, String specification, String status, int epicId) {
        super(title, specification, status);
        this.epicId = epicId;
    }

    public int getEpicId() {
        return epicId;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        SubTask otherSubtask = (SubTask) obj;
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
