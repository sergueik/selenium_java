package example;

import java.io.File;
import java.io.FileWriter;
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
import java.util.Properties;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

import example.dao.JDBCDao;
import example.projection.ServerInstanceApplication;
import example.utils.Utils;

public class App {

	private static Utils utils = Utils.getInstance();
	private static final String filemask = "data.txt.*$";
	private static JDBCDao dao = new JDBCDao();

	private static final Random randomId = new Random();
	private static Connection connection = null;
	private static String osName = utils.getOSName();
	public static final int INVALID_OPTION = 42;

	final static Map<String, String> env = System.getenv();

	private static List<Map<String, String>> metricsData = new ArrayList<>();
	private static String[] headers = { "hostname", "timestamp", "memory", "cpu",
			"disk", "load_average" };
	private static List<List<Object>> csvData = new ArrayList<>();

	private static boolean debug = false;
	private static boolean merge = false;
	private static boolean save = false;
	private static boolean query = false;
	private static boolean verifylinks = false;

	private static String databaseHost = null;
	private static String database = null;
	private static int databasePort = 3306;
	private static String sqliteDatabaseName = "cache.db";
	private static String csvFileName = "dump.csv";
	private static String databaseTable = "metric_table";
	private static String linkedDataDir = null;

	private static String databaseUser = null;
	private static String databasePassword = null;
	private static String vendor = null;
	private final static Options options = new Options();
	private static CommandLineParser commandLineparser = new DefaultParser();
	private static CommandLine commandLine = null;

	private static String hostname = null;
	private static HostData hostData = null;
	private static Map<String, String> data = new HashMap<>();
	private static Map<String, String> metricExtractors = new HashMap<>();

	static {
		metricExtractors.put("load_average",
				"\\s*(?:\\S+)\\s\\s*(?:\\S+)\\s\\s*(?:\\S+)\\s\\s*(?:\\S+)\\s\\s*(\\S+)\\s*");
		metricExtractors.put("rpm", "\\b(\\d+)\\b");
	}

	private static String[] labelNames = { "instance", "dc", "app", "env" };

	private static String[] metricNames = { "memory", "cpu", "disk",
			"load_average" };

	private static Map<String, String> extractedMetricNames = new HashMap<>();
	// TODO: initialize
	// { 'load_average': 'loadaverage'}
	private static Properties propertiesObject;

	public static void main(String args[]) throws ParseException {
		
		// loads propertiesMapCache
		// NOTE: cannot prepend with (void) leads to vague error
		// "Type mismatch: cannot convert from void to boolean"
		utils.getProperties("application.properties");

		
		propertiesObject = utils.getPropertiesObject();
		options.addOption("h", "help", false, "help");
		options.addOption("d", "debug", false, "debug");
		options.addOption("m", "merge", false, "merge");

		options.addOption("s", "save", false, "save");
		// NOTE: in few revisions, the same single letter option
		// for "query" and "vendor"
		options.addOption("q", "query", false, "query");
		options.addOption("p", "path", true, "path to scan");

		options.addOption("o", "link", true, "linked data dir");
		options.addOption("x", "hostname", true, "hostname");
		options.addOption("f", "file", true, "sqlite database filename to write");

		options.addOption("b", "vendor", true,
				"database kind. surrently supported sqlite and mysql (partially)");
		options.addOption("с", "csv", true, "csv dump filename to write");
		options.addOption("v", "verifylinks", false,
				"verify file links that are found during scan");
		options.addOption("r", "reject", true, "folder(s) to reject");
		options.addOption("i", "collect", true, "folder(s) to collect");

		options.addOption("x", "host", true, "database host");
		options.addOption("y", "port", true, "database port");
		options.addOption("w", "database", true, "database");
		options.addOption("u", "user", true, "database user");
		options.addOption("t", "table", true, "database table");
		options.addOption("z", "password", true, "database password");

		commandLine = commandLineparser.parse(options, args);
		if (commandLine.hasOption("h")) {
			help();
		}
		if (commandLine.hasOption("d")) {
			debug = true;
		}

		if (commandLine.hasOption("merge")) {
			merge = true;

			moveDataToCache();
			return;
		}

		if (commandLine.hasOption("query")) {
			query = true;
		}
		if (commandLine.hasOption("verifylinks")) {
			verifylinks = true;
		}
		if (commandLine.hasOption("link")) {
			linkedDataDir = commandLine.getOptionValue("link");
		} else {
			linkedDataDir = null;
		}

		if (commandLine.hasOption("csv")) {
			csvFileName = commandLine.getOptionValue("csv");
		}

		if (commandLine.hasOption("save")) {
			save = true;
		}

		// NOTE: some challenge with hostname argument added to within some other
		// argument check
		hostname = commandLine.getOptionValue("x");
		if (hostname == null) {
			System.err.println("Missing argument: hostname. Using default");
			hostname = "hostname";
		} else {
			System.err.println("hostname: " + hostname);
		}

		vendor = commandLine.getOptionValue("vendor");
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
				databaseTable = "metric_table";
			}

			databasePassword = commandLine.getOptionValue("password");
			if (databasePassword == null) {
				System.err.println("Missing argument: databasePassword. Using default");
				databasePassword = "password";
			}
			try {
				testJDBCConnection(vendor);
			} catch (Exception e) {
				System.err.println("Exception (ignored)" + e.toString());
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
				metricsData = listFilesDsNames(path, collectFolders, rejectFolders);
			} else {
				metricsData = listFilesDsNames(path);
			}
		} catch (IOException e) {
		}

		if (save) {
			// TODO: refactoring needed - the connection is not open when doing SQLite
			if (!(vendor.equals("mysql"))) {
				// createTableCommon();
				createTableForLegacyData();

			}

			//
			// saveLegacyData(metricsData);
			saveLegacyDataBatch(metricsData);
		}
		if (query) {
			displayLegacyData();
			try {
				createCSVFile();
			} catch (IOException e) {
				// ignore
			}

		}

		if (debug) {
			System.err.println("Done: " + path);
		}

	}

	public static void createCSVFile() throws IOException {
		FileWriter out = new FileWriter(csvFileName);
		try (@SuppressWarnings("deprecation")
		CSVPrinter printer = new CSVPrinter(out,
				CSVFormat.DEFAULT.withHeader(headers))) {
			csvData.forEach((List<Object> row) -> {
				try {
					printer.printRecord(row);
				} catch (IOException e) {
					// ignore
				}
			});
		}
	}

	private static void displayLegacyData() {

		try {
			System.err.println(
					"Querying data : " + connection.getMetaData().getDatabaseProductName()
							+ "\t" + "catalog: " + connection.getCatalog() + "\t" + "schema: "
							+ connection.getSchema());
			Statement statement = connection.createStatement();
			statement.setQueryTimeout(30);
			String query = String.format(
					"SELECT DISTINCT hostname" + "," + "timestamp" + "," + "memory" + ","
							+ "cpu" + "," + "disk" + ","
							+ "load_average FROM %s ORDER BY hostname, timestamp",
					databaseTable);
			System.err.println("query: " + query);
			ResultSet rs = statement.executeQuery(query);
			while (rs.next()) {

				csvData.add(Arrays.asList(
						new Object[] { rs.getString("hostname"), rs.getString("timestamp"),
								rs.getString("disk"), rs.getString("cpu"),
								rs.getString("memory"), rs.getString("load_average") }));
				if (debug)
					System.err.println("hostname = " + rs.getString("hostname") + "\t"
							+ "timestamp = " + rs.getString("timestamp") + "\t" + "disk = "
							+ rs.getString("disk") + "\t" + "cpu = " + rs.getString("cpu")
							+ "\t" + "memory = " + rs.getString("memory") + "\t"
							+ "load_average = " + rs.getString("load_average"));
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
		createTableForLegacyData("metric_table.sql");
	}

	private static void createTableForLegacyData(
			String schemaDefinitionFilename) {
		try {
			createTableCommon();
			Statement statement = connection.createStatement();
			statement.setQueryTimeout(30);
			String sql = utils.getScriptContent(schemaDefinitionFilename);
			if (debug)
				System.err.println("Running SQL: " + sql);
			statement.executeUpdate(sql);
			statement.close();

		} catch (SQLException e) {
			System.err.println("Exception processing " + schemaDefinitionFilename
					+ " (ignored)" + e.getMessage());
		} catch (Exception e) {
			System.err
					.println("Unexpected exception processing " + schemaDefinitionFilename
							+ " " + e.getClass().getName() + ": " + e.getMessage());
			System.exit(1);
		}
	}

	private static void createTableCommon() {
		connection = null;

		final String databasePath = String.format("%s%s%s",
				env.get(osName.equals("windows") ? "USERPROFILE" : "HOME"),
				File.separator, sqliteDatabaseName);
		try {
			/*
			Class driverObject = Class
					.forName(prop.getProperty("datasource.driver-class-name",
							"com.mysql.cj.jdbc.Driver" ));
			System.err.println("Get driverObject=" + driverObject);
			*/
			// TODO: fix
			// Exception (ignored)invalid database address:
			// jdbc:mysql://192.168.0.29:3306/test
			// NOTE: does not have to be in memory, can be persisted to disk during
			// development
			String connectionUrl = utils
					.resolveEnvVars(propertiesObject.getProperty("cache.datasource.url",
							"jdbc:sqlite:" + databasePath.replaceAll("\\\\", "/")));
			System.out.println("Opening database connection: " + connectionUrl);
			connection = DriverManager.getConnection(connectionUrl);
			System.out
					.println("Opened database connection successfully: " + connectionUrl);

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

	// origin:
	// https://stackoverflow.com/questions/3784197/efficient-way-to-do-batch-inserts-with-jdbc
	private static void saveLegacyDataBatch(
			List<Map<String, String>> metricsData) {
		System.err.println("Batch saving data");
		/*
		try {
			Statement statement = connection.createStatement();
			statement.setQueryTimeout(30);
			statement.executeUpdate("delete from " + databaseTable);
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
		*/
		try {

			// https://www.sqlite.org/datatype3.html
			// java has no unsigned long type, you can treat signed 64-bit
			// two's-complement integers (i.e. long values) as unsigned if you are
			// careful about it
			String sql = vendor.equals("mysql")
					? String.format(
							"INSERT INTO %s " + "( " + "`id`" + "," + "`hostname`" + ","
									+ "`timestamp`" + "," + "`memory`" + "," + "`cpu`" + ","
									+ "`disk`" + "," + "`load_average`" + ")"
									+ " VALUES (?, ?, FROM_UNIXTIME(?), ?, ?, ?, ?);",
							databaseTable)
					: String.format("INSERT INTO %s " + "( " + "`id`" + "," + "`hostname`"
							+ "," + "`timestamp`" + "," + "`memory`" + "," + "`cpu`" + ","
							+ "`disk`" + "," + "`load_average`" + ")"
							+ " VALUES (?, ?, ?, ?, ?, ?, ?);", databaseTable);
			// connection.setAutoCommit(false);
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			// NOTE: optional
			preparedStatement.clearParameters();
			metricsData.stream().forEach(row -> {
				String hostname = row.get("hostname");
				String timestamp = row.get("timestamp");
				String memory = row.get("memory");
				String cpu = row.get("cpu");
				String disk = row.get("disk");
				String load_average = row.get("load_average");
				if (debug)
					System.err.println(
							"about to insert data row: " + Arrays.asList(new String[] {
									hostname, timestamp, memory, cpu, disk, load_average }));

				long id = randomId.nextLong();
				try {
					preparedStatement.setLong(1, id);
					preparedStatement.setString(2, hostname);
					preparedStatement.setLong(3, Long.parseLong(timestamp));
					preparedStatement.setFloat(4, Float.parseFloat(memory));
					preparedStatement.setFloat(5, Float.parseFloat(cpu));
					preparedStatement.setFloat(6, Float.parseFloat(disk));
					preparedStatement.setFloat(7, Float.parseFloat(load_average));
					preparedStatement.addBatch();
				} catch (SQLException e) {
					// Values not bound to statement
					System.err
							.println("Error in preparing batch statement: " + e.getMessage());
				}
			});
			preparedStatement.executeBatch();
			System.err.println("updated " + preparedStatement.getUpdateCount());
		} catch (SQLException e) {
			System.err
					.println("Error in executing batch statement: " + e.getMessage());
		}
	}

	// NOTE: largely a replica of "saveData"
	private static void saveLegacyData(List<Map<String, String>> metricsData) {
		System.err.println("Saving data");
		/*
		try {
			Statement statement = connection.createStatement();
			statement.setQueryTimeout(30);
			statement.executeUpdate("delete from " + databaseTable);
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
		*/
		metricsData.stream().forEach(row -> {
			String hostname = row.get("hostname");
			String timestamp = row.get("timestamp");
			String memory = row.get("memory");
			String cpu = row.get("cpu");
			String disk = row.get("disk");
			String load_average = row.get("load_average");
			if (debug)
				System.err
						.println("about to insert data row: " + Arrays.asList(new String[] {
								hostname, timestamp, memory, cpu, disk, load_average }));

			try {

				// https://www.sqlite.org/datatype3.html
				// java has no unsigned long type, you can treat signed 64-bit
				// two's-complement integers (i.e. long values) as unsigned if you are
				// careful about it
				String sql = vendor.equals("mysql")
						? String.format(
								"INSERT INTO %s " + "( " + "`id`" + "," + "`hostname`" + ","
										+ "`timestamp`" + "," + "`memory`"
										+ "," + "`cpu`" + "," + "`disk`" + "," + "`load_average`"
										+ ")" + " VALUES (?, ?, FROM_UNIXTIME(?), ?, ?, ?, ?);",
								databaseTable)
						: String.format("INSERT INTO %s " + "( " + "`id`" + ","
								+ "`hostname`" + "," + "`timestamp`" + "," + "`memory`" + ","
								+ "`cpu`" + "," + "`disk`" + "," + "`load_average`" + ")"
								+ " VALUES (?, ?, ?, ?, ?, ?, ?);", databaseTable);
				PreparedStatement preparedStatement = connection.prepareStatement(sql);

				long id = randomId.nextLong();
				// NOTE: nextLong is a no arg method - cannot supply scale like with
				// nextInt()
				// 1_000_000_000_000L
				// can use ThreadLocalRandom.current().nextLong(n)
				// see also:
				// https://stackoverflow.com/questions/2546078/java-random-long-number-in-0-x-n-range
				preparedStatement.setLong(1, id);
				preparedStatement.setString(2, hostname);
				// NOTE:
				// Exception in thread "main" java.lang.NumberFormatException:
				// For input string: "1656475200000"
				// influxDB needs nanosecond, Prometheus millisecond
				preparedStatement.setLong(3, Long.parseLong(timestamp));
				preparedStatement.setFloat(4, Float.parseFloat(memory));
				preparedStatement.setFloat(5, Float.parseFloat(cpu));
				preparedStatement.setFloat(6, Float.parseFloat(disk));
				preparedStatement.setFloat(7, Float.parseFloat(load_average));
				preparedStatement.execute();
			} catch (SQLException e) {
				System.err.println(e.getMessage());
			}
		});

	}

	private static List<Map<String, String>> listFilesDsNames(String path,
			List<String> collectFolders, List<String> rejectFolders)
			throws IOException {

		final List<Path> result = new ArrayList<>();
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
				// NOTE: exact match required
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

		return readFiles(result);
	}

	private static List<Map<String, String>> readFiles(List<Path> result) {
		List<Map<String, String>> results = new ArrayList<>();
		System.err.println(String.format("Ingesting %d files: ", result.size()));
		result.stream().forEach(o -> {
			hostData = new HostData(hostname,
					o.getParent().toAbsolutePath().toString(),
					o.getFileName().toString());
			// sync debug settings
			hostData.setDebug(debug);
			// NOTE: metricNames are used in SQL insert when processing metricsData
			hostData.setMetrics(Arrays.asList(metricNames));
			if (debug)
				System.err.println("about to add data: " + Arrays.asList(metricNames));
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
				results.add(data);
			} else {
				if (debug)
					System.err.println("data is empty: " + o.getFileName().toString());
			}
		});
		return results;
	}

	// NOTE: not reducing to calling the other method with a empty argument
	// return listFilesDsNames(
	// path,
	// new ArrayList<String>(),
	// new ArrayList<String>());
	private static List<Map<String, String>> listFilesDsNames(String path)
			throws IOException {

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
		if (verifylinks) {
			try (Stream<Path> walk = Files.walk(basePath)) {
				result2 = walk.filter(Files::isSymbolicLink).filter(o -> {
					try {
						Path targetPath = Files.readSymbolicLink(o.toAbsolutePath());
						if (debug)
							System.err.println("Testing link " + o.getFileName().toString()
									+ " target path " + targetPath.toString());

						File target = new File(
								String.format(
										"%s/%s", (linkedDataDir == null
												? o.getParent().toAbsolutePath() : linkedDataDir),
										targetPath.toString()));
						if (target.exists() && target.isFile())
							if (debug)
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
			return readFiles(result2);
		} else {
			try (Stream<Path> walk = Files.walk(basePath)) {
				result = walk.filter(Files::isRegularFile)
						.filter(o -> o.getFileName().toString().matches(filemask))
						.collect(Collectors.toList());
			}
			// NOTE: streams are not designed to be rescanned
			return readFiles(result);
		}

	}

	private static String getDataFileUri(String dataFilePath) {
		return osName.equals("windows")
				? "file:///" + dataFilePath.replaceAll("\\\\", "/")
				: "file://" + dataFilePath;
	}

	public static void testJDBCConnection(String vendor) throws Exception {
		System.err.println("testJDBCConnection for vendor: " + vendor);
		if (vendor.equals("mysql")) {
			try {
				// TODO: refactor

				Class driverObject = Class
						.forName(propertiesObject.getProperty("datasource.driver-class-name",
								"com.mysql.cj.jdbc.Driver" /* "org.gjt.mm.mysql.Driver" */));
				System.err.println("driverObject=" + driverObject);

				final String url = utils
						.resolveEnvVars(propertiesObject.getProperty("datasource.url", "jdbc:mysql://"
								+ databaseHost + ":" + databasePort + "/" + database));
				connection = DriverManager.getConnection(url,
						propertiesObject.getProperty("datasource.username", databaseUser),
						propertiesObject.getProperty("datasource.password", databasePassword));
				if (connection != null) {
					System.out.println("Connected to product: "
							+ connection.getMetaData().getDatabaseProductName());
					System.out
							.println("Connected to catalog: " + connection.getCatalog());
					// System.out.println("Connected to: " + connection.getSchema());
					// java.sql.SQLFeatureNotSupportedException: Not supported
					// connection.close();
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

	private static void moveDataToCache() {
		@SuppressWarnings("unchecked")
		// NOTE inventory uses separate connection
		// receive stronglty typed JPA style object list
		List<ServerInstanceApplication> servers = (List<ServerInstanceApplication>) dao
				.findAllServerInstanceApplication();

		createTableForLegacyData("host_inventory.sql");
		System.err.println("Merging server metadata");
		String databaseTable = "host_inventory";
		try {
			Statement statement = connection.createStatement();
			statement.setQueryTimeout(30);
			statement.executeUpdate("delete from " + databaseTable);
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}

		servers.stream().forEach(server -> {
			String serverName = server.getServerName();
			String instanceName = server.getInstanceName();
			String applicationName = server.getApplicationName();
			if (debug)
				System.err.println("ServerInstanceApplication " + "[ " + "serverName = "
						+ server.getServerName() + ", " + "instanceName = "
						+ server.getInstanceName() + ", " + "applicationName = "
						+ server.getApplicationName() + " ]");

			try {

				String sql = String.format("INSERT INTO %s " + "( " + "`id`" + ","
						+ "`server`" + "," + "`instance`" + "," + "`application`" + ")"
						+ " VALUES (?, ?, ?, ?);", databaseTable);
				PreparedStatement preparedStatement = connection.prepareStatement(sql);

				long id = randomId.nextLong();
				// NOTE: nextLong is a no arg method - cannot supply scale like with
				// nextInt()
				// 1_000_000_000_000L
				// can use ThreadLocalRandom.current().nextLong(n)
				// see also:
				// https://stackoverflow.com/questions/2546078/java-random-long-number-in-0-x-n-range
				preparedStatement.setLong(1, id);
				preparedStatement.setString(2, serverName);
				preparedStatement.setString(3, instanceName);
				preparedStatement.setString(4, applicationName);
				preparedStatement.execute();
			} catch (SQLException e) {
				System.err.println(e.getMessage());
			}
		});
	}

	public static void help() {
		System.err.println("Usage:\n"
				+ "java -cp target/example.datatxt-cachedb.jar:target/lib/* example.App --path data --save --file my.db --collect file1,file2 --reject file3,file4");
		System.err
				.println("use --legacy to import metrics from legacy plain text files");
		System.exit(0);
	}

}
