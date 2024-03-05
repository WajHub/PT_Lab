package org.example;

public class WorkerPrintResult implements Runnable{
    ManagerResult managerResult;

    public WorkerPrintResult(ManagerResult managerResult) {
        this.managerResult = managerResult;
    }

    @Override
    public void run() {
        while(!Thread.currentThread().isInterrupted()){
            try {
                var result = (Result) managerResult.getResult();
                if(result==null) return;
                String prime = result.isPrime() ? " is Prime. " : " is not Prime. " ;
                System.out.println("[Result] "+result.getNumber()+prime+"Calculated by "+result.getNameThread());
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return ;
            }
        }
    }
}
