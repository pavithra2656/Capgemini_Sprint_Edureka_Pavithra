package com.edureka.utilities;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Properties;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

public class ScreenShot {

    private ScreenShot() {
    }

    public static String captureScreenshot(
            WebDriver driver,
            String testName,
            Properties properties) throws IOException {

        if (driver == null) {
            throw new IllegalArgumentException("WebDriver cannot be null.");
        }

        if (!(driver instanceof TakesScreenshot)) {
            throw new IllegalArgumentException("This WebDriver does not support screenshots.");
        }

        String directory = properties.getProperty("screenshotDirectory", "Screenshots");

        Path screenshotPath = Paths.get(System.getProperty("user.dir"), directory);

        Files.createDirectories(screenshotPath);

        String safeTestName = testName.replaceAll("[^a-zA-Z0-9_-]", "_");

        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss_SSS"));

        String fileName = safeTestName + "_" + timestamp+ "_" + Thread.currentThread().getId()+ ".png";

        File source = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);

        Path destination = screenshotPath.resolve(fileName);

        Files.copy(source.toPath(), destination);

        return destination.toAbsolutePath().toString();
    }
}
