package main;

import java.util.Random;

public class Philosopher implements Runnable {
    private int philosopherNumber;
    public Philosopher(int philosopherNumber) {
        this.philosopherNumber = philosopherNumber;
    }
    @Override
    public void run() {
        int timesThroughLoop = 0;
        int sleepTime;
        Random random = new Random();
        while (timesThroughLoop < 5) {
            sleepTime = random.nextInt(5);

            timesThroughLoop++;
        }
    }
}
