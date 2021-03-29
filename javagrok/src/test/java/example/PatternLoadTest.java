package example;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;

import org.assertj.core.api.Assertions;
import org.junit.Test;

import example.Grok;
import example.GrokCompiler;
import example.Match;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.Matchers.containsInAnyOrder;
// https://self-learning-java-tutorial.blogspot.com/2018/03/hamcrest-arraycontaininginanyorder.html
import static org.hamcrest.Matchers.arrayContainingInAnyOrder;
import static org.hamcrest.Matchers.hasItems;

import org.apache.commons.collections4.CollectionUtils;

public class PatternLoadTest {

	private static final boolean debug = false;
	private static Object[] keywordArray = new Object[] { "COMBINEDAPACHELOG",
			"COMMONAPACHELOG", "clientip", "IP", "IPV6", "IPV4", "HOSTNAME", "ident",
			"EMAILADDRESS", "EMAILLOCALPART", "USER", "USERNAME", "auth", "timestamp",
			"MONTHDAY", "MONTH", "YEAR", "TIME", "HOUR", "MINUTE", "SECOND", "INT",
			"verb", "request", "httpversion", "BASE10NUM", "rawrequest", "response",
			"bytes", "referrer", "QUOTEDSTRING", "agent" };

	@SuppressWarnings("deprecation")
	@Test
	public void test() {
		GrokCompiler grokCompiler = GrokCompiler.newInstance();
		grokCompiler.registerPatternFromClasspath("/patterns/elasticsearch");

		final Grok grok = grokCompiler.compile("%{COMBINEDAPACHELOG}");

		String log = "112.169.19.192 - - [06/Mar/2013:01:36:30 +0900] \"GET / HTTP/1.1\" 200 44346 \"-\" "
				+ "\"Mozilla/5.0 (Macintosh; Intel Mac OS X 10_8_2) AppleWebKit/537.22 (KHTML, like Gecko) "
				+ "Chrome/25.0.1364.152 Safari/537.22\"";

		Match gm = grok.match(log);

		final Map<String, Object> capture = gm.capture();
		Assertions.assertThat(capture).hasSize(32);
		if (debug)
			capture.keySet().stream().map(o->String.format("%s\t",o)).forEach(System.err::println);
		assertThat(new HashSet<Object>(capture.keySet()),
				containsInAnyOrder(keywordArray));
		assertThat(new HashSet<Object>(capture.keySet()), hasItems(keywordArray));
		assertTrue(new HashSet<Object>(capture.keySet())
				.containsAll(new HashSet<Object>(Arrays.asList(keywordArray))));
		assertTrue(new HashSet<Object>(Arrays.asList(keywordArray))
				.containsAll(new HashSet<Object>(capture.keySet())));

	}
}
