package com.github.sergueik.swet_javafx;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.scene.layout.HBox;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;

@SuppressWarnings("restriction")
// parent widget
public class ModalDialogEx extends Application {
	private final int width = 400;
	private final int height = 300;

	public static void main(String[] args) {
		Application.launch(args);
	}

	@Override
	public void start(final Stage stage) {
		stage.setTitle("Dialog");
		Group group = new Group();
		HBox hbox = new HBox();
		Button button = new Button("Close");
		button.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				stage.close();
			}
		});
		hbox.getChildren().add(button);
		hbox.setSpacing(20);
		hbox.setPadding(new Insets(10));
		group.getChildren().add(hbox);
		stage.setScene(new Scene(group, width, 300, Color.WHITE));

		stage.show();

		Stage stage2 = new Stage();
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {

		}
		// TODO: better than switching to brandh new stage
		Stage ModalDialog = new ModalDialog(stage);
		ModalDialog.sizeToScene();
		ModalDialog.show();
		// TODO: block in a SWT fashion?
		// https://docs.oracle.com/javafx/2/swt_interoperability/jfxpub-swt_interoperability.htm
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {

		}

		// stage.close();
	}
}

class ModalDialog extends Stage {

	private final int width = 320;
	private final int height = 150;

	@SuppressWarnings("restriction")
	public ModalDialog(Stage stage) {
		super();
		initOwner(stage);
		setTitle("title");
		initModality(Modality.APPLICATION_MODAL);
		Group group = new Group();
		// Scene scene = ;
		setScene(new Scene(group, width, height, Color.WHITE));

		GridPane gridpane = new GridPane();
		gridpane.setPadding(new Insets(5));
		gridpane.setHgap(5);
		gridpane.setVgap(5);

		Label userNameLabel = new Label("User Name: ");
		gridpane.add(userNameLabel, 0, 1);

		Label passwordLbl = new Label("Password: ");
		gridpane.add(passwordLbl, 0, 2);
		final TextField userNameFld = new TextField("Admin");
		gridpane.add(userNameFld, 1, 1);

		final PasswordField passwordField = new PasswordField();
		passwordField.setText("password");
		gridpane.add(passwordField, 1, 2);

		Button button = new Button("Change");
		button.setOnAction(new EventHandler<ActionEvent>() {

			public void handle(ActionEvent event) {
				close();
			}
		});
		gridpane.add(button, 1, 3);
		GridPane.setHalignment(button, HPos.RIGHT);
		group.getChildren().add(gridpane);
	}
}
