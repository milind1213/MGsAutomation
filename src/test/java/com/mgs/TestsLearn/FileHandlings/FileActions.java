package com.mgs.TestsLearn.FileHandlings;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class FileActions {
    public static void createFile(String filePath) {
        File file = new File(filePath);
        try {
            if (file.createNewFile()) {
                System.out.println("File created successfully");
            } else {
                System.out.println("File already exists");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void writeFile(String filePath) throws IOException {
        FileWriter writer = null;
        try {
            writer = new FileWriter(filePath);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        writer.close();
        System.out.println("File written successfully");
    }

    public static void readFile(String filePath) {
        File file = new File(filePath);
        Scanner reader = null;
        try {
            reader = new Scanner(file);
            while (reader.hasNextLine()) {
                String data = reader.nextLine();
                System.out.println(data);
            }
            reader.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void renameFile(String oldfilepath,String newfilepath) throws FileNotFoundException{
      File old = new File(oldfilepath);
      File newFile = new File(newfilepath);
      if(old.renameTo(newFile)) {
          System.out.println("File renamed successfully");
      } else {
          System.out.println("Failed to rename the file");
      }
    }

    public static void deleteFile(String filePath) {
        File file = new File(filePath);
        if (file.exists()) {
            file.delete();
            System.out.println("File deleted successfully");
        } else {
            System.out.println("File does not exist");
        }
    }
}
