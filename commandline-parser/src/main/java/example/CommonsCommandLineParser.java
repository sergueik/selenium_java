package example;

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
	private CommandLineParser commandLineparser = new DefaultParser();;
	private CommandLine commandLine = null;
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

	// https://www.programcreek.com/java-api-examples/org.apache.commons.cli.CommandLineParser
	public void run(String... args) {

		try {
			commandLine = commandLineparser.parse(options, args);
			if (commandLine.hasOption("h")) {
				help();
			}
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
