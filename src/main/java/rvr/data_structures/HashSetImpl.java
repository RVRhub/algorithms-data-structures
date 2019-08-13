package rvr.data_structures;

import org.jetbrains.annotations.NotNull;

import java.util.*;

/**
 * Perf test with diff capacity
 * @param <T>
 */
public class HashSetImpl<T> implements Set<T> {

    private static final Integer INITIAL_CAPACITY = 1 << 4; // 16

    private Node<T>[] buckets;

    private int size;

    public HashSetImpl(final int capacity) {
        this.buckets = new Node[capacity];
        this.size = 0;
    }

    public HashSetImpl() {
        this(INITIAL_CAPACITY);
    }


    @Override
    public boolean add(T t) {

        int index = Hashing.hash(t) % buckets.length;
        Node bucket = buckets[index];

        Node<T> newNode = new Node<>(t);

        if (bucket == null) {
            buckets[index] = newNode;
            size++;
            return true;
        }

        while (bucket.next != null) {
            if (bucket.data.equals(t)) {
                return true;
            }
            bucket = bucket.next;
        }

        // add only if last element doesn't have the value being added
        if (!bucket.data.equals(t)) {
            bucket.next = newNode;
            size++;
        }

        return true;
    }



    @Override
    public boolean containsAll(@NotNull Collection<?> c) {
        return false;
    }

    @Override
    public boolean addAll(@NotNull Collection<? extends T> c) {
        return false;
    }

    @Override
    public boolean retainAll(@NotNull Collection<?> c) {
        return false;
    }

    @Override
    public boolean removeAll(@NotNull Collection<?> c) {
        return false;
    }

    @Override
    public void clear() {

    }

    @Override
    public boolean remove(Object t) {

        int index = Hashing.hash(t) % buckets.length;

        Node bucket = buckets[index];

        if (bucket == null) {
            throw new NoSuchElementException("No Element Found");
        }

        if (bucket.data.equals(t)) {
            buckets[index] = bucket.next;
            size--;
            return true;
        }

        Node prev = bucket;

        while (bucket != null) {
            if (bucket.data.equals(t)) {
                prev.next = bucket.next;
                size--;
                return true;
            }
            prev = bucket;
            bucket = bucket.next;
        }
        return false;
    }

    @Override
    public int size(){
        return size;
    }

    public int getBucketsSize() {
        return this.buckets.length;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public boolean contains(Object o) {
        return false;
    }

    @NotNull
    @Override
    public Iterator<T> iterator() {
        return new SimpleHashSetIterator();
    }

    @NotNull
    @Override
    public Object[] toArray() {
        return new Object[0];
    }

    @NotNull
    @Override
    public <T1> T1[] toArray(@NotNull T1[] a) {
        return null;
    }

    class SimpleHashSetIterator implements Iterator{
        private int currentBucket;
        private int previousBucket;
        private Node currentEntry;
        private Node previousEntry;

        public SimpleHashSetIterator() {
            currentEntry = null;
            previousEntry = null;
            currentBucket = -1;
            previousBucket = -1;
        }

        @Override
        public boolean hasNext() {

            // currentEntry node has next
            if (currentEntry != null && currentEntry.next != null) { return true; }

            // there are still nodes
            for (int index = currentBucket+1; index < buckets.length; index++) {
                if (buckets[index] != null) { return true; }
            }

            // nothing left
            return false;
        }

        @Override
        public Object next() {

            previousEntry = currentEntry;
            previousBucket = currentBucket;

            // if either the current or next node are null
            if (currentEntry == null || currentEntry.next == null) {

                // go to next bucket
                currentBucket++;

                // keep going until you find a bucket with a node
                while (currentBucket < buckets.length &&
                        buckets[currentBucket] == null) {
                    // go to next bucket
                    currentBucket++;
                }

                // if bucket array index still in bounds
                // make it the current node
                if (currentBucket < buckets.length) {
                    currentEntry = buckets[currentBucket];
                }
                // otherwise there are no more elements
                else {
                    throw new NoSuchElementException();
                }
            }
            // go to the next element in bucket
            else {

                currentEntry = currentEntry.next;
            }

            // return the element in the current node
            return currentEntry.getData();

        }
    }

    class Node<T> {
        T data;
        Node next;

        Node(T data) {
            this.data = data;
            this.next = null;
        }

        public T getData() {
            return data;
        }

        public Node getNext() {
            return next;
        }

        @Override
        public int hashCode() {
            return Objects.hash(data, next);
        }

        @Override
        public String toString() {
            return "Node{" +
                    "data=" + data +
                    ", next=" + next +
                    '}';
        }
    }
}
