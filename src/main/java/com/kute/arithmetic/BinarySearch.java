package com.kute.arithmetic;

import java.util.Arrays;

/**
 * created on 2018-07-09 10:59
 */
public class BinarySearch {

    public static void main(String[] args) {

        int[] a = {4,3,45,40,2,56,20, 6};

        MergeSort.mergeSort(a, 0, a.length - 1);

        System.out.println(Arrays.toString(a));

        int keyword = 20;

        System.out.println(binarySearch(a, keyword));
        System.out.println(recursionBinarySearch(a, 0, a.length - 1, keyword));

    }

    public static boolean binarySearch(int[] a, int keyword) {
        int low = 0;
        int high = a.length - 1;
        while(low <= high) {
            int middle = (low + high) / 2;
            if(a[middle] == keyword) {
                System.out.println(middle);
                return true;
            } else if(a[middle] < keyword) {
                low = middle + 1;
            } else {
                high = middle - 1;
            }
        }
        return false;
    }

    public static boolean recursionBinarySearch(int[] a, int low, int high, int keyword) {
        if(low > high) {
            return false;
        }
        int middle = (low + high) / 2;
        if(a[middle] == keyword) {
            System.out.println(middle);
            return true;
        } else if(a[middle] < keyword) {
            low = middle + 1;
        } else {
            high = middle - 1;
        }
        return recursionBinarySearch(a, low, high, keyword);
    }

}
