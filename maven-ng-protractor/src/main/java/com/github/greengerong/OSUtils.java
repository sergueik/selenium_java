package com.github.greengerong;

public class OSUtils {
	public static boolean isWindows() {
		return System.getProperty("os.name").toLowerCase().contains("windows");
	}
}