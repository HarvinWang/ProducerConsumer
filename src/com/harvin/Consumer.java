package com.harvin;

/**
 * Created by ehaibin on 11/18/2014.
 */
public class Consumer implements Runnable {
    private final ProducerConsumerQueue sharedQueue;

    public Consumer(ProducerConsumerQueue sharedQueue) {
        this.sharedQueue = sharedQueue;
    }

    @Override
    public void run() {
        while (true) {
            try {
                synchronized (this){
                    System.out.println("\tConsumer: " + sharedQueue.take());
                }
            } catch (InterruptedException ex) {
                System.out.println(Thread.currentThread().getName() + " throw a interrupexception.");
                //do something.
            }
        }
    }
}
