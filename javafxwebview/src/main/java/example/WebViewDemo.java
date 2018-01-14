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
// TODO: https://stackoverflow.com/questions/25222811/access-restriction-the-type-application-is-not-api-restriction-on-required-l

// based on: https://github.com/lipido/javafxwebview 
public class WebViewDemo extends Application {

	private final String PAGE = "/index.html";

	@Override
	public void start(Stage stage) {
		createWebView(stage, PAGE);
	}

	private void createWebView(Stage stage, String page) {

		final WebView webView = new WebView();

		connectBackendObject(webView.getEngine(), "fruitsService",
				new FruitsService());

		connectBackendObject(webView.getEngine(), "calculatorService",
				new CalculatorService());

		// show "alert" Javascript messages in stderr for debuging
		webView.getEngine().setOnAlert(new EventHandler<WebEvent<String>>() {
			@Override
			public void handle(WebEvent<String> arg) {
				System.err.println("alertwb1: " + arg.getData());
			}
		});

		// load index.html
		webView.getEngine().load(getClass().getResource(page).toExternalForm());

		stage.setScene(new Scene(webView));
		stage.setTitle("WebView with Java backend");
		stage.show();
	}

	public static void main(String[] args) {
		System.setProperty("prism.lcdtext", "false"); // enhance fonts
		launch(args);
	}
}
