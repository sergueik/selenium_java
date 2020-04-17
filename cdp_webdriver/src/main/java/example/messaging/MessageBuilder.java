package example.messaging;

import static java.lang.System.err;
import static java.lang.System.out;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.apache.commons.codec.binary.Base64;

import com.google.gson.Gson;

import example.utils.Utils;

public class MessageBuilder {

	private static String method = null;
	private static Message message = null;
	private static Map<String, Object> params = new HashMap<>();
	private static Map<String, Object> data = new HashMap<>();

	private static class Message {
		// no need for getters or setters in the static nested class
		@SuppressWarnings("unused")
		private int id;
		@SuppressWarnings("unused")
		private String method;
		private Map<String, Object> params;
		private static final Gson gson = new Gson();

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
			return gson.toJson(this);
		}
	}

	private static String buildMessage(int id, String method) {
		return (new Message(id, method)).toJson();
	}

	private static String buildMessage(int id, String method,
			Map<String, Object> params) {
		message = new Message(id, method);
		for (String key : params.keySet()) {
			message.addParam(key, params.get(key));
		}
		return message.toJson();
	}

	public static String buildGeoLocationMessage(int id, double latitude,
			double longitude) {
		method = "Emulation.setGeolocationOverride";
		params = new HashMap<>();
		params.put("latitude", latitude);
		params.put("longitude", longitude);
		params.put("accuracy", 100);
		return buildMessage(id, method, params);
		/*
		 * return
		 * String.format("{\"id\":%s,\"method\":\"Emulation.setGeolocationOverride\"," +
		 * "\"params\":{\"latitude\":%s,\"longitude\":%s,\"accuracy\":100}}",id,latitude
		 * ,longitude);
		 */
	}

	// https://chromedevtools.github.io/devtools-protocol/tot/Network/#method-setExtraHTTPHeaders
	public static String buildNetWorkSetExtraHTTPHeadersMessage(int id,
			Map<String, String> headers) {
		method = "Network.setExtraHTTPHeaders";
		params = new HashMap<>();
		params.put("headers", headers);
		return buildMessage(id, method, params);
		/*
		 * return String.format(
		 * "{\"id\":%s,\"method\":\"Network.setExtraHTTPHeaders\",\"params\":{\"headers\":{\"header key\":\"header value\"}}}",
		 * id);
		 */
	}

	// https://chromedevtools.github.io/devtools-protocol/tot/Network/#method-setExtraHTTPHeaders
	public static String buildNetWorkSetExtraHTTPHeadersMessage(int id,
			String headerKey, String headerValue) {
		method = "Network.setExtraHTTPHeaders";
		params = new HashMap<>();
		Map<String, String> headers = new HashMap<>();
		headers.put(headerKey, headerValue);
		params.put("headers", headers);
		return buildMessage(id, method, params);
		/*
		 * return String.format(
		 * "{\"id\":%s,\"method\":\"Network.setExtraHTTPHeaders\",\"params\":{\"headers\":{\"%s\":\"%s\"}}}",
		 * id, headerKey, headerValue);
		 */
	}

	public static String buildGetResponseBodyMessage(int id, String requestId) {
		method = "Network.getResponseBody";
		params = new HashMap<>();
		params.put("requestId", requestId);
		return buildMessage(id, method, params);
		/*
		 * return String.format(
		 * "{\"id\":%s,\"method\":\"Network.getResponseBody\",\"params\":{\"requestId\":\"%s\"}}",
		 * id, requestId);
		 */
	}

	// https://chromedevtools.github.io/devtools-protocol/tot/Network/#method-enable
	public static String buildNetWorkEnableMessage(int id) {
		method = "Network.enable";
		params = new HashMap<>();
		params.put("maxTotalBufferSize", 10000000);
		params.put("maxResourceBufferSize", 5000000);
		params.put("maxPostDataSize", 5000000);
		return buildMessage(id, method, params);
		/*
		 * return String.format(
		 * "{\"id\":%s,\"method\":\"Network.enable\",\"params\":{\"maxTotalBufferSize\":10000000,\"maxResourceBufferSize\":5000000, \"maxPostDataSize\":5000000}}",
		 * id);
		 */
	}

	public static String buildRequestInterceptorPatternMessage(int id,
			String urlPattern, String resourceType) {
		method = "Network.setRequestInterception";
		params = new HashMap<>();
		data = new HashMap<>();
		data.put("urlPattern", urlPattern);
		data.put("resourceType", resourceType);
		data.put("interceptionStage", "HeadersReceived");

		List<Map<String, Object>> patterns = new ArrayList<>();
		patterns.add(data);
		params.put("patterns", patterns);
		return buildMessage(id, method, params);
		/*
		 * return String.format( "{\"id\":%s," +
		 * "\"method\":\"Network.setRequestInterception\"," +
		 * "\"params\":{\"patterns\":[{\"urlPattern\":\"%s\",\"resourceType\":\"%s\",\"interceptionStage\":\"HeadersReceived\"}]}}",
		 * id, urlPattern, resourceType);
		 */
	}

	public static String buildGetResponseBodyForInterceptionMessage(int id,
			String interceptionId) {
		method = "Network.getResponseBodyForInterception";
		params = new HashMap<>();
		params.put("interceptionId", interceptionId);
		return buildMessage(id, method, params);
		/*
		 * return String.format(
		 * "{\"id\":%s,\"method\":\"Network.getResponseBodyForInterception\"," +
		 * "\"params\":{\"interceptionId\":\"%s\"}}", id, interceptionId);
		 */
	}

	// https://chromedevtools.github.io/devtools-protocol/tot/Network#method-continueInterceptedRequest
	public static String buildGetContinueInterceptedRequestMessage(int id,
			String interceptionId, String rawResponse) {
		method = "Network.getResponseBodyForInterception";
		params = new HashMap<>();
		params.put("interceptionId", interceptionId);
		params.put("rawResponse",
				new String(Base64.encodeBase64(rawResponse.getBytes())));
		return buildMessage(id, method, params);
		/*
		 * return String.format(
		 * "{\"id\":%s,\"method\":\"Network.continueInterceptedRequest\","+
		 * "\"params\":{\"interceptionId\":\"%s\",\"rawResponse\":\"%s\"}}", id,
		 * interceptionId, new String(Base64.encodeBase64(rawResponse.getBytes())));
		 */
	}

	// TODO: debug the role of encoded
	public static String buildGetContinueInterceptedRequestEncodedMessage(int id,
			String interceptionId, String encodedResponse) {
		method = "Network.getResponseBodyForInterception";
		params = new HashMap<>();
		params.put("interceptionId", interceptionId);
		params.put("rawResponse", encodedResponse);
		return buildMessage(id, method, params);
		/*
		 * return String.format(
		 * "{\"id\":%s,\"method\":\"Network.continueInterceptedRequest\",\"params\":{\"interceptionId\":\"%s\",\"rawResponse\":\"%s\"}}",
		 * id, interceptionId, encodedResponse);
		 */
	}

	public static String buildGetDocumentMessage(int id) {
		return buildMessage(id, "DOM.getDocument");
		/*
		return String.format("{\"id\":%s,\"method\":\"DOM.getDocument\"}", id);
		*/
	}

	public static String buildDescribeNodeMessage(int id, long nodeId) {
		method = "DOM.describeNode";
		params = new HashMap<>();
		params.put("nodeId", nodeId);
		params.put("depth", 1);
		return buildMessage(id, method, params);
		/*
		return String.format("{\"id\":%s,\"method\":\"DOM.describeNode\"}\",\"params\":{\"nodeId\":\"%d\",\"depth\":\"%d\"}}", id, nodeId, 1);
		*/
	}

	public static String buildQuerySelectorMessage(int id, long nodeId,
			String selector) {
		method = "DOM.querySelector";
		params = new HashMap<>();
		params.put("nodeId", nodeId);
		params.put("selector", selector);
		return buildMessage(id, method, params);
		/*
		return String.format("{\"id\":%s,\"method\":\"DOM.querySelector\"}\",\"params\":{\"nodeId\":\"%d\", \"selector\":\"%s\"}}", id, nodeId, selector);
		*/
	}

	public static String buildServiceWorkerEnableMessage(int id) {
		return buildMessage(id, "ServiceWorker.enable");
		/*
		 * return String.format("{\"id\":%s,\"method\":\"ServiceWorker.enable\"}", id);
		 */
	}

	public static String buildBrowserVersionMessage(int id) {
		return buildMessage(id, "Browser.getVersion");
		/*
		 * return String.format("{\"id\":%s,\"method\":\"Browser.getVersion\"}", id);
		 */
	}

	public static String buildBrowserVersionMessage() {
		return buildMessage(Utils.getInstance().getDynamicID(),
				"Browser.getVersion");
		/*
		 * return String.format("{\"id\":%s,\"method\":\"Browser.getVersion\"}", id);
		 */
	}

	public static String buildServiceWorkerInspectMessage(int id,
			String versionId) {
		method = "ServiceWorker.inspectWorker";
		params = new HashMap<>();
		params.put("versionId", versionId);
		return buildMessage(id, method, params);
		/*
		 * return String.format(
		 * "{\"id\":%s,\"method\":\"ServiceWorker.inspectWorker\"," +
		 * "\"params\":{\"versionId\":\"%s\"}}", id, "versionId");
		 */
	}

	public static String buildEnableLogMessage(int id) {
		return buildMessage(id, "Log.enable");
		/*
		 * return String.format("{\"id\":%d,\"method\":\"Log.enable\"}", id);
		 */
	}

	public static String buildprintPDFMessage() {
		return buildPrintPDFMessage(Utils.getInstance().getDynamicID());
	}

	public static String buildPrintPDFMessage(int id) {
		method = "Page.printToPDF";
		params = new HashMap<>();
		params.put("landscape", false);
		params.put("displayHeaderFooter", false);
		params.put("printBackground", true);
		params.put("preferCSSPageSize", true);
		return buildMessage(id, method, params);
		/*
		 * return String.format("{\"id\":%d,\"method\":\"Page.printToPDF\", " +
		 * "\"params\":{\"landscape\":%b,\"displayHeaderFooter\":%b,\"printBackground\":%b,\"preferCSSPageSize\":%b}}",
		 * id, false, false, true, true);
		 */
		// NOTE: {"error":{"code":-32602,"message":"Invalid parameters"
		// ,"data":"landscape: boolean value expected...
	}

	public static String buildEnableRuntimeMessage(int id) {
		return buildMessage(id, "Runtime.enable");
		/*
		 * return String.format("{\"id\":%d,\"method\":\"Runtime.enable\"}", id);
		 */
	}

	public static String buildSendPushNotificationMessage(int id, String origin,
			String registrationId, String data) {
		method = "ServiceWorker.deliverPushMessage";
		params = new HashMap<>();
		params.put("origin", origin);
		params.put("registrationId", registrationId);
		params.put("data", data);
		return buildMessage(id, method, params);
		/*
		 * return String.format(
		 * "{\"id\":%s,\"method\":\"ServiceWorker.deliverPushMessage\","+
		 * "\"params\":{\"origin\":\"%s\",\"registrationId\":\"%s\",\"data\":\"%s\"}}",
		 * id, origin, registrationId, data);
		 */
	}

	public static String buildObserveBackgroundServiceMessage(int id) {
		method = "BackgroundService.startObserving";
		params = new HashMap<>();
		params.put("service", "pushMessaging");
		return buildMessage(id, method, params);
		/*
		 * return
		 * String.format("{\"id\":%s,\"method\":\"BackgroundService.startObserving\"," +
		 * "\"params\":{\"service\":\"%s\"}}" id, "pushMessaging");
		 */
	}

	public static String buildGetBrowserContextMessage(int id) {
		return buildMessage(id, "Target.getBrowserContexts");
		/*
		 * return String.format("{\"id\":%d,\"method\":\"Target.getBrowserContexts\"}",
		 * id);
		 */
	}

	public static String buildClearBrowserCacheMessage(int id) {
		return buildMessage(id, "Network.clearBrowserCache");
		/*
		 * return String.format("{\"id\":%d,\"method\":\"Network.clearBrowserCache\"}",
		 * id);
		 */
	}

	public static String buildClearBrowserCookiesMessage(int id) {
		return buildMessage(id, "Network.clearBrowserCookies");
		/*
		 * return
		 * String.format("{\"id\":%d,\"method\":\"Network.clearBrowserCookies\"}", id);
		 */
	}

	public static String buildClearDataForOriginMessage(int id, String url) {
		method = "Storage.clearDataForOrigin";
		params = new HashMap<>();
		params.put("url", url);
		params.put("storageTypes", "all");
		return buildMessage(id, method, params);
		/*
		 * return String.format("{\"id\":%s,\"method\":\"Storage.clearDataForOrigin\","
		 * + "\"params\":{\"origin\":\"%s\",\"storageTypes\":\"all\"}}", id, url);
		 */
	}

	public static String buildTakeElementScreenShotMessage(int id, long x, long y,
			long height, long width, int scale) {
		method = "Page.captureScreenshot";
		params = new HashMap<>();
		data = new HashMap<>();
		data.put("x", x);
		data.put("y", y);
		data.put("height", height);
		data.put("width", width);
		data.put("scale", 100);
		params.put("clip", data);
		return buildMessage(id, method, params);
		/*
		 * return String.format("{\"id\":%s,\"method\":\"Page.captureScreenshot\"," +
		 * "\"params\":{\"clip\":{\"x\":%s,\"y\":%s,\"width\":%s,\"height\":%s,\"scale\":%s}}}",
		 * id, x, y, width, height, scale);
		 */
	}

	public static String buildTakePageScreenShotMessage(int id) {
		return buildMessage(id, "Page.captureScreenshot");
	}

	public static String buildRequestInterceptorEnabledMessage() {
		String method = "Network.setRequestInterception";
		int id = 4;
		message = new Message(id, method);
		params = new HashMap<>();
		params.put("enabled", true);
		return buildMessage(id, method, params);
		/*
		 * return String.format(
		 * "{\"id\":4, \"method\":\"Network.setRequestInterception\"," +
		 * "\"params\":{\"enabled\":true}}");
		 */
	}

	public static String buildBasicHttpAuthenticationMessage(String username,
			String password) {
		byte[] encodedBytes = Base64
				.encodeBase64(String.format("%s:%s", username, password).getBytes());
		String base64EncodedCredentials = new String(encodedBytes);
		String method = "Network.setExtraHTTPHeaders";
		int id = 2;
		params = new HashMap<>();
		data = new HashMap<>();
		data.put("Authorization",
				String.format("Basic %s", base64EncodedCredentials));
		params.put("headers", data);
		return buildMessage(id, method, params);
		/*
		 * return String.format( "{\"id\":2,\"method\":\"Network.setExtraHTTPHeaders\","
		 * + "\"params\":{\"headers\":{\"Authorization\":\"Basic %s\"}}}",
		 * base64EncodedCredentials);
		 */
	}

	public static String buildSendObservingPushMessage() {
		String method = "BackgroundService.clearEvents";
		message = new Message(Utils.getInstance().getDynamicID(), method);
		params = new HashMap<>();
		params.put("service", "backgroundFetch");
		return buildMessage(Utils.getInstance().getDynamicID(), method, params);
		/*
		 * return String.format(
		 * "{\"id\":%d,\"method\":\"BackgroundService.clearEvents\"," +
		 * "\"params\":{\"service\":\"backgroundFetch\"}}",
		 * Utils.getInstance().getDynamicID());
		 */
	}

	public static String buildSetUserAgentOverrideMessage(String userAgent,
			String platform) {
		String method = "Network.setUserAgentOverride";
		message = new Message(Utils.getInstance().getDynamicID(), method);
		params = new HashMap<>();
		params.put("userAgent", userAgent);
		params.put("platform", platform);
		return buildMessage(Utils.getInstance().getDynamicID(), method, params);
	}

	private String buildAttachToTargetMessage(String targetId) {

		String method = "BackgroundService.clearEvents";
		message = new Message(Utils.getInstance().getDynamicID(), method);
		params = new HashMap<>();
		params.put("targetId", targetId);
		return buildMessage(Utils.getInstance().getDynamicID(), method, params);
		/*
		 * return String.format("{\"id\":%d,\"method\":\"Target.attachToTarget\"," +
		 * "\"params\":{\"targetId\":\"%s\"}}", Utils.getInstance().getDynamicID(),
		 * targetId);
		 */
	}
}
