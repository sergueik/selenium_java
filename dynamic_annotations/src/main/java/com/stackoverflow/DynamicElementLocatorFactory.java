package com.stackoverflow;

import java.lang.reflect.Field;
import java.util.Map;
import org.slf4j.LoggerFactory;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.support.pagefactory.ElementLocatorFactory;

public final class DynamicElementLocatorFactory
		implements ElementLocatorFactory {
	private final SearchContext searchContext;
	private final Map<String, String> substitutions;

	// The only thing that is different from DefaultElementLocatorFactory
	// is that the constructor for this class takes the substitutions
	// parameter that consists of the key/value mappings to use
	// for substituting keys in locator strings for FindBy, FindAll and
	// FindBys with values known or determined at runtime.
	public DynamicElementLocatorFactory(final SearchContext searchContext,
			final Map<String, String> substitutions) {
		this.searchContext = searchContext;
		this.substitutions = substitutions;
	}

	// This produces an instance of the DynamicElementLocator class and
	// specifies the key value mappings to substitute in locator Strings
	public DynamicElementLocator createLocator(final Field field) {
		return new DynamicElementLocator(searchContext, field, substitutions);
	}
}
