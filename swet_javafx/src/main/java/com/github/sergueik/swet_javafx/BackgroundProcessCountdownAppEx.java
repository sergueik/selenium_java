package com.github.sergueik.swet_javafx;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

// based on: 
// https://riptutorial.com/javafx/example/7291/updating-the-ui-using-platform-runlater
@SuppressWarnings("restriction")
public class BackgroundProcessCountdownAppEx extends Application {

	private static int count = 10;;
	private static int delay = 1000;
	private final Text text = new Text(Integer.toString(count));

	@Override
	public void start(Stage primaryStage) {
		StackPane root = new StackPane();
		root.getChildren().add(text);

		Scene scene = new Scene(root, 200, 200);

		// thread to run time consuming operation on
		Thread thread = new Thread(new Runnable() {

			@Override
			public void run() {

				while (true) {
					try {
						Thread.sleep(delay);
					} catch (InterruptedException ex) {
					}

					// UI update is run on the Application thread
					Platform.runLater(new Runnable() {

						@Override
						public void run() {
							count--;
							if (count > 0) {
								text.setText(Integer.toString(count));
							} else {
								Platform.exit();
								System.exit(0);
							}
						}
					});
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
		if (args.length > 0) {
			try {
				count = Integer.parseInt(args[0]);
			} catch (Exception e) {
			}
		}
		launch(args);
	}

}
