package example;
/**
 * Copyright 2020 Serguei Kouzmine
 */

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertThat;

import java.util.Arrays;

import org.junit.BeforeClass;
import org.junit.Test;
import example.Utils;

/**
 * Unit Tests for Utils 
 * @author: Serguei Kouzmine (kouzmine_serguei@yahoo.com)
 */
@SuppressWarnings("deprecation")
public class UtilsTest {

	private static boolean debug = true;

	private final static String resource = "<xsl:template match=\"xxx:filter-mapping[xxx:filter-name[text()='httpHeaderSecurity']]\">";

	@Test
	public void xpathReplaceNameTest() {
		String result = Utils.replaceXPath(resource, "dummy", true);
		assertThat(result, notNullValue());
		assertThat(result, containsString("dummy"));
		if (debug) {
			System.err.println("xpathReplaceNameTest result: " + result);
		}
	}

	@Test
	public void xpathReplaceTagNameTest() {
		String result = Utils.replaceXPath(resource, "tag", "tag-info", "dummy",
				true);
		assertThat(result, notNullValue());
		assertThat(result, containsString("tag"));
		assertThat(result, containsString("tag-info"));
		assertThat(result, containsString("dummy"));
		if (debug) {
			System.err.println("xpathReplaceTagNameTest result: " + result);
		}
	}

	@Test
	public void xpathReplacePrefixTagNameTest() {
		String result = Utils.replaceXPath(resource, "prefix", "tag", "tag-info",
				"dummy", true);
		assertThat(result, notNullValue());
		assertThat(result, containsString("prefix:tag"));
		assertThat(result, containsString("prefix:tag-info"));
		assertThat(result, containsString("dummy"));
		if (debug) {
			System.err.println("xpathReplacePrefixTagNameTest result: " + result);
		}
	}
}
