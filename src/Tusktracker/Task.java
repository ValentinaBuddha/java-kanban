package Tusktracker;

import java.util.Objects;

class Task {
    protected String title;
    protected String specification;
    protected int id;
    protected static int lastId = 0;
    protected String status;

    public Task(String title, String specification, String status) {
        this.title = title;
        this.specification = specification;
        this.id = ++lastId;
        this.status = status;
    }

    public String getTitle() {
        return title;
    }

    public String getSpecification() {
        return specification;
    }

    public String getStatus() {
        return status;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Task otherTask = (Task) obj;
        return Objects.equals(title, otherTask.title) &&
                Objects.equals(specification, otherTask.specification) &&
                (id == otherTask.id) &&
                Objects.equals(status, otherTask.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, specification, id, status);
    }

    @Override
    public String toString() {
        return "Задача{" +
                "название='" + title + '\'' +
                ", описание='" + specification + '\'' +
                ", id='" + id + '\'' +
                ", статус='" + status + '\'' + '}';
    }
}
