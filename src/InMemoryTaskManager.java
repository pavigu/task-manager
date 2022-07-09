import org.w3c.dom.ls.LSOutput;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class InMemoryTaskManager implements TaskManager {
    HashMap<Integer, LineTask> lineTasks;
    HashMap<Integer, BigTask> bigTasks;
    HashMap<Integer, Subtask> subtasks;
    ArrayList<Task> history;
    int idCounter;

    public InMemoryTaskManager() {
        this.lineTasks = new HashMap<>();
        this.bigTasks = new HashMap<>();
        this.subtasks = new HashMap<>();
        this.history = new ArrayList<>();
        this.idCounter = 1;
    }

    public void start() {
        while (true) {
            if (lineTasks.isEmpty() && bigTasks.isEmpty()) {
                System.out.println("[!] Список задач пуст\n");
                createTask(true);
            } else {
                printALlTasks();
                programMenu();
            }
        }
    }

    public void programMenu() {
        while (true) {
            System.out.println("""
                    Введите:
                    номер id[] — вызвать меню задачи
                    m — вызвать меню программы
                    t — создать задачу
                    q — выйти""");
            Scanner scanner = new Scanner(System.in);
            if (scanner.hasNextInt()) {
                int id = scanner.nextInt();
                if (id > 0 && id < idCounter) {
                    taskMenu(id);
                    pause();
                    break;
                } else if (id >= idCounter) {
                    System.out.println("[!] Нет задачи с таким id[]");
                } else {
                    System.out.println("[!] Нужно ввести целое число больше 0");
                }
            } else {
                String command = scanner.nextLine();
                switch (command) {
                    case "m" -> {
                        settings();
                        return;
                    }
                    case "t" -> {
                        createTask(false);
                        return;
                    }
                    case "q" -> {
                        System.out.println("Завершение программы");
                        System.exit(0);
                    }
                    default -> System.out.println("[!] Ожидается ввод команды из предложенных");
                }
            }
        }
    }

    public void taskMenu(int id) {
        System.out.println("МЕНЮ ЗАДАЧИ");
        addToHistory(id);
        if (lineTasks.containsKey(id)) {
            LineTask lineTask = lineTasks.get(id);
            lineTask.printEntirely();
            taskMenu(lineTask);
        } else if (bigTasks.containsKey(id)) {
            BigTask bigTask = bigTasks.get(id);
            bigTask.printEntirely();
            taskMenu(bigTask);
        } else if (subtasks.containsKey(id)) {
            Subtask subtask = subtasks.get(id);
            BigTask bigTask = bigTasks.get(subtask.parentID);
            subtask.printEntirely(bigTask);
            taskMenu(subtask);
        }
    }

    public void taskMenu(LineTask lineTask) {
        System.out.println("\nЧТО СДЕЛАТЬ С ЭТОЙ ЗАДАЧЕЙ?");
        switch (lineTask.status) {
            case NEW:
                System.out.println("""
                        1 — добавить к ней подзадачу
                        2 — перенести в подзадачи к другой задаче
                        3 — удалить
                        4 — пометить взятой на выполнение
                        5 — пометить завершённой
                        0 — выйти из меню задачи""");
                Scanner scanner = new Scanner(System.in);
                if (scanner.hasNextInt()) {
                    int command = scanner.nextInt();
                    switch (command) {
                        case 1 -> {
                            System.out.println("ДОБАВИТЬ ПОДЗАДАЧУ К ЗАДАЧЕ");
                            createSubtask(lineTask.id);
                        }
                        case 2 -> {
                            System.out.println("ПЕРЕНЕСТИ В ПОДЗАДАЧИ К ДРУГОЙ ЗАДАЧЕ");
                            moveToSubtasks(lineTask);
                        }
                        case 3 -> {
                            System.out.println("УДАЛИТЬ");
                            delete(lineTask, false);
                        }
                        case 4 -> {
                            System.out.println("ПОМЕТИТЬ ВЗЯТОЙ НА ВЫПОЛНЕНИЕ");
                            take(lineTask);
                        }
                        case 5 -> {
                            System.out.println("ПОМЕТИТЬ ЗАВЕРШЁННОЙ");
                            complete(lineTask);
                        }
                        case 0 -> {
                            messageAboutReturn();
                            return;
                        }
                        default -> warningAboutCommand();
                    }
                } else {
                    warningAboutInteger();
                }
                break;
            case IN_PROCESS:
                System.out.println("""
                        1 — перенести в подзадачи к другой задаче
                        2 — удалить
                        3 — пометить завершённой
                        0 — выйти из меню задачи""");
                scanner = new Scanner(System.in);
                if (scanner.hasNextInt()) {
                    int command = scanner.nextInt();
                    switch (command) {
                        case 1 -> {
                            System.out.println("ПЕРЕНЕСТИ В ПОДЗАДАЧИ К ДРУГОЙ ЗАДАЧЕ");
                            moveToSubtasks(lineTask);
                        }
                        case 2 -> {
                            System.out.println("УДАЛИТЬ");
                            delete(lineTask, false);
                        }
                        case 3 -> {
                            System.out.println("ПОМЕТИТЬ ЗАВЕРШЁННОЙ");
                            complete(lineTask);
                        }
                        case 0 -> {
                            messageAboutReturn();
                            return;
                        }
                        default -> warningAboutCommand();
                    }
                } else {
                    warningAboutInteger();
                }
                break;
            case DONE:
                System.out.println("""
                        1 — удалить
                        2 — вернуть задаче статус взятой на выполнение
                        0 — выйти из меню задачи""");
                scanner = new Scanner(System.in);
                if (scanner.hasNextInt()) {
                    int command = scanner.nextInt();
                    switch (command) {
                        case 1 -> {
                            System.out.println("УДАЛИТЬ");
                            delete(lineTask, false);
                        }
                        case 2 -> {
                            System.out.println("СНОВА ПОМЕТИТЬ ВЗЯТОЙ НА ВЫПОЛНЕНИЕ");
                            take(lineTask);
                        }
                        case 0 -> {
                            messageAboutReturn();
                            return;
                        }
                        default -> warningAboutCommand();
                    }
                } else {
                    warningAboutInteger();
                }
                break;
        }
    }

    public void taskMenu(BigTask bigTask) {
        System.out.println("""
                ЧТО СДЕЛАТЬ С ЭТОЙ ЗАДАЧЕЙ?
                1 — удалить вместе с подзадачами
                2 — добавить подзадачу
                0 — выйти из меню задачи""");
        Scanner scanner = new Scanner(System.in);
        if (scanner.hasNextInt()) {
            int command = scanner.nextInt();
            switch (command) {
                case 1 -> {
                    System.out.println("УДАЛИТЬ ВМЕСТЕ С ПОДЗАДАЧАМИ");
                    delete(bigTask);
                }
                case 2 -> {
                    System.out.println("ДОБАВИТЬ ПОДЗАДАЧУ");
                    createSubtask(bigTask.id);
                }
                case 0 -> messageAboutReturn();
                default -> warningAboutCommand();
            }
        } else {
            warningAboutCommand();
        }
    }

    public void taskMenu(Subtask subtask) {
        System.out.println("\nЧТО СДЕЛАТЬ С ПОДЗАДАЧЕЙ id[" + subtask.id + "]?");
        switch (subtask.status) {
            case NEW:
                System.out.println("""
                        1 — удалить
                        2 — пометить взятой на выполнение
                        3 — пометить завершённой
                        0 — выйти из меню задачи""");
                Scanner scanner = new Scanner(System.in);
                if (scanner.hasNextInt()) {
                    int command = scanner.nextInt();
                    switch (command) {
                        case 1 -> {
                            System.out.println("УДАЛИТЬ");
                            delete(subtask);
                        }
                        case 2 -> {
                            System.out.println("ПОМЕТИТЬ ВЗЯТОЙ НА ВЫПОЛНЕНИЕ");
                            take(subtask);
                        }
                        case 3 -> {
                            System.out.println("ПОМЕТИТЬ ЗАВЕРШЁННОЙ");
                            complete(subtask);
                        }
                        case 0 -> {
                            messageAboutReturn();
                            return;
                        }
                        default -> warningAboutCommand();
                    }
                } else {
                    warningAboutInteger();
                }
                break;
            case IN_PROCESS:
                System.out.println("""
                        1 — удалить
                        2 — пометить завершённой
                        0 — выйти из меню задачи""");
                scanner = new Scanner(System.in);
                if (scanner.hasNextInt()) {
                    int command = scanner.nextInt();
                    switch (command) {
                        case 1 -> {
                            System.out.println("УДАЛИТЬ");
                            delete(subtask);
                        }
                        case 2 -> {
                            System.out.println("ПОМЕТИТЬ ЗАВЕРШЁННОЙ");
                            complete(subtask);
                        }
                        case 0 -> {
                            messageAboutReturn();
                            return;
                        }
                        default -> warningAboutCommand();
                    }
                } else {
                    warningAboutCommand();
                }
                break;
            case DONE:
                System.out.println("""
                        1 — удалить
                        2 — вернуть задаче статус взятой на выполнение
                        0 — выйти из меню задачи""");
                scanner = new Scanner(System.in);
                if (scanner.hasNextInt()) {
                    int command = scanner.nextInt();
                    switch (command) {
                        case 1 -> {
                            System.out.println("УДАЛИТЬ");
                            delete(subtask);
                        }
                        case 2 -> {
                            System.out.println("СНОВА ПОМЕТИТЬ ВЗЯТОЙ НА ВЫПОЛНЕНИЕ");
                            take(subtask);
                        }
                        case 0 -> {
                            messageAboutReturn();
                            return;
                        }
                        default -> warningAboutCommand();
                    }
                } else {
                    warningAboutCommand();
                }
                break;
        }
    }

    public void createTask(TaskType taskType) {
        String title = inputTaskTitle();
        if (title.equals("0")) {
            messageAboutReturn();
            return;
        }
        String description = inputTaskDescription();
        switch (taskType) {
            case LINE_TASK -> {
                LineTask lineTask = new LineTask(idCounter, title, description, Status.NEW);
                if (bigTasks.containsValue(lineTask) || lineTasks.containsValue(lineTask)
                        || subtasks.containsValue(lineTask)) {
                    System.out.println("[!] Такая задача уже есть");
                } else {
                    lineTasks.put(lineTask.id, lineTask);
                    idCounter++;
                    lineTask.print(true);
                }
            }
            case BIG_TASK -> {
                BigTask bigTask = new BigTask(idCounter, title, description, new ArrayList<>(), Status.NEW);
                if (lineTasks.containsValue(bigTask) || bigTasks.containsValue(bigTask)
                        || subtasks.containsValue(bigTask)) {
                    System.out.println("[!] Такая задача уже есть");
                } else {
                    bigTasks.put(bigTask.id, bigTask);
                    idCounter++;
                    createSubtask(bigTask.id);
                }
            }
        }
        pause();
    }

    private void createTask(boolean isAsMainMenu) {
        System.out.println("""
                СОЗДАТЬ ЗАДАЧУ:
                1 — простую
                2 — с подзадачами""");
        System.out.println(isAsMainMenu ? "0 — выйти" : "0 — назад");
        Scanner scanner = new Scanner(System.in);
        if (scanner.hasNextInt()) {
            int command = scanner.nextInt();
            switch (command) {
                case 1:
                    System.out.println("СОЗДАТЬ ПРОСТУЮ ЗАДАЧУ");
                    createTask(TaskType.LINE_TASK);
                    break;
                case 2:
                    System.out.println("СОЗДАТЬ ЗАДАЧУ С ПОДЗАДАЧАМИ");
                    createTask(TaskType.BIG_TASK);
                    break;
                case 0:
                    if (isAsMainMenu) {
                        System.out.println("Программа завершена");
                        System.exit(0);
                    } else {
                        messageAboutReturn();
                        return;
                    }
                default:
                    warningAboutCommand();
            }
        } else {
            warningAboutCommand();
            pause();
        }
    }

    private void createSubtask(int parentID) {
        BigTask bigTask;
        if (lineTasks.containsKey(parentID)) {
            LineTask lineTask = lineTasks.get(parentID);
            bigTask = new BigTask(lineTask.id, lineTask.title, lineTask.description, new ArrayList<>(), lineTask.status);
            bigTasks.put(bigTask.id, bigTask);
            lineTasks.remove(lineTask.id, lineTask);
        } else {
            bigTask = bigTasks.get(parentID);
        }
        bigTask.print(false, true, false, false);
        System.out.println("  ↳ добавить сюда подзадачу\n\t\t↑");
        String title = inputTaskTitle();
        if (title.equals("0")) {
            messageAboutReturn();
            checkTypeOf(bigTask);
            return;
        }
        String description = inputTaskDescription();
        Subtask subtask = new Subtask(parentID, idCounter, title, description, Status.NEW);
        if (bigTask.subtasks.contains(subtask)) {
            System.out.println("[!] Такая задача уже есть среди подзадач задачи");
            checkTypeOf(bigTask);
        } else if (bigTasks.containsValue(subtask)) {
            System.out.println("[!] Такая задача уже есть среди задач с подзадачами");
            checkTypeOf(bigTask);
        } else if (lineTasks.containsValue(subtask)) {
            System.out.println("[!] Такая задача уже есть среди обычных задач.");
            checkTypeOf(bigTask);
        } else {
            subtasks.put(subtask.id, subtask);
            idCounter++;
            bigTask.subtasks.add(subtask);
            bigTask.print(false, true, true, true);
        }
    }

    public void moveToSubtasks(LineTask lineTask) {
        lineTask.print(false);
        System.out.println("↓");
        System.out.println("В подзадачи к какой задаче добавить:");
        printPossibleParentTasks(lineTask);
        System.out.println("↑\nВведите номер id[] задачи, в подзадачи к которой\n" +
                "добавить задачу id[" + lineTask.id + "] «" + lineTask.title + "»,\nили 0, чтобы вернуться назад:");
        Scanner scanner = new Scanner(System.in);
        if (scanner.hasNextInt()) {
            int id = scanner.nextInt();
            if (id == 0) {
                messageAboutReturn();
                return;
            } else if (id == lineTask.id) {
                System.out.println("[!] Нельзя добавить задачу в подзадачи к себе самой");
            } else if (subtasks.containsKey(id)) {
                System.out.println("[!] Это id[] подзадачи, ожидается ввод id[] только из предложенных");
            } else if ((!lineTasks.containsKey(id) && !bigTasks.containsKey(id))) {
                System.out.println("[!] Ожидается ввод id[] только из предложенных, " +
                        "к другим id[] нельзя добавить подзадачу");
            }
            if (lineTasks.containsKey(id) && lineTask.id != id) {
                LineTask parentTask = lineTasks.get(id);
                BigTask bigTask =
                        new BigTask(parentTask.id, parentTask.title, parentTask.description,
                                new ArrayList<>(), parentTask.status);
                Subtask subtask = new Subtask(bigTask.id, lineTask.id, lineTask.title,
                        lineTask.description, lineTask.status);
                subtasks.put(subtask.id, subtask);
                bigTask.subtasks.add(subtask);
                lineTasks.remove(parentTask.id, parentTask);
                lineTasks.remove(lineTask.id, lineTask);
                bigTasks.put(bigTask.id, bigTask);
                System.out.println("Задача перенесена в подзадачи");
                refreshStatusOf(bigTask);
                bigTask.print(false, true, true, true);
            } else if (bigTasks.containsKey(id)) {
                BigTask bigTask = bigTasks.get(id);
                Subtask subtask = new Subtask(bigTask.id, lineTask.id, lineTask.title,
                        lineTask.description, lineTask.status);
                subtasks.put(subtask.id, subtask);
                bigTask.subtasks.add(subtask);
                lineTasks.remove(lineTask.id, lineTask);
                bigTasks.put(bigTask.id, bigTask);
                System.out.println("Задача перенесена в подзадачи");
                bigTask.print(false, true, true, true);
            }
        } else {
            warningAboutInteger();
        }
    }

    private void delete(LineTask lineTask, boolean isAuto) {
        if (isAuto) {
            delete(lineTask);
        } else {
            System.out.println("Задача id[" + lineTask.id + "] «" + lineTask.title + "» удалена");
            lineTasks.remove(lineTask.id, lineTask);
        }
    }

    public void delete(LineTask lineTask) {
        while (true) {
            System.out.println("Удалить задачу id[" + lineTask.id + "] «" + lineTask.title + "»?\n" +
                    "1 — да, 0 — нет");
            Scanner scanner = new Scanner(System.in);
            if (scanner.hasNextInt()) {
                int command = scanner.nextInt();
                switch (command) {
                    case 1 -> {
                        lineTasks.remove(lineTask.id, lineTask);
                        return;
                    }
                    case 0 -> {
                        messageAboutReturn();
                        return;
                    }
                    default -> warningAboutCommand();
                }
            } else {
                warningAboutInteger();
            }
        }
    }

    public void delete(BigTask bigTask) {
        System.out.println("Удалить задачу id[" + bigTask.id + "] «" + bigTask.title + "» " +
                " вместе с подзадачами?\n" +
                "1 — да, 0 — нет");
        Scanner scanner = new Scanner(System.in);
        if (scanner.hasNextInt()) {
            int command = scanner.nextInt();
            switch (command) {
                case 1 -> {
                    for (Subtask subtask : bigTask.subtasks) {
                        subtasks.remove(subtask.id, subtask);
                    }
                    bigTask.subtasks.clear();
                    System.out.println("Задача id[" + bigTask.id + "] «" + bigTask.title +
                            "» окончательно удалена со своими подзадачами");
                    bigTasks.remove(bigTask.id, bigTask);
                }
                case 0 -> messageAboutReturn();
                default -> warningAboutCommand();
            }
        } else {
            warningAboutInteger();
        }
    }

    public void delete(Subtask subtask) {
        BigTask bigTask = bigTasks.get(subtask.parentID);
        while (true) {
            bigTask.print(false, false, false, true);
            for (Subtask currentSubtask : bigTask.subtasks) {
                if (currentSubtask.equals(subtask)) {
                    System.out.println("→ ↳id[" + subtask.id + "] " + subtask.title +
                            (subtask.description.isEmpty() ? "" : " (см.—детали)") +
                            " | " + subtask.status);
                } else {
                    System.out.print("  ");
                    currentSubtask.print(false);
                }
            }
            System.out.println("\nУдалить подзадачу id[" + subtask.id + "]?\n" +
                    "1 — да, 0 — нет");
            Scanner scanner = new Scanner(System.in);
            if (scanner.hasNextInt()) {
                int command = scanner.nextInt();
                switch (command) {
                    case 1 -> {
                        System.out.println("Подзадача ↳id[" + subtask.id + "] «" + subtask.title + "» удалена");
                        subtasks.remove(subtask.id, subtask);
                        bigTask.subtasks.remove(subtask);
                        checkTypeOf(bigTask);
                        return;
                    }
                    case 0 -> {
                        messageAboutReturn();
                        return;
                    }
                    default -> warningAboutCommand();
                }
            } else {
                warningAboutInteger();
            }
        }
    }

    public void deleteAllTasks() {
        System.out.println("Удалить все задачи без возможности восстановления?\n" +
                "1 — да, 0 — нет");
        Scanner scanner = new Scanner(System.in);
        if (scanner.hasNextInt()) {
            int command = scanner.nextInt();
            switch (command) {
                case 1:
                    lineTasks.clear();
                    for (BigTask bigTask : bigTasks.values()) {
                        bigTask.subtasks.clear();
                    }
                    bigTasks.clear();
                    subtasks.clear();
                    idCounter = 1;
                    System.out.println("Все задачи удалены");
                    break;
                case 0:
                    messageAboutReturn();
                default:
                    warningAboutCommand();
            }
        } else {
            warningAboutInteger();
        }
    }

    public void printALlTasks() {
        System.out.println("ЗАПЛАНИРОВАННЫЕ");
        printTasksOfStatus(Status.NEW);
        System.out.println("ВЗЯТЫЕ НА ВЫПОЛНЕНИЕ");
        printTasksOfStatus(Status.IN_PROCESS);
        System.out.println("ЗАВЕРШЁННЫЕ");
        printTasksOfStatus(Status.DONE);
    }

    private void printTasksOfStatus(Status status) {
        boolean isExistLineTasksOfStatus = false;
        boolean isExistBigTasksOfStatus = false;
        for (LineTask lineTask : lineTasks.values()) {
            if (lineTask.status.equals(status)) {
                lineTask.print(false);
                isExistLineTasksOfStatus = true;
            }
        }
        System.out.print(isExistLineTasksOfStatus ? "\n" : "");
        for (BigTask bigTask : bigTasks.values()) {
            if (bigTask.status.equals(status)) {
                bigTask.print(false, true, false, true);
                isExistBigTasksOfStatus = true;
            }
        }
        if (!isExistLineTasksOfStatus && !isExistBigTasksOfStatus) {
            System.out.println("Нет задач такого типа\n");
        }
    }

    private void printPossibleParentTasks(Task task) {
        if (task instanceof LineTask) {
            for (LineTask lineTask : lineTasks.values()) {
                if (!lineTask.equals(task)) {
                    System.out.print("? ");
                    lineTask.print(false);
                }
            }
            for (BigTask bigTask : bigTasks.values()) {
                System.out.print("? ");
                bigTask.print(false);
            }
        }
    }

    public void take(LineTask lineTask) {
        Status initialStatus = lineTask.status;
        lineTask.status = Status.IN_PROCESS;
        System.out.println("Статус задачи id[" + lineTask.id + "] «" + lineTask.title + "» изменён:\n" +
                initialStatus + " → " + lineTask.status);
    }

    public void take(Subtask subtask) {
        Status initialStatus = subtask.status;
        if (initialStatus.equals(Status.DONE)) {
            while (true) {
                System.out.println("Подзадача id[" + subtask.id + "] «" + subtask.title + "» завершена.\n" +
                        "Снова её пометить взятой на выполнение?\n" +
                        "1 — да, 0 — нет");
                Scanner scanner = new Scanner(System.in);
                if (scanner.hasNextInt()) {
                    int command = scanner.nextInt();
                    if (command == 1) {
                        break;
                    } else if (command == 0) {
                        messageAboutReturn();
                        return;
                    } else {
                        warningAboutCommand();
                    }
                } else {
                    warningAboutInteger();
                }
            }
        }
        subtask.status = Status.IN_PROCESS;
        BigTask bigTask = bigTasks.get(subtask.parentID);
        refreshStatusOf(bigTask);
        System.out.println("Статус подзадачи ↳id[" + subtask.id + "] «" + subtask.title + "» изменён:\n" +
                initialStatus + " → " + subtask.status);
    }

    public void refreshStatusOf(BigTask bigTask) {
        int numberOfNewSubtasks = 0;
        int numberOfSubtasksInProcess = 0;
        int numberOfCompletedSubtasks = 0;
        for (Subtask subtask : bigTask.subtasks) {
            switch (subtask.status) {
                case NEW -> numberOfNewSubtasks++;
                case IN_PROCESS -> numberOfSubtasksInProcess++;
                case DONE -> numberOfCompletedSubtasks++;
            }
        }
        if (numberOfCompletedSubtasks == bigTask.subtasks.size()) {
            bigTask.status = Status.DONE;
        } else if (numberOfSubtasksInProcess > 0) {
            bigTask.status = Status.IN_PROCESS;
        } else if (numberOfNewSubtasks > 0 && numberOfSubtasksInProcess == 0) {
            bigTask.status = Status.NEW;
        }
    }

    public void checkTypeOf(BigTask bigTask) {
        if (bigTask.subtasks.isEmpty()) {
            LineTask lineTask = new LineTask(bigTask.id, bigTask.title, bigTask.description, bigTask.status);
            lineTasks.put(lineTask.id, lineTask);
            bigTasks.remove(bigTask.id, bigTask);
        }
    }

    public void complete(LineTask lineTask) {
        Status initialStatus = lineTask.status;
        if (initialStatus.equals(Status.NEW)) {
            while (true) {
                System.out.println("Задача id[" + lineTask.id + "] «" + lineTask.title + "» ещё не была на выполнении.\n" +
                        "Всё равно пометить завершённой?\n" +
                        "1 — да, 0 — нет, 2 — пометить взятой на выполнение");
                Scanner scanner = new Scanner(System.in);
                if (scanner.hasNextInt()) {
                    int command = scanner.nextInt();
                    if (command == 1) {
                        break;
                    } else if (command == 2) {
                        take(lineTask);
                        return;
                    } else if (command == 0) {
                        messageAboutReturn();
                        return;
                    } else {
                        warningAboutCommand();
                    }
                } else {
                    warningAboutInteger();
                }
            }
        }
        lineTask.status = Status.DONE;
        System.out.println("Статус задачи id[" + lineTask.id + "] «" + lineTask.title + "» изменён:\n" +
                initialStatus + " → " + lineTask.status);
    }

    public void complete(Subtask subtask) {
        Status initialStatus = subtask.status;
        if (initialStatus.equals(Status.NEW)) {
            while (true) {
                System.out.println("Подзадача ↳id[" + subtask.id + "] «" + subtask.title + "» ещё не была на выполнении.\n" +
                        "Всё равно пометить завершённой?\n" +
                        "1 — да, 0 — нет");
                Scanner scanner = new Scanner(System.in);
                if (scanner.hasNextInt()) {
                    int command = scanner.nextInt();
                    if (command == 1) {
                        break;
                    } else if (command == 0) {
                        messageAboutReturn();
                        return;
                    } else {
                        warningAboutCommand();
                    }
                } else {
                    warningAboutInteger();
                }
            }
        }
        subtask.status = Status.DONE;
        System.out.println("Статус подзадачи ↳id[" + subtask.id + "] «" + subtask.title + "» изменён:\n" +
                initialStatus + " → " + subtask.status);
        BigTask bigTask = bigTasks.get(subtask.parentID);
        refreshStatusOf(bigTask);
    }

    private static String inputTaskTitle() {
        while (true) {
            System.out.print("Заголовок: ");
            Scanner scanner = new Scanner(System.in);
            String title = scanner.nextLine();
            if (title.equals("")) {
                System.out.println("[!] Ожидается ввод заголовка, или введите 0 для возврата");
            } else if (title.equals("0")) {
                return "0";
            } else {
                return title;
            }
        }
    }

    private static String inputTaskDescription() {
        System.out.print("Описание (если нужно): ");
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }

    public void settings() {
        while (true) {
            System.out.println("""
                    НАСТРОЙКИ МЕНЕДЖЕРА ЗАДАЧ
                    1 — удалить все задачи
                    2 — история просмотра задач
                    0 — назад""");
            Scanner scanner = new Scanner(System.in);
            if (scanner.hasNextInt()) {
                int command = scanner.nextInt();
                switch (command) {
                    case 1 -> {
                        System.out.println("УДАЛИТЬ ВСЕ ЗАДАЧИ");
                        deleteAllTasks();
                        return;
                    }
                    case 2 -> {
                        System.out.println("ИСТОРИЯ ПРОСМОТРА ЗАДАЧ");
                        printHistory();
                        return;
                    }
                    case 0 -> {
                        messageAboutReturn();
                        return;
                    }
                    default -> warningAboutCommand();
                }
            } else {
                warningAboutInteger();
            }
        }
    }

    public void addToHistory(int id) {
        if (lineTasks.containsKey(id)) {
            LineTask lineTask = lineTasks.get(id);
            history.add(lineTask);
        }
        if (bigTasks.containsKey(id)) {
            BigTask bigTask = bigTasks.get(id);
            history.add(bigTask);
        }
        if (subtasks.containsKey(id)) {
            Subtask subtask = subtasks.get(id);
            history.add(subtask);
        }
    }

    public void printHistory() {
        if (history.isEmpty()) {
            System.out.println("[!] История пуста");
        } else {
            System.out.println("Последние 10 просмотренных задач (от старых к новым):");
            if (history.size() < 10) {
                for (Task task : history) {
                    determineTaskTypeAndStartPrint(task);
                }
            } else {
                for (int i = history.size() - 10; i < history.size(); i++) {
                    Task task = history.get(i);
                    determineTaskTypeAndStartPrint(task);
                }
            }
        }
        pause();
    }

    private void determineTaskTypeAndStartPrint(Task task) {
        if (task instanceof LineTask lineTask) {
            lineTask.print(false);
        }
        if (task instanceof BigTask bigTask) {
            bigTask.print(false, false, false, false);
        }
        if (task instanceof Subtask subtask) {
            subtask.print(false);
        }
    }

    private void pause() {
        System.out.println("Нажмите любую клавишу, чтобы продолжить");
        Scanner scanner = new Scanner(System.in);
        String command = scanner.nextLine();
    }

    private void warningAboutInteger() {
        System.out.println("[!] Ожидается ввод целого числа");
    }

    private void warningAboutCommand() {
        System.out.println("[!] Ожидается команда из предложенных");
    }

    private void messageAboutReturn() {
        System.out.println("↓ возврат");
    }
}