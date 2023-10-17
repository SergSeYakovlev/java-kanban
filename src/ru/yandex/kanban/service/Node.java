package ru.yandex.kanban.service;

public class Node<T> {

    private Node<T> previous;
    private final T data;
    private Node<T> next;

    public Node(T data) {
        this.previous = null;
        this.data = data;
        this.next = null;
    }

    public Node<T> getPrevious() {
        return previous;
    }

    public void setPrevious(Node<T> previous) {
        this.previous = previous;
    }

    public T getData() {
        return data;
    }

    public Node<T> getNext() {
        return next;
    }

    public void setNext(Node<T> next) {
        this.next = next;
    }
}
