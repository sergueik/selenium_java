package example;

import javafx.collections.ObservableList;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.*;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.apache.log4j.Category;

import javafx.scene.Node;
import javafx.stage.Window;

import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;

public class DataFormControllerEx {

	@SuppressWarnings("deprecation")
	static final Category logger = Category
			.getInstance(DataFormControllerEx.class);

	private Stage mainStage;

	@FXML
	private Button buttonSave;
	@FXML
	private Button buttonDelete;
	@FXML
	private Button buttonCancel;

	@FXML
	private RadioButton radioButtonID;

	@FXML
	private RadioButton radioButtonCSS;

	@FXML
	private RadioButton radioButtonXPath;

	@FXML
	private RadioButton radioButtonText;

	@FXML
	private ToggleGroup toggleGroup;

	// https://github.com/jfoenixadmin/JFoenix/blob/master/demo/src/main/resources/fxml/ui/RadioButton.fxml
	@FXML
	private TableView tablePassBook;
	@FXML
	private TextField filterField;

	private Parent fxmlEdit;
	private Parent fxmlEdit1;
	private FXMLLoader fxmlLoader = new FXMLLoader();
	private FXMLLoader fxmlLoader1 = new FXMLLoader();

	private Stage stage;
	private Stage changestage;

	public void setMainStage(Stage mainStage) {
		this.mainStage = mainStage;
	}

	@FXML
	private void initialize() {

		// NOTE: not reached
		logger.info("Start.");
		toggleGroup.selectedToggleProperty()
				.addListener(new ChangeListener<Toggle>() {
					public void changed(ObservableValue<? extends Toggle> ov,
							Toggle old_toggle, Toggle new_toggle) {

						if (toggleGroup.getSelectedToggle() != null) {
							RadioButton radioButton = (RadioButton) toggleGroup
									.getSelectedToggle();
							logger.info("Selected (immediate) " + radioButton.getId());
						}
					}
				});

	}

	@SuppressWarnings("restriction")
	@FXML
	public void start(Stage stage) {
		// NOTE: not reached
	}

	@SuppressWarnings("restriction")
	public void actionButtonPressed(ActionEvent event) {

		Object source = event.getSource();

		if (!(source instanceof Button)) {
			return;
		}

		Button button = (Button) source;
		// Website selectedWebsite = (Website) tablePassBook.getSelectionModel()
		// .getSelectedItem();
		Window parentWindow = ((Node) event.getSource()).getScene().getWindow();
		logger.info("Clicked " + button.getId());

		// Has selection.
		if (toggleGroup.getSelectedToggle() != null) {
			RadioButton radioButton = (RadioButton) toggleGroup.getSelectedToggle();
			logger.info("Selected " + radioButton.getId());
		} else {
			logger.info("Nothing is selected");
		}

		switch (button.getId()) {
		case "buttonSave":
			break;
		case "buttonDelete":
			/*
			if (tablePassBook.getSelectionModel().getSelectedItem() != null) {
				editpassw.setWebsite(
						(Website) tablePassBook.getSelectionModel().getSelectedItem());
				changeDialog();
			}
			*/
			break;
		case "buttonCancel":
			break;
		}
	}
}
