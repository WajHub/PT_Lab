package org.example;

import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class WorkerManageTask {
    final Lock lock = new ReentrantLock();
    final Condition notEmpty = lock.newCondition();

    Queue<Integer> tasks;

    public WorkerManageTask(Queue<Integer> tasks) {
        this.tasks = tasks;
    }

    public  void addNumber(int number){
        lock.lock();
        try{
            tasks.add(number);
            System.out.println(tasks);
            notEmpty.signal();
        } finally {
            lock.unlock();
        }
    }

    public Object getNumber() throws InterruptedException{
        lock.lock();
        try{
            while(tasks.isEmpty())
                notEmpty.await();
            return tasks.poll();
        }finally {
            lock.unlock();
        }
    }
}
