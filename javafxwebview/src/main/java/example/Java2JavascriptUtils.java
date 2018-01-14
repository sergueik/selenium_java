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

/**
 * Utilities to connect Java objects as javascript objects in a WebEngine.
 * 
 * It also allows to call callback functions.
 * 
 * @author lipido
 */
public class Java2JavascriptUtils {

	private static Map<WebEngine, Map<String, Object>> backendObjects = new HashMap<>();
	private static Set<WebEngine> webEnginesWithAlertChangeListener = new HashSet<>();

	private static boolean changing = false;

	public static void connectBackendObject(final WebEngine webEngine,
			final String varname, final Object backend) {

		registerBackendObject(webEngine, varname, backend);

		// create a onAlertChangeListener. We always want to listen
		// to onAlert events, since via this event, the javascript front-end
		// will send us an special "alert" message asking to connect the
		// backend object as soon as possible(*).
		// However, if the programmer also wants to set
		// his own onAlert handler for this web engine,
		// we will create a handlerwrapper with our
		// behavior plus the programmer's one.

		// (*) It was impossible for me to re-connect the backend object
		// when the users navigates from one page to another page where the
		// backend object was also needed. The navigation erases any javascript
		// variables, so the backend has to be reconnected. However,
		// The recommended state change listeners on
		// webEngine were executed too late, after javascript code asking for the
		// backend object is executed, so it was not a solution.
		// The only way I found is to place a custom javacript "signaling"
		// code to ask Java to reconnect the backend object.
		// The solution was "alert", because we can listen to alert calls from
		// javascript, so via an special "alert" message, we can connect the
		// backend object again.
		// It is not a bad solution, because the programmer has only to inlude
		// a simple additional script (such as "mybackend.js") in the page
		// before any other scripts uses the backend variable.
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
		// System.err.println("Calling: " + call);
		try {
			callback.eval(call);
		} catch (JSException e) {
			// angular  Error: [$rootScope:infdig]
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
		public void handle(WebEvent<String> arg0) {
			if (arg0.getData().contains(CONNECT_BACKEND_MAGIC_WORD)) {
				String varname = arg0.getData()
						.substring(CONNECT_BACKEND_MAGIC_WORD.length());

				connectToWebEngine(engine, varname);
			} else if (wrappedHandler != null)
				wrappedHandler.handle(arg0);
		}
	}
}