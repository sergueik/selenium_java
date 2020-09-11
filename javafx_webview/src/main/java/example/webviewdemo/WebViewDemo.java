package example.webviewdemo;

import static example.webview.Java2JavascriptUtils.connectBackendObject;

import example.webviewdemo.backend.CalculatorService;
import example.webviewdemo.backend.FruitsService;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.web.WebEvent;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

@SuppressWarnings("restriction")
public class WebViewDemo extends Application {

	private final String PAGE = "/index.html";

	@Override
	public void start(Stage stage) {
		createWebView(stage, PAGE);
	}

	private void createWebView(Stage stage, String page) {

		final WebView webView = new WebView();

		connectBackendObject(webView.getEngine(), "fruitsService", new FruitsService());

		connectBackendObject(webView.getEngine(), "calculatorService", new CalculatorService());
		// set the JavaScript alert handler
		// to print Javascript "alert" argument to stdout
		// useful to debug, does not appear to work
		webView.getEngine().setOnAlert(new EventHandler<WebEvent<String>>() {
			@Override
			public void handle(WebEvent<String> e) {
				System.err.println("alert received: " + e.getData());
			}
		});

		// load web page
		webView.getEngine().load(getClass().getResource(page).toExternalForm());
		// Specify the scene to be used on this stage.
		stage.setScene(new Scene(webView));
		stage.setTitle("WebView with Java backend");
		stage.show();
	}

	public static void main(String[] args) {
		System.setProperty("prism.lcdtext", "false"); // enhance fonts
		Application.launch(args);
	}
}
