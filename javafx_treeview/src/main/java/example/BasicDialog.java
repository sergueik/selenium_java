package example;

import javafx.application.Application;

import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import javafx.geometry.Insets;

@SuppressWarnings("restriction")
public class BasicDialog extends Application {

	Label status;
	TextField nameField;
	TextField emailField;
	TextArea commentsField;

	@Override
	public void start(Stage primaryStage) throws Exception {
		status = new Label();
		status.setId("status-label");
		VBox panel = new VBox(20);
		panel.getChildren().addAll(row("Name", nameField = new TextField()), row("Email", emailField = new TextField()),
				row("Comments", commentsField = new TextArea()), row("", buttons()), status);
		HBox root = new HBox(10);
		root.getChildren().addAll(panel);
		HBox.setMargin(panel, new Insets(10));
		Scene scene = new Scene(root, 500, 400);
		primaryStage.setScene(scene);
		primaryStage.centerOnScreen();
		primaryStage.show();
	}

	public Node row(String labelText, Node field) {
		HBox row = new HBox(10);
		Label label = new Label(labelText);
		label.setMinWidth(120);
		row.getChildren().addAll(label, field);
		return row;
	}

	public Node buttons() {
		HBox buttons = new HBox(10);
		Button cancel = new Button("Cancel");
		cancel.setOnAction((e) -> status.setText("You cancelled"));
		Button ok = new Button("OK");
		ok.setOnAction((e) -> status.setText(computeStatus()));
		buttons.getChildren().addAll(cancel, ok);
		return buttons;
	}

	private String computeStatus() {
		return "Name: " + nameField.getText() + ", Email: " + emailField.getText() + ", Comments: "
				+ commentsField.getText();
	}

	public static void main(String[] args) {
		Application.launch(BasicDialog.class);
	}

}
