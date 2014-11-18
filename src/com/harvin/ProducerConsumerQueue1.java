package com.harvin;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by ehaibin on 11/18/2014.
 */
public class ProducerConsumerQueue1<E> implements ProducerConsumerQueue<E> {
    private final Lock lock = new ReentrantLock();
    private final Condition notEmpty = lock.newCondition();
    private final Condition notFull = lock.newCondition();
    private final int CAPACITY;
    private List<E> list;

    public ProducerConsumerQueue1(int CAPACITY) {
        this.CAPACITY = CAPACITY;
        this.list = new LinkedList<E>();
    }

    //put the e into the queue.
    public void put(E e) throws InterruptedException {
        try {
            lock.lock();
            System.out.println(Thread.currentThread().getName() + " tring to put " + e.toString() + " into queue.");

            if (list.size() == this.CAPACITY) {
                this.notFull.await();
            }
            this.list.add(e);
            if (list.size() == 0) {
                this.notEmpty.signalAll();
            }


            System.out.println(Thread.currentThread().getName() + " finish putting " + e.toString() + " into queue.");

        } finally {
            lock.unlock();
        }
    }

    //get e from queue.
    public E take() throws InterruptedException {
        try {
            lock.lock();
            System.out.println(Thread.currentThread().getName() + " tring to take element form queue.");

            if (list.size() == 0) {
                this.notEmpty.await();
            }
//            if (list.size() < this.CAPACITY) {
//                this.notFull.signalAll();
//            }

            System.out.println(Thread.currentThread().getName() + " finish taking element from queue.");
            return list.remove(list.size() - 1);

        } finally {
            lock.unlock();
        }
    }

}
