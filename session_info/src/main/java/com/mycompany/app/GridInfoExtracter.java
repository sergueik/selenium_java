package com.mycompany.app;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicHttpEntityEnclosingRequest;
import org.json.JSONException;
import org.json.JSONObject;
import org.openqa.selenium.remote.SessionId;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

@SuppressWarnings("deprecation")
public class GridInfoExtracter {
	private static final Logger logger = Logger			
			.getLogger(GridInfoExtracter.class.getCanonicalName());

	private static String[] getHostNameAndPort(String hostName, int port,
			SessionId session) {
		String[] hostAndPort = new String[2];
		String errorMsg = "Failed to acquire remote webdriver node and port info. Root cause: ";

		try {
			HttpHost host = new HttpHost(hostName, port);
			@SuppressWarnings("deprecation")
			DefaultHttpClient client = new DefaultHttpClient();
			URL sessionURL = new URL("http://" + hostName + ":" + port
					+ "/grid/api/testsession?session=" + session);
			BasicHttpEntityEnclosingRequest r = new BasicHttpEntityEnclosingRequest(
					"POST", sessionURL.toExternalForm());
			HttpResponse response = client.execute(host, r);
			JSONObject object = extractObject(response);
			URL myURL = new URL(object.getString("proxyId"));
			if ((myURL.getHost() != null) && (myURL.getPort() != -1)) {
				hostAndPort[0] = myURL.getHost();
				hostAndPort[1] = Integer.toString(myURL.getPort());
			}
		} catch (Exception e) {
			logger.log(Level.SEVERE, errorMsg, e);
			throw new RuntimeException(errorMsg, e);
		}
		return hostAndPort;
	}

	private static JSONObject extractObject(HttpResponse resp)
			throws IOException, JSONException {
		BufferedReader rd = new BufferedReader(
				new InputStreamReader(resp.getEntity().getContent()));
		StringBuffer s = new StringBuffer();
		String line;
		while ((line = rd.readLine()) != null) {
			s.append(line);
		}
		rd.close();
		JSONObject objToReturn = new JSONObject(s.toString());
		return objToReturn;
	}
}