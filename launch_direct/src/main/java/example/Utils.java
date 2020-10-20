package example;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;

import java.net.URI;
import java.net.URISyntaxException;

import java.nio.file.Files;
import java.nio.file.Paths;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;
import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;

public class Utils {

	private static DumperOptions options = new DumperOptions();
	private static Yaml yaml = null;
	private static boolean debug = false;

	protected static String getScriptContent(String scriptName) {
		try {
			final InputStream stream = LaunchDirect.class.getClassLoader()
					.getResourceAsStream(scriptName);
			final byte[] bytes = new byte[stream.available()];
			stream.read(bytes);
			return new String(bytes, "UTF-8");
		} catch (IOException e) {
			throw new RuntimeException(scriptName);
		}
	}

	protected static String getPageContent(String pagename) {
		try {
			URI uri = LaunchDirect.class.getClassLoader().getResource(pagename)
					.toURI();
			if (debug)
				System.err.println("Testing local file: " + uri.toString());
			return uri.toString();
		} catch (URISyntaxException e) {
			throw new RuntimeException(e);
		}
	}

	public static String readProperty(String propertyName, String propertyFile) {
		String resourcePath = "";
		try {
			resourcePath = Thread.currentThread().getContextClassLoader()
					.getResource("").getPath();
			System.err.println(String.format(
					"The running application resource path: \"%s\"", resourcePath));
		} catch (NullPointerException e) {
			System.err.println("Exception (ignored): " + e.toString());
			/*
			 * if (debug) { e.printStackTrace(); }
			 */
		}
		Configuration config = null;
		try {
			config = new PropertiesConfiguration(resourcePath + propertyFile);

			Configuration extConfig = ((PropertiesConfiguration) config)
					.interpolatedConfiguration();
			return extConfig.getProperty(propertyName).toString();
		} catch (ConfigurationException e) {
			return null;
		}
	}

	public static String resolveEnvVars(String input) {
		if (null == input) {
			return null;
		}
		Pattern pattern = Pattern.compile("\\$(?:\\{(?:env:)?(\\w+)\\}|(\\w+))");
		Matcher matcher = pattern.matcher(input);
		StringBuffer sb = new StringBuffer();
		while (matcher.find()) {
			String envVarName = null == matcher.group(1) ? matcher.group(2)
					: matcher.group(1);
			String envVarValue = System.getenv(envVarName);
			matcher.appendReplacement(sb,
					null == envVarValue ? "" : envVarValue.replace("\\", "\\\\"));
		}
		matcher.appendTail(sb);
		return sb.toString();
	}

	public static void saveConfiguration(Object config, String fileName) {
		init();
		if (fileName != null) {
			try {
				Writer out = new OutputStreamWriter(new FileOutputStream(fileName),
						"UTF8");
				if (debug)
					System.err.println("Writing the config to: " + fileName);

				yaml.dump(config, out);
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			if (debug)
				System.err.println("Dumping the config: \n" + yaml.dump(config));
		}
	}

	private static void init() {
		if (yaml == null) {
			options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
			options.setExplicitStart(true);
			yaml = new Yaml(options);
		}
	}

	@SuppressWarnings("unchecked")
	public static LinkedHashMap<String, Map<String, String>> loadConfiguration(
			String fileName) {
		init();
		LinkedHashMap<String, Map<String, String>> config = null;
		try (InputStream in = Files.newInputStream(Paths.get(fileName))) {

			config = (LinkedHashMap<String, Map<String, String>>) yaml.load(in);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return config;

	}

	// TODO
	@SuppressWarnings("unchecked")
	public static List<NodeConfiguration> loadTypedConfiguration(
			String fileName) {
		init();
		List<NodeConfiguration> configurations = new ArrayList<>();
		Map<String, NodeConfiguration> data = null;
		try (InputStream in = Files.newInputStream(Paths.get(fileName))) {
			data = yaml.loadAs(in, Map.class);
		} catch (IOException e) {
			e.printStackTrace();
		}
		for (Entry<String, NodeConfiguration> rawdata : data.entrySet()) {
			Map<String, Object> hostdata = (Map<String, Object>) rawdata.getValue();
			if (debug)
				System.err.println(
						String.format("Processing host %s configration", rawdata.getKey()));

			NodeConfiguration configuration = new NodeConfiguration.Builder()
					.Hostname(rawdata.getKey())
					.Environment(hostdata.get("environment").toString())
					.Datacenter(hostdata.get("datacenter").toString())
					.Role(hostdata.get("role").toString()).build();
			configurations.add(configuration);
		}
		return configurations;
	}

	// name of excel file
	private static String excelFileName = null;

	public static void setExcelFileName(String data) {
		Utils.excelFileName = data;
	}

	private static String sheetFormat = "Excel 2007"; // format of the sheet

	public static void setSheetFormat(String data) {
		Utils.sheetFormat = data;
	}

	// name of the sheet
	private static String sheetName = "Sheet1";

	public static void setSheetName(String data) {
		Utils.sheetName = data;
	}

	// indexed
	private static List<Map<Integer, String>> tableData = new ArrayList<>();

	public static void setTableData(List<Map<Integer, String>> data) {
		tableData = data;
	}

	private static Map<Integer, String> rowData = new HashMap<>();

	public static void writeXLSXFile() throws Exception {

		XSSFWorkbook xssfwb = new XSSFWorkbook();
		XSSFSheet sheet = xssfwb.createSheet(sheetName);
		for (int row = 0; row < tableData.size(); row++) {
			XSSFRow xddfrow = sheet.createRow(row);
			rowData = tableData.get(row);
			for (int col = 0; col < rowData.size(); col++) {
				XSSFCell cell = xddfrow.createCell(col);
				cell.setCellValue(rowData.get(col));
				if (debug)
					System.err
							.println("Writing " + row + " " + col + "  " + rowData.get(col));
			}
		}

		try (OutputStream fileOutputStream = new FileOutputStream(excelFileName)) {

			xssfwb.write(fileOutputStream);
			xssfwb.close();
			fileOutputStream.flush();
			fileOutputStream.close();

		} catch (IOException e) {
			String message = String.format("Exception saving XLSX file %s\n",
					excelFileName) + e.getMessage();
			throw new Exception(message);
		}
	}

}
