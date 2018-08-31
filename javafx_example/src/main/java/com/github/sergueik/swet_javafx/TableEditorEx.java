package com.github.sergueik.swet_javafx;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Category;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.scene.control.*;

import org.apache.log4j.Category;

public class TableEditorEx extends Application {

	@SuppressWarnings("deprecation")
	static final Category logger = Category.getInstance(TableEditorEx.class);

	private Scene scene = null;

	public void setScene(Scene scene) {
		this.scene = scene;
	}

	@SuppressWarnings({ "restriction", "unchecked" })
	@Override
	public void start(Stage stage) throws Exception {

		FXMLLoader fxmlLoader = new FXMLLoader();
		fxmlLoader.setLocation(getClass().getResource("/main.fxml"));
		Parent fxmlMain = fxmlLoader.load();
		DataFormControllerEx controller = fxmlLoader.getController();
		controller.setMainStage(stage);
		Map<String, Object> inputs = new HashMap<>();

		Map<String, String> inputData = new HashMap<>();
		inputData.put("title", "Step detail");
		if (scene != null) {
			try {
				inputs = (Map<String, Object>) scene.getUserData();
				inputData = (Map<String, String>) inputs.get("inputs");
				logger.info("Loaded " + inputData.toString());
			} catch (ClassCastException e) {
				logger.info("Exception (ignored) " + e.toString());
			} catch (Exception e) {
				throw e;
			}

		}
		if (inputData.keySet().size() == 0) {
			throw new IllegalArgumentException("You must provide data");
		}

		stage.setTitle(inputData.get("title"));
		stage.setScene(new Scene(fxmlMain, 700, 600));
		// stage.setResizable(false);
		// CheckConnection();
		stage.show();
		stage.getIcons().add(new Image("/ico.png"));

	}

	public static void main(String[] args) {
		launch(args);
	}
}
