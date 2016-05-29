package com.mycompany.pagetype;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import org.springframework.stereotype.Component;

import com.jprotractor.NgBy;
import com.jprotractor.NgWebDriver;
import com.jprotractor.NgWebElement;

import com.mycompany.api.ProtractorDriver;

@Component
public class AngularPage {

  private final By _searchBox = NgBy.repeater("item in items"); 

  public void isPageDisplayed(String url) {
    ProtractorDriver.ngDriver.get(url);
    ProtractorDriver.waitForElementVisible(_searchBox);
  }
}
