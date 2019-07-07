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

	String host = "127.0.0.1";
	int port = 49156;

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

		System.out.println("result:" + result);
		if (result.success()) {
			System.out.println("send success.");
		} else {
			System.err.println("send fail!");
		}
	}
}
