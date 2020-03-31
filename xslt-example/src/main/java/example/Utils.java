package example;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utils {

	protected static String getScriptContent(String scriptName) {
		try {
			final InputStream stream = MergeDocumentFragments.class.getClassLoader()
					.getResourceAsStream(scriptName);
			final byte[] bytes = new byte[stream.available()];
			stream.read(bytes);
			return new String(bytes, "UTF-8");
		} catch (IOException e) {
			throw new RuntimeException(scriptName);
		}
	}

	protected static String getPageContent(String pagename) {
		try {
			URI uri = MergeDocumentFragments.class.getClassLoader()
					.getResource(pagename).toURI();
			System.err.println("Testing local file: " + uri.toString());
			return uri.toString();
		} catch (URISyntaxException e) {
			throw new RuntimeException(e);
		}
	}

	public static String resolveEnvVars(String input) {
		if (null == input) {
			return null;
		}
		Pattern pattern = Pattern.compile("\\$(?:\\{(?:env:)?(\\w+)\\}|(\\w+))");
		Matcher matcher = pattern.matcher(input);
		StringBuffer sb = new StringBuffer();
		while (matcher.find()) {
			String envVarName = null == matcher.group(1) ? matcher.group(2)
					: matcher.group(1);
			String envVarValue = System.getenv(envVarName);
			matcher.appendReplacement(sb,
					null == envVarValue ? "" : envVarValue.replace("\\", "\\\\"));
		}
		matcher.appendTail(sb);
		return sb.toString();
	}

	public static String replaceXPath(String input, String name, boolean debug) {
		if (null == input) {
			return null;
		}
		Matcher matcher = (Pattern.compile("\\[text\\(\\)='(?:\\w+)'\\]"))
				.matcher(input);
		if (debug) {
			if (!matcher.find()) {
				return null;
			}
		}
		return matcher.replaceAll(String.format("[text()='%s']", name));
	}

	public static String replaceXPath(String input, String tag1, String tag2,
			String name, boolean debug) {
		final String prefix = "xxx";
		return replaceXPath(input, prefix, tag1, tag2, name, debug);
	}

	public static String replaceXPath(String input, String prefix, String tag1,
			String tag2, String name, boolean debug) {
		if (null == input) {
			return null;
		}
		Matcher matcher = Pattern
				.compile(
						"(?:\\w+):(?:[a-z.0-9-]+)\\[(?:\\w+):(?:[a-z.0-9-]+)\\[text\\(\\)='(?:\\w+)'\\]\\]")
				.matcher(input);
		if (debug) {
			if (!matcher.find()) {
				return null;
			}
		}
		return matcher.replaceAll(String.format("%s:%s[%s:%s[text()='%s']]", prefix,
				tag1, prefix, tag2, name));
	}
}
