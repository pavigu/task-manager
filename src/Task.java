import java.util.HashMap;
import java.util.Objects;
import java.util.Scanner;

public class Task {
    int id;
    String title;
    String description;
    Status status;

    static HashMap<Integer, Task> tasks = new HashMap<>();

    public Task(int id, String title, String description, Status status) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.status = status;
    }

    public static void create() {
        System.out.print("СОЗДАНИЕ ОБЫЧНОЙ ЗАДАЧИ. Что хотите сделать: ");
        while (true) {
            Scanner scanner = new Scanner(System.in);
            String title = scanner.nextLine();
            if (title.equals("")) {
                System.out.print("[!] Введите заголовок задачи или 0, чтобы выйти в меню: ");
            } else if (title.equals("0")) {
                Main.printMessageGoBack();
                break;
            } else {
                System.out.print("Введите описание задачи (необязательно): ");
                scanner = new Scanner(System.in);
                String description = scanner.nextLine();
                int id = Manager.idCounter;
                Task task = new Task(id, title, description, Status.NEW);
                if (!(isExist(task))) {
                    tasks.put(task.id, task);
                    print(task, true, false);
                    Manager.idCounter++;
                    Main.pause();
                    break;
                } else {
                    System.out.print("[!] Такая задача уже существует. Введите другую или 0, чтобы вернуться в меню: ");
                }
            }
        }
    }

    static void convertToSubtask(Task task) {
        if (BigTask.bigTasks.containsKey(task.id)) {
            System.out.println("[!] Задачу, у которой есть свои подзадачи нельзя сделать подзадачей");
        } else if (Subtask.subtasks.containsKey(task.id)) {
            System.out.println("[!] Эта задача уже является подзадачей");
        } else {
            System.out.print("К какой задаче её добавить? Введите id: ");
            Scanner scanner = new Scanner(System.in);
            int parentID = scanner.nextInt();
            if (Subtask.subtasks.containsKey(parentID)) {
                System.out.println("[!] Указан id[] подзадачи, к ней нельзя добавить подзадачу");
            } else if (task.id == parentID) {
                System.out.println("[!] Нельзя добавить задачу в подзадачи к себе самой");
            } else {
                Subtask.create(parentID, task);
            }
        }
    }

    public static void delete(Task task) {
        while (true) {
            System.out.println("Удалить задачу id[" + task.id + "] «" + task.title +
                    "» | " + task.status + " без возможности восстановления?\n" + "1 — да, 0 — нет");
            Scanner scanner = new Scanner(System.in);
            if (scanner.hasNextInt()) {
                int command = scanner.nextInt();
                if (command == 1) {
                    tasks.remove(task.id, task);
                    System.out.println("Задача id[" + task.id + "] «" + task.title + "» удалена");
                    Main.pause();
                    break;
                } else if (command == 0) {
                    Main.printMessageGoBack();
                    break;
                }
            } else {
                Main.printErrorOfInputOfCommandType();
            }
        }
    }

    public static void take(Task task) {
        Status initialStatus = task.status;
        task.status = Status.IN_PROCESS;
        System.out.println("Статус задачи id[" + task.id + "] «" + task.title + "» изменён: " +
                initialStatus + " → " + Status.IN_PROCESS);
        Main.pause();
    }

    public static void complete(Task task) {
        if (task.status == Status.NEW) {
            while (true) {
                System.out.println("Задача [" + task.id + "] «" + task.title +
                        "» ещё не была на выполнении. Всё равно пометить её завершённой?\n" +
                        "1 — пометить завершённой, 2 — пометить взятой на исполнение, 0 — назад");
                Scanner scanner = new Scanner(System.in);
                if (scanner.hasNextInt()) {
                    int command = scanner.nextInt();
                    if (command == 1) {
                        printStatusComplete(task);
                        break;
                    } else if (command == 2) {
                        take(task);
                        break;
                    } else if (command == 0) {
                        Main.printMessageGoBack();
                        break;
                    } else {
                        Main.printErrorOfInputOfCommandType();
                    }
                } else {
                    Main.printErrorOfInputOfCommandType();
                }
            }
        } else if (task.status == Status.IN_PROCESS) {
            printStatusComplete(task);
        }
    }

    private static void printStatusComplete(Task task) {
        Status initialStatus = task.status;
        task.status = Status.DONE;
        System.out.println("Статус задачи [" + task.id + "] «" + task.title + "» изменён: " + initialStatus + " → " + Status.DONE);
        Main.pause();
    }

    public static void print(Task task, boolean isCreatedNow, boolean isWhole) {
        if (isWhole) {
            String result = "id[" + task.id + "] " + task.title + " | " + task.status;
            result = task.description.length() == 0 ? result : result + "\nОписание: " + task.description;
            System.out.println(result);
        } else {
            String mainSpelling = "id[" + task.id + "] " + task.title + getShortDescription(task) + " | " + task.status;
            String result = isCreatedNow ? mainSpelling + " — добавлено сейчас" : mainSpelling;
            System.out.println(result);
        }
    }

    static String getShortDescription(Task task) {
        return task.description.length() == 0 ? "" : " (см. детали)";
    }

    public static void printAll(Status status) {
        if (!(tasks.isEmpty())) {
            for (Task task : tasks.values()) {
                if (task.status == status) {
                    print(task, false, false);
                }
            }
            if (countAll(status) > 0) {
                System.out.println();
            }
        }
    }

    public static int countAll(Status status) {
        int taskCounter = 0;
        for (Task task : tasks.values()) {
            if (task.status == status) {
                taskCounter++;
            }
        }
        return taskCounter;
    }


    public static void printAll(boolean printTitle) {
        System.out.print(printTitle ? "ОБЫЧНЫЕ ЗАДАЧИ\n" : "");
        if (tasks.isEmpty()) {
            System.out.println("[!] Нет добавленных задач");
        } else {
            for (Task task : tasks.values()) {
                print(task, false, false);
            }
            System.out.println();
        }

    }

    static boolean isExist(Task task) {
        return (Task.tasks.containsValue(task));
    }

    enum Status {
        NEW,
        IN_PROCESS,
        DONE
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

    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", status=" + status +
                '}';
    }
}