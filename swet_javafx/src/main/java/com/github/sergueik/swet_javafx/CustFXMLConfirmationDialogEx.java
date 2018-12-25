package com.github.sergueik.swet_javafx;

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

	@Override
	public void start(Stage primaryStage) throws Exception {
		// without the absolute path, would search curent directory
		FXMLLoader fxmlLoader = new FXMLLoader();

		Parent root = /* FXMLLoader*/ fxmlLoader
				.load(getClass().getResource("/dialog_css_demo.fxml"));
		Scene scene = new Scene(root, 800, 450);
		scene.getStylesheets()
				.add(getClass().getResource("/dialog_css_demo.css").toExternalForm());
		VanillaControllerEx controller = fxmlLoader.getController();

		// controller.setMainStage(primaryStage);
		primaryStage.setTitle("Selenium Dialog (WIP)");

		primaryStage.setScene(scene);
		primaryStage.show();
	}

	public static void main(String[] args) {
		// buildConfirmationDialog();
		launch(args);
	}

	public static Alert buildConfirmationDialog(String title, String header,
			String content, ButtonType defaultButton) {
		return buildDialog(title, header, content, Alert.AlertType.CONFIRMATION,
				defaultButton);
	}

	private static Alert buildDialog(String title, String header, String content,
			Alert.AlertType type, ButtonType defaultButton) {
		Text contentText = new Text(content);
		contentText.setWrappingWidth(360.0);

		Alert alert = new Alert(type);
		alert.setTitle(title);
		alert.setHeaderText(header);
		alert.getDialogPane().setContent(contentText);
		DialogPane dialogPane = alert.getDialogPane();
		dialogPane.getStylesheets()
				.add(CustFXMLConfirmationDialogEx.class.getClassLoader()
						.getResource("/cust_confirmation_dialog.css").toExternalForm());
		dialogPane.getStyleClass().add("myDialog");

		alert.getDialogPane().getButtonTypes().stream().forEach(buttonType -> {
			Button btn = (Button) alert.getDialogPane().lookupButton(buttonType);
			btn.setDefaultButton(buttonType.equals(defaultButton));
		});

		return alert;
	}

}
