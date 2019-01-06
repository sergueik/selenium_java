package com.github.sergueik.testng.utils;

import java.util.Random;

import org.testng.SkipException;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;

import java.io.IOException;
import java.io.InputStream;

import java.nio.file.Files;
import java.nio.file.Paths;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

public class TestRandomizer {

	private boolean runAll;
	private boolean debug = true;
	private static DumperOptions options = new DumperOptions();
	private static Yaml yaml = null;
	private String inventoryFilePath = null;
	private static Map<String, Object> testInventoryData = new HashMap<>();

	public void setInventoryFilePath(String value) {
		this.inventoryFilePath = value;
	}

	public void setDebug(boolean value) {
		this.debug = value;
	}

	public void setRunAll(boolean value) {
		this.runAll = value;
	}

	// TestRandomizer does not have to be a singleton
	private static TestRandomizer instance = new TestRandomizer();

	private TestRandomizer() {
	}

	public static TestRandomizer getInstance() {
		return instance;
	}

	public boolean decide(String methodName) {
		// TODO: flexible percentage
		System.err.println("Deciding on " + methodName);
		boolean status = (new Random().nextInt() % 10 != 0 & !runAll) ? false
				: true;
		status = false;
		if (!status) {
			throw new SkipException("Skipping " + methodName);
		}
		if (yaml == null) {
			loadInventory();
		}
		testInventoryData.put(methodName, status);
		return status;
	}

	// NOTE: do not throw from here or all subsequent tests would be skipped
	// https://stackoverflow.com/questions/21591712/how-do-i-use-testng-skipexception
	public void badDecision(String methodName) {
		System.err.println("Deciding on " + methodName);
		if (new Random().nextInt() % 10 != 0 & !runAll) {
			throw new SkipException("skipping " + methodName);
		}
		if (yaml == null) {
			loadInventory();
		}
		testInventoryData.put(methodName, true);
	}

	public void loadInventory() {

		options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
		yaml = new Yaml(options);

		try (InputStream in = Files.newInputStream(Paths.get(inventoryFilePath))) {
			testInventoryData = yaml.loadAs(in, testInventoryData.getClass());
		} catch (IOException e) {
			e.printStackTrace();
		}
		testInventoryData.keySet().forEach(t -> testInventoryData.put(t, false));
	}

	// based on:
	// https://stackoverflow.com/questions/33459961/how-to-filter-a-map-by-its-values-in-java-8
	static <K, V> Map<K, V> filterByValue(Map<K, V> map, Predicate<V> predicate) {
		return map.entrySet().stream()
				.filter(entry -> predicate.test(entry.getValue()))
				.collect(Collectors.toMap(Entry::getKey, Entry::getValue));
	}

	public List<String> listExecutedTests() {

		Map<String, Object> filteredExecutedTests = filterByValue(testInventoryData,
				value -> value
						.equals(true) /*	Boolean.parseBoolean(value.toString()) */ );
		return new ArrayList<String>(filteredExecutedTests.keySet());
	}

	public void printInventory() {
		listExecutedTests().stream().forEach(System.err::println);
	}
}