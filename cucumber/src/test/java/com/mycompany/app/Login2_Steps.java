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
 * Author : Serguei Kouzmine
 * Author : Yu Wang
 * Date : 2016-04-07
 * The prerequite of the test is that we have valid username: cat and valid password: be123456
 */

public class Login2_Steps {

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
	public void tearDown() {
		driver.quit();
	}

  // Cannot stack annotations:  cucumber.runtime.DuplicateStepDefinitionException
	// @Given("^I go to Account page$")
	public void goToAccountPage() throws Throwable {
    // cucumber does not appear to recognize junit's Before / After annotation
    setup();
		driver.navigate().to(
				"http://store.demoqa.com/products-page/your-account/?login=1");
	}

	@When("^I enter username \"([^\"]*)\" and password \"([^\"]*)\"$")
	public void loginWithUsernameAndPassword(String userName, String pwd)
			throws Throwable {
		HomePage.login(driver, userName, pwd);
	}

	@Then("^I am logged in$")
	public void loggedIn() throws Throwable {
		assertTrue(HomePage.pageTitle(driver).contentEquals(pageTitle));
	}

  // pageShows(String arg1) is defined in Login1_Steps
}
