package example;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

import java.io.File;
import java.io.IOException;

import java.net.URL;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import org.rrd4j.core.RrdDb;
import org.rrd4j.core.RrdMemoryBackendFactory;

import example.HostData;

// https://bitbucket.org/xerial/sqlite-jdbc
// https://docs.oracle.com/javase/tutorial/jdbc/basics/sqlrowid.html

public class App {

	// TODO: make a parameter
	// for RRD inventory operations
	// private static final String filemask = ".*rrd$";
	// for legacy data.txt inventory operations
	private static final String filemask = "data.txt.*$";

	private static final Random randomId = new Random();
	private static Connection connection = null;
	private static String osName = getOSName();
	public static final int INVALID_OPTION = 42;

	final static Map<String, String> env = System.getenv();
	private Map<String, String> flags = new HashMap<>();
	private static Map<String, List<String>> dsMap = new HashMap<>();

	private static boolean noop = false;
	private static boolean debug = false;
	private static boolean legacy = false;
	private static boolean save = false;
	private static boolean verifylinks = false;

	private static String databaseHost = null;
	private static String database = null;
	private static int databasePort = 3306;
	private static String sqliteDatabaseName = "cache.db";
	private static String databaseTable = "cache_table";
	private static String databaseUser = null;
	private static String databasePassword = null;

	private final static Options options = new Options();
	private static CommandLineParser commandLineparser = new DefaultParser();
	private static CommandLine commandLine = null;

	public static void main(String args[]) throws ParseException {
		options.addOption("h", "help", false, "help");
		options.addOption("d", "debug", false, "debug");
		options.addOption("s", "save", false, "save");
		options.addOption("l", "legacy", false, "legacy");
		options.addOption("p", "path", true, "path to scan");
		options.addOption("f", "file", true, "sqlite database filename to write");
		options.addOption("v", "verifylinks", false,
				"verify file links that are found during scan");
		options.addOption("r", "reject", true, "folder(s) to reject");
		options.addOption("i", "collect", true, "folder(s) to collect");

		options.addOption("q", "vendor", true,
				"database kind. surrently supported sqlite and mysql (partially)");

		options.addOption("z", "host", true, "database host");
		options.addOption("y", "port", true, "database port");
		options.addOption("w", "database", true, "database");
		options.addOption("u", "user", true, "database user");
		options.addOption("t", "table", true, "database table");
		options.addOption("z", "password", true, "database password");
		options.addOption("n", "noop", false, "noop");

		commandLine = commandLineparser.parse(options, args);
		if (commandLine.hasOption("h")) {
			help();
		}
		if (commandLine.hasOption("d")) {
			debug = true;
		}
		if (commandLine.hasOption("noop")) {
			// suppress actual rrd inspection
			noop = true;
		}
		if (commandLine.hasOption("verifylinks")) {
			verifylinks = true;
		}

		if (commandLine.hasOption("save")) {
			save = true;
		}

		if (commandLine.hasOption("legacy")) {
			legacy = true;
		}
		String vendor = commandLine.getOptionValue("vendor");
		if (vendor == null) {
			System.err.println("Missing argument: vendor. Using default");
			vendor = "sqlite";
		}
		if (!vendor.matches("(?i)(mysql|sqlite)")) {
			System.err.printf("Unrecognized argument: vendor %s. Using default\n",
					vendor);
			vendor = "sqlite";
		}
		if (vendor.equals("mysql")) {
			databaseHost = commandLine.getOptionValue("databaseHost");
			if (databaseHost == null) {
				System.err.println("Missing argument: databaseHost. Using default");
				databaseHost = "localhost";
			}

			try {
				databasePort = Integer
						.parseInt(commandLine.getOptionValue("databasePort"));
			} catch (Exception e) {
				System.err.println("Missing argument: databasePort. Using default");
				databasePort = 3306;
			}

			database = commandLine.getOptionValue("database");
			if (database == null) {
				System.err.println("Missing argument: database. Using default");
				database = "test";
			}

			databaseUser = commandLine.getOptionValue("user");
			if (databaseUser == null) {
				System.err.println("Missing argument: databaseUser. Using default");
				databaseUser = "java";
			}

			databaseTable = commandLine.getOptionValue("table");
			if (databaseTable == null) {
				System.err.println("Missing argument: databaseTable. Using default");
				databaseTable = "cache_table";
			}

			databasePassword = commandLine.getOptionValue("password");
			if (databasePassword == null) {
				System.err.println("Missing argument: databasePassword. Using default");
				databasePassword = "password";
			}
			try {
				testJDBCConnection(vendor);
			} catch (Exception e) {
				System.err.println("Excetpion (ignored)" + e.toString());
			}
		}

		sqliteDatabaseName = commandLine.getOptionValue("file");
		if (sqliteDatabaseName == null) {
			System.err.println("Missing argument: sqliteDatabaseName. Using default");
			sqliteDatabaseName = "cache.db";
		}

		String path = commandLine.getOptionValue("path");
		if (path == null) {
			System.err.println("Missing required argument: path");
			return;
		}
		String collect = commandLine.getOptionValue("collect");
		String reject = commandLine.getOptionValue("reject");

		try {
			List<String> collectFolders = collect == null ? new ArrayList<>()
					: Arrays.asList(collect.split(","));
			List<String> rejectFolders = reject == null ? new ArrayList<>()
					: Arrays.asList(reject.split(","));
			if (collectFolders.size() != 0 || rejectFolders.size() != 0) {
				dsMap = listFilesDsNames(path, collectFolders, rejectFolders);
			} else {
				dsMap = listFilesDsNames(path);
			}
		} catch (IOException e) {
		}

		if (save) {
			if (legacy) {
				createTableForLegacyData();
				saveLegacyData(dsMap);
				displayLegacyData();
			} else {
				createTable();
				saveData(dsMap);
				displayData();
			}
		}
		if (debug) {
			System.err.println("Done: " + path);
		}

	}

	private static void clearData() {
		try {
			System.err.println("Querying data");
			Statement statement = connection.createStatement();
			statement.setQueryTimeout(30);
			statement.executeUpdate("delete from " + databaseTable);
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}

	}

	private static void displayLegacyData() {
		// TODO
	}

	private static void displayData() {
		try {
			System.err.println(
					"Querying data : " + connection.getMetaData().getDatabaseProductName()
							+ "\t" + "catalog: " + connection.getCatalog() + "\t" + "schema: "
							+ connection.getSchema());
			Statement statement = connection.createStatement();
			statement.setQueryTimeout(30);

			ResultSet rs = statement.executeQuery(
					String.format("SELECT DISTINCT fname, ds FROM %s ORDER BY fname, ds",
							databaseTable));
			while (rs.next()) {
				System.err.println("fname = " + rs.getString("fname") + "\t" + "ds = "
						+ rs.getString("ds"));
			}
			statement.close();
			statement = connection.createStatement();
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		} finally {
			try {
				if (connection != null)
					connection.close();
			} catch (SQLException e) {
				System.err.println(e);
			}
		}
	}

	private static void createTableForLegacyData() {
		// TODO - join with hostname/appid/invironment
		// CREATE TABLE "hosts" ( `id` INTEGER, `hostname` TEXT NOT NULL, `appid`
		// TEXT, `environment` TEXT, `datacenter` TEX, `addtime` TEXT, PRIMARY
		// KEY(`id`) )
		// NOTE:
		createTableCommon();
	}

	private static void createTableCommon() {
		connection = null;

		final String databasePath = String.format("%s%s%s",
				env.get(osName.equals("windows") ? "USERPROFILE" : "HOME"),
				File.separator, sqliteDatabaseName);
		try {
			connection = DriverManager
					.getConnection("jdbc:sqlite:" + databasePath.replaceAll("\\\\", "/"));
			System.out
					.println("Opened database connection successfully: " + databasePath);

			System.out.println("Connected to product: "
					+ connection.getMetaData().getDatabaseProductName() + "\t"
					+ "catalog: " + connection.getCatalog() + "\t" + "schema: "
					+ connection.getSchema());

		} catch (SQLException e) {
			System.err.println("Exception (ignored)" + e.getMessage());
		} catch (Exception e) {
			System.err.println("Unexpected exception " + e.getClass().getName() + ": "
					+ e.getMessage());
			System.exit(1);
		}

	}

	private static void createTable() {
		try {
			createTableCommon();
			Statement statement = connection.createStatement();
			statement.setQueryTimeout(30);
			String sql = String.format(
					"CREATE TABLE IF NOT EXISTS %s " + "( "
							+ "id INT PRIMARY KEY NOT NULL," + "ds CHAR(50) NOT NULL, "
							+ "fname TEXT NOT NULL," + "expose CHAR(50)" + ");",
					databaseTable);
			System.out.println("Running SQL: " + sql);
			statement.executeUpdate(sql);
			statement.close();

		} catch (SQLException e) {
			System.err.println("Exception (ignored)" + e.getMessage());
		} catch (Exception e) {
			System.err.println("Unexpected exception " + e.getClass().getName() + ": "
					+ e.getMessage());
			System.exit(1);
		}
		// TOTO: add flag for in-memory
		/*
		try {
			Class.forName("org.sqlite.JDBC");
			connection = DriverManager.getConnection("jdbc:sqlite::memory:");
			Statement statement = connection.createStatement();
			statement.setQueryTimeout(30);
			String sql = String.format(
			String sql = String.format("CREATE TABLE IF NOT EXISTS %s " + "( "
					+ "id INT PRIMARY KEY NOT NULL," + "ds CHAR(50) NOT NULL, "
					+ "fname CHAR(255) NOT NULL," + " expose CHAR(50) NOT NULL " + ")",
					databaseTable);
			statement.executeUpdate(sql);
			statement.close();
		
		} catch (ClassNotFoundException | SQLException e) {
			System.err.println(e.getMessage());
			return;
		}
		*/
	}

	private static void saveLegacyData(Map<String, List<String>> dsMap) {
		// TODO
	}

	private static void saveData(Map<String, List<String>> dsMap) {
		System.err.println("Saving data");
		try {
			Statement statement = connection.createStatement();
			statement.setQueryTimeout(30);
			statement.executeUpdate("delete from " + databaseTable);
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}

		dsMap.entrySet().stream().forEach(o -> {
			String fname = o.getKey();
			o.getValue().stream().forEach(ds -> {
				try {

					PreparedStatement preparedStatement = connection.prepareStatement(
							String.format("INSERT INTO %s (id, fname, ds) VALUES (?, ?, ?)",
									databaseTable));
					int id = randomId.nextInt(1_000_000_000);
					preparedStatement.setInt(1, id);
					preparedStatement.setString(2, fname);
					preparedStatement.setString(3, ds);
					preparedStatement.execute();

				} catch (SQLException e) {
					System.err.println(e.getMessage());
				}

			});
		});

	}

	private static HostData hostData = null;
	private static Map<String, String> data = new HashMap<>();
	private static Map<String, String> metricExtractors = new HashMap<>();
	// TODO: initialize
	// {'load_average':'\\s*(?:\\S+)\\s\\s*(?:\\S+)\\s\\s*(?:\\S+)\\s\\s*(?:\\S+)\\s\\s*(\\S+)\\s*',
	// rpm:'\\b(\\d+)\\b', rpm_custom_name:'\\b(\\d+)\\b'}

	private static String[] labelNames = { "instance", "dc", "app", "env" };

	private static String[] metricNames = { "memory", "cpu", "disk",
			"load_average" };

	private static Map<String, String> extractedMetricNames = new HashMap<>();
	// TODO: initialize

	// { 'load_average': 'loadaverage'}

	private static Map<String, List<String>> listFilesDsNames(String path,
			List<String> collectFolders, List<String> rejectFolders)
			throws IOException {

		final List<Path> result = new ArrayList<>();
		final Map<String, List<String>> dsMap = new HashMap<>();
		Path basePath = Paths.get(path);
		final String basePathUri = new URL(
				getDataFileUri(basePath.toAbsolutePath().toString())).getFile() + "/";
		System.err.println("Scanning path: " + basePathUri);
		List<Path> folders = new ArrayList<>();
		// Probably quite sub-optimal
		try (Stream<Path> walk = Files.walk(basePath)) {
			// collect folders
			folders = walk.filter(Files::isDirectory).filter(o -> {
				String key = o.getFileName().toString();
				System.err.println("inspect: " + key);
				boolean status = true;
				if ((collectFolders.size() > 0 && !collectFolders.contains(key))
						|| rejectFolders.size() > 0 && rejectFolders.contains(key)) {
					status = false;
				}
				System.err.println("status: " + status);
				return status;
			}).collect(Collectors.toList());
		}
		// collect files in folders
		for (Path folder : folders) {
			Stream<Path> walk = Files.walk(folder);
			walk.filter(Files::isRegularFile)
					.filter(o -> o.getFileName().toString().matches(filemask))
					.forEach(o -> {
						if (debug)
							System.err.println("found file: " + o.getFileName().toString());
						result.add(o);
					});
		}
		if (noop) {
			// if (debug)
			System.err.println(String.format("Ingesting %d files: ", result.size()));
			result.stream().forEach(o -> {
				String hostname = "hostname";
				hostData = new HostData(hostname, basePath.toAbsolutePath().toString(),
						o.getFileName().toString());

				hostData.setMetrics(Arrays.asList(metricNames));
				if (debug)
					System.err
							.println("about to add data: " + Arrays.asList(metricNames));
				hostData.setExtractedMetricNames(extractedMetricNames);
				hostData.setMetricExtractors(metricExtractors);
				hostData.readData();
				long timestamp = hostData.getTimestamp();
				if (timestamp == 0)
					timestamp = Instant.now().toEpochMilli();
				if (debug)
					System.err.println("adding timestamp: " + timestamp);
				data = hostData.getData();
				if (data != null && !data.isEmpty()) {
					if (debug)
						System.err.println("added data: " + data.keySet());
					data.put("timestamp", Long.toString(timestamp, 10));
					data.put("hostname", hostname);
				} else {
					if (debug)
						System.err.println("data is empty: " + o.getFileName().toString());
				}
			});
			return new HashMap<>();
		}
		result.stream().forEach(o -> {
			try {
				List<String> dsList = new ArrayList<>();
				URL url = new URL(getDataFileUri(o.toAbsolutePath().toString()));
				// NOTE: not the same as filemask
				final String key = url.getFile().replaceFirst(basePathUri, "")
						.replaceAll("/", ":").replaceFirst(".rrd$", "");
				System.err.println("Reading RRD file: " + key);
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
		return dsMap;
	}

	private static Map<String, List<String>> listFilesDsNames(String path)
			throws IOException {
		/*		return listFilesDsNames(path, new ArrayList<String>(),
						new ArrayList<String>());
						*/

		final Map<String, List<String>> dsMap = new HashMap<>();
		Path basePath = Paths.get(path);
		// NOTE: do not use File.separator
		final String basePathUri = new URL(
				getDataFileUri(basePath.toAbsolutePath().toString())).getFile() + "/";
		System.err.println("Scanning path: " + basePathUri);
		// origin:
		// https://github.com/mkyong/core-java/blob/master/java-io/src/main/java/com/mkyong/io/api/FilesWalkExample.java
		// sub-optimal
		List<Path> result;
		List<Path> result2;
		//
		try (Stream<Path> walk = Files.walk(basePath)) {
			result = walk.filter(Files::isRegularFile)
					.filter(o -> o.getFileName().toString().matches(filemask))
					.collect(Collectors.toList());
		}
		// NOTE: streams are not designed to be rescanned
		if (verifylinks) {
			try (Stream<Path> walk = Files.walk(basePath)) {
				result2 = walk.filter(Files::isSymbolicLink).filter(o -> {
					try {
						Path targetPath = Files.readSymbolicLink(o.toAbsolutePath());
						System.err.println("Testing link " + o.getFileName().toString()
								+ " target path " + targetPath.toString());

						File target = new File(String.format("%s/%s",
								o.getParent().toAbsolutePath(), targetPath.toString()));
						if (target.exists() && target.isFile())
							System.err.println("Valid link " + o.getFileName().toString()
									+ " target path " + target.getCanonicalPath());
						return true;
					} catch (IOException e) {
						// fall through
					}
					return false;
				}).filter(o -> o.getFileName().toString().matches(filemask))
						.collect(Collectors.toList());
			}
		}
		// NOTE: streams are not meant to be reused
		if (debug) {
			System.err.println("RRD File paths: "
					+ result.stream().map(o -> o.toAbsolutePath().toString())
							.collect(Collectors.toList()).toString());
			return null;
		} else {
			result.stream().forEach(o -> {
				try {
					List<String> dsList = new ArrayList<>();
					URL url = new URL(getDataFileUri(o.toAbsolutePath().toString()));
					final String key = url.getFile().replaceFirst(basePathUri, "")
							.replaceAll("/", ":").replaceFirst(".rrd$", "");
					System.err.println("Reading RRD file: " + key);
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
			return dsMap;
		}

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

	public static void testJDBCConnection(String vendor) throws Exception {
		if (vendor.equals("mysql")) {
			try {
				// TODO: refactor

				Class driverObject = Class.forName(
						"com.mysql.cj.jdbc.Driver" /* "org.gjt.mm.mysql.Driver" */);
				System.out.println("driverObject=" + driverObject);

				final String url = "jdbc:mysql://" + databaseHost + ":" + databasePort
						+ "/" + database;
				connection = DriverManager.getConnection(url, databaseUser,
						databasePassword);
				if (connection != null) {
					System.out.println("Connected to product: "
							+ connection.getMetaData().getDatabaseProductName());
					System.out
							.println("Connected to catalog: " + connection.getCatalog());
					// System.out.println("Connected to: " + connection.getSchema());
					// java.sql.SQLFeatureNotSupportedException: Not supported
					Statement statement = connection.createStatement();
					statement.setQueryTimeout(30);
					// TODO: check syntax, removed "IF EXISTS"
					String sql = String.format("DROP TABLE %s", databaseTable);
					statement.executeUpdate(sql);

					sql = String.format("CREATE TABLE IF NOT EXISTS %s " + "( "
							+ "id MEDIUMINT PRIMARY KEY NOT NULL AUTO_INCREMENT,"
							+ "ins_date datetime NOT NULL," + "ds VARCHAR(50) NOT NULL, "
							+ "fname VARCHAR(255) NOT NULL,"
							+ "expose VARCHAR(50) DEFAULT NULL " + " )", databaseTable);
					System.out.println("Running SQL: " + sql);
					statement.executeUpdate(sql);

					PreparedStatement preparedStatement = connection
							.prepareStatement(String.format(
									"INSERT INTO %s (ins_date, fname, ds) VALUES (now(), ?, ?)",
									databaseTable));

					preparedStatement.setString(1, "fname");
					preparedStatement.setString(2, "ds0");
					preparedStatement.execute();

					ResultSet resultSet = statement.executeQuery(String
							.format("SELECT id, fname, ds, expose FROM %s", databaseTable));
					while (resultSet.next()) {
						System.out.println("fname = " + resultSet.getString("fname") + "\t"
								+ "ds = " + resultSet.getString("ds") + "\t" + "expose = "
								+ resultSet.getString("expose") + "\t" + "id = "
								+ resultSet.getInt("id"));
					}
					connection.close();
				} else {
					System.out.println("Failed to connect");
				}
			} catch (Exception e) {
				// java.lang.ClassNotFoundException
				System.out.println("Exception: " + e.getMessage());
				e.printStackTrace();
			}
		}
	}

	public static void help() {
		System.err.println("Usage:\n"
				+ "java -cp target/example.rrd-cachedb.jar:target/lib/* example.App --path data --save --file my.db --collect file1,file2 --reject file3,file4");
		System.err
				.println("use --legacy to import metrics from legacy plain text files");
		System.exit(0);
	}

}
