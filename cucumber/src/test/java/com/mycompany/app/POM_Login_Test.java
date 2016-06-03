package com.mycompany.app;

import static org.junit.Assert.assertTrue;

import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.Ignore;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/*
 * Author : Yu Wang 
 * Date : 2016-04-21
 * The prerequite of the test is that we have valid username: cat and valid password: be123456
 * Most of the test cases we run here are white-box testing.
 * We test UI component and see if they work as expected. 
 * Test cases here is not related to structure of the internal software.
 * 
 * */
public class POM_Login_Test 
{
	private WebDriver driver;
	String userName = "cat";
	String pwd = "be123456";
	String errorMsg = "ERROR";
	
	@Before
	public void setup(){
		driver = new FirefoxDriver();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.navigate().to("http://store.demoqa.com");
		driver.manage().window().maximize();
	}
	
	@After
	public void testDown(){
		driver.quit();
	}
	
  //We try to open the webpage and see if we can and open the right page.
  @Ignore
  @Test
  public void testStorePageTitle(){
    String titleStr = "ONLINE STORE | Toolsqa Dummy Test site";
    assertTrue(HomePage.pageTitle(driver).contentEquals(titleStr));
  }

  //Test the login and logout buttons in UI, see if they can work as expected.

  @Ignore
  @Test
  public void LoginLogoutWithCorrectUsernameAndPassword(){    
    HomePage.login(driver, userName, pwd);
    HomePage.logOut(driver);
    String pageTitle = "Your Account | ONLINE STORE";
    assertTrue(HomePage.pageTitle(driver).contentEquals(pageTitle));
  }

  // login failed with wrong user name and correct password, show wrong response msg 
  @Ignore
  @Test
  public void LoginWithWrongUsernameAndCorrectPassword(){
    String wrongUserName = userName + "tt";

    HomePage.login(driver, wrongUserName, pwd);
    WebDriverWait wait2 = new WebDriverWait(driver, 30);        
    wait2.until(ExpectedConditions.textToBePresentInElement(LoginPage.wrong_Response_Message(driver), errorMsg));  
  }

  // login failed with both wrong user name and wrong password
  @Ignore
  @Test
  public void LoginWithWrongUsernameAndWrongPassword(){
    String wrongUserName = "cattt" + userName;
    String wrongPwd = pwd + "7890";
    HomePage.login(driver, wrongUserName, wrongPwd);
    WebDriverWait wait2 = new WebDriverWait(driver, 30);        
    wait2.until(ExpectedConditions.textToBePresentInElement(LoginPage.wrong_Response_Message(driver), errorMsg));  
  }

  // login successful with empty user name and empty password, notice wrong response msg
  @Ignore
  @Test
  public void LoginWithEmptyUsernameAndEmptyPassword(){
    String emptyMsg = "Please enter your username and password.";
    HomePage.login(driver, " ", " ");
    WebDriverWait wait2 = new WebDriverWait(driver, 30);        
    wait2.until(ExpectedConditions.textToBePresentInElement(LoginPage.wrong_Response_Message(driver), emptyMsg));  
  }
}
