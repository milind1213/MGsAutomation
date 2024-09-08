package com.mgs.Tests.WebTests;

import java.util.*;

public class TestCode {
    public static void main(String[] args) {
        int[] array = {5, 2, 9, 1, 6};
        sortArray(array);
    }
    public static void sortArray(int[] array) {
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array.length; j++) {
                if (array[i] <array[j]) {
                    int temp = array[j];
                    array[j] = array[i];
                    array[i] = temp;
                }
            }
        }
        System.out.println("Sorted array: " + Arrays.toString(array));
    }

}