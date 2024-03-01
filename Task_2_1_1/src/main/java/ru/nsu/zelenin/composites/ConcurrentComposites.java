package ru.nsu.zelenin.composites;

import java.util.ArrayList;
import java.util.List;

/**
 * Class for finding a composite number in array.
 * (using concurrent computations - few threads)
 */
public class ConcurrentComposites {

    private static volatile boolean answer;
    
    /**
     * Method return boolean value - does an array contain a composite number or not.
     * (if there is a composite number / numbers - method prints one of them)
     *
     * @param array - array we are considering
     * @param n - number of threads using
     * @return - boolean value
     */
    public static boolean compFinder(Integer[] array, int n) {
        answer = false;
        Thread[] threads = new Thread[n];
        List<Integer> ranges = makePartition(array, n);
        int prevL;
        int prevR = 0;

        for (int i = 0; i < n; ++i) {
            prevL = prevR;
            prevR += ranges.get(i);
            int r = prevR;
            int l = prevL;
            threads[i] = new Thread(() -> {
                for (int j = l; j < r; ++j) {
                    if (ProgressivelyComposites.isComposite(array[j])) {
                        synchronized (ConcurrentComposites.class) {
                            if (!answer) {
                                System.out.println(array[j] + " is composite!");
                            }
                            answer = true;
                            return;
                        }
                    }
                    if (answer) {
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
     * Private method for making a partition of elements for threads.
     *
     * @param array - given array
     * @param n - number of threads
     * @return - list of numbers of elements on each thread
     */
    private static List<Integer> makePartition(Integer[] array, int n) {
        List<Integer> partition = new ArrayList<>();
        int elemsLeft = array.length;
        int threadsLeft = n;

        for (int i = 0; i < n; i++) {
            if (elemsLeft % threadsLeft == 0) {
                for (int j = 0; j < threadsLeft; ++j) {
                    partition.add(elemsLeft / threadsLeft);
                }
                break;
            }
            int x = elemsLeft / threadsLeft + 1;
            partition.add(x);
            elemsLeft -= x;
            threadsLeft--;
        }
        return partition;
    }
}