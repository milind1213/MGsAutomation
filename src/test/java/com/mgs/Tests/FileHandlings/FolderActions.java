package com.mgs.Tests.FileHandlings;
import java.io.File;

public class FolderActions {
    public static void createFolder(String folderPath) {
        File folder = new File(folderPath);
        if (!folder.exists()) {
            folder.mkdirs(); // Creates parent directories if necessary
            System.out.println("Folder created : " + folderPath);
        } else {
            System.out.println("Folder already exists: " + folderPath);
        }
    }

    public static boolean isFolderExists(String folderPath) {
        File folder = new File(folderPath);
        return folder.exists();
    }

    public static void renameFolder(String oldPath, String newPath) {
        File olderFolder = new File(oldPath);
        File newFolder = new File(newPath);
        if (olderFolder.exists()) {
            olderFolder.renameTo(newFolder);
            System.out.println("Folder reNamed To :" + newFolder);
        }
    }

    public static void deleteFolder(String path) {
        File folder = new File(path);
        if (folder.exists()) {
            folder.delete();
            System.out.println("Folder Deleted :" + folder);
        }
    }

}
