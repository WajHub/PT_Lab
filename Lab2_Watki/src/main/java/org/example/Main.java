package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String instrucion = "";

        ExecutorService executorService = null;
        List<Thread> threads = new ArrayList<>();
        int numberOfThreads = 0;

        if (args.length>0){
            numberOfThreads = Integer.parseInt(args[0]);
            executorService = Executors.newFixedThreadPool(numberOfThreads);
        }

        for(int i=0;i<numberOfThreads;i++){
            Thread thread = new Thread(new MyRunnable());
            executorService.submit(thread);
            threads.add(thread);
        }

        while(!Objects.equals(instrucion, "exit")){
            instrucion = scanner.nextLine();
        }
        executorService.shutdown();

    }
}