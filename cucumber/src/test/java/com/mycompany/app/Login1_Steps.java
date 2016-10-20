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

public class Login1_Steps {

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

	@Given("^I go to the start page$")
	public void goStartPage() throws Throwable {
    // Cucumber framework does not appear to recognize Before / After annotation of the Junit framework 
    setup();
		driver.navigate().to("http://store.demoqa.com");
	}

  // Cannot create duplicates annotations in the same source. Uncommenting the following line
	// @Given("^I go to Account page$")
  // will raise cucumber.runtime.DuplicateStepDefinitionException
	@When("^I go to Account page$")
	public void goToAccountPage() throws Throwable {
		driver.navigate().to(
				"http://store.demoqa.com/products-page/your-account/?login=1");
	}

  // Can not create duplicates - e.g. Login2_Steps cannot have its own version of pageShows
	@Then("^The page shows \"([^\"]*)\"$")
	public void pageShows(String arg1) throws Throwable {
		String remindMsg = arg1;
		assertTrue(LoginPage.account_Login_Remind_Msg(driver).getText()
				.contains(remindMsg));
	}
}
