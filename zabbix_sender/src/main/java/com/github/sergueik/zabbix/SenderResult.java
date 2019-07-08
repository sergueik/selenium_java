package com.github.sergueik.zabbix;

// import com.alibaba.fastjson.JSON;
import org.json.JSONObject;

public class SenderResult {

	private int processed;
	private int failed;
	private int total;
	private float spentSeconds;

	// track if zabbix server returns an "[]" - sometimes zabbix server does
	private boolean emptyResponse = false;

	// will return true, if all sended data are processed, else return false.
	public boolean success() {
		return !emptyResponse && processed == total;
	}

	public int getProcessed() {
		return processed;
	}

	public void setProcessed(int processed) {
		this.processed = processed;
	}

	public int getFailed() {
		return failed;
	}

	public void setFailed(int failed) {
		this.failed = failed;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public float getSpentSeconds() {
		return spentSeconds;
	}

	public void setSpentSeconds(float spentSeconds) {
		this.spentSeconds = spentSeconds;
	}

	public void setemptyResponse(boolean emptyResponse) {
		this.emptyResponse = emptyResponse;
	}

	@Override
	public String toString() {
		try {
			return new JSONObject(this).toString();
		} catch (org.json.JSONException e) {
			e.printStackTrace();
			JSONObject obj = new JSONObject();
			obj.put("processed", this.getProcessed());
			obj.put("failed", this.getFailed());
			obj.put("total", this.getTotal());
			obj.put("spentSeconds", this.getSpentSeconds());
			// NOTE: debug how emptyResponse was serialized by
			// com.alibaba.fastjson.JSONObject
			return obj.toString();
		}

	}
}
