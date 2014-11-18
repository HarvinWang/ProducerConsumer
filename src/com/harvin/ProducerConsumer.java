package com.harvin;

interface ProducerConsumerQueue<E> {
    public E take() throws InterruptedException;

    public void put(E e) throws InterruptedException;
}

/**
 * Created by ehaibin on 11/18/2014.
 */
public class ProducerConsumer {
    public static void main(String[] args) {
        ProducerConsumerQueue sharedQueue1 = new ProducerConsumerQueue1(4);

        Thread prodThread = new Thread(new Producer(sharedQueue1));
        Thread consThread = new Thread(new Consumer(sharedQueue1));

        prodThread.start();
        consThread.start();


        ProducerConsumerQueue sharedQueue2 = new ProducerConsumerQueue2(4);

        Thread prodThread2 = new Thread(new Producer(sharedQueue2));
        Thread consThread2 = new Thread(new Consumer(sharedQueue2));

        prodThread2.start();
        consThread2.start();
    }
}
