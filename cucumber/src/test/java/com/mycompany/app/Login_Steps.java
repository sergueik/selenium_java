package com.mycompany.app;

import static org.junit.Assert.assertTrue;

import java.util.concurrent.TimeUnit;

// for 1.1.5 and above
import cucumber.api.java.Before;
import cucumber.api.java.After;

// for 1.1.4 and below
// import cucumber.annotation.After;
// import cucumber.annotation.Before;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.When;
import cucumber.api.java.en.Then;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/*
 * Author : Serguei Kouzmine
 * Author : Yu Wang
 * Date : 2016-04-07
 * The prerequisite of the test is that we have valid username/password: cat/be123456
 */

public class Login_Steps {

	public static WebDriver driver;
	public static WebDriverWait wait;
	String emptyMsg = "Please enter your username and password.";
	String pageTitle = "Your Account | ONLINE STORE";

	@Before("@Regular")
	public void setup() {
		driver = new FirefoxDriver();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.manage().window().maximize();
		WebDriverWait wait = new WebDriverWait(driver, 30);
		LoginPage.setDriver(driver);
		HomePage.setDriver(driver);
	}

	@After("@Regular")
	public void tearDown() {
		driver.quit();
	}

  // -- for screnario: Login Page Security Warning

	@Given("^I go to the start page$")
	public void goStartPage() throws Throwable {
		driver.navigate().to("http://store.demoqa.com");
	}

	// Cannot create duplicates annotations in the same source. Uncommenting the
	// following line
	// @Given("^I go to Account page$")
	// will raise cucumber.runtime.DuplicateStepDefinitionException
	@When("^I go to Account page$")
	public void goToAccountPage() throws Throwable {
		driver.navigate().to(
				"http://store.demoqa.com/products-page/your-account/?login=1");
	}

	// Can not create duplicates - e.g. Some_Other_Steps cannot have its own
	// version of pageShows
	@Then("^The page shows \"([^\"]*)\"$")
	public void pageShows(String messsage) throws Throwable {
		assertTrue(LoginPage.accountMessage().getText().contains(messsage));
	}

	// for scenario: Ability to Login

	@When("^I enter username \"([^\"]*)\" and password \"([^\"]*)\"$")
	public void loginWithUsernameAndPassword(String userName, String pwd)
			throws Throwable {
		HomePage.login(userName, pwd);
	}

	@Then("^I am logged in$")
	public void confirmLoggedIn() throws Throwable {
		assertTrue(HomePage.pageTitle().contentEquals(pageTitle));
	}

	// for scenario: Failed Login

	@Then("^error message should throw$")
	public void errorMessage() throws Throwable {
		assertTrue(LoginPage.isResponseMessageShown("ERROR"));
	}

	@Then("^empty notice message should throw$")
	public void noticeMessage() throws Throwable {
		assertTrue(LoginPage
				.isResponseMessageShown("Please enter your username and password."));
	}

}
