package com.hribol.automation.cli;

import java.io.File;

import com.hribol.automation.cli.commands.Command;
import com.hribol.automation.cli.commands.RecordCommand;
import com.hribol.automation.cli.commands.ReplayCommand;
import com.hribol.automation.core.utils.*;

/**
 * Created by hvrigazov on 14.03.17.
 */
public class Main {

	public static void main(String[] args) {
		String pathToChromeDriver = new File("c:/java/selenium/chromedriver.exe")
				.getAbsolutePath();
		String pathToJSInjectionFile = getResourcePath("eventsRecorder.js");
		String pathToApplicationConfiguration = getResourcePath("tenniskafe.json");
		String baseUrl = "http://tenniskafe.com";
		String testCaseFile = getResourcePath("testCase.json");
		Command command = new ReplayCommand(pathToChromeDriver,
				pathToApplicationConfiguration, testCaseFile);
		command.run();
		// Command command = new RecordCommand(pathToChromeDriver,
		// pathToJSInjectionFile, baseUrl, testCaseFile);
		// command.run();
		System.exit(0);
	}

	public static String getResourcePath(String resourceFileName) {
		return String.format("%s/../spiderman-core/src/test/resources/%s",
				System.getProperty("user.dir"), resourceFileName);
	}
}
