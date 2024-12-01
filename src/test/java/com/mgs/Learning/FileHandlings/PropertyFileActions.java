package com.mgs.Learning.FileHandlings;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class PropertyFileActions {
    public static void writeProperty(String filePath) throws IOException {
        Properties prop = new Properties();
        prop.setProperty("Name", "Milind");
        prop.setProperty("Age", "30");
        prop.setProperty("City", "Pune");
        prop.setProperty("PinCode", "123456");

        FileOutputStream fis = new FileOutputStream(filePath);
        try {
            prop.store(fis, "UserDetails");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        fis.close();
        System.out.println("Properties have been updated");
    }


    public static void readProperty(String filePath) throws IOException {
        Properties prop = new Properties();
        FileInputStream fis = new FileInputStream(filePath);
        try {
            prop.load(fis);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Name: " + prop.getProperty("Name"));
        System.out.println("Age: " + prop.getProperty("Age"));
        System.out.println("City: " + prop.getProperty("City"));
        System.out.println("PinCode: " + prop.getProperty("PinCode"));
    }

    public static void main(String[] args) throws IOException {
        String path = System.getProperty("user.dir") + "\\TestData\\user.properties";
        writeProperty(path);
        readProperty(path);
    }
}
