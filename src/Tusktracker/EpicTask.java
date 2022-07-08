package Tusktracker;

import java.util.ArrayList;
import java.util.Objects;

public class EpicTask extends Task {
    private ArrayList<Integer> arrayOfSubtaskId;

    public EpicTask(String title, String specification, String status, ArrayList<Integer> subtaskId) {
        super(title, specification, status);
        this.arrayOfSubtaskId = subtaskId;
    }

    public ArrayList<Integer> getArrayOfSubtaskId() {
        return arrayOfSubtaskId;
    }

    public void setArrayOfSubtaskId(ArrayList<Integer> arrayOfSubtaskId) {
        this.arrayOfSubtaskId = arrayOfSubtaskId;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        EpicTask otherEpic = (EpicTask) obj;
        return Objects.equals(title, otherEpic.title) &&
                Objects.equals(specification, otherEpic.specification) &&
                (id == otherEpic.id) &&
                Objects.equals(status, otherEpic.status) &&
                Objects.equals(arrayOfSubtaskId, otherEpic.arrayOfSubtaskId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, specification, id, status, arrayOfSubtaskId);
    }

    @Override
    public String toString() {
        return "Эпик{" +
                "название='" + title + '\'' +
                ", описание='" + specification + '\'' +
                ", id='" + id + '\'' +
                ", статус='" + status + '\'' +
                ", id подзадач(и)='" + arrayOfSubtaskId + '\'' + '}';
    }
}
