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
	public void start(Stage primaryStage) {
		createWebView(primaryStage, PAGE);
	}

	private void createWebView(Stage primaryStage, String page) {

		final WebView webView = new WebView();

		connectBackendObject(webView.getEngine(), "fruitsService", new FruitsService());

		connectBackendObject(webView.getEngine(), "calculatorService", new CalculatorService());

		// print messages from invoking "alert" Javascript to stdout
		// useful to debug, does not appear to work
		webView.getEngine().setOnAlert(new EventHandler<WebEvent<String>>() {
			@Override
			public void handle(WebEvent<String> arg0) {
				System.err.println("alertwb1: " + arg0.getData());
			}
		});

		// load index.html
		webView.getEngine().load(getClass().getResource(page).toExternalForm());

		primaryStage.setScene(new Scene(webView));
		primaryStage.setTitle("WebView with Java backend");
		primaryStage.show();
	}

	public static void main(String[] args) {
		System.setProperty("prism.lcdtext", "false"); // enhance fonts
		launch(args);
	}
}
