package example;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

@SuppressWarnings("restriction")
public class ConfigFormEx extends Application {

	private static Map<String, String> configData = new HashMap<>();
	private static Map<String, Map<String, String>> configOptions = new HashMap<>();
	private static Map<String, String> templates = new HashMap<>();

	public static void main(String[] args) {
		Application.launch(args);
	}

	@SuppressWarnings("restriction")

	@Override
	public void start(Stage primaryStage) {

		Map<String, String> browserOptions = new HashMap<>();
		for (String browser : new ArrayList<String>(Arrays.asList(new String[] {
				"Chrome", "Firefox", "Internet Explorer", "Edge", "Safari" }))) {
			browserOptions.put(browser, "unused");
		}
		configOptions.put("Browser", browserOptions);
		// offer templates embedded in the application jar and
		// make rest up to customer
		configData.put("Template", "Core Selenium Java (embedded)");

		configOptions.put("Template", new HashMap<String, String>());
		templates = configOptions.get("Template");

		primaryStage.setTitle("Session Configuration");
		BorderPane root = new BorderPane();
		Scene scene = new Scene(root, 480, 250, Color.WHITE);

		GridPane gridpane1 = new GridPane();

		GridPane gridpane = new GridPane();
		GridPane.setRowIndex(gridpane, 0);
		GridPane.setColumnIndex(gridpane, 0);

		HBox buttonbarHbox = new HBox(10);
		buttonbarHbox.setPadding(new Insets(10));

		// gridpane.setVgap(5);
		GridPane.setRowIndex(buttonbarHbox, 1);
		GridPane.setColumnIndex(buttonbarHbox, 0);

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

		buttonbarHbox.getChildren().addAll(saveButton, cancelButton);

		gridpane1.getChildren().addAll(gridpane, buttonbarHbox);

		// origin:

		gridpane.setPadding(new Insets(5));
		gridpane.setHgap(5);
		gridpane.setVgap(5);
		// https://docs.oracle.com/javafx/2/api/javafx/scene/doc-files/cssref.html
		gridpane.setStyle(
				"-fx-padding: 10; -fx-border-style: solid inside; -fx-border-width: 2; -fx-border-insets: 5; -fx-border-radius: 0; -fx-border-color: darkgray;");
		// origin:
		// http://www.java2s.com/Tutorials/Java/JavaFX/0340__JavaFX_GridPane.htm

		// https://docs.oracle.com/javase/8/javafx/api/javafx/scene/layout/GridPane.html
		ColumnConstraints column1 = new ColumnConstraints(150);
		ColumnConstraints column2 = new ColumnConstraints(50, 300, 450);
		column2.setHgrow(Priority.ALWAYS);
		gridpane.getColumnConstraints().addAll(column1, column2);

		// First name label
		Label baseURLLbl = new Label("Base URL");
		GridPane.setHalignment(baseURLLbl, HPos.RIGHT);
		// Set one constraint at a time...
		// Places the label at the first row and first column
		GridPane.setRowIndex(baseURLLbl, 0);
		GridPane.setColumnIndex(baseURLLbl, 0);

		// Last name label
		Label templateDirLabel = new Label("Template directory");

		GridPane.setHalignment(templateDirLabel, HPos.RIGHT);
		// Set column and row constraint at once...
		// Places the label at the third row and first column
		gridpane.add(templateDirLabel, 0, 2);
		gridpane.getChildren().addAll(baseURLLbl);

		// base URL field
		TextField baseURLFld = new TextField();
		GridPane.setHalignment(baseURLFld, HPos.LEFT);
		gridpane.add(baseURLFld, 1, 0);

		// tempalteDir field
		TextField tempalteDirFld = new TextField();
		GridPane.setHalignment(tempalteDirFld, HPos.LEFT);
		tempalteDirFld.setMaxWidth(Double.MAX_VALUE);

		Button browseButton = new Button("Browse");
		GridPane.setHalignment(browseButton, HPos.RIGHT);
		browseButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				System.out.println("Browse");
			}
		});

		HBox browseButtonbarHbox = new HBox(10);
		browseButtonbarHbox.setPadding(new Insets(10));
		browseButtonbarHbox.getChildren().addAll(tempalteDirFld, browseButton);

		gridpane.add(browseButtonbarHbox, 1, 2);
		browseButtonbarHbox.setMaxWidth(Double.MAX_VALUE);

		final String[] browsers = new String[] { "Chrome", "Firefox", "Edge",
				"Internet Explorer" };
		ChoiceBox browserChoice = new ChoiceBox();
		browserChoice
				.setItems(FXCollections.observableArrayList(Arrays.asList(browsers)));
		browserChoice.getSelectionModel().selectedIndexProperty()
				.addListener(new ChangeListener<Number>() {
					public void changed(ObservableValue ov, Number index,
							Number new_index) {
						System.err.println("Browser: " + browsers[new_index.intValue()]);
					}
				});
		browserChoice.getSelectionModel().select(0);
		browserChoice.setMaxWidth(Double.MAX_VALUE);
		gridpane.add(browserChoice, 1, 1);

		Label browserLabel = new Label("Browser");
		GridPane.setHalignment(browserLabel, HPos.RIGHT);
		gridpane.add(browserLabel, 0, 1);
		final String[] templates = new String[] { "mockup 1", "mockup 2",
				"mockup 3" };

		ComboBox templateChoice = new ComboBox();
		templateChoice.getItems().addAll(Arrays.asList(templates));
		templateChoice.setPromptText("template");
		templateChoice.setOnAction(e -> {
			String dummy = templateChoice.getSelectionModel().getSelectedItem()
					.toString();

		});

		GridPane.setHalignment(templateChoice, HPos.RIGHT);

		templateChoice.setEditable(true);
		templateChoice.getEditor().setEditable(true);
		System.out.println("New value " + templates[0]);
		templateChoice.valueProperty().setValue(templates[0]);
		templateChoice.valueProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue ov, String t, String t1) {
				System.out.println("New value " + t1);

			}
		});

		templateChoice.setStyle("-fx-background-color: gray");
		templateChoice.setMaxWidth(Double.MAX_VALUE);
		// setHgrow(Priority.ALWAYS);
		gridpane.add(templateChoice, 1, 3);

		Label templateLabel = new Label("Template");
		GridPane.setHalignment(templateLabel, HPos.RIGHT);
		gridpane.add(templateLabel, 0, 3);

		root.setCenter(gridpane1);
		primaryStage.setScene(scene);
		primaryStage.show();
		for (String configKey : Arrays.asList("Browser", "Base URL",
				"Template Directory", "Template")) {
			if (configOptions.containsKey(configKey)) {
			} else {
			}
		}
	}
}
