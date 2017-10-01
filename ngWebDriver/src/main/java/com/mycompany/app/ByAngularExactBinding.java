package com.mycompany.app;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.SearchContext;

public class ByAngularExactBinding extends ByAngular.BaseBy {

	private String binding;

	public ByAngularExactBinding(String rootSelector, String exactBinding) {
		super(rootSelector);
		this.binding = exactBinding;
	}

	protected Object getObject(SearchContext context,
			JavascriptExecutor javascriptExecutor) {
		return javascriptExecutor
				.executeScript("var using = arguments[0] || document;\n"
						+ "var rootSelector = '" + rootSelector + "';\n"
						+ "var exactMatch = true;\n" + "var binding = '" + binding + "';\n"
						+ "\n" + ByAngular.functions.get("findBindings"), context);
	}

	@Override
	public String toString() {
		return "exactBinding(" + binding + ')';
	}
}