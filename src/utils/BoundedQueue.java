package utils;

public class BoundedQueue<E> {
    final Object[] data;
    int head = 0;
    int size = 0;

    public BoundedQueue(int maxSize) {
        data = new Object[maxSize];
    }

    public boolean isFull() {
        return size == data.length;
    }

    public void push(E e) {
        if (isFull())
            throw new IllegalStateException();
        data[(head + size) % data.length] = e;
        size += 1;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public E pop() {
        if (isEmpty())
            throw new IllegalStateException();
        E e = peek();
        head = (head + 1) % data.length;
        size -= 1;
        return e;
    }

    public E peek() {
        return isEmpty() ? null : (E) data[head];
    }

    public int size() {
        return data.length;
    }

    public void printBoundedQueue() {
        for (Object obj : data) {
            System.out.println(obj);
        }
    }
}
