package example;
/**
 * Copyright 2023 Serguei Kouzmine
 */

import org.junit.Ignore;
import org.junit.Test;

import com.sun.jna.platform.win32.VersionHelpers;

import java.io.*;
import java.util.Properties;

@SuppressWarnings({ "deprecation", "unused" })
public class VersionHelpersTest {

	@Test
	public void test1() {
		System.err.println("status: " + VersionHelpers.IsWindows8Point1OrGreater());
	}
	// origin:
	// https://stackoverflow.com/questions/6109679/how-to-check-windows-edition-in-java
	// msinfo32.exe /report %temp%/sysinfo.txt
	// systeminfo.exe /fo list
	// NOTE: both msinfo32.exe and systeminfo.exe run for a very long time and
	// produce excessive inventory
	// there appears no way to limit to summary:
	// msinfo32.exe /nfo mysummary.nfo /categories +systemsummary /report
	// %temp%/sysinfo1.txt
	// especially for running tasks and loaded modules and error reporting

	@Ignore
	@Test
	public void test2() {
		Runtime rt;
		Process pr;
		BufferedReader in;
		String line = "";
		String sysInfo = "";
		String edition = "";
		String fullOSName = "";
		final String SEARCH_TERM = "OS Name:";
		final String[] EDITIONS = { "Basic", "Home", "Professional", "Enterprise" };

		try {
			rt = Runtime.getRuntime();
			pr = rt.exec("SYSTEMINFO");
			in = new BufferedReader(new InputStreamReader(pr.getInputStream()));

			// add all the lines into a variable
			while ((line = in.readLine()) != null) {
				if (line.contains(SEARCH_TERM)) // found the OS you are using
				{
					// extract the full os name
					fullOSName = line.substring(
							line.lastIndexOf(SEARCH_TERM) + SEARCH_TERM.length(),
							line.length() - 1);
					break;
				}
			}

			// extract the edition of windows you are using
			for (String s : EDITIONS) {
				if (fullOSName.trim().contains(s)) {
					edition = s;
				}
			}
			System.err
					.println("status: " + VersionHelpers.IsWindows8Point1OrGreater());
			System.out.println("The edition of Windows you are using is " + edition);

		} catch (IOException ioe) {
			System.err.println(ioe.getMessage());
		}

	}

	@Test
	public void test3() {

		Properties properties = System.getProperties();
		System.err.println("System.Properties: " + properties.get("os.name") + " "
				+ "version: " + properties.get("os.version"));
	}
}
