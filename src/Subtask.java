import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Subtask extends Task {
    int parentID;

    static HashMap<Integer, Subtask> subtasks = new HashMap<>();

    public Subtask(int parentID, int id, String title, String description, Status status) {
        super(id, title, description, status);
        this.parentID = parentID;
    }

    public static void create(int parentID) {
        BigTask bigTask = BigTask.bigTasks.get(parentID);
        switch (bigTask.subtasks.size()) {
            case 0:
                System.out.print("Введите первую подзадачу для этой задачи: ");
                break;
            default:
                System.out.print("Допишите новую подзадачу для задачи id[" + bigTask.id + "] «" + bigTask.title + "»: ");
        }
        while (true) {
            Scanner scanner = new Scanner(System.in);
            String title = scanner.nextLine();
            if (title.equals("")) {
                System.out.println("[!] Нужно обязательно ввести заголовок для создания задачи.\n" +
                        "Введите, что планируете сделать или 0, чтобы вернуться в меню:");
            } else if (title.equals("0")) {
                Main.printMessageGoBack();
                break;
            } else {
                System.out.print("Добавьте описание (необязательно): ");
                scanner = new Scanner(System.in);
                String description = scanner.nextLine();
                int id = Manager.idCounter;
                Subtask subtask = new Subtask(bigTask.id, id, title, description, Status.NEW);
                bigTask = BigTask.bigTasks.get(subtask.parentID);
                if (bigTask.subtasks.contains(subtask)) {
                    System.out.println("[!] Такая задача уже есть среди подзадач задачи. " +
                            "Введите другую или 0, чтобы выйти в меню");
                } else if (BigTask.bigTasks.containsValue(subtask)) {
                    System.out.println("[!] Такая задача уже есть среди задач с подзадачами. " +
                            "Назовите по-другому, чтобы избежать путаницы");
                } else if (Task.tasks.containsValue(subtask)) {
                    System.out.println("[!] Такая задача уже есть среди обычных задач.");
                } else {
                    print(subtask, true, false);
                    bigTask.subtasks.add(subtask);
                    Subtask.subtasks.put(subtask.id, subtask);
                    Main.pause();
                    Manager.idCounter++;
                    break;
                }
            }
        }
    }

    public static void create(int parentID, Task task) {
        if (BigTask.bigTasks.containsKey(parentID)) {
            BigTask bigTask = BigTask.bigTasks.get(parentID);
            Subtask subtask = new Subtask(parentID, task.id, task.title, task.description, task.status);
            bigTask.subtasks.add(subtask);
            System.out.println("Задача id[" + task.id + "] «" + task.title + "» теперь является подзадачей id[" +
                    bigTask.id + "] «" + bigTask.title + "»");
            Task.tasks.remove(task.id, task);
        } else if (Task.tasks.containsKey(parentID)) {
            Task taskAsBigTask = Task.tasks.get(parentID);
            BigTask bigTask = new BigTask(taskAsBigTask.id, taskAsBigTask.title, taskAsBigTask.description,
                    taskAsBigTask.status, new ArrayList<>());
            BigTask.bigTasks.put(bigTask.id, bigTask);
            Subtask subtask = new Subtask(bigTask.id, task.id, task.title, task.description, task.status);
            Subtask.subtasks.put(subtask.id, subtask);
            bigTask.subtasks.add(subtask);
            System.out.println("Задача id[" + task.id + "] «" + task.title + "» теперь является подзадачей id[" +
                    bigTask.id + "] «" + bigTask.title + "»");
            Task.tasks.remove(taskAsBigTask.id, taskAsBigTask);
            Task.tasks.remove(task.id, task);
        }
        Main.pause();
    }

    static void printAll(BigTask bigTask) {
        for (Subtask subtask : bigTask.subtasks) {
            print(subtask, false, false);
        }
    }

    public static void print(Subtask subtask, boolean isCreatedNow, boolean isWhole) {
        if (isWhole) {
            String result = "↳id[" + subtask.id + "] " + subtask.title + " | " + subtask.status;
            result = subtask.description.length() == 0 ? result : result + "\nОписание: " + subtask.description;
            System.out.println(result);
            System.out.println("Тип задачи: подзадача");
            BigTask bigTask = BigTask.bigTasks.get(subtask.parentID);
            System.out.println("Родительская задача: id[" + bigTask.id + "] " + bigTask.title +
                    BigTask.getShortDescription(bigTask) + " | " + bigTask.status);
        } else {
            String mainSpelling = "  ↳id[" + subtask.id + "] " + subtask.title + getShortDescription(subtask)
                    + " | " + subtask.status;
            String result = isCreatedNow ? mainSpelling + " — добавлено сейчас" : mainSpelling;
            System.out.println(result);
        }
    }

    public static void take(Subtask subtask) {
        BigTask bigTask = BigTask.bigTasks.get(subtask.parentID);
        Status initialStatus = subtask.status;
        switch (subtask.status) {
            case NEW:
                subtask.status = Task.Status.IN_PROCESS;
                System.out.println("Статус задачи ↳[" + subtask.id + "] «" + subtask.title + "» изменён: " +
                        initialStatus + " → " + subtask.status);
                bigTask.status = Task.Status.IN_PROCESS;
                Main.pause();
                break;
            case IN_PROCESS:
                System.out.println("[!] Задача уже помечена как в процессе выполнения");
                break;
            case DONE:
                System.out.println("Задача помечена выполненной. Пометить её как в процессе выполнения?\n" +
                        "1 — да, 0 — нет");
                Scanner scanner = new Scanner(System.in);
                int command = scanner.nextInt();
                switch (command) {
                    case 1:
                        subtask.status = Task.Status.IN_PROCESS;
                        System.out.println("Статус задачи ↳[" + subtask.id + "] «" + subtask.title + "» изменён: " +
                                initialStatus + " → " + subtask.status);
                        bigTask.status = Task.Status.IN_PROCESS;
                        Main.pause();
                        break;
                    case 0:
                        System.out.println("Изменение статуса задачи отменено");
                        break;
                }
                break;
        }
    }

    public static void complete(Subtask subtask) {
        BigTask bigTask = BigTask.bigTasks.get(subtask.parentID);
        switch (subtask.status) {
            case NEW:
                System.out.println("Подзадача ↳[" + subtask.id + "] «" + subtask.title +
                        "» ещё не была на выполнении. Всё равно пометить её завершённой?\n" +
                        "1 — пометить завершённой, 2 — пометить взятой на исполнение, 0 — назад");
                Scanner scanner = new Scanner(System.in);
                int command = scanner.nextInt();
                switch (command) {
                    case 1:
                        subtask.status = Status.DONE;
                        System.out.println("Статус задачи ↳[" + subtask.id + "] «" + subtask.title + "» изменён: " +
                                Status.NEW + " → " + subtask.status);
                        BigTask.automaticallyChangeStatus(bigTask);
                        Main.pause();
                        break;
                    case 2:
                        subtask.status = Status.IN_PROCESS;
                        System.out.println("Статус задачи ↳[" + subtask.id + "] «" + subtask.title + "» изменён: " +
                                Status.NEW + " → " + subtask.status);
                        BigTask.automaticallyChangeStatus(bigTask);
                        Main.pause();
                        break;
                    case 0:
                        System.out.println("Изменение статуса задачи отменено.");
                        break;
                    default:
                }
                break;
            case IN_PROCESS:
                subtask.status = Status.DONE;
                System.out.println("Статус задачи ↳[" + subtask.id + "] «" + subtask.title + "» изменён: " +
                        Status.NEW + " → " + subtask.status);
                BigTask.automaticallyChangeStatus(bigTask);
        }
    }

    public static void delete(Subtask subtask) {
        BigTask bigTask = BigTask.bigTasks.get(subtask.parentID);
        while (true) {
            System.out.println("Удалить подзадачу ↳[" + subtask.id + "] «" + subtask.title +
                    "» из списка подзадач задачи id[" + bigTask.id + "] «" + bigTask.title +
                    "» без возможности восстановления?\n" +
                    "1 — да, 0 — нет");
            Scanner scanner = new Scanner(System.in);
            if (scanner.hasNextInt()) {
                int command = scanner.nextInt();
                if (command == 1) {
                    subtasks.remove(subtask.id, subtask);
                    System.out.println("Задача ↳id[" + subtask.id + "] «" + subtask.title +
                            "» удалена из списка подзадач задачи id[" + bigTask.id + "] «" + bigTask.title + "»");
                    bigTask.subtasks.remove(subtask);
                    BigTask.typeCheck(bigTask);
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
}