package example;

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
		String command = "$info = 'test'; write-output $info | out-file -LiteralPath 'C:\\TEMP\\a123.txt' -append; "
				+ String.format("start-sleep -seconds %d", timeout);
		command = "$info = start-process 'java.exe' -argumentlist '-jar','c:\\developer\\sergueik\\springboot_study\\basic-rrd4j\\target\\rrd4j-3.9-SNAPSHOT-inspector.jar' -passthru; write-output ('Pid={0}' -f $info.id) | out-file -LiteralPath 'C:\\TEMP\\a123.txt' -encoding ascii; "
				+ String.format("start-sleep -seconds %d", timeout);
		launchPowershell1(command);
	}

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

}
