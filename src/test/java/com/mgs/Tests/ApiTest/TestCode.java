package com.mgs.Tests.ApiTest;

import java.util.Arrays;

public class TestCode {

    public static void main(String[] args) {
        int[] nums = { 1, 1, 0, 0, 1, 1, 1, 0 };
        maxCount(nums);
    }
    static void maxCount(int[] a) {
        int currentcount = 1;
        int maxcount = 1;
        int maxElement = a[0];
        for (int i =1; i < a.length; i++) {
            if(a[i]==a[i-1]) {
                currentcount ++;
            } else {
                currentcount = 1;
            }
            if(currentcount > maxcount) {
                maxcount =currentcount;
                maxElement =a[i];
            }
        }
        System.out.print(maxcount);
    }
}
