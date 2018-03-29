package example;

import java.util.concurrent.*;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.application.Platform;
import javafx.beans.property.*;
import javafx.concurrent.Task;
import javafx.event.*;
import javafx.geometry.Pos;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.*;

// origin: https://gist.github.com/jewelsea/4989970
// https://stackoverflow.com/questions/14941084/javafx2-can-i-pause-a-background-task-service
@SuppressWarnings("restriction")
public class PromptingTaskDemo extends Application {
	private static final String[] SAMPLE_TEXT = "MISSING Lorem ipsum dolor sit amet MISSING consectetur adipisicing elit sed do eiusmod tempor incididunt MISSING ut labore et dolore magna aliqua"
			.split(" ");

	@Override
	public void start(Stage primaryStage) {
		Label status = new Label();
		ProgressBar progress = new ProgressBar();

		VBox textContainer = new VBox(10);
		textContainer.setStyle("-fx-background-color: burlywood; -fx-padding: 10;");

		LoadTextTask task = new LoadTextTask(SAMPLE_TEXT, textContainer);
		status.textProperty().bind(task.messageProperty());
		progress.progressProperty().bind(task.progressProperty());

		final Thread taskThread = new Thread(task, "label-generator");
		taskThread.setDaemon(true);

		VBox layout = new VBox(10);
		layout.getChildren().addAll(status, progress, textContainer);
		layout.setStyle("-fx-background-color: cornsilk; -fx-padding: 10;");

		primaryStage.setScene(new Scene(layout, 300, 700));
		primaryStage.show();

		taskThread.start();
	}

	public static void main(String[] args) {
		launch(args);
	}
}

@SuppressWarnings("restriction")
class LoadTextTask extends Task<Void> {
	private final String[] lines;
	private final Pane container;
	private final IntegerProperty idx = new SimpleIntegerProperty(0);

	LoadTextTask(final String[] lines, final Pane container) {
		this.lines = lines;
		this.container = container;
	}

	@Override
	protected Void call() throws Exception {
		try {
			updateProgress(0, lines.length);

			while (idx.get() < lines.length) {
				final Label nextLabel = new Label();
				final int curIdx = idx.get();
				updateMessage("Reading Line: " + curIdx);
				String nextText = lines[curIdx];

				if ("MISSING".equals(nextText)) {
					updateMessage("Prompting for missing text for line: " + curIdx);
					FutureTask<String> futureTask = new FutureTask<>(
							new MissingTextPrompt(container.getScene().getWindow()));
					Platform.runLater(futureTask);
					nextText = futureTask.get();

					nextLabel.setStyle("-fx-background-color: palegreen;");
				}
				nextLabel.setText(nextText);

				Platform.runLater(new AddNodeLater(container, curIdx, nextLabel));
				idx.set(curIdx + 1);

				updateProgress(curIdx + 1, lines.length);

				Thread.sleep(200);
			}

			updateMessage("Loading Text Completed: " + idx.get() + " lines loaded.");
		} catch (Throwable t) {
			t.printStackTrace();
		}
		return null;
	}

	class MissingTextPrompt implements Callable<String> {
		final Window owner;

		MissingTextPrompt(Window owner) {
			this.owner = owner;
		}

		@Override
		public String call() throws Exception {
			final Stage dialog = new Stage();
			dialog.setTitle("Enter Missing Text");
			dialog.initOwner(owner);
			dialog.initStyle(StageStyle.UTILITY);
			dialog.initModality(Modality.WINDOW_MODAL);

			final TextField textField = new TextField();
			final Button submitButton = new Button("Submit");
			submitButton.setDefaultButton(true);
			submitButton.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent t) {
					dialog.close();
				}
			});

			final VBox layout = new VBox(10);
			layout.setAlignment(Pos.CENTER_RIGHT);
			layout.setStyle("-fx-background-color: azure; -fx-padding: 10;");
			layout.getChildren().setAll(textField, submitButton);

			dialog.setScene(new Scene(layout));
			dialog.showAndWait();

			return textField.getText();
		}
	}

	class AddNodeLater implements Runnable {
		final Pane container;
		final Node node;
		final int idx;

		public AddNodeLater(final Pane container, final int idx, final Node node) {
			this.container = container;
			this.node = node;
			this.idx = idx;
		}

		@Override
		public void run() {
			container.getChildren().add(idx, node);
		}
	}
}