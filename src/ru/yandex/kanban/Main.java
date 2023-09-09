package ru.yandex.kanban;

import ru.yandex.kanban.model.Epic;
import ru.yandex.kanban.model.Status;
import ru.yandex.kanban.model.Subtask;
import ru.yandex.kanban.model.Task;
import ru.yandex.kanban.service.Manager;

public class Main {

    public static void main(String[] args) {

        Manager manager = new Manager();

        Task task1 = new Task("Первая таска", "Описание первой таски");
        manager.addTask(task1);
        Task task2 = new Task("Вторая таска", "Описание второй таски");
        manager.addTask(task2);
        Epic epic1 = new Epic("Первый эпик", "Описание первого эпика");
        manager.addEpic(epic1);
        Epic epic2 = new Epic("Второй эпик", "Описание второго эпика");
        manager.addEpic(epic2);
        Subtask subtask1 = new Subtask("Первая подзадача первого эпика",
                "Описание первой подзадачи первого эпика");
        manager.addSubtask(subtask1, epic1);
        Subtask subtask2 = new Subtask("Первая подзадача второго эпика",
                "Описание первой подзадачи второго эпика");
        manager.addSubtask(subtask2, epic2);
        Subtask subtask3 = new Subtask("Вторая подзадача второго эпика",
                "Описание второй подзадачи второго эпика");
        manager.addSubtask(subtask3, epic2);
        System.out.println("Создано...");

        System.out.println(manager.getTasks());
        System.out.println(manager.getEpics());
        System.out.println(manager.getSubtasks());

        manager.setSubtaskStatus(subtask1, Status.DONE);
        manager.setSubtaskStatus(subtask2, Status.IN_PROGRESS);
        manager.setTaskStatus(task1, Status.IN_PROGRESS);

        System.out.println("Изменены статусы...");

        System.out.println(manager.getTasks());
        System.out.println(manager.getEpics());
        System.out.println(manager.getSubtasks());

        manager.deleteSubtask(subtask1);
        manager.deleteEpic(epic2);
        manager.deleteTask(task1);
        System.out.println("Выполнены удаления...");

        System.out.println(manager.getTasks());
        System.out.println(manager.getEpics());
        System.out.println(manager.getSubtasks());
    }
}