package example;

import java.nio.file.Paths;

public class Constants {
	public static String osName = getOSName();
	// osName.equals("windows") ?
	public static final String PATH_TO_DB = Paths.get(System.getProperty("user.home")).resolve("Desktop")
			.resolve("passwkeeper.db").toAbsolutePath().toString();

	// Utilities
	public static String getOSName() {
		if (osName == null) {
			osName = System.getProperty("os.name").toLowerCase();
			if (osName.startsWith("windows")) {
				osName = "windows";
			}
		}
		return osName;
	}
}
