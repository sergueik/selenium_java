package example;

/**
 * Copyright 2021 Serguei Kouzmine
 */

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Stream;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AppLinux {
	private static Logger logger = LogManager.getLogger(App.class.getName());
	private static CommandLineParser commandLineparser = new DefaultParser();
	private static CommandLine commandLine = null;
	private final static Options options = new Options();
	public static void help() {
		System.exit(1);
	}

	public static void main(String[] args) {
		options.addOption("h", "help", false, "Help");
		options.addOption("d", "debug", false, "Debug");
		options.addOption("a", "action", true, "Action");
		options.addOption("p", "pid", true, "Pid");
		try {
			commandLine = commandLineparser.parse(options, args);
			if (commandLine.hasOption("h")) {
				help();
			}
			String action = commandLine.getOptionValue("action");
			if (action == null) {
				System.err.println("Missing required argument: action");

			}
			if (action.equals("list")) {
				infoOfLiveProcesses();
			}
			/*
			 * if (action.equals("current")) { Process process =
			 * Process.getCurrentProcess(); getWindowsProcessId(process, logger);
			 * logger.info(process.toHandle().pid()); }
			 */
			if (action.equals("check")) {
				String resource = commandLine.getOptionValue("pid");
				if (resource == null) {
					System.err.println("Missing required argument: pid");
				} else {
					Integer pid = Integer.parseInt(resource);
					logger.info("looking pid " + pid);
					// Returns an Optional<ProcessHandle> for an existing native process.
					Optional<ProcessHandle> result = ProcessHandle.of(pid);
					ProcessHandle processHandle = null;
					try {
						processHandle = result.isPresent() ? result.get() : null;
					} catch (NoSuchElementException e1) {
					}
					logger.info(processHandle);

					Boolean status = (processHandle == null) ? false : processHandle.isAlive();
					String extraInfo = null;
					if (status)
						try {
							extraInfo = "(" + "command: " + processHandle.info().command().get() + " started:"
									+ processHandle.info().startInstant().get() + " " + "pid:" + processHandle.pid()
									+ ")";
						} catch (NoSuchElementException e1) {
						}
					logger.info("Process pid (via ProcessHandle): " + pid + " is: "

							+ (status ? "alive" : "not alive") + " " + extraInfo);
				}
			}
		} catch (ParseException e) {
		}
	}

	private static void infoOfLiveProcesses() {
		Stream<ProcessHandle> liveProcesses = ProcessHandle.allProcesses();
		liveProcesses.filter(ProcessHandle::isAlive).forEach(ph -> {
			logger.info("PID: " + ph.pid());
			logger.info("Instance: " + ph.info().startInstant());
			logger.info("User: " + ph.info().user());
		});
	}

}
