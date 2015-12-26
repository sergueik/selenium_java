package com.jprotractor.bys;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

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
		String script = "";
		try {
				System.err.println(NgBy.class.getClassLoader().getResource(getFilename()).toURI());
				InputStream is = NgBy.class.getClassLoader().getResourceAsStream(getFilename());
				BufferedReader br = new BufferedReader(new InputStreamReader(is));
				String line;
				while ((line = br.readLine()) != null) {
					script = script + line;
				}
				is.close();
				return script;
		} catch ( IOException e) {
			throw new RuntimeException(e);
		} catch ( URISyntaxException e) {
			throw new RuntimeException(e);
		}
	}

	protected abstract String getFilename();
}
