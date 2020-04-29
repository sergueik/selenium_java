package example;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

// based on: http://www.java2s.com/Code/Java/Development-Class/ArepresentationofthecommandlineargumentspassedtoaJavaclassmainStringmethod.htm
// see also: 
// https://github.com/freehep/freehep-argv/blob/master/src/main/java/org/freehep/util/argv/ArgumentParser.java

public class CommandLineParser {

	private boolean debug = false;

	public boolean isDebug() {
		return debug;
	}

	public void setDebug(boolean debug) {
		this.debug = debug;
	}

	// pass-through
	private String[] arguments = null;

	public String[] getArguments() {
		return arguments;
	}

	private Map<String, String> flags = new HashMap<>();

	//
	// the flag values that are expected to be followed with a value
	// that allows the application to process the flag.
	//
	private Set<String> flagsWithValues = new HashSet<>();

	public Set<String> getFlags() {
		Set<String> result = flags.keySet();
		return result;
	}

	public String getFlagValue(String flagName) {
		return flags.get(flagName);
	}

	public int getNumberOfArguments() {
		return arguments.length;
	}

	public int getNumberOfFlags() {
		return flags.size();
	}

	public boolean hasFlag(String flagName) {
		return flags.containsKey(flagName);
	}

	// contains no constructor nor logic to discover unknown flags
	public void parse(String[] args) {
		List<String> regularArgs = new ArrayList<>();

		for (int n = 0; n < args.length; ++n) {
			if (args[n].charAt(0) == '-') {
				String name = args[n].replaceFirst("-", "");
				String value = null;
				// remove the dash
				if (debug) {
					System.err.println("Examine: " + name);
				}
				if (flagsWithValues.contains(name) && n < args.length - 1) {
					value = args[++n];
					if (debug) {
						System.err.println("Collect value for: " + name + " = " + value);
					}
				} else {
					if (debug) {
						System.err.println("Ignore the value for " + name);
					}
				}

				flags.put(name, value);
			}

			else
				regularArgs.add(args[n]);
		}

		arguments = regularArgs.toArray(new String[regularArgs.size()]);
	}

	public void saveFlagValue(String flagName) {
		flagsWithValues.add(flagName);
	}
	// Example data:
	// -argument "{count:0, type:navigate, size:100, flag:true}"
	// NOTE: not using org.json to reduce size

	private static final String keyValueSeparator = ":";
	private static final String entrySeparator = ",";

	public Map<String, String> extractExtraArgs(String argument) {

		final Map<String, String> extraArgData = new HashMap<>();
		argument = argument.substring(1, argument.length() - 1);
		final String[] pairs = argument.split(entrySeparator);

		for (String pair : pairs) {
			String[] values = pair.split(keyValueSeparator);

			if (values[1].trim().toLowerCase().substring(0, 1).compareTo("{") != 0) {
				if (debug) {
					System.err.println("Collecting: " + pair);
				}
				extraArgData.put(values[0].trim(), values[1].trim());
			} else {
				if (debug) {
					System.err.println("Ignoring: " + pair);
				}
			}
		}
		return extraArgData;
	}

}
