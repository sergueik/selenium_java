package example;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.web.WebEvent;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

import static example.Java2JavascriptUtils.connectBackendObject;

import example.CalculatorService;
import example.FruitsService;

public class WebViewDemo extends Application {

	private final String PAGE = "/index.html";

	@Override
	public void start(Stage primaryStage) {
		createWebView(primaryStage, PAGE);
	}

	private void createWebView(Stage primaryStage, String page) {

		// create the JavaFX webview
		final WebView webView = new WebView();

		// connect the FruitsService instance as "fruitsService"
		// javascript variable
		connectBackendObject(webView.getEngine(), "fruitsService",
				new FruitsService());

		// connect the CalculatorService instance as "calculatorService"
		// javascript variable
		connectBackendObject(webView.getEngine(), "calculatorService",
				new CalculatorService());

		// show "alert" Javascript messages in stdout (useful to debug)
		webView.getEngine().setOnAlert(new EventHandler<WebEvent<String>>() {
			@Override
			public void handle(WebEvent<String> arg) {
				System.err.println("alertwb1: " + arg.getData());
			}
		});

		// load index.html
		webView.getEngine().load(getClass().getResource(page).toExternalForm());

		primaryStage.setScene(new Scene(webView));
		primaryStage.setTitle("WebView with Java backend");
		primaryStage.show();
	}

	public static void main(String[] args) {
		// System.setProperty("prism.lcdtext", "false"); // enhance fonts
		launch(args);
	}
}
