package example;

/**
 * Copyright 2026 Serguei Kouzmine
 */

import java.io.File;
import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@SuppressWarnings("unused")
public class Runner {
	private static boolean debug = false;

	@SuppressWarnings("deprecation")
	public static void main(String[] args) throws Exception {

		Map<String, String> cli = parseArgs(args);

		String inputFile = null;
		String outputFile = null;
		String data = null;
		String codePage = "cp037";
		String operation = null;
		Long threshold = 0L;

		if (cli.containsKey("debug")) {
			debug = true;
		}
		if (debug)
			System.err.println(cli.keySet());
		/*
		 * if (cli.containsKey("help") || !cli.containsKey("inputfile") ||
		 * !cli.containsKey("operation") || !cli.containsKey("outputfile")) {
		 * System.err.println(String.format("Usage: jar " +
		 * "-inputfile <filename> -outputfile  <filename> -codePage  <codePage> -threshold <number> -debug true\r\n"
		 * )); return; }
		 */
		if (cli.containsKey("outputfile"))
			outputFile = cli.get("outputfile");
		if (cli.containsKey("data"))
			data = cli.get("data");
		if (cli.containsKey("inputfile"))
			inputFile = cli.get("inputfile");
		if (cli.containsKey("threshold"))
			threshold = Long.parseLong(cli.get("threshold"));

		if (cli.containsKey("codepage"))
			codePage = cli.get("codepage");
		if (cli.containsKey("operation"))
			operation = cli.get("operation");

		if (operation == null) {
			System.err.println("Missing required argument: operation");
			return;
		}
		if (debug) {
			System.err.println("Doing: " + operation + " " + codePage);
		}
		final Converter converter = new Converter(inputFile, outputFile, codePage, threshold, data);
		if (operation.equalsIgnoreCase("encode")) {
			converter.encodeFile();
		}

		if (operation.equalsIgnoreCase("decode")) {
			converter.decodeFile();
		}

		if (operation.equalsIgnoreCase("validate")) {
			byte[] input = (inputFile != null) ? Files.readAllBytes(Path.of(inputFile))
					: converter.hexToByteArray(data);
			ValidationResult result = converter.validateGeneric();
			System.err.println(result.isValid() ? "valid" : "invalid");
		}
		if (debug) {
			System.err.println("Done: " + operation + " " + codePage);
		}
	}

	// Extremely simple CLI parser: -key value
	private static Map<String, String> parseArgs(String[] args) {
		if (Arrays.asList(args).contains("debug"))
			System.err.println("Processing: " + Arrays.asList(args));
		Map<String, String> map = new HashMap<>();
		for (int i = 0; i < args.length - 1; i++) {
			if (args[i].startsWith("-")) {
				map.put(args[i].substring(1), args[i + 1]);
				i++;
			}
		}
		return map;
	}
}
