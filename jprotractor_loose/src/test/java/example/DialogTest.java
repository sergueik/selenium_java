package example;

/**
 * Copyright 2021 Serguei Kouzmine
 */
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.greaterThan;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.TimeoutException;

import org.junit.Ignore;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import static org.hamcrest.CoreMatchers.equalTo;

/**
 * Local file Integration tests for nested loose Angular Protractor calls
 *
 * @author Serguei Kouzmine (kouzmine_serguei@yahoo.com)
 */

public class DialogTest extends CommonTest {

	private static List<WebElement> elements = new ArrayList<>();
	private static WebElement element = null;
	private static WebElement element2 = null;
	private static WebElement element3 = null;
	private static String value = null;
	private static String inputId = null;

	private final static Map<String, String> formData = new HashMap<>();
	static {
		formData.put("email", "test_user@rambler.ru");
		formData.put("password", "secret");
	}
	private final static Map<String, String> formData2 = new HashMap<>();
	static {
		formData2.put("Email address", "test_user@rambler.ru");
		formData2.put("Password", "secret");
	}
	private static String text = null;

	@SuppressWarnings("unchecked")
	@Test
	public void test1() {
		getLocalPage("ng_modal2.htm");
		elements = (List<WebElement>) executeScript(
				getScriptContent("buttonText.js"), null, "Open modal", null);
		assertThat(elements, notNullValue());
		assertThat(elements.size(), greaterThan(0));
		element = elements.get(0);
		element.click();

		element = wait.until(new ExpectedCondition<WebElement>() {

			@Override
			// NOTE: the isDisplayed is a WebElement method
			// the isPresent is Optional method
			public WebElement apply(WebDriver driver) {
				Optional<WebElement> element = ((List<WebElement>) executeScript(
						getScriptContent("binding.js"), null, "title", null)).stream()
								.filter(e -> e.isDisplayed()).findFirst();
				return (element.isPresent()) ? element.get() : (WebElement) null;
			}
		});
		// none of the select elements will have "selected" attribute set.
		assertThat(element, notNullValue());
		highlight(element);
		element2 = element.findElement(By.xpath("../.."));
		System.err.println(
				"Dialog Element contents: " + element2.getAttribute("outerHTML"));
		for (String inputId : formData.keySet()) {
			value = formData2.get(inputId);
			/*
			elements = element2.findElements(
					By.cssSelector(String.format("form label[ for='%s']", inputId)));
			assertThat(elements.size(), equalTo(1));
			highlight(elements.get(0));
*/
			element3 = element2
					.findElement(By.cssSelector(String.format("form input#%s", inputId)));
			assertThat(element3, notNullValue());
			highlight(element3);
			System.err.println(String.format("filling the input: %s with %s", inputId,
					formData.get(inputId)));
			element3.sendKeys(formData.get(inputId));
		}
		sleep(1000);
		// close the dialog
		element = element2.findElement(By.cssSelector("button[type='submit']")); //
		element.click();
		sleep(1000);

	}

	@SuppressWarnings("unchecked")
	@Test
	public void test2() {
		getLocalPage("ng_modal2.htm");
		elements = (List<WebElement>) executeScript(
				getScriptContent("buttonText.js"), null, "Open modal", null);
		assertThat(elements, notNullValue());
		assertThat(elements.size(), greaterThan(0));
		element = elements.get(0);
		element.click();

		element = wait.until(new ExpectedCondition<WebElement>() {

			@Override
			// NOTE: the isDisplayed is a WebElement method
			// the isPresent is Optional method
			public WebElement apply(WebDriver driver) {
				Optional<WebElement> element = ((List<WebElement>) executeScript(driver,
						getScriptContent("binding.js"), null, "title", null)).stream()
								.filter(e -> e.isDisplayed()).findFirst();
				return (element.isPresent()) ? element.get() : (WebElement) null;
			}
		});
		// none of the select elements will have "selected" attribute set.
		assertThat(element, notNullValue());
		highlight(element);
		element2 = element.findElement(By.xpath("../.."));
		System.err.println(
				"Dialog Element contents: " + element2.getAttribute("outerHTML"));
		for (String text : formData2.keySet()) {

			value = formData2.get(text);
			inputId = wait.until(new ExpectedCondition<String>() {

				@Override
				public String apply(WebDriver driver) {

					Optional<WebElement> element = ((List<WebElement>) element2
							.findElements(By.cssSelector("form label"))).stream()
									.filter(e -> e.getText().contains(text)).findFirst();
					if (element.isPresent()) {
						element3 = element.get();
						highlight(element3);
						return element3.getAttribute("for");
					} else {
						return null;
					}
				}
			});

			element3 = element2
					.findElement(By.cssSelector(String.format("form input#%s", inputId)));
			assertThat(element3, notNullValue());
			highlight(element3);

			System.err
					.println(String.format("filling the input: %s with %s", text, value));
			element3.sendKeys(value);
		}
		sleep(1000);
		// close the dialog
		element = element2.findElement(By.cssSelector("button[type='submit']")); //
		element.click();
		sleep(1000);

	}

}
