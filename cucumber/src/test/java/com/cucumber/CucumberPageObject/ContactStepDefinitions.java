package com.cucumber.CucumberPageObject;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import cucumber.api.DataTable;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import junit.framework.Assert;

public class ContactStepDefinitions extends AbstractPageStepDefinition
{
	
	WebDriver driver = getDriver();
	LandingPage landingPage;
	ContactPage contactPage;
	ContactConfirmPage contactConfirmationPage;
	
	@Given("^I navigate to zoo site$")
	public void i_navigate_to_zoo_site() throws Throwable {
		//driver.navigate().to("http://www.thetestroom.com/webapp");
		landingPage = new LandingPage();
		landingPage.navigateToWebApp();
			
	}

/*	@When("^I click on a Contact button$")
	public void i_click_on_a_Contact_button() throws Throwable {
		
		driver.findElement(By.id("contact_link")).click();
	 
	}*/
	
	@When("^I click on a \"([^\"]*)\" button$")
	public void i_click_on_a_button(String link) throws Throwable {
	   
		//driver.findElement(By.id(link+"_link")).click();
		//landingPage = new LandingPage(driver);
		contactPage = landingPage.navigateToContactPage(link);
	}

	@Then("^I check I am on contact page$")
	public void i_check_I_am_on_contact_page() throws Throwable {
		System.out.println(driver.findElement(By.cssSelector(".content h1")).getText());
		
	  Assert.assertTrue(driver.findElement(By.cssSelector(".content h1")).getText().contains("CONTACT US"));
	    
	}

	@Then("^I fill in the Contact Form$")
	public void i_fill_in_the_Contact_Form(DataTable table) throws Throwable {
		
		List<List<String>> data = table.raw();
		contactPage.setNameField(data.get(1).get(1));
		contactPage.setAddressField(data.get(2).get(1));
		contactPage.setEmailField(data.get(4).get(1));
		contactPage.setPostCodeField(data.get(3).get(1));
		
		
		
		/* With Data Table
		 * List<List<String>> data = table.raw();
	 	driver.findElement(By.name("name_field")).sendKeys(data.get(1).get(1));
		driver.findElement(By.id("rdona")).click();
		driver.findElement(By.id("cadop")).click();
		driver.findElement(By.name("address_field")).sendKeys(data.get(2).get(1));
		driver.findElement(By.name("postcode_field")).sendKeys(data.get(3).get(1));
		driver.findElement(By.name("email_field")).sendKeys(data.get(4).get(1));*/
		
		/* With out DataTable
		 * driver.findElement(By.name("name_field")).sendKeys("Rajesh");
		driver.findElement(By.id("rdona")).click();
		driver.findElement(By.id("cadop")).click();
		driver.findElement(By.name("address_field")).sendKeys("222 commerce");
		driver.findElement(By.name("postcode_field")).sendKeys("92620");
		driver.findElement(By.name("email_field")).sendKeys("rajesh@gmail.com");*/
			    
	}

	@Then("^I submit the form$")
	public void i_submit_the_form() throws Throwable {
	    		//driver.findElement(By.id("submit_message")).click();
		contactConfirmationPage = contactPage.submitForm();
	}

	@Then("^I check the confirmation message$")
	public void i_check_the_confirmation_message() throws Throwable {
	   //Assert.assertTrue(driver.findElement(By.cssSelector(".content h1")).getText().contains("We have your message"));		
		Assert.assertTrue(contactConfirmationPage.getPageTitle().contains("We have your message"));
	}
	
	@Then("^I close the Brower$")
	public void i_close_the_Brower() throws Throwable {
	    
	  driver.quit();
	}
	
	


}
