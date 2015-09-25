package com.mycompany.app;

import java.util.List;      
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;  
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.Test;
// http://www.guru99.com/using-apache-ant-with-selenium.html
public class Program {
    @Test      
        public void TestMethod(){
            WebDriver driver = new FirefoxDriver(); 
              driver.get("http://guru99.com");
                List<WebElement> links = 
driver.findElements(
        By.xpath("//div[@class='canvas-middle']//a"));                                 
 for (WebElement webElement : links) {
     System.out.println(webElement.getAttribute("href"));
  }
  }
}       
