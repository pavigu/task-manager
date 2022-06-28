import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class BigTask extends Task {
    ArrayList<Subtask> subtasks;

    static HashMap<Integer, BigTask> bigTasks = new HashMap<>();

    public BigTask(int id, String title, String description, Task.Status status, ArrayList<Subtask> subtasks) {
        super(id, title, description, status);
        this.subtasks = subtasks;
    }

    public static void create() {
        System.out.print("ВВОД ЗАДАЧИ СО СПИСКОМ ПОДЗАДАЧ. Что планируете сделать: ");
        while (true) {
            Scanner scanner = new Scanner(System.in);
            String title = scanner.nextLine();
            if (title.equals("")) {
                System.out.println("[!] Нужно обязательно ввести заголовок для создания задачи.\n" +
                        "Введите, что планируете сделать или 0, чтобы вернуться в меню:");
            } else if (title.equals("0")) {
                Main.printMessageGoBack();
                return;
            } else {
                System.out.print("Добавьте описание (необязательно): ");
                scanner = new Scanner(System.in);
                String description = scanner.nextLine();
                int id = Manager.idCounter;
                BigTask bigTask = new BigTask(id, title, description, Task.Status.NEW, new ArrayList<>());
                if (Task.tasks.containsValue(bigTask) || bigTasks.containsValue(bigTask)
                        || Subtask.subtasks.containsValue(bigTask)) {
                    System.out.println("[!] Такая задача уже есть\n" +
                            "Введите другую задачу или 0, чтобы вернуться в меню:");
                } else {
                    print(bigTask, true, false);
                    bigTasks.put(bigTask.id, bigTask);
                    Manager.idCounter++;
                    Subtask.create(bigTask.id);
                    break;
                }
            }
        }
    }

    public static void print(BigTask bigTask, boolean isCreatedNow, boolean isWhole) {
        if (isWhole) {
            String result = "id[" + bigTask.id + "] " + bigTask.title + " | " + bigTask.status;
            result = bigTask.description.length() == 0 ? result : result + "\nОписание: " + bigTask.description;
            System.out.println(result);
            System.out.println("Тип: задача с подзадачами");
            System.out.println("Количество подзадач: " + bigTask.subtasks.size());
            Subtask.printAll(bigTask);
        } else {
            String mainSpelling = "id[" + bigTask.id + "] " + bigTask.title + getShortDescription(bigTask) + " | " + bigTask.status;
            String result = isCreatedNow ? mainSpelling + " — добавлено сейчас" : mainSpelling;
            System.out.println(result);
            Subtask.printAll(bigTask);
        }
    }

    public static void printAll(boolean printTitle) {
        System.out.print(printTitle ? "ЗАДАЧИ С ПОДЗАДАЧАМИ\n" : "");
        if (bigTasks.size() == 0) {
            System.out.println("[!] Нет добавленных задач");
        } else {
            for (BigTask bigTask : bigTasks.values()) {
                print(bigTask, false, false);
                System.out.println();
            }
        }
    }

    public static void printAll(Task.Status status) {
        if (!(bigTasks.isEmpty())) {
            for (BigTask bigTask : bigTasks.values()) {
                if (bigTask.status == status) {
                    print(bigTask, false, false);
                    System.out.println();
                }
            }
        }
    }

    public static int countAll(Status status) {
        int taskCounter = 0;
        for (BigTask bigTask : bigTasks.values()) {
            if (bigTask.status == status) {
                taskCounter++;
            }
        }
        return taskCounter;
    }

    public static void delete(BigTask bigTask) {
        System.out.println("Удалить задачу id[" + bigTask.id + "] «" + bigTask.title + "» | " + bigTask.status +
                " и все её подзадачи (" + bigTask.subtasks.size() + ")?\n" +
                "1 — да, 0 — нет");
        Scanner scanner = new Scanner(System.in);
        int command = scanner.nextInt();
        if (command == 1) {
            bigTask.subtasks.clear();
            BigTask.bigTasks.remove(bigTask.id, bigTask);
            System.out.println("Задача id[" + bigTask.id + "] «" + bigTask.title + "» — удалена вместе со своими подзадачами");
            Main.pause();
        } else if (command == 0) {
            System.out.println("Удаление задачи отменено");
        }
    }

    static void automaticallyChangeStatus(BigTask bigTask) {
        int numberOfNewSubtasks = 0;
        int numberOfSubtasksInProcess = 0;
        int numberOfCompletedSubtasks = 0;
        for (Subtask subtask : bigTask.subtasks) {
            switch (subtask.status) {
                case NEW:
                    numberOfNewSubtasks++;
                    break;
                case IN_PROCESS:
                    numberOfSubtasksInProcess++;
                    break;
                case DONE:
                    numberOfCompletedSubtasks++;
                    break;
            }
        }
        if (numberOfCompletedSubtasks == bigTask.subtasks.size()) {
            bigTask.status = Task.Status.DONE;
        } else if (numberOfSubtasksInProcess > 0) {
            bigTask.status = Task.Status.IN_PROCESS;
        } else if (numberOfNewSubtasks > 0 && numberOfSubtasksInProcess == 0) {
            bigTask.status = Task.Status.NEW;
        }
    }

    public static void typeCheck(BigTask bigTask) {
        if(bigTask.subtasks.isEmpty()){
            Task task = new Task(bigTask.id, bigTask.title, bigTask.description, bigTask.status);
            Task.tasks.put(task.id, task);
            BigTask.bigTasks.remove(bigTask.id, bigTask);
        }
    }
}