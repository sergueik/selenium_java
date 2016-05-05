package com.mycompany.app;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;
import org.testng.annotations.Test;

public class Test4 extends TestBase{

  @Test
  public void testLink()throws Exception{
    getDriver().get("https://twitter.com");
    WebElement textBox = getDriver().findElement(By.xpath("//label[text()='full name']"));
    textBox.click();
    textBox.sendKeys("Test 1!");
    Thread.sleep(2000);
  }
}

