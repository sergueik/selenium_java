package com.github.sergueik.zabbix;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.startsWith;
import static org.hamcrest.core.IsNot.not;
import static org.hamcrest.CoreMatchers.is;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;

import com.github.sergueik.zabbix.DataObject;
import com.github.sergueik.zabbix.SenderResult;
import com.github.sergueik.zabbix.ZabbixSender;

public class ZabbixSenderTest {

	// install locally https://www.zabbix.com/download
	// e.g.
	// https://www.zabbix.com/download?zabbix=4.2&os_distribution=ubuntu&os_version=16.04_xenial&db=mysql
	String host = "127.0.0.1";
	// there is an issue with zabbix appliance not listening to 10051 initially,
	// most likely due to database error during initialization
	int port = 10051;

	@Before
	public void before() throws IOException {
		assertThat(isListening(port), is(true));
	}

	@Test
	public void test_LLD_rule() throws IOException {
		ZabbixSender zabbixSender = new ZabbixSender(host, port);

		DataObject dataObject = new DataObject();
		dataObject.setHost("172.17.42.1");
		dataObject.setKey("healthcheck[dw,notificationserver]");

		JSONObject data = new JSONObject();

		List<JSONObject> testDataAray = new LinkedList<>();
		JSONObject testData = new JSONObject();
		testData.put("hello", "hello");

		testDataAray.add(testData);
		data.put("data", testDataAray);

		dataObject.setValue(data.toString());
		dataObject.setClock(System.currentTimeMillis() / 1000);
		SenderResult result = zabbixSender.send(dataObject);

		System.out.println("result:" + result);
		if (result.success()) {
			System.err.println("send " + ((result.success()) ? "success." : "fail!"));
		}

	}

	@Test
	public void test() throws IOException {
		ZabbixSender zabbixSender = new ZabbixSender(host, port);

		DataObject dataObject = new DataObject();
		dataObject.setHost("172.17.42.1");
		dataObject.setKey("a[test, jvm.mem.non-heap.used]");
		dataObject.setValue("10");
		dataObject.setClock(System.currentTimeMillis() / 1000);
		SenderResult result = zabbixSender.send(dataObject);

		System.err.println("result:" + result);
		// result:{"failed":1,"processed":0,"spentSeconds":0.001796,"total":1}

		if (result.success()) {
			System.out.println("send successfully");
		} else {
			System.err.println("send failed -  check server side errors");
		}
		// send fail!
	}

	// https://stackoverflow.com/questions/434718/sockets-discover-port-availability-using-java
	// keeping the fancy syntax
	private boolean isListening(int port) {
		try (Socket ignored = new Socket(host, port)) {
			return false;
		} catch (IOException e) {
			return true;
		}
	}

	// origin:
	// https://github.com/apache/camel/blob/master/components/camel-test/src/main/java/org/apache/camel/test/AvailablePortFinder.java
	// unused
	public static boolean isAvailable(int port) {
		if (port < 1100 || port > 65535) {
			throw new IllegalArgumentException(
					"Not a valid IPv4 port value: " + port);
		}

		ServerSocket serverSocket = null;
		DatagramSocket datagramSocket = null;
		try {
			serverSocket = new ServerSocket(port);
			serverSocket.setReuseAddress(true);
			datagramSocket = new DatagramSocket(port);
			datagramSocket.setReuseAddress(true);
			return true;
		} catch (IOException e) {
			// no op
		} finally {
			if (datagramSocket != null) {
				datagramSocket.close();
			}

			if (serverSocket != null) {
				try {
					serverSocket.close();
				} catch (IOException e) {
					// no op
				}
			}
		}
		return false;
	}
}
