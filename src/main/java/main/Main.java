package main;


import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    public static void main(String[] args) {
        Philosopher[] philosophers = new Philosopher[5];
        for (int i = 0; i < 5; i++) {
            philosophers[i] = new Philosopher(i);
        }

        ExecutorService executorService = Executors.newCachedThreadPool();

        for (Philosopher philosopher : philosophers) {
            executorService.execute(philosopher);
        }

        executorService.shutdown();

        System.out.println("Hello world!");
    }
}