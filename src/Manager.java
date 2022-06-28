import java.util.Scanner;

public class Manager {
    public static int idCounter = 1;

    public static boolean isTaskListEmpty() {
        return Task.tasks.isEmpty() && BigTask.bigTasks.isEmpty();
    }

    public static void createTask() {
        while (true) {
            System.out.println("""
                    Выберите, какую задачу создать:
                    1 — обычную
                    2 — с подзадачами
                    ——
                    3 — назад""");
            Scanner scanner = new Scanner(System.in);
            if (scanner.hasNextInt()) {
                int command = scanner.nextInt();
                if (command == 1) {
                    Task.create();
                    break;
                } else if (command == 2) {
                    BigTask.create();
                    break;
                } else if (command == 3) {
                    Main.printMessageGoBack();
                    break;
                } else {
                    Main.printErrorOfCommandInput();
                }
            } else {
                Main.printErrorOfInputOfCommandType();
            }
        }
    }

    public static void printAllTasks() {
        String[] titles = {"\n\nЗАПЛАНИРОВАНО", "В РАБОТЕ", "ГОТОВО"};
        Task.Status[] statuses = {Task.Status.NEW, Task.Status.IN_PROCESS, Task.Status.DONE};
        for (int i = 0; i < 3; i++) {
            System.out.println(titles[i]);
            Task.Status status = statuses[i];
            if ((Task.countAll(status) + BigTask.countAll(status)) > 0) {
                Task.printAll(status);
                BigTask.printAll(status);
            } else {
                System.out.println("Нет задач\n");
            }
        }
    }

    public static void settings() {
        while (true) {
            System.out.println("""
                    Настройки менеджера задач:
                    1 — удалить все задачи
                    ——
                    0 — назад""");
            Scanner scanner = new Scanner(System.in);
            if (scanner.hasNextInt()) {
                int command = scanner.nextInt();
                switch (command) {
                    case 1:
                        deleteAllTasks();
                        return;
                    case 0:
                        Main.printMessageGoBack();
                        return;
                    default:
                        Main.printErrorOfCommandInput();
                }
            } else {
                Main.printErrorOfInputOfCommandType();
            }
        }
    }

    public static void deleteAllTasks() {
        while (true) {
            System.out.println("""
                    Удалить все задачи без возможности восстановления?
                    1 — да, 0 — нет""");
            Scanner scanner = new Scanner(System.in);
            if (scanner.hasNextInt()) {
                int command = scanner.nextInt();
                switch (command) {
                    case 1:
                        Task.tasks.clear();
                        Subtask.subtasks.clear();
                        BigTask.bigTasks.clear();
                        Manager.idCounter = 1;
                        System.out.println("Все задачи удалены\n");
                        return;
                    case 0:
                        Main.printMessageGoBack();
                        return;
                    default:
                        Main.printErrorOfCommandInput();
                }
            } else {
                Main.printErrorOfInputOfCommandType();
            }
        }

    }
}