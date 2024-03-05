package org.example;

public class Result {
    String nameThread;
    int number;
    boolean isPrime;

    public Result(String nameThread, int number, boolean isPrime) {
        this.nameThread = nameThread;
        this.number = number;
        this.isPrime = isPrime;
    }

    public String getNameThread() {
        return nameThread;
    }

    public int getNumber() {
        return number;
    }

    public boolean isPrime() {
        return isPrime;
    }

    @Override
    public String toString() {
        return "Result{" +
                "nameThread='" + nameThread + '\'' +
                ", number=" + number +
                ", isPrime=" + isPrime +
                '}';
    }
}
