package com.harvin;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by ehaibin on 11/18/2014.
 */
public final class ProducerConsumerQueue<E>  {
    private final Lock lock = new ReentrantLock();
    private final Condition notEmpty = lock.newCondition();
    private final Condition notFull = lock.newCondition();
    private final int CAPACITY;
    private Queue<E> list;

    public ProducerConsumerQueue(int CAPACITY) {
        this.CAPACITY = CAPACITY;
        this.list = new LinkedList<E>();
    }

    //put the e into the queue.
    public void put(E e) throws InterruptedException {
        try {
            lock.lock();
            System.out.println("* The Producer "+Thread.currentThread().getName()+" tries to put ["+e+"] into the queue.");

            if (list.size() == this.CAPACITY){
                System.out.println("The queue is full. \n\tso, Producer "+Thread.currentThread().getName()+" is blocked.\n");
                this.notFull.await();
                System.out.println("the Producer "+Thread.currentThread().getName()+" resumes to work.\n");
            }

            if (list.size() == 0){
//                System.out.println("When the queue is empty,maybe all the consumers have been blocked.\n\t" +
//                        "so, the next step should resume the notEmpty, as result of waking up all the consumers.");
                this.notEmpty.signalAll();
            }


            list.offer(e);


            System.out.println("After the Producer:"+Thread.currentThread().getName());
            showQueue();

        } finally {
            lock.unlock();
        }
    }

    //get e from queue.
    public E take() throws InterruptedException {
        try {
            lock.lock();
            System.out.println("* The Consumer "+Thread.currentThread().getName()+" tries to take element from the queue.");

            if (list.size() == 0) {
                System.out.println("The queue is empty. \n\tso, Consumer " + Thread.currentThread().getName() + " is blocked.\n");
                this.notEmpty.await();
                System.out.println("the Consumer "+Thread.currentThread().getName()+" resumes to work.\n");
            }

            if (list.size() == this.CAPACITY){
//                System.out.println("When the queue is full,maybe all the producers have been blocked.\n\t" +
//                        "so, the next step should resume the notFull, as result of waking up all the producer.");
                this.notFull.signalAll();
            }

            E tmp = list.poll();
            System.out.println("After the Consumer:"+Thread.currentThread().getName());
            showQueue();
            return tmp;

        } finally {
            lock.unlock();
        }
    }


    private void showQueue(){
        System.out.println("The queue now is :");
        Iterator it = list.iterator();
        while (it.hasNext())
            System.out.print("    "+it.next());
        System.out.println("\n");
    }
}
