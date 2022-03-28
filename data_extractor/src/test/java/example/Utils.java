package example;

import java.io.IOException;
import java.io.InputStream;

public class Utils {
	public static String getScriptContent(String scriptName) {
		try {
			final InputStream stream = Utils.class.getClassLoader().getResourceAsStream(scriptName);
			final byte[] bytes = new byte[stream.available()];
			stream.read(bytes);
			return new String(bytes, "UTF-8");
		} catch (IOException e) {
			throw new RuntimeException(scriptName);
		}
	}

}
