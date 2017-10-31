package org.example;

import org.apache.http.*;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

// origin: https://alvinalexander.com/java/java-apache-httpclient-restful-client-examples
public class GetExample {

	private static String host = "jsonplaceholder.typicode.com";
	private static String proto = "https";
	private static int port = 443;
	private static String path = "/posts/1";

	@SuppressWarnings("deprecation")
	public static void main(String[] args) {
		DefaultHttpClient httpclient = new DefaultHttpClient();
		try {
			HttpHost target = new HttpHost(host, port, proto);
			HttpGet getRequest = new HttpGet(path);

			System.out.println("executing request to " + target);

			HttpResponse httpResponse = httpclient.execute(target, getRequest);
			HttpEntity entity = httpResponse.getEntity();

			System.out.println("----------------------------------------");
			System.out.println(httpResponse.getStatusLine());

			for (Header header : httpResponse.getAllHeaders()) {
				System.out.println(header);
			}
			System.out.println("----------------------------------------");

			if (entity != null) {
				System.out.println(EntityUtils.toString(entity));
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			httpclient.getConnectionManager().shutdown();
		}
	}
}