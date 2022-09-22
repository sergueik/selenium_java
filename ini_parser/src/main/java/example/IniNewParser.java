package example;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*
 * @author Ángel Luis (angelluis.perales@um.es) 
 * @author Serguei Kouzmine (kouzmine_serguei@yahoo.com)
 */

public final class IniNewParser {

	private static IniNewParser instance = new IniNewParser();
	private boolean debug = false;

	public void setDebug(boolean value) {
		this.debug = value;
	}

	private IniNewParser() {
	}

	public static IniNewParser getInstance() {
		return instance;
	}

	// TODO: this is too tight
	private Map<String, Map<String, Object>> data = new HashMap<>();

	public Map<String, Map<String, Object>> getData() {
		return data;
	}

	/**
	 * Parse ini file <i>filePath</i>
	 * @param filePath Input file path
	 */
	public void parseFile(String filePath) {
		try {
			data.clear();
			BufferedReader br = new BufferedReader(new FileReader(filePath));
			String line = "";
			StringBuilder stringBuilder = new StringBuilder();
			String lineSeparator = System.getProperty("line.separator");
			while ((line = br.readLine()) != null) {
				stringBuilder.append(line);
				stringBuilder.append(lineSeparator);
			}
			String fileContents = stringBuilder.toString();
			// positive lookahead keeping the section title and data together
			String iniSectionPattern = "\\[(?=[^]]+\\])";
			String[] iniSections = fileContents.split(iniSectionPattern);

			// NOTE the "Count of sections" will be 1 too many
			System.err.println("Count of sections = " + iniSections.length);
			// String sectionHeaderPattern = "^(.+)\\]\\n.*$";
			// Pattern pattern = Pattern.compile(sectionHeaderPattern,
			// Pattern.CASE_INSENSITIVE | Pattern.MULTILINE);

			for (String iniSection : iniSections) {
				// TODO: use matched to capture section key and data
				// Matcher matcher = pattern.matcher(iniSection);
				String[] parts = iniSection.split("\\]");
				if (parts.length != 0 && !parts[0].isEmpty()) {
					String section = parts[0];
					String rawData = parts[1];
					data.put(section, new HashMap<String, Object>());
					System.err.println(
							String.format("Section: %s\nData:\n----%s\n", section, rawData));
					for (String dataLine : rawData.split("\\r?\\n")) {
						if (dataLine.trim().startsWith(";")) {
							continue;
						}
						if (dataLine.contains("=")) {
							String[] entryParts = dataLine.split("=");
							Map<String, Object> hash = data.get(section);
							// TODO: better process comments
							String entryKey = entryParts[0].trim();
							String entryValue = entryParts[1].trim();
							int hasComments = entryValue.indexOf(';');
							if (hasComments > -1) {
								hash.put(entryKey, entryValue.substring(0, hasComments));
							} else {
								hash.put(entryKey, entryValue);
							}
						}
					}
				}
			}
			br.close();

		} catch (

		FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Write the data into file <i>filePath</i>
	 *
	 * @param filePath Target file path
	 */
	public void writeFile(String filePath) {
		try {
			FileOutputStream outputStreamWriter = new FileOutputStream(filePath);
			for (Map.Entry<String, Map<String, Object>> de : data.entrySet()) {
				String line = "[" + de.getKey() + "]\n";

				outputStreamWriter.write(line.getBytes());

				for (Map.Entry<String, Object> d : de.getValue().entrySet()) {
					if (!d.getKey().equals(";")) {
						line = d.getKey() + "=" + d.getValue() + "\n";
					} else {
						line = d.getKey() + d.getValue() + "\n";
					}
					outputStreamWriter.write(line.getBytes());
				}
			}
			outputStreamWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * add a section <i>section</i> 
	 *
	 * @param section Name of section.
	 */
	public void addSection(String section) {
		if (!data.containsKey(section)) {
			data.put(section, new HashMap<String, Object>());
		}
	}

	/**
	 * add an string valued entry inside section <i>section</i> with key <i>key</i>
	 * throws ArgumentException when section <i>section</i> not found 
	 *
	 * @param section Section name
	 * @param key Entry name
	 * @param value Entry value
	 */
	public void addString(String section, String key, String value) {
		if (!data.containsKey(section)) {
			throw new IllegalArgumentException("Section doesn't exist: " + section);
		}
		Map<String, Object> hash = data.get(section);
		hash.put(key, value);
	}

	/**
	 * add an integer valued entry inside section <i>section</i> with key <i>key</i>
	 * throws ArgumentException when section <i>section</i> not found 
	 *
	 * @param section Section name
	 * @param key Entry name
	 * @param value Entry value
	 */
	public void addInteger(String section, String key, int value) {
		if (!data.containsKey(section)) {
			throw new IllegalArgumentException("Section doesn't exist: " + section);
		}
		Map<String, Object> hash = data.get(section);
		hash.put(key, value);
	}

	/**
	 * add an float valued entry inside section <i>section</i> with key <i>key</i>
	 * throws ArgumentException when section <i>section</i> not found 
	 *
	 * @param section Section name
	 * @param key Entry name
	 * @param value Entry value
	 */
	public void addFloat(String section, String key, float value) {
		if (!data.containsKey(section)) {
			throw new IllegalArgumentException("Section doesn't exist: " + section);
		}
		Map<String, Object> hash = data.get(section);
		hash.put(key, value);
	}

	/**
	 * add an double valued entry inside section <i>section</i> with key <i>key</i>
	 * throws ArgumentException when section <i>section</i> not found 
	 *
	 * @param section Section name
	 * @param key Entry name
	 * @param value Entry value
	 */
	public void addDouble(String section, String key, double value) {
		if (!data.containsKey(section)) {
			throw new IllegalArgumentException("Section doesn't exist: " + section);
		}
		Map<String, Object> hash = data.get(section);
		hash.put(key, value);
	}

	/**
	 * add an boolean valued entry inside section <i>section</i> with key <i>key</i>
	 * throws ArgumentException when section <i>section</i> not found 
	 *
	 * @param section Section name
	 * @param key Entry name
	 * @param value Entry value
	 */
	public void addBoolean(String section, String key, boolean value) {
		if (!data.containsKey(section)) {
			throw new IllegalArgumentException("Section doesn't exist: " + section);
		}
		Map<String, Object> hash = data.get(section);
		hash.put(key, value);
	}

	/**
	 * return a string inside section <i>section</i> with key <i>key</i>
	 * throws ArgumentException when section <i>section</i> not found 
	 *
	 * @param section Section name
	 * @param key Entry name
	 *
	 * @return Value of entry
	 */
	public String getString(String section, String key) {
		if (!data.containsKey(section)) {
			throw new IllegalArgumentException("Section doesn't exist: " + section);
		}
		Map<String, Object> hash = data.get(section);
		if (!hash.containsKey(key)) {
			throw new IllegalArgumentException("Key doesn't exist: " + key);
		}
		String result = hash.get(key).toString();
		// special case of empty string
		result = result.matches("^\"\"$") ? "" : result;
		if (debug) {
			System.err
					.println("Raw value: " + hash.get(key) + "\treturning: " + result);
		}
		return result;
	}

	/**
	 * return integer inside section <i>section</i> with key <i>key</i>
	 * throws ArgumentException when section <i>section</i> not found
	 *
	 * @param section Section name
	 * @param key Entry name
	 *
	 * @return Value of entry
	 */
	public int getInteger(String section, String key) {
		if (!data.containsKey(section)) {
			throw new IllegalArgumentException("Section doesn't exist: " + section);
		}
		Map<String, Object> hash = data.get(section);
		if (!hash.containsKey(key)) {
			throw new IllegalArgumentException("Key doesn't exist: " + key);
		}

		int result = Integer.parseInt(hash.get(key).toString());
		if (debug) {
			System.err
					.println("Raw value: " + hash.get(key) + "\treturning: " + result);
		}
		return result;
	}

	/**
	 * return float inside section <i>section</i> with key <i>key</i>
	 * throws ArgumentException when section <i>section</i> not found
	 *
	 * @param section Name of section
	 * @param key Name of Key
	 *
	 * @return Value of entry
	 */
	public float getFloat(String section, String key) {
		if (!data.containsKey(section)) {
			throw new IllegalArgumentException("Section doesn't exist: " + section);
		}
		Map<String, Object> hash = data.get(section);
		if (!hash.containsKey(key)) {
			throw new IllegalArgumentException("Key doesn't exist: " + key);
		}

		float result = Float.parseFloat(hash.get(key).toString());
		if (debug) {
			System.err
					.println("Raw value: " + hash.get(key) + "\treturning: " + result);
		}
		return result;
	}

	/**
	 * return double inside section <i>section</i> with key <i>key</i>
	 * throws ArgumentException when section <i>section</i> not found
	 *
	 * @param section Name of section
	 * @param key Name of Key
	 *
	 * @return Value of entry
	 */
	public double getDouble(String section, String key) {
		if (!data.containsKey(section)) {
			throw new IllegalArgumentException("Section doesn't exist: " + section);
		}
		Map<String, Object> hash = data.get(section);
		if (!hash.containsKey(key)) {
			throw new IllegalArgumentException("Key doesn't exist: " + key);
		}

		double result = Double.parseDouble(hash.get(key).toString());
		if (debug) {
			System.err
					.println("Raw value: " + hash.get(key) + "\treturning: " + result);
		}
		return result;
	}

	/**
	 * return a boolean inside section <i>section</i> with key <i>key</i>
	 * throws ArgumentException when section <i>section</i> not found
	 *
	 * @param section Name of section
	 * @param key Name of Key
	 *
	 * @return Value of entry
	 */
	public boolean getBoolean(String section, String key) {
		if (!data.containsKey(section)) {
			throw new IllegalArgumentException("Section doesn't exist: " + section);
		}
		Map<String, Object> hash = data.get(section);
		if (!hash.containsKey(key)) {
			throw new IllegalArgumentException("Key doesn't exist: " + key);
		}
		boolean result = ((String) hash.get(key)).toLowerCase().equals("true")
				? true : false;
		if (debug) {
			System.err
					.println("Raw value: " + hash.get(key) + "\treturning: " + result);
		}
		return result;
	}

}
