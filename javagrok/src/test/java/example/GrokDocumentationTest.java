package example;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.junit.Assert.assertThat;

import java.util.HashSet;
import java.util.Map;

import org.assertj.core.api.Assertions;
import org.junit.Test;

import example.Grok;
import example.GrokCompiler;
import example.Match;

public class GrokDocumentationTest {
	private static Object[] keywordArray = new Object[] { "COMBINEDAPACHELOG",
			"COMMONAPACHELOG", "clientip", "IP", "IPV6", "IPV4", "HOSTNAME", "ident",
			"EMAILADDRESS", "EMAILLOCALPART", "USER", "USERNAME", "auth", "timestamp",
			"MONTHDAY", "MONTH", "YEAR", "TIME", "HOUR", "MINUTE", "SECOND", "INT",
			"verb", "request", "httpversion", "BASE10NUM", "rawrequest", "response",
			"bytes", "referrer", "QUOTEDSTRING", "agent" };

	@SuppressWarnings("deprecation")
	@Test
	public void assureCodeInReadmeWorks() {
		GrokCompiler grokCompiler = GrokCompiler.newInstance();
		grokCompiler.registerDefaultPatterns();

		final Grok grok = grokCompiler.compile("%{COMBINEDAPACHELOG}");

		String log = "112.169.19.192 - - [06/Mar/2013:01:36:30 +0900] \"GET / HTTP/1.1\" 200 44346 \"-\" "
				+ "\"Mozilla/5.0 (Macintosh; Intel Mac OS X 10_8_2) AppleWebKit/537.22 (KHTML, like Gecko) "
				+ "Chrome/25.0.1364.152 Safari/537.22\"";

		Match gm = grok.match(log);

		/* Get the map with matches */
		final Map<String, Object> capture = gm.capture();

		Assertions.assertThat(capture).hasSize(22);
		// capture.keySet().stream().forEach(System.err::println);
		assertThat(new HashSet<Object>(capture.keySet()),
				containsInAnyOrder(keywordArray));
	}
}
