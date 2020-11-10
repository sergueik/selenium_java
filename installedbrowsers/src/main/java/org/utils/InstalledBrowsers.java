package org.utils;

import java.io.BufferedReader;

import java.io.File;
import java.io.InputStreamReader;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.sun.jna.Memory;
import com.sun.jna.Pointer;
import com.sun.jna.platform.win32.Advapi32Util;
import com.sun.jna.platform.win32.VerRsrc.VS_FIXEDFILEINFO;
import com.sun.jna.platform.win32.WinReg;
import com.sun.jna.ptr.IntByReference;
import com.sun.jna.ptr.PointerByReference;

public final class InstalledBrowsers {

	private static boolean is64bit;
	private static Map<String, String> installedBrowsers = null;
	private static Map<String, int[]> browserVersionInfo = new HashMap<>();

	private static boolean debug = false;

	public static void setDebug(boolean value) {
		InstalledBrowsers.debug = value;
	}

	private InstalledBrowsers() {
		throw new AssertionError("no constructor");
	}

	public static List<String> getInstalledBrowsers() {
		if (installedBrowsers == null) {
			find();
		}
		List<String> browsersList = new ArrayList<>();
		installedBrowsers.keySet().forEach(o -> browsersList.add(o));
		return browsersList;
	}

	public static String getPath(String browserName) {
		if (installedBrowsers == null) {
			find();
		}
		return installedBrowsers.containsKey(browserName) ? installedBrowsers.get(browserName) : null;
	}

	public static boolean isInstalled(String browser) {
		if (installedBrowsers == null) {
			find();
		}
		return installedBrowsers.containsKey(browser);
	}

	private static int[] getBrowserVersionInfo(String browser) {

		if (browserVersionInfo == null || !browserVersionInfo.containsKey(browser)) {
			int[] versionInfo = getVersionInfo(installedBrowsers.get(browser));
			if (debug)
				System.err.println("Adding version info for " + browser + " " + Arrays.asList(versionInfo));
			browserVersionInfo.put(browser, versionInfo);
		} else {
			if (debug)
				System.err.println("getBrowserVersionInfo returning cached value for " + browser);
		}

		return browserVersionInfo.get(browser);
	}

	public static String getVersion(String browser) {
		if (!isInstalled(browser))
			return null;
		if (debug)
			System.err.println("get Version for " + browser);
		int[] versionInfo = getBrowserVersionInfo(browser);
		return String.valueOf(versionInfo[0]) + "." + String.valueOf(versionInfo[1]) + "."
				+ String.valueOf(versionInfo[2]) + "." + String.valueOf(versionInfo[3]);
	}

	public static int getMajorVersion(String browser) {
		if (isInstalled(browser)) {
			if (debug)
				System.err.println("get Major Version for " + browser);
			int[] versionInfo = getBrowserVersionInfo(browser);
			return versionInfo[0];
		} else {
			return 0;
		}
	}

	public static int getMinorVersion(String browser) {
		if (isInstalled(browser)) {
			if (debug)
				System.err.println("get Minor Version for " + browser);
			int[] versionInfo = getBrowserVersionInfo(browser);
			return versionInfo[1];
		} else {
			return 0;
		}
	}

	public static int getBuildVersion(String browser) {
		if (isInstalled(browser)) {
			int[] versionInfo = getBrowserVersionInfo(browser);
			return versionInfo[2];
		} else {
			return 0;
		}
	}

	public static int getRevisionVersion(String browser) {
		if (isInstalled(browser)) {
			int[] versionInfo = getBrowserVersionInfo(browser);
			return versionInfo[3];
		} else {
			return 0;
		}
	}

	private static void find() {
		if (System.getProperty("os.arch").contains("64")) {
			is64bit = true;
		}
		installedBrowsers = new HashMap<>();
		Set<String> pathsList = new HashSet<>();
		pathsList.addAll(findBrowsersInProgramFiles());
		pathsList.addAll(findBrowsersInRegistry());
		// TODO: execute findBrowsersInRegistryWithExec
		for (String path : pathsList) {
			String[] tmp = (path.split("\\\\"));
			String browser = tmp[tmp.length - 1];
			installedBrowsers.put(browser, path);
		}
	}

	// https://java-native-access.github.io/jna/4.2.0/com/sun/jna/platform/win32/package-summary.html
	// https://github.com/java-native-access/jna/blob/master/contrib/platform/src/com/sun/jna/platform/win32/Version.java
	// https://github.com/java-native-access/jna/blob/master/contrib/platform/src/com/sun/jna/platform/win32/VersionUtil.java
	// https://msdn.microsoft.com/en-us/library/windows/desktop/aa381058(v=vs.85).aspx
	@SuppressWarnings("unused")
	private static int[] getVersionInfo(String path) {
		if (installedBrowsers == null) {
			find();
		}
		IntByReference dwDummy = new IntByReference();
		dwDummy.setValue(0);

		int versionlength = com.sun.jna.platform.win32.Version.INSTANCE.GetFileVersionInfoSize(path, dwDummy);

		byte[] bufferarray = new byte[versionlength];
		Pointer lpData = new Memory(bufferarray.length);
		PointerByReference lplpBuffer = new PointerByReference();
		IntByReference puLen = new IntByReference();
		boolean fileInfoResult = com.sun.jna.platform.win32.Version.INSTANCE.GetFileVersionInfo(path, 0, versionlength,
				lpData);
		boolean verQueryVal = com.sun.jna.platform.win32.Version.INSTANCE.VerQueryValue(lpData, "\\", lplpBuffer,
				puLen);

		VS_FIXEDFILEINFO lplpBufStructure = new VS_FIXEDFILEINFO(lplpBuffer.getValue());
		lplpBufStructure.read();

		int v1 = (lplpBufStructure.dwFileVersionMS).intValue() >> 16;
		int v2 = (lplpBufStructure.dwFileVersionMS).intValue() & 0xffff;
		int v3 = (lplpBufStructure.dwFileVersionLS).intValue() >> 16;
		int v4 = (lplpBufStructure.dwFileVersionLS).intValue() & 0xffff;
		return new int[] { v1, v2, v3, v4 };
	}

	private static List<String> findBrowsersInProgramFiles() {
		// find possible root
		File[] rootPaths = File.listRoots();
		List<String> browsers = new ArrayList<>();
		String[] defaultPath = (is64bit)
				? new String[] { "Program Files (x86)\\Google\\Chrome\\Application\\chrome.exe",
						"Program Files (x86)\\Internet Explorer\\iexplore.exe",
						"Program Files (x86)\\Mozilla Firefox\\firefox.exe" }
				: new String[] { "Program Files\\Google\\Chrome\\Application\\chrome.exe",
						"Program Files\\Internet Explorer\\iexplore.exe",
						"Program Files\\Mozilla Firefox\\firefox.exe" };

		// check file existence
		for (File rootPath : rootPaths) {
			for (String defPath : defaultPath) {
				File exe = new File(rootPath + defPath);
				if (exe.exists()) {
					browsers.add(exe.toString());
				}
			}
		}
		return browsers;
	}

	// http://www.programcreek.com/java-api-examples/index.php?api=com.sun.jna.platform.win32.Advapi32Util
	private static List<String> findBrowsersInRegistry() {
		// String regPath = "SOFTWARE\\Clients\\StartMenuInternet\\";
		String regPath = is64bit ? "SOFTWARE\\Wow6432Node\\Clients\\StartMenuInternet\\"
				: "SOFTWARE\\Clients\\StartMenuInternet\\";

		List<String> browsers = new ArrayList<>();
		String path = null;
		try {
			for (String browserName : Advapi32Util.registryGetKeys(WinReg.HKEY_LOCAL_MACHINE, regPath)) {
				path = Advapi32Util.registryGetStringValue(WinReg.HKEY_LOCAL_MACHINE,
						regPath + "\\" + browserName + "\\shell\\open\\command", "").replace("\"", "");
				if (path != null && new File(path).exists()) {
					browsers.add(path);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return browsers;
	}

	// http://stackoverflow.com/questions/62289/read-write-to-windows-registry-using-java
	private static List<String> findBrowsersInRegistryWithExec() {

		String regPath = is64bit ? "SOFTWARE\\Wow6432Node\\Clients\\StartMenuInternet\\"
				: "SOFTWARE\\Clients\\StartMenuInternet\\";

		List<String> browsers = new ArrayList<>();
		List<String> values = new ArrayList<>();
		try {
			Process findBrowsers = Runtime.getRuntime()
					.exec("reg.exe query \"" + "HKEY_LOCAL_MACHINE\\" + regPath + " \"");
			BufferedReader in = new BufferedReader(new InputStreamReader(findBrowsers.getInputStream()));
			String inputLine;

			// read browser names
			while ((inputLine = in.readLine()) != null) {
				if (inputLine.contains(regPath))
					values.add(inputLine);
			}
			in.close();
			// read browser paths (through `defaulticon`)
			for (String value : values) {
				Process findPath = Runtime.getRuntime().exec("reg.exe query \"" + value + "\\DefaultIcon\"");
				BufferedReader ins = new BufferedReader(new InputStreamReader(findPath.getInputStream()));
				String result = "";
				String inLine;
				while ((inLine = ins.readLine()) != null) {
					result = result.concat(inLine);
				}
				ins.close();
				final String REGSTR_TOKEN = "REG_SZ";
				int p = result.indexOf(REGSTR_TOKEN);
				if (p == -1)
					continue;
				String browser = ((result.substring(p + REGSTR_TOKEN.length()).trim()).split(","))[0];
				if (browser != null && new File(browser).exists()) {
					browsers.add(browser);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return browsers;
	}
}