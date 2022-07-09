public interface TaskManager {
    void start();
    void programMenu();
    void taskMenu(int id);
    void taskMenu(LineTask lineTask);
    void taskMenu(Subtask subtask);
    void taskMenu(BigTask bigTask);
    void createTask(TaskType taskType);
    void moveToSubtasks(LineTask lineTask);
    void delete(LineTask lineTask);
    void delete(BigTask bigTask);
    void delete(Subtask subtask);
    void deleteAllTasks();
    void printALlTasks();
    void take(LineTask lineTask);
    void take(Subtask subtask);
    void complete(LineTask lineTask);
    void complete(Subtask subtask);
    void refreshStatusOf(BigTask bigTask);
    void checkTypeOf(BigTask bigTask);
    void settings();
    void printHistory();
    void addToHistory(int id);
}
