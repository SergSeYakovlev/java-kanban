package ru.yandex.kanban.service;

import ru.yandex.kanban.model.Task;

import java.util.List;

public class InMemoryHistoryManager implements HistoryManager {
    private final CustomLinkedList<Task> history = new CustomLinkedList<>();

    @Override
    public List<Task> getHistory() {
        return history.getTasks();
    }

    @Override
    public void add(Task task) {
        history.add(task.getId(), task);
        int delta = history.size() - 10;
        for (int i = 0; i < delta; i++) {
            history.remove(history.getHead().getData().getId());
        }
    }

    @Override
    public void printHistory() {
        System.out.println("История, размер: " + history.size());
        System.out.println(getHistory());
    }

    @Override
    public void remove(int id) {
        history.remove(id);
    }
}