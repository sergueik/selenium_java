package example;

/**
 * Copyright 2021,2022,2024 Serguei Kouzmine
 */

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Launcher {

	private boolean debug = false;

	public boolean isDebug() {
		return debug;
	}

	public void setDebug(boolean value) {
		debug = value;
	}

	private static Launcher instance = new Launcher();

	private Launcher() {
	}

	public static Launcher getInstance() {
		return instance;
	}

	private Logger logger = LogManager.getLogger(Launcher.class.getName());
	private String javaPath = System.getenv("JAVA_HOME") + System.getProperty("file.separator") + "bin";

	public String getJavaPath() {
		return javaPath;
	}

	public void setJavaPath(String data) {
		javaPath = data;
	}

	private String pidfilePath = "C:\\TEMP\\pidfile.txt";
	private String debugLogFilePath = "C:\\TEMP\\debug.log";

	public String getPidfilePath() {
		return pidfilePath;
	}

	public void setPidfilePath(String data) {
		pidfilePath = data;
	}

	private final String demoCommand = "java.exe -cp target\\example.processhandle.jar;target\\lib\\* example.Dialog";

	private String javaCommand = demoCommand;

	public String getJavaCommand() {
		return javaCommand;
	}

	public void setJavaCommand(String data) {
		javaCommand = data;
	}

	private List<String> specialOptions = new ArrayList<>();

	public List<String> getSpecialOptions() {
		return specialOptions;
	}

	public void setSpecialOptions(List<String> data) {
		logger.info("setting special Options: " + data);
		specialOptions = data;
	}

	private Process process = null;

	// https://www.baeldung.com/java-lang-processbuilder-api
	public void launch(List<String> arguments) {
		logger.info("ProcessBuilder arguments:  %s\n" + arguments, "NOTE the doubled commas in the above is expected");
		logger.info("Launching command: " + String.join(" ", arguments));

		ProcessBuilder processBuilder = new ProcessBuilder(arguments);
		Map<String, String> env = processBuilder.environment();
		env.put("PATH", String.format("%s;%s", env.get("PATH"), javaPath));
		try {
			process = processBuilder.start();
		} catch (IOException e) {
			logger.info("Exception (ignored): " + e.toString());
		}
	}

	public String composeDebugCommand(String debugLogFilePath) {

		return String.format(
				"out-file -literalpath '%s' -encoding ascii -append " + "-inputobject "
						+ "('currentdir: {0}{1}path:{2}' -f $pwd, ([char]13 + [char]10), $env:PATH); ",
				debugLogFilePath);
	}

	// https://stackoverflow.com/questions/7486717/finding-parent-process-id-on-windows
	public void launchPowershell1() {
		final int timeout = 10;
		String debugCommand = null;
		String commandTemplate = null;
		String command = null;
		if (debug)
			debugCommand = composeDebugCommand(debugLogFilePath);
		else
			// NOTE: cannot leave as "null"
			debugCommand = "";
		
		commandTemplate = Utils.getScriptContent("wrapper_script.txt").replaceAll("\\r?\\n", "");
		command = String.format(commandTemplate, javaCommand.replaceAll("java.exe", ""), pidfilePath);
		logger.info("Building command: " + command);
		// commandTemplate = "$info = start-process %s -passthru; $info.PriorityClass=System.Diagnostics.ProcessPriorityClass]::BelowNormal; write-output ('Pid={0}' -f $info.id) | out-file -LiteralPath '%s' -encoding ascii;";
		// command = debugCommand + String.format(commandTemplate, buildCommand(javaCommand), pidfilePath);
		logger.info("Building command: " + command);
		List<String> commandArguments = new ArrayList<>(Arrays.asList(command.split("\\s+")));
		List<String> arguments = new ArrayList<>(
				Arrays.asList(new String[] { "powershell.exe", "/noprofile", "/executionpolicy", "bypass" }));
		arguments.add("\"&{");
		arguments.addAll(commandArguments);
		arguments.add("}\"");
		launch(arguments);
	}

	// https://stackoverflow.com/questions/19250927/in-powershell-set-affinity-in-start-process
	public String buildCommand(final String command) {
		logger.info("processing command: " + command);
		String newCommand = null;
		List<String> commandArguments = new ArrayList<>(Arrays.asList(command.replaceAll("\\s+", " ").split("\\s+")));
		if (commandArguments.get(0).contains("java")) {
			commandArguments.remove(0);
		}
		StringBuffer contents = new StringBuffer();
		contents.append("-filepath 'java.exe' -argumentlist ");
		List<String> newCommandArguments = new ArrayList<>();

		// note trailing space
		// usually options are provided before jar or class argument, whoch is
		// the
		// last one
		if (!specialOptions.isEmpty()) {
			List<String> specialOptionsArguments = new ArrayList<>();
			for (String arg : specialOptions) {
				specialOptionsArguments.add(String.format("'%s'", arg));
			}
			newCommandArguments.addAll(specialOptionsArguments);
		}

		for (String arg : commandArguments) {
			newCommandArguments.add(String.format("'%s'", arg));
		}
		logger.info("New Command Arguments:" + newCommandArguments);
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
			result = contents.toString().replaceAll("\r?\n", "").replaceAll("Pid=", "");
			logger.info(String.format("%s data:%s", filePath, result));

		} catch (Exception e) {
			logger.info("Exception (ignored): " + e.toString());
		}
		return result;
	}

	public void launchCmd1() {
		List<String> commandArguments = new ArrayList<>(Arrays.asList(javaCommand.split("\\s+")));
		List<String> arguments = new ArrayList<>(Arrays.asList(new String[] { "cmd.exe", "/c", "start", "/low" }));
		try {
			arguments.addAll(commandArguments);
		} catch (UnsupportedOperationException e) {
			logger.info("Exception (ignored): " + e.toString());
		}
		launch(arguments);
	}

	// https://howtodoinjava.com/java/collections/arraylist/merge-arraylists/
	public void launchCmd2() {

		List<String> arguments = new ArrayList<>(Arrays.asList(javaCommand.split("\\s+")));
		try {
			arguments.addAll(0, new ArrayList<>(Arrays.asList(new String[] { "cmd.exe", "/c", "start", "/low" })));
		} catch (java.lang.UnsupportedOperationException e) {
			logger.info("Exception (ignored): " + e.toString());
		}
		launch(arguments);
	}

}
