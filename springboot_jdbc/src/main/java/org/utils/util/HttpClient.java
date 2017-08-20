package org.utils.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

public class HttpClient {

	private static final Logger logger = Logger
			.getLogger(HttpClient.class.getName());
	private static final RequestConfig requestConfig = RequestConfig.custom()
			.setSocketTimeout(20000).setConnectTimeout(20000).build();

	private static final CloseableHttpClient httpClient = HttpClients
			.createDefault();

	public static String sendGet(String url) {
		if (url == null || url.isEmpty()) {
			return "";
		}
		String result = "";
		HttpGet httpGet = new HttpGet(url);
		httpGet.setConfig(requestConfig);
		CloseableHttpResponse response = null;
		try {
			response = httpClient.execute(httpGet);
			System.out.println("output URI: " + httpGet.getURI());
			int status = response.getStatusLine().getStatusCode();
			System.out.println("HTTP Status： " + status);
			Header[] headers = response.getAllHeaders();
			int headerLength = headers.length;
			for (int i = 0; i < headerLength; i++) {
				System.out.println("Header: " + headers[i]);
			}
			HttpEntity entity = response.getEntity();
			result = EntityUtils.toString(entity, "UTF-8");
			System.out.println("Response: " + result);
		} catch (IOException | ParseException e) {
			logger.log(Level.INFO, e.getMessage());
		} finally {
			try {
				httpGet.releaseConnection();
				if (response != null) {
					response.close();
				}
				// httpClient.close();
			} catch (IOException e) {
				logger.log(Level.INFO, e.getMessage());
			}
		}
		return result;
	}

	public static String sendPost(String url, Map<String, String> params) {
		if (url == null || url.isEmpty() || params == null || params.isEmpty()) {
			return "";
		}
		String result = "";
		HttpPost httpPost = new HttpPost(url);
		httpPost.setConfig(requestConfig);
		List<NameValuePair> list = new ArrayList<>();
		for (Map.Entry<String, String> entry : params.entrySet()) {
			list.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
		}
		CloseableHttpResponse response = null;
		try {
			httpPost.setEntity(new UrlEncodedFormEntity(list, "UTF-8"));
			response = httpClient.execute(httpPost);
			HttpEntity entity = response.getEntity();
			result = EntityUtils.toString(entity, "UTF-8");
		} catch (IOException | ParseException e) {
			logger.log(Level.INFO, e.getMessage());
		} finally {
			try {
				httpPost.releaseConnection();
				if (response != null) {
					response.close();
				}
				// httpClient.close();
			} catch (IOException e) {
				logger.log(Level.INFO, e.getMessage());
			}
		}
		return result;
	}

	public static String sendPost(String url, String jsonParams) {
		if (url == null || url.isEmpty() || jsonParams == null
				|| jsonParams.isEmpty()) {
			return "";
		}
		String result = "";
		HttpPost httpPost = new HttpPost(url);
		httpPost.setConfig(requestConfig);
		httpPost.setHeader("Content-Type", "application/json");
		CloseableHttpResponse response = null;
		try {
			httpPost.setEntity(new StringEntity(jsonParams, "UTF-8"));
			response = httpClient.execute(httpPost);
			HttpEntity httpEntity = response.getEntity();
			result = EntityUtils.toString(httpEntity, "UTF-8");
		} catch (IOException | ParseException e) {
			logger.log(Level.INFO, e.getMessage());
		} finally {
			try {
				httpPost.releaseConnection();
				if (response != null) {
					response.close();
				}
				// httpClient.close();
			} catch (IOException e) {
				logger.log(Level.INFO, e.getMessage());
			}
		}
		return result;
	}
}
