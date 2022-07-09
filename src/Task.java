import java.util.Objects;

public abstract class Task {
    int id;
    String title;
    String description;
    Status status;

    public Task(int id, String title, String description, Status status) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.status = status;
    }

    void print(boolean isCreatedNow) {
        System.out.println("id[" + id + "] " + title +
                (description.isEmpty() ? "" : " (см. детали)") +
                " | " + status + (isCreatedNow ? " — добавлено сейчас" : ""));
    }

    void printEntirely() {
        System.out.println("id[" + id + "] " + title + " | " + status +
                (description.isEmpty() ? "" : "\nДетали: " + description));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Task task = (Task) o;
        return Objects.equals(title, task.title) && Objects.equals(description, task.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, description);
    }
}