package com;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

public class LRUCache<K, V> {
    private Node<K, V> head;
    private Node<K, V> tail;
    private final HashMap<K, Node<K, V>> map;
    private int maxSize;

    public LRUCache(int maxSize) {
        assert (maxSize > 0) : "Expected positive number";
        this.maxSize = maxSize;
        this.map = new HashMap<>();
    }

    public V get(@NotNull K key) {
        assert(this.size() <= this.maxSize) : "Size overflow";
        Node<K, V> node = this.map.get(key);

        if (node == null) {
            return null;
        }

        this.removeNode(node);
        this.putNode(node);

        assert(this.size() <= this.maxSize) : "Size overflow";

        return node.value;
    }

    public void put(@NotNull K key, @NotNull V value) {
        assert(this.size() <= this.maxSize) : "Size overflow";
        int oldSize = this.size();
        Node<K, V> newNode = new Node<>(key, value);

        if (!this.map.containsKey(key)) {
            this.putNode(newNode);
            if (this.size() == this.maxSize) {
                this.map.remove(this.tail.key);
                this.removeNode(this.tail);
            }
        } else {
            Node<K, V> node = this.map.get(key);
            assert(node != null) : "Null node found";

            this.removeNode(node);
            this.putNode(newNode);

            assert(this.size() == oldSize): "Unexpected resize";
        }

        this.map.put(key, newNode);

        assert(this.size() <= this.maxSize);
        assert(this.size() >= oldSize);
        assert(this.head.key == key);
        assert(this.head.value == value);
    }

    public int size() {
        return map.size();
    }

    public void remove(@NotNull K key) {
        Node<K, V> node = map.get(key);
        if (node == null) {
            return;
        }

        removeNode(node);
        map.remove(key);
    }


    private void putNode(@NotNull Node<K, V> node) {
        node.prev = this.head;
        node.next = null;

        if (this.head != null) {
            this.head.next = node;
        }

        this.head = node;

        if (this.tail == null) {
            this.tail = node;
        }
    }

    private void removeNode(@NotNull Node<K, V> node) {
        if (node.prev != null) {
            node.prev.next = node.next;
        }
        if (node.next != null) {
            node.next.prev = node.prev;
        }

        if (this.head == node) {
            this.head = node.prev;
        }
        if (this.tail == node) {
            this.tail = node.next;
        }
    }

    private static class Node<K, V> {
        private final K key;
        private final V value;
        public Node<K, V> next;
        public Node<K, V> prev;

        Node(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }

}
