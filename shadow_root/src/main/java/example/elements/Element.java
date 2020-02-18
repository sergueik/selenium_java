package example.elements;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Point;
import org.openqa.selenium.Rectangle;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;

// NOTE: class is not used
public class Element implements WebElement {

	public <X> X getScreenshotAs(OutputType<X> arg0) throws WebDriverException {
		return null;
	}

	public void clear() {
	}

	public void click() {
	}

	public WebElement findElement(By arg0) {
		return null;
	}

	public List<WebElement> findElements(By arg0) {
		return null;
	}

	public String getAttribute(String arg0) {
		return null;
	}

	public String getCssValue(String arg0) {
		return null;
	}

	public Point getLocation() {
		return null;
	}

	public Rectangle getRect() {
		return null;
	}

	public Dimension getSize() {
		return null;
	}

	public String getTagName() {
		return null;
	}

	public String getText() {
		return null;
	}

	public boolean isDisplayed() {
		return false;
	}

	public boolean isEnabled() {
		return false;
	}

	public boolean isSelected() {
		return false;
	}

	public void sendKeys(CharSequence... arg0) {
	}

	public void submit() {
	}

}
