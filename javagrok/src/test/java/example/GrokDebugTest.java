package example;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.hasItem;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

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
	private static Object[] keywordArray = new Object[] {
			/* "HTTPDATE", */ "timestamp", "MONTHDAY", "MONTH", "YEAR", "TIME",
			"HOUR", "MINUTE", "SECOND", "INT" };
	private static Grok grok;
	private static GrokCompiler compiler;

	private static final String datePatterns = "HTTPDATE %{MONTHDAY}/%{MONTH}/%{YEAR}:%{TIME} %{INT}\n"
			+ "YEAR (?>\\d\\d){1,2}\n" + "# Time: HH:MM:SS\n"
			+ "INT (?:[+-]?(?:[0-9]+))\n"
			+ "MONTH \\b(?:Jan(?:uary)?|Feb(?:ruary)?|Mar(?:ch)?|Apr(?:il)?|May|Jun(?:e)?|Jul(?:y)?|Aug(?:ust)?|Sep(?:tember)?|Oct(?:ober)?|Nov(?:ember)?|Dec(?:ember)?)\\b\n"
			+ "MONTHDAY (?:(?:0[1-9])|(?:[12][0-9])|(?:3[01])|[1-9])\n"
			+ "HOUR (?:2[0123]|[01]?[0-9])\n"
			+ "HOUR (?:2[0123]|[01]?[0-9])\n" 
			+ "MINUTE (?:[0-5][0-9])\n"
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
		String log = "06/Mar/2013:01:36:30 +0900";
		grok = compiler.compile("%{HTTPDATE:timestamp}");
		Match gm = grok.match(log);

		/* Get the map with matches */
		final Map<String, Object> capture = gm.capture();
		// we are not capturing the outermost so let's remove "HTTPDATE" from
		// keywordArray
		Assertions.assertThat(capture).hasSize(keywordArray.length);
		if (debug)
			capture.keySet().stream().forEach(System.err::println);
		assertTrue(new HashSet<Object>(Arrays.asList(keywordArray))
				.containsAll(new HashSet<Object>(capture.keySet())));

		Arrays.asList(keywordArray).stream()
				.forEach(o -> assertThat(capture.keySet(), hasItem((String) o)));
		assertThat(new HashSet<Object>(capture.keySet()),
				containsInAnyOrder(keywordArray));
		assertTrue(new HashSet<Object>(capture.keySet())
				.containsAll(new HashSet<Object>(Arrays.asList(keywordArray))));
	}

}

