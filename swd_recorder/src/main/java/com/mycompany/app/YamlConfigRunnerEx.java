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
import java.util.Date;
import java.util.List;
import java.util.Map;

import java.lang.RuntimeException;
import static java.lang.String.format;
import java.text.SimpleDateFormat;

import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;

public class YamlConfigRunnerEx {
	private static String yamlFile = null;
	private static DumperOptions options = new DumperOptions();
	private static Yaml yaml;

	private static Configuration loadConfiguration(String fileName) {
		Configuration config = null;
		try (InputStream in = Files.newInputStream(Paths.get(fileName))) {
			config = yaml.loadAs(in, Configuration.class);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return config;
	}

	private static void saveConfiguration(Configuration config) {
		saveConfiguration(config, null);
	}

	@SuppressWarnings("deprecation")
	private static void saveConfiguration(Configuration config, String fileName) {
		String pattern = "yyyy-MM-dd";
		SimpleDateFormat format = new SimpleDateFormat(pattern);
		Date date = new Date();
		try {
			config.updated = format.parse(String.format("%4d-%2d-%2d", date.getYear(),
					date.getMonth(), date.getDay()));
		} catch (java.text.ParseException e) {
			config.updated = date;
		}
		if (fileName != null) {
			try {
				FileOutputStream fos = new FileOutputStream(fileName);
				Writer out = new OutputStreamWriter(fos, "UTF8");
				yaml.dump(config, out);
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			System.err.println(yaml.dump(config));
		}
	}

	public static void main(String[] args) throws IOException {

		yamlFile = (args.length == 0) ? String.format("%s/src/main/resources/%s",
				System.getProperty("user.dir"), "sample.yaml") : args[0];

		options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
		yaml = new Yaml(options);
		Configuration config = loadConfiguration(yamlFile);
		saveConfiguration(config);
	}
}