package org.swet;

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

/**
 * Configuration test for Selenium Webdriver Elementor Tool
 *  
 * @author Serguei Kouzmine (kouzmine_serguei@yahoo.com)
 */

public class YamlConfigRunnerEx {
	private static String yamlFile = null;

	public static void main(String[] args) throws IOException {

		yamlFile = (args.length == 0) ? String.format("%s/src/main/resources/%s",
				System.getProperty("user.dir"), "sample.yaml") : args[0];

		Configuration config = YamlHelper.loadConfiguration(yamlFile);
		YamlHelper.printConfiguration(config);
	}
}
