package ru.nsu.zelenin.composites;

public class ConcurrentComposites {
    private volatile static int idx;
    private static boolean answer;

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

    private synchronized static int getNextIndex() {
        return idx++;
    }

    private static void interruptAllThreads(Thread[] threads) {
        for (Thread thread : threads) {
            thread.interrupt();
        }
    }

}