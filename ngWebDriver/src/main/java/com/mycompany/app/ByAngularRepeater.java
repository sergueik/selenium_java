package com.mycompany.app;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.SearchContext;

public class ByAngularRepeater extends ByAngular.BaseBy {


	private String repeater;
	private boolean exact;
	
	public ByAngularRepeater(String rootSelector, String repeater,
			boolean exact) {
		super(rootSelector);
		this.repeater = repeater;
		this.exact = exact;
	}

	public ByAngularRepeaterRow row(int row) {
		return new ByAngularRepeaterRow(rootSelector, repeater, exact, row);
	}

	public ByAngularRepeaterColumn column(String column) {
		return new ByAngularRepeaterColumn(rootSelector, repeater, exact, column);
	}

	protected Object getObject(SearchContext context,
			JavascriptExecutor javascriptExecutor) {
		return javascriptExecutor.executeScript(
				"var using = arguments[0] || document;\n" + "var rootSelector = '"
						+ rootSelector + "';\n" + "var repeater = '"
						+ repeater.replace("'", "\\'") + "';\n" + "var exact = " + exact
						+ ";\n" + "\n" + ByAngular.functions.get("findAllRepeaterRows"),
				context);
	}

	@Override
	public String toString() {
		return (exact ? "exactR" : "r") + "epeater(" + repeater + ')';
	}
}
