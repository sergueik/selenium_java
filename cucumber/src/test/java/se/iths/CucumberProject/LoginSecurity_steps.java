package se.iths.CucumberProject;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import static org.junit.Assert.assertTrue;

public class LoginSecurity_steps {
	
	public static final WebDriver driver = new FirefoxDriver();


	@Before
	public void setup (){
		driver.get("http:store.demoqa.com");
	}	
	
	/*
	@After
	public void testDown(){
		driver.quit();
	}
	*/
	@Given("^open en firefox browser$")
	public void open_en_firefox_browser() throws Throwable {
		driver.get("http://store.demoqa.com");
		//HomePage.login(driver, "cat", "be123456");
	}

	@When("^Go to page \"([^\"]*)\"$")
	public void go_to_page(String arg1) throws Throwable {
		
        driver.navigate().to("http://store.demoqa.com/products-page/your-account/?login=1");  
		driver.manage().window().maximize();       
        
	}

	@Then("^the page shows \"([^\"]*)\"$")
	public void the_page_shows(String arg1) throws Throwable {
		String remindMsg = "You must be logged in to use this page. Please use the form below to login to your account.";

		assertTrue(LoginPage.account_Login_Remind_Msg(driver).getText().contains(remindMsg));
	}

}
