package org.example;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.Assume.assumeFalse;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

public class PostExample {

	private static String json = "{\"id\":1,\"name\":\"John\"}";
	private static StringEntity entity = null;
	private static CloseableHttpResponse response = null;

	public static void main(String args[]) {

		CloseableHttpClient client = HttpClients.createDefault();
		// "http://localhost/v2/api/cicd/injectjs"
		HttpPost httpPost = new HttpPost("http://www.example.com");

		try {
			entity = new StringEntity(json);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		if (entity != null) {
			httpPost.setEntity(entity);
			httpPost.setHeader("Accept", "application/json");
			httpPost.setHeader("Content-type", "application/json");
		}

		try {
			response = client.execute(httpPost);
		} catch (IOException e) {
			e.printStackTrace();
		}
		assertThat(response, notNullValue());
		assertThat(response.getStatusLine().getStatusCode(), equalTo(200));
		try {
			client.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/* 	HttpClient client = new HttpClient();
		client.getParams().setParameter("http.useragent", "Test Client");
	
		BufferedReader br = null;
	
		PostMethod method = new PostMethod("http://search.yahoo.com/search");
		method.addParameter("p", "\"java2s\"");
		StringEntity params = new StringEntity("details={\"p\":\"java2s\"} ");
		request.addHeader("content-type", "application/x-www-form-urlencoded");
		request.setEntity(params);
		try {
			int returnCode = client.executeMethod(method);
	
			if (returnCode == HttpStatus.SC_NOT_IMPLEMENTED) {
				System.err.println("The Post method is not implemented by this URI");
				// still consume the response body
				method.getResponseBodyAsString();
			} else {
				br = new BufferedReader(
						new InputStreamReader(method.getResponseBodyAsStream()));
				String readLine;
				while (((readLine = br.readLine()) != null)) {
					System.err.println(readLine);
				}
			}
		} catch (Exception e) {
			System.err.println(e);
		} finally {
			method.releaseConnection();
			if (br != null)
				try {
					br.close();
				} catch (Exception fe) {
				}
		}
		}
	*/
}
