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
		Map<String, String> formData = new HashMap<>();
		formData.put("email", "test_user@rambler.ru");
		formData.put("password", "secret");
		for (String formElementId : formData.keySet()) {

			List<WebElement> labelElements = element2.findElements(By
					.cssSelector(String.format("form label[ for='%s']", formElementId)));
			assertThat(labelElements.size(), equalTo(1));
			highlight(labelElements.get(0));

			WebElement inputElement = element2.findElement(
					By.cssSelector(String.format("form input#%s", formElementId)));
			assertThat(inputElement, notNullValue());
			highlight(inputElement);
			System.err.println(String.format("filling the input: %s with %s",
					formElementId, formData.get(formElementId)));
			inputElement.sendKeys(formData.get(formElementId));
		}
		sleep(1000);
		// close the dialog
		element = element2.findElement(By.cssSelector("button[type='submit']")); //
		element.click();
		sleep(1000);

	}

}
