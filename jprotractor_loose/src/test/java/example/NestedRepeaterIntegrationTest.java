package example;

/**
 * Copyright 2021 Serguei Kouzmine
 */
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.greaterThan;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.openqa.selenium.WebElement;

/**
 * Local file Integration tests for nested loose Angular Protractor calls
 *
 * @author Serguei Kouzmine (kouzmine_serguei@yahoo.com)
 */

public class NestedRepeaterIntegrationTest extends CommonTest {

	private static List<WebElement> elements = new ArrayList<>();
	private static List<WebElement> itemElements = new ArrayList<>();
	private static List<WebElement> childrenElements = new ArrayList<>();
	private static WebElement element = null;

	private static String text = null;

	@SuppressWarnings("unchecked")
	@Test
	public void test1() {
		getLocalPage("ng_nested_repeater.htm");
		itemElements = (List<WebElement>) executeScript(
				getScriptContent("repeater.js"), null, "item in data", null);
		assertThat(itemElements, notNullValue());
		assertThat(itemElements.size(), greaterThan(1));

		for (WebElement itemElement : itemElements) {
			highlight(itemElement);

			if (debug)
				System.err
						.println("item element: " + itemElement.getAttribute("outerHTML"));

			text = (String) executeScript(getScriptContent("evaluate.js"),
					itemElement, "item.name", null);
			System.err.println(String.format("%s has children:", text));

			childrenElements = (List<WebElement>) executeScript(
					getScriptContent("repeater.js"), itemElement,
					"child in item.children", null);

			for (WebElement childElement : childrenElements) {
				highlight(childElement);
				text = (String) executeScript(getScriptContent("evaluate.js"),
						childElement, "child.name", null);

				List<WebElement> grandchildrenElements = (List<WebElement>) executeScript(
						getScriptContent("repeater.js"), itemElement,
						"grand_child in child.children", null);
				if (grandchildrenElements.size() == 0) {
					System.err.println(String.format("%s has no grand-children", text));
					continue;
				}
				System.err.println(String.format("%s has grand-children:", text));
				for (WebElement grandchildElement : grandchildrenElements) {
					highlight(grandchildElement);
					text = (String) executeScript(getScriptContent("evaluate.js"),
							grandchildElement, "grand_child.name", null);
					if (text == null)
						text = grandchildElement.getText();
					System.err.println("name: " + text);

				}
			}
		}
	}

	@SuppressWarnings("unchecked")
	@Test
	public void test2() {
		getLocalPage("ng_nested_repeater.htm");
		itemElements = (List<WebElement>) executeScript(
				getScriptContent("repeater.js"), null, "item in data", null);
		assertThat(itemElements, notNullValue());
		assertThat(itemElements.size(), greaterThan(1));

		for (WebElement itemElement : itemElements) {
			highlight(itemElement);

			if (debug)
				System.err
						.println("item element: " + itemElement.getAttribute("outerHTML"));

			elements = (List<WebElement>) executeScript(
					getScriptContent("binding.js"), itemElement, "item.name", null);
			assertThat(itemElements, notNullValue());
			assertThat(itemElements.size(), greaterThan(0));
			element = elements.get(0);

			System.err.println(String.format("%s has children:", element.getText()));

			childrenElements = (List<WebElement>) executeScript(
					getScriptContent("repeater.js"), itemElement,
					"child in item.children", null);

			for (WebElement childElement : childrenElements) {
				highlight(childElement);
				elements = (List<WebElement>) executeScript(
						getScriptContent("binding.js"), childElement, "child.name", null);
				assertThat(itemElements, notNullValue());
				assertThat(itemElements.size(), greaterThan(0));
				element = elements.get(0);
				List<WebElement> grandchildrenElements = (List<WebElement>) executeScript(
						getScriptContent("repeater.js"), itemElement,
						"grand_child in child.children", null);
				if (grandchildrenElements.size() == 0) {
					System.err.println(
							String.format("%s has no grand-children", element.getText()));
					continue;
				}
				System.err.println(
						String.format("%s has grand-children:", element.getText()));
				for (WebElement grandchildElement : grandchildrenElements) {
					highlight(grandchildElement);
					text = (String) executeScript(getScriptContent("evaluate.js"),
							grandchildElement, "grand_child.name", null);
					if (text == null)
						text = grandchildElement.getText();
					System.err.println("name: " + text);
				}
			}
		}
	}
}
