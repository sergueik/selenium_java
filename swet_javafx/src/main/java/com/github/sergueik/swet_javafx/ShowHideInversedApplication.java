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
public class ShowHideInversedApplication extends Application {

	private static final Insets SAFETY_ZONE = new Insets(10);
	private Label cowerInFear = new Label();
	private Stage mainStage;
	private static boolean done = false;

	@SuppressWarnings("restriction")
	@Override
	public void start(final Stage stage) {
		mainStage = stage;
		mainStage.setAlwaysOnTop(true);
		Platform.setImplicitExit(false);

		// commented the original flow of the example - need to reverse the logic
		ShowHideTask s = new ShowHideTask();
		System.err.println("First call");
		done = false;
		s.run();
		cowerInFear.setPadding(SAFETY_ZONE);
		cowerInFear.textProperty().addListener((observable, oldValue, newValue) -> {
			PauseTransition pause = new PauseTransition(Duration.seconds(120));
			pause.setOnFinished(event -> stage.hide());
			pause.play();
		});

		cowerInFear.setOnMouseClicked(event -> {
			// timer.cancel();
			done = true;
			stage.hide();
		});
		while (!done) {
			try {
				System.err.println("Waiting.");
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		System.err.println("Second call");
		done = false;
		s.run();
		while (!done) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		System.err.println("Third call");
		done = false;
		s.run();
		while (!done) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		stage.setScene(new Scene(cowerInFear));
		Platform.exit();
	}

	public class ShowHideTask implements Runnable {
		private String[] attacks = { "hugs you", "reads you a bedtime story",
				"sings you a lullaby", "puts you to sleep" };

		private Random random = new Random(42);

		@Override
		public void run() {
			// access containment class member directly
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