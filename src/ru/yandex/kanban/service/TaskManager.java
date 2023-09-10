package ru.yandex.kanban.service;

import ru.yandex.kanban.model.Epic;
import ru.yandex.kanban.model.Status;
import ru.yandex.kanban.model.Subtask;
import ru.yandex.kanban.model.Task;

import java.util.List;
import java.util.Map;

public interface TaskManager {
    int getNextId();

    void deleteAllSubtasks();

    void deleteAllTasks();

    void deleteAllEpics();

    Epic getEpic(int id);

    Subtask getSubtask(int id);

    Map<Integer, Task> getTasks();

    Map<Integer, Epic> getEpics();

    Map<Integer, Subtask> getSubtasks();

    void deleteTask(Task task);

    void deleteSubtask(Subtask subtask);

    void deleteEpic(Epic epic);

    void addTask(Task task);

    void addEpic(Epic epic);

    void addSubtask(Subtask subtask, Epic epic);

    void setTaskStatus(Task task, Status status);

    void setSubtaskStatus(Subtask subtask, Status status);

    void updateSubtask(int oldTaskId, Subtask newSubTask);

    void updateEpic(int oldEpicId, Epic newEpic);

    void updateTask(int oldTaskId, Task newTask);

    List<Task> getHistory();

    void printHistory();
}