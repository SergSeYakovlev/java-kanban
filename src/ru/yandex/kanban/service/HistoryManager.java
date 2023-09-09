package ru.yandex.kanban.service;

import ru.yandex.kanban.model.Task;

import java.util.List;

public interface HistoryManager {
    void add(Task task);

    List<Task> getHistory();

    void printHistory();
}