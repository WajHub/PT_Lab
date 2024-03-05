package org.example;

import java.util.Queue;

public class ManagerTask {

    Queue<Integer> tasks;

    public ManagerTask(Queue<Integer> tasks) {
        this.tasks = tasks;
    }

    public synchronized void addNumber(int number){
        tasks.add(number);
        notify();
    }

    public synchronized Object getNumber() throws InterruptedException{
        while(tasks.isEmpty())
                wait();
        return tasks.poll();
    }
}
