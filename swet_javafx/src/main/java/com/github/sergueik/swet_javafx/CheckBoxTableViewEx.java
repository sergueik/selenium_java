package com.github.sergueik.swet_javafx;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Category;

import example.CommandLineParser;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.paint.Color;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

// https://stackoverflow.com/questions/7217625/how-to-add-checkboxs-to-a-tableview-in-javafx
// https://stackoverflow.com/questions/26631041/javafx-combobox-with-checkboxes
// https://stackoverflow.com/questions/52467979/checkbox-and-combobox-javafx/52469980
@SuppressWarnings("restriction")
public class CheckBoxTableViewEx extends Application {
	@SuppressWarnings("unused")
	private static boolean debug = false;
	private static CommandLineParser commandLineParser;
	@SuppressWarnings("unused")
	private String[] args = {};
	@SuppressWarnings("unused")
	private String arg = null;

	private static String osName = Utils.getOsName();

	@SuppressWarnings("deprecation")
	static final Category logger = Category
			.getInstance(CheckBoxTableViewEx.class);

	private static Map<String, String> configData = new HashMap<>();
	// TODO: add more members but show only the name
	// http://tutorials.jenkov.com/javafx/tableview.html#create-a-tableview
	private static final TableView<TargetOs> view = new TableView<>();

	public static void main(String[] args) {
		commandLineParser = new CommandLineParser();

		commandLineParser.parse(args);

		if (commandLineParser.hasFlag("debug")) {
			debug = true;
		}
		Application.launch(args);
	}

	@Override
	public void start(Stage primaryStage) {

		primaryStage.setTitle("Agent Configuration");
		BorderPane root = new BorderPane();
		Scene scene = new Scene(root, 480, 350, Color.WHITE);

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
				System.err.println("Saving");
				Stage stage = (Stage) ((Button) (event.getSource())).getScene()
						.getWindow();

				final Set<TargetOs> del = new HashSet<>();
				for (final TargetOs targetOs : view.getItems()) {
					System.err.println(
							String.format("%s %s", targetOs.getName(), targetOs.getDelete()));
					if (targetOs.getDelete()) {
						del.add(targetOs);
					}
				}
				view.getItems().removeAll(del);

				stage.close();
			}
		});
		saveButton.setDefaultButton(true);
		// Cancel button
		Button cancelButton = new Button("Cancel");
		cancelButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				System.err.println("Cancel");
				Stage stage = (Stage) ((Button) (event.getSource())).getScene()
						.getWindow();
				stage.close();
			}
		});

		buttonbarHbox.getChildren().addAll(saveButton, cancelButton);
		gridpane1.getChildren().addAll(gridpane, buttonbarHbox);

		gridpane.setPadding(new Insets(5));
		gridpane.setHgap(5);
		gridpane.setVgap(5);
		// https://docs.oracle.com/javafx/2/api/javafx/scene/doc-files/cssref.html
		gridpane.setStyle(
				"-fx-padding: 10; -fx-border-style: solid inside; -fx-border-width: 2; -fx-border-insets: 5; -fx-border-radius: 0; -fx-border-color: darkgray;");

		// see also:
		// http://www.java2s.com/Tutorials/Java/JavaFX/0340__JavaFX_GridPane.htm
		// https://docs.oracle.com/javase/8/javafx/api/javafx/scene/layout/GridPane.html
		ColumnConstraints column1 = new ColumnConstraints(400, 300,
				Double.MAX_VALUE);
		column1.setHgrow(Priority.ALWAYS);
		gridpane.getColumnConstraints().add(column1);

		// ===
		// NOTE: usine plain strings do not work
		final ObservableList<TableColumn<TargetOs, ?>> columns = view.getColumns();

		view.setStyle("-fx-background-color: gray");
		view.setMaxWidth(Double.MAX_VALUE);

		final TableColumn<TargetOs, Boolean> nameColumn = new TableColumn<>("Name");
		nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
		columns.add(nameColumn);

		final TableColumn<TargetOs, Boolean> loadedColumn = new TableColumn<>(
				"Delete");
		loadedColumn.setCellValueFactory(new PropertyValueFactory<>("delete"));
		loadedColumn.setCellFactory(tc -> new CheckBoxTableCell<>());
		columns.add(loadedColumn);

		final ObservableList<TargetOs> items = FXCollections.observableArrayList(
				new TargetOs("discovery", false), new TargetOs("tomcat api", true),
				new TargetOs("tomcat api", true), new TargetOs("tomcat web", true),
				new TargetOs("load balancer", false),
				new TargetOs("load balancer", false),
				new TargetOs("load balancer", false));
		// the "delete" member is currenly ignored
		view.setItems(items);
		view.setEditable(true);
		GridPane.setHalignment(view, HPos.LEFT);
		GridPane.setRowIndex(view, 0);
		GridPane.setColumnIndex(view, 0);
		gridpane.add(view, 0, 0);
		root.setCenter(gridpane1);
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	public static class TargetOs {

		private String name = null;
		private boolean delete = false;

		public TargetOs(String name, boolean delete) {
			this.name = name;
			this.delete = delete;
		}

		public String getName() {
			return name;
		}

		public Boolean getDelete() {
			return delete;
		}
	}

}
