package com.mycompany.app;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import static org.junit.Assert.assertTrue;

import java.util.concurrent.TimeUnit;

import org.junit.Test;
import org.junit.Ignore;

public class LoginSecurity_steps {

	public static WebDriver driver;
	public static WebDriverWait wait;

	@Before
	public void setup() {
		driver = new FirefoxDriver();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.manage().window().maximize();
		WebDriverWait wait = new WebDriverWait(driver, 30);
	}

	/*
	 * @After public void testDown(){ driver.quit(); }
	 */

	@Ignore
	@Given("^open en firefox browser$")
	public void open_en_firefox_browser() throws Throwable {
		driver.get("http://store.demoqa.com");
		// HomePage.login(driver, "cat", "be123456");
	}

	@Ignore
	@When("^Go to page \"([^\"]*)\"$")
	public void go_to_page(String arg1) throws Throwable {
		driver.navigate().to(
				"http://store.demoqa.com/products-page/your-account/?login=1");
	}

	@Ignore
	@Then("^the page shows \"([^\"]*)\"$")
	public void the_page_shows(String arg1) throws Throwable {
		String remindMsg = "You must be logged in to use this page. Please use the form below to login to your account.";
		assertTrue(LoginPage.account_Login_Remind_Msg(driver).getText()
				.contains(remindMsg));
	}

}
