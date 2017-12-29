package com.github.sergueik.selenium;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.testng.Assert.assertTrue;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Selected test scenarios for Selenium WebDriver
 * @author: Serguei Kouzmine (kouzmine_serguei@yahoo.com)
 */

public class JqueryBarRatingTest extends BaseTest {

	// NOTE: only works with firefox profile
	private Predicate<WebElement> hasClass;
	private Predicate<WebElement> hasAttr;
	private Predicate<WebElement> hasText;
	private String baseUrl = "http://antenna.io/demo/jquery-bar-rating/examples/";

	@BeforeMethod
	public void beforeMethod(Method method) {
		super.beforeMethod(method);
		driver.get(baseUrl);
	}

	@AfterMethod
	@Override
	public void afterMethod() {
		driver.get("about:blank");
	}

	@Test(enabled = true)
	public void test1() {
		if (!BaseTest.getBrowser().matches("firefox")) {
			System.err.println("This test is only working with firefox");
			return;
		}
		// Arrange
		WebElement bar = wait.until((WebDriver d) -> {
			WebElement element = null;
			try {
				element = d.findElement(By.cssSelector(
						"section.section-examples div.examples div.box-example-square div.box-body div.br-theme-bars-square"));
			} catch (Exception e) {
				return null;
			}
			return (element.isDisplayed()) ? element : null;
		});
		scrolltoElement(bar);
		// Act
		// NOTE: relative xpath selector
		assertTrue(
				bar.findElements(By.xpath("//a[@data-rating-value]")).size() > 7);
		List<WebElement> ratingElements = bar
				.findElements(By.xpath(".//a[@data-rating-value]"));
		assertTrue(ratingElements.size() > 0);
		// TODO: test that result set elements are unique ?
		Map<String, WebElement> ratings = ratingElements.stream().collect(Collectors
				.toMap(o -> o.getAttribute("data-rating-text"), Function.identity()));
		//
		ratings.keySet().stream().forEach(o -> {
			System.err.println("Mouse over rating: " + o);
			WebElement r = ratings.get(o);
			// hover
			actions.moveToElement(r).build().perform();
			highlight(r);
			sleep(1000);
		});
		// Assert
	}

	@Test(enabled = true)
	public void test2_1() {
		if (!BaseTest.getBrowser().matches("firefox")) {
			System.err.println("This test is only working with firefox");
			return;
		}
		// Arrange

		wait.until(ExpectedConditions.or(
				// NOTE: Boolean
				ExpectedConditions.visibilityOfElementLocated(
						By.cssSelector("div.examples div.box-example-reversed")),
				ExpectedConditions.visibilityOfElementLocated(
						By.cssSelector("div.examples div.box-example-reversed"))));

		WebElement bar = wait.until(ExpectedConditions.visibilityOfElementLocated(
				By.cssSelector("div.examples div.box-example-reversed")));

		// scrolltoElement(bar);
		scroll(0, 800);
		sleep(2000);
		List<WebElement> ratingElements = bar
				.findElements(By.xpath(".//a[@data-rating-value]"));
		assertTrue(ratingElements.size() > 0);
		Map<String, WebElement> ratings = ratingElements.stream().collect(Collectors
				.toMap(o -> o.getAttribute("data-rating-text"), Function.identity()));
		// Act
		ratings.keySet().stream().forEach(o -> {
			WebElement r = ratings.get(o);
			assertThat(r, notNullValue());
			// hover
			actions.moveToElement(r).build().perform();
			flash(r);
			// Assert
			WebElement comment = bar.findElement(By.xpath(
					".//*[contains(@class, 'br-current-rating') and contains(@class ,'br-selected')]"));
			assertThat(comment.getText(), equalTo(o));
			System.err.println("Mouse over rating: " + o);
			sleep(1000);
		});
	}

	@Test(enabled = true)
	public void test2_2() {
		// Arrange

		wait.until(ExpectedConditions.or(
				// NOTE: Boolean
				ExpectedConditions.visibilityOfElementLocated(
						By.cssSelector("div.examples div.box-example-reversed")),
				ExpectedConditions.visibilityOfElementLocated(
						By.cssSelector("div.examples div.box-example-reversed"))));

		WebElement bar = wait.until(ExpectedConditions.visibilityOfElementLocated(
				By.cssSelector("div.examples div.box-example-reversed")));

		scroll(0, 800);
		sleep(2000);
		List<WebElement> ratingElements = bar
				.findElements(By.xpath(".//a[@data-rating-value]"));
		assertTrue(ratingElements.size() > 0);
		Map<String, WebElement> ratings = ratingElements.stream().collect(Collectors
				.toMap(o -> o.getAttribute("data-rating-text"), Function.identity()));
		// Act
		int cnt = 0;
		// TODO: change ratings object to map elements to their labels
		Object[] ratingsKeys = ratings.keySet().toArray();
		for (cnt = 0; cnt < ratingsKeys.length; cnt++) {
			String o = (String) ratingsKeys[cnt];
			System.err.println("Trying to select " + o);
			WebElement r = ratings.get(o);
			assertThat(r, notNullValue());
			String cssSelector = cssSelectorOfElement(r);
			cssSelector = String
					.format("div.examples div.br-widget > a:nth-of-type(%d)", cnt + 1);
			// the selectors are indistinguishable. Have to add ":nth-of-type(cnt)"
			System.err.println(String.format("element: %s\n%s\n",
					r.getAttribute("outerHTML"), cssSelector));
			jqueryHover(cssSelector);
			flash(r);
			// Assert
			WebElement comment = bar.findElement(By.xpath(
					".//*[contains(@class, 'br-current-rating') and contains(@class ,'br-selected')]"));
			System.err.println("Mouse over rating: " + o);
			// NOTE: order
			// assertThat(comment.getText(), equalTo(o));
			sleep(1000);
		}
	}

	@Test(enabled = true)
	public void test3() {
		if (!BaseTest.getBrowser().matches("firefox")) {
			System.err.println("This test is only working with firefox");
			return;
		}
		// Arrange
		// custom Expected Condition static Helper class
		WebElement bar = wait.until(Helper.oneOfElementsLocatedVisible(
				By.xpath(
						"//section[contains(@class, 'section-examples')]//div[contains(@class, 'box-example-movie')]//div[contains(@class, 'br-widget')]"),
				By.cssSelector(
						"section.section-examples div.box-example-movie div.br-widget")));
		scrolltoElement(bar);
		// Act
		List<WebElement> ratingElements = bar
				.findElements(By.xpath(".//a[@data-rating-value]"));
		assertTrue(ratingElements.size() > 0);
		Map<String, WebElement> ratings = ratingElements.stream().collect(Collectors
				.toMap(o -> o.getAttribute("data-rating-text"), Function.identity()));
		ratings.keySet().stream().forEach(o -> {
			WebElement r = ratings.get(o);
			assertThat(r, notNullValue());
			// hover
			actions.moveToElement(r).build().perform();
			highlight(r);
			r.click();
			// Assert
			WebElement comment = bar
					.findElement(By.xpath(".//*[contains(@class, 'br-current-rating')]"));
			assertThat(comment.getText(), equalTo(o));
			System.err.println("Mouse over rating: " + o);
			// NOTE: there is a hidden select sibling element. The selected option
			// does not appear to be updated
			sleep(1000);
			final String script = "var result = $(\"section.section-examples div.box-example-movie div.br-theme-bars-movie select#example-movie option[selected='selected']\");return result.val();";
			if (driver instanceof JavascriptExecutor) {
				Object result = ((JavascriptExecutor) driver).executeScript(script);
				System.err.println("Select option value: " + result.toString());
			}
		});
	}

	public static class Helper {
		// for complex expected Condition, based on
		// http://stackoverflow.com/questions/14840884/wait-untilexpectedconditions-visibilityof-element1-or-element2

		public static ExpectedCondition<WebElement> oneOfElementsLocatedVisible(
				By... bys) {
			return new ExpectedCondition<WebElement>() {
				@Override
				public WebElement apply(WebDriver driver) {
					for (By by : Arrays.asList(bys)) {
						WebElement element;
						try {
							System.err.println("Locator : " + by.toString());
							element = driver.findElement(by);
						} catch (Exception e) {
							continue;
						}
						if (element.isDisplayed())
							return element;
					}
					return null;
				}
			};
		}
	}

	public void sleep(Integer seconds) {
		long secondsLong = (long) seconds;
		try {
			Thread.sleep(secondsLong);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
