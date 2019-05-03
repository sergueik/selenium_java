package com.github.sergueik.swet_javafx;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

@SuppressWarnings("restriction")
public class FirstFormController {

	
	private Scene newScene;
	private Stage stage;

	public Scene getNewScene() {
		return newScene;
	}

	public void setNewScene(Scene newScene) {
		this.newScene = newScene;
	}

	public Stage getStage() {
		return stage;
	}

	public void setStage(Stage stage) {
		this.stage = stage;
	}

	@FXML
	public Button btn1;
	

	
	
	public void actionBtn(ActionEvent actionEvent) {
		stage = new Stage();
		try {
			// Parent root = FXMLLoader.load(getClass().getResource("test.fxml"));
			// stage.setScene(new Scene(root, 600, 400));

			final FXMLLoader loader = new FXMLLoader();

			loader.setLocation(getClass().getResource("/test.fxml"));
			Pane rootLayout = (Pane) loader.load();
			newScene = new Scene(rootLayout, 200, 300);
			newScene.setUserData(this);
			stage.setScene(newScene);
			stage.show();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@FXML
	void initialize() {
	}
}
