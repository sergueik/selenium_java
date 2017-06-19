package org.app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * @author twistezo (13.03.2017)
 */
public class Gui extends Application {

	@Override
	public void start(Stage stage) throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource("/scene.fxml"));
		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.setTitle("Daily free book downloader");
		stage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}

}
