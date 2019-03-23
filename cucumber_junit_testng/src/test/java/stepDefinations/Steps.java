package stepDefinations;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class Steps {
	WebDriver driver = null;

	@Given("^by launching a browser$")
	public WebDriver by_launching_a_browser() throws Throwable {
		System.setProperty("webdriver.chrome.driver", "C:\\Users\\sp369w\\Downloads\\chromedriver.exe");
		driver = new ChromeDriver();
		return driver;

	}

	@Given("^by navigating to the Execute Automation$")
	public void by_navigating_to_the_Execute_Automation() throws Throwable {
		driver.navigate().to("http://executeautomation.com/demosite/Login.html");
	}

	@When("^by providing username and password$")
	public void by_providing_username_and_password() throws Throwable {
		driver.findElement(By.name("UserName")).sendKeys("Rupesh");
		driver.findElement(By.name("Password")).sendKeys("Kumar");
		driver.findElement(By.name("Login")).click();
	}

	@Then("^We will be logged into Execute Automation$")
	public void We_will_be_logged_into_Execute_Automation() throws Throwable {

	}

	@Given("^as already logged in$")
	public void as_already_logged_in() throws Throwable {

	}

	@When("^by logging out$")
	public void by_logging_out() throws Throwable {
		driver.findElement(By.name("")).click();

	}

	@Then("^Have to logout$")
	public void Have_to_logout() throws Throwable {

	}

}
