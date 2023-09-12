package ru.nsu.zelenin;

import java.util.Arrays;
import java.util.Random;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class HeapsortTest {

    @Test
    public void defaultArray() {
        Assertions.assertArrayEquals(new int[]{1, 2, 3, 4, 5},
                   Heapsort.heapSort(new int[]{3, 1, 2, 5, 4}));
    }

    @Test
    public void emptyArr() {
        Assertions.assertArrayEquals(new int[0],
                Heapsort.heapSort(new int[0]));
    }

    @Test
    public void biggerArray() {
        Assertions.assertArrayEquals(new int[]{-1000, -890, 0, 1, 14, 213, 1234, 1243},
                   Heapsort.heapSort(new int[]{0, 1243, 213, 1, -890, 1234, 14, -1000}));
    }

    @Test
    public void singleArray() {
        Assertions.assertArrayEquals(new int[]{1994},
                   Heapsort.heapSort(new int[]{1994}));
    }

    public static int[] generateRandomArray(int n) {
        int[] array = new int[n];
        Random random = new Random();

        for (int i = 0; i < n; ++i) {
            array[i] = random.nextInt(1000000);
        }

        return array;
    }

    @Test
    public void oneMillion() {
        int[] myArray = generateRandomArray(1000000);
        Arrays.sort(myArray);
        int[] mySecondArray = Heapsort.heapSort(myArray);
        Assertions.assertArrayEquals(myArray, Heapsort.heapSort(mySecondArray));
    }
}