package example;

import org.apache.log4j.Category;

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
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import example.ChoiceItem;
import example.ChoicesDialog;
import example.ConfigFormEx;

import name.antonsmirnov.javafx.dialog.Dialog;

@SuppressWarnings("restriction")
public class FlowPaneEx extends Application {

	@SuppressWarnings("deprecation")
	static final Category logger = Category.getInstance(FlowPaneEx.class);
	static Stage stage = null;

	@Override
	public void start(Stage stage) {
		this.stage = stage;
		stage.setTitle("SWET");
		stage.setWidth(500);
		stage.setHeight(160);
		Scene scene = new Scene(new Group());

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
					flow.getChildren().add(new Button(String.format("Step %d", i)));
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

		Button saveButton = new Button();
		Image saveImage = new Image(
				getClass().getClassLoader().getResourceAsStream("save_32.png"));
		saveButton.setGraphic(new ImageView(saveImage));
		saveButton.setTooltip(new Tooltip("Save session"));
		saveButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				try {
					testFunction();
				} catch (Exception e) {

					Dialog.showThrowable("Exception", "Exception thrown",
							(Exception) e /* e.getCause()*/, stage);
				}
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
				loadButton, saveButton, configButton, quitButton);

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

		ChoiceItem[] items = new ChoiceItem[] {
				new ChoiceItem("Exit and save my project", 2),
				new ChoiceItem("Exit and don't save", 1),
				new ChoiceItem("Don't exit", 10), };

		ChoicesDialog choicesDialog = new ChoicesDialog(stage, items);
		choicesDialog.setChoices(items);
		// choicesDialog.initStyle(StageStyle.UNDECORATED);
		choicesDialog.sizeToScene();
		// optionally hide self
		// ((Node)(event.getSource())).getScene().getWindow().hide();
		/*		choicesDialog.show();
		while (choicesDialog.isShowing()) {
		
		}
		*/
		choicesDialog.showAndWait();
		int code = Integer.parseInt(choicesDialog.getResult());
		logger.info("Exit app with code: " + code);
		if (code == 1 || code == 2) {
			stage.close();
		}

	}

	private static void testFunction() throws Exception {
		throw new Exception("This is a test exception.");
	}
}
