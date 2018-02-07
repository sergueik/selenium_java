package com.mycompany.app;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)

// origin:
// http://automated-testing.info/t/kak-peredelat-parametrizovannyj-test-na-junit-v-testng/10970/11
public class App2Test {

	public String url;

	public App2Test(String url) {
		this.url = url;
	}

	@Test
	public void test() {
		System.err.println(String.format("Url = '%s'", url));
	}

	@Parameters(name = "url = {0}")
	public static Iterable<Object[]> data() {
		List<Object[]> testData = new ArrayList<Object[]>();
		List<Object> dataRow = new ArrayList<Object>();
		String separator = ",";
		BufferedReader reader = null;
		String line;
		try {
			reader = new BufferedReader(new FileReader("./data.txt"));
			while ((line = reader.readLine()) != null) {
				// load comma-separated columns
				testData.add(line.split(separator));
				// this alwo works for single item per line 
        // no need to 
				// testData.add(new Object[] { line });
			}
			reader.close();
		} catch (IOException e) {
			// cat process multiple exception type,
			// SomeExceptionType|AnotherExceptionType
			e.printStackTrace();
		}
		return testData;
	}

	// java 8 style, somewhat excessive ?
	@Parameters(name = "url = {0}")
	public static Collection<Object[]> data8() {
		List<String> lines = new ArrayList<>();
		BufferedReader reader = null;
		String line;
		try {
			reader = new BufferedReader(new FileReader("./data.txt"));
			while ((line = reader.readLine()) != null) {
				lines.add(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return lines.stream().map(o -> new String[] { o })
				.collect(Collectors.toList());
	}
}
