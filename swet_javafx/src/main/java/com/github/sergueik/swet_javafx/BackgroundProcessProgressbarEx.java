package com.github.sergueik.swet_javafx;

import javafx.application.Application;
import javafx.application.Platform;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

// based on: 
// http://www.java2s.com/Code/Java/JavaFX/ProgressBarandBackgroundProcesses.htm
// https://docs.oracle.com/javase/8/javafx/api/javafx/concurrent/Task.html

@SuppressWarnings("restriction")
public class BackgroundProcessProgressbarEx extends Application {
	@SuppressWarnings("rawtypes")
	Task backgroundProcess;
	final static int iterations = 10;
	private Button buttonStart;

	public static void main(String[] args) {
		Application.launch(args);
	}

	@Override
	public void start(Stage primaryStage) {
		primaryStage.setTitle("Background Processes");
		Group root = new Group();
		Scene scene = new Scene(root, 330, 120, Color.WHITE);

		BorderPane mainPane = new BorderPane();
		root.getChildren().add(mainPane);

		final Label label = new Label("Files Transfer Mockup");
		final ProgressBar progressBar = new ProgressBar(0);

		final HBox hbox = new HBox();
		hbox.setSpacing(5);
		hbox.setAlignment(Pos.CENTER);
		hbox.getChildren().addAll(label, progressBar);
		mainPane.setTop(hbox);

		buttonStart = new Button("Start");
		final Button buttonCancel = new Button("Cancel");
		final HBox hb2 = new HBox();
		hb2.setSpacing(5);
		hb2.setAlignment(Pos.CENTER);
		hb2.getChildren().addAll(buttonStart, buttonCancel);
		mainPane.setBottom(hb2);

		buttonStart.setOnAction(new EventHandler<ActionEvent>() {

			public void handle(ActionEvent event) {
				buttonStart.setDisable(true);
				progressBar.setProgress(0);
				buttonCancel.setDisable(false);
				backgroundProcess = createWorker();

				// TODO: currently shows progress twice
				progressBar.progressProperty().unbind();
				progressBar.progressProperty()
						.bind(backgroundProcess.progressProperty());

				backgroundProcess.messageProperty()
						.addListener(new ChangeListener<String>() {
							public void changed(ObservableValue<? extends String> observable,
									String oldValue, String newValue) {
								System.err.println(newValue);
							}
						});

				new Thread(backgroundProcess).start();
			}
		});
		buttonCancel.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				if (backgroundProcess != null) {
					System.err.println("Canceling.");
					buttonStart.setDisable(false);
					buttonCancel.setDisable(true);
					backgroundProcess.cancel(true);
					progressBar.progressProperty().unbind();
					progressBar.setProgress(0);
					buttonStart.setDisable(false);
					buttonCancel.setDisable(false);
					System.err.println("Canceled.");
				}
			}
		});
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	public Task<Boolean> createWorker() {
		return new Task<Boolean>() {

			@Override
			protected Boolean call() throws Exception {

				for (int i = 0; i < iterations; i++) {
					int iteration = i;
					if (isCancelled()) {
						break;
					}
					Thread.sleep(2000);
					updateMessage("Iteration " + iteration);
					updateProgress(iteration + 1, iterations);
					// prevent java.lang.IllegalStateException: Not on FX application
					// thread
					Platform.runLater(new Runnable() {
						@Override
						public void run() {
							buttonStart.setVisible(false);
							buttonStart.setText("Iteration " + iteration);
							buttonStart.setVisible(true);
						}
					});
					Platform.runLater(() -> {
						buttonStart.setVisible(false);
						buttonStart.setText("Iteration " + iteration);
						buttonStart.setVisible(true);
					});
				}
				System.err.println("Done.");
				return true;
			}
		};
	}
}
