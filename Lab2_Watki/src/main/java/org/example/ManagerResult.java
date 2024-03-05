package org.example;

import java.util.Queue;

public class ManagerResult {

    Queue<Result> results;

    public ManagerResult(Queue<Result> results) {
        this.results = results;
    }

    public synchronized void addResult(Result result){
        results.add(result);
        notify();
    }

    public synchronized Object getResult() throws InterruptedException {
        while(results.isEmpty())
            wait();
        return results.poll();
    }

}
