package main;

import java.util.Random;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import static main.PhilosopherStatus.*;

public class Philosopher implements Runnable {
    private static final int NUMBER_OF_PHILS = 5; // same as number of philosphers in the next line
    private static final PhilosopherStatus[] status = new PhilosopherStatus[5];
    private static final Lock lock = new ReentrantLock();
    private static final Condition[] canEatCondition = new Condition[5];
    private final int philosopherNumber; // same as ID

    /**
     * Creates a Philosopher object
     * @param philosopherNumber the number for this philosopher
     */
    public Philosopher(int philosopherNumber) {
        this.philosopherNumber = philosopherNumber;
        status[philosopherNumber] = PhilosopherStatus.THINKING;
        canEatCondition[philosopherNumber] = lock.newCondition();
    }

    /**
     * From implementing runnable. When the thread is executed the code in here is run.
     */
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

            try {
                pickUpForks();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            System.out.printf("Philosopher %d is EATING\n", philosopherNumber);

            try {
                eating(sleepTime);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            putDownForks();

            timesThroughLoop++;
        }
    }

    /**
     * Picks up the forks for this philosopher. If it is not able to eat, it will wait until it can
     * @throws InterruptedException if the current thread is interrupted (and interruption of thread suspension is supported)
     */
    private void pickUpForks() throws InterruptedException {
        lock.lock();
        status[philosopherNumber] = HUNGRY;
        test(philosopherNumber);

        while (status[philosopherNumber] != EATING) {
            canEatCondition[philosopherNumber].await();
        }
        lock.unlock();
    }

    /**
     * Puts down the forks after eating and makes the philosopher go to the thinking state
     */
    private void putDownForks() {
        lock.lock();
        status[philosopherNumber] = THINKING;
        test(left_neighbor());
        test(right_neighbor());
        lock.unlock();
    }

    /**
     * Thinking for a certain sleep time
     * @param sleepTime the sleep time
     * @throws InterruptedException if any thread has interrupted the current thread.
     * The interrupted status of the current thread is cleared when this exception is thrown.
     */
    private void thinking(int sleepTime) throws InterruptedException {
        Thread.sleep(sleepTime);
    }

    /**
     * Eating for a certain sleep time
     * @param sleepTime the sleep time
     * @throws InterruptedException if any thread has interrupted the current thread.
     * The interrupted status of the current thread is cleared when this exception is thrown.
     */
    private void eating(int sleepTime) throws InterruptedException {
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
            return NUMBER_OF_PHILS - 1;
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
    private int right_neighbor() {
        if ((philosopherNumber + 1) == NUMBER_OF_PHILS)
        {
            return 0;
        }
        else
        {
            return (philosopherNumber + 1);
        }
    }

    /**
     * Tests to see if it is possible for the philosopher to eat.
     * @param philosopherNumber the philosopher number
     */
    private void test(int philosopherNumber) {
        lock.lock();
        //if im hungry and left and right arent eatting then let me eat
        if ((status[this.left_neighbor()] != EATING) && (status[philosopherNumber] == HUNGRY) && (status[this.right_neighbor()] != EATING))
        {
            status[philosopherNumber] = EATING;
            canEatCondition[philosopherNumber].signal();
        }

        lock.unlock();
    }
}
