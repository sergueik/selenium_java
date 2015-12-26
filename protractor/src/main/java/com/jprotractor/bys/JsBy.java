package com.jprotractor.bys;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import java.io.IOException;

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
				InputStream is = NgBy.class.getClassLoader().getResourceAsStream(getFilename());
				byte[] bytes = new byte[is.available()];
				is.read(bytes);
				return new String(bytes, "UTF-8");
		} catch ( IOException e) {
			throw new RuntimeException(e);
		}
	}

	protected abstract String getFilename();
}
