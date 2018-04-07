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
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.*;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.awt.event.WindowEvent;
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

	private Map<String, String> inputData = new HashMap<>();

	public void setInputData(Map<String, String> inputData) {
		this.inputData = inputData;
		this.textFieldID.textProperty().set("xxx");
	}

	public Map<String, String> getInputData() {
		return this.inputData;
	}

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
	private AnchorPane topAnchorPane;

	@SuppressWarnings("restriction")
	@FXML
	private Button buttonDelete;

	@SuppressWarnings("restriction")
	@FXML
	private Button buttonCancel;

	@SuppressWarnings("restriction")
	@FXML
	private RadioButton radioButtonID;

	@SuppressWarnings("restriction")
	@FXML
	private RadioButton radioButtonCSS;

	@SuppressWarnings("restriction")
	@FXML
	private RadioButton radioButtonXPath;

	@SuppressWarnings("restriction")
	@FXML
	private RadioButton radioButtonText;

	@SuppressWarnings("restriction")
	@FXML
	private ToggleGroup toggleGroup;

	@SuppressWarnings("restriction")
	@FXML
	private TextField textFieldID;

	@SuppressWarnings("restriction")
	@FXML
	private TextField textFieldCSS;

	@SuppressWarnings("restriction")
	@FXML
	private TextField textFieldXPath;

	@SuppressWarnings("restriction")
	@FXML
	private TextField textFieldText;

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
	private Map<RadioButton, String> radioButtons = new HashMap<>();

	private Map<TextField, String> locatorValues = new HashMap<>();

	public void setMainStage(@SuppressWarnings("restriction") Stage mainStage) {
		this.mainStage = mainStage;
	}

	public Map<String, String> radioButtonUserData = new HashMap<>();

	// public void initialize(URL url, ResourceBundle rb){

	@SuppressWarnings({ "restriction", "unchecked" })
	@FXML
	private void initialize() {

		logger.info("Start.");
		// https://findusages.com/search/javafx.scene.layout.AnchorPane/addEventHandler$2
		/*
		topAnchorPane.addEventHandler(WindowEvent.WINDOW_ACTIVATED,
				new EventHandler<WindowEvent>() {
					@Override
					public void handle(WindowEvent event) {
		
						mouseClicked(event.getX(), event.getY());
					}
				});
		*/
		// topAnchorPane.layoutChildren();
		// Use any of the controls that is bound in the Controller and use
		// getScene() on it.
		// Remember *not* to use it in initialize() as the root element(though
		// completely processed) is still not placed on the scene when initialize()
		// is called for the controller
		// which gives?
		// https://stackoverflow.com/questions/26060859/javafx-getting-scene-from-a-controller

		/*
		Scene scene = // this.fxmlEdit.getScene();
				this.mainStage.getScene();
		inputs = (Map<String, Object>) scene.getUserData();
		inputData = (Map<String, String>) inputs.get("inputs");
		logger.info("Controller loaded " + inputData.toString());
		*/
		logger.info("Controller loaded " + inputData.toString());
		// https://stackoverflow.com/questions/19785130/javafx-event-when-objects-are-rendered
		// NOTE: effectively shadow the ID
		this.radioButtons.put(this.radioButtonID, "ID");
		this.radioButtons.put(this.radioButtonCSS, "CSS");
		this.radioButtons.put(this.radioButtonText, "Text");
		this.radioButtons.put(this.radioButtonXPath, "XPath");
		// https://stackoverflow.com/questions/13246211/javafx-how-to-get-stage-from-controller-during-initialization/30910015
		if (this.radioButtonXPath != null) {
			this.radioButtonXPath.setUserData(radioButtonUserData);
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
		this.locatorValues.put(this.textFieldID, "ID");
		this.locatorValues.put(this.textFieldCSS, "CSS");
		this.locatorValues.put(this.textFieldText, "Text");
		this.locatorValues.put(this.textFieldXPath, "XPath");

		for (TextField textField : this.locatorValues.keySet()) {
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

		Scene scene = mainStage.getScene();
		logger
				.info("Controller about to access main stage " + mainStage.toString());
		@SuppressWarnings("unchecked")
		Map<String, Object> results = (Map<String, Object>) scene.getUserData();
		if (results != null) {
			logger.info("Controller about to update " + results.toString());
			Map<String, String> resultData = (Map<String, String>) results
					.get("results");
			// logger.info("Controller about to update " + inputData.toString());
		} else {

			logger.info("Controller see no inputs to update.");

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
