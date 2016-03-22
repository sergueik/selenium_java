package tests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import retry.RetryAnalyzer;
import retry.TestListener;

import static org.junit.Assert.assertTrue;

@Listeners(TestListener.class)
public class Test {
    public static int cnt_retries_1 = 0;
    public static int cnt_retries_2 = 0;
    
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
    driver.findElement(By.cssSelector("[href='/ru/dashboard']")).click();
    if(cnt_retries_1 == 1){
    		System.err.println("Failed");
    		assertTrue(false);
    	} else {
    		System.err.println("Passed");
    		assertTrue(true);
    	}
    cnt_retries_1 ++ ;
  }

  @org.testng.annotations.Test (retryAnalyzer = RetryAnalyzer.class)
  public void findMusic () {
    driver.findElement(By.cssSelector("[href='/ru/music']")).click();
    if (cnt_retries_2 < 2){
    		System.err.println("Failed in run " + cnt_retries_2 );
    		assertTrue(false);
    	} else {
    		System.err.println("Passed in run "  + cnt_retries_2);
    		assertTrue(true);
    	}
    cnt_retries_2 ++ ;
  }

  @org.testng.annotations.Test (retryAnalyzer = RetryAnalyzer.class)
  public void findEvents () {
    driver.findElement(By.cssSelector("[href='/ru/events']")).click();
  }
  
  @org.testng.annotations.Test (enabled=false , retryAnalyzer = RetryAnalyzer.class)
  public void findFeatures () {
    driver.findElement(By.cssSelector("[href='ru/featuresERROR']")).click();
  }
}
