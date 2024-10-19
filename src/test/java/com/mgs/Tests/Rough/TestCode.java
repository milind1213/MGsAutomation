package com.mgs.Tests.Rough;
import java.util.*;
public class TestCode {
    public  static void main(String [] args){
       String str = "tb";
       boolean ans =  isOvelContains(str);
       System.out.println(ans);
    }
   static boolean isOvelContains(String str){
       String a =  str.toLowerCase();
        char [] ch = a.toCharArray();
        for( char c: ch ){
            if(c == 'a' || c=='e' || c=='i' || c=='o' || c=='u'){
                return true;
            }
        }
        return false ;
    }
}
