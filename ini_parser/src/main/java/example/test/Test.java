package example.test;

/**
 * Copyright 2022 Serguei Kouzmine
 */

import java.util.HashMap;
import java.util.Map;

import example.IniNewParser;

import example.test.Utils;

public class Test {
	private static String iniFilename = "data.ini";
	private static IniNewParser parser = IniNewParser.getInstance();
	private static String iniFile = String
			.format("%s/%s", System.getProperty("user.dir"), iniFilename)
			.replaceAll("\\\\", "/");
	private static Map<String, Map<String, Object>> data = new HashMap<>();
	private static Map<String, Object> sectionData = new HashMap<>();
	private static String[] entries = { "message", "flag", "number", "empty" };
	private static Utils utils = Utils.getInstance();

	private static Map<String, String> propertiesMap;
	private static boolean debug = System.getenv("DEBUG") != null
			? Boolean.parseBoolean(System.getenv("DEBUG")) : false;

	public static void main(String[] args) {

		// -apply argument
		// -format ini,yaml
		//
		System.err.println("debug: " + debug);

		utils.setDebug(debug);
		String fileName = String
				.format("%s\\%s", System.getProperty("user.dir"), "custom.properties")
				.replaceAll("\\\\", "/");

		System.err.println("fileName: " + fileName);
		propertiesMap = utils.getProperties(fileName);
		// propertiesMap = utils.getProperties("application.properties");

		String option1 = utils
				.resolveEnvVars(utils.getPropertiesMap().get("option1"));
		if (option1 == null) {
			option1 = "default value for option1";
		}

		System.err.println("option1: " + option1);
		String option2 = utils.resolveEnvVars(
				utils.getPropertiesMap().get("option2"), "default value for option2");
		System.err.println("option2: " + option2);
		String option3 = utils.resolveEnvVars(
				utils.getPropertiesMap().get("option3"), "default value for option3");
		System.err.println("option3: " + option3);
		// ini file
		/*
		parser.parseFile(iniFile);
		data = parser.getData();
		sectionData = data.get("Section1");
		*/
	}
}
