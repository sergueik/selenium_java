package example;

import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

import example.Converter.IConverter;

public class Grok {
	private final String namedRegex;
	private final Map<String, String> namedRegexCollection;
	private final String originalGrokPattern;
	private final Pattern compiledNamedRegex;
	private final Map<String, String> grokPatternDefinition;
	public final Set<String> namedGroups;
	public final Map<String, Converter.Type> groupTypes;
	public final Map<String, IConverter<? extends Object>> converters;
	private Discovery disco;

	private String savedPattern = "";

	public Grok(String pattern, String namedRegex,
			Map<String, String> namedRegexCollection,
			Map<String, String> patternDefinitions, ZoneId defaultTimeZone) {
		this.originalGrokPattern = pattern;
		this.namedRegex = namedRegex;
		this.compiledNamedRegex = Pattern.compile(namedRegex);
		this.namedRegexCollection = namedRegexCollection;
		this.namedGroups = GrokUtils.getNameGroups(namedRegex);
		this.groupTypes = Converter.getGroupTypes(namedRegexCollection.values());
		this.converters = Converter.getConverters(namedRegexCollection.values(),
				defaultTimeZone);
		this.grokPatternDefinition = patternDefinitions;
	}

	public String getSaved_pattern() {
		return savedPattern;
	}

	public void setSaved_pattern(String data) {
		savedPattern = data;
	}

	public Map<String, String> getPatterns() {
		return grokPatternDefinition;
	}

	public String getNamedRegex() {
		return namedRegex;
	}

	public String getOriginalGrokPattern() {
		return originalGrokPattern;
	}

	public String getNamedRegexCollectionById(String id) {
		return namedRegexCollection.get(id);
	}

	public Map<String, String> getNamedRegexCollection() {
		return namedRegexCollection;
	}

	public Map<String, Object> capture(String log) {
		Match match = match(log);
		return match.capture();
	}

	public ArrayList<Map<String, Object>> capture(List<String> logs) {
		final ArrayList<Map<String, Object>> matched = new ArrayList<>();
		for (String log : logs) {
			matched.add(capture(log));
		}
		return matched;
	}

	public Match match(CharSequence text) {
		if (compiledNamedRegex == null || text == null) {
			return Match.EMPTY;
		}

		Matcher matcher = compiledNamedRegex.matcher(text);
		if (matcher.find()) {
			return new Match(text, this, matcher, matcher.start(0), matcher.end(0));
		}

		return Match.EMPTY;
	}

	public String discover(String input) {

		if (disco == null) {
			disco = new Discovery(this);
		}
		return disco.discover(input);
	}
}
