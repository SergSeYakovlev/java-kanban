package ru.yandex.kanban;

import ru.yandex.kanban.model.Epic;
import ru.yandex.kanban.model.Status;
import ru.yandex.kanban.model.Subtask;
import ru.yandex.kanban.model.Task;
import ru.yandex.kanban.service.Managers;
import ru.yandex.kanban.service.TaskManager;

public class Main {

    public static void main(String[] args) {

        TaskManager taskManager = Managers.getDefault();

        Task task1 = new Task("1я таска", "Описание 1й таски");
        taskManager.addTask(task1);
        taskManager.printHistory();
        Task task2 = new Task("2я таска", "Описание 2й таски");
        taskManager.addTask(task2);
        taskManager.printHistory();
        Task task3 = new Task("3я таска", "Описание 3й таски");
        taskManager.addTask(task3);
        taskManager.printHistory();

        Epic epic1 = new Epic("Первый эпик", "Описание первого эпика");
        taskManager.addEpic(epic1);
        taskManager.printHistory();
        Epic epic2 = new Epic("Второй эпик", "Описание второго эпика");
        taskManager.addEpic(epic2);
        taskManager.printHistory();
        Subtask subtask1 = new Subtask("Первая подзадача первого эпика",
                "Описание первой подзадачи первого эпика");
        taskManager.addSubtask(subtask1, epic1);
        taskManager.printHistory();
        Subtask subtask2 = new Subtask("вторая подзадача первого эпика",
                "Описание второй подзадачи первого эпика");
        taskManager.addSubtask(subtask2, epic1);
        taskManager.printHistory();
        Subtask subtask3 = new Subtask("Третья подзадача первого эпика",
                "Описание третьей подзадачи первого эпика");
        taskManager.addSubtask(subtask3, epic1);
        taskManager.printHistory();
        taskManager.printAllTasks("Создано...");

        Task task4 = new Task("4я таска", "Описание 4й таски");
        taskManager.addTask(task4);
        taskManager.printHistory();
        Task task5 = new Task("5я таска", "Описание 5й таски");
        taskManager.addTask(task5);
        taskManager.printHistory();
        Task task6 = new Task("6я таска", "Описание 6й таски");
        taskManager.addTask(task6);
        taskManager.printHistory();

        taskManager.getTask(1);
        taskManager.printHistory();
        taskManager.getTask(2);
        taskManager.printHistory();

        taskManager.getEpic(4);
        taskManager.printHistory();
        taskManager.getEpic(5);
        taskManager.printHistory();

        taskManager.getSubtask(6);
        taskManager.printHistory();
        taskManager.getSubtask(7);
        taskManager.printHistory();
        taskManager.getSubtask(8);
        taskManager.printHistory();

        taskManager.getTask(3);
        taskManager.printHistory();
        taskManager.getTask(9);
        taskManager.printHistory();
        taskManager.getTask(10);
        taskManager.printHistory();
        taskManager.getTask(11);
        taskManager.printHistory();

        taskManager.setSubtaskStatus(subtask1, Status.DONE);
        taskManager.printHistory();
        taskManager.setSubtaskStatus(subtask2, Status.IN_PROGRESS);
        taskManager.printHistory();
        taskManager.setSubtaskStatus(subtask3, Status.DONE);
        taskManager.printHistory();
        taskManager.setTaskStatus(task1, Status.IN_PROGRESS);
        taskManager.printAllTasks("Изменены статусы...");

        taskManager.deleteSubtask(subtask1);
        taskManager.printHistory();
        taskManager.deleteEpic(epic1);
        taskManager.printHistory();
        taskManager.deleteTask(task1);
        taskManager.printHistory();
        taskManager.printAllTasks("Выполнены удаления...");
    }
}