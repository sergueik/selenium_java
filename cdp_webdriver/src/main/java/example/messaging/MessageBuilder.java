package example.messaging;

import static java.lang.System.err;
import static java.lang.System.out;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import org.apache.commons.codec.binary.Base64;

import com.google.gson.Gson;

import example.utils.Utils;

public class MessageBuilder {

	private static String method = null;
	private static Message message = null;
	private static Map<String, Object> params = new HashMap<>();
	private static Map<String, String> headers = new HashMap<>();

	public static String buildGeoLocationMessage(int id, double latitude,
			double longitude) {
		method = "Emulation.setGeolocationOverride";
		params = new HashMap<>();
		params.put("latitude", latitude);
		params.put("longitude", longitude);
		params.put("accuracy", 100);
		return buildMessage(id, method, params);
		// String message =
		// String.format("{\"id\":%s,\"method\":\"Emulation.setGeolocationOverride\",\"params\":{\"latitude\":%s,\"longitude\":%s,\"accuracy\":100}}",id,latitude,longitude);
	}

	public static String buildGetResponseBodyMessage(int id, String requestId) {
		/*
		String message = String.format(
				"{\"id\":%s,\"method\":\"Network.getResponseBody\",\"params\":{\"requestId\":\"%s\"}}",
				id, requestId);
				*/
		method = "Emulation.setGeolocationOverride";
		params = new HashMap<>();
		params.put("requestId", requestId);
		return buildMessage(id, method, params);
	}

	public static String buildNetWorkEnableMessage(int id) {
		method = "Network.enable";
		params = new HashMap<>();
		params.put("maxTotalBufferSize", 10000000);
		params.put("maxResourceBufferSize", 5000000);
		return buildMessage(id, method, params);
		/*
		String message = String.format(
				"{\"id\":%s,\"method\":\"Network.enable\",\"params\":{\"maxTotalBufferSize\":10000000,\"maxResourceBufferSize\":5000000}}",
				id);
				*/
		// return message;
	}

	public static String buildRequestInterceptorPatternMessage(int id,
			String pattern, String resourceType) {
		String message = String.format(
				"{\"id\":%s,\"method\":\"Network.setRequestInterception\",\"params\":{\"patterns\":[{\"urlPattern\":\"%s\",\"resourceType\":\"%s\",\"interceptionStage\":\"HeadersReceived\"}]}}",
				id, pattern, resourceType);
		return message;
	}

	public static String buildGetResponseBodyForInterceptionMessage(int id,
			String interceptionId) {
		String message = String.format(
				"{\"id\":%s,\"method\":\"Network.getResponseBodyForInterception\",\"params\":{\"interceptionId\":\"%s\"}}",
				id, interceptionId);
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

	public static String buildGetContinueInterceptedRequestEncodedMessage(int id,
			String interceptionId, String encodedResponse) {
		String message = String.format(
				"{\"id\":%s,\"method\":\"Network.continueInterceptedRequest\",\"params\":{\"interceptionId\":\"%s\",\"rawResponse\":\"%s\"}}",
				id, interceptionId, encodedResponse);
		return message;
	}

	public static String buildServiceWorkerEnableMessage(int id) {
		String message = String
				.format("{\"id\":%s,\"method\":\"ServiceWorker.enable\"}", id);
		return message;
	}

	public static String buildServiceWorkerInspectMessage(int id,
			String versionId) {
		String message = String.format(
				"{\"id\":%s,\"method\":\"ServiceWorker.inspectWorker\",\"params\":{\"versionId\":\"%s\"}}",
				id, "versionId");
		return message;
	}

	public static String buildEnableLogMessage(int id) {
		String message = String.format("{\"id\":%d,\"method\":\"Log.enable\"}", id);
		return message;
	}

	public static String printPDFMessage(int id) {
		// Received this ws message: {"error":{"code":-32602,"message":"Invalid
		// parameters" ,"data":"landscape: boolean value expected;
		// displayHeaderFooter: boolean value expected
		String message = String.format(
				"{\"id\":%d,\"method\":\"Page.printToPDF\", \"params\":{\"landscape\":%b,\"displayHeaderFooter\":%b,\"printBackground\":%b,\"preferCSSPageSize\":%b}}",
				id, false, false, true, true);
		// Received this ws message:
		// {"error":{"code":-32000,"message":"PrintToPDF is not
		// implemented"},"id":...}
		return message;
	}

	public static String buildEnableRuntimeMessage(int id) {
		String message = String.format("{\"id\":%d,\"method\":\"Runtime.enable\"}",
				id);
		return message;
	}

	public static String buildSendPushNotificationMessage(int id, String origin,
			String registrationId, String data) {
		String message = String.format(
				"{\"id\":%s,\"method\":\"ServiceWorker.deliverPushMessage\",\"params\":{\"origin\":\"%s\",\"registrationId\":\"%s\",\"data\":\"%s\"}}",
				id, origin, registrationId, data);
		return message;
	}

	public static String buildObserveBackgroundServiceMessage(int id) {
		String message = String.format(
				"{\"id\":%s,\"method\":\"BackgroundService.startObserving\",\"params\":{\"service\":\"%s\"}}",
				id, "pushMessaging");
		return message;
	}

	public static String buildGetBrowserContextMessage(int id) {
		String message = String
				.format("{\"id\":%d,\"method\":\"Target.getBrowserContexts\"}", id);
		return message;
	}

	public static String buildClearBrowserCacheMessage(int id) {
		String message = String
				.format("{\"id\":%d,\"method\":\"Network.clearBrowserCache\"}", id);
		return message;
	}

	public static String buildClearBrowserCookiesMessage(int id) {
		String message = String
				.format("{\"id\":%d,\"method\":\"Network.clearBrowserCookies\"}", id);
		return message;
	}

	public static String buildClearDataForOriginMessage(int id, String url) {
		String message = String.format(
				"{\"id\":%s,\"method\":\"Storage.clearDataForOrigin\",\"params\":{\"origin\":\"%s\",\"storageTypes\":\"all\"}}",
				id, url);
		return message;
	}

	public static String buildTakeElementScreenShotMessage(int id, long x, long y,
			long height, long width, int scale) {
		String message = String.format(
				"{\"id\":%s,\"method\":\"Page.captureScreenshot\",\"params\":{\"clip\":{\"x\":%s,\"y\":%s,\"width\":%s,\"height\":%s,\"scale\":%s}}}",
				id, x, y, width, height, scale);
		return message;
	}

	public static String buildMessage(int id, String method) {
		return (new Message(id, method)).toJson();
	}

	public static String buildMessage(int id, String method,
			Map<String, Object> params) {
		message = new Message(id, method);
		for (String key : params.keySet()) {
			message.addParam(key, params.get(key));
		}
		return message.toJson();
	}

	public static String buildTakePageScreenShotMessage(int id) {
		String method = "Page.captureScreenshot";
		return buildMessage(id, method, null);
	}

	private String buildRequestInterceptorEnabledMessage() {
		String method = "Network.setRequestInterception";
		int id = 4;
		message = new Message(id, method);
		params = new HashMap<>();
		params.put("enabled", true);
		message.addParam("enabled", true);
		return buildMessage(id, method, params);
		/*
		String message = String.format(
				"{\"id\":4,\"method\":\"Network.setRequestInterception\",\"params\":{\"enabled\":true}}");
		System.out.println(message);
		*/
	}

	private String buildBasicHttpAuthenticationMessage(String username,
			String password) {
		byte[] encodedBytes = Base64
				.encodeBase64(String.format("%s:%s", username, password).getBytes());
		String base64EncodedCredentials = new String(encodedBytes);
		String method = "Network.setExtraHTTPHeaders";
		int id = 2;
		params = new HashMap<>();
		headers = new HashMap<>();
		headers.put("Authorization",
				String.format("Basic %s", base64EncodedCredentials));
		params.put("headers", headers);
		return buildMessage(id, method, params);

		/*
		String message = String.format(
				"{\"id\":2,\"method\":\"Network.setExtraHTTPHeaders\",\"params\":{\"headers\":{\"Authorization\":\"Basic %s\"}}}",
				base64EncodedCredentials);
		System.err.println(message);
		*/
	}

	private String buildSendObservingPushMessage() {
		int id = Utils.getInstance().getDynamicID();
		String message = String.format(
				"{\"id\":%d,\"method\":\"BackgroundService.clearEvents\",\"params\":{\"service\":\"backgroundFetch\"}}",
				id);
		System.out.println(message);
		return message;
	}

	private String buildAttachToTargetMessage(String targetId) {
		int id = Utils.getInstance().getDynamicID();
		String message = String.format(
				"{\"id\":%d,\"method\":\"Target.attachToTarget\",\"params\":{\"targetId\":\"%s\"}}",
				id, targetId);
		System.out.println(message);
		return message;
	}

	private static class Message {
		private int id;
		private String method;
		private Map<String, Object> params;

		public Message(int id, String method) {
			this.id = id;
			this.method = method;
		}

		public void addParam(String key, Object value) {
			if (Objects.isNull(params))
				params = new HashMap<>();
			params.put(key, value);
		}

		public String toJson() {
			Gson gson = new Gson();
			return gson.toJson(this);
		}
	}

}
