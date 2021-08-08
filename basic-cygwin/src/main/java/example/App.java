package example;

import static java.lang.System.err;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

public class App {

	protected static String osName = getOSName();
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

	public App() {
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

	public static void main(String args[]) {
		runProcessls(Arrays.asList(args));
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

	// https://www.javaworld.com/article/2071275/core-java/when-runtime-exec---won-t.html?page=2
	public static void runProcessls(List<String> dirs) {
		err.println("Listing the dirs: " + dirs);

		if (dirs.isEmpty()) {
			return;
		}
		String command = (osName.toLowerCase().startsWith("windows"))
				? String.format("c:\\cygwin\\bin\\bash.exe -c  \"%s\" %s",
						"/bin/ls -Q $0 $1 $2 $3 $4 $5 $6 $7 $8 $9", String.join(" ", dirs))
				: String.format("ls %s", String.join(" ", dirs));
		err.println("Running the command: " + command);
		try {
			Runtime runtime = Runtime.getRuntime();
			Process process = runtime.exec(command);
			// process.redirectErrorStream( true);

			BufferedReader stdoutBufferedReader = new BufferedReader(
					new InputStreamReader(process.getInputStream()));

			BufferedReader stderrBufferedReader = new BufferedReader(
					new InputStreamReader(process.getErrorStream()));
			String line = null;

			StringBuffer processOutput = new StringBuffer();
			while ((line = stdoutBufferedReader.readLine()) != null) {
				processOutput.append(line);
			}
			StringBuffer processError = new StringBuffer();
			while ((line = stderrBufferedReader.readLine()) != null) {
				processError.append(line);
			}
			int exitCode = process.waitFor();
			// ignore exit code 128: the process "<browser driver>" not found.
			if (exitCode != 0 && (exitCode ^ 128) != 0) {
				err.println("Process exit code: " + exitCode);
				if (processOutput.length() > 0) {
					err.println(
							"<OUTPUT>" + fixOutput(processOutput.toString()) + "</OUTPUT>");
				}
				if (processError.length() > 0) {
					err.println("<ERROR>" + processError.toString() + "</ERROR>");
				}
			} else {
				if (processOutput.length() > 0) {
					err.println(
							"<OUTPUT>" + fixOutput(processOutput.toString()) + "</OUTPUT>");
				}
			}
		} catch (Exception e) {
			err.println("Exception (ignored): " + e.getMessage());
		}
	}

	public static String getOSName() {
		if (osName == null) {
			osName = System.getProperty("os.name").toLowerCase();
			if (osName.startsWith("windows")) {
				osName = "windows";
			}
		}
		return osName;
	}

	private static String fixOutput(String data) {
		String value = null;
		Pattern p = Pattern.compile("\"([^\"]+)\"");
		Matcher m = p.matcher(data.replaceAll("\0", "\r\n").replaceAll("\n", "\r\n")
				.replaceAll("\"\"", "\"\r\n\""));
		if (m.find()) {
			value = m.replaceAll("$1");
			// if (debug) {
			// System.err.println("Fixed: " + value);
			// }
		}
		return value;
	}
}
