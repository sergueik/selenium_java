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
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Category;

import javafx.scene.Node;
import javafx.stage.Window;

import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;

public class DataFormControllerEx {

	@SuppressWarnings("deprecation")
	static final Category logger = Category
			.getInstance(DataFormControllerEx.class);

	private static final List<String> locators = Arrays
			.asList("ElementCssSelector", "ElementXPath", "ElementId", "ElementText");
	// selectorTable keys
	private static Map<String, String> mapSWD2CoreSelenium = new HashMap<>();
	static {
		mapSWD2CoreSelenium.put("ElementXPath", "xpath");
		mapSWD2CoreSelenium.put("ElementCssSelector", "cssSelector");
		mapSWD2CoreSelenium.put("ElementText", "text");
		mapSWD2CoreSelenium.put("ElementId", "id");
		// TODO:
		mapSWD2CoreSelenium.put("ElementLinkText", "linkText");
		mapSWD2CoreSelenium.put("ElementTagName", "tagName");
	}

	private Stage mainStage;

	@FXML
	private Button buttonSave;
	@FXML
	private Button buttonDelete;
	@FXML
	private Button buttonCancel;

	@FXML
	private static RadioButton radioButtonID;

	@FXML
	private static RadioButton radioButtonCSS;

	@FXML
	private static RadioButton radioButtonXPath;

	@FXML
	private static RadioButton radioButtonText;

	@FXML
	private ToggleGroup toggleGroup;

	@FXML
	private static TextField textFieldID;

	@FXML
	private static TextField textFieldCSS;

	@FXML
	private static TextField textFieldXPath;

	@FXML
	private static TextField textFieldText;

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
	private static Map<RadioButton, String> radioButtons = new HashMap<>();
	static {
		radioButtons.put(radioButtonID, "ID");
		radioButtons.put(radioButtonCSS, "CSS");
		radioButtons.put(radioButtonText, "Text");
		radioButtons.put(radioButtonXPath, "XPath");
	}

	private static Map<TextField, String> locatorValues = new HashMap<>();
	static {
		locatorValues.put(textFieldID, "ID");
		locatorValues.put(textFieldCSS, "CSS");
		locatorValues.put(textFieldText, "Text");
		locatorValues.put(textFieldXPath, "XPath");
	}

	public void setMainStage(Stage mainStage) {
		this.mainStage = mainStage;
	}

	@SuppressWarnings("restriction")
	@FXML
	private void initialize() {

		logger.info("Start.");
		Map<String, String> radioButtonUserData = new HashMap<>();
		// TODO: can not do from initialize ?
		radioButtonXPath.setUserData(radioButtonUserData);
		for (RadioButton radioButton : radioButtons.keySet()) {
			radioButtonUserData = new HashMap<>();
			logger.info("Loading into user data: " + radioButtons.get(radioButton));

			radioButtonUserData.put("key", radioButtons.get(radioButton));
			radioButton.setUserData(radioButtonUserData);
			radioButtonUserData.clear();
		}

		for (TextField textField : locatorValues.keySet()) {
			Map<String, String> textFieldUserData = new HashMap<>();
			textFieldUserData.put("key", locatorValues.get(textField));
			textField.setUserData(textFieldUserData);
		}

		toggleGroup.selectedToggleProperty()
				.addListener(new ChangeListener<Toggle>() {
					public void changed(ObservableValue<? extends Toggle> ov,
							Toggle old_toggle, Toggle new_toggle) {

						if (toggleGroup.getSelectedToggle() != null) {
							RadioButton radioButton = (RadioButton) toggleGroup
									.getSelectedToggle();
							logger.info("Selected (immediate) " + radioButton.getId());
							Map<String, String> radioButtonUserData = new HashMap<>();
							try {
								radioButtonUserData = (Map<String, String>) radioButton
										.getUserData();
							} catch (ClassCastException e) {
								logger.info("Exception (ignored) " + e.toString());
							} catch (Exception e) {
								throw e;
							}
							if (radioButtonUserData.containsKey("key")) {
								String key = radioButtonUserData.get("key");
								for (TextField textField : locatorValues.keySet()) {
									Map<String, String> textFieldUserData = new HashMap<>();
									try {
										textFieldUserData = (Map<String, String>) textField
												.getUserData();
										if (textFieldUserData.containsKey("key")) {
											if (textFieldUserData.get("key") == key) {
												textField.setDisable(false);
											} else {
												textField.setDisable(true);
											}
										}
									} catch (ClassCastException e) {
										logger.info("Exception (ignored) " + e.toString());
									} catch (Exception e) {
										throw e;
									}

								}
							}
						}
					}
				});

		textFieldText.focusedProperty().addListener((o, oldVal, newVal) -> {
			if (!newVal) {
				textFieldText.setUserData(oldVal);
				logger.info("Value " + textFieldText.getText());
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
