package com.stackoverflow;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.pagefactory.ElementLocator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DynamicElementLocator implements ElementLocator {

	private static final Logger log = LoggerFactory
			.getLogger(DynamicElementLocator.class.getCanonicalName());

	private final SearchContext searchContext;
	private final boolean shouldCache;
	private final By by;
	private WebElement cachedElement;
	private List<WebElement> cachedElementList;

	// The only thing that differs from DefaultElementLocator is
	// the substitutions parameter for this method.
	public DynamicElementLocator(final SearchContext searchContext,
			final Field field, final Map<String, String> substitutions) {
		this.searchContext = searchContext;
		// DynamicAnnotations is my implementation of annotation processing
		// that uses the substitutions to find and replace values in the
		// locator strings in the FindBy, FindAll, FindBys annotations
		DynamicAnnotations annotations = new DynamicAnnotations(field,
				substitutions);
		shouldCache = annotations.isLookupCached();
		by = annotations.buildBy();
		// log.debug("Successful completion of the dynamic element locator");
	}

	/**
	 * Find the element.
	 */
	public WebElement findElement() {
		if (cachedElement != null && shouldCache) {
			return cachedElement;
		}

		WebElement element = searchContext.findElement(by);
		if (shouldCache) {
			cachedElement = element;
		}
		return element;
	}

	/**
	 * Find the element list.
	 */
	public List<WebElement> findElements() {
		if (cachedElementList != null && shouldCache) {
			return cachedElementList;
		}

		List<WebElement> elements = searchContext.findElements(by);
		if (shouldCache) {
			cachedElementList = elements;
		}
		return elements;
	}
}