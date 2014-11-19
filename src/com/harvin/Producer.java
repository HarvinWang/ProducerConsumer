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
        for (int i = 0; i < 20; i++) {
            try {
                synchronized (this){
                    System.out.println("Producer: "+i);
                    sharedQueue.put(i);
                }
            } catch (InterruptedException ex) {
                System.out.println(Thread.currentThread().getName() + " throw a interrupexception. ");
                //do something.
            }
        }
    }
}
