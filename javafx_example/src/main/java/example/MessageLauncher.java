// https://stackoverflow.com/questions/9846046/run-main-class-of-maven-project
// https://github.com/kurnakov92/CalculatorFX/blob/master/src/main/java/calculator/conrollers/AlertDialogController.java
package example;

import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import java.awt.*;

public class MessageLauncher extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {
		Button fadingAlertbButton = new Button("Fading Alert");
		Button modalLaunchButton = new Button("Modal Alert");
		HBox box = new HBox(50, fadingAlertbButton, modalLaunchButton);
		box.setAlignment(Pos.CENTER);
		primaryStage.setTitle("Messagebox/javaFx");

		primaryStage.setScene(new Scene(box, 260, 40));
		primaryStage.show();

		fadingAlertbButton.setOnAction(event -> new ToolWin("Lorem ipsum...").show());
		modalLaunchButton.setOnAction(
				event -> new modAlert(Alert.AlertType.INFORMATION, "Lorem ipsum...").Show());
	}

	class modAlert extends Alert {

		private Thread thread;
		private javafx.stage.Window window;

		public modAlert(AlertType alertType, String contentText,
				ButtonType... buttons) {
			super(alertType, contentText, buttons);
			setHeaderText("test");
			window = getDialogPane().getScene().getWindow();
			// System.out.println(window);
			thread = new Thread(() -> {
				try {
					Thread.sleep(3000);
				} catch (InterruptedException ignored) {
				}
				long startTime = System.currentTimeMillis();
				while (System.currentTimeMillis() - startTime < 1000) {
					try {
						Thread.sleep(50);
					} catch (InterruptedException ignored) {
					}
					Platform.runLater(
							() -> window.setOpacity(Math.max(0, window.getOpacity() - .05)));
				}
				Platform.runLater(this::close);
			});

		}

		void Show() {
			show();
			thread.start();
		}
	}

	class ToolWin extends Application {
		private String contentText;
		private Parent root;
		private FadeTransition fade;
		private Stage thisStage;

		private double WIDTH = 300, HEIGHT = 200;

		ToolWin(String contentText) {
			this.contentText = contentText;
		}

		void show() {
			try {
				init();
				start(new Stage(StageStyle.TRANSPARENT));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		@Override
		public void init() throws Exception {
			super.init();
			root = new Group(new StackPane(
					new Rectangle(WIDTH, HEIGHT,
							new Color(0.6784314f, 0.84705883f, 0.9019608f, .9)),
					new Label(contentText)));
			fade = new FadeTransition(Duration.seconds(1), root);
		}

		@Override
		public void start(Stage stage) throws Exception {
			thisStage = stage;
			stage.setAlwaysOnTop(true);
			stage.setScene(new Scene(root, Color.TRANSPARENT));
			Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
			stage.setX(size.getWidth() - WIDTH - 10);
			stage.setY(size.getHeight() - HEIGHT - 10);
			stage.show();

			fade.setFromValue(0);
			fade.setToValue(.9);
			fade.play();

			Thread thread = new Thread(() -> {
				try {
					Thread.sleep(5000);
				} catch (InterruptedException ignored) {
				}
				Platform.runLater(() -> {
					try {
						stop();
					} catch (Exception ignored) {
					}
				});
			});
			fade.setOnFinished(event -> thread.start());
		}

		@Override
		public void stop() throws Exception {
			fade.setFromValue(.9);
			fade.setToValue(0);
			fade.play();

			fade.setOnFinished(event -> thisStage.close());
		}
	}
}