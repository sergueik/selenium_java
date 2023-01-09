package example;

/**
 * Copyright 2023 Serguei Kouzmine
 */
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.CoreMatchers.containsString;
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

@SuppressWarnings({ "deprecation" })
public class LocalFileTest extends CommonTest {

	private static List<WebElement> elements = new ArrayList<>();
	private static WebElement element = null;
	private static WebElement element2 = null;
	private static WebElement element3 = null;
	private static String value = null;
	private static String inputId = null;

	@SuppressWarnings("unchecked")
	// @Ignore("suppressed while there is no internet connection")
	@Test
	public void testEvaluate() {
		getLocalPage("ng_service.htm");
		for (WebElement currentElement : (List<WebElement>) executeScript(
				getScriptContent("repeater.js"), null, "person in people", null)) {
			if (currentElement.getText().isEmpty()) {
				break;
			}
			WebElement personName = (WebElement) executeScript(
					getScriptContent("binding.js"), currentElement, "person.Name", null);

			assertThat(personName, notNullValue());
			Object personCountry = executeScript(getScriptContent("evaluate.js"),
					currentElement, "person.Country", null);
			assertThat(personCountry, notNullValue());
			System.err.println(personName.getText() + " " + personCountry.toString());
			if (personName.getText().indexOf("Around the Horn") >= 0) {
				assertThat(personCountry.toString(), containsString("UK"));
				highlight(personName);
			}
		}
	}

}
