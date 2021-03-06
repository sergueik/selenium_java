package com.github.sergueik.swet_javafx;
/**
 * Copyright 2020 Serguei Kouzmine
 */

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Optional;

import org.json.JSONException;
import org.json.JSONObject;

// https://en.wikipedia.org/wiki/Java_Native_Access
import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.NativeMapped;
import com.sun.jna.PointerType;
import com.sun.jna.win32.W32APIFunctionMapper;
import com.sun.jna.win32.W32APITypeMapper;

public class Utils {

	private static String osName;
	private static final boolean debug = false;
	@SuppressWarnings("unused")
	private static String defaultKey = "sender"; 

	public static String getDefaultKey() {
		return defaultKey;
	}

	public static String getOsName() {
		if (osName == null) {
			osName = System.getProperty("os.name").toLowerCase();
			if (osName.startsWith("windows")) {
				osName = "windows";
			}
		}
		return osName;
	}

	// origin:
	// http://stackoverflow.com/questions/585534/what-is-the-best-way-to-find-the-users-home-directory-in-java
	public static String getDesktopPath() {
		HWND hwndOwner = null;
		int nFolder = Shell32.CSIDL_DESKTOPDIRECTORY;
		HANDLE hToken = null;
		int dwFlags = Shell32.SHGFP_TYPE_CURRENT;
		char[] pszPath = new char[Shell32.MAX_PATH];
		int hResult = Shell32.INSTANCE.SHGetFolderPath(hwndOwner, nFolder, hToken,
				dwFlags, pszPath);
		if (Shell32.S_OK == hResult) {
			String path = new String(pszPath);
			return (path.substring(0, path.indexOf('\0')));
		} else {
			return String.format("%s\\Desktop", System.getProperty("user.home"));
		}
	}

	static class HANDLE extends PointerType implements NativeMapped {
	}

	static class HWND extends HANDLE {
	}

	private static Map<String, Object> OPTIONS = new HashMap<>();
	static {
		OPTIONS.put(Library.OPTION_TYPE_MAPPER, W32APITypeMapper.UNICODE);
		OPTIONS.put(Library.OPTION_FUNCTION_MAPPER, W32APIFunctionMapper.UNICODE);
	}

	static interface Shell32 extends Library {

		public static final int MAX_PATH = 260;
		// https://sourceforge.net/u/cstrauss/w32api/ci/7805df8efec130f582b131b8c0d75e1b6ce0993b/tree/include/shlobj.h?format=raw
		public static final int CSIDL_DESKTOPDIRECTORY = 0x0010;
		public static final int SHGFP_TYPE_CURRENT = 0;
		public static final int SHGFP_TYPE_DEFAULT = 1;
		public static final int S_OK = 0;

		@SuppressWarnings("deprecation")
		static Shell32 INSTANCE = Native.loadLibrary("shell32", Shell32.class,
				OPTIONS);

		/**
		 * see http://msdn.microsoft.com/en-us/library/bb762181(VS.85).aspx
		 *
		 * HRESULT SHGetFolderPath( HWND hwndOwner, int nFolder, HANDLE hToken,
		 * DWORD dwFlags, LPTSTR pszPath);
		 */
		public int SHGetFolderPath(HWND hwndOwner, int nFolder, HANDLE hToken,
				int dwFlags, char[] pszPath);

	}

	// NOTE: put inside "WEB-INF/classes" for web hosted app
	public static String getScriptContent(String resourceFileName) {
		try {
			if (debug)
				System.err
						.println("Script contents: " + getResourceURI(resourceFileName));

			final InputStream stream = getResourceStream(resourceFileName);
			final byte[] bytes = new byte[stream.available()];
			stream.read(bytes);
			return new String(bytes, "UTF-8");
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	// NOTE: getResourceURI may not work with standalone or web hosted
	// application
	public static String getResourceURI(String resourceFileName) {
		try {
			if (debug)
				System.err.println("Getting resource URI for: " + resourceFileName);

			URI uri = new Utils().getClass().getClassLoader()
					.getResource(resourceFileName).toURI();
			if (debug)
				System.err.println("Resource URI: " + uri.toString());

			return uri.toString();
		} catch (URISyntaxException e) {
			throw new RuntimeException(e);
		}
	}

	public static InputStream getResourceStream(String resourceFilePath) {
		return new Utils().getClass().getResourceAsStream(resourceFilePath);
	}

	public static String getResourcePath(String resourceFileName) {
		final String resourcePath = String.format("%s/src/main/resources/%s",
				System.getProperty("user.dir"), resourceFileName);
		if (debug)
			System.err.println("Project based resource path: " + resourcePath);

		return resourcePath;
	}

	public static void sleep(Integer milliSeconds) {
		try {
			Thread.sleep((long) milliSeconds);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public static String writeDataJSON(Map<String, String> data,
			String defaultPayload) {
		String payload = defaultPayload;
		JSONObject json = new JSONObject();
		try {
			for (String key : data.keySet()) {
				json.put(key, data.get(key));
			}
			StringWriter wr = new StringWriter();
			json.write(wr);
			payload = wr.toString();
			// logger.info("Created payload: " + payload);
		} catch (JSONException e) {
			System.err.println("Exception (ignored): " + e);
		}
		return payload;
	}

	public static String readData(Optional<Map<String, String>> parameters) {
		return readData(null, parameters);
	}

	// Deserialize the hashmap from the JSON
	// see also
	// https://stackoverflow.com/questions/3763937/gson-and-deserializing-an-array-of-objects-with-arrays-in-it
	// https://futurestud.io/tutorials/gson-mapping-of-arrays-and-lists-of-objects
	public static String readData(String payload,
			Optional<Map<String, String>> parameters) {

		Map<String, String> collector = (parameters.isPresent()) ? parameters.get()
				: new HashMap<>();

		String data = (payload == null) ? "{}" // empty hash
				: payload;
		try {
			JSONObject elementObj = new JSONObject(data);
			@SuppressWarnings("unchecked")
			Iterator<String> propIterator = elementObj.keys();
			while (propIterator.hasNext()) {
				String propertyKey = propIterator.next();
				String propertyVal = elementObj.getString(propertyKey);
				// logger.info(propertyKey + ": " + propertyVal);
				if (debug) {
					System.err.println("readData: " + propertyKey + ": " + propertyVal);
				}
				collector.put(propertyKey, propertyVal);
			}
		} catch (JSONException e) {
			System.err.println("Exception (ignored): " + e.toString());
			return null;
		}
		return collector.get(defaultKey);
	}

}
