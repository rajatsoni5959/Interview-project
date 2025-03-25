package com.utils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
public class ScreenshotUtil {
    public static void captureScreenshot(WebDriver driver, String screenshotName) {
        try {
            
            File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
           
            String destination = "screenshots/" + screenshotName + ".png";
            
            Files.createDirectories(Paths.get("screenshots"));
           
            Files.copy(screenshot.toPath(), Paths.get(destination));
            System.out.println("Screenshot captured: " + destination);
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Failed to capture screenshot: " + e.getMessage());
        }
    }
}