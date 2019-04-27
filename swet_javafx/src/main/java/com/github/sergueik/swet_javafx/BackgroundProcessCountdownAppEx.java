package com.github.sergueik.swet_javafx;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

// bases on: 
// https://riptutorial.com/javafx/example/7291/updating-the-ui-using-platform-runlater
@SuppressWarnings("restriction")
public class BackgroundProcessCountdownAppEx extends Application {

	private static int count = 0;
	private final Text text = new Text(Integer.toString(count));

	private void decrementCount() {
		count--;
		if (count > 0) {
			text.setText(Integer.toString(count));
		} else {
			Platform.exit();
			System.exit(0);
		}

	}

	@Override
	public void start(Stage primaryStage) {
		StackPane root = new StackPane();
		root.getChildren().add(text);

		Scene scene = new Scene(root, 200, 200);

		// longrunning operation runs on different thread
		Thread thread = new Thread(new Runnable() {

			@Override
			public void run() {
				Runnable updater = new Runnable() {

					@Override
					public void run() {
						decrementCount();
					}
				};

				while (true) {
					try {
						Thread.sleep(1000);
					} catch (InterruptedException ex) {
					}

					// UI update is run on the Application thread
					Platform.runLater(updater);
				}
			}

		});
		// don't let thread prevent JVM shutdown
		thread.setDaemon(true);
		thread.start();

		primaryStage.setScene(scene);
		primaryStage.show();
	}

	public static void main(String[] args) {
		try {
			count = Integer.parseInt(args[0]);
		} catch (Exception e) {
			count = 5;
		}
		launch(args);
	}

}
