package com.github.sergueik.jprotractor.integration;

import static org.exparity.hamcrest.date.DateMatchers.sameOrBefore;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.core.AnyOf.anyOf;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.Alert;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.github.sergueik.jprotractor.NgBy;
import com.github.sergueik.jprotractor.NgWebDriver;
import com.github.sergueik.jprotractor.NgWebElement;

public class NgDemoTest {

	private static NgWebDriver ngDriver;
	private static WebDriver seleniumDriver;
	static WebDriverWait wait;
	static Actions actions;
	static Alert alert;
	private int cnt = 0;
	private LocalDate today = LocalDate.now();
	private SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yy");
	public static String baseUrl = "http://www.way2automation.com/angularjs-protractor/banking";

	@Test
	public void testCustomerLogin() {

		// When I proceed to "Customer Login"
		NgWebElement element = ngDriver
				.findElement(NgBy.buttonText("Customer Login"));
		highlight(element);
		element.click();

		// choose myself from the list of existing customers
		NgWebElement custId = ngDriver.findElement(NgBy.input("custId"));
		highlight(custId);

		custId.findElements(NgBy.repeater("cust in Customers")).stream()
				.filter(c -> c.getText().indexOf("Hermoine Granger") >= 0).findFirst()
				.get().click();

		// And I log in
		NgWebElement login = ngDriver.findElement(NgBy.buttonText("Login"));
		assertTrue(login.isEnabled());
		login.click();

		// Then I am greeted by my first and last name
		assertThat("greeting", ngDriver.findElement(NgBy.binding("user")).getText(),
				containsString("Hermoine Granger"));

		// And I see balance on one of my accounts
		NgWebElement ng_accountNo = ngDriver.findElement(NgBy.binding("accountNo"));
		assertThat("account number", ng_accountNo.getText(),
				anyOf(is("1001"), is("1002"), is("1003")));
		highlight(ng_accountNo);

		// And I open account
		ngDriver.findElements(NgBy.options("account for account in Accounts"))
				.stream().filter(account -> account.getText().matches("1001"))
				.collect(Collectors.toList()).get(0).click();

		// And I inspect transactions
		NgWebElement transactions_button = ngDriver
				.findElement(NgBy.partialButtonText("Transactions"));
		highlight(transactions_button);
		transactions_button.click();

		// And I wait until transactions are loaded
		wait.until(ExpectedConditions.visibilityOf(ngDriver
				.findElement(NgBy.repeater("tx in transactions")).getWrappedElement()));

		// Then I can inspect few transactions
		for (WebElement tx : ngDriver
				.findElements(NgBy.repeater("tx in transactions"))) {
			if (cnt++ > 5) {
				break;
			}
			NgWebElement ngTx = new NgWebElement(ngDriver, tx);

			// it would be either Debit or Credit
			String txType = ngTx.evaluate("tx.type").toString();
			assertThat("transaction type", txType,
					anyOf(containsString("Debit"), containsString("Credit")));

			// it will have non zero amount
			int txAmount = Integer.parseInt(ngTx.evaluate("tx.amount").toString());
			assertThat("it will have non zero amount", txAmount, greaterThan(0));
			highlight(ngTx.findElement(NgBy.binding("tx.amount")));

			// its transaction dates should be the past
			try {
				Date txDate = dateFormat.parse(ngTx.evaluate("tx.date").toString());
				assertThat("transaction date", txDate, sameOrBefore(new Date()));
			} catch (ParseException e) {
				// ignore
			}
		}
	}

	
	
	@BeforeClass
	public static void setup() throws IOException {
		seleniumDriver = CommonFunctions.getSeleniumDriver();
		int width = 700;
		int height = 550;
		seleniumDriver.manage().window().setSize(new Dimension(width, height));
		int implicitWait = 10;
		int flexibleWait = 5;
		long pollingInterval = 500;
		seleniumDriver.manage().timeouts().pageLoadTimeout(50, TimeUnit.SECONDS)
				.implicitlyWait(implicitWait, TimeUnit.SECONDS)
				.setScriptTimeout(10, TimeUnit.SECONDS);
		wait = new WebDriverWait(seleniumDriver, flexibleWait);
		wait.pollingEvery(pollingInterval, TimeUnit.MILLISECONDS);
		actions = new Actions(seleniumDriver);
		ngDriver = new NgWebDriver(seleniumDriver);
		CommonFunctions.setHighlightTimeout(1000);
	}

	@Before
	public void beforeEach() {
		// Given I am on Home page
		ngDriver.navigate().to(baseUrl);
	}

	@AfterClass
	public static void teardown() {
		ngDriver.close();
		seleniumDriver.quit();
	}

	private static void highlight(WebElement element) {
		CommonFunctions.highlight(element);
	}

}
