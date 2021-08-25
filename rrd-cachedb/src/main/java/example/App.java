package example;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.junit.Assert;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import org.rrd4j.ConsolFun;
import org.rrd4j.DsType;
import org.rrd4j.core.RrdDb;
import org.rrd4j.core.RrdMemoryBackendFactory;
import java.lang.IllegalArgumentException;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.hasKey;
import static org.hamcrest.Matchers.equalTo;

public class App {

	protected static String osName = getOSName();
	public static final int INVALID_OPTION = 42;
	private Map<String, String> flags = new HashMap<>();
	private final static Map<String, List<String>> dsMap = new HashMap<>();
	private static List<Path> result = new ArrayList<>();

	private static boolean debug = false;
	private final static Options options = new Options();
	private static CommandLineParser commandLineparser = new DefaultParser();
	private static CommandLine commandLine = null;

	public static void main(String args[]) throws ParseException {
		options.addOption("h", "help", false, "Help");
		options.addOption("d", "debug", false, "Debug");
		options.addOption("p", "path", true, "Path to scan");
		commandLine = commandLineparser.parse(options, args);
		if (commandLine.hasOption("h")) {
			help();
		}
		if (commandLine.hasOption("d")) {
			debug = true;
		}
		String path = commandLine.getOptionValue("path");
		if (path == null) {
			System.err.println("Missing required argument: path");
			return;
		}
		try {
			listFilesDsNames(path);
		} catch (IOException e) {

		}
		if (debug) {
			System.err.println("Done: " + path);
		}

	}

	public static void help() {
		System.exit(1);
	}

	public static void listFilesDsNames(String path) throws IOException {

		Path basePath = Paths.get(path);
		// NOTE: do not use File.separator
		final String basePathUri = new URL(
				getDataFileUri(basePath.toAbsolutePath().toString())).getFile() + "/";
		System.err.println("Scanning path: " + basePathUri);
		// origin:
		// https://github.com/mkyong/core-java/blob/master/java-io/src/main/java/com/mkyong/io/api/FilesWalkExample.java
		try (Stream<Path> walk = Files.walk(basePath)) {
			result = walk.filter(Files::isRegularFile)
					.filter(o -> o.getFileName().toString().matches(".*rrd$"))
					.collect(Collectors.toList());
		}
		// NOTE: streams are not meant to be reused
		final boolean debug = false;
		if (debug)
			System.err.println("RRD File paths: "
					+ result.stream().map(o -> o.toAbsolutePath().toString())
							.collect(Collectors.toList()).toString());

		result.stream().forEach(o -> {
			try {
				List<String> dsList = new ArrayList<>();

				final String key = o.getFileName().toString();
				final String dataFilePath = o.toAbsolutePath().toString();
				final String dataFileUri = getDataFileUri(dataFilePath);
				URL url = new URL(dataFileUri);
				System.err.println(
						"Reading RRD file: " + url.getFile().replaceFirst(basePathUri, "")
								.replaceAll("/", ":").replaceFirst(".rrd$", ""));
				RrdDb rrd = RrdDb.getBuilder().setPath("test")
						.setRrdToolImporter(url.getFile())
						.setBackendFactory(new RrdMemoryBackendFactory()).build();
				for (int cnt = 0; cnt != rrd.getDsCount(); cnt++) {
					String ds = rrd.getDatasource(cnt).getName();
					System.err.println(String.format("ds[%d]= \"%s\"", cnt, ds));
					assertThat(ds, notNullValue());
					dsList.add(ds);
				}
				dsMap.put(key, dsList);
			} catch (IllegalArgumentException | IOException e) {
				System.err.println("Exception (ignored): " + e.toString());
			}
		});
		assertThat(dsMap, notNullValue());
	}

	private static String getOSName() {
		if (osName == null) {
			osName = System.getProperty("os.name").toLowerCase();
			if (osName.startsWith("windows")) {
				osName = "windows";
			}
		}
		return osName;
	}

	private static String getDataFileUri(String dataFilePath) {
		return osName.equals("windows")
				? "file:///" + dataFilePath.replaceAll("\\\\", "/")
				: "file://" + dataFilePath;
	}

}
