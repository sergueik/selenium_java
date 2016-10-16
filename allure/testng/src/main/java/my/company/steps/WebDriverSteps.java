package my.company.steps;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Formatter;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import ru.yandex.qatools.allure.annotations.Attachment;
import ru.yandex.qatools.allure.annotations.Step;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.io.File;

/**
 * @author Dmitry Baev charlie@yandex-team.ru Date: 28.10.13
 */
public class WebDriverSteps {

	public WebDriver driver;

	public WebDriverSteps(WebDriver driver) {
		this.driver = driver;
	}

	@Step
	public void openMainPage(String url) {
		driver.get(url);
	}

	@Step("Search by XPath \"{0}\"")
	public void searchXPath(String xpath, String type) {
		List<WebElement> elements = driver.findElements(By.xpath(xpath));
		WebElement element = elements.get(0);
		// ArrayIndexOutOfBoundsException exception will mark a test as broken.
		assertThat(element.getAttribute("type"), containsString(type));
	}

	@Attachment
	public byte[] makeScreenshot() {
		return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
	}

	public void quit() {
		driver.quit();
	}
}