import java.util.ArrayList;
import java.util.Objects;

public class BigTask extends Task {
    ArrayList<Subtask> subtasks;

    public BigTask(int id, String title, String description, ArrayList<Subtask> subtasks, Status status) {
        super(id, title, description, status);
        this.subtasks = new ArrayList<>();
    }

    void print(boolean isCreatedNow, boolean isPrintSubtasks, boolean isAddedNewSubtask, boolean isBottomIndent) {
        super.print(isCreatedNow);
        if (isPrintSubtasks) {
            if (isAddedNewSubtask) {
                for (int i = 0; i < subtasks.size(); i++) {
                    System.out.print("  ");
                    subtasks.get(i).print(i == subtasks.size() - 1);
                }
            } else {
                for (Subtask subtask : subtasks) {
                    System.out.print("  ");
                    subtask.print(false);
                }
            }
            System.out.print(isBottomIndent ? "\n" : "");
        }
    }

    @Override
    void printEntirely() {
        super.printEntirely();
        System.out.println("Подзадачи (" + subtasks.size() + "):");
        for (Subtask subtask : subtasks) {
            System.out.print("  ");
            subtask.print(false);
        }
        System.out.println();
    }
}