package ru.yandex.kanban.service;

import ru.yandex.kanban.model.Epic;
import ru.yandex.kanban.model.Status;
import ru.yandex.kanban.model.Subtask;
import ru.yandex.kanban.model.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class InMemoryTaskManager implements TaskManager {
    protected int currentId;
    protected Map<Integer, Task> tasks = new HashMap<>();
    protected Map<Integer, Epic> epics = new HashMap<>();
    protected Map<Integer, Subtask> subtasks = new HashMap<>();

    protected HistoryManager historyManager = Managers.getDefaultHistory();

    @Override
    public int getNextId() {
        currentId++;
        return currentId;
    }

    @Override
    public HistoryManager getHistoryManager() {
        return historyManager;
    }

    @Override
    public void deleteAllSubtasks() {
        HashMap<Integer, Epic> updatingEpics = new HashMap<>();
        for (Subtask subtask : subtasks.values()) {
            Epic epic = getEpic(subtask.getEpicId());
            updatingEpics.put(epic.getId(), epic);
        }
        subtasks.clear();
        for (Epic epic : updatingEpics.values()) {
            epic.setSubtasksIds(new ArrayList<>());
            epic.setStatus(Status.NEW);
        }
    }

    @Override
    public void deleteAllTasks() {
        tasks.clear();
    }

    @Override
    public void deleteAllEpics() {
        epics.clear();
        subtasks.clear();
    }

    @Override
    public Epic getEpic(int id) {
        Epic epic = epics.getOrDefault(id, new Epic("", ""));
        historyManager.add(epic);
        return epic;
    }

    @Override
    public Subtask getSubtask(int id) {
        Subtask subtask = subtasks.getOrDefault(id, new Subtask("", ""));
        historyManager.add(subtask);
        return subtask;
    }


    @Override
    public Map<Integer, Task> getTasks() {
        return tasks;
    }

    @Override
    public Map<Integer, Epic> getEpics() {
        return epics;
    }

    @Override
    public Map<Integer, Subtask> getSubtasks() {
        return subtasks;
    }

    @Override
    public void deleteTask(Task task) {
        deleteTaskById(task.getId());
    }

    @Override
    public void deleteSubtask(Subtask subtask) {
        deleteSubtaskById(subtask.getId());
    }

    @Override
    public void deleteEpic(Epic epic) {
        deleteEpicById(epic.getId());
    }

    private void deleteTaskById(int id) {
        tasks.remove(id);
    }

    private void deleteEpicById(int id) {
        ArrayList<Integer> deletingList = new ArrayList<>(getEpic(id).getSubtasksIds());
        for (Integer subtaskId : deletingList) {
            deleteSubtaskById(subtaskId);
        }
        epics.remove(id);
    }

    private void deleteSubtaskById(int id) {
        Epic epic = getEpic(subtasks.getOrDefault(id, new Subtask("", "")).getEpicId());
        int index = epic.getSubtasksIds().indexOf(id);
        if (index >= 0) {
            epic.getSubtasksIds().remove(index);
        }
        epic.setStatus(defineEpicStatus(epic));
        subtasks.remove(id);
    }

    @Override
    public void addTask(Task task) {
        task.setId(getNextId());
        tasks.put(task.getId(), task);
    }

    @Override
    public void addEpic(Epic epic) {
        epic.setId(getNextId());
        epics.put(epic.getId(), epic);
    }

    @Override
    public void addSubtask(Subtask subtask, Epic epic) {
        subtask.setId(getNextId());
        subtasks.put(subtask.getId(), subtask);
        subtask.setEpicId(epic.getId());
        epic.getSubtasksIds().add(subtask.getId());
        epic.setStatus(defineEpicStatus(epic));
    }

    @Override
    public void setTaskStatus(Task task, Status status) {
        task.setStatus(status);
    }

    @Override
    public void setSubtaskStatus(Subtask subtask, Status status) {
        subtask.setStatus(status);
        Epic epic = getEpic(subtask.getEpicId());
        epic.setStatus(defineEpicStatus(epic));
    }

    private Status defineEpicStatus(Epic epic) {
        ArrayList<Integer> newStatusTasks = new ArrayList<>();
        ArrayList<Integer> doneStatusTasks = new ArrayList<>();

        ArrayList<Integer> subtasksByEpic = new ArrayList<>(epic.getSubtasksIds());

        for (Integer id : subtasksByEpic) {
            Status status = getSubtask(id).getStatus();
            if (status == Status.IN_PROGRESS) {
                return Status.IN_PROGRESS;
            }
            if (status == Status.DONE) {
                doneStatusTasks.add(id);
            }
            if (status == Status.NEW) {
                newStatusTasks.add(id);
            }
        }

        if (newStatusTasks.size() == epic.getSubtasksIds().size()) {
            return Status.NEW;
        }
        if (doneStatusTasks.size() == epic.getSubtasksIds().size()) {
            return Status.DONE;
        }

        return Status.IN_PROGRESS;
    }

    @Override
    public void updateSubtask(int oldTaskId, Subtask newSubTask) {
        newSubTask.setId(oldTaskId);
        subtasks.put(oldTaskId, newSubTask);
        Epic epic = getEpic(newSubTask.getId());
        epic.setStatus(defineEpicStatus(epic));
    }

    @Override
    public void updateEpic(int oldEpicId, Epic newEpic) {
        newEpic.setId(oldEpicId);
        epics.put(oldEpicId, newEpic);
        newEpic.setStatus(defineEpicStatus(newEpic));
    }

    @Override
    public void updateTask(int oldTaskId, Task newTask) {
        tasks.put(oldTaskId, newTask);
    }
}