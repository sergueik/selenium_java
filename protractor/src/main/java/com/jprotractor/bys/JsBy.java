package com.jprotractor.bys;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.jprotractor.NgBy;

public abstract class JsBy extends By {
	protected final JavascriptExecutor executor;

	public JsBy(WebDriver executor) {
		super();
		this.executor = (JavascriptExecutor) executor;
	}
	
	protected WebElement getElement(SearchContext context) {
		return context.findElement(By.cssSelector("*"));
	}

	protected String getScriptContent() {
		try {
			URI uri = NgBy.class.getClassLoader().getResource(getFilename())
					.toURI();
			return new String(Files.readAllBytes(Paths.get(uri)), "UTF-8");
		} catch (URISyntaxException | IOException e) {
			throw new RuntimeException(e);
		}
	}

	protected abstract String getFilename();
}
