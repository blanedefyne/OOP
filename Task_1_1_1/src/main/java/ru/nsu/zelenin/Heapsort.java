package ru.nsu.zelenin;

/**
 * Пирамидальная сортировка (сортировка кучей).
 */

public class Heapsort {

    /** heapify method sifts down vertex on its right position.
     *
     * @param arr - array containing all the vertexes
     * @param length - length of that array
     * @param index - inner vertex we work with
     */

    public static void heapify(int[] arr, int length, int index) {
        int leftChild = index * 2 + 1;
        int rightChild = index * 2 + 2;
        int max = index;
        if (leftChild < length && arr[leftChild] > arr[index]) {
            max = leftChild;
        }

        if (rightChild < length && arr[rightChild] > arr[max]) {
            max = rightChild;
        }

        if (max != index) {
            int temp = arr[index];
            arr[index] = arr[max];
            arr[max] = temp;
            heapify(arr, length, max);
        }

    }

    /** heapSort itself sorts the given array.
     *
     * @param arr - initial array
     * @return - method returns sorted array
     */

    public static int[] heapSort(int[] arr) {
        if (arr.length == 0) {
            return new int[0];
        } else {
            int i;
            for (i = arr.length / 2 - 1; i >= 0; --i) {
                heapify(arr, arr.length, i);
            }

            for (i = arr.length - 1; i >= 0; --i) {
                int temp = arr[i];
                arr[i] = arr[0];
                arr[0] = temp;
                heapify(arr, i, 0);
            }

            return arr;
        }
    }

    /** does nothing - just to run script properly.
     *
     * @param args - common main method parameters
     */

    public static void main(String[] args) {
    }
}
