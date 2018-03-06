package com.xls.report.config;

// based on: https://github.com/rahulrathore44/ExcelReportGenerator
public class ElementLocator {

	private String method = "";
	private String selector = "";

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getSelector() {
		return selector;
	}

	public void setSelector(String selector) {
		this.selector = selector;
	}


	@Override
	public String toString() {
		return method + " = " + selector;
	}

}
