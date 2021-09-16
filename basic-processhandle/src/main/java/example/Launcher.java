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

	public static void launch() {
		ProcessBuilder processBuilder = new ProcessBuilder("cmd.exe", "/c", "start",
				"/low", "java.exe", "-jar",
				"c:\\developer\\sergueik\\springboot_study\\basic-rrd4j\\target\\rrd4j-3.9-SNAPSHOT-inspector.jar");
		Map<String, String> env = processBuilder.environment();
		env.put("PATH",
				String.format("%s;%s", env.get("PATH"), "c:\\java\\jdk1.8.0_101\\bin"));
		try {
			Process process = processBuilder.start();
		} catch (IOException e) {
			logger.info("Exception (ignored): " + e.toString());
		}
	}

	public static void launch2() {

		ProcessBuilder processBuilder = new ProcessBuilder(Arrays.asList(
				new String[] { "cmd.exe", "/c", "start", "/low", "java.exe", "-jar",
						"c:\\developer\\sergueik\\springboot_study\\basic-rrd4j\\target\\rrd4j-3.9-SNAPSHOT-inspector.jar" }));
		Map<String, String> env = processBuilder.environment();
		env.put("PATH",
				String.format("%s;%s", env.get("PATH"), "c:\\java\\jdk1.8.0_101\\bin"));
		try {
			Process process = processBuilder.start();
		} catch (IOException e) {
			logger.info("Exception (ignored): " + e.toString());
		}
	}

	public static void launch3() {
		List<String> arguments = Arrays.asList(new String[] { "cmd.exe", "/c",
				"start", "/low", "java.exe", "-jar",
				"c:\\developer\\sergueik\\springboot_study\\basic-rrd4j\\target\\rrd4j-3.9-SNAPSHOT-inspector.jar" });
		ProcessBuilder processBuilder = new ProcessBuilder(arguments);
		Map<String, String> env = processBuilder.environment();
		env.put("PATH",
				String.format("%s;%s", env.get("PATH"), "c:\\java\\jdk1.8.0_101\\bin"));
		try {
			Process process = processBuilder.start();
		} catch (IOException e) {
			logger.info("Exception (ignored): " + e.toString());
		}
	}

	public static void launch5() {
		String command = "java.exe -jar c:\\developer\\sergueik\\springboot_study\\basic-rrd4j\\target\\rrd4j-3.9-SNAPSHOT-inspector.jar";
		// String command = "java.exe -jar
		// c:\\developer\\sergueik\\springboot_study\\basic-rrd4j\\target\\rrd4j-3.9-SNAPSHOT-inspector.jar";
		launch5(command);
	}

	// https://howtodoinjava.com/java/collections/arraylist/merge-arraylists/
	public static void launch5(final String command) {

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
		ProcessBuilder processBuilder = new ProcessBuilder(arguments);
		Map<String, String> env = processBuilder.environment();
		env.put("PATH",
				String.format("%s;%s", env.get("PATH"), "c:\\java\\jdk1.8.0_101\\bin"));
		try {
			Process process = processBuilder.start();
		} catch (IOException e) {
			logger.info("Exception (ignored): " + e.toString());
		}
	}

	public static void launch4() {
		String command = "java.exe -jar c:\\developer\\sergueik\\springboot_study\\basic-rrd4j\\target\\rrd4j-3.9-SNAPSHOT-inspector.jar";
		// String command = "java.exe -jar
		// c:\\developer\\sergueik\\springboot_study\\basic-rrd4j\\target\\rrd4j-3.9-SNAPSHOT-inspector.jar";
		launch4(command);
	}

	// https://www.baeldung.com/java-lang-processbuilder-api
	public static void launch4(String command) {
		List<String> commandArguments = new ArrayList<>(
				Arrays.asList(command.split("\\s+")));
		List<String> arguments = new ArrayList<>(
				Arrays.asList(new String[] { "cmd.exe", "/c", "start", "/low" }));
		try {
			arguments.addAll(commandArguments);
		} catch (UnsupportedOperationException e) {
			// ignore
			logger.info("Exception (ignored): " + e.toString());
		}

		arguments = new ArrayList<>(
				Arrays.asList(new String[] { "cmd.exe", "/c", "start", "/low" }));
		for (String arg : commandArguments) {
			try {
				arguments.add(arg);
			} catch (UnsupportedOperationException e) {
				// ignore
				logger.info("Exception (ignored): " + e.toString());
			}
		}

		logger.info("Launching: " + arguments);
		ProcessBuilder processBuilder = new ProcessBuilder(arguments);
		Map<String, String> env = processBuilder.environment();
		env.put("PATH",
				String.format("%s;%s", env.get("PATH"), "c:\\java\\jdk1.8.0_101\\bin"));
		try {
			Process process = processBuilder.start();
		} catch (IOException e) {
			logger.info("Exception (ignored): " + e.toString());
		}
	}
	// $info = start-process "java.exe" -argumentlist
	// "-jar","c:\developer\sergueik\springboot_study\basic-rrd4j\target\rrd4j-3.9-SNAPSHOT-inspector.jar"
	// -passthru; write-output $info.id
}
