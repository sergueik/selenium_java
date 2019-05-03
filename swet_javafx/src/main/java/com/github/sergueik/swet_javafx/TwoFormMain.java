package com.github.sergueik.swet_javafx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

@SuppressWarnings("restriction")
public class TwoFormMain extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {
		final FXMLLoader loader = new FXMLLoader();

		loader.setLocation(getClass().getResource("/sample.fxml"));

		Pane rootLayout = (Pane) loader.load();
		primaryStage.setScene(new Scene(rootLayout));
		// Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
		// primaryStage.setScene(new Scene(root, 600, 400));
		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}