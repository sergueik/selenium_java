package example;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.JavascriptException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;

// https://qna.habr.com/q/787137
public class AvtokodTest extends BaseTest {

	private final static String baseUrl = "https://avtokod.mos.ru/Autohistory#!/Home";
	private static String locator = "autohistory-card";
	private JavascriptExecutor js;

	@Before
	public void beforMethod() {
		driver.navigate().to(baseUrl);
	}

	@Test
	public void test1() {

		WebElement element = shadowDriver.findElement(locator);
		System.err.println("test1 element: " + element);
		String pageSource = shadowDriver.getDriver().getPageSource();
		assertThat(pageSource, notNullValue());
	}

	// converted from Python - not using API,just plain Javascript
	@Test
	public void test2() {
		WebElement element = shadowDriver.findElement(locator);
		System.err.println(element);
		js = ((JavascriptExecutor) driver);
		WebElement result = (WebElement) (js.executeScript(String.format(
				"return document.querySelector('%s').shadowRoot.querySelector('label[for=autoVin]')",
				locator)));
		System.err.println("test2: " + result.getAttribute("outerHTML"));
	}

	// converted from Python - not using API,just plain Javascript
	@Test
	public void test3() {

		WebElement element = shadowDriver.findElement(locator);
		System.err.println(element);
		js = ((JavascriptExecutor) driver);
		WebElement result = (WebElement) (js.executeScript(String.format(
				"return document.querySelector('%s').shadowRoot.querySelector('input#carVin')",
				locator)));
		System.err.println("test3: " + result.getAttribute("outerHTML"));
	}

	// nested shadow-root
	@Test
	public void test4() {
		WebElement element = shadowDriver.findElement(locator);
		String locator2 = "button-ui";
		String locator3 = "button";
		System.err.println(element);
		js = ((JavascriptExecutor) driver);
		WebElement result = (WebElement) (js.executeScript(String.format(
				"return document.querySelector('%s').shadowRoot.querySelector('%s').shadowRoot.querySelector('%s')",
				locator, locator2, locator3)));
		System.err.println("test4: " + result.getAttribute("outerHTML"));
	}

	@Test
	public void test5() {
		List<WebElement> elements = shadowDriver.findElements(locator);
		assertThat(elements, notNullValue());
		assertThat(elements.size(), greaterThan(0));
		System.err.println(String.format("test4: located %d %s elements:",
				elements.size(), locator));
		// NOTE: default toString() is not be particularly useful
		if (debug) {
			elements.stream().forEach(System.err::println);
		}
		elements.stream().map(o -> o.getTagName()).forEach(System.err::println);
		// the innerHTML isbroken - never shows anything
		elements.stream()
				.map(o -> String.format("test5 element.getAttribute(\"innerHTML\")= %s",
						o.getAttribute("innerHTML")))
				.forEach(System.err::println);
		elements.stream()
				.map(o -> String.format("test5 element.getAttribute(\"outerHTML\")= %s",
						o.getAttribute("outerHTML")))
				.forEach(System.err::println);
	}

	@Test
	public void test6() {
		locator = "autohistory-card";
		WebElement element = driver.findElement(By.tagName(locator));
		List<WebElement> elements = shadowDriver.getAllShadowElement(element,
				"label[for=autoVin]");
		assertThat(elements, notNullValue());
		assertThat(elements.size(), greaterThan(0));
		WebElement element2 = elements.get(0);
		System.err.println(String.format("test6 located element: %s",
				element2.getAttribute("outerHTML")));

		elements = shadowDriver.getAllShadowElement(element, "input#carSts");
		assertThat(elements, notNullValue());
		assertThat(elements.size(), greaterThan(0));
		element2 = elements.get(0);
		System.err.println(String.format("test6 located element: %s",
				element2.getAttribute("outerHTML")));
	}

	@Test
	public void test7() {
		String locator2 = "button-ui";
		String locator3 = "button";
		WebElement element = driver.findElement(By.tagName(locator));
		List<WebElement> elements2 = shadowDriver.getAllShadowElement(element,
				locator2);
		assertThat(elements2, notNullValue());
		assertThat(elements2.size(), greaterThan(0));
		WebElement element2 = elements2.get(0);
		List<WebElement> elements3 = shadowDriver.getAllShadowElement(element2,
				locator3);
		assertThat(elements3, notNullValue());
		assertThat(elements3.size(), greaterThan(0));
		WebElement element3 = elements3.get(0);
		System.err.println(String.format("test7 located element: %s",
				element3.getAttribute("outerHTML")));
	}

}
