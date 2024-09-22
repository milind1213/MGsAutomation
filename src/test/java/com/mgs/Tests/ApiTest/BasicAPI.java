package com.mgs.Tests.ApiTest;
import java.io.*;
import java.util.LinkedHashSet;
import java.util.Set;

public class BasicAPI {
    public static void main(String[] args) {
        String str = "abcabcebb";
        longestSubstring(str);
    }
    private static void longestSubstring(String str) {
        int start = 0;
        int end = 0;
        int maxLength = 0;
        int maxStart = 0;
        Set<Character> set = new LinkedHashSet<>();
        while (end < str.length()) {
            if (!set.contains(str.charAt(end))) {
                set.add(str.charAt(end));
                end++;
                if (end - start > maxLength) {
                    maxLength = end - start;
                    maxStart = start;
                }
            } else {
                set.remove(str.charAt(start));
                start++;
            }
        }
        String maxSubString = str.substring(maxStart, maxStart + maxLength);
        System.out.println(maxSubString);
    }
}
