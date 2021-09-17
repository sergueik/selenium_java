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
	private static Launcher instance = new Launcher();
	private boolean debug = false;

	public void setDebug(boolean value) {
		debug = value;
	}

	private Launcher() {
	}

	public static Launcher getInstance() {
		return instance;
	}

	private Logger logger = LogManager.getLogger(Launcher.class.getName());
	private final String javaPath = "c:\\java\\jdk1.8.0_101\\bin";
	private final String pidfilePath = "C:\\TEMP\\a123.txt";
	private final String demoCommand = "java.exe -cp target\\example.processhandle.jar;target\\lib\\* example.Dialog";

	public String getDemoCommand() {
		return demoCommand;
	}

	// https://www.baeldung.com/java-lang-processbuilder-api
	public void launch(List<String> arguments) {
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

	public void launchPowershell1() {
		launchPowershell1(getDemoCommand());
	}

	// https://stackoverflow.com/questions/7486717/finding-parent-process-id-on-windows
	public void launchPowershell1(final String javaCommand) {
		final int timeout = 10;
		// dummy

		final String commandTemplate = "$info = start-process %s -passthru; $info.PriorityClass=System.Diagnostics.ProcessPriorityClass]::BelowNormal; write-output ('Pid={0}' -f $info.id) | out-file -LiteralPath 'C:\\TEMP\\a123.txt' -encoding ascii; ";
		String command = String.format(commandTemplate, buildCommand(javaCommand));
		List<String> commandArguments = new ArrayList<>(
				Arrays.asList(command.split("\\s+")));
		List<String> arguments = new ArrayList<>(Arrays.asList(new String[] {
				"powershell.exe", "/noprofile", "/executionpolicy", "bypass" }));
		arguments.add("\"&{");
		arguments.addAll(commandArguments);
		arguments.add("}\"");
		launch(arguments);
	}

	// https://stackoverflow.com/questions/19250927/in-powershell-set-affinity-in-start-process
	public String buildCommand(final String command) {
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
		}
		contents.append(String.join(", ", newCommandArguments));
		// note trailing space after each arg
		newCommand = contents.toString();
		logger.info(String.format("New Command: \"%s\"", newCommand));
		return newCommand;
	}

	public int getPid() {
		final int pid = Integer.parseInt(readFile(pidfilePath));
		return pid;
	}

	private String readFile(final String filePath) {
		String result = null;
		StringBuffer contents = new StringBuffer();
		BufferedReader reader = null;
		try {
			File file = new File(filePath);
			reader = new BufferedReader(new FileReader(file));
			String text = null;

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

	public void launchCmd1() {
		launchCmd1(getDemoCommand());
	}

	public void launchCmd1(String command) {
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

	public void launchCmd2() {
		launchCmd2(getDemoCommand());
	}

	// https://howtodoinjava.com/java/collections/arraylist/merge-arraylists/
	public void launchCmd2(final String command) {

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
		launch(arguments);
	}

}
