package org.examples;

import static org.apache.commons.lang3.SystemUtils.IS_OS_MAC;

import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.github.bonigarcia.wdm.ChromeDriverManager;

public class OpenNewTabChromeTest {

  private WebDriver driver;

  @BeforeClass
  public static void setupClass() {
    ChromeDriverManager.getInstance().setup();
  }

  @Before
  public void setupTest() {
    driver = new ChromeDriver();
  }

  @After
  public void teardown() {
    if (driver != null) {
      driver.quit();
    }
  }

  @Test
  public void test() throws Exception {
    // Open URL in default tab
    driver.get("https://wikipedia.org/");

    // If Mac OS X, the key combination is CMD+t, otherwise is CONTROL+t
    int vkControl = IS_OS_MAC ? KeyEvent.VK_META : KeyEvent.VK_CONTROL;
    Robot robot = new Robot();
    robot.keyPress(vkControl);
    robot.keyPress(KeyEvent.VK_T);
    robot.keyRelease(vkControl);
    robot.keyRelease(KeyEvent.VK_T);

    // Wait up to 5 seconds to the second tab to be opened
    WebDriverWait wait = new WebDriverWait(driver, 5);
    wait.until(ExpectedConditions.numberOfWindowsToBe(2));

    // Switch to new tab
    List<String> windowHandles = new ArrayList<>(driver.getWindowHandles());
    System.err.println(windowHandles);
    driver.switchTo().window(windowHandles.get(1));

    // Open other URL in second tab
    driver.get("https://google.com/");
  }

}
