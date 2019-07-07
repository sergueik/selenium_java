package com.github.sergueik.zabbix;

// import com.alibaba.fastjson.JSON;
import org.json.JSONObject;

public class SenderResult {
	int processed;
	int failed;
	int total;

	float spentSeconds;

	/**
	 * sometimes zabbix server will return "[]".
	 */
	boolean bReturnEmptyArray = false;

	/**
	 * if all sended data are processed, will return true, else return false.
	 * 
	 * @return
	 */
	public boolean success() {
		return !bReturnEmptyArray && processed == total;
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

	public void setbReturnEmptyArray(boolean bReturnEmptyArray) {
		this.bReturnEmptyArray = bReturnEmptyArray;
	}

	@Override
	public String toString() {
		try {
			return JSONObject.valueToString(this);
		} catch (org.json.JSONException e) {
			e.printStackTrace();
			JSONObject obj = new JSONObject();
			obj.put("processed", this.getProcessed());
			obj.put("failed", this.getFailed());
			obj.put("total", this.getTotal());
			obj.put("spentSeconds", this.getSpentSeconds());
			// NOTE: debug how bReturnEmptyArray is serialized by
			// com.alibaba.fastjson.JSONObject
			return obj.toString();
		}

	}
}
