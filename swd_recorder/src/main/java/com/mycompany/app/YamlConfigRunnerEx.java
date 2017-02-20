package com.mycompany.app;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.Paths;

import java.util.Date;
import java.util.List;
import java.util.Map;

import java.lang.RuntimeException;
import static java.lang.String.format;
import java.text.SimpleDateFormat;

import org.yaml.snakeyaml.Yaml;

// based on: https://www.google.com/url?sa=t&rct=j&q=&esrc=s&source=web&cd=1&cad=rja&uact=8&ved=0ahUKEwjX45it55zSAhXljlQKHdWfAYYQFggcMAA&url=https%3A%2F%2Fdzone.com%2Farticles%2Fusing-yaml-java-application&usg=AFQjCNE18HXxSifZVDFBSno04sNVcg_j_g
public class YamlConfigRunnerEx {
	@SuppressWarnings("deprecation")
	public static void main(String[] args) throws IOException {
		Yaml yaml = new Yaml();
		String yamlFile = null;
		if (args.length != 1) {
			yamlFile = String.format("%s/src/main/resources/%s",
					System.getProperty("user.dir"), "sample.yaml");
		} else {
			yamlFile = args[0];
		}

		try (InputStream in = Files.newInputStream(Paths.get(yamlFile))) {
			Configuration config = yaml.loadAs(in, Configuration.class);
			System.err.println(config.toString());
			String pattern = "yyyy-MM-dd";
			SimpleDateFormat format = new SimpleDateFormat(pattern);
			Date date = new Date();
			try {
				config.updated = format.parse(String.format("%4d-%2d-%2d",
						date.getYear(), date.getMonth(), date.getDay()));
			} catch (java.text.ParseException e) {
				config.updated = date;
			}
			System.err.println(yaml.dump(config));
		}
	}

}