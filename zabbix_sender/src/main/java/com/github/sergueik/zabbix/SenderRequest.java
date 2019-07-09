package com.github.sergueik.zabbix;

import java.util.List;
import org.json.JSONObject;

public class SenderRequest {

	private boolean debug = false;
	public void setDebug(boolean debug) {
		this.debug = debug;
	}

	static private final byte header[] = { 'Z', 'B', 'X', 'D', '\1' };
	private String request = "sender data";
	private long clock;
	private List<DataObject> data;

	public byte[] toBytes() {

		// https://www.zabbix.org/wiki/Docs/protocols/zabbix_sender/2.0
		// https://www.zabbix.org/wiki/Docs/protocols/zabbix_sender/1.8/java_example

		String jsonString = new JSONObject(this).toString();

		if (debug) {
			System.err.println("Sending: " + jsonString);
			// Sending:
			/*
				{
				    "request": "sender data",
				    "data": [{
				        "host": "172.17.42.1",
				        "clock": 1562622751,
				        "value": "{\"data\":[{\"hello\":\"hello\"}]}",
				        "key": "healthcheck[dw,notificationserver]"
				    }],
				    "clock": 1562622751
				}			 
			 */
		}
		byte[] jsonBytes = jsonString.getBytes();

		byte[] result = new byte[header.length + 4 + 4 + jsonBytes.length];

		System.arraycopy(header, 0, result, 0, header.length);

		result[header.length] = (byte) (jsonBytes.length & 0xFF);
		result[header.length + 1] = (byte) ((jsonBytes.length >> 8) & 0x00FF);
		result[header.length + 2] = (byte) ((jsonBytes.length >> 16) & 0x0000FF);
		result[header.length + 3] = (byte) ((jsonBytes.length >> 24) & 0x000000FF);

		System.arraycopy(jsonBytes, 0, result, header.length + 4 + 4,
				jsonBytes.length);
		return result;
	}

	public String getRequest() {
		return request;
	}

	public void setRequest(String request) {
		this.request = request;
	}

	public long getClock() {
		return clock;
	}

	public void setClock(long clock) {
		this.clock = clock;
	}

	public List<DataObject> getData() {
		return data;
	}

	public void setData(List<DataObject> data) {
		this.data = data;
	}
}
