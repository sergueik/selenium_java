package com.github.sergueik.swet_javafx;

import javafx.animation.PauseTransition;
import javafx.application.*;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.*;

// based on: https://stackoverflow.com/questions/24320014/how-to-call-launch-more-than-once-in-java
@SuppressWarnings("restriction")
public class ShowHideApplication extends Application {

	private static final Insets SAFETY_ZONE = new Insets(10);
	private Label cowerInFear = new Label();
	private Stage mainStage;

	@SuppressWarnings("restriction")
	@Override
	public void start(final Stage stage) {
		// wumpus rulez
		mainStage = stage;
		mainStage.setAlwaysOnTop(true);

		// the wumpus doesn't leave when the last stage is hidden.
		Platform.setImplicitExit(false);

		// commented the original flow of the example - need to reverse the logic
		Timer timer = new Timer();
		timer.schedule(new ShowHideTask(), 0, 5_000);

		cowerInFear.setPadding(SAFETY_ZONE);
		cowerInFear.textProperty().addListener((observable, oldValue, newValue) -> {
			PauseTransition pause = new PauseTransition(Duration.seconds(2));
			pause.setOnFinished(event -> stage.hide());
			pause.play();
		});

		cowerInFear.setOnMouseClicked(event -> {
			timer.cancel();
			Platform.exit();
		});

		stage.setScene(new Scene(cowerInFear));
	}

	public class ShowHideTask extends TimerTask {
		private String[] attacks = { "hugs you", "reads you a bedtime story",
				"sings you a lullaby", "puts you to sleep" };

		private Random random = new Random(42);

		@Override
		public void run() {
			// use runlater when we mess with the scene graph,
			// so we don't cross the streams, as that would be bad.
			Platform.runLater(() -> {
				cowerInFear.setText("The Wumpus " + nextAttack() + "!");
				mainStage.sizeToScene();
				mainStage.show();
			});
		}

		private String nextAttack() {
			return attacks[random.nextInt(attacks.length)];
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}