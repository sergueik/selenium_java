package com.github.sergueik.iniparser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

// based on https://github.com/RdlP/IniParser/blob/master/IniParser.java

// C# twin is also presented by the same author
// https://github.com/RdlP/IniParser/blob/master/IniParser.cs
// and also
// https://github.com/lukamicoder/IniParser/tree/master/IniParser
// note there exist seriously bloated projects containing *dozens of classes* 
// just to deal with the ini file
// https://github.com/simplesoft-pt/IniParser
// https://github.com/lipkau/PsIni/blob/master/PSIni/Functions/Get-IniContent.ps1
/**
* This class is a very simple ini parser.
*
* In order to write an ini file:
*
* <code>
* IniParser parser = new IniParser();
* parser.addSection("Section1");
* parser.addString("Section1", "test", "Hello");
* parser.writeFile(ini);
* </code>
*
* In order to parse an ini file:
*
* <code>
* IniParser parser = IniParser.getInstance();
* parser.parseFile(path_to_ini_file);
* string s1 = parser.getString("Section1", "test");
* </code>
*
* Ángel Luis.
*/

public final class IniParser {

	private static IniParser instance = new IniParser();
	private boolean debug = false;

	public void setDebug(boolean value) {
		this.debug = value;
	}

	private IniParser() {
	}

	public static IniParser getInstance() {
		return instance;
	}

	// TODO: this is too tight
	private HashMap<String, HashMap<String, Object>> data = new HashMap<String, HashMap<String, Object>>();

	public Map<String, Map<String, Object>> getData() {
		// java Cannot cast from HashMap<String,HashMap<String,Object>> to
		// Map<String,Map<String,Object>>
		return new HashMap<String, Map<String, Object>>();
	}

	/**
	 * This method parse a file given by parameter.
	 *
	 * @param path A string that contains the path of file to parse
	 */
	public void parseFile(String path) {
		try {
			data.clear();
			BufferedReader br = new BufferedReader(new FileReader(path));
			String line, lastSection = "";
			while ((line = br.readLine()) != null) {
				int startSection = line.indexOf('[');
				int endSection = line.indexOf(']');
				if (startSection != -1 && endSection != -1
						&& endSection > startSection) {
					String section = lastSection = line.substring(startSection + 1,
							endSection - startSection);
					data.put(section, new HashMap<String, Object>());
				} else if (line.contains("=") && !line.trim().startsWith(";")) {
					String[] keyValue = line.split("=");
					int hasComments = keyValue[1].trim().indexOf(';');
					if (hasComments == -1) {
						HashMap<String, Object> hash = data.get(lastSection);
						hash.put(keyValue[0].trim(), keyValue[1].trim());
					} else {
						HashMap<String, Object> hash = data.get(lastSection);
						hash.put(keyValue[0].trim(),
								keyValue[1].trim().substring(0, hasComments));
					}
				} else {
					if (line.trim().startsWith(";")) {
						String[] comment = line.split(";");
						HashMap<String, Object> hash = data.get(lastSection);
						hash.put(";", comment[1]);
					}
				}
			}
			br.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * This method will write the data into file given by path.
	 *
	 * @param path A string that contains the path of file
	 */
	public void writeFile(String path) {
		try {
			FileOutputStream outputStreamWriter = new FileOutputStream(path);
			for (Map.Entry<String, HashMap<String, Object>> de : data.entrySet()) {
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
	 * This method will add a section to ini file.
	 *
	 * @param section Name of section.
	 */
	public void addSection(String section) {
		if (!data.containsKey(section)) {
			data.put(section, new HashMap<String, Object>());
		}
	}

	/**
	 * This method will add a string inside section with key <i>key</i> and value <i>value</i>.
	 * If the section doesn't exist it throw ArgumentException.
	 *
	 * @param section Name of section
	 * @param key Name of key
	 * @param value Value of Key
	 */
	public void addString(String section, String key, String value) {
		if (!data.containsKey(section)) {
			throw new IllegalArgumentException(
					"Section " + section + " doesn't exist");
		}
		HashMap<String, Object> hash = data.get(section);
		hash.put(key, value);
	}

	/**
	 * This method will add an integer inside section with key <i>key</i> and value <i>value</i>.
	 * If the section doesn't exist it throw ArgumentException.
	 *
	 * @param section Name of section
	 * @param key Name of key
	 * @param value Value of Key
	 */
	public void addInteger(String section, String key, int value) {
		if (!data.containsKey(section)) {
			throw new IllegalArgumentException(
					"Section " + section + " doesn't exist");
		}
		HashMap<String, Object> hash = data.get(section);
		hash.put(key, value);
	}

	/**
	 * This method will add a float inside section with key <i>key</i> and value <i>value</i>.
	 * If the section doesn't exist it throw ArgumentException.
	 *
	 * @param section Name of section
	 * @param key Name of key
	 * @param value Value of Key
	 */
	public void addFloat(String section, String key, float value) {
		if (!data.containsKey(section)) {
			throw new IllegalArgumentException(
					"Section " + section + " doesn't exist");
		}
		HashMap<String, Object> hash = data.get(section);
		hash.put(key, value);
	}

	/**
	 * This method will add a double inside section with key <i>key</i> and value <i>value</i>.
	 * If the section doesn't exist it throw ArgumentException.
	 *
	 * @param section Name of section
	 * @param key Name of key
	 * @param value Value of Key
	 */
	public void addDouble(String section, String key, double value) {
		if (!data.containsKey(section)) {
			throw new IllegalArgumentException(
					"Section " + section + " doesn't exist");
		}
		HashMap<String, Object> hash = data.get(section);
		hash.put(key, value);
	}

	/**
	 * This method will add a boolean inside section with key <i>key</i> and value <i>value</i>.
	 * If the section doesn't exist it throw ArgumentException.
	 *
	 * @param section Name of section
	 * @param key Name of key
	 * @param value Value of Key
	 */
	public void addBoolean(String section, String key, boolean value) {
		if (!data.containsKey(section)) {
			throw new IllegalArgumentException(
					"Section " + section + " doesn't exist");
		}
		HashMap<String, Object> hash = data.get(section);
		hash.put(key, value);
	}

	/**
	 * This method return a string inside section <i>section</i> with key <i>key</i>.
	 * If the section or key doesn't exist it throw IllegalArgumentException.
	 *
	 * @param section Name of section
	 * @param key Name of Key
	 *
	 * @return Value of Key
	 */
	public String getString(String section, String key) {
		if (!data.containsKey(section)) {
			throw new IllegalArgumentException(
					"Section " + section + " doesn't exist");
		}
		HashMap<String, Object> hash = data.get(section);
		if (!hash.containsKey(key)) {
			throw new IllegalArgumentException("Key " + key + " doesn't exist");
		}
		return hash.get(key).toString();

	}

	/**
	 * This method return an integer inside section <i>section</i> with key <i>key</i>.
	 * If the section or key doesn't exist it throw IllegalArgumentException.
	 *
	 * @param section Name of section
	 * @param key Name of Key
	 *
	 * @return Value of Key
	 */
	public int getInteger(String section, String key) {
		if (!data.containsKey(section)) {
			throw new IllegalArgumentException(
					"Section " + section + " doesn't exist");
		}
		HashMap<String, Object> hash = data.get(section);
		if (!hash.containsKey(key)) {
			throw new IllegalArgumentException("Key " + key + " doesn't exist");
		}

		int num = Integer.parseInt(hash.get(key).toString());
		return num;
	}

	/**
	 * This method return a float inside section <i>section</i> with key <i>key</i>.
	 * If the section or key doesn't exist it throw IllegalArgumentException.
	 *
	 * @param section Name of section
	 * @param key Name of Key
	 *
	 * @return Value of Key
	 */
	public float getFloat(String section, String key) {
		if (!data.containsKey(section)) {
			throw new IllegalArgumentException(
					"Section " + section + " doesn't exist");
		}
		HashMap<String, Object> hash = data.get(section);
		if (!hash.containsKey(key)) {
			throw new IllegalArgumentException("Key " + key + " doesn't exist");
		}

		float num = Float.parseFloat(hash.get(key).toString());
		return num;
	}

	/**
	 * This method return a double inside section <i>section</i> with key <i>key</i>.
	 * If the section or key doesn't exist it throw IllegalArgumentException.
	 *
	 * @param section Name of section
	 * @param key Name of Key
	 *
	 * @return Value of Key
	 */
	public double getDouble(String section, String key) {
		if (!data.containsKey(section)) {
			throw new IllegalArgumentException(
					"Section " + section + " doesn't exist");
		}
		HashMap<String, Object> hash = data.get(section);
		if (!hash.containsKey(key)) {
			throw new IllegalArgumentException("Key " + key + " doesn't exist");
		}

		double num = Double.parseDouble(hash.get(key).toString());
		return num;
	}

	/**
	 * This method return a boolean inside section <i>section</i> with key <i>key</i>.
	 * If the section or key doesn't exist it throw IllegalArgumentException.
	 *
	 * @param section Name of section
	 * @param key Name of Key
	 *
	 * @return Value of Key
	 */
	public boolean getBoolean(String section, String key) {
		if (!data.containsKey(section)) {
			throw new IllegalArgumentException(
					"Section " + section + " doesn't exist");
		}
		HashMap<String, Object> hash = data.get(section);
		if (!hash.containsKey(key)) {
			throw new IllegalArgumentException("Key " + key + " doesn't exist");
		}
		return ((String) hash.get(key)).toLowerCase() == "true" ? true : false;
	}

}
