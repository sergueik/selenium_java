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
public class RecorderConfigurationEditor extends Application {
	@SuppressWarnings("unused")
	private static boolean debug = false;
	private static CommandLineParser commandLineParser;
	@SuppressWarnings("unused")
	private String[] args = {};
	@SuppressWarnings("unused")
	private String arg = null;

	private static String TargetOsName = Utils.getOsName();
	private static String dirPath = null;

	@SuppressWarnings("deprecation")
	static final Category logger = Category.getInstance(RecorderConfigurationEditor.class);

	private static Map<String, String> configData = new HashMap<>();
	private static Map<String, Map<String, String>> configOptions = new HashMap<>();
	// Load the matching templates from template directory on change event
	// handler
	private static Map<String, String> templates = new HashMap<>();

	public static void main(String[] args) {
		commandLineParser = new CommandLineParser();

		commandLineParser.parse(args);

		if (commandLineParser.hasFlag("debug")) {
			debug = true;
		}
		Application.launch(args);
	}

	@SuppressWarnings({ "unchecked" })

	@Override
	public void start(Stage primaryStage) {

		Map<String, String> browserOptions = new HashMap<>();
		for (String browser : new ArrayList<String>(
				Arrays.asList(new String[] { "Chrome", "Firefox", "Internet Explorer", "Edge", "Safari" }))) {
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
				System.out.println("Saved");
				Stage stage = (Stage) ((Button) (event.getSource())).getScene().getWindow();
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
				Stage stage = (Stage) ((Button) (event.getSource())).getScene().getWindow();
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
		// https://docs.oracle.com/javafx/2/ui_controls/combo-box.htm
		// https://docs.oracle.com/javafx/2/events/convenience_methods.htm
		// https://stackoverflow.com/questions/23968162/javafx-editable-combo-box
		baseURLFld.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent t) {
				if (t.getCode() == KeyCode.ENTER) {
					System.out.println("Entered");
				} else if (t.getCode() == KeyCode.ESCAPE) {
					System.out.println("Entered");
				} else {

				}
			}
		});

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
				// https://docs.oracle.com/javase/8/javafx/api/javafx/stage/DirectoryChooser.html
				DirectoryChooser DirectoryChooser = new DirectoryChooser();
				// http://stackoverflow.com/questions/585534/what-is-the-best-way-to-find-the-users-home-directory-in-java
				if (dirPath != null) {
					logger.info("Initial templates dir: " + dirPath);
				} else {
					dirPath = TargetOsName.startsWith("windows") ? Utils.getDesktopPath() : System.getProperty("user.home");

				}
				try {
					DirectoryChooser.setInitialDirectory(new File(dirPath));
					DirectoryChooser.setTitle("Select Template Directory");
				} catch (IllegalArgumentException e) {
					logger.info("Exception (ignored): " + e.toString());
				}

				File result = DirectoryChooser.showDialog(primaryStage);
				if (result != null) {
					final String dir = result.getAbsolutePath();
					if (dir != null && dir != tempalteDirFld.getText()) {
						logger.info("Loading templates from directory: " + dir);
						TemplateCache templateCache = TemplateCache.getInstance();
						templateCache.fillTemplateDirectoryCache(new File(dir), "user defined", templates);
						tempalteDirFld.setText(dir);
					}
				}
			}
		});

		HBox browseButtonbarHbox = new HBox(10);
		browseButtonbarHbox.setPadding(new Insets(10));
		browseButtonbarHbox.getChildren().addAll(tempalteDirFld, browseButton);

		gridpane.add(browseButtonbarHbox, 1, 2);
		browseButtonbarHbox.setMaxWidth(Double.MAX_VALUE);

		final String[] browsers = new String[] { "Chrome", "Firefox", "Edge", "Internet Explorer" };
		@SuppressWarnings("rawtypes")
		ChoiceBox browserChoice = new ChoiceBox();
		browserChoice.setItems(FXCollections.observableArrayList(Arrays.asList(browsers)));
		browserChoice.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {

			@SuppressWarnings("rawtypes")
			public void changed(ObservableValue ov, Number index, Number new_index) {
				logger.info("Browser: " + browsers[new_index.intValue()]);
			}
		});
		browserChoice.getSelectionModel().select(0);
		browserChoice.setMaxWidth(Double.MAX_VALUE);
		gridpane.add(browserChoice, 1, 1);

		Label browserLabel = new Label("Browser");
		GridPane.setHalignment(browserLabel, HPos.RIGHT);
		gridpane.add(browserLabel, 0, 1);
		final String[] templates = new String[] { "mockup 1", "mockup 2", "mockup 3" };

		@SuppressWarnings("rawtypes")
		ComboBox templateChoice = new ComboBox();
		templateChoice.getItems().addAll(Arrays.asList(templates));
		templateChoice.setPromptText("template");
		templateChoice.setOnAction(e -> {
			@SuppressWarnings("unused")
			String dummy = templateChoice.getSelectionModel().getSelectedItem().toString();
		});

		GridPane.setHalignment(templateChoice, HPos.RIGHT);

		templateChoice.setEditable(true);
		templateChoice.getEditor().setEditable(true);
		System.out.println("New value " + templates[0]);
		templateChoice.valueProperty().setValue(templates[0]);
		templateChoice.valueProperty().addListener(new ChangeListener<String>() {
			@SuppressWarnings("rawtypes")
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

		// ===

		final TableView<TargetOs> view = new TableView<>();
		final ObservableList<TableColumn<TargetOs, ?>> columns = view.getColumns();

		final TableColumn<TargetOs, Boolean> nameColumn = new TableColumn<>("Name");
		nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
		columns.add(nameColumn);

		final TableColumn<TargetOs, Boolean> loadedColumn = new TableColumn<>("Delete");
		loadedColumn.setCellValueFactory(new PropertyValueFactory<>("delete"));
		loadedColumn.setCellFactory(tc -> new CheckBoxTableCell<>());
		columns.add(loadedColumn);

		final ObservableList<TargetOs> items = FXCollections.observableArrayList(new TargetOs("MicrTargetOsoft Windows 3.1", true),
				new TargetOs("MicrTargetOsoft Windows 3.11", true), new TargetOs("MicrTargetOsoft Windows 95", true),
				new TargetOs("MicrTargetOsoft Windows NT 3.51", true), new TargetOs("MicrTargetOsoft Windows NT 4", true),
				new TargetOs("MicrTargetOsoft Windows 2000", true), new TargetOs("MicrTargetOsoft Windows Vista", true),
				new TargetOs("MicrTargetOsoft Windows Seven", false), new TargetOs("Linux all versions :-)", false));
		view.setItems(items);
		view.setEditable(true);
		gridpane.add(view, 1, 3);

		final Button delBtn = new Button("Delete");
		delBtn.setMaxWidth(Double.MAX_VALUE);
		delBtn.setOnAction(e -> {
			final Set<TargetOs> del = new HashSet<>();
			for (final TargetOs TargetOs : view.getItems()) {
				if (TargetOs.getDelete()) {
					del.add(TargetOs);
				}
			}
			view.getItems().removeAll(del);
		});

		gridpane.add(delBtn, 0, 3);
		
		root.setCenter(gridpane1);
		primaryStage.setScene(scene);
		primaryStage.show();
		for (String configKey : Arrays.asList("Browser", "Base URL", "Template Directory", "Template")) {
			if (configOptions.containsKey(configKey)) {
			} else {
			}
		}
	}

	public static class TargetOs {

		private String name = null;
		private boolean delete = false;

		public TargetOs(String nm, boolean del) {
			this.name = nm;
			this.delete = del;
		}

		public String getName() {
			return name;
		}

		public Boolean getDelete() {
			return delete;
		}
	}

}
