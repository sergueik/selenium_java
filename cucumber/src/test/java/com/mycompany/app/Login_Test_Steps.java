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

public class Login_Test_Steps {
  
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

	// @Ignore
	@Given("^I go to the start page$")
	public void i_go_to_the_start_page() throws Throwable {
		driver.get("http://store.demoqa.com");
	}

	// @Ignore
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
}
