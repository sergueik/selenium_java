package example;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;

public class Utils {

	protected static String getScriptContent(String scriptName) {
		try {
			final InputStream stream = MergeDocumentFragments.class.getClassLoader()
					.getResourceAsStream(scriptName);
			final byte[] bytes = new byte[stream.available()];
			stream.read(bytes);
			return new String(bytes, "UTF-8");
		} catch (IOException e) {
			throw new RuntimeException(scriptName);
		}
	}

	protected static String getPageContent(String pagename) {
		try {
			URI uri = MergeDocumentFragments.class.getClassLoader()
					.getResource(pagename).toURI();
			System.err.println("Testing local file: " + uri.toString());
			return uri.toString();
		} catch (URISyntaxException e) {
			throw new RuntimeException(e);
		}
	}

}
