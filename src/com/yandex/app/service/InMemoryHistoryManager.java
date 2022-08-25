package com.yandex.app.service;

import com.yandex.app.model.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InMemoryHistoryManager implements HistoryManager {
    final private CustomLinkedList history = new CustomLinkedList();

    @Override
    public List<Task> getHistory() {
        return history.getTasks();
    }

    @Override
    public void add(Task task) {
        history.linkLast(task);
    }

    @Override
    public void remove(int id) {
            history.removeNode(id);
    }

    private static class CustomLinkedList {

        private static class Node<T> {
            public Task task;
            public Node<Task> next;
            public Node<Task> prev;

            public Node(Node<Task> prev, Task task, Node<Task> next) {
                this.task = task;
                this.next = next;
                this.prev = prev;
            }
        }

        private Node<Task> head;
        private Node<Task> tail;

        final private Map<Integer, CustomLinkedList.Node<Task>> idNode = new HashMap<>();

        public void linkLast(Task task) {
            if (idNode.containsKey(task.getId())) {
                removeNode(idNode.get(task.getId()));
            }
            final Node<Task> oldTail = tail;
            final Node<Task> newNode = new Node<>(oldTail, task, null);
            tail = newNode;
            if (oldTail == null) {
                head = newNode;
            } else {
                oldTail.next = newNode;
            }
            idNode.put(task.getId(), newNode);
        }

        private void removeNode(Node<Task> node) {
            final Node<Task> prev = node.prev;
            final Node<Task> next = node.next;
            if (prev == null) {
                head = next;
            } else {
                prev.next = next;
                node.prev = null;
            }
            if (next == null) {
                tail = prev;
            } else {
                next.prev = prev;
                node.next = null;
            }
            node.task = null;
        }

        private void removeNode (int id) {
            if (idNode.containsKey(id)) {
                removeNode(idNode.get(id));
            }
        }

        private List<Task> getTasks() {
            List<Task> tasks = new ArrayList<>();
            for (Node<Task> node = head; node != null; node = node.next) {
                tasks.add(node.task);
            }
            return tasks;
        }
    }
}