package example;

/**
 * Copyright 2021 Serguei Kouzmine
 */
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasKey;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import example.Grok;
import example.GrokCompiler;
import example.Match;
// import static org.junit.Assert.*;

// https://self-learning-java-tutorial.blogspot.com/2018/03/hamcrest-arraycontaininginanyorder.html
import static org.hamcrest.Matchers.arrayContainingInAnyOrder;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.CoreMatchers.notNullValue;

import org.apache.commons.collections4.CollectionUtils;

@SuppressWarnings("deprecation")
public class CrontabPatseTest {

	private static final boolean debug = true;
	private static final GrokCompiler grokCompiler = GrokCompiler.newInstance();
	private static Grok grok;
	// https://www.thegeekstuff.com/2009/06/15-practical-crontab-examples/
	private static String entry;
	private static Match gm;
	private static Map<String, Object> capture;

	@Before
	public void beforeTest() throws IOException {
		grokCompiler.registerPatternFromClasspath("/patterns/cron");

	}

	@Test
	public void test1() {

		grok = grokCompiler.compile("%{MINUTE:minute}");
		entry = "*";
		gm = grok.match(entry);
		capture = gm.capture();
		if (debug) {
			System.err.println("Captured " + capture.keySet().size() + " fields in " + entry + ":");
			capture.keySet().stream().map(o -> String.format("%s\t", o)).forEach(System.err::println);
		}
		assertThat(capture.size(), is(1));
	}

	@Test
	public void test2() {

		capture = grokCompiler.compile("%{MDAY}").match("1,11,21,31").capture();
		if (debug) {
			System.err.println("Captured " + capture.keySet().size() + " fields in " + entry + ":");
			capture.keySet().stream().map(o -> String.format("%s\t", o)).forEach(System.err::println);
		}
		assertThat(capture.size(), is(1));
		// assertThat(capture.get("MDAY"), is("1,11,21,31"));

	}

	@Test
	public void test21() {
		grokCompiler.registerPatternFromClasspath("/patterns/elasticsearch");

		entry = "1,11,21,31";
		capture = grokCompiler.compile("(?<mday>(?:%{MONTHDAY}(?:(?:,%{MONTHDAY})*|(?:\\-%{MONTHDAY}))))").match(entry)
				.capture();
		if (debug) {
			System.err.println("Captured " + capture.keySet().size() + " fields in " + entry + ":");
			capture.keySet().stream().map(o -> String.format("%s\t", o)).forEach(System.err::println);
		}
		assertThat(capture.size(), is(2));
		assertThat(capture.get("mday"), is("1,11,21,31"));
	}

	@Test
	public void test3() {
		grok = grokCompiler.compile("%{WEEKDAY:weekday}");
		grokCompiler.registerPatternFromClasspath("/patterns/elasticsearch");
		entry = "SAT-SUN";
		capture = grok.match(entry).capture();
		if (debug) {
			System.err.println("Captured " + capture.keySet().size() + " fields in " + entry + ":");
			capture.keySet().stream().map(o -> String.format("%s\t", o)).forEach(System.err::println);
		}
		assertThat(capture.size(), is(1));
		// assertThat(capture.get("weekday"), is("SAT-SUN"));
	}

	// TODO: add 'generate' method and test
	// TODO: combine
	@Test
	public void test4() {
		grok = grokCompiler.compile("%{MINUTE} %{HOUR} %{MDAY} %{MONTH} %{WEEKDAY} %{GREEDYDATA}");
		grokCompiler.registerPatternFromClasspath("/patterns/elasticsearch");

		entry = "30 08 10 06 * /home/ramesh/full-backup";

		Match gm = grok.match(entry);
		assertThat(gm, notNullValue());
		capture = gm.capture();
		if (debug) {
			System.err.println("Captured " + capture.keySet().size() + " fields in " + entry + ":");
			capture.keySet().stream().map(o -> String.format("%s\t", o)).forEach(System.err::println);
		}
	}

	// TODO: create and test 'generate' method
	@Test
	public void test5() {
		grok = grokCompiler.compile("%{MINUTE} %{HOUR} %{MDAY} %{MONTH} %{WEEKDAY}");
		entry = "30 08 10 06 *";
		gm = grok.match(entry);

		capture = gm.capture();
		if (debug) {
			System.err.println("Captured " + capture.keySet().size() + " fields in " + entry + ":");
			capture.keySet().stream().map(o -> String.format("%s\t", o)).forEach(System.err::println);
		}
	}

	@Test
	public void test6() {
		grok = grokCompiler.compile("%{MINUTE:minute} %{HOUR:hour}");
		entry = "* *";
		gm = grok.match(entry);
		capture = gm.capture();
		if (debug) {
			System.err.println("Captured " + capture.keySet().size() + " fields in " + entry + ":");
			capture.keySet().stream().map(o -> String.format("%s\t", o)).forEach(System.err::println);
		}
	}

	@Test
	public void test7() {
		grok = grokCompiler.compile("%{CRONTAB_SCHEDULE1}");

		entry = "30 * * * *";
		// entry = "30 08 10 06 *";

		gm = grok.match(entry);

		capture = gm.capture();
		if (debug) {
			System.err.println("Captured " + capture.keySet().size() + " fields in " + entry + ":");
			capture.keySet().stream().map(o -> String.format("%s\t", o)).forEach(System.err::println);
		}
	}

	@Test
	public void test10() {
		grok = grokCompiler.compile("%{CRONTAB_SCHEDULE} %{GREEDYDATA}");

		entry = "30 08 10 06 * /home/ramesh/full-backup";

		gm = grok.match(entry);

		capture = gm.capture();
		if (debug) {
			System.err.println("Captured " + capture.keySet().size() + " fields in " + entry + ":");
			capture.keySet().stream().map(o -> String.format("%s\t", o)).forEach(System.err::println);
		}
	}
}
