package example.gui;

import example.logic.SelectionDTO;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

// import org.apache.log4j.Category;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.stage.WindowEvent;

@SuppressWarnings("restriction")
// see also:
// http://www.java2s.com/Tutorials/Java/JavaFX/0540__JavaFX_DatePicker.htm
//
// Caused by: java.lang.module.InvalidModuleDescriptorException: Package
// example.dateRangedialog not found in module
public class GUIMain extends Application {

	private static SelectionDTO caller = new SelectionDTO();
	Stage stage;

	private static int column;
	private static int row;
	private static int columnSpan;
	private static int rowSpan;
	private static final String[] quickRanges = new String[] { "", "Past Month",
			"Past Week", "Past Day" };
	private static String quickRange = null;

	// temporarily disabled logging
	// @SuppressWarnings("deprecation")
	// static final Category logger = Category.getInstance(DateRangeDialog.class);
	
	public static String result = null;

	public static void main(String[] args) {
		Application.launch(args);
	}

	@Override
	public void start(Stage stage) {
		Platform.setImplicitExit(false);
		stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
			@Override
			public void handle(WindowEvent e) {
				System.err.println("Stage close request is processed");

				// not closing
				e.consume();
				// Platform.exit();
				// System.exit(0);
			}
		});

		this.stage = stage;
		this.stage.setTitle("Report Configuration");
		VBox vbox = new VBox(20);
		Scene scene = new Scene(vbox, 260, 260);
		stage.setScene(scene);

		// https://docs.oracle.com/javase/8/javafx/api/javafx/scene/layout/GridPane.html
		GridPane gridpane = new GridPane();
		gridpane.setPadding(new Insets(12));
		gridpane.setHgap(8);
		gridpane.setVgap(8);

		Label startLabel = new Label("Start range: ");
		final DatePicker tillDatePicker = new DatePicker();
		tillDatePicker.setOnAction((event) -> {
			caller.setTillDate(tillDatePicker.getValue());
		});
		column = 0;
		row = 0;
		gridpane.add(startLabel, column, row);
		column = 1;
		row = 0;
		final DatePicker fromDatePicker = new DatePicker();
		gridpane.add(fromDatePicker, column, row);
		final Callback<DatePicker, DateCell> dayCellFactory = new Callback<DatePicker, DateCell>() {
			@Override
			public DateCell call(final DatePicker datePicker) {
				return new DateCell() {
					@Override
					public void updateItem(LocalDate item, boolean empty) {
						super.updateItem(item, empty);

						if (item.isAfter(tillDatePicker.getValue())) {
							setDisable(true);
							setStyle("-fx-background-color: #EEEEEE;");
							// TODO: enable logging does not log anything but changes the
							// chrome of the disabled part of the calendar
							// logger.info("Disabled " + item.format(
							// DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT)));
						}
					}
				};
			}
		};

		column = 0;
		row = 1;
		Label tillLabel = new Label("End range: ");
		gridpane.add(tillLabel, column, row);
		column = 1;
		row = 1;
		gridpane.add(tillDatePicker, column, row);
		fromDatePicker.setDayCellFactory(dayCellFactory);
		fromDatePicker.setOnAction((event) -> {
			caller.setFromDate(fromDatePicker.getValue());
		});
		tillDatePicker.setValue(LocalDate.now());

		fromDatePicker.setValue(tillDatePicker.getValue().minusDays(7));
		caller.setFromDate(fromDatePicker.getValue());
		caller.setTillDate(tillDatePicker.getValue());
		Label userNameLabel = new Label("User Name: ");
		column = 0;
		row = 2;
		// gridpane.add(userNameLabel, column, row);
		GridPane.setRowIndex(userNameLabel, row);
		GridPane.setColumnIndex(userNameLabel, column);

		final TextField userNameFld = new TextField(caller.getUsername());

		column = 1;
		row = 2;
		GridPane.setRowIndex(userNameFld, 2);
		GridPane.setColumnIndex(userNameFld, 1);
		gridpane.getChildren().addAll(userNameLabel, userNameFld);

		Label passwordLbl = new Label("Password: ");
		column = 0;
		row = 3;
		GridPane.setRowIndex(passwordLbl, row);
		GridPane.setColumnIndex(passwordLbl, column);

		final PasswordField passwordField = new PasswordField();
		column = 1;
		row = 3;
		GridPane.setRowIndex(passwordField, row);
		GridPane.setColumnIndex(passwordField, column);
		passwordField.setText(caller.getPassword());
		// gridpane.add(passwordField, column, row);
		gridpane.getChildren().addAll(passwordField, passwordLbl);

		CheckBox checkBox = new CheckBox();
		checkBox.setText("continue");
		checkBox.setSelected(caller.isLongRun());
		checkBox.setTooltip(new Tooltip("will run multiple times"));
		// legacy style
		checkBox.selectedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> ov,
					Boolean old_val, Boolean new_val) {
				caller.setLongRun(checkBox.isSelected());
			}
		});

		checkBox.setOnAction(event -> {
			CheckBox owner = (CheckBox) (event.getSource());
			System.err.println(owner.isSelected());
		});

		column = 0;
		row = 4;
		GridPane.setRowIndex(checkBox, row);
		GridPane.setColumnIndex(checkBox, column);
		gridpane.getChildren().add(checkBox);

		vbox.getChildren().add(gridpane);

		Label templateLabel = new Label("Quick Range");
		GridPane.setHalignment(templateLabel, HPos.LEFT);
		column = 0;
		row = 5;
		gridpane.add(templateLabel, column, row);

		ComboBox templateChoice = new ComboBox();
		templateChoice.getItems().addAll(Arrays.asList(quickRanges));
		templateChoice.setPromptText("template");
		templateChoice.setOnAction(e -> {

			quickRange = templateChoice.getSelectionModel().getSelectedItem()
					.toString();
			caller.setQuickRange(quickRange);
		});

		GridPane.setHalignment(templateChoice, HPos.RIGHT);

		templateChoice.setEditable(true);
		templateChoice.getEditor().setEditable(true);
		// logger.info("Quick Range Value: " + quickRanges[0]);
		templateChoice.valueProperty().setValue(quickRanges[0]);
		templateChoice.valueProperty().addListener(new ChangeListener<String>() {
			@SuppressWarnings("rawtypes")
			@Override
			public void changed(ObservableValue ov, String oldValue,
					String newValue) {
				quickRange = newValue;
				// logger.info("New Quick Range Value: " + quickRange);
			}
		});

		templateChoice.setStyle("-fx-background-color: gray");
		templateChoice.setMaxWidth(Double.MAX_VALUE);
		// setHgrow(Priority.ALWAYS);
		column = 1;
		row = 5;
		gridpane.add(templateChoice, column, row);

		Button saveButton = new Button("Save");

		saveButton.setPadding(new Insets(10));
		saveButton.setMaxWidth(Double.MAX_VALUE);
		saveButton.setDefaultButton(true);

		Button closeButton = new Button("Close");
		closeButton.setPadding(new Insets(10));
		closeButton.setMaxWidth(Double.MAX_VALUE);

		HBox buttonBox = new HBox(saveButton, closeButton);
		HBox.setHgrow(saveButton, Priority.ALWAYS);
		HBox.setHgrow(closeButton, Priority.ALWAYS);
		column = 0;
		row = 6;

		columnSpan = 2;
		rowSpan = 1;
		gridpane.add(buttonBox, column, row, columnSpan, rowSpan);

		saveButton.setOnAction(e -> showData(e));

		closeButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				// do not rely on the class variable
				Stage stage = (Stage) ((Button) (event.getSource())).getScene()
						.getWindow();
				// https://stackoverflow.com/questions/15520573/how-to-show-and-hide-a-window-in-javafx-2
				// hide does not make stage invisible on Linux, works on Windows
				stage.hide();
				stage.close();
				Platform.exit();
			}
		});

		stage.sizeToScene();
		// NOTE: the handler only gets fired when stage window is closed by window
		// manager
		stage.getScene().getWindow().addEventFilter(
				WindowEvent.WINDOW_CLOSE_REQUEST, this::closeWindowEvent);
		stage.show();
	}

	@Override
	public void stop() {
		System.err.println("Stage is closing");
		// stage.setIconified(true); // no effect
		// TODO: how to prevent application from finishing

	}

	// based on:
	// https://stackoverflow.com/questions/26619566/javafx-stage-close-handler
	// not working
	private void closeWindowEvent(WindowEvent event) {
		System.err.println("Window close request ...");

		Alert alert = new Alert(Alert.AlertType.INFORMATION);
		alert.getButtonTypes().remove(ButtonType.OK);
		alert.getButtonTypes().add(ButtonType.CANCEL);
		alert.getButtonTypes().add(ButtonType.YES);
		alert.setTitle("Quit application");
		alert.setContentText(String.format("Close without saving?"));
		Optional<ButtonType> res = alert.showAndWait();

		if (res.isPresent()) {
			if (res.get().equals(ButtonType.CANCEL)) {
				event.consume();
				stage.setIconified(true);
			}
		}
	}

	private void showData(ActionEvent event) {
		String data = "\n" + "From=" + caller.getFromDate() + "\n" + "Till="
				+ caller.getTillDate() + "\n" + "Quick Range: " + quickRange;
		// logger.info(data);
		// dataFld.setText(data);
		stage.setIconified(true);
		// https://stackoverflow.com/questions/15520573/how-to-show-and-hide-a-window-in-javafx-2
		// NOTE: hide only works on Windows 7, does not make stage invisible on
		// Linux
		stage.hide();
		// stage.getOnCloseRequest();
		stage.close();
		try {
			System.err.println("sleep");
			Thread.sleep(10000);
			System.err.println("done");
		} catch (InterruptedException e) {
			System.err.println("interrupted :" + e.toString());
		}
		if (caller.isLongRun()) {
			stage.show();
			stage.setIconified(false);
		} else {
			Platform.exit();
		}
	}
}
