package ru.nsu.zelenin.composites;

/**
 * Class for finding composite number in array
 * using progressively computations
 */
public class ProgressivelyComposites {

    /**
     * Method return true if given number is composite.
     *
     * @param x - given number
     * @return boolean value
     */
    public static boolean isComposite(int x) {
        for (int j = 2; j * j <= x; j++) {
            if (x % j == 0) {
                return true;
            }
        }
        return false;
    }

    /**
     * Method-finder of this class - searching for composite number in given array.
     *
     * @param array - given array
     * @return boolean value
     */
    public static boolean compFinder(Integer[] array) {

        for (Integer x : array) {
            if (isComposite(x)) {
                return true;
            }
        }
        return false;
    }
}
