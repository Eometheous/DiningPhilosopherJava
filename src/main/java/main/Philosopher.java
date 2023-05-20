package main;

import java.util.Random;

public class Philosopher implements Runnable {
    private final int philosopherNumber;
    private static final PhilosopherStatus[] status = new PhilosopherStatus[5];
    public Philosopher(int philosopherNumber) {
        this.philosopherNumber = philosopherNumber;
        status[philosopherNumber] = PhilosopherStatus.THINKING;
    }
    @Override
    public void run() {
        int timesThroughLoop = 0;
        int sleepTime;
        Random random = new Random();
        while (timesThroughLoop < 5) {
            sleepTime = random.nextInt(5000);

            try {
                System.out.printf("Philosopher %d is THINKING\n", philosopherNumber);
                thinking(sleepTime);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            timesThroughLoop++;
        }
    }

    private void thinking(int sleepTime) throws InterruptedException {
        Thread.sleep(sleepTime);
    }
}
