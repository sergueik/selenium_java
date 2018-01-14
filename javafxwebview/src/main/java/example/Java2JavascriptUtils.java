package example;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebEvent;

import java.net.URLDecoder;

import netscape.javascript.JSException;
import netscape.javascript.JSObject;

public class Java2JavascriptUtils {

	private static Map<WebEngine, Map<String, Object>> backendObjects = new HashMap<>();
	private static Set<WebEngine> webEnginesWithAlertChangeListener = new HashSet<>();

	private static boolean changing = false;

	@SuppressWarnings("restriction")
	public static void connectBackendObject(final WebEngine webEngine,
			final String varname, final Object backend) {

		registerBackendObject(webEngine, varname, backend);

		// create a onAlertChangeListener
		if (!webEnginesWithAlertChangeListener.contains(webEngine)) {
			if (webEngine.getOnAlert() == null) {
				webEngine.setOnAlert(new AlertEventHandlerWrapper(webEngine, null));
			}

			webEngine.onAlertProperty()
					.addListener(new ChangeListener<EventHandler<WebEvent<String>>>() {

						@Override
						public void changed(
								ObservableValue<? extends EventHandler<WebEvent<String>>> arg0,
								EventHandler<WebEvent<String>> previous,
								final EventHandler<WebEvent<String>> newHandler) {

							if (!changing) { // avoid recursive calls
								changing = true;
								webEngine.setOnAlert(
										new AlertEventHandlerWrapper(webEngine, newHandler));
								changing = false;
							}
						}
					});
		}
		webEnginesWithAlertChangeListener.add(webEngine);
	}

	private static void registerBackendObject(final WebEngine webEngine,
			final String varname, final Object backend) {
		Map<String, Object> webEngineBridges = backendObjects.get(webEngine);
		if (webEngineBridges == null) {
			webEngineBridges = new HashMap<>();
			backendObjects.put(webEngine, webEngineBridges);

		}
		webEngineBridges.put(varname, backend);

	}

	@SuppressWarnings("restriction")
	private static void connectToWebEngine(WebEngine engine, String varname) {
		if (backendObjects.containsKey(engine)
				&& backendObjects.get(engine).containsKey(varname)) {

			@SuppressWarnings("restriction")
			JSObject window = (JSObject) engine.executeScript("window");
			window.setMember(varname, backendObjects.get(engine).get(varname));
		}
	}

	@SuppressWarnings({ "restriction", "deprecation" })
	public static void call(JSObject callback, Object... arguments) {
		String argumentsList = "";
		for (int i = 0; i < arguments.length; i++) {
			callback.setMember("___res___" + i, arguments[i]);
			argumentsList += "this.___res___" + i;
			if (i != arguments.length - 1) {
				argumentsList += ",";
			}
		}

		String call = "this(" + argumentsList + ")";
		try {
			callback.eval(call);
		} catch (JSException e) {
			System.err
					.println("Exception (ignored): " + URLDecoder.decode(e.getMessage()));
		}
	}

	private final static class AlertEventHandlerWrapper
			implements EventHandler<WebEvent<String>> {

		private static final String CONNECT_BACKEND_MAGIC_WORD = "__CONNECT__BACKEND__";
		private final EventHandler<WebEvent<String>> wrappedHandler;
		private WebEngine engine;

		private AlertEventHandlerWrapper(WebEngine engine,
				EventHandler<WebEvent<String>> wrappedHandler) {

			this.engine = engine;
			this.wrappedHandler = wrappedHandler;
		}

		@Override
		public void handle(WebEvent<String> input) {
			if (input.getData().contains(CONNECT_BACKEND_MAGIC_WORD)) {
				String varname = input.getData()
						.substring(CONNECT_BACKEND_MAGIC_WORD.length());

				connectToWebEngine(engine, varname);
			} else if (wrappedHandler != null)
				wrappedHandler.handle(input);
		}
	}
}