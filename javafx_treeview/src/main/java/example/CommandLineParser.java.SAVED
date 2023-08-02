package example;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

// origin:
// 50b3627fa1204747ccd871eac876ecdab80d0da9
// https://github.com/sergueik/selenium_java/blob/master/xslt-example/src/main/java/example/CommandLineParser.java    
// based on: http://www.java2s.com/Code/Java/Development-Class/ArepresentationofthecommandlineargumentspassedtoaJavaclassmainStringmethod.htm
// see also: 
// https://github.com/freehep/freehep-argv/blob/master/src/main/java/org/freehep/util/argv/ArgumentParser.java

public class CommandLineParser {
	// non-flag values
	private String[] arguments = null;

	public String[] getArguments() {
		return arguments;
	}

	private Map<String, String> _flags = new HashMap<>();

	//
	// the flag values that are expected to be followed with a value
	// that allows the application to process the flag.
	//
	private Set<String> _flagsWithValues = new HashSet<>();

	public Set<String> getFlags() {
		Set<String> result = _flags.keySet();
		return result;
	}

	public String getFlagValue(String flagName) {
		return (String) _flags.get(flagName);
	}

	public int getNumberOfArguments() {
		return arguments.length;
	}

	public int getNumberOfFlags() {
		return _flags.size();
	}

	public boolean hasFlag(String flagName) {
		return _flags.containsKey(flagName);
	}

	// contains no constructor nor logic to discover unknown flags
	public void parse(String[] args) {
		List<String> regularArgs = new ArrayList<>();

		for (int n = 0; n < args.length; ++n) {
			if (args[n].charAt(0) == '-') {
				String name = args[n].replaceFirst("-", "");
				String value = null;
				// remove the dash

				System.err.println("Examine: " + name);
				if (_flagsWithValues.contains(name) && n < args.length - 1) {
					value = args[++n];
					System.err.println("Collect value for: " + name + " = " + value);
				} else {

					System.err.println("Ignore the value for " + name);

				}

				_flags.put(name, value);
			}

			else
				regularArgs.add(args[n]);
		}

		arguments = (String[]) regularArgs.toArray(new String[regularArgs.size()]);
	}

	public void saveFlagValue(String flagName) {
		_flagsWithValues.add(flagName);
	}
}
