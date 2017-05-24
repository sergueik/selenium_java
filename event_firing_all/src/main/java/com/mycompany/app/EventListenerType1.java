package com.mycompany.app;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.events.WebDriverEventListener;

public class EventListenerType1 implements WebDriverEventListener {

	public void afterNavigateRefresh(WebDriver driver) {
		// added to satisfy the compiler
	}

	public void beforeNavigateRefresh(WebDriver driver) {
		// added to satisfy the compiler
	}

	@Override
	public void beforeScript(String script, WebDriver driver) {
	}

	@Override
	public void afterScript(String script, WebDriver driver) {
	}

	@Override
	public void beforeChangeValueOf(WebElement element, WebDriver driver) {
		// String strlastElement = (element.toString().split("->")[1]);
		// strlastElement = strlastElement.substring(0 , strlastElement.length()-1);
		System.err.println("beforeChangeValueOf '" + element.toString() + "'");
	}

	@Override
	public void afterChangeValueOf(WebElement element, WebDriver driver) {
	}

	@Override
	public void beforeClickOn(WebElement element, WebDriver driver) {
	}

	@Override
	public void afterClickOn(WebElement element, WebDriver driver) {
		System.err.println("afterClickOn '" + element.toString() + "'");
	}

	@Override
	public void beforeFindBy(By by, WebElement element, WebDriver driver) {
	}

	@Override
	public void afterFindBy(By by, WebElement element, WebDriver driver) {
	}

	@Override
	public void beforeNavigateBack(WebDriver driver) {
	}

	@Override
	public void afterNavigateBack(WebDriver driver) {
	}

	@Override
	public void beforeNavigateForward(WebDriver driver) {
	}

	@Override
	public void afterNavigateForward(WebDriver driver) {
	}

	@Override
	public void beforeNavigateTo(String url, WebDriver driver) {
	}

	@Override
	public void afterNavigateTo(String url, WebDriver driver) {
	}

	@Override
	public void onException(Throwable ex, WebDriver driver) {
		System.err.println(
				"There is an exception in the script, please find the below error description");
		ex.printStackTrace();
	}

}
