import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        fillTestData();
        while (true) {
            if (Manager.isTaskListEmpty()) {
                welcomeScreen();
            } else {
                workingArea();
            }
        }
    }

    private static void welcomeScreen() {
        System.out.println("[!] Список задач пуст");
        System.out.println("1 — добавить задачу");
        System.out.println("0 — выйти");
        Scanner scanner = new Scanner(System.in);
        int command = scanner.nextInt();
        switch (command) {
            case 1:
                System.out.println("""
                        Какую задачу создать?
                        1 — обычную
                        2 — с подзадачами
                        0 — назад""");
                if (scanner.hasNextInt()) {
                    command = scanner.nextInt();
                    switch (command) {
                        case 1:
                            Task.create();
                            break;
                        case 2:
                            BigTask.create();
                            break;
                        case 0:
                            printMessageGoBack();
                            return;
                        default:
                            printErrorOfCommandInput();
                    }
                } else {
                    printErrorOfInputOfCommandType();
                }
                break;
            case 0:
                System.out.println("Программа завершена");
                System.exit(0);
        }
    }

    private static void workingArea() {
        Manager.printAllTasks();
        menu();
    }

    public static void menu() {
        while (true) {
            System.out.println("Введите:\n" +
                    "номер id[] — вызвать меню задачи\n" +
                    "m — вызвать меню программы\n" +
                    "t — создать задачу\n" +
                    "q — выйти");
            Scanner scanner = new Scanner(System.in);
            if (scanner.hasNextInt()) {
                int id = scanner.nextInt();
                if (id > 0 && id <= Manager.idCounter) {
                    taskMenu(id);
                    break;
                } else if (id > Manager.idCounter) {
                    System.out.println("[!] Нет задачи с таким id[]");
                } else {
                    System.out.println("[!] Нужно ввести целое число больше 0");
                }
            } else {
                String command = scanner.nextLine();
                switch (command) {
                    case "m":
                        Manager.settings();
                        return;
                    case "t":
                        Manager.createTask();
                        return;
                    case "q":
                        System.out.println("Завершение программы");
                        System.exit(0);
                    default:
                        printErrorOfCommandInput();
                }
            }
        }
    }

    private static void taskMenu(int id) {
        if (Task.tasks.containsKey(id)) {
            Task task = Task.tasks.get(id);
            switch (task.status) {
                case NEW:
                    while (true) {
                        Task.print(task, false, true);
                        System.out.println("""
                                ЧТО СДЕЛАТЬ С ЭТОЙ ЗАДАЧЕЙ?
                                1 — перенести в подзадачи к другой задаче
                                2 — удалить
                                3 — пометить взятой на выполнение
                                4 — пометить завершённой
                                5 — выйти из меню задачи""");
                        Scanner scanner = new Scanner(System.in);
                        if (scanner.hasNextInt()) {
                            int command = scanner.nextInt();
                            switch (command) {
                                case 1:
                                    Task.convertToSubtask(task);
                                    return;
                                case 2:
                                    Task.delete(task);
                                    return;
                                case 3:
                                    Task.take(task);
                                    return;
                                case 4:
                                    Task.complete(task);
                                    return;
                                case 5:
                                    Main.printMessageGoBack();
                                    return;
                                default:
                                    Main.printErrorOfInputOfCommandType();
                            }
                        } else {
                            Main.printErrorOfInputOfCommandType();
                        }
                    }
                case IN_PROCESS:
                    while (true) {
                        Task.print(task, false, true);
                        System.out.println("""
                                ЧТО СДЕЛАТЬ С ЭТОЙ ЗАДАЧЕЙ?
                                1 — перенести в подзадачи к другой задаче
                                2 — удалить
                                3 — пометить завершённой
                                4 — выйти из меню задачи""");
                        Scanner scanner = new Scanner(System.in);
                        if (scanner.hasNextInt()) {
                            int command = scanner.nextInt();
                            switch (command) {
                                case 1:
                                    Task.convertToSubtask(task);
                                    return;
                                case 2:
                                    Task.delete(task);
                                    return;
                                case 3:
                                    Task.complete(task);
                                    return;
                                case 4:
                                    Main.printMessageGoBack();
                                    return;
                                default:
                                    Main.printErrorOfInputOfCommandType();
                            }
                        } else {
                            Main.printErrorOfInputOfCommandType();
                        }
                    }
                case DONE:
                    while (true) {
                        Task.print(task, false, true);
                        System.out.println("""
                                ЧТО СДЕЛАТЬ С ЭТОЙ ЗАДАЧЕЙ?
                                1 — удалить
                                2 — вернуть задаче статус взятой на выполнение
                                3 — выйти из меню задачи""");
                        Scanner scanner = new Scanner(System.in);
                        if (scanner.hasNextInt()) {
                            int command = scanner.nextInt();
                            switch (command) {
                                case 1:
                                    Task.delete(task);
                                    return;
                                case 2:
                                    Task.take(task);
                                    return;
                                case 3:
                                    Main.printMessageGoBack();
                                    return;
                                default:
                                    Main.printErrorOfInputOfCommandType();
                            }
                        } else {
                            Main.printErrorOfInputOfCommandType();
                        }
                    }
            }
        } else if (BigTask.bigTasks.containsKey((id))) {
            BigTask bigTask = BigTask.bigTasks.get(id);
            BigTask.print(bigTask, false, true);
            while (true) {
                System.out.println("""
                        ЧТО СДЕЛАТЬ С ЭТОЙ ЗАДАЧЕЙ B ЕЁ ПОДЗАДАЧАМИ?
                        1 — удалить
                        2 — добавить подзадачу
                        0 — выйти из меню задачи""");
                Scanner scanner = new Scanner(System.in);
                if (scanner.hasNextInt()) {
                    int command = scanner.nextInt();
                    switch (command) {
                        case 1:
                            BigTask.delete(bigTask);
                            return;
                        case 2:
                            Subtask.create(bigTask.id);
                            return;
                        case 0:
                            Main.printMessageGoBack();
                            return;
                        default:
                            Main.printErrorOfInputOfCommandType();
                    }
                } else {
                    Main.printErrorOfInputOfCommandType();
                }
            }
        } else if (Subtask.subtasks.containsKey(id)) {
            Subtask subtask = Subtask.subtasks.get(id);
            subtask.print(subtask, false, true);
            switch (subtask.status) {
                case NEW:
                    while (true) {
                        System.out.println("""
                                ЧТО СДЕЛАТЬ С ЭТОЙ ЗАДАЧЕЙ?
                                 1 — удалить
                                 2 — пометить взятой на выполнение
                                 3 — пометить завершённой
                                 4 — выйти из меню задачи""");
                        Scanner scanner = new Scanner(System.in);
                        if (scanner.hasNextInt()) {
                            int command = scanner.nextInt();
                            switch (command) {
                                case 1:
                                    Subtask.delete(subtask);
                                    return;
                                case 2:
                                    Subtask.take(subtask);
                                    return;
                                case 3:
                                    Subtask.complete(subtask);
                                    return;
                                case 4:
                                    Main.printMessageGoBack();
                                    return;
                                default:
                                    Main.printErrorOfInputOfCommandType();
                            }
                        } else {
                            Main.printErrorOfInputOfCommandType();
                        }
                    }
                case IN_PROCESS:
                    while (true) {
                        System.out.println("""
                                ЧТО СДЕЛАТЬ С ЭТОЙ ЗАДАЧЕЙ?
                                1 — удалить
                                2 — пометить завершённой
                                3 — выйти из меню задачи""");
                        Scanner scanner = new Scanner(System.in);
                        if (scanner.hasNextInt()) {
                            int command = scanner.nextInt();
                            switch (command) {
                                case 1:
                                    Subtask.delete(subtask);
                                    return;
                                case 2:
                                    Subtask.complete(subtask);
                                    return;
                                case 4:
                                    Main.printMessageGoBack();
                                    return;
                                default:
                                    Main.printErrorOfInputOfCommandType();
                            }
                        } else {
                            Main.printErrorOfInputOfCommandType();
                        }
                    }
                case DONE:
                    while (true) {
                        System.out.println("""
                                ЧТО СДЕЛАТЬ С ЭТОЙ ЗАДАЧЕЙ?
                                1 — удалить
                                2 — вернуть задаче статус взятой на выполнение
                                3 — выйти из меню задачи""");
                        Scanner scanner = new Scanner(System.in);
                        if (scanner.hasNextInt()) {
                            int command = scanner.nextInt();
                            switch (command) {
                                case 1:
                                    Subtask.delete(subtask);
                                    return;
                                case 2:
                                    Subtask.take(subtask);
                                    return;
                                case 3:
                                    Main.printMessageGoBack();
                                    return;
                                default:
                                    Main.printErrorOfInputOfCommandType();
                            }
                        } else {
                            Main.printErrorOfInputOfCommandType();
                        }
                    }
            }
        }
    }

    private static void fillTestData() {
        BigTask task1 = new BigTask(1, "Улучшить сайт", "", Task.Status.NEW, new ArrayList<>());
        BigTask.bigTasks.put(task1.id, task1);

        Subtask task2 = new Subtask(1, 2, "Внести исправления в функционал личного кабинета", "Cписок правок — в корпоративном облаке (папка «Очередь правок на сайт»)", Task.Status.NEW);
        Subtask.subtasks.put(task2.id, task2);
        task1.subtasks.add(task2);

        Task task3 = new Task(3, "Проанализировать цены на офисные стеллажи", "", Task.Status.NEW);
        Task.tasks.put(task3.id, task3);

        Task task4 = new Task(4, "Купить офисные кресла", "20 шт. с подлокотниками и эргономичной спинкой", Task.Status.NEW);
        Task.tasks.put(task4.id, task4);

        BigTask task5 = new BigTask(5, "Заключить договор 143", "", Task.Status.NEW, new ArrayList<>());
        BigTask.bigTasks.put(task5.id, task5);

        Subtask task6 = new Subtask(5, 6, "Уладить разногласия", "", Task.Status.NEW);
        Subtask.subtasks.put(task6.id, task6);
        task5.subtasks.add(task6);

        Subtask task7 = new Subtask(5, 7, "Подписать договор", "", Task.Status.NEW);
        Subtask.subtasks.put(task7.id, task7);
        task5.subtasks.add(task7);

        Manager.idCounter = 8;
    }

    static void printErrorOfCommandInput() {
        System.out.println("[!] Нужно ввести команду из предложенных");
    }

    static void printErrorOfInputOfCommandType() {
        System.out.println("[!] Команду нужно вводить целым числом");
    }

    static void printMessageGoBack() {
        System.out.println("↓ возврат");
    }

    static void pause() {
        System.out.println("Нажмите Enter, чтобы продолжить");
        Scanner scanner = new Scanner(System.in);
        String command = scanner.nextLine();
    }
}