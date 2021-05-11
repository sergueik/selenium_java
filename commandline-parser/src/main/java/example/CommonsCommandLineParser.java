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

	public CommonsCommandLineParser() {
		options.addOption("h", "help", false, "Help");
		options.addOption(Option.builder("d").longOpt("data").required(true)
				.desc("Data argument").build());
	}

	// https://www.programcreek.com/java-api-examples/org.apache.commons.cli.CommandLineParser
	public void run(String... args) {

		CommandLineParser commandLineparser = new DefaultParser();

		CommandLine commandLine = null;
		try {
			commandLine = commandLineparser.parse(options, args);
			if (commandLine.hasOption("help")) {
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
