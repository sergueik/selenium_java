package example.utils;

import java.util.Random;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

public class Utils {

	private Utils utils;
	private boolean debug = false;

	private String osName = getOSName();

	private static ThreadLocal<Utils> instance = new ThreadLocal<Utils>();

	public void setDebug(boolean value) {
		debug = value;
	}

	public static Utils getInstance() {
		if (instance.get() == null) {
			instance.set(new Utils());
		}
		return instance.get();
	}

	public String getOSName() {
		if (osName == null) {
			osName = System.getProperty("os.name").toLowerCase();
			if (osName.startsWith("windows")) {
				osName = "windows";
			}
		}
		return osName;
	}

	public void waitFor(long interval) {
		try {
			TimeUnit.SECONDS.sleep(interval);
			Thread.sleep(interval);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void sleep(long interval) {
		waitFor(interval);
	}

}
