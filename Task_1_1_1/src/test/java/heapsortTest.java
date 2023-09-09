import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class heapsortTest {
    @Test
    public void heapSort() {
        Assertions.assertArrayEquals(new int[]{1, 2, 3, 4, 5}, heapsort.heapSort(new int[] {3, 1, 2, 5, 4}), "Array Equal Test");
        Assertions.assertArrayEquals(new int[]{-1000, -890, 0, 1, 14, 213, 1234, 1243}, heapsort.heapSort(new int[] {0, 1243, 213, 1, -890, 1234, 14, -1000}), "Array Equal Test");
        Assertions.assertArrayEquals(new int[]{}, heapsort.heapSort(new int[] {}), "Array Equal Test");
    }

}