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

	@SuppressWarnings("unused")
	private Stage mainStage;

	@SuppressWarnings("restriction")
	@FXML
	private Button buttonSave;

	@SuppressWarnings("restriction")
	@FXML
	private Button buttonDelete;

	@SuppressWarnings("restriction")
	@FXML
	private Button buttonCancel;

	@SuppressWarnings("restriction")
	@FXML
	private static RadioButton radioButtonID;

	@SuppressWarnings("restriction")
	@FXML
	private static RadioButton radioButtonCSS;

	@SuppressWarnings("restriction")
	@FXML
	private static RadioButton radioButtonXPath;

	@SuppressWarnings("restriction")
	@FXML
	private static RadioButton radioButtonText;

	@SuppressWarnings("restriction")
	@FXML
	private ToggleGroup toggleGroup;

	@SuppressWarnings("restriction")
	@FXML
	private static TextField textFieldID;

	@SuppressWarnings("restriction")
	@FXML
	private static TextField textFieldCSS;

	@SuppressWarnings("restriction")
	@FXML
	private static TextField textFieldXPath;

	@SuppressWarnings("restriction")
	@FXML
	private static TextField textFieldText;

	// https://github.com/jfoenixadmin/JFoenix/blob/master/demo/src/main/resources/fxml/ui/RadioButton.fxml
	@SuppressWarnings("restriction")
	@FXML
	private TableView tablePassBook;

	@SuppressWarnings("restriction")
	@FXML
	private TextField filterField;

	private Parent fxmlEdit;
	private Parent fxmlEdit1;
	
	@SuppressWarnings("restriction")
	private FXMLLoader fxmlLoader = new FXMLLoader();
	
	@SuppressWarnings("restriction")
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

	public void setMainStage(@SuppressWarnings("restriction") Stage mainStage) {
		this.mainStage = mainStage;
	}

	public Map<String, String> radioButtonUserData = new HashMap<>();

	@SuppressWarnings("restriction")
	@FXML
	private void initialize() {

		logger.info("Start.");
		// TODO: while @FX elements null in initialize ?
		if (radioButtonXPath != null) {
			radioButtonXPath.setUserData(radioButtonUserData);
		} else {
			logger.info("radioButtonXPath is null");
		}
		for (RadioButton radioButton : radioButtons.keySet()) {
			radioButtonUserData = new HashMap<>();
			if (radioButton != null) {
				logger.info("Loading into user data: " + radioButtons.get(radioButton)
						+ " id: " + radioButton.getId());
			} else {
				logger.info("Loading into user data: " + radioButtons.get(radioButton)
						+ " null ");
			}

			radioButtonUserData.put("key", radioButtons.get(radioButton));
			if (radioButton != null) {
				radioButton.setUserData(radioButtonUserData);
			} else {
				logger.info("radioButton is null");
			}
			radioButtonUserData.clear();
		}

		for (TextField textField : locatorValues.keySet()) {
			Map<String, String> textFieldUserData = new HashMap<>();
			textFieldUserData.put("key", locatorValues.get(textField));
			if (textField != null) {
				textField.setUserData(textFieldUserData);
			} else {
				logger.info("textField is null");
			}
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
							if (radioButtonUserData != null) {
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
					}
				});
		if (textFieldText != null) {
			textFieldText.focusedProperty().addListener((o, oldVal, newVal) -> {
				if (!newVal) {
					textFieldText.setUserData(oldVal);
					logger.info("Value " + textFieldText.getText());
				}
			});
		} else {
			logger.info("textFieldText is null");
		}
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
