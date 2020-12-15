package com.github.sergueik.swet_javafx;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Control;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.Group;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.RowConstraints;

import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.paint.Color;
import javafx.scene.Scene;
import javafx.stage.Stage;

import org.apache.log4j.Category;
import org.apache.log4j.Category;

import org.controlsfx.tools.Borders.Border;

// origin: 
@SuppressWarnings({ "restriction", "unused" })
public class ElementAttributeEditor extends Application {

	private Map<String, Object> inputs = new HashMap<>();
	private Map<String, String> inputData = new HashMap<>();
	@SuppressWarnings("deprecation")
	static final Category logger = Category.getInstance(TableEditorEx.class);

	private Scene parentScene = null;

	@SuppressWarnings({ "static-access" })
	@Override
	public void start(Stage stage) {
		BorderPane root = new BorderPane();
		stage.setTitle(inputData.containsKey("title") ? inputData.get("title")
				: "Element Locators");

		Scene scene = new Scene(root, 480, 250, Color.WHITE);
		// stage.setScene(new Scene(root, 480, 250, Color.WHITE));
		stage.setScene(scene);

		GridPane gridPane1 = new GridPane();

		GridPane gridPane = new GridPane();
		gridPane.setRowIndex(gridPane, 0);
		gridPane.setColumnIndex(gridPane, 0);

		HBox buttonbarHbox = new HBox(10);
		buttonbarHbox.setPadding(new Insets(10));
		// buttonbarHbox.setSpacing(50);
		// buttonbarHbox.setPadding(new Insets(10, 10, 10, 10));

		// gridPane.setVgap(5);
		gridPane.setRowIndex(buttonbarHbox, 1);
		gridPane.setColumnIndex(buttonbarHbox, 0);

		// Save button
		Button saveButton = new Button("Save");
		saveButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				System.out.println("Saved");
				Stage stage = (Stage) ((Button) (event.getSource())).getScene()
						.getWindow();
				stage.close();
			}
		});

		saveButton.setDefaultButton(true);

		// Delete button
		Button deleteButton = new Button("Delete");
		deleteButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				System.out.println("Deleted");
				Stage stage = (Stage) ((Button) (event.getSource())).getScene()
						.getWindow();
				stage.close();
			}
		});

		// Cancel button
		Button cancelButton = new Button("Cancel");
		cancelButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				System.out.println("Cancel");
				Stage stage = (Stage) ((Button) (event.getSource())).getScene()
						.getWindow();
				stage.close();
			}
		});

		buttonbarHbox.getChildren().addAll(saveButton, deleteButton, cancelButton);

		gridPane.setPadding(new Insets(20, 10, 10, 20));
		gridPane.setHgap(5);
		gridPane.setVgap(5);
		gridPane.setStyle(
				"-fx-padding: 10; -fx-border-style: solid inside; -fx-border-width: 2; -fx-border-insets: 5; -fx-border-radius: 0; -fx-border-color: darkgray;");

		ToggleGroup group = new ToggleGroup();

		// CSS
		RadioButton cssButton = new RadioButton("css");
		cssButton.setToggleGroup(group);
		cssButton.setSelected(true);
		gridPane.setHalignment(cssButton, HPos.LEFT);
		gridPane.add(cssButton, 0, 0);

		TextField cssFld = new TextField();
		gridPane.setHalignment(cssFld, HPos.LEFT);
		cssFld.setMaxWidth(Double.MAX_VALUE);
		gridPane.add(cssFld, 1, 0);

		// XPath
		RadioButton xpathButton = new RadioButton("xpath");
		xpathButton.setToggleGroup(group);
		gridPane.setHalignment(xpathButton, HPos.LEFT);
		gridPane.add(xpathButton, 0, 1);

		TextField xpathFld = new TextField();
		gridPane.setHalignment(xpathFld, HPos.LEFT);
		xpathFld.setMaxWidth(Double.MAX_VALUE);
		gridPane.add(xpathFld, 1, 1);

		// ID
		RadioButton idButton = new RadioButton("id");
		idButton.setToggleGroup(group);
		gridPane.setHalignment(idButton, HPos.LEFT);
		gridPane.add(idButton, 0, 2);
		idButton.setDisable(true);

		TextField idFld = new TextField();
		gridPane.setHalignment(idFld, HPos.LEFT);
		idFld.setMaxWidth(Double.MAX_VALUE);
		gridPane.add(idFld, 1, 2);
		idFld.setDisable(true);

		RadioButton textButton = new RadioButton("text");
		textButton.setToggleGroup(group);
		gridPane.setHalignment(textButton, HPos.LEFT);
		gridPane.add(textButton, 0, 3);

		TextField textFld = new TextField();
		gridPane.setHalignment(textFld, HPos.LEFT);
		textFld.setMaxWidth(Double.MAX_VALUE);
		gridPane.add(textFld, 1, 3);

		final RowConstraints rowConstraints = new RowConstraints(64); // constant
		// height
		final ColumnConstraints column1Constraints = new ColumnConstraints(100,
				Control.USE_COMPUTED_SIZE, Control.USE_COMPUTED_SIZE);

		final ColumnConstraints column2Constraints = new ColumnConstraints(250,
				Control.USE_COMPUTED_SIZE, Double.MAX_VALUE);
		gridPane.getRowConstraints().add(rowConstraints);
		column2Constraints.setHgrow(Priority.ALWAYS);
		gridPane.getColumnConstraints().addAll(column1Constraints,
				column2Constraints);
		gridPane.prefWidthProperty().bind(root.widthProperty());

		gridPane1.getChildren().addAll(gridPane, buttonbarHbox);
		gridPane1.prefWidthProperty().bind(root.widthProperty());

		root.getChildren().add(gridPane1);

		scene.setRoot(root);
		stage.show();
		/*
		 * oigin: http://www.cyberforum.ru/javafx/thread2497234.html
		 */
		stage.setOnCloseRequest(event -> {
			// confirm close
			Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
			// update dialog title and text
			alert.setTitle("Title");
			alert.setHeaderText("Some Text");
			alert.setContentText("Choose your option.");
			// create confirm and cancel buttons
			ButtonType buttonTypeOne = new ButtonType("Yes");
			ButtonType buttonTypeCancel = new ButtonType("No",
					ButtonBar.ButtonData.CANCEL_CLOSE);
			alert.getButtonTypes().setAll(buttonTypeOne, buttonTypeCancel);
			Optional<ButtonType> result = alert.showAndWait();
			// show the confirmation dialog
			if (result.get() == buttonTypeCancel)
				// ignore the closerequest event
				event.consume();
		});

	}

	@SuppressWarnings("unchecked")
	private void loadInputData(Map<String, Object> inputs) {
		try {
			inputData = (Map<String, String>) inputs.get("inputs");
			if (inputData.keySet().size() == 0) {
				throw new IllegalArgumentException("You must provide data");
			}
			logger.info("Loaded " + inputData.toString());
		} catch (ClassCastException e) {
			logger.info("Exception (ignored) " + e.toString());
		} catch (Exception e) {
			logger.info("Exception (rethrown) " + e.toString());
			throw e;
		}
	}

	@SuppressWarnings("unchecked")
	public void setScene(Scene scene) {
		parentScene = scene;
		if (parentScene != null) {
			loadInputData((Map<String, Object>) parentScene.getUserData());
		}
	}

	public static void main(String[] args) {
		launch(args);
	}

}
