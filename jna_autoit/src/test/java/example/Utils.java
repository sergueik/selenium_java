package example;

import java.net.*;
import org.openqa.selenium.WebDriver;
import java.io.*;

// origin: https://gist.github.com/daluu/4411221
public class Utils {

	// for download completion and validation readiness
	public static String downloadFileDirect(String url, String filenamePrefix,
			String fileExtension, WebDriver driver) throws Exception {
		// request setup...
		URLConnection request = null;
		request = new URL(url).openConnection();
		// extract session cookie from browser and use with HTTP request calls
		final String sessionCookie = "PHPSESSID";
		request.setRequestProperty("Cookie", String.format("%s=%s", sessionCookie,
				driver.manage().getCookieNamed(sessionCookie).getValue()));
		// other headers as needed...
		// handle the download file request and return temp file path
		// optionally check for HTTP status code 200
		InputStream in = request.getInputStream();
		File downloadedFile = File.createTempFile(filenamePrefix, fileExtension);
		FileOutputStream out = new FileOutputStream(downloadedFile);
		byte[] buffer = new byte[1024];
		int len = in.read(buffer);
		while (len != -1) {
			out.write(buffer, 0, len);
			len = in.read(buffer);
			if (Thread.interrupted()) {
				throw new InterruptedException();
			}
		}
		in.close();
		out.close();
		return downloadedFile.getAbsolutePath();
	}

}
