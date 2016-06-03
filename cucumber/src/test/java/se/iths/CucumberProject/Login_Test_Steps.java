package se.iths.CucumberProject;

import static org.junit.Assert.assertTrue;

import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

/*
 * Author : Yu Wang 
 * Date : 2016-04-07
 * The prerequite of the test is that we have valid username: cat and valid password: be123456
 * Most of the test cases we run here are white-box testing.
 * We test UI component and see if they work as expected. 
 * Test cases here is not related to structure of the internal software.
 * 
 */
public class Login_Test_Steps{
	public static final WebDriver driver = new FirefoxDriver();
	
	String emptyMsg = "Please enter your username and password.";
	String pageTitle = "Your Account | ONLINE STORE";
	
	@Before
	public void setup(){
		/*
		 * GET method will get a page to load or get page source or get text that’s all. 
		 * GET will wait till the whole page gets loaded i.e. 
		 * the onload event has fired before returning control to our test or script. 
		 * If our pages uses lot of AJAX then we can’t know that 
		 * when our pages has completely loaded. To overcome this we can use WAIT.
		 */
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		
	}
	
	@After
	public void testDown(){
		driver.quit();
	}
	
	/*
	 * NAVIGATE will just redirect to our required page and will not wait. 
	 * It will guide us through the history like refresh, back, forward. 
	 * For example if we want to move forward and do some functionality 
	 * and back to the home page then this can be achieved through navigate() only.
	 * driver.navigate().forward(); driver.navigate().back();
	 * 
	 */
	@Given("^I go to the start page$")
	public void i_go_to_the_start_page() throws Throwable {
		driver.get("http://store.demoqa.com");
		driver.manage().window().maximize();

	}

	@When("^I enter username \"([^\"]*)\" and password \"([^\"]*)\"$")
	public void i_enter_username_and_password(String userName, String pwd) throws Throwable {
		HomePage.login(driver, userName, pwd);
	}

	@Then("^I am logged in$")
	public void i_am_logged_in() throws Throwable {
		assertTrue(HomePage.pageTitle(driver).contentEquals(pageTitle));
	}

	@Then("^error message should throw$")
	public void error_message_should_throw() throws Throwable {
		
		String errorMsg = "ERROR";
		
		WebDriverWait wait2 = new WebDriverWait(driver, 30);        
        wait2.until(ExpectedConditions
        		.textToBePresentInElement(LoginPage.wrong_Response_Message(driver), errorMsg));
        
	}
	
	@Then("^empty notice message should throw$")
	public void empty_notice_message_should_throw() throws Throwable {
		
    	String emptyMsg = "Please enter your username and password.";
    	
    	WebDriverWait wait2 = new WebDriverWait(driver, 30);        
        wait2.until(ExpectedConditions.textToBePresentInElement(LoginPage.wrong_Response_Message(driver), emptyMsg));  
	}


}
