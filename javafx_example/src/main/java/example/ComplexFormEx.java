package example;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.collections.FXCollections;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.controlsfx.tools.Borders.Border;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ChoiceBox;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

// origin: 
public class ComplexFormEx extends Application {

	@Override
	public void start(Stage stage) {
		BorderPane root = new BorderPane();
		Scene scene = new Scene(root, 480, 250, Color.WHITE);
		stage.setScene(scene);
		stage.setTitle("Element Locators");

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

		gridpane1.getChildren().addAll(gridpane, buttonbarHbox);

		gridpane.setPadding(new Insets(5));
		gridpane.setHgap(5);
		gridpane.setVgap(5);

		gridpane.setStyle(
				"-fx-padding: 10; -fx-border-style: solid inside; -fx-border-width: 2; -fx-border-insets: 5; -fx-border-radius: 0; -fx-border-color: darkgray;");
		ColumnConstraints column1 = new ColumnConstraints(150);
		ColumnConstraints column2 = new ColumnConstraints(50, 300, 450);
		column2.setHgrow(Priority.ALWAYS);
		gridpane.getColumnConstraints().addAll(column1, column2);

		ToggleGroup group = new ToggleGroup();

		// First name field
		RadioButton button1 = new RadioButton("css");
		button1.setToggleGroup(group);
		button1.setSelected(true);
		GridPane.setHalignment(button1, HPos.LEFT);
		gridpane.add(button1, 0, 0);

		// css value field
		TextField cssFld = new TextField();
		GridPane.setHalignment(cssFld, HPos.LEFT);
		cssFld.setMaxWidth(Double.MAX_VALUE);
		// cssFld.setHgrow(Priority.ALWAYS);
		gridpane.add(cssFld, 1, 0);

		RadioButton button2 = new RadioButton("xpath");
		button2.setToggleGroup(group);
		GridPane.setHalignment(button2, HPos.LEFT);
		gridpane.add(button2, 0, 1);

		// xpath value field
		TextField xpathFld = new TextField();
		GridPane.setHalignment(xpathFld, HPos.LEFT);
		xpathFld.setMaxWidth(Double.MAX_VALUE);
		// cssFld.setHgrow(Priority.ALWAYS);
		gridpane.add(xpathFld, 1, 1);

		RadioButton button3 = new RadioButton("id");
		button3.setToggleGroup(group);
		GridPane.setHalignment(button3, HPos.LEFT);
		gridpane.add(button3, 0, 2);
		button3.setDisable(true);
		// id value field
		TextField idFld = new TextField();
		GridPane.setHalignment(idFld, HPos.LEFT);
		idFld.setMaxWidth(Double.MAX_VALUE);
		// cssFld.setHgrow(Priority.ALWAYS);
		gridpane.add(idFld, 1, 2);
		idFld.setDisable(true);

		RadioButton button4 = new RadioButton("text");
		button4.setToggleGroup(group);
		GridPane.setHalignment(button4, HPos.LEFT);
		gridpane.add(button4, 0, 3);
		TextField textFld = new TextField();
		GridPane.setHalignment(textFld, HPos.LEFT);
		textFld.setMaxWidth(Double.MAX_VALUE);
		// cssFld.setHgrow(Priority.ALWAYS);
		gridpane.add(textFld, 1, 3);

		root.getChildren().add(gridpane1);

		scene.setRoot(root);
		stage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}

}
