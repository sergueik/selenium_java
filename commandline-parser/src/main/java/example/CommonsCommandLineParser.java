package example;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

public class CommonsCommandLineParser {

	public static final int INVALID_OPTION = 42;
	private final Options options = new Options();
	private CommandLineParser commandLineparser = new DefaultParser();
	private CommandLine commandLine = null;
	private Map<String, String> flags = new HashMap<>();

	private HelpFormatter helpFormatter = new HelpFormatter();
	private boolean debug = false;

	public void saveFlagValue(String flagName, String longFlagNAme) {
		options.addRequiredOption(flagName, "action", true,
				String.format("%s option", longFlagNAme));
	}

	public CommonsCommandLineParser() {
		options.addOption("h", "help", false, "Help");
		/*
		options.addOption(Option.builder("d").longOpt("data").required(true)
				.hasArg(true).desc("Data option").build());
		options.addRequiredOption("a", "action", true, "Action option");
		*/
	}

	public boolean isDebug() {
		return debug;
	}

	public void setDebug(boolean value) {
		debug = value;
	}

	public Set<String> getFlags() {
		Set<String> result = flags.keySet();
		return result;
	}

	public String getFlagValue(String name) {
		return flags.get(name);
	}

	// https://www.programcreek.com/java-api-examples/org.apache.commons.cli.CommandLineParser
	public void run(String... args) {

		try {
			commandLine = commandLineparser.parse(options, args);
			if (commandLine.hasOption("h")) {
				help();
			}

			Arrays.asList(commandLine.getOptions()).stream()
					.forEach(o -> flags.put(o.getArgName(), o.getValue()));
			flags.keySet().stream().forEach(
					o -> System.err.println(String.format("%s", o, flags.get(o))));
		} catch (ParseException e) {
			System.err.println("Exception parsing command line: " + e.toString());

			new HelpFormatter().printHelp(this.getClass().getSimpleName() + " [args]",
					options);
			System.exit(INVALID_OPTION);
		}
		System.exit(0);
	}

	public void help() {
		System.exit(1);
	}

	public void help(int status) {
		System.exit(status);
	}
}
