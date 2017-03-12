package com.mycompany.app;

import java.io.IOException;
import java.io.InputStream;
import java.lang.RuntimeException;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Formatter;

public class Utils {

	public String getScriptContent(String resourceFileName) {
		try {
			System.err
					.println("Script contents: " + getResourceURI(resourceFileName));
			final InputStream stream = this.getClass().getClassLoader()
					.getResourceAsStream(resourceFileName);
			final byte[] bytes = new byte[stream.available()];
			stream.read(bytes);
			return new String(bytes, "UTF-8");
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public String getPageContent(String resourceFileName) {
		try {
			URI uri = this.getClass().getClassLoader().getResource(resourceFileName)
					.toURI();
			System.err.println("Page contents: " + uri.toString());
			return uri.toString();
		} catch (URISyntaxException e) {
			throw new RuntimeException(e);
		}
	}

	// NOTE: getResourceURI does not work well with standalone app.
	public String getResourceURI(String resourceFileName) {
		try {
			URI uri = this.getClass().getClassLoader().getResource(resourceFileName)
					.toURI();
			return uri.toString();
		} catch (URISyntaxException e) {
			throw new RuntimeException(e);
		}
	}

	public String getResourcePath(String resourceFileName) {
		return String.format("%s/src/main/resources/%s",
				System.getProperty("user.dir"), resourceFileName);
	}

}