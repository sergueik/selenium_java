package com.mycompany.app;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Vector;

import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;

import com.mycompany.app.Utils;

/**
 * Generate source code from testData and twig template for Selenium Webdriver Eclipse Tool
 * 
 * @author Serguei Kouzmine (kouzmine_serguei@yahoo.com)
 */

class RenderTemplate {

	private static final HashMap<String, String> elementData = createSampleElementData();
	private static HashMap<String, HashMap<String, String>> testData;
	private String templateName = "templates/example2.twig";

	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}

	public String getTemplateName() {
		return templateName;
	}

	private static HashMap<String, String> createSampleElementData() {
		HashMap<String, String> elementData = new HashMap<String, String>();
		elementData.put("ElementId", "id");
		elementData.put("ElementCodeTemplateName", "gmail link");
		elementData.put("ElementXPath", "/html//img[1]");
		elementData.put("ElementCssSelector", "div#gbw > a.highlight");
		elementData.put("ElementSelectedBy", "ElementCssSelector");
		return elementData;
	}

	private static HashMap<String, HashMap<String, String>> createSampleTestData() {
		final HashMap<String, HashMap<String, String>> testData = new HashMap<String, HashMap<String, String>>();
		testData.put("1", elementData);
		return testData;
	}

	public String renderTest() {
		testData = createSampleTestData();
		return renderTest(testData);
	}

	public String renderTest(
			HashMap<String, HashMap<String, String>> testData) {
		Iterator<String> testDataKeys = testData.keySet().iterator();
		String stepId;
		List<String> scripts = new ArrayList<String>();
		while (testDataKeys.hasNext()) {
			stepId = testDataKeys.next();
			HashMap<String, String> elementData = testData.get(stepId);
			scripts.add(renderElement(elementData));
		}
		StringBuilder result = new StringBuilder();
		for (String line : scripts) {
			result.append(line);
			result.append("\n");
		}
		return result.toString();
	}

	private String renderElement(Map<String, String> data) {
		JtwigTemplate template = JtwigTemplate.classpathTemplate(this.templateName);
		JtwigModel model = JtwigModel.newModel();
		for (String key : data.keySet()) {
			model.with(key, data.get(key).replace("\"","\\\""));
		}
		String output = template.render(model);
		return output;
	}

	public static void main(String[] args) {
		String output = new RenderTemplate().renderTest();
		System.err.println("Rendered: " + output);
	}
}
