package example.entity;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Result implements Serializable {

	private int status;
	private Object data;

	public int getStatus() {
		return status;
	}

	public void setStatus(int value) {
		status = value;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object value) {
		data = value;
	}
}
