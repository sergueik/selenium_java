package example;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class App {

protected static String osName = getOSName();
public static final int INVALID_OPTION = 42;
private Map<String, String> flags = new HashMap<>();
private static boolean useQuoteArg = false;
private static boolean useBash = false;
private static boolean useInline = false;

private static boolean debug = false;
private final static Options options = new Options();
private static CommandLineParser commandLineparser = new DefaultParser();
private static CommandLine commandLine = null;

public static void main(String args[]) throws ParseException {
		options.addOption("h", "help", false, "Help");
		options.addOption("d", "debug", false, "Debug");
		options.addOption("q", "quote", false, "Enclose entry names in double quotes");
		options.addOption("i", "inline", false, "Pass arguments inline");
		options.addOption("b", "bash", false, "Use bash to run command ");
		options.addOption("c", "command", true, "Command to run");
		options.addOption("a", "arguments", true, "Command Arguments");
		commandLine = commandLineparser.parse(options, args);
		if (commandLine.hasOption("h")) {
			help();
		}
		if (commandLine.hasOption("d")) {
			debug = true;
		}
		if (commandLine.hasOption("quote")) {
			useQuoteArg = true;
		}
		if (commandLine.hasOption("bash")) {
			useBash = true;
		}
		if (commandLine.hasOption("inline")) {
			useInline = true;
		}
		String arguments = commandLine.getOptionValue("arguments");
		if (arguments == null) {
			System.err.println("Missing required argument: arguments");
			return;
		}
		// TODO:
		/*
		 * List<String> argumentsFixed = Arrays.asList(arguments.split(",")).stream()
		 * .map(f -> Paths.get(f).toUri().toString()).collect(Collectors.toList());
		 */
		String command = commandLine.getOptionValue("command");
		if (command == null) {
			System.err.println("Missing required argument: command");
			return;
		}

		if (command.equalsIgnoreCase("ls")) {
			if (debug) {
				System.err.println("Listing the dirs: " + arguments);
			}
			String[] argumentsArray = arguments.split(",");
			runProcessls(Arrays.asList(argumentsArray));
		}
		if (debug) {
			System.err.println("Done: " + command + " " + arguments);
		}

	}

	private static String buildLsBashcommand(List<String> dirs) {
		final String quoteArg = (useQuoteArg) ? "-Q" : "";
		if (dirs.isEmpty()) {
			return null;
		}
		String command = osName.toLowerCase().startsWith("windows")
				? String.format("c:\\cygwin\\bin\\bash.exe -c  \"%s\" %s",
						String.format("/bin/ls %s $0 $1 $2 $3 $4 $5 $6 $7 $8 $9", quoteArg), String.join(" ", dirs))
				: String.format("ls %s %s", quoteArg, String.join(" ", dirs));
		return command;
	}

	private static String buildLscommand(List<String> dirs) {
		final String quoteArg = (useQuoteArg) ? "-Q" : "";
		if (dirs.isEmpty()) {
			return null;
		}
		String command = osName.toLowerCase().startsWith("windows")
				? String.format("c:\\cygwin\\bin\\ls.exe %s %s", quoteArg, String.join(" ", dirs))
				: String.format("ls %s %s", quoteArg, String.join(" ", dirs));
		return command;
	}

	// https://www.javaworld.com/article/2071275/core-java/when-runtime-exec---won-t.html?page=2
	public static void runProcessls(List<String> dirs) {
		final String command = useBash ?  buildLsBashcommand(dirs): buildLscommand(dirs);
		System.err.println("Running the command: " + command);
		try {
			Runtime runtime = Runtime.getRuntime();
			Process process = runtime.exec(command);
			// process.redirectErrorStream( true);

			BufferedReader stdoutBufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()));

			BufferedReader stderrBufferedReader = new BufferedReader(new InputStreamReader(process.getErrorStream()));
			String line = null;

			StringBuffer processOutput = new StringBuffer();
			while ((line = stdoutBufferedReader.readLine()) != null) {
				processOutput.append(line);
				processOutput.append("\n");
			}
			StringBuffer processError = new StringBuffer();
			while ((line = stderrBufferedReader.readLine()) != null) {
				processError.append(line);
				processOutput.append("\n");
			}
			int exitCode = process.waitFor();
			// ignore exit code 128 on Windows: the process "<browser driver>" not found.
			if (exitCode != 0 && (exitCode ^ 128) != 0) {
				System.err.println("Process exit code: " + exitCode);
				if (processOutput.length() > 0) {
					System.err.println(
							"<OUTPUT>" + ((useQuoteArg) ? fixQuotedOutput(processOutput.toString()) : processOutput)
									+ "</OUTPUT>");
				}
				if (processError.length() > 0) {
					System.err.println("<ERROR>" + processError + "</ERROR>");
				}
			} else {
				if (processOutput.length() > 0) {
					System.err.println(
							"<OUTPUT>" + ((useQuoteArg) ? fixQuotedOutput(processOutput.toString()) : processOutput)
									+ "</OUTPUT>");
				}
			}
		} catch (Exception e) {
			System.err.println("Exception (ignored): " + e.getMessage());
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

	private static String fixQuotedOutput(String data) {
		String value = data;

		Pattern p = Pattern.compile("\"([^\"]+)\"");
		Matcher m = p.matcher(data.replaceAll("\0", "\r\n").replaceAll("\n", "\r\n").replaceAll("\"\"", "\"\r\n\""));
		if (m.find()) {
			value = m.replaceAll("$1");
		}
		return value;
	}

	public static void help() {
		System.exit(1);
	}

}
