package com.kute.arithmetic;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by kute on 2017/5/26.
 */
public class Sort {


    public static void main(String[] args) {

        Integer[] data = new Integer[10];
        for(int i=1; i<=10; i++) {
            data[i - 1] = i;
        }
        List<Integer> list = Arrays.asList(data);
        Collections.shuffle(list);

        Integer[] ary = list.toArray(new Integer[list.size()]);
        print(ary);
//        bubble(ary);
        select(ary);

        Integer[] a = {1, 3, 5, 7, 10};
        Integer[] b = {2, 6, 8, 11, 15, 20};

    }

    /**
     * O(N^2) 不稳定
     * @param a
     */
    public static void select(Integer[] a) {
        int size = a.length;
        for(int i=0; i<size - 1; i++) {
            int min = i;
            for(int j=i+1; j<size; j++) {
                if(a[min] > a[j]) {
                    min = j;
                }
            }
            if(i != min) {
                int temp = a[i];
                a[i] = a[min];
                a[min] = temp;

            }
        }
        print(a);
    }

    /**
     * O(N^2) 稳定排序
     * @param a
     */
    public static void bubble(Integer[] a) {
        int size = a.length;
        for(int i=0; i<size - 1; i++) {
            for(int j=0; j<size - i - 1; j++) {
                if(a[j] > a[j + 1]) {
                    int tmp = a[j + 1];
                    a[j + 1] = a[j];
                    a[j] = tmp;
                }
            }
        }
        print(a);
    }

    public static void print(Integer[] ary) {
        System.out.println(Arrays.asList(ary));
    }

}
