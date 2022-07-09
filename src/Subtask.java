public class Subtask extends Task {
    int parentID;

    public Subtask(int parentID, int id, String title, String description, Status status) {
        super(id, title, description, status);
        this.parentID = parentID;
    }

    @Override
    void print(boolean isCreatedNow) {
        System.out.print("↳");
        super.print(isCreatedNow);
    }

    void printEntirely(BigTask bigTask) {
        System.out.print("↳");
        super.printEntirely();
        System.out.print("Родительская задача: ");
        bigTask.print(false);
    }
}
