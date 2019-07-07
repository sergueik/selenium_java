package com.github.sergueik.zabbix;

import java.util.List;
import org.json.JSONObject;

// import com.alibaba.fastjson.JSON;

public class SenderRequest {
	static final byte header[] = { 'Z', 'B', 'X', 'D', '\1' };

	String request = "sender data";

	/**
	 * TimeUnit is SECONDS.
	 */
	long clock;

	List<DataObject> data;

	public byte[] toBytes() {
		// https://www.zabbix.org/wiki/Docs/protocols/zabbix_sender/2.0
		// https://www.zabbix.org/wiki/Docs/protocols/zabbix_sender/1.8/java_example

		// byte[] jsonBytes = JSON.toJSONBytes(this);
		byte[] jsonBytes = JSONObject.valueToString(this).getBytes();

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

	/**
	 * TimeUnit is SECONDS.
	 *
	 * @return
	 */
	public long getClock() {
		return clock;
	}

	/**
	 * TimeUnit is SECONDS.
	 *
	 * @param clock
	 */
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
