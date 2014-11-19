package com.harvin;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by Harvin.Wang on 11/18/2014.
 */
public final class ProducerConsumerQueue<E> {
    private final Lock lock = new ReentrantLock();
    private final Condition notEmpty = lock.newCondition();
    private final Condition notFull = lock.newCondition();
    private final int CAPACITY;
    private Queue<E> queue;

    public ProducerConsumerQueue(int CAPACITY) {
        this.CAPACITY = CAPACITY;
        this.queue = new LinkedList<E>();
    }

    //put the e into the queue.
    public void put(E e) throws InterruptedException {
        lock.lock();

        try {
            while (queue.size() == this.CAPACITY)
                this.notFull.await();

            queue.offer(e);
            this.notEmpty.signalAll();

        } finally {
            lock.unlock();
        }
    }

    //get e from queue.
    public E take() throws InterruptedException {
        lock.lock();

        try {
            while (queue.size() == 0)
                this.notEmpty.await();

            this.notFull.signalAll();
            return queue.poll();

        } finally {
            lock.unlock();
        }
    }
}
