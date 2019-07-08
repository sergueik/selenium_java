package com.github.sergueik.zabbix;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.json.JSONObject;
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
}
