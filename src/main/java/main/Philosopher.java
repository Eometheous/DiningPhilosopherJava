package main;

import java.util.Random;

import static main.PhilosopherStatus.*;

public class Philosopher implements Runnable {


    private final int philosopherNumber; // same as ID
    private final int NumberOfPhils = 5; // same as number of philosphers in the next line
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

    /**
     * returns the left neighbor
     * @return the philosopherNumber of the left neighbor.
     */
    private int left_neighbor()
    {
        if (philosopherNumber == 0)
        {
            return NumberOfPhils - 1;
        }
        else
        {
            return (philosopherNumber - 1);
        }
    }
    /**
     * returns the right neighbor
     * @return the philosopherNumber of the right neighbor.
     */
    private int right_neighbor()
    {
        if ((philosopherNumber + 1) == NumberOfPhils)
        {
            return 0;
        }
        else
        {
            return (philosopherNumber + 1);
        }
    }


    private void test()
    {
        //if im hungry and left and right arent eatting then let me eat
        if ((status[this.left_neighbor()] != EATING) && (status[philosopherNumber] == HUNGRY) && (status[this.right_neighbor()] != EATING))
        {
            status[philosopherNumber] = EATING;
            // fIXX pthread_cond_signal(&cond_vars[id]);

        }
    }





}
