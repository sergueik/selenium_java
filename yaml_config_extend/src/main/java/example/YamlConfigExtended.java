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
import com.github.jsixface.YamlConfig;

public class YamlConfigExtended {

	private YamlConfig yamlConfig;
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

	// NOTE: cannot create class YamlConfigExtended extends YamlConfig

	// The YamlConfig no-argument constructor is private and is not visible to the
	// subclass, leading to a compile-time error
	// Implicit super constructor is not visible. Must explicitly invoke another
	// constructor
	private YamlConfigExtended() {

	}

	public static YamlConfigExtended load(Reader reader) {
		YamlConfigExtended instance = new YamlConfigExtended();
		Yaml yml = new Yaml();
		instance.content = yml.load(reader);
		instance.yamlConfig = YamlConfig.load(reader);
		return instance;
	}

	public void dump() {
		Yaml yml = new Yaml();
		System.err.println("dump: " + yml.dump(this.content));
	}

	public static YamlConfigExtended load(InputStream in) {
		YamlConfigExtended instance = new YamlConfigExtended();
		Yaml yml = new Yaml();
		instance.content = yml.load(in);
		// no suitable method found for
		// load(org.yaml.snakeyaml.Yaml,java.io.InputStream)
		// for release 1.0.1
		instance.yamlConfig = YamlConfig.load(in);
		return instance;
	}

	public String getString(String key) {
		Object foundNode = getNode(key, content);
		if (foundNode != null && !(foundNode instanceof Collection)) {
			if (debug)
				System.err.println(
						String.format("getString found String %s", foundNode.toString()));
		} else {
			if (debug)
				System.err.println("getString found null or collection");
		}
		Object value = this.yamlConfig.getString(key);
		if (debug)
			System.err.println("super.getString found " + value);
		return value != null ? value.toString() : null;
	}

	public Integer getInt(String key) {
		Object foundNode = getNode(key, content);
		if (foundNode instanceof Integer) {
			if (debug)
				System.err.println(
						String.format("getInt found integer %d", (Integer) foundNode));
		} else {
			if (debug)
				System.err.println("getInt found null");
		}
		Object value = this.yamlConfig.getInt(key);
		if (debug)
			System.err.println("super.getInt found " + value);
		return (Integer) value;
	}

	public Boolean getBoolean(String key) {
		Object foundNode = getNode(key, content);
		return Boolean.parseBoolean(foundNode.toString());
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

	// NOTE: have to duplicate
	// The method decompose(String) from the type YamlConfig is not visible
	private String[] decompose(String key) {
		return key.split("\\.");
	}
}
