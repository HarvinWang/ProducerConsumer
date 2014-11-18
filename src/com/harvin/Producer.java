package com.harvin;

/**
 * Created by ehaibin on 11/18/2014.
 */
public class Producer implements Runnable {
    private final ProducerConsumerQueue sharedQueue;

    public Producer(ProducerConsumerQueue sharedQueue) {
        this.sharedQueue = sharedQueue;
    }

    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            try {
                System.out.println("Producerd: " + i);
                sharedQueue.put(i);
            } catch (InterruptedException ex) {
                System.out.println(Thread.currentThread().getName() + " throw a interrupexception.");
            }
        }
    }
}
