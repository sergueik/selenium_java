package se.iths.CucumberProject;

import static org.junit.Assert.assertEquals;
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
 * Author: YU WANG
 * Date: 2016-04-21
 * 
 * White-box testing: test UI component 
 * Branch testing. (test cases: buyOneItemCheckOutWithOutLogin and buyOneItemContinueWithOutoginIn)
 * Use case testing
 * Boundary testing --- checkOutWithEmptyShoppingCartWiutoginInCheckCounter
 * Test cases cover different senarios/flows.
 * Most test cases here are assumed that user is not logged in the system.
 * The only test cases that need login info is when test the whole purchase process.
 * 
 */
public class ShoppingCart_steps  
{
	public static final WebDriver driver = new FirefoxDriver();
	
	String itemName;
	double itemPrice;
	String popUpCheckOutBtnLabel = "go_to_checkout";
	int originItemCounter;
	
	@Before
	public void setup(){

		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}
	
	@After
	public void testDown(){
		driver.quit();
	}
	
	@Given("^I am not logged in$")
	public void i_am_not_logged_in() throws Throwable {
	    
	}

	/*
	 * NAVIGATE will just redirect to our required page and will not wait. 
	 * It will guide us through the history like refresh, back, forward. 
	 * For example if we want to move forward and do some functionality 
	 * and back to the home page then this can be achieved through navigate() only.
	 * driver.navigate().forward(); driver.navigate().back();
	 * 
	 */

	@Given("^open start page$")
	public void open_start_page() throws Throwable {
	
		driver.get("http://store.demoqa.com");
		driver.manage().window().maximize();

	}
	@When("^I buy one item$")
	public void i_buy_one_item() throws Throwable {
		
		clickBuyNow_btn();
    	addOneItemInShoppingCart();
	}
	
	@Then("^I continue shopping$")
	public void i_continue_shopping() throws Throwable {
	   	
    	AlertPopupPage.popup_Btn("continue_shopping", driver).click();
    	Thread.sleep(2000);	    	 
	}

	@Then("^I check out$")
	public void i_check_out() throws Throwable {
	   	AlertPopupPage.popup_Btn(popUpCheckOutBtnLabel, driver).click(); 
	   	Thread.sleep(1000);
   
	   	CheckOut_YouCart_Page.Continue_Btn(driver).click();
	   	Thread.sleep(1000);      
	}
	
	@Then("^I log in with my accout$")
	public void i_log_in_with_my_accout() throws Throwable {
       
		CheckOut_Info_Page.login(driver, "cat", "be123456");
	}


	@Then("^I complete en shopping action$")
	public void i_complete_en_shopping_action() throws Throwable {		
	    
	    Thread.sleep(3000);
	    CheckOut_Info_Page.clickPurchase(driver);
	    assertEquals("Transaction Results | ONLINE STORE", driver.getTitle());
	}


	@When("^I check the shopping cart$")
	public void i_check_the_shopping_cart() throws Throwable {
		
		HomePage.checkOut(driver);
	  	Thread.sleep(1000);
	}

	@Then("^I get empty notice msg$")
	public void i_get_empty_notice_msg() throws Throwable {
		
	  	String emptyMsg = "Oops, there is nothing in your cart.";
	  	
	  	assertEquals(emptyMsg, CheckOut_Info_Final_Page.getEmptyPageMsg(driver));

}

	@When("^I buy one item for (\\d+) times$")
	public void i_buy_one_item_for_times(int counter) throws Throwable {
    	originItemCounter = HomePage.getItemNumber(driver);
    	HomePage.clickBuyNow_btn(driver);
    	
    	for(int i = 0; i < counter; i++){
    		addOneItemInShoppingCart();
    		AlertPopupPage.popup_Btn("continue_shopping", driver).click(); 
    		Thread.sleep(5000);
    	}
	}
	
	@Then("^I get item counter will be (\\d+)$")
	public void i_get_item_counter_will_be(int counter) throws Throwable {
		
    	int changedItemCounter = HomePage.getItemNumber(driver);
    	assertEquals(counter, (changedItemCounter - originItemCounter));
	}

	
	 public void clickBuyNow_btn(){
	   	HomePage.buyNow_Btn(driver).click();
	  }
		   
	   //add one item in the shoppingcart
	 public void addOneItemInShoppingCart(){
	   	driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	   	Slide_Item_Page.wpsc_Buy_Btn(driver).click();
	  }	
}
