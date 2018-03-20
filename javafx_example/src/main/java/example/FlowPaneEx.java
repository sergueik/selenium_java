package example;

import org.apache.log4j.Category;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.Scene;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

// import example.ChoiceItem;
import example.ChoicesDialog;
import example.ConfigFormEx;

import name.antonsmirnov.javafx.dialog.Dialog;

@SuppressWarnings("restriction")
public class FlowPaneEx extends Application {

	@SuppressWarnings("deprecation")
	static final Category logger = Category.getInstance(FlowPaneEx.class);
	static Stage stage = null;
	String configFilePath = null;
	Scene scene = null;

	@Override
	public void start(Stage stage) {
		this.stage = stage;
		stage.setTitle("SWET/javaFx");
		stage.setWidth(500);
		stage.setHeight(160);
		scene = new Scene(new Group());

		VBox vbox = new VBox();

		FlowPane flow = new FlowPane();
		flow.setVgap(8);
		flow.setHgap(4);
		flow.setPrefWrapLength(300);

		Button launchButton = new Button();
		Image launchImage = new Image(
				getClass().getClassLoader().getResourceAsStream("browsers_32.png"));
		launchButton.setGraphic(new ImageView(launchImage));
		launchButton.setTooltip(new Tooltip("Launch browser"));

		Button injectButton = new Button();
		Image injectImage = new Image(
				getClass().getClassLoader().getResourceAsStream("find_32.png"));
		injectButton.setGraphic(new ImageView(injectImage));
		injectButton.setTooltip(new Tooltip("Inject script"));
		injectButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				System.out.println("generate step!");
				for (int i = 0; i < 20; i++) {
					Button stepButton = new Button(String.format("Step %d", i));
					stepButton.setOnAction(new EventHandler<ActionEvent>() {
						@Override
						public void handle(ActionEvent event) {
							// ComplexFormEx
							Map<String, String> inputData = new HashMap<>();
							Button button = (Button) event.getTarget();

							inputData.put("dummy", "42");
							inputData.put("title",
									String.format("%s element Locators", button.getText()));
							Map<String, Map> inputs = new HashMap<>();
							inputs.put("inputs", inputData); // TODO: JSON
							scene.setUserData(inputs);
							logger.info(
									"launching complexFormEx for " + inputData.get("title"));

							ComplexFormEx complexFormEx = new ComplexFormEx();
							complexFormEx.setScene(scene);
							try {
								complexFormEx.start(new Stage());
							} catch (Exception e) {
							}
						}
					});

					flow.getChildren().add(stepButton);

				}
			}
		});
		Button generateButton = new Button();
		Image generateImage = new Image(
				getClass().getClassLoader().getResourceAsStream("codegen_32.png"));
		generateButton.setGraphic(new ImageView(generateImage));
		generateButton.setTooltip(new Tooltip("Generate program"));

		Button loadButton = new Button();
		Image loadImage = new Image(
				getClass().getClassLoader().getResourceAsStream("open_32.png"));
		loadButton.setGraphic(new ImageView(loadImage));
		loadButton.setTooltip(new Tooltip("Load session"));
		loadButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				// https://docs.oracle.com/javafx/2/ui_controls/file-chooser.htm
				FileChooser fileChooser = new FileChooser();
				if (configFilePath != null) {
					logger.info("Loading recording from: " + configFilePath);
					try {
						fileChooser.setInitialDirectory(new File(configFilePath));
					} catch (IllegalArgumentException e) {
						logger.info("Exception (ignored): " + e.toString());
					}
				}
				// Set extension filter
				fileChooser.getExtensionFilters()
						.add(new FileChooser.ExtensionFilter("YAML file", "*.yaml"));
				fileChooser.setTitle("Open Recording");
				File file = fileChooser.showOpenDialog(stage);
				if (file != null) {
					logger.info("Load recording: " + file.getPath());
					configFilePath = file.getParent();
					openRecordingFile(file);
				}
			}
		});

		Button testsuiteButton = new Button();
		Image testsuiteImage = new Image(
				getClass().getClassLoader().getResourceAsStream("excel_gen_32.png"));
		testsuiteButton.setGraphic(new ImageView(testsuiteImage));
		testsuiteButton.setTooltip(new Tooltip("Generate Excel TestSuite"));
		testsuiteButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {

				// Stage stage = new Stage();
				Map<String, String> inputData = new HashMap<>();
				inputData.put("dummy", "42");
				inputData.put("title", "Step detail");
				Map<String, Map> inputs = new HashMap<>();
				inputs.put("inputs", inputData); // TODO: JSON
				scene.setUserData(inputs);

				TableEditorEx tableEditorEx = new TableEditorEx();
				tableEditorEx.setScene(scene);
				try {
					tableEditorEx.start(new Stage());
				} catch (Exception e) {
				}
			}
		});

		Button saveButton = new Button();
		Image saveImage = new Image(
				getClass().getClassLoader().getResourceAsStream("save_32.png"));
		saveButton.setGraphic(new ImageView(saveImage));
		saveButton.setTooltip(new Tooltip("Save session"));
		saveButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				try {
					System.err.println("Exercise exception");
					testException();
				} catch (Exception e) {
					Dialog.showThrowable("Exception", "Exception thrown",
							(Exception) e /* e.getCause()*/, stage);
				}

				// save

				// https://docs.oracle.com/javafx/2/ui_controls/file-chooser.htm
				FileChooser fileChooser = new FileChooser();
				if (configFilePath != null) {
					logger.info("Saving recording to: " + configFilePath);
					try {
						fileChooser.setInitialDirectory(new File(configFilePath));
					} catch (IllegalArgumentException e) {
						logger.info("Exception (ignored): " + e.toString());
					}
				}
				// Set extension filter
				fileChooser.getExtensionFilters()
						.add(new FileChooser.ExtensionFilter("YAML file", "*.yaml"));
				fileChooser.setTitle("Save Recording");
				File file = fileChooser.showSaveDialog(stage);
			}
		});

		Button configButton = new Button();
		Image configImage = new Image(
				getClass().getClassLoader().getResourceAsStream("preferences_32.png"));
		configButton.setGraphic(new ImageView(configImage));
		configButton.setTooltip(new Tooltip("Configure"));
		configButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				Stage stage = new Stage();
				ConfigFormEx s = new ConfigFormEx();
				s.start(stage);
			}
		});

		Button quitButton = new Button();
		Image quitImage = new Image(
				getClass().getClassLoader().getResourceAsStream("quit_32.png"));
		quitButton.setGraphic(new ImageView(quitImage));
		quitButton.setTooltip(new Tooltip("Exit"));

		stage.setOnCloseRequest(e -> {
			e.consume();
			confirmClose();
		});

		quitButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				confirmClose();
			}
		});

		HBox toolbarHbox = new HBox();
		toolbarHbox.getChildren().addAll(launchButton, injectButton, generateButton,
				loadButton, testsuiteButton, saveButton, configButton, quitButton);

		Label statusLabel = new Label();
		statusLabel.setText("Status");
		HBox statusbarHbox = new HBox();
		statusbarHbox.getChildren().addAll(statusLabel);
		HBox.setHgrow(statusLabel, Priority.ALWAYS);

		vbox.getChildren().add(toolbarHbox);
		vbox.getChildren().add(flow);
		vbox.getChildren().add(statusbarHbox);
		VBox.setVgrow(flow, Priority.ALWAYS);

		scene.setRoot(vbox);
		stage.setScene(scene);
		stage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}

	public void confirmClose() {

		Map<String, Integer> inputData = new HashMap<>();
		inputData.put("Exit and save my project", 2);
		inputData.put("Exit and don't save", 1);
		inputData.put("Don't exit", 10);
		Map<String, Map<String, Integer>> inputs = new HashMap<>();
		inputs.put("inputs", inputData); // TODO: JSON
		scene.setUserData(inputs);

		ChoicesDialog choicesDialog = new ChoicesDialog(new Stage(), scene);
		// choicesDialog.initStyle(StageStyle.UNDECORATED);
		choicesDialog.sizeToScene();
		// optionally hide self
		// ((Node)(event.getSource())).getScene().getWindow().hide();
		/*		choicesDialog.show();
		while (choicesDialog.isShowing()) {
		
		}
		*/
		choicesDialog.showAndWait();
		@SuppressWarnings("unchecked")
		Map<String, String> data = (Map<String, String>) choicesDialog.getScene()
				.getUserData();
		int code = Integer.parseInt(data.get("result"));
		logger.info("Exit app with code: " + code);
		// code = Integer.parseInt(choicesDialog.getResult());
		// logger.info("Exit app with code: " + code);
		if (code == 1 || code == 2) {
			stage.close();
		}

	}

	private static void openRecordingFile(File file) {
	}

	private static void testException() throws Exception {
		throw new Exception("This is a test exception.");
	}
}
