package com.github.sergueik.swet_javafx;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.log4j.Category;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.util.Callback;

// TODO: past month  past week past day
@SuppressWarnings("restriction")
// see also:
// http://www.java2s.com/Tutorials/Java/JavaFX/0540__JavaFX_DatePicker.htm
//
public class DateRangeDialog extends Application {

	DatePicker fromDatePicker = new DatePicker();
	DatePicker tillDatePicker = new DatePicker();
	Stage stage;
	@SuppressWarnings("deprecation")
	static final Category logger = Category.getInstance(DateRangeDialog.class);
	public static String result = null;

	@SuppressWarnings("unused")
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage stage) {
		this.stage = stage;
		VBox vbox = new VBox(20);
		Scene scene = new Scene(vbox, 300, 200);
		stage.setScene(scene);

		GridPane gridpane = new GridPane();
		gridpane.setPadding(new Insets(5));
		gridpane.setHgap(5);
		gridpane.setVgap(5);

		Label startLabel = new Label("Start: ");
		gridpane.add(startLabel, 0, 1);
		gridpane.add(fromDatePicker, 1, 1);
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
							//		DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT)));
						}
					}
				};
			}
		};

		Label tillLabel = new Label("End: ");
		gridpane.add(tillLabel, 0, 2);
		gridpane.add(tillDatePicker, 1, 2);
		fromDatePicker.setDayCellFactory(dayCellFactory);
		tillDatePicker.setValue(LocalDate.now().plusDays(1));
		fromDatePicker.setValue(tillDatePicker.getValue().minusDays(7));
		vbox.getChildren().add(gridpane);
		Button saveButton = new Button("Save");
		Button closeButton = new Button("Close");
		saveButton.setMaxWidth(Double.MAX_VALUE);
		saveButton.setDefaultButton(true);
		closeButton.setMaxWidth(Double.MAX_VALUE);

		HBox buttonBox = new HBox(saveButton, closeButton);
		HBox.setHgrow(saveButton, Priority.ALWAYS);
		HBox.setHgrow(closeButton, Priority.ALWAYS);
		gridpane.add(buttonBox, 0, 3, 2, 1); // column=2, row=0, colspan=1,
																					// rowspan=2

		saveButton.setOnAction(e -> showData());

		closeButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				stage.hide();
				stage.close();
			}
		});

		stage.sizeToScene();
		stage.show();
	}

	private void showData() {
		String data = "\nFrom=" + fromDatePicker.getValue() + "\nTill="
				+ tillDatePicker.getValue();
		logger.info(data);
		// dataFld.setText(data);
		stage.hide();
		stage.close();
	}
}
