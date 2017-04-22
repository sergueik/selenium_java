package org.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.sun.jna.Memory;
import com.sun.jna.Pointer;
import com.sun.jna.platform.win32.VerRsrc.VS_FIXEDFILEINFO;
import com.sun.jna.ptr.IntByReference;
import com.sun.jna.ptr.PointerByReference;

/**
 * based on class for registry lookup for installed browsers 
 * and finding our their versions by Anar Sultanov
 */
public final class InstalledBrowsers {

	private static boolean is64bit;

	private static Map<String, String> installedBrowsers;

	private InstalledBrowsers() {
		throw new AssertionError("Not for instantiation!");
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
		return installedBrowsers.containsKey(browserName)
				? installedBrowsers.get(browserName) : null;
	}

	public static boolean isInstalled(String browserName) {
		if (installedBrowsers == null) {
			find();
		}
		return installedBrowsers.containsKey(browserName);
	}

	public static String getVersion(String browserName) {
		if (isInstalled(browserName) == false)
			return null;
		int[] version = getVersionInfo(installedBrowsers.get(browserName));
		return String.valueOf(version[0]) + "." + String.valueOf(version[1]) + "."
				+ String.valueOf(version[2]) + "." + String.valueOf(version[3]);
	}

	public static int getMajorVersion(String browserName) {
		return isInstalled(browserName)
				? getVersionInfo(installedBrowsers.get(browserName))[0] : 0;
	}

	public static int getMinorVersion(String browserName) {
		return isInstalled(browserName)
				? getVersionInfo(installedBrowsers.get(browserName))[1] : 0;
	}

	public static int getBuildVersion(String browserName) {
		return isInstalled(browserName)
				? getVersionInfo(installedBrowsers.get(browserName))[2] : 0;
	}

	public static int getRevisionVersion(String browserName) {
		return isInstalled(browserName)
				? getVersionInfo(installedBrowsers.get(browserName))[3] : 0;
	}

	private static void find() {
		if (System.getProperty("os.arch").contains("64")) {
			is64bit = true;
		}
		installedBrowsers = new HashMap<>();
		Set<String> pathsList = new HashSet<>();
		pathsList.addAll(findBrowsersInProgramFiles());
		pathsList.addAll(findBrowsersInRegistry());
		for (String path : pathsList) {
			String[] tmp = (path.split("\\\\"));
			String browser = tmp[tmp.length - 1];
			installedBrowsers.put(browser, path);
		}
	}

	@SuppressWarnings("unused")
	private static int[] getVersionInfo(String path) {
		if (installedBrowsers == null) {
			find();
		}
		IntByReference dwDummy = new IntByReference();
		dwDummy.setValue(0);

		int versionlength = com.sun.jna.platform.win32.Version.INSTANCE
				.GetFileVersionInfoSize(path, dwDummy);

		byte[] bufferarray = new byte[versionlength];
		Pointer lpData = new Memory(bufferarray.length);
		PointerByReference lplpBuffer = new PointerByReference();
		IntByReference puLen = new IntByReference();
		boolean fileInfoResult = com.sun.jna.platform.win32.Version.INSTANCE
				.GetFileVersionInfo(path, 0, versionlength, lpData);
		boolean verQueryVal = com.sun.jna.platform.win32.Version.INSTANCE
				.VerQueryValue(lpData, "\\", lplpBuffer, puLen);

		VS_FIXEDFILEINFO lplpBufStructure = new VS_FIXEDFILEINFO(
				lplpBuffer.getValue());
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
				? new String[] {
						"Program Files (x86)\\Google\\Chrome\\Application\\chrome.exe",
						"Program Files (x86)\\Internet Explorer\\iexplore.exe",
						"Program Files (x86)\\Mozilla Firefox\\firefox.exe" }
				: new String[] {
						"Program Files\\Google\\Chrome\\Application\\chrome.exe",
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

	private static List<String> findBrowsersInRegistry() {
		String regPath = is64bit
				? "HKEY_LOCAL_MACHINE\\SOFTWARE\\Wow6432Node\\Clients\\StartMenuInternet\\"
				: "HKEY_LOCAL_MACHINE\\SOFTWARE\\Clients\\StartMenuInternet\\";

		List<String> browsers = new ArrayList<>();
		// find browsers keys
		try {
			List<String> values = new ArrayList<>();
			// TODO: replace execs
			Process findBrowsers = Runtime.getRuntime()
					.exec("reg.exe query \"" + regPath + " \"");
			BufferedReader in = new BufferedReader(
					new InputStreamReader(findBrowsers.getInputStream()));
			String inputLine;
			while ((inputLine = in.readLine()) != null) {
				if (inputLine.contains(regPath))
					values.add(inputLine);
			}
			in.close();
			// read browser path
			for (String value : values) {
				Process findPath = Runtime.getRuntime()
						.exec("reg.exe query \"" + value + "\\DefaultIcon\"");
				BufferedReader ins = new BufferedReader(
						new InputStreamReader(findPath.getInputStream()));
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
				String browser = ((result.substring(p + REGSTR_TOKEN.length()).trim())
						.split(","))[0];
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