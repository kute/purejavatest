package com.kute.arithmetic;

import java.util.Arrays;

/**
 * created on 2018-07-03 16:29
 */
public class MergeSort {

    public static void main(String[] args) {

        int[] a = {4, 2, 5, 32, 34, 5, 1};
        mergeSort(a, 0, a.length - 1);
        System.out.println(Arrays.toString(a));

    }

    /**
     * 递归
     *
     * @param a
     * @param low
     * @param high
     */
    public static void mergeSort(int[] a, int low, int high) {
        int middle = (low + high) / 2;
        if (low < high) {
            mergeSort(a, low, middle);
            mergeSort(a, middle + 1, high);
            merge(a, low, middle, high);
        }
    }

    public static void merge(int[] a, int low, int middle, int high) {
        int[] temp = new int[high - low + 1];
        int i = low;
        int j = middle + 1;
        int k = 0;
        while (i <= middle && j <= high) {
            if (a[i] < a[j]) {
                temp[k++] = a[i++];
            } else {
                temp[k++] = a[j++];
            }
        }
        while (i <= middle) {
            temp[k++] = a[i++];
        }
        while (j <= high) {
            temp[k++] = a[j++];
        }
        for (int l = 0; l < temp.length; l++) {
            a[low + l] = temp[l];
        }
    }

}
