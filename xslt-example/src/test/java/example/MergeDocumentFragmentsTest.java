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
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;

import org.junit.BeforeClass;
import org.junit.Test;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import example.MergeDocumentFragments;

/**
 * Unit Tests for MergeDocumentFragments 
 * @author: Serguei Kouzmine (kouzmine_serguei@yahoo.com)
 */

public class MergeDocumentFragmentsTest {
	private static boolean debug = true;

	private final static String resource = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
			+ "<web-app xmlns=\"http://xmlns.jcp.org/xml/ns/javaee\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" version=\"3.1\" xsi:schemaLocation=\"http://xmlns.jcp.org/xml/ns/javaee http://xmlns.jcp.org/xml/ns/javaee/web-app_3_1.xsd\">"
			+ "<filter>" + "<filter-name>httpHeaderSecurity</filter-name>"
			+ "<filter-class>org.apache.catalina.filters.HttpHeaderSecurityFilter</filter-class>"
			+ "<async-supported>true</async-supported>" + "</filter>"
			+ "<filter-mapping>" + "<filter-name>httpHeaderSecurity</filter-name>"
			+ "<url-pattern>/*</url-pattern>" + "<dispatcher>REQUEST</dispatcher>"
			+ "</filter-mapping>" + "</web-app>";

	private static DocumentBuilderFactory documentBuilderFactory;
	private static DocumentBuilder documentBuilder;
	private static Document document;

	@BeforeClass
	public static void load()
			throws ParserConfigurationException, SAXException, IOException {
		documentBuilderFactory = DocumentBuilderFactory.newInstance();
		documentBuilder = documentBuilderFactory.newDocumentBuilder();
		InputSource source = new InputSource(new StringReader(resource));
		document = documentBuilder.parse(source);
	}

	@Test
	public void searchNodeTest() {
		assertThat(MergeDocumentFragments.searchNode(document, "filter",
				"filter-name", "httpHeaderSecurity"), is(true));
		assertThat(MergeDocumentFragments.searchNode(document, "filter",
				"filter-name", "other"), is(false));
		assertThat(MergeDocumentFragments.searchNode(document, "filter", "tag", ""),
				is(false));
	}
}
