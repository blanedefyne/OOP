package ru.nsu.zelenin.composites;

import java.util.Arrays;

public class ConcurrentStreams {

    public static boolean compStreamFinder(Integer[] array) {
        return Arrays.asList(array)
                .parallelStream()
                .anyMatch(ProgressivelyComposites::isComposite);
    }
}
