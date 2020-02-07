package example;

import example.messaging.CDPClient;
import static java.lang.System.err;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasKey;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;

import java.nio.file.Files;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.json.JSONObject;
import org.json.JSONException;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

// TODO: get rid of
import com.neovisionaries.ws.client.WebSocketException;

import example.messaging.MessageBuilder;
import example.messaging.ServiceWorker;
import example.utils.Utils;

public class NavigationTest extends BaseTest {
	private String URL = null;
	private String responseMessage = null;
	private JSONObject result = null;

	// @Ignore
	// https://chromedevtools.github.io/devtools-protocol/tot/DOM/#method-getDocuments
	// https://chromedevtools.github.io/devtools-protocol/tot/DOM#type-Node
	// https://chromedevtools.github.io/devtools-protocol/tot/DOM/#method-querySelector
	// https://chromedevtools.github.io/devtools-protocol/tot/DOM/#method-describeNode
	// https://chromedevtools.github.io/devtools-protocol/tot/DOM/#method-focus
	// https://chromedevtools.github.io/devtools-protocol/tot/DOM/#method-highlightNode
	// https://chromedevtools.github.io/devtools-protocol/tot/Runtime#type-RemoteObjectId
	//
	@Test
	public void getDocumentTest() {
		// Arrange
		long nodeId = (long) -1;
		driver.get("https://www.google.com");
		try {
			CDPClient.sendMessage(MessageBuilder.buildGetDocumentMessage(id));
			responseMessage = CDPClient.getResponseDataMessage(id);
			result = new JSONObject(responseMessage);
			assertThat(result.has("root"), is(true));
			assertThat(result.getJSONObject("root").has("nodeId"), is(true));
			nodeId = result.getJSONObject("root").getLong("nodeId");
			assertTrue(nodeId != 0);
		} catch (IOException | WebSocketException | InterruptedException
				| JSONException e) {
			// ignore
			System.err.println("Exception (ignored): " + e.toString());
		} catch (CDPClient.MessageTimeOutException e) {
			throw new RuntimeException(e.toString());
		}

		try {
			CDPClient
					.sendMessage(MessageBuilder.buildDescribeNodeMessage(id, nodeId));
			responseMessage = CDPClient.getResponseDataMessage(id);
			result = new JSONObject(responseMessage);
			assertThat(result.has("node"), is(true));
			JSONObject data = result.getJSONObject("node");
			for (String field : Arrays.asList(
					new String[] { "nodeType", "nodeName", "localName", "nodeValue" })) {
				assertThat(data.has(field), is(true));
			}
			System.err.println("Command returned node: " + data.toString(2));
		} catch (IOException | WebSocketException | InterruptedException
				| JSONException e) {
			// ignore
			System.err.println("Exception (ignored): " + e.toString());
		} catch (CDPClient.MessageTimeOutException e) {
			throw new RuntimeException(e.toString());
		}

		String selector = "input[name='q']";
		try {
			CDPClient.sendMessage(
					MessageBuilder.buildQuerySelectorMessage(id, nodeId, selector));
			responseMessage = CDPClient.getResponseDataMessage(id);
			result = new JSONObject(responseMessage);
			assertThat(result.has("nodeId"), is(true));
			nodeId = result.getLong("nodeId");
			assertTrue(nodeId != 0);
			System.err.println("Command returned nodeId: " + nodeId);
		} catch (IOException | WebSocketException | InterruptedException
				| JSONException e) {
			// ignore
			System.err.println("Exception (ignored): " + e.toString());
		} catch (CDPClient.MessageTimeOutException e) {
			throw new RuntimeException(e.toString());
		}
		/*
		command = "DOM.resolveNode";
		params = new HashMap<>();
		params.put("nodeId", nodeId);
		
		try {
			result = driver.executeCdpCommand(command, params);
			// depth, 1
			// Assert
			assertThat(result, hasKey("object"));
			// object
			@SuppressWarnings("unchecked")
			Map<String, Object> data = (Map<String, Object>) result.get("object");
			for (String field : Arrays.asList(
					new String[] { "type", "subtype", "className", "objectId" })) {
				assertThat(data, hasKey(field));
			}
			String objectId = (String) data.get("objectId");
			assertThat(objectId, notNullValue());
			System.err
					.println("Command " + command + " returned objectId: " + objectId);
		} catch (org.openqa.selenium.WebDriverException e) {
			err.println(
					"Exception in command " + command + " (ignored): " + e.toString());
		}
		
		command = "DOM.something not defined";
		try {
			// Act
			result = driver.executeCdpCommand(command, new HashMap<>());
		} catch (org.openqa.selenium.WebDriverException e) {
			err.println(
					"Exception in command " + command + " (ignored): " + e.toString());
			// wasn't found
		}
		// DOM.removeNode
		command = "DOM.focus";
		params = new HashMap<>();
		params.put("nodeId", nodeId);
		try {
			// Act
			result = driver.executeCdpCommand(command, params);
		} catch (org.openqa.selenium.WebDriverException e) {
			err.println(
					"Exception in command " + command + " (ignored): " + e.toString());
			// : unknown error: unhandled inspector error:
			// {"code":-32000,"message":"Element is not focusable"}
		}
		command = "DOM.highlightNode";
		try {
			// Act
			result = driver.executeCdpCommand(command, new HashMap<>());
			Utils.sleep(10000);
		} catch (org.openqa.selenium.WebDriverException e) {
			err.println(
					"Exception in command " + command + " (ignored): " + e.toString());
		}
		// TODO: command = "Runtime.callFunctionOn";
		 
		 */
	}
}
