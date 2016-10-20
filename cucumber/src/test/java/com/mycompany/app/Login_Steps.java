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

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

/*
 * Author : Yu Wang 
 * Date : 2016-04-07
 * The prerequite of the test is that we have valid username: cat and valid password: be123456
 */

public class Login_Steps {
  
	public static WebDriver driver;
	public static WebDriverWait wait;
	String emptyMsg = "Please enter your username and password.";
	String pageTitle = "Your Account | ONLINE STORE";

	@Before
	public void setup() {
		driver = new FirefoxDriver();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.manage().window().maximize();
		WebDriverWait wait = new WebDriverWait(driver, 30);
	}

	@After
	public void testDown() {
		driver.quit();
	}

	@Given("^I go to the start page$")
	public void i_go_to_the_start_page() throws Throwable {
    // cucumber does not appear to honor junit's Before / After annotation
    setup();
		driver.navigate().to("http://store.demoqa.com");
	}

	@When("^I enter username \"([^\"]*)\" and password \"([^\"]*)\"$")
	public void i_enter_username_and_password(String userName, String pwd)
			throws Throwable {
		HomePage.login(driver, userName, pwd);
	}

	@Then("^I am logged in$")
	public void i_am_logged_in() throws Throwable {
		assertTrue(HomePage.pageTitle(driver).contentEquals(pageTitle));
	}

	@Then("^error message should throw$")
	public void error_message_should_throw() throws Throwable {
		String errorMsg = "ERROR";
		wait.until(ExpectedConditions.textToBePresentInElement(
				LoginPage.wrong_Response_Message(driver), errorMsg));
	}

	@Then("^empty notice message should throw$")
	public void empty_notice_message_should_throw() throws Throwable {
		String emptyMsg = "Please enter your username and password.";
		wait.until(ExpectedConditions.textToBePresentInElement(
				LoginPage.wrong_Response_Message(driver), emptyMsg));
	}
  
	@Ignore
	@When("^I Go to page \"([^\"]*)\"$")
	public void go_to_page(String arg1) throws Throwable {
		driver.navigate().to( arg1 );
	}


  // Cannot stack annotations:  cucumber.runtime.DuplicateStepDefinitionException
	@Given("^I go to Account page 2$")
	public void go_to_page1() throws Throwable {
		driver.navigate().to(
				"http://store.demoqa.com/products-page/your-account/?login=1");
	}

	@When("^I go to Account page 1$")
	public void go_to_page2() throws Throwable {
		driver.navigate().to(
				"http://store.demoqa.com/products-page/your-account/?login=1");
	}

	@Ignore
	@Then("^The page shows \"([^\"]*)\"$")
	public void the_page_shows(String arg1) throws Throwable {
		String remindMsg = arg1;
		assertTrue(LoginPage.account_Login_Remind_Msg(driver).getText()
				.contains(remindMsg));
	}
}
