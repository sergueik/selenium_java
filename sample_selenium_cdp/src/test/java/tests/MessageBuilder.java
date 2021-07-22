package tests;

import org.apache.commons.codec.binary.Base64;

import java.lang.reflect.Array;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class MessageBuilder {
	protected String method;
	int id;
	protected Map<String, Object> params;
	private Map<String, Array> param;

	MessageBuilder(String method) {
		this.method = method;
	}

	public void addParam(String key, Object value) {
		if (Objects.isNull(params)) {
			params = new HashMap<>();
		}
		params.put(key, value);
	}

	public void addArrayParam(String key, Array value) {
		if (Objects.isNull(params)) {
			param = new HashMap<>();
		}
		params.put(key, value);
	}

	public static String buildRequestInterceptorPatternMessage(int id,
			String pattern, String resourceType) {
		String message = String.format(
				"{\"id\":%s,\"method\":\"Network.setRequestInterception\",\"params\":{\"patterns\":[{\"urlPattern\":\"%s\",\"resourceType\":\"%s\",\"interceptionStage\":\"HeadersReceived\"}]}}",
				id, pattern, resourceType);
		return message;
	}

	public static String buildGetContinueInterceptedRequestEncodedMessage(int id,
			String interceptionId, String encodedResponse) {
		String message = String.format(
				"{\"id\":%s,\"method\":\"Network.continueInterceptedRequest\",\"params\":{\"interceptionId\":\"%s\",\"rawResponse\":\"%s\"}}",
				id, interceptionId, encodedResponse);
		return message;
	}

	public static String buildGetContinueInterceptedRequestMessage(int id,
			String interceptionId, String response) {
		String encodedResponse = new String(
				Base64.encodeBase64(response.getBytes()));
		String message = String.format(
				"{\"id\":%s,\"method\":\"Network.continueInterceptedRequest\",\"params\":{\"interceptionId\":\"%s\",\"rawResponse\":\"%s\"}}",
				id, interceptionId, encodedResponse);
		return message;
	}

}
