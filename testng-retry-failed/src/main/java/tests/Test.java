package tests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import retry.RetryAnalyzer;
import retry.TestListener;

/**
 * Created by user on 07.12.15.
 */

@Listeners(TestListener.class)
public class Test {
    private static WebDriver driver;

    @BeforeClass
    public static void init () {
        driver = new FirefoxDriver();
        driver.get("http://www.last.fm/ru/");
    }

    @AfterClass
    public static void close () {
        driver.close();
    }

    @org.testng.annotations.Test (retryAnalyzer = RetryAnalyzer.class)
    public void findLive () {
        driver.findElement(By.cssSelector("[href=\"/ru/dashboard\"]")).click();
    }

    @org.testng.annotations.Test (retryAnalyzer = RetryAnalyzer.class)
    public void findMusic () {
        driver.findElement(By.cssSelector("[href=\"/ru/music\"]")).click();
    }

    @org.testng.annotations.Test (retryAnalyzer = RetryAnalyzer.class)
    public void findEvents () {
        driver.findElement(By.cssSelector("[href=\"/ru/events\"]")).click();
    }

    @org.testng.annotations.Test (retryAnalyzer = RetryAnalyzer.class)
    public void findFeatures () {
        driver.findElement(By.cssSelector("[href=\"/ru/featuresERROR\"]")).click();
    }



}
