package com.mgs.Tests.WebTests;
import java.util.*;
public class CodingTest {
	public static void main(String[] args) {
       String str = "MilindGhongade";
	    System.out.println(reverse(str));
	}
	static String reverse(String str) {
		if (str.length() == 0) {
            return str;
        } else {
            return reverse(str.substring(1)) + str.charAt(0);
        }
	}
}