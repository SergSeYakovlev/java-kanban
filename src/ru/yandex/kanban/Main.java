package ru.yandex.kanban;

import ru.yandex.kanban.model.Epic;
import ru.yandex.kanban.model.Status;
import ru.yandex.kanban.model.Subtask;
import ru.yandex.kanban.model.Task;
import ru.yandex.kanban.service.HistoryManager;
import ru.yandex.kanban.service.Managers;
import ru.yandex.kanban.service.TaskManager;

public class Main {

    public static void main(String[] args) {

        TaskManager taskManager = Managers.getDefault();
        HistoryManager historyManager = taskManager.getHistoryManager();

        Task task1 = new Task("Первая таска", "Описание первой таски");
        taskManager.addTask(task1);

        historyManager.printHistory();

        Task task2 = new Task("Вторая таска", "Описание второй таски");
        taskManager.addTask(task2);

        historyManager.printHistory();

        Epic epic1 = new Epic("Первый эпик", "Описание первого эпика");
        taskManager.addEpic(epic1);

        historyManager.printHistory();

        Epic epic2 = new Epic("Второй эпик", "Описание второго эпика");
        taskManager.addEpic(epic2);

        historyManager.printHistory();

        Subtask subtask1 = new Subtask("Первая подзадача первого эпика",
                "Описание первой подзадачи первого эпика");
        taskManager.addSubtask(subtask1, epic1);

        historyManager.printHistory();

        Subtask subtask2 = new Subtask("Первая подзадача второго эпика",
                "Описание первой подзадачи второго эпика");
        taskManager.addSubtask(subtask2, epic2);

        historyManager.printHistory();

        Subtask subtask3 = new Subtask("Вторая подзадача второго эпика",
                "Описание второй подзадачи второго эпика");
        taskManager.addSubtask(subtask3, epic2);

        historyManager.printHistory();

        System.out.println("Создано...");

        System.out.println(taskManager.getTasks());
        System.out.println(taskManager.getEpics());
        System.out.println(taskManager.getSubtasks());

        taskManager.setSubtaskStatus(subtask1, Status.DONE);

        historyManager.printHistory();

        taskManager.setSubtaskStatus(subtask2, Status.IN_PROGRESS);

        historyManager.printHistory();

        taskManager.setSubtaskStatus(subtask3, Status.DONE);

        historyManager.printHistory();

        taskManager.setTaskStatus(task1, Status.IN_PROGRESS);

        System.out.println("Изменены статусы...");

        System.out.println(taskManager.getTasks());
        System.out.println(taskManager.getEpics());
        System.out.println(taskManager.getSubtasks());

        taskManager.deleteSubtask(subtask1);

        historyManager.printHistory();

        taskManager.deleteEpic(epic2);

        historyManager.printHistory();

        taskManager.deleteTask(task1);
        System.out.println("Выполнены удаления...");

        System.out.println(taskManager.getTasks());
        System.out.println(taskManager.getEpics());
        System.out.println(taskManager.getSubtasks());
    }
}