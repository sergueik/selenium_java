package example;

import org.yaml.snakeyaml.Yaml;

import java.io.InputStream;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class YamlConfig {
	private Object content;
	private static boolean debug = false;

	private final static Pattern arrayKeyPattern = Pattern
			.compile("^([a-zA-Z][a-zA-Z0-9_.]+)\\[([0-9]+)]$");

	public static boolean isDebug() {
		return debug;
	}

	public static void setDebug(boolean value) {
		debug = value;
	}

	private YamlConfig() {
	}

	/**
	 * Create configuration from Reader
	 * @param reader the reader to read config from
	 * @return YamlConfig instance
	 */
	public static YamlConfig load(Reader reader) {
		YamlConfig instance = new YamlConfig();
		Yaml yml = new Yaml();
		instance.content = yml.load(reader);

		return instance;
	}

	/**
	 * Create configuration from input stream
	 * @param in the Input stream to read from
	 * @return YamlConfig instance
	 */
	public static YamlConfig load(InputStream in) {
		YamlConfig instance = new YamlConfig();
		Yaml yml = new Yaml();
		instance.content = yml.load(in);
		return instance;
	}

	/**
	 * Gets the String value for the specified key from the config.
	 *
	 * @param key Key in dotted notation like <code>first.second[2].third</code>
	 * @return  The String value of property. <br ><code>null</code> if the key is not present
	 *          or not a leaf node. <code>Boolean</code> or <code>Integer</code> or other format
	 *          are converted to String.
	 */
	public String getString(String key) {
		Object foundNode = getNode(key, content);
		if (foundNode != null && !(foundNode instanceof Collection)) {
			return foundNode.toString();
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public ArrayList<Object> getList(String key) {
		Object foundNode = getNode(key, this.content);
		if (foundNode != null) {
			if (debug)
				System.err.println(String.format("getObject inspecting \"%s\"",
						new Yaml().dump(foundNode)));
			if (foundNode instanceof ArrayList) {
				if (debug)
					System.err.println("Result type is right");
				return (ArrayList<Object>) foundNode;
			} else {
				if (debug)
					System.err.println("Unexpected result type");
			}
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public Map<String, Object> getMap(String key) {
		Object foundNode = getNode(key, this.content);
		if (foundNode != null) {
			if (debug)
				System.err.println(String.format("getObject inspecting \"%s\"",
						new Yaml().dump(foundNode)));
			if (foundNode instanceof Map) {
				if (debug)
					System.err.println("Result type is right");
				return (Map<String, Object>) foundNode;
			} else {
				if (debug)
					System.err.println("Unexpected result type");
			}
		}
		return null;
	}

	/**
	 * Gets the Integer value for the specified key from the config.
	 *
	 * @param key Key in dotted notation like <code>first.second[2].third</code>
	 * @return  The Integer value of property. <br ><code>null</code> if the key is not present
	 *          or not a leaf node.
	 */
	public Integer getInt(String key) {
		Object foundNode = getNode(key, content);
		if (foundNode instanceof Integer) {
			return (Integer) foundNode;
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	private Object getNode(String key, Object foundNode) {
		String[] parts = decompose(key);
		if (debug)
			System.err.println("Got key " + key + " parts: " + Arrays.asList(parts));
		for (String part : parts) {
			int arrayNum = -1;
			Matcher matcher = arrayKeyPattern.matcher(part);
			if (matcher.matches()) {
				part = matcher.group(1);
				arrayNum = Integer.parseInt(matcher.group(2));
			}
			if (foundNode instanceof Map) {
				if (debug)
					System.err.println(String.format("Looking for %s in %s", part,
							new Yaml().dump(foundNode)));
				if (((Map<String, Object>) foundNode).containsKey(part)) {
					foundNode = ((Map<String, Object>) foundNode).get(part);
					if (arrayNum >= 0) {
						if (foundNode instanceof ArrayList
								&& ((ArrayList) foundNode).size() > arrayNum) {
							foundNode = ((ArrayList) foundNode).get(arrayNum);
						} else
							return null;
					}
				} else if (foundNode instanceof ArrayList) {
					return (ArrayList<Object>) foundNode;
				} else
					return null;
			}
		}
		// NOTE: ignore phantom newlines and | or |- added by YAML dumper
		if (debug)

			System.err
					.println(String.format("Found: \"%s\"", new Yaml().dump(foundNode)));
		return foundNode;
	}

	private String[] decompose(String key) {
		return key.split("\\.");
	}
}
