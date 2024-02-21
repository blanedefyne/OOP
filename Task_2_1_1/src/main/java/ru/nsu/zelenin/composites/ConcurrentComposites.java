package ru.nsu.zelenin.composites;

/**
 * Class for finding a composite number in array.
 * (using concurrent computations - few threads)
 */
public class ConcurrentComposites {
    private static volatile int idx;
    private static boolean answer;

    /**
     * Method return boolean value - does an array contain a composite number or not.
     *
     * @param array - array we are considering
     * @param n - number of threads using
     * @return - boolean value
     */
    public static boolean compFinder(Integer[] array, int n) {
        answer = false;
        idx = 0;
        Thread[] threads = new Thread[n];

        for (int i = 0; i < n; ++i) {
            threads[i] = new Thread(() -> {
                int index;
                while ((index = getNextIndex()) < array.length) {
                    if (ProgressivelyComposites.isComposite(array[index])) {
                        synchronized (ConcurrentComposites.class) {
                            answer = true;
                        }
                        System.out.println(array[index] + " is composite!");
                        interruptAllThreads(threads);
                        return;
                    }
                    if (answer) {
                        interruptAllThreads(threads);
                        return;
                    }
                }
            });
            threads[i].start();
        }

        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        return answer;
    }

    /**
     * Synchronized method for incrementing index of element of array.
     *
     * @return int - new index
     */
    private static synchronized int getNextIndex() {
        return idx++;
    }

    /**
     * Method interrupts all threads - in case we already found a number.
     *
     * @param threads - array of threads
     */
    private static void interruptAllThreads(Thread[] threads) {
        for (Thread thread : threads) {
            thread.interrupt();
        }
    }

}