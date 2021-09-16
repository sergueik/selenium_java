package example;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Launcher {
	private static Logger logger = LogManager.getLogger(Launcher.class.getName());
	private final static String javaPath = "c:\\java\\jdk1.8.0_101\\bin";
	private final static String pidfilePath = "C:\\TEMP\\a123.txt";

	public static void launchCmd2() {
		String command = "java.exe -jar c:\\developer\\sergueik\\springboot_study\\basic-rrd4j\\target\\rrd4j-3.9-SNAPSHOT-inspector.jar";
		launchCmd2(command);
	}

	// https://howtodoinjava.com/java/collections/arraylist/merge-arraylists/
	public static void launchCmd2(final String command) {

		List<String> arguments = new ArrayList<>(
				Arrays.asList(command.split("\\s+")));
		try {
			arguments.addAll(0, new ArrayList<>(
					Arrays.asList(new String[] { "cmd.exe", "/c", "start", "/low" })));
		} catch (java.lang.UnsupportedOperationException e) {
			logger.info("Exception (ignored): " + e.toString());
		}
		logger.info("Launching: " + arguments);
		arguments = new ArrayList<>(Arrays.asList(command.split("\\s+")));
		// NOTE: need to reverse
		// https://www.geeksforgeeks.org/collections-reverseorder-java-examples/
		// TODO: prepended in wrong order
		/*
		List<String> extraarguments = new ArrayList<>(
				Arrays.asList(new String[] { "cmd.exe", "/c", "start", "/low" }));
		Collections.sort(extraarguments, Collections.reverseOrder());
		for (String arg : extraarguments) {
			try {
				arguments.add(0, arg);
			} catch (UnsupportedOperationException e) {
				// ignore
				logger.info("Exception (ignored): " + e.toString());
			}
		}
		
		logger.info("Launching: " + arguments);
		
		   
		 */
		launch(arguments);
	}

	// https://www.baeldung.com/java-lang-processbuilder-api
	public static void launch(List<String> arguments) {
		logger.info("Launching: " + arguments);
		ProcessBuilder processBuilder = new ProcessBuilder(arguments);
		Map<String, String> env = processBuilder.environment();
		env.put("PATH", String.format("%s;%s", env.get("PATH"), javaPath));
		try {
			Process process = processBuilder.start();
		} catch (IOException e) {
			logger.info("Exception (ignored): " + e.toString());
		}

	}

	public static void launchPowershell1() {
		final int timeout = 10;
		// dummy
		String command = "$info = 'test'; write-output $info | out-file -LiteralPath 'C:\\TEMP\\a123.txt' -append; "
				+ String.format("start-sleep -seconds %d", timeout);

		command = "$info = start-process -filepath 'java.exe' -argumentlist '-jar','c:\\developer\\sergueik\\springboot_study\\basic-rrd4j\\target\\rrd4j-3.9-SNAPSHOT-inspector.jar' -passthru; $info.PriorityClass=System.Diagnostics.ProcessPriorityClass]::BelowNormal; write-output ('Pid={0}' -f $info.id) | out-file -LiteralPath 'C:\\TEMP\\a123.txt' -encoding ascii; "
				+ String.format("start-sleep -seconds %d", timeout);
		String javaCommand = "java.exe -jar c:\\developer\\sergueik\\springboot_study\\basic-rrd4j\\target\\rrd4j-3.9-SNAPSHOT-inspector.jar";
		final String commandTemplate = "$info = start-process %s -passthru; $info.PriorityClass=System.Diagnostics.ProcessPriorityClass]::BelowNormal; write-output ('Pid={0}' -f $info.id) | out-file -LiteralPath 'C:\\TEMP\\a123.txt' -encoding ascii; ";
		command = String.format(commandTemplate, buildCommand(javaCommand));
		launchPowershell1(command);
	}

	// https://stackoverflow.com/questions/19250927/in-powershell-set-affinity-in-start-process
	public static String buildCommand(final String command) {
		String newCommand = null;
		List<String> commandArguments = new ArrayList<>(
				Arrays.asList(command.replaceAll("\\s+", " ").split("\\s+")));
		if (commandArguments.get(0).contains("java")) {
			commandArguments.remove(0);
		}
		// List<String> newCommandArguments = new ArrayList<>();
		StringBuffer contents = new StringBuffer();
		contents.append("-filepath 'java.exe' -argumentlist ");
		// note trailing space
		List<String> newCommandArguments = new ArrayList<>();
		for (String arg : commandArguments) {
			newCommandArguments.add(String.format("'%s'", arg));
			// contents.append(String.format("'%s'", arg)).append(", ");
			// note trailing space after each arg
		}
		contents.append(String.join(", ", newCommandArguments));
		// wrong: trailing comma
		newCommand = contents.toString();
		newCommand.replaceAll(",\\s*$", " ");
		// remove the trailing comma
		logger.info(String.format("New Command: \"%s\"", newCommand));
		return newCommand;
	}

	// https://stackoverflow.com/questions/7486717/finding-parent-process-id-on-windows
	public static void launchPowershell1(String command) {

		List<String> commandArguments = new ArrayList<>(
				Arrays.asList(command.split("\\s+")));
		List<String> arguments = new ArrayList<>(
				Arrays.asList(new String[] { "powershell.exe", "/noprofile",
						"/executionpolicy", "bypass", "\"&{" }));

		arguments.addAll(commandArguments);
		arguments.add("}\"");
		launch(arguments);
	}

	public static void launchCmd1() {
		String command = "java.exe -jar c:\\developer\\sergueik\\springboot_study\\basic-rrd4j\\target\\rrd4j-3.9-SNAPSHOT-inspector.jar";
		launchCmd1(command);
	}

	public static void launchCmd1(String command) {
		List<String> commandArguments = new ArrayList<>(
				Arrays.asList(command.split("\\s+")));
		List<String> arguments = new ArrayList<>(
				Arrays.asList(new String[] { "cmd.exe", "/c", "start", "/low" }));
		try {
			arguments.addAll(commandArguments);
		} catch (UnsupportedOperationException e) {
			logger.info("Exception (ignored): " + e.toString());
		}
		launch(arguments);
	}

	public static int getPid() {

		final int pid = Integer.parseInt(readFile(pidfilePath));
		return pid;
	}

	public static String readFile(final String filePath) {
		String result = null;
		StringBuffer contents = new StringBuffer();
		BufferedReader reader = null;
		try {
			File file = new File(filePath);
			reader = new BufferedReader(new FileReader(file));
			String text = null;

			// repeat until all lines is read
			while ((text = reader.readLine()) != null) {
				contents.append(text).append(System.getProperty("line.separator"));
			}
			reader.close();
			result = contents.toString().replaceAll("\r?\n", "").replaceAll("Pid=",
					"");
			logger.info(String.format("%s data:%s", filePath, result));

		} catch (Exception e) {
			logger.info("Exception (ignored): " + e.toString());
		}
		return result;
	}

}
