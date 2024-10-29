package com.mgs.Tests.Rough;

import io.github.bonigarcia.wdm.WebDriverManager;
import io.restassured.response.Response;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.URL;
import java.util.*;

public class TestCode {
    public static void main(String[] args) {
        String input = "my name is milind";
        char ch;
        String rev = "";
        for (int i = 0; i < input.length(); i++) {
            ch = input.charAt(i);
            rev = ch + rev;
        }
        System.out.println("Reversed String is : " + rev);
    }


}