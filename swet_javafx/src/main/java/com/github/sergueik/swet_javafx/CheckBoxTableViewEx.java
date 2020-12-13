package com.github.sergueik.swet_javafx;
/**
 * Copyright 2020 Serguei Kouzmine
 */

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Category;

import example.CommandLineParser;
import javafx.application.Application;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.paint.Color;
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

	@SuppressWarnings("deprecation")
	static final Category logger = Category
			.getInstance(CheckBoxTableViewEx.class);

	// http://tutorials.jenkov.com/javafx/tableview.html#create-a-tableview
	private static final TableView<Resource> tableView = new TableView<>();

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

		Button updateButton = new Button("Update");
		updateButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				Stage stage = (Stage) ((Button) (event.getSource())).getScene()
						.getWindow();

				final Set<Resource> delSet = new HashSet<>();
				final Set<Resource> selSet = new HashSet<>();
				for (final Resource Resource : tableView.getItems()) {
					if (!Resource.selectedProperty().get()) {
						delSet.add(Resource);
					} else {
						selSet.add(Resource);
					}
				}
				tableView.getItems().removeAll(delSet);
				System.err.println("Processing");
				for (final Resource Resource : selSet) {
					System.err
							.println(String.format("%s %s", Resource.nameProperty().get(),
									Resource.selectedProperty().get()));
				}
				sleep(500);
				stage.close();
			}
		});
		updateButton.setDefaultButton(true);
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

		buttonbarHbox.getChildren().addAll(updateButton, cancelButton);
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

		final ObservableList<TableColumn<Resource, ?>> columns = tableView
				.getColumns();

		tableView.setStyle("-fx-background-color: gray");
		tableView.setMaxWidth(Double.MAX_VALUE);

		final TableColumn<Resource, Boolean> isSelectedColumn = new TableColumn<>(
				"Select");
		isSelectedColumn
				.setCellValueFactory(new PropertyValueFactory<>("selected"));
		isSelectedColumn.setCellFactory(o -> new CheckBoxTableCell<>());
		// does not storess
		columns.add(isSelectedColumn);

		final TableColumn<Resource, String> nameColumn = new TableColumn<>("Name");
		nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
		nameColumn.setEditable(false);
		columns.add(nameColumn);

		final List<Resource> resources = new ArrayList<>();
		resources.add(new Resource("service discovery 1", false));
		resources.add(new Resource("service discovery 2", false));
		resources.add(new Resource("service discovery 3", false));
		resources.add(new Resource("tomcat api 1", true));
		resources.add(new Resource("tomcat api 2", true));
		resources.add(new Resource("tomcat api 3", true));
		resources.add(new Resource("tomcat api 4", true));
		resources.add(new Resource("tomcat api 5", true));
		resources.add(new Resource("tomcat api 6", true));
		resources.add(new Resource("tomcat web 1", true));
		resources.add(new Resource("tomcat web 2", true));
		resources.add(new Resource("load balancer 1", false));
		resources.add(new Resource("load balancer 2", false));
		resources.add(new Resource("load balancer 3", false));
		// final List<Resource> resources = fetchList.getValue();
		final ObservableList<Resource> items = FXCollections.observableArrayList();
		for (Resource resource : resources) {
			items.add(resource);
		}
		// final ObservableList<Resource> items =
		// FXCollections.observableArrayList();
		// the "delete" member is currenly ignored
		tableView.setItems(items);
		tableView.setEditable(true);
		GridPane.setHalignment(tableView, HPos.LEFT);
		GridPane.setRowIndex(tableView, 0);
		GridPane.setColumnIndex(tableView, 0);
		gridpane.add(tableView, 0, 0);
		root.setCenter(gridpane1);
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	public static class Resource {

		private final SimpleStringProperty name;
		private final SimpleObjectProperty<Id> id;
		private final SimpleBooleanProperty selected;

		// NOTE: cannot have init method -
		// The final field CheckBoxTableViewEx.Resource.name cannot be assigned -
		// have to do in constructor
		/*
		private void init() {
			this.name = new SimpleStringProperty();
			this.id = new SimpleObjectProperty<Id>();		
			this.selected = new SimpleBooleanProperty();
		}
		*/

		public Resource(String id, String name) {
			// init();
			this.name = new SimpleStringProperty();
			this.id = new SimpleObjectProperty<Id>();
			this.selected = new SimpleBooleanProperty();
			this.id.set(new Id(id));
			this.name.set(name);

		}

		public Resource(String id, String name, boolean selected) {
			this.name = new SimpleStringProperty();
			this.id = new SimpleObjectProperty<Id>();
			this.selected = new SimpleBooleanProperty();
			this.name.set(name);
			this.id.set(new Id(id));
			this.selected.set(selected);
		}

		public Resource(String name, boolean selected) {
			this.name = new SimpleStringProperty();
			// The blank final field id may not have been initialized
			this.id = new SimpleObjectProperty<Id>();

			this.selected = new SimpleBooleanProperty();
			this.name.set(name);
			this.selected.set(selected);
		}

		public SimpleStringProperty nameProperty() {
			return name;
		}

		public SimpleObjectProperty<Id> idProperty() {
			return id;
		}

		public Id getIdObj() {
			return id.get();
		}

		public String getIdString() {
			return id.get().toString();
		}

		public void setIdObj(Id data) {
			this.id.set(data);
		}

		public void setIdObj(String data) {
			this.id.set(new Id(data));
		}

		public SimpleBooleanProperty selectedProperty() {
			return selected;
		}
	}

	public static class Id {

		private final SimpleStringProperty Id;

		public Id(String Id) {
			this.Id = new SimpleStringProperty(Id);
		}

		public String getId() {
			return this.Id.get();
		}

		public StringProperty IdProperty() {
			return this.Id;
		}

		public void setId(String Id) {
			this.Id.set(Id);
		}

	}

	public static void sleep(Integer milliSeconds) {
		try {
			Thread.sleep((long) milliSeconds);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}