package example;

import static java.lang.String.format;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

import example.exception.GrokException;

public class GrokCompiler {

	// We don't want \n and commented line
	private static final Pattern patternLinePattern = Pattern
			.compile("^([A-z0-9_]+)\\s+(.*)$");

	private final Map<String, String> grokPatternDefinitions = new HashMap<>();

	private GrokCompiler() {
	}

	public static GrokCompiler newInstance() {
		return new GrokCompiler();
	}

	public Map<String, String> getPatternDefinitions() {
		return grokPatternDefinitions;
	}

	public void register(String name, String pattern) {
		name = Objects.requireNonNull(name).trim();
		pattern = Objects.requireNonNull(pattern).trim();

		if (!name.isEmpty() && !pattern.isEmpty()) {
			grokPatternDefinitions.put(name, pattern);
		}
	}

	public void register(Map<String, String> patternDefinitions) {
		Objects.requireNonNull(patternDefinitions);
		patternDefinitions.forEach(this::register);
	}

	public void registerDefaultPatterns() {
		registerPatternFromClasspath("/patterns/patterns");
	}

	public void registerPatternFromClasspath(String path) throws GrokException {
		registerPatternFromClasspath(path, StandardCharsets.UTF_8);
	}

	//
	public void registerPatternFromString(String payload) {
		Reader reader = new StringReader(payload);
		try {
			register(reader);
		} catch (IOException e) {
			throw new GrokException(e.getMessage(), e);
		}
	}

	public void registerPatternFromClasspath(String path, Charset charset)
			throws GrokException {
		final InputStream inputStream = this.getClass().getResourceAsStream(path);
		try (Reader reader = new InputStreamReader(inputStream, charset)) {
			register(reader);
		} catch (IOException e) {
			throw new GrokException(e.getMessage(), e);
		}
	}

	public void register(InputStream input) throws IOException {
		register(input, StandardCharsets.UTF_8);
	}

	public void register(InputStream input, Charset charset) throws IOException {
		try (BufferedReader in = new BufferedReader(
				new InputStreamReader(input, charset))) {
			in.lines().map(patternLinePattern::matcher).filter(Matcher::matches)
					.forEach(m -> register(m.group(1), m.group(2)));
		}
	}

	public void register(Reader input) throws IOException {
		new BufferedReader(input).lines().map(patternLinePattern::matcher)
				.filter(Matcher::matches)
				.forEach(m -> register(m.group(1), m.group(2)));
	}

	public Grok compile(String pattern) throws IllegalArgumentException {
		return compile(pattern, false);
	}

	public Grok compile(final String pattern, boolean namedOnly)
			throws IllegalArgumentException {
		return compile(pattern, ZoneOffset.systemDefault(), namedOnly);
	}

	public Grok compile(final String pattern, ZoneId defaultTimeZone,
			boolean namedOnly) throws IllegalArgumentException {

		if (StringUtils.isBlank(pattern)) {
			throw new IllegalArgumentException(
					"{pattern} should not be empty or null");
		}

		String namedRegex = pattern;
		int index = 0;
		/** flag for infinite recursion. */
		int iterationLeft = 1000;
		Boolean continueIteration = true;
		Map<String, String> patternDefinitions = new HashMap<>(
				grokPatternDefinitions);

		// output
		Map<String, String> namedRegexCollection = new HashMap<>();

		// Replace %{foo} with the regex (mostly group name regex)
		// and then compile the regex
		while (continueIteration) {
			continueIteration = false;
			if (iterationLeft <= 0) {
				throw new IllegalArgumentException(
						"Deep recursion pattern compilation of " + pattern);
			}
			iterationLeft--;

			Set<String> namedGroups = GrokUtils
					.getNameGroups(GrokUtils.GROK_PATTERN.pattern());
			Matcher matcher = GrokUtils.GROK_PATTERN.matcher(namedRegex);
			// Match %{Foo:bar} -> pattern name and subname
			// Match %{Foo=regex} -> add new regex definition
			if (matcher.find()) {
				continueIteration = true;
				Map<String, String> group = GrokUtils.namedGroups(matcher, namedGroups);
				if (group.get("definition") != null) {
					patternDefinitions.put(group.get("pattern"), group.get("definition"));
					group.put("name", group.get("name") + "=" + group.get("definition"));
				}
				int count = StringUtils.countMatches(namedRegex,
						"%{" + group.get("name") + "}");
				for (int i = 0; i < count; i++) {
					String definitionOfPattern = patternDefinitions
							.get(group.get("pattern"));
					if (definitionOfPattern == null) {
						throw new IllegalArgumentException(
								format("No definition for key '%s' found, aborting",
										group.get("pattern")));
					}
					String replacement = String.format("(?<name%d>%s)", index,
							definitionOfPattern);
					if (namedOnly && group.get("subname") == null) {
						replacement = String.format("(?:%s)", definitionOfPattern);
					}
					namedRegexCollection.put("name" + index, (group.get("subname") != null
							? group.get("subname") : group.get("name")));
					namedRegex = StringUtils.replace(namedRegex,
							"%{" + group.get("name") + "}", replacement, 1);
					// System.out.println(_expanded_pattern);
					index++;
				}
			}
		}

		if (namedRegex.isEmpty()) {
			throw new IllegalArgumentException("Pattern not found");
		}

		return new Grok(pattern, namedRegex, namedRegexCollection,
				patternDefinitions, defaultTimeZone);
	}
}

