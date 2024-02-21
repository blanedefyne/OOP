package ru.nsu.zelenin.composites;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/**
 * Class for testing finding solutions.
 */
public class CompositesTest {

    @Test
    public void streamTest() {
        Integer[] array = {2, 3, 11, 11, 47, 701, 6997927, 606559, 8};
        assertTrue(ConcurrentStreams.compStreamFinder(array));
    }

    @Test
    public void streamTest2() {
        Integer[] arr = {20319251, 6997901, 6997927, 6997937,
                         17858849, 6997967, 6998009, 6998029,
                         20165149, 6998051, 6998053, 6998039};
        assertFalse(ConcurrentStreams.compStreamFinder(arr));
    }

    @Test
    public void streamTest3() {
        Integer[] arr = {157543, 157559, 157561, 157571, 157579,
                         946753, 946769, 946783, 946801, 17, 19, 370};
        assertTrue(ConcurrentStreams.compStreamFinder(arr));
    }

    @Test
    public void progressivelyTest() {
        Integer[] array = {2, 3, 5, 7, 11, 13, 41, 37, 45};
        assertTrue(ProgressivelyComposites.compFinder(array));
    }

    @Test
    public void anotherOne() {
        Integer[] array = {7, 11, 83, 13, 158843, 608213, 608863};
        assertFalse(ProgressivelyComposites.compFinder(array));
    }

    @Test
    public void concurrentTest() {
        Integer[] arr = {157273, 157277, 157279,
                         157291, 157303, 157307, 157321, 157327,
                         157349, 157351, 157363, 157393, 157411,
                         157427, 157429, 157433, 157457, 157477,
                         157483, 157489, 157513, 157519, 157523,
                         157543, 157559, 157561, 157571, 157579,
                         157649, 157667, 157669, 157679, 157721};
        assertFalse(ConcurrentComposites.compFinder(arr, 3));
    }

    @Test
    public void concurrentTest2() {
        Integer[] arr = {946369, 8, 946459, 946469, 946487, 81,
                         946489, 946507, 946511, 946513, 946549, 946573,
                         946579, 946607, 946661, 946663, 946667, 946669,
                         946681, 946697, 946717, 946727, 946733, 946741,
                         946753, 946769, 946783, 946801, 946819, 946823,
                         946873, 946877, 946901, 90, 946919};

        assertTrue(ConcurrentComposites.compFinder(arr, 4));
    }


    @Test
    public void bigTestForGraph() {
        Integer[] arr = new Integer[25000000];
        for (int i = 0; i < 12500000; ++i) {
            arr[2 * i] = 20319251;
            arr[2 * i + 1] = 6998053;
        }

        long startTime = System.nanoTime();
        boolean res = ConcurrentStreams.compStreamFinder(arr);
        long endTime = System.nanoTime();
        long duration = (endTime - startTime) / 1000000;

        System.out.println(duration);
        assertFalse(res);
    }
}
