package ru.yandex.kanban.service;

import ru.yandex.kanban.model.Epic;
import ru.yandex.kanban.model.Status;
import ru.yandex.kanban.model.Subtask;
import ru.yandex.kanban.model.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Manager {
    protected int currentId;
    protected Map<Integer, Task> tasks = new HashMap<>();
    protected Map<Integer, Epic> epics = new HashMap<>();
    protected Map<Integer, Subtask> subtasks = new HashMap<>();

    public int getNextId() {
        currentId++;
        return currentId;
    }

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

    public void deleteAllTasks() {
        tasks.clear();
    }

    public void deleteAllEpics() {
        epics.clear();
        subtasks.clear();
    }

    public Epic getEpic(int id) {
        return epics.getOrDefault(id, new Epic("", " "));
    }

    public Subtask getSubtask(int id) {
        return subtasks.getOrDefault(id, new Subtask("", ""));
    }

    public Map<Integer, Task> getTasks() {
        return tasks;
    }

    public Map<Integer, Epic> getEpics() {
        return epics;
    }

    public Map<Integer, Subtask> getSubtasks() {
        return subtasks;
    }

    public void deleteTask(Task task) {
        deleteTaskById(task.getId());
    }

    public void deleteSubtask(Subtask subtask) {
        deleteSubtaskById(subtask.getId());
    }

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

    public void addTask(Task task) {
        task.setId(getNextId());
        tasks.put(task.getId(), task);
    }

    public void addEpic(Epic epic) {
        epic.setId(getNextId());
        epics.put(epic.getId(), epic);
    }

    public void addSubtask(Subtask subtask, Epic epic) {
        subtask.setId(getNextId());
        subtasks.put(subtask.getId(), subtask);
        subtask.setEpicId(epic.getId());
        epic.getSubtasksIds().add(subtask.getId());
        epic.setStatus(defineEpicStatus(epic));
    }

    public void setTaskStatus(Task task, Status status) {
        task.setStatus(status);
    }

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

    public void updateSubtask(int oldTaskId, Subtask newSubTask) {
        newSubTask.setId(oldTaskId);
        subtasks.put(oldTaskId, newSubTask);
        Epic epic = getEpic(newSubTask.getId());
        epic.setStatus(defineEpicStatus(epic));
    }

    public void updateEpic(int oldEpicId, Epic newEpic) {
        newEpic.setId(oldEpicId);
        epics.put(oldEpicId, newEpic);
        newEpic.setStatus(defineEpicStatus(newEpic));
    }

    public void updateTask(int oldTaskId, Task newTask) {
        tasks.put(oldTaskId, newTask);
    }
}