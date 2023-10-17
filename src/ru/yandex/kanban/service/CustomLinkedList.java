package ru.yandex.kanban.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CustomLinkedList<T> {
    private Node<T> head;
    private Node<T> tail;

    private int size;

    private final Map<Integer, Node<T>> existTasks = new HashMap<>();

    public CustomLinkedList() {
        this.head = null;
        this.tail = null;
        size = 0;
    }

    public Node<T> linkLast(T data) {
        final Node<T> newNode = new Node<>(data);
        if (tail == null) {
            tail = newNode;
            head = newNode;
        } else {
            tail.setNext(newNode);
            newNode.setPrevious(tail);
            tail = newNode;
        }
        size++;

        return newNode;
    }

    public Node<T> getHead() {
        return head;
    }

    public List<T> getTasks() {
        List<T> result = new ArrayList<>();
        Node<T> currentNode = head;
        while (currentNode != null) {
            result.add(currentNode.getData());
            currentNode = currentNode.getNext();
        }
        return result;
    }

    public int size() {
        return size;
    }

    public void removeNode(Node<T> node) {
        if (node == tail && node == head) {
            tail = null;
            head = null;
            size = 0;
        } else if (node == tail) {
            tail = node.getPrevious();
            node.getPrevious().setNext(null);
            size--;
        } else if (node == head) {
            head = node.getNext();
            node.getNext().setPrevious(null);
            size--;
        } else if (node != null) {
            node.getPrevious().setNext(node.getNext());
            node.getNext().setPrevious(node.getPrevious());
            size--;
        }
    }

    public void add(int id, T data) {
        if (existTasks.containsKey(id)) {
            removeNode(existTasks.get(id));
        }
        Node<T> newNode = linkLast(data);
        existTasks.put(id, newNode);
    }

    public void remove(int id) {
        if (existTasks.containsKey(id)) {
            Node<T> removingNode = existTasks.get(id);
            existTasks.remove(id);
            removeNode(removingNode);
        }
    }
}

