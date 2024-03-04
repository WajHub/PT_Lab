package org.example;

public class MyRunnable implements Runnable{

    @Override
    public void run() {
        System.out.println("[Start] Current Thread: "+Thread.currentThread().getName());
        try {
            System.out.println("[Counting] Current Thread: "+Thread.currentThread().getName());
            int randomTime = (int) Math.floor(Math.random()*10000+1000);
            Thread.sleep(randomTime);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("[End] Current Thread: "+Thread.currentThread().getName());
    }
}
