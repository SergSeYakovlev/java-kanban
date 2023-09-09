package ru.yandex.kanban.service;

import ru.yandex.kanban.model.Task;

import java.util.LinkedList;
import java.util.List;

public class InMemoryHistoryManager implements HistoryManager {
    protected List<Task> history = new LinkedList<>();

    @Override
    public List<Task> getHistory() {
        return history;
    }

    @Override
    public void add(Task task) {
        history.add(task);
        int delta = history.size() - 10;
        if (delta > 0) {
            history.subList(0, delta).clear();
        }
    }

    @Override
    public void printHistory() {
        System.out.print(history.size());
        System.out.println(history);
    }
}