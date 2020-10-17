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

public class Utils {

	private static DumperOptions options = new DumperOptions();
	private static Yaml yaml = null;

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
			System.err.println("Testing local file: " + uri.toString());
			return uri.toString();
		} catch (URISyntaxException e) {
			throw new RuntimeException(e);
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
				System.err.println("Dumping the config to: " + fileName);

				yaml.dump(config, out);
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			System.err.println("Dumped the YAML: \n" + yaml.dump(config));
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
	public static List<Configuration> loadTypedConfiguration(String fileName) {
		init();
		List<Configuration> configurations = new ArrayList<>();
		Configuration configuration = null;
		Map<String, Configuration> data = null;
		try (InputStream in = Files.newInputStream(Paths.get(fileName))) {
			data = yaml.loadAs(in, Map.class);
		} catch (IOException e) {
			e.printStackTrace();
		}
		for (Entry<String, Configuration> rawdata : data.entrySet()) {
			String hostname = rawdata.getKey();
			System.err
					.println(String.format("Processing configration of %s", hostname));
			try {
				configuration = (Configuration) rawdata.getValue();
			} catch (java.lang.ClassCastException e) {
				System.err
						.println(String.format("Processing exception %s", e.toString()));
				// java.lang.ClassCastException:
				// java.util.LinkedHashMap cannot be cast to example.Configuration
				Map<String, Object> value = rawdata.getValue();
				configuration = new Configuration();
				configuration.setEnvironment(value.get("environment").toString());
				configuration.setDatacenter(value.get("datacenter").toString());
				configuration.setRole(value.get("role").toString());
			}
			configuration.setHostname(hostname);
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
