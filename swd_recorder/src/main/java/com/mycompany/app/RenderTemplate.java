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
import java.util.HashMap;
import java.util.Hashtable;
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

	private static final Map<String, String> elementData = createSampleElement();

	private static Map<String, String> createSampleElement() {
		Map<String, String> elementData = new HashMap<String, String>();
		elementData.put("ElementId", "id");
		elementData.put("ElementCodeName", "gmail link");
		elementData.put("ElementXPath", "/html//img[1]");
		elementData.put("ElementCssSelector", "div#gbw > a.highlight");
		elementData.put("useCss", "true");
		elementData.put("useXPath", "false");
		elementData.put("useId", "false");
		elementData.put("useText", "false");
		return elementData;
	}

	private String sampleRender() {
		return sampleRender(elementData);
	}

	private String sampleRender(Map<String, String> data) {
		JtwigTemplate template = JtwigTemplate
				.classpathTemplate("templates/example.twig");
		JtwigModel model = JtwigModel.newModel();
		for (String key : data.keySet()) {
			model.with(key, data.get(key));
		}
		String output = template.render(model);
		return output;
	}

	public static void main(String[] args) {
		String output = new RenderTemplate().sampleRender();
		System.err.println("Rendered: " + output);
	}
}
