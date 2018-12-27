package com.github.sergueik.swet_javafx;

import java.util.HashMap;
import java.util.Map;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;

import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.text.Text;
import javafx.scene.control.*;

// based on: http://www.java2s.com/example/java/javafx/build-javafx-confirmation-dialog.html
// based on: https://github.com/callicoder/javafx-examples/blob/master/javafx-css-demo-app/src/javafx/example/CSSDemoApplication.java
@SuppressWarnings("restriction")
public class CustFXMLConfirmationDialogEx extends Application {

	@SuppressWarnings("unused")
	@Override
	public void start(Stage stage) throws Exception {
		FXMLLoader fxmlLoader = new FXMLLoader();
		Map<String, String> data = new HashMap<>();
		data.put("title", "Selenium Dialog (WIP)");
		data.put("close message", "Abort dialog is closed");
		data.put("header text", "The test is being aborted");
		data.put("summary message", "Exception in the code");
		data.put("continue button text", "Abort");
		data.put("code", "Exception in Application start method\n" +
"java.lang.reflect.InvocationTargetException\n" +
"        at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)\n" +
"        at sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62)\n" +
"        at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)\n" +
"        at java.lang.reflect.Method.invoke(Method.java:498)");
		// without the absolute path, would search current directory
		/* FXMLLoader*/ fxmlLoader
				.setLocation(getClass().getResource("/dialog_css_demo.fxml"));
		Parent parent = fxmlLoader.load();
		VanillaControllerEx controller = fxmlLoader.getController();
		controller.setMainStage(stage);

		Scene scene = new Scene(parent, 600, 650); // TODO: measure?
		scene.getStylesheets()
				.add(getClass().getResource("/dialog_css_demo.css").toExternalForm());
		data.put("summary",
				"Summary of the test");
		controller.setMainStage(stage);
		stage.setTitle(data.get("title"));

		stage.setScene(scene);
		stage.show();
		if (controller != null) {
			controller.setInputData(data);
		} else {
			System.err.println("Controller is not reachable.");
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}
