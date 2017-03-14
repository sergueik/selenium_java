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
import java.util.List;
import java.util.Locale;
import java.util.Map;

import java.lang.RuntimeException;
import static java.lang.String.format;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.text.SimpleDateFormat;
import java.text.DateFormat;

import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;

public class YamlHelper {

	private static DumperOptions options = new DumperOptions();
	private static Yaml yaml = null;
	private static Date dateString;
	private static Calendar calendar;

	public static Configuration loadConfiguration(String fileName) {
		if (yaml == null) {
			options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
			yaml = new Yaml(options);
		}
		Configuration config = null;
		try (InputStream in = Files.newInputStream(Paths.get(fileName))) {
			config = yaml.loadAs(in, Configuration.class);
			// TODO: better method naming
			YamlHelper.saveConfiguration(config);

		} catch (IOException e) {
			e.printStackTrace();
		}
		return config;
	}

	public static void printConfiguration(Configuration config) {
		saveConfiguration(config);
	}

	public static void saveConfiguration(Configuration config) {
		saveConfiguration(config, null);
	}

	@SuppressWarnings("deprecation")
	public static void saveConfiguration(Configuration config, String fileName) {
		if (yaml == null) {
			options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
			yaml = new Yaml(options);
		}
		/*
		String pattern = "yyyy-MM-dd";
		SimpleDateFormat format = new SimpleDateFormat(pattern);
		Date date = new Date();
		try {
			config.updated = format.parse(String.format("%4d-%2d-%2d", date.getYear(),
					date.getMonth(), date.getDay()));
		} catch (java.text.ParseException e) {
			config.updated = date;
		}
		*/
		calendar = new GregorianCalendar();
		SimpleDateFormat dateFormat = new SimpleDateFormat(
				((SimpleDateFormat) DateFormat.getDateInstance(DateFormat.SHORT,
						Locale.US)).toPattern().replaceAll("\\byy\\b", "yyyy").replaceAll("\\bM\\b", "MM").replaceAll("\\bd\\b", "dd"));
		System.err.println("Testing date format: " + dateFormat.toPattern());

		try {
			config.updated = dateFormat.parse(dateFormat.format(calendar.getTime()));
		} catch (java.text.ParseException e) {
			System.err.println("Ignoring date parse exception: " + e.toString());
			config.updated = new Date();
		}

		if (fileName != null) {
			try {
				Writer out = new OutputStreamWriter(new FileOutputStream(fileName),
						"UTF8");
				yaml.dump(config, out);
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			System.err.println(yaml.dump(config));
		}
	}
}
