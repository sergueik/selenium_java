package example;

import static java.lang.String.format;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;

import example.Converter.IConverter;
import example.exception.GrokException;

public class Match {
	private final CharSequence subject;
	private final Grok grok;
	private final Matcher match;
	private final int start;
	private final int end;
	private boolean keepEmptyCaptures = true;
	private Map<String, Object> capture = Collections.emptyMap();

	public Match(CharSequence subject, Grok grok, Matcher match, int start,
			int end) {
		this.subject = subject;
		this.grok = grok;
		this.match = match;
		this.start = start;
		this.end = end;
	}

	public static final Match EMPTY = new Match("", null, null, 0, 0);

	public Matcher getMatch() {
		return match;
	}

	public int getStart() {
		return start;
	}

	public int getEnd() {
		return end;
	}

	public void setKeepEmptyCaptures(boolean ignore) {
		// clear any cached captures
		if (capture.size() > 0) {
			capture = new LinkedHashMap<>();
		}
		keepEmptyCaptures = ignore;
	}

	public boolean isKeepEmptyCaptures() {
		return keepEmptyCaptures;
	}

	public CharSequence getSubject() {
		return subject;
	}

	public Map<String, Object> capture() {
		return capture(false);
	}

	/**
	 * Match to the <tt>subject</tt> the <tt>regex</tt> and save the matched element into a map
	 *
	 * Multiple values to the same key are flattened to one value: the sole non-null value will be captured.
	 * Should there be multiple non-null values a RuntimeException is being thrown.
	 *
	 * This can be used in cases like: (foo (.*:message) bar|bar (.*:message) foo) where the regexp guarantees that only
	 * one value will be captured.
	 *
	 * See also {@link #capture} which returns multiple values of the same key as list.
	 *
	 * @return the matched elements
	 * @throws GrokException if a keys has multiple non-null values.
	 */
	public Map<String, Object> captureFlattened() throws GrokException {
		return capture(true);
	}

	/**
	 * Private implementation of captureFlattened and capture.
	 * @param flattened will it flatten values.
	 * @return the matched elements.
	 * @throws GrokException if a keys has multiple non-null values, but only if flattened is set to true.
	 */
	private Map<String, Object> capture(boolean flattened) throws GrokException {
		if (match == null) {
			return Collections.emptyMap();
		}

		if (!capture.isEmpty()) {
			return capture;
		}

		capture = new LinkedHashMap<>();

		// _capture.put("LINE", this.line);
		// _capture.put("LENGTH", this.line.length() +"");

		Map<String, String> mappedw = GrokUtils.namedGroups(this.match,
				this.grok.namedGroups);

		mappedw.forEach((key, valueString) -> {
			String id = this.grok.getNamedRegexCollectionById(key);
			if (id != null && !id.isEmpty()) {
				key = id;
			}

			if ("UNWANTED".equals(key)) {
				return;
			}

			Object value = valueString;
			if (valueString != null) {
				IConverter<?> converter = grok.converters.get(key);

				if (converter != null) {
					key = Converter.extractKey(key);
					try {
						value = converter.convert(valueString);
					} catch (Exception e) {
						capture.put(key + "_grokfailure", e.toString());
					}

					if (value instanceof String) {
						value = cleanString((String) value);
					}
				} else {
					value = cleanString(valueString);
				}
			} else if (!isKeepEmptyCaptures()) {
				return;
			}

			if (capture.containsKey(key)) {
				Object currentValue = capture.get(key);

				if (flattened) {
					if (currentValue == null && value != null) {
						capture.put(key, value);
					}
					if (currentValue != null && value != null) {
						throw new GrokException(format(
								"key '%s' has multiple non-null values, this is not allowed in flattened mode, values:'%s', '%s'",
								key, currentValue, value));
					}
				} else {
					if (currentValue instanceof List) {
						@SuppressWarnings("unchecked")
						List<Object> cvl = (List<Object>) currentValue;
						cvl.add(value);
					} else {
						List<Object> list = new ArrayList<Object>();
						list.add(currentValue);
						list.add(value);
						capture.put(key, list);
					}
				}
			} else {
				capture.put(key, value);
			}
		});

		capture = Collections.unmodifiableMap(capture);

		return capture;
	}

	/**
	 * remove from the string the quote and double quote.
	 *
	 * @param value string to pure: "my/text"
	 * @return unquoted string: my/text
	 */
	private String cleanString(String value) {
		if (value == null || value.isEmpty()) {
			return value;
		}

		char firstChar = value.charAt(0);
		char lastChar = value.charAt(value.length() - 1);

		if (firstChar == lastChar && (firstChar == '"' || firstChar == '\'')) {
			if (value.length() <= 2) {
				return "";
			} else {
				int found = 0;
				for (int i = 1; i < value.length() - 1; i++) {
					if (value.charAt(i) == firstChar) {
						found++;
					}
				}
				if (found == 0) {
					return value.substring(1, value.length() - 1);
				}
			}
		}

		return value;
	}

	public Boolean isNull() {
		return this.match == null;
	}

}
