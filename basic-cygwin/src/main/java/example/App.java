package example;
/**
 * Copyright 2021,2022,2023 Serguei Kouzmine
 */

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.MissingArgumentException;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

public class App {

	protected static String osName = getOSName();
	private static String cygwinHome = System.getenv().containsKey("CYGWIN_HOME")
			? System.getenv("CYGWIN_HOME") : "c:\\cygwin";
	private static boolean useQuoteArg = false;
	private static boolean useBash = false;
	private static boolean debug = false;
	private final static Options options = new Options();
	private static CommandLineParser commandLineparser = new DefaultParser();
	private static CommandLine commandLine = null;

	public static void main(String args[]) throws ParseException {
		options.addOption("h", "help", false, "Help");
		options.addOption("d", "debug", false, "Debug");
		options.addOption("q", "quote", false,
				"Enclose entry names in double quotes");
		options.addOption("b", "bash", false, "Use bash to run command ");
		options.addOption("c", "command", true, "Command to run");
		options.addOption("a", "arguments", true, "Command Arguments");
		try {
			commandLine = commandLineparser.parse(options, args);
		} catch (MissingArgumentException e) {
			System.err.println("Aborting after exception " + e.toString());
			return;
		}
		if (commandLine.hasOption("h")) {
			help();
		}
		if (commandLine.hasOption("d")) {
			debug = true;
			System.err.println(
					"command: " + commandLine.getParsedOptionValue("command") + "\n"
							+ "arguments: " + commandLine.getParsedOptionValue("arguments"));
			System.err.println(String.format("args: %s", commandLine.getArgList()));
			System.err.println("All optons: ");
			Arrays.asList(commandLine.getOptions()).stream().map(o -> o.getValue())
					.forEach(System.err::println);

			// System.err.println("optons: " +
			// Arrays.print(commandLine.getOptions()));
		}
		if (commandLine.hasOption("quote")) {
			useQuoteArg = true;
		}
		if (commandLine.hasOption("bash")) {
			useBash = true;
		}
		if (commandLine.hasOption("inline")) {
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
		} else {
			// generic command
			runProcess(String.format("%s %s", command, arguments), null);
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
				? String.format("%s\\bin\\bash.exe -c \"%s\" %s", cygwinHome,
						String.format("/bin/ls %s $0 $1 $2 $3 $4 $5 $6 $7 $8 $9", quoteArg),
						String.join(" ", dirs))
				: String.format("ls %s %s", quoteArg, String.join(" ", dirs));
		return command;
	}

	private static String buildLscommand(List<String> dirs) {
		final String quoteArg = (useQuoteArg) ? "-Q" : "";
		if (dirs.isEmpty()) {
			return null;
		}
		String command = osName.toLowerCase().startsWith("windows")
				? String.format("%s\\bin\\ls.exe %s %s", cygwinHome, quoteArg,
						String.join(" ", dirs))
				: String.format("ls %s %s", quoteArg, String.join(" ", dirs));
		return command;
	}

	public static void runProcessls(List<String> dirs) {
		final String command = useBash ? buildLsBashcommand(dirs)
				: buildLscommand(dirs);
		runProcess(command, null);
	}

	// https://www.javaworld.com/article/2071275/core-java/when-runtime-exec---won-t.html?page=2
	public static void runProcess(String command, String payload) {

		try {
			Runtime runtime = Runtime.getRuntime();

			Process process = null;

			if (payload != null) {
				// see also:
				// execute the specified string command in a separate process
				// with the specified environment
				// http://docs.oracle.com/javase/6/docs/api/java/lang/Runtime.html#exec%28java.lang.String,%20java.lang.String%5B%5D%29
				String[] envp = { String.format("CONTENT_LENGTH=%d", payload.length()),
						"REQUEST_METHOD=POST" };
				System.err.println("Running with environment: " + Arrays.asList(envp));
				process = runtime.exec(command, envp);
				BufferedWriter bufferedWriter = new BufferedWriter(
						new OutputStreamWriter(process.getOutputStream()));
				System.err.println("Passing the payload: " + payload);
				// Passing the payload: %7B%22foo%22%3A+%22bar%22%7D=
				bufferedWriter.write(payload);
				bufferedWriter.newLine();
				bufferedWriter.flush();
				bufferedWriter.close();
			} else {
				process = runtime.exec(command);
			}
			// process.redirectErrorStream( true);

			BufferedReader stdoutBufferedReader = new BufferedReader(
					new InputStreamReader(process.getInputStream()));

			BufferedReader stderrBufferedReader = new BufferedReader(
					new InputStreamReader(process.getErrorStream()));
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
			// ignore exit code 128 on Windows: the process "<browser driver>" not
			// found
			if (exitCode != 0 && (exitCode ^ 128) != 0) {
				System.err.println("Process exit code: " + exitCode);
				if (processOutput.length() > 0) {
					System.err.println("<OUTPUT>" + ((useQuoteArg)
							? fixQuotedOutput(processOutput.toString()) : processOutput)
							+ "</OUTPUT>");

				}
				if (processError.length() > 0) {
					System.err.println("<ERROR>" + processError + "</ERROR>");
				}
			} else {
				if (processOutput.length() > 0) {
					System.err.println("<OUTPUT>" + ((useQuoteArg)
							? fixQuotedOutput(processOutput.toString()) : processOutput)
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
		Matcher m = p.matcher(data.replaceAll("\0", "\r\n").replaceAll("\n", "\r\n")
				.replaceAll("\"\"", "\"\r\n\""));
		if (m.find()) {
			value = m.replaceAll("$1");
		}
		return value;
	}

	public static void help() {
		System.exit(1);
	}

}
