package example;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

import java.io.IOException;
import java.util.Map;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

public class CrontabParseTest {

	private static final boolean debug = true;
	private static final GrokCompiler grokCompiler = GrokCompiler.newInstance();
	private static Grok grok;
	private static String expression;
	// https://www.thegeekstuff.com/2009/06/15-practical-crontab-examples/
	private static String entry;
	private static Match gm;
	private static Map<String, Object> capture;

	@Before
	public void beforeTest() throws IOException {
		grokCompiler.registerPatternFromClasspath("/patterns/cron");
		grokCompiler.registerDefaultPatterns();
	}

	@Test
	public void test1() {
		expression = "%{CRONTAB_MINUTE}";
		grok = grokCompiler.compile(expression);
		entry = "*";
		gm = grok.match(entry);
		capture = gm.capture();
		printCaptureKyes("Captured " + capture.keySet().size() + " fields in "
				+ entry + " : " + expression, capture);
		assertThat(capture.size(), is(2));
	}

	@Test
	public void test12() {
		expression = "%{CRONTAB_MINUTE}";
		grok = grokCompiler.compile(expression);
		entry = "30";
		gm = grok.match(entry);
		capture = gm.capture();
		printCaptureKyes("Captured " + capture.keySet().size() + " fields in "
				+ entry + " : " + expression, capture);
		assertThat(capture.size(), is(2));
	}

	@Test
	public void test13() {
		expression = "%{CRONTAB_MINUTE} %{CRONTAB_HOUR}";
		grok = grokCompiler.compile(expression);
		entry = "30 8";
		gm = grok.match(entry);
		capture = gm.capture();
		printCaptureKyes("Captured " + capture.keySet().size() + " fields in "
				+ entry + " : " + expression, capture);
		assertThat(capture.size(), is(4));
	}

	@Test
	public void test14() {
		expression = "%{CRONTAB_MINUTE} %{CRONTAB_HOUR}";
		grok = grokCompiler.compile(expression);
		entry = "30 08";
		gm = grok.match(entry);
		capture = gm.capture();
		printCaptureKyes("Captured " + capture.keySet().size() + " fields in "
				+ entry + " : " + expression, capture);
		assertThat(capture.size(), is(4));
	}

	@Test
	public void test2() {
		expression = "%{CRONTAB_MONTHDAY}";
		capture = grokCompiler.compile(expression).match("1,11,21,31").capture();
		printCaptureKyes("Captured " + capture.keySet().size() + " fields in "
				+ entry + " : " + expression, capture);
		assertThat(capture.size(), is(1));
		// assertThat(capture.get("CRONTAB_MONTHDAY"), is("1,11,21,31"));

	}

	@Test
	public void test21() {
		// grokCompiler.registerPatternFromClasspath("/patterns/elasticsearch");

		expression = "(?<mday>(?:%{MONTHDAY}(?:(?:,%{MONTHDAY})*|(?:\\-%{MONTHDAY}))))";
		entry = "1,11,21,31";
		capture = grokCompiler.compile(expression).match(entry).capture();
		printCaptureKyes("Captured " + capture.keySet().size() + " fields in "
				+ entry + " : " + expression, capture);
		assertThat(capture.size(), is(2));
		assertThat(capture.get("mday"), is("1,11,21,31"));
	}

	@Test
	public void test3() {
		expression = "%{CRONTAB_WEEKDAY:weekday}";
		grok = grokCompiler.compile(expression);
		grokCompiler.registerPatternFromClasspath("/patterns/elasticsearch");
		entry = "SAT-SUN";
		capture = grok.match(entry).capture();
		printCaptureKyes("Captured " + capture.keySet().size() + " fields in "
				+ entry + " : " + expression, capture);
		assertThat(capture.size(), is(1));
		// assertThat(capture.get("weekday"), is("SAT-SUN"));
	}

	// @Ignore
	@Test
	public void test32() {
		expression = "^%{CRONTAB_WEEKDAY:crontab_weekday}";
		grok = grokCompiler.compile(expression);
		grokCompiler.registerPatternFromClasspath("/patterns/elasticsearch");
		entry = "SAT-SUN";
		capture = grok.match(entry).capture();
		printCaptureKyes("Captured " + capture.keySet().size() + " fields in "
				+ entry + " : " + expression, capture);
		assertThat(capture.size(), is(2));
		// TODO: only captures SAT
		// assertThat(capture.get("weekday"), is("SAT-SUN"));
	}

	@Test
	public void test31() {
		expression = "%{CRONTAB_WEEKDAY:crontab_weekday} %{GREEDYDATA:command}";
		grok = grokCompiler.compile(expression);
		grokCompiler.registerPatternFromClasspath("/patterns/elasticsearch");
		entry = "SAT-SUN /usr/bin/cat";
		capture = grok.match(entry).capture();
		printCaptureKyes("Captured " + capture.keySet().size() + " fields in "
				+ entry + " : " + expression, capture);
		assertThat(capture.size(), is(3));
		// TODO: captures only SUN
		// assertThat(capture.get("crontab_weekday"), is("SAT-SUN"));
		assertThat(capture.get("command"), is("/usr/bin/cat"));
	}

	// TODO: add 'generate' method and test
	// TODO: combine
	@Test
	public void test4() {
		expression = "%{CRONTAB_MINUTE} %{CRONTAB_HOUR} %{CRONTAB_MONTHDAY} %{CRONTAB_MONTH} %{CRONTAB_WEEKDAY} %{GREEDYDATA}";
		grok = grokCompiler.compile(expression);
		grokCompiler.registerPatternFromClasspath("/patterns/elasticsearch");

		entry = "30 08 10 06 * /home/ramesh/full-backup";

		Match gm = grok.match(entry);
		assertThat(gm, notNullValue());
		capture = gm.capture();
		printCaptureKyes("Captured " + capture.keySet().size() + " fields in "
				+ entry + " : " + expression, capture);
	}

	// TODO: create and test 'generate' method
	@Test
	public void test5() {
		expression = "%{CRONTAB_MINUTE} %{CRONTAB_HOUR} %{CRONTAB_MONTHDAY} %{CRONTAB_MONTH} %{CRONTAB_WEEKDAY}";
		grok = grokCompiler.compile(expression);
		entry = "30 08 10 06 *";
		gm = grok.match(entry);

		capture = gm.capture();
		printCaptureKyes("Captured " + capture.keySet().size() + " fields in "
				+ entry + " : " + expression, capture);
	}

	@Test
	public void test6() {
		expression = "%{CRONTAB_MINUTE:minute} %{CRONTAB_HOUR:hour}";
		grok = grokCompiler.compile(expression);
		entry = "* *";
		gm = grok.match(entry);
		capture = gm.capture();
		printCaptureKyes("Captured " + capture.keySet().size() + " fields in "
				+ entry + " : " + expression, capture);
	}

	@Test
	public void test7() {
		grok = grokCompiler.compile("%{CRONTAB_SCHEDULE1}");

		entry = "30 * * * *";
		// entry = "30 08 10 06 *";

		gm = grok.match(entry);

		capture = gm.capture();
		printCaptureKyes(
				"Captured " + capture.keySet().size() + " fields in " + entry + ":",
				capture);
	}

	@Test
	public void test10() {
		expression = "%{CRONTAB_SCHEDULE} %{GREEDYDATA}";
		grok = grokCompiler.compile(expression);

		entry = "30 08 10 06 * /home/ramesh/full-backup";

		gm = grok.match(entry);

		capture = gm.capture();
		printCaptureKyes("Captured " + capture.keySet().size() + " fields in "
				+ entry + " : " + expression, capture);
	}

	private static void printCaptureKyes(String message,
			Map<String, Object> capture) {

		if (debug) {
			System.err.println(message);
			capture.keySet().stream().map(o -> String.format("%s\t", o))
					.forEach(System.err::println);
		}
	}

}

