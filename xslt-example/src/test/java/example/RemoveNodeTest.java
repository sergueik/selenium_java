package example;
/**
 * Copyright 2020 Serguei Kouzmine
 */

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertThat;

import java.io.IOException;
import java.io.StringReader;
import java.util.Arrays;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.junit.BeforeClass;
import org.junit.Test;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import example.RemoveNode;
import example.MergeDocumentFragments;

/**
 * Unit Tests for Utils 
 * @author: Serguei Kouzmine (kouzmine_serguei@yahoo.com)
 */

public class RemoveNodeTest {

	private static boolean debug = true;

//@formatter:off
	private final static String resource = 
			"<?xml version=\"1.0\" encoding=\"ISO-8859-1\" standalone=\"no\"?>" +
					"<web-app xmlns=\"http://xmlns.jcp.org/xml/ns/javaee\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" version=\"3.1\" xsi:schemaLocation=\"http://xmlns.jcp.org/xml/ns/javaee                       http://xmlns.jcp.org/xml/ns/javaee/web-app_3_1.xsd\">" +
					"" +
					"<filter>" +
							"<filter-name>responseHeadersFilter</filter-name>" +
							"<filter-class>example.ResponseHeadersFilter</filter-class>" +
							"<init-param>" +
								"<param-name>Expires</param-name>" +
								"<param-value>0</param-value>" +
							"</init-param>" +
						"</filter>" +
							// NOTE: BUG
						"<filter-mapping>" +
							"<filter-name>anotherFilter</filter-name>" +
							"<url-pattern>/*</url-pattern>" +
						"</filter-mapping>" +
					"</web-app>" ;
			//@formatter:on
	private static DocumentBuilderFactory documentBuilderFactory;
	private static DocumentBuilder documentBuilder;
	private static Document document;

	private static String name = "xxx";
	private static String tag1 = "filter";
	private static String tag3 = "filter-name";

	@BeforeClass
	public static void load()
			throws ParserConfigurationException, SAXException, IOException {
		documentBuilderFactory = DocumentBuilderFactory.newInstance();
		documentBuilder = documentBuilderFactory.newDocumentBuilder();
		InputSource source = new InputSource(new StringReader(resource));
		document = documentBuilder.parse(source);
	}

	@Test
	public void searchMissingNodeTest() {
		assertThat(MergeDocumentFragments.searchNode(document, "filter",
				"filter-name", "missing"), is(false));
	}

	@Test
	public void searchExistingNodeTest() {
		assertThat(MergeDocumentFragments.searchNode(document, "filter",
				"filter-name", "responseHeadersFilter"), is(true));

	}

	@Test
	public void searchDirtySiblingchildNodeTest() {
		name = "anotherFilter";
		tag1 = "filter";
		tag3 = "filter-name";
		Boolean result = MergeDocumentFragments.searchNode(document, tag1, tag3,
				name);
		assertThat(result, is(false));

	}
}
