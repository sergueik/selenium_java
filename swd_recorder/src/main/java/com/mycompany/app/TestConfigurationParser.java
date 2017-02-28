package com.mycompany.app;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Scanner;
import java.util.regex.Pattern;
import java.lang.RuntimeException;
import static java.lang.String.format;
import java.text.SimpleDateFormat;

import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;

public class TestConfigurationParser {

	public static Scanner loadTestData(final String fileName) {
		Scanner scanner = null;
		System.err
				.println(String.format("Reading configuration file: '%s'", fileName));
		try {
			scanner = new Scanner(new File(fileName));
		} catch (FileNotFoundException e) {
			// fail(String.format("File '%s' was not found.", fileName));
			System.err.println(
					String.format("Configuration file was not found: '%s'", fileName));
			e.printStackTrace();
		}
		return scanner;
	}

	public static List<String[]> getConfiguration(final String fileName) {
		ArrayList<String[]> listOfData = new ArrayList<>();
		Scanner scanner = loadTestData(fileName);
		String separator = "|";
		while (scanner.hasNext()) {
			String line = scanner.next();
			String[] data = line.split(Pattern.compile("(\\||\\|/)")
					.matcher(separator).replaceAll("\\\\$1"));
			for (String entry : data) {
				System.err.println("data entry: " + entry);
			}
			listOfData.add(data);
		}
		scanner.close();
		return listOfData;
	}
}