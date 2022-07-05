package ru.job4j;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.LinkedList;
import java.util.Queue;

@ThreadSafe
public class SimpleBlockingQueue<T> {

    @GuardedBy("this")
    private final Queue<T> queue = new LinkedList<>();
    public int limit;

    public SimpleBlockingQueue(int limit) {
        this.limit = limit;
    }

    public synchronized void offer(T value) throws InterruptedException {
        while (queue.size() == limit) {
            System.out.println("SimpleBlockingQueue is full, waiting until space is free.");
            wait();
        }
        if (queue.size() == 0) {
            System.out.println("SimpleBlockingQueue is empty, notify.");
            notifyAll();
        }
        queue.offer(value);
        System.out.println("SimpleBlockingQueue put ok: " + value);
    }

    public synchronized T poll() throws InterruptedException {
        while (queue.size() == 0) {
            System.out.println("SimpleBlockingQueue is empty, waiting until something is put.");
            wait();
        }
        if (queue.size() == limit) {
            notifyAll();
        }
        T result = queue.poll();
        System.out.println("SimpleBlockingQueue take ok: " + result);
        return result;
    }
}
