package org.example;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String instrucion = "";

        ExecutorService executorService;
        ManagerTask managerTask = new ManagerTask(new LinkedList<>());
        ManagerResult managerResult = new ManagerResult(new LinkedList<>());
        Thread workerPrintResult = new Thread(new WorkerPrintResult(managerResult));
        workerPrintResult.start();

        int numberOfThreads = args.length>0 ? Integer.parseInt(args[0]) : 5;
        executorService = Executors.newFixedThreadPool(numberOfThreads);

//        for(int i=0; i<2; i++){
//            int random = (int) (Math.random() * 100000) +1;
//            managerTask.addNumber(random);
//        }

        // Create new threads
        for(int i=0;i<numberOfThreads;i++){
            Thread thread = new Thread(new WorkerCountValue(managerTask, managerResult));
            executorService.submit(thread);
        }

        while(!Objects.equals(instrucion, "exit")){
            instrucion = scanner.nextLine();
            try{
                int number = Integer.parseInt(instrucion);
                managerTask.addNumber(number);
            }catch(NumberFormatException e){
                if(!Objects.equals(instrucion, "exit")) System.out.println("Invalid format");
            }
        }
        executorService.shutdownNow();
        workerPrintResult.interrupt();
    }
}