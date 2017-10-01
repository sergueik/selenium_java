package com.mycompany.app;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.SearchContext;

public class ByAngularPartialButtonText extends ByAngular.BaseBy {

	private String searchText;

	public ByAngularPartialButtonText(String rootSelector,
			String partialButtonText) {
		super(rootSelector);
		this.searchText = partialButtonText;
	}

	protected Object getObject(SearchContext context,
			JavascriptExecutor javascriptExecutor) {
		return javascriptExecutor
				.executeScript("var using = arguments[0] || document;\n"
						+ "var searchText = '" + searchText + "';\n" + "\n"
						+ ByAngular.functions.get("findByPartialButtonText"), context);
	}

	@Override
	public String toString() {
		return "searchText(" + searchText + ')';
	}
}
