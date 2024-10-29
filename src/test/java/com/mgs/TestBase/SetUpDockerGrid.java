package com.mgs.TestBase;

import org.testng.annotations.Test;

import java.io.IOException;

import static com.mgs.CommonConstants.CURRENT_DIRECTORY;

public class SetUpDockerGrid {
    @Test(priority = 1)
    public void startDockerGrid()
    {
        try {
            Process process = Runtime.getRuntime().exec("cmd /c start /wait " + CURRENT_DIRECTORY + "/Resource/startDockerGrid.bat");
            process.waitFor();
            System.out.println("Docker Grid started successfully.");
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException("Failed to start Docker Grid", e);
        }
    }

    @Test(priority = 2)
    public void stopDockerGrid() throws IOException
    {
        try {
            Process process = Runtime.getRuntime().exec("cmd /c start /wait "+CURRENT_DIRECTORY+"/Resource/stopDockerGrid.bat");
            process.waitFor();
            Runtime.getRuntime().exec("taskkill /f /im cmd.exe");
            System.out.println("Docker Grid stopped, and all command prompts are closed.");
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException("Failed to stop Docker Grid", e);
        }
    }
}
