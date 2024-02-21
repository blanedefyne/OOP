package ru.nsu.zelenin.composites;


public class ProgressivelyComposites {

    public static boolean isComposite(int x) {
        for (int j = 2; j * j <= x; j++) {
            if (x % j == 0) {
                return true;
            }
        }
        return false;
    }

    public static boolean compFinder(Integer[] array) {

        for (Integer x : array) {
            if (isComposite(x)) {
                return true;
            }
        }
        return false;
    }
}
