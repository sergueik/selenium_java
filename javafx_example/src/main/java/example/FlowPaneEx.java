package example;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Pos;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.stage.StageStyle;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Category;

@SuppressWarnings("restriction")
public class FlowPaneEx extends Application {

	static final Category logger = Category.getInstance(FlowPaneEx.class);

	@Override
	public void start(Stage stage) {
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

		Button configButton = new Button();
		Image configImage = new Image(
				getClass().getClassLoader().getResourceAsStream("preferences_32.png"));
		configButton.setGraphic(new ImageView(configImage));
		configButton.setTooltip(new Tooltip("Configure"));

		Button quitButton = new Button();
		Image quitImage = new Image(
				getClass().getClassLoader().getResourceAsStream("quit_32.png"));
		quitButton.setGraphic(new ImageView(quitImage));
		quitButton.setTooltip(new Tooltip("Exit"));

		quitButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				Stage stage = new Stage();

				Stage ChoicesDialog = new ChoicesDialog(stage);
				ChoicesDialog.initStyle(StageStyle.UNDECORATED);
				ChoicesDialog.sizeToScene();
				ChoicesDialog.show();
				int code = 0;
				logger.info("Exit app with code: " + code);

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
}

// see also: https://github.com/4ntoine/JavaFxDialog/wiki
//
class ChoicesDialog extends Stage {

	// NOTE: can't do that
	// private static final Map<String, String> userData = new HashMap<>();
	static final Category logger = Category.getInstance(ChoicesDialog.class);

	// https://stackoverflow.com/questions/12830402/javafx-2-buttons-size-fill-width-and-are-each-same-width
	@SuppressWarnings("restriction")
	public ChoicesDialog(Stage stage) {
		super();
		// https://stackoverflow.com/questions/8341305/how-to-remove-javafx-stage-buttons-minimize-maximize-close
		// stage.initStyle(StageStyle.UNDECORATED);
		initOwner(stage);
		setTitle("title");
		initModality(Modality.APPLICATION_MODAL);
		Group root = new Group();
		HBox outerHBox = new HBox();
		outerHBox.setAlignment(Pos.CENTER);

		Scene scene = new Scene(root, 300, 200);
		setScene(scene);

		VBox outerVBox = new VBox();
		outerHBox.getChildren().add(outerVBox);
		outerHBox.setSpacing(12);
		scene.setRoot(outerHBox);

		HBox labelHBox = new HBox();
		Label label = new Label();
		label.setText("Do yo relly want to exit?");
		labelHBox.setHgrow(label, Priority.ALWAYS);
		labelHBox.getChildren().add(label);

		HBox buttonHBox1 = new HBox();
		Button button1 = new Button();
		button1.setText("Exit and save my project");
		button1.setMaxWidth(Double.MAX_VALUE);
		buttonHBox1.setHgrow(button1, Priority.ALWAYS);
		buttonHBox1.getChildren().add(button1);
		Map<String, String> userData = new HashMap<>();
		userData.put("test", "1");
		// userData.replace("test", "1");
		button1.setUserData(userData);
		button1.setOnAction(new buttonHandler());

		HBox buttonHBox2 = new HBox();
		Button button2 = new Button();
		button2.setText("Exit and don't save");
		button2.setMaxWidth(Double.MAX_VALUE);
		buttonHBox2.setHgrow(button2, Priority.ALWAYS);
		buttonHBox2.getChildren().add(button2);
		userData = new HashMap<>();
		userData.put("test", "2");
		// userData.replace("test", "2");
		button2.setUserData(userData);
		button2.setOnAction(new buttonHandler());

		HBox buttonHBox3 = new HBox();
		Button button3 = new Button();
		button3.setText("Don't exit");
		button3.setMaxWidth(Double.MAX_VALUE);
		userData = new HashMap<>();
		userData.put("test", "3");
		button3.setUserData(userData);
		button3.setOnAction(new buttonHandler());
		/*
		button3.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				close();
			}
		});
		*/
		buttonHBox3.setHgrow(button3, Priority.ALWAYS);
		buttonHBox3.getChildren().add(button3);

		outerVBox.setSpacing(12);
		outerVBox.getChildren().addAll(labelHBox, buttonHBox1, buttonHBox2,
				buttonHBox3);

		/*
				GridPane gridpane = new GridPane();
				gridpane.setPadding(new Insets(5));
				gridpane.setHgap(5);
				gridpane.setVgap(5);
		
				Label userNameLbl = new Label("User Name: ");
				gridpane.add(userNameLbl, 0, 1);
		
				Label passwordLbl = new Label("Password: ");
				gridpane.add(passwordLbl, 0, 2);
				final TextField userNameFld = new TextField("Admin");
				gridpane.add(userNameFld, 1, 1);
		
				final PasswordField passwordFld = new PasswordField();
				passwordFld.setText("password");
				gridpane.add(passwordFld, 1, 2);
		
				Button login = new Button("Change");
				login.setOnAction(new EventHandler<ActionEvent>() {
		
					public void handle(ActionEvent event) {
						close();
					}
				});
				gridpane.add(login, 1, 3);
				GridPane.setHalignment(login, HPos.RIGHT);
				root.getChildren().add(gridpane);
				*/
	}

	private static class buttonHandler implements EventHandler<ActionEvent> {
		public void handle(ActionEvent event) {
			Button button = (Button) event.getSource();

			Map<String, String> data = (Map<String, String>) button.getUserData();
			String eventData = null;
			try {
				eventData = data == null ? null : data.getOrDefault("test", null);
			} catch (Exception e) {
				System.err.println("Exception (ignored) " + e.toString());
				eventData = null;
			}
			logger.info("Received: "
					+ (eventData == null ? "no user data was received" : eventData));
			Stage stage = (Stage) button.getScene().getWindow();
			stage.close();
		}

	}
}
