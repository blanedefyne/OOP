import java.util.Arrays;

/**
 Пирамидальная сортировка (сортировка кучей).
*/
public class Heapsort {

    /**
     * @param arr - our array we need to sort
     * @param length - its length
     * @param index - inner vertex we work with
     */
    public static void heapify(int[] arr, int length, int index) {
        int leftChild = index * 2 + 1;
        int rightChild = index * 2 + 2;
        int max = index;
        if (leftChild < length && arr[leftChild] > arr[max]) {
            max = leftChild;
        }
        if (rightChild < length && arr[rightChild] > arr[max]) {
            max = rightChild;
        }

        if (max != index) { /* if we found a child with bigger value than its parent*/
            int temp;
            temp = arr[index]; /* then we swap them */
            arr[index] = arr[max];
            arr[max] = temp;
            heapify(arr, length, max); /* and call heapify to reassure vertex is on its place */
        }
    }
    /**
     *
     * @param arr - initial array
     * @return - return sorted array
     */
    public static int[] heapSort(int[] arr) {
        if (arr.length == 0)
            return new int[] {};

        for (int i = arr.length / 2 - 1 ; i >= 0; i--) {
            heapify(arr, arr.length, i);
        }

        for (int i = arr.length - 1; i >= 0; i--) {
            int temp;
            temp = arr[i];
            arr[i] = arr[0];
            arr[0] = temp;

            heapify(arr, i, 0); /* rebuilding a heap */
        }

        return arr;
    }

}
