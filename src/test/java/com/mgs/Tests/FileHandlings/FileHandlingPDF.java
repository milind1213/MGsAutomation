package com.mgs.Tests.FileHandlings;
import java.io.File;
import java.io.IOException;

public class FileHandlingPDF {
   public void readPDFFile(String filename) throws IOException {
        File file = new File(filename);


    }

    public static void main(String[] args) {
        FileHandlingPDF fileHandlingPDF = new FileHandlingPDF();
        try {
            fileHandlingPDF.readPDFFile("example.pdf");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
