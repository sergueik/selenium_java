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
import example.CommandLineParser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class App {

	protected static String osName = getOSName();
	public static final int INVALID_OPTION = 42;
	private Map<String, String> flags = new HashMap<>();
	private static boolean useQuoteArg = false;

	private static boolean debug = false;
	private static CommandLineParser commandLineParser;

	public static void main(String args[]) {
		commandLineParser = new CommandLineParser();

		commandLineParser.saveFlagValue("quote");
		commandLineParser.saveFlagValue("command");
		commandLineParser.saveFlagValue("arguments");
		commandLineParser.parse(args);

		if (commandLineParser.hasFlag("debug")) {
			debug = true;
		}
		if (commandLineParser.hasFlag("quote")) {
			useQuoteArg = true;
		}
		String arguments = commandLineParser.getFlagValue("arguments");
		if (arguments == null) {
			System.err.println("Missing required argument: arguments");
			return;
		}
		String command = commandLineParser.getFlagValue("command");
		if (command == null) {
			System.err.println("Missing required argument: command");
			return;
		}
		if (command.equalsIgnoreCase("ls")) {

			String[] argumentsArray = arguments.split(",");
			runProcessls(Arrays.asList(argumentsArray));
		}
		if (debug) {
			System.err.println("Done: " + command + " " + arguments);
		}

	}

	// https://www.javaworld.com/article/2071275/core-java/when-runtime-exec---won-t.html?page=2
	public static void runProcessls(List<String> dirs) {
		err.println("Listing the dirs: " + dirs);
		final String quoteArg = (useQuoteArg) ? "-Q" : "";
		if (dirs.isEmpty()) {
			return;
		}
		String command = (osName.toLowerCase().startsWith("windows"))
				? String.format("c:\\cygwin\\bin\\bash.exe -c  \"%s\" %s", "/bin/ls %s $0 $1 $2 $3 $4 $5 $6 $7 $8 $9",
						quoteArg, String.join(" ", dirs))
				: String.format("ls %s %s", quoteArg, String.join(" ", dirs));
		err.println("Running the command: " + command);
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
				err.println("Process exit code: " + exitCode);
				if (processOutput.length() > 0) {
					err.println("<OUTPUT>" + ((useQuoteArg) ? fixQuotedOutput(processOutput.toString()) : processOutput)
							+ "</OUTPUT>");
				}
				if (processError.length() > 0) {
					err.println("<ERROR>" + processError + "</ERROR>");
				}
			} else {
				if (processOutput.length() > 0) {
					err.println("<OUTPUT>" + ((useQuoteArg) ? fixQuotedOutput(processOutput.toString()) : processOutput)
							+ "</OUTPUT>");
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

	private static String fixQuotedOutput(String data) {
		String value = data;

		Pattern p = Pattern.compile("\"([^\"]+)\"");
		Matcher m = p.matcher(data.replaceAll("\0", "\r\n").replaceAll("\n", "\r\n").replaceAll("\"\"", "\"\r\n\""));
		if (m.find()) {
			value = m.replaceAll("$1");
		}
		return value;
	}
}
