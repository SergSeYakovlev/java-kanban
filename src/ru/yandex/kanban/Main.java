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

        Task task1 = new Task("Первая таска", "Описание первой таски");
        taskManager.addTask(task1);
        taskManager.printHistory();
        Task task2 = new Task("Вторая таска", "Описание второй таски");
        taskManager.addTask(task2);
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
        Subtask subtask2 = new Subtask("Первая подзадача второго эпика",
                "Описание первой подзадачи второго эпика");
        taskManager.addSubtask(subtask2, epic2);
        taskManager.printHistory();
        Subtask subtask3 = new Subtask("Вторая подзадача второго эпика",
                "Описание второй подзадачи второго эпика");
        taskManager.addSubtask(subtask3, epic2);
        taskManager.printHistory();
        taskManager.printAllTasks("Создано...");

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
        taskManager.deleteEpic(epic2);
        taskManager.printHistory();
        taskManager.deleteTask(task1);
        taskManager.printAllTasks("Выполнены удаления...");
    }
}