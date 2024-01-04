package example;

/**
 * Copyright 2021,2022,2024 Serguei Kouzmine
 */

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.sun.jna.Pointer;
import com.sun.jna.platform.win32.Kernel32;
import com.sun.jna.platform.win32.WinNT.HANDLE;

public class App {

	private static Logger logger = LogManager.getLogger(App.class.getName());
	private static Launcher launcher = Launcher.getInstance();
	private static Utils utils = Utils.getInstance();
	private static CommandLineParser commandLineparser = new DefaultParser();
	private static CommandLine commandLine = null;
	private static String pidfile = "c:\\temp\\pidfile";
	private final static Options options = new Options();
	private static int delay = 10000;
	private final static List<List<Float>> data = new ArrayList<>();
	private static Map<String, String> propertiesMap;

	public static void main(String[] args) {
		options.addOption("d", "debug", false, "Debug");
		options.addOption("a", "action", true, "Action");
		options.addOption("w", "wait", true, "Wait Timeout before killing");
		options.addOption("p", "pid", true, "Pid");
		options.addOption("f", "file", true, "Pid File");
		try {
			commandLine = commandLineparser.parse(options, args);

			if( commandLine.hasOption("debug"))
				launcher.setDebug(true);

			String wait = commandLine.getOptionValue("wait");
			if (wait != null)
				delay = Integer.parseInt(wait);
			String file = commandLine.getOptionValue("file");
			if (file != null) {
				pidfile = utils.resolveEnvVars(file);
				logger.info("Use pidfile: " + pidfile);
				launcher.setPidfilePath(pidfile);
			}
			String action = commandLine.getOptionValue("action");
			if (action == null) {
				System.err.println("Missing required argument: action");
			}
			if (action.equals("cmd1")) {
				launcher.launchCmd1();
			}
			if (action.equals("cmd2")) {
				launcher.launchCmd2();
			}
			if (action.equals("powershell")) {
				utils.setDebug(true);
				launcher.setJavaPath(System.getenv("JAVA_HOME")
						+ System.getProperty("file.separator") + "bin");
				// set to JDK 1.8 path to observe the format error
				// private String javaPath = "c:\\java\\jdk1.8.0_101\\bin";

				propertiesMap = utils
						.getProperties(String.format("%s\\src\\main\\resources\\%s",
								System.getProperty("user.dir"), "application.properties"));
				// propertiesMap = utils.getProperties("application.properties");
				// TODO: cyclic call
				String options = propertiesMap.get("options");
				// expands environment
				logger.info("Added options: " + options);
				String special_options = utils.getPropertyEnv("special_options", "");
				if (special_options != null && !special_options.isEmpty()) {
					logger.info(
							String.format("Added special options: \"%s\" ", special_options));
					launcher.setSpecialOptions(new ArrayList<String>(
							Arrays.asList(new String[] { special_options })));

				}
				String command = String.format(
						"java.exe -cp target\\example.processhandle.jar;target\\lib\\* %s example.Dialog",
						options);
				command = propertiesMap.get("demo_command");
				launcher.buildCommand(command);
				launcher.setJavaCommand(command);
				launcher.launchPowershell1();
				logger.info(String.format("Waiting for %d millisecond ", delay));
				sleep(delay);

				int pid = launcher.getPid();
				boolean isAlive = isProcessIdRunning(pid);
				logger.info("is alive: " + isAlive);
				isAlive = isProcessIdRunningOnWindows(pid);
				logger.info("is alive (Windows version): " + isAlive);
				
				
				killProcess(pid);
			}
		} catch (ParseException e) {
		}
	}

	// https://www.tabnine.com/code/java/methods/com.sun.jna.platform.win32.Kernel32/GetProcessId
	private static Long getWindowsProcessId(final Process process,
			final Logger logger) {
		/* determine the pid on windows platforms */
		try {
			Field f = process.getClass().getDeclaredField("handle");
			f.setAccessible(true);
			long handl = f.getLong(process);
			Kernel32 kernel = Kernel32.INSTANCE;
			HANDLE handle = new HANDLE();
			handle.setPointer(Pointer.createConstant(handl));
			int ret = kernel.GetProcessId(handle);
			logger.debug("Detected pid: {}", ret);
			return Long.valueOf(ret);
		} catch (final IllegalAccessException | NoSuchFieldException nsfe) {
			logger.debug("Could not find PID for child process due to {}", nsfe);
		}
		return null;
	}

	public static boolean isProcessIdRunning(int pid) {
		Stream<ProcessHandle> liveProcesses = ProcessHandle.allProcesses();
		ProcessHandle processHandle = liveProcesses.filter(ProcessHandle::isAlive)
				.filter(o -> o.pid() == pid).findFirst().orElse(null);
		return (processHandle == null) ? false : processHandle.isAlive();
	}

	// https://stackoverflow.com/questions/2533984/java-checking-if-any-process-id-is-currently-running-on-windows/41489635
	public static boolean isProcessIdRunningOnWindows(int pid) {
		try {
			Runtime runtime = Runtime.getRuntime();
			String cmds[] = { "cmd", "/c", "tasklist /FI \"PID eq " + pid + "\"" };
			Process proc = runtime.exec(cmds);
			logger.info("Running the command: " + Arrays.asList(cmds));
			InputStream inputstream = proc.getInputStream();
			InputStreamReader inputstreamreader = new InputStreamReader(inputstream);
			BufferedReader bufferedreader = new BufferedReader(inputstreamreader);
			String line;
			while ((line = bufferedreader.readLine()) != null) {
				// Search the PID matched lines single line for the sequence: " 1300 "
				// if you find it, then the PID is still running.
				if (line.contains(" " + pid + " ")) {
					return true;
				}
			}

			return false;
		} catch (Exception ex) {
			ex.printStackTrace();
			System.out.println("Cannot query the tasklist for some reason.");
			System.exit(0);
		}
		return false;

	}

	// based on:
	// https://www.javaworld.com/article/2071275/core-java/when-runtime-exec---won-t.html?page=2
	public static void killProcess(final int pid) {
		logger.info("Killing the process pid: " + pid);

		if (pid <= 0) {
			return;
		}
		String command = String.format("taskkill.exe /T /F /FI \"pid eq %s\"", pid);
		// /T Terminates the specified process and any child processes which were
		// started by it
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
				logger.info("Process exit code: " + exitCode);
				if (processOutput.length() > 0) {
					logger.info("Output: " + processOutput);
				}
				if (processError.length() > 0) {
					// e.g. The process "chromedriver.exe"
					// with PID 5540 could not be terminated.
					// Reason: Access is denied.
					logger.info("Error: " + processError);
				}
			}
		} catch (Exception e) {
			logger.error("Exception (ignored): " + e.getMessage());
		}
	}

	public static void sleep(Integer milliSeconds) {
		try {
			Thread.sleep((long) milliSeconds);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}