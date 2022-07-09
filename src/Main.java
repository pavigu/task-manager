import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        InMemoryTaskManager inMemoryTaskManager = new InMemoryTaskManager();
        fillTestData(inMemoryTaskManager); // test data
        inMemoryTaskManager.start();
    }

    private static void fillTestData(InMemoryTaskManager inMemoryTaskManager) {
        inMemoryTaskManager.lineTasks.put(1, new LineTask(1, "Отправить презентацию Святославу",
                "@svyat_svyat_svyat", Status.NEW));
        inMemoryTaskManager.lineTasks.put(2, new LineTask(2, "Исправить баг с изменением пароля",
                "", Status.NEW));
        inMemoryTaskManager.lineTasks.put(3, new LineTask(3, "Созвониться по видео с новенькими в команде",
                "", Status.NEW));
        inMemoryTaskManager.bigTasks.put(4, new BigTask(4, "Подготовить ТЗ", "",
                new ArrayList<>(), Status.NEW));
        BigTask bigTask = inMemoryTaskManager.bigTasks.get(4);
        Subtask subtask = new Subtask(4, 5, "Написать текст ТЗ",
                "Что сделать, требования, ресурсы, в каком виде нужен результат", Status.NEW);
        bigTask.subtasks.add(subtask);
        inMemoryTaskManager.subtasks.put(subtask.id, subtask);
        subtask = new Subtask(4, 6, "Собрать ресурсы в помощь", "Тексты, дизайн, референсы",
                Status.NEW);
        bigTask.subtasks.add(subtask);
        inMemoryTaskManager.subtasks.put(subtask.id, subtask);
        inMemoryTaskManager.bigTasks.put(7, new BigTask(7, "Создать страницу продукта", "",
                new ArrayList<>(), Status.NEW));
        bigTask = inMemoryTaskManager.bigTasks.get(7);
        subtask = new Subtask(7, 8, "Создать макет страницы", "", Status.NEW);
        bigTask.subtasks.add(subtask);
        inMemoryTaskManager.subtasks.put(subtask.id, subtask);
        subtask = new Subtask(7, 9, "Подготовить тексты и изображения", "", Status.NEW);
        bigTask.subtasks.add(subtask);
        inMemoryTaskManager.subtasks.put(subtask.id, subtask);
        subtask = new Subtask(7, 10, "Сверстать страницу", "", Status.NEW);
        bigTask.subtasks.add(subtask);
        inMemoryTaskManager.subtasks.put(subtask.id, subtask);
        inMemoryTaskManager.idCounter = 13;
    }
}