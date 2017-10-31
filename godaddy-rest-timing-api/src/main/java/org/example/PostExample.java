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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

// origin: http://www.baeldung.com/httpclient-post-http-request

public class PostExample {

	private static String json = "{\"title\": \"foo\", \"body\": \"bar\",	\"userId\": 1}";
	private static StringEntity entity = null;
	private static CloseableHttpResponse response = null;
	private static String readLine;
	private static BufferedReader br;

	public static void main(String args[]) {

		CloseableHttpClient client = HttpClients.createDefault();
		// "http://localhost/v2/api/cicd/injectjs"
		HttpPost httpPost = new HttpPost(
				"http://jsonplaceholder.typicode.com/posts");

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
		assertThat(response.getStatusLine().getStatusCode(), equalTo(201)); // 201 created	

		try {
			br = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
			while ((readLine = br.readLine()) != null) {
				System.err.println(readLine);
			}
			client.close();
		} catch (UnsupportedOperationException | IOException e) {
			e.printStackTrace();
		}
	}
}
