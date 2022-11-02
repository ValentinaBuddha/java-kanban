package service;

import model.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InMemoryHistoryManager implements HistoryManager {
    //создаем свой двусвязный список просмотренных задач
    final private CustomLinkedList history = new CustomLinkedList();

    //метод для получения списка просмотренных задач
    @Override
    public List<Task> getHistory() {
        return history.getTasks();
    }

    //метод добавляет задачу в конец списка истории и удаляет предыдущий просмотр этой задачи
    @Override
    public void add(Task task) {
        history.linkLast(task);
    }

    //метод удаляет задачу из истории просмотренных задач при удалении задачи из канбана
    @Override
    public void remove(int id) {
        history.removeNode(id);
    }

    //внутренний класс для создания своей коллекции - двусвязный список
    //с внутренней мапой и узлами - для быстрого удаления предыдущих просмотров
    private static class CustomLinkedList {

        //внутренний класс для создания узла в коллекции
        private static class Node<T> {
            public T task;
            public Node<T> next;
            public Node<T> prev;

            public Node(Node<T> prev, T task, Node<T> next) {
                this.task = task;
                this.next = next;
                this.prev = prev;
            }
        }

        private Node<Task> head;
        private Node<Task> tail;

        //мапа для хранения пары номер задачи - узел
        //(для удаления предыдущего просмотра задачи из истории за O(1))
        final private Map<Integer, CustomLinkedList.Node<Task>> idNode = new HashMap<>();

        //метод добавляет задачу в конец списка и удаляет ее предыдущий просмотр
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

        //метод принимает id задачи на удаление и передает соответствующий узел в сл метод по удалению
        private void removeNode(int id) {
            if (idNode.containsKey(id)) {
                removeNode(idNode.get(id));
            }
        }

        //метод удаляет из мапы узел с уже просмотренной и добавляемой вновь задачей
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

        //метод формирует список задач для просмотра истории
        private List<Task> getTasks() {
            List<Task> tasks = new ArrayList<>();
            for (Node<Task> node = head; node != null; node = node.next) {
                tasks.add(node.task);
            }
            return tasks;
        }
    }
}