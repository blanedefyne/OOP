package ru.nsu.zelenin.composites;

import java.util.Arrays;

/**
 * Class for finding composite number in array
 * using parallelStream()
 */
public class ConcurrentStreams {

    /**
     * Method itself - returning an answer - there is or there's not.
     *
     * @param array - considering array
     * @return boolean value
     */
    public static boolean compStreamFinder(Integer[] array) {
        return Arrays.asList(array)
                .parallelStream()
                .anyMatch(ProgressivelyComposites::isComposite);
    }
}
