package example;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.hamcrest.CoreMatchers.is;

import java.util.HashSet;
import java.util.Locale;
import java.util.Map;

import org.assertj.core.api.Assertions;
import org.assertj.core.util.Arrays;
import org.junit.Before;
import org.junit.Test;

// inspired by http://grokdebug.herokuapp.com
// planning javafx standalone app
public class GrokDebugTest {

	private static final boolean debug = false;
	private static Grok grok;
	private static GrokCompiler compiler;
	private static Map<String, Object> capture;
	private static final String log = "06/Mar/2013:01:36:30 +0900";

	private static final String datePatterns = "HTTPDATE %{MONTHDAY}/%{MONTH}/%{YEAR}:%{TIME} %{INT}\n"
			+ "YEAR (?>\\d\\d){1,2}\n" + "# Time: HH:MM:SS\n"
			+ "INT (?:[+-]?(?:[0-9]+))\n"
			+ "MONTH \\b(?:Jan(?:uary)?|Feb(?:ruary)?|Mar(?:ch)?|Apr(?:il)?|May|Jun(?:e)?|Jul(?:y)?|Aug(?:ust)?|Sep(?:tember)?|Oct(?:ober)?|Nov(?:ember)?|Dec(?:ember)?)\\b\n"
			+ "MONTHDAY (?:(?:0[1-9])|(?:[12][0-9])|(?:3[01])|[1-9])\n"
			+ "HOUR (?:2[0123]|[01]?[0-9])\n" + "MINUTE (?:[0-5][0-9])\n"
			+ "SECOND (?:(?:[0-5]?[0-9]|60)(?:[:.,][0-9]+)?)\n"
			+ "TIME (?!<[0-9])%{HOUR}:%{MINUTE}(?::%{SECOND})(?![0-9])\n";

	static {
		Locale.setDefault(Locale.ROOT);
	}

	@Before
	public void setUp() throws Exception {
		compiler = GrokCompiler.newInstance();
		compiler.registerPatternFromString(datePatterns);
	}

	@SuppressWarnings("deprecation")
	@Test
	public void test1() {
		final Object[] keysArray = new Object[] { "HTTPDATE", "MONTHDAY", "MONTH",
				"YEAR", "TIME", "HOUR", "MINUTE", "SECOND", "INT" };

		grok = compiler.compile("%{HTTPDATE}");
		capture = grok.match(log).capture();

		Assertions.assertThat(capture).hasSize(keysArray.length);
		if (debug)
			capture.keySet().stream().forEach(System.err::println);
		assertThat(new HashSet<Object>(capture.keySet()),
				containsInAnyOrder(keysArray));
	}

	@SuppressWarnings("deprecation")
	@Test
	public void test2() {
		final String field = "data";
		final Object[] keysArray = new Object[] { field, "MONTHDAY", "MONTH",
				"YEAR", "TIME", "HOUR", "MINUTE", "SECOND", "INT" };

		grok = compiler.compile(String.format("%%{HTTPDATE:%s}", field));

		capture = grok.match(log).capture();
		Assertions.assertThat(capture).hasSize(keysArray.length);
		if (debug)
			capture.keySet().stream().forEach(System.err::println);
		Arrays.asList(keysArray).stream()
				.forEach(o -> assertThat(capture.keySet(), hasItem((String) o)));
	}

	@Test
	public void test3() {
		compiler.registerPatternFromString(
				"HOUR (?:2[0123]|[01]?[0-9])\n" + "MINUTE (?:[0-5][0-9])\n"
						+ "SECOND (?:(?:[0-5]?[0-9]|60)(?:[:.,][0-9]+)?)\n"
						+ "TIME (?!<[0-9])%{HOUR}:%{MINUTE}(?::%{SECOND})(?![0-9])\n");
		final String field = "data";
		final Object[] keysArray = new Object[] { "TIME", "HOUR", "MINUTE",
				"SECOND" };

		grok = compiler.compile("(?:(?:0[1-9])|(?:[12][0-9])|(?:3[01])|[1-9])" + "/"
				+ "\\b(?:Jan|Feb|Mar|Apr|May|Jun|Jul|Aug|Sep|Oct|Nov|Dec)\\b" + "/"
				+ "(?>\\d\\d){1,2}:%{TIME} (?:[+-]?(?:[0-9]+))");

		capture = grok.match(log).capture();
		Assertions.assertThat(capture).hasSize(keysArray.length);
		if (debug)
			capture.keySet().stream().forEach(System.err::println);
		assertTrue(new HashSet<Object>(Arrays.asList(keysArray))
				.containsAll(new HashSet<Object>(capture.keySet())));

		assertTrue(new HashSet<Object>(capture.keySet())
				.containsAll(new HashSet<Object>(Arrays.asList(keysArray))));
	}

	@SuppressWarnings("deprecation")
	@Test
	public void test4() {
		compiler.registerPatternFromString(
				"HOUR (?:2[0123]|[01]?[0-9])\n" + "MINUTE (?:[0-5][0-9])\n"
						+ "SECOND (?:(?:[0-5]?[0-9]|60)(?:[:.,][0-9]+)?)\n"
						+ "TIME (?!<[0-9])%{HOUR}:%{MINUTE}(?::%{SECOND})(?![0-9])\n"
						+ "DATA .*?\n");
		final Object[] keysArray = new Object[] { "DATA", "TIME", "HOUR", "MINUTE",
				"SECOND" };

		grok = compiler
				.compile("%{DATA}/%{DATA}/(?>\\d\\d){1,2}:%{TIME} (?:[+-]?(?:[0-9]+))");

		capture = grok.match(log).capture();
		Assertions.assertThat(capture).hasSize(keysArray.length);
		if (debug)
			capture.keySet().stream().forEach(System.err::println);
		assertTrue(new HashSet<Object>(Arrays.asList(keysArray))
				.containsAll(new HashSet<Object>(capture.keySet())));

		assertTrue(new HashSet<Object>(capture.keySet())
				.containsAll(new HashSet<Object>(Arrays.asList(keysArray))));
		assertThat(capture.get("DATA"), notNullValue());
		assertThat(capture.get("DATA").getClass().getName(),
				is("java.util.ArrayList"));
		assertThat(((java.util.ArrayList) capture.get("DATA")).size(), is(2));
	}

}

