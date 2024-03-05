package org.example;

public class WorkerCountValue implements Runnable{
    ManagerTask workerManageTask;
    ManagerResult managerResult;

    public WorkerCountValue( ManagerTask workerManageTask,ManagerResult managerResult) {
        this.workerManageTask = workerManageTask;
        this.managerResult = managerResult;
    }

    @Override
    public synchronized void run() {
        System.out.println("[Start] Current Thread: "+Thread.currentThread().getName());
        while(true) {
            try {
                var number = workerManageTask.getNumber();
                if(number==null) {
                    return;
                }
                System.out.println("[Counting] Current Thread: " + Thread.currentThread().getName() + " [Nubmer " +
                        number + "]");
                int random = (int) (Math.random() * 10000) +1;
                Thread.sleep(random);
                managerResult.addResult(new Result(Thread.currentThread().getName(),(int)number,isPrime((int)number)));
                }
            catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private boolean isPrime(int n)
    {
        if (n <= 1)
            return false;

        // Check if n=2 or n=3
        if (n == 2 || n == 3)
            return true;

        // Check whether n is divisible by 2 or 3
        if (n % 2 == 0 || n % 3 == 0)
            return false;

        // Check from 5 to square root of n
        // Iterate i by (i+6)
        for (int i = 5; i <= Math.sqrt(n); i = i + 6)
            if (n % i == 0 || n % (i + 2) == 0)
                return false;

        return true;
    }
}
