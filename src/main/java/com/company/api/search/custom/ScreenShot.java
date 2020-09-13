package com.company.api.search.custom;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.io.File;
import java.io.IOException;

public class ScreenShot {
    public static void run() {
//        ChromeOptions options = new ChromeOptions();
//        options.addArguments("--start-maximized", "--start-fullscreen");
//
//// Creating a driver instance with the previous capabilities
//        WebDriver driver = new ChromeDriver(options);
//
//// Load page & take screenshot of full-screen page
//        driver.get("http://google.com");
//        File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);

        System.setProperty("webdriver.chrome.driver","./chromedriver.exe");
        WebDriver driver = new ChromeDriver();
        driver.get("https://www.google.com/search?q=Azula");
        File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
        try {
            FileUtils.copyFile(scrFile, new File("creen"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
