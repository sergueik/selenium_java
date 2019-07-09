package com.github.sergueik.zabbix;

import org.json.JSONObject;

// import com.alibaba.fastjson.JSON;

public class DataObject {

	private long clock;
	private String host;
	private String key;
	private String value;

	public DataObject() {

	}

	public DataObject(long clock, String host, String key, String value) {
		this.clock = clock;
		this.host = host;
		this.key = key;
		this.value = value;
	}

	static public Builder builder() {
		return new Builder();
	}

	public static class Builder {
		Long clock;
		String host;
		String key;
		String value;

		Builder() {

		}

		public Builder clock(long clock) {
			this.clock = clock;
			return this;
		}

		public Builder host(String host) {
			this.host = host;
			return this;
		}

		public Builder key(String key) {
			this.key = key;
			return this;
		}

		public Builder value(String value) {
			this.value = value;
			return this;
		}

		public DataObject build() {
			if (clock == null) {
				clock = System.currentTimeMillis() / 1000;
			}
			return new DataObject(clock, host, key, value);
		}
	}

	public long getClock() {
		return clock;
	}

	public void setClock(long clock) {
		this.clock = clock;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@Override
	public String toString() {
		JSONObject jsonObj;
		try {
			jsonObj = new JSONObject(this);
		} catch (org.json.JSONException e) {
			System.err.println(e.toString());
			jsonObj = new JSONObject();
			jsonObj.put("value", this.getValue());
			jsonObj.put("clock", this.getClock());
			jsonObj.put("host", this.getHost());
			jsonObj.put("key", this.getKey());
		}
		return jsonObj.toString();
	}

}
