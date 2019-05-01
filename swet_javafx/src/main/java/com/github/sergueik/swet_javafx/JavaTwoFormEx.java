package com.github.sergueik.swet_javafx;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

// based on: http://www.cyberforum.ru/javafx/thread2437152.html
// added passing object to connect forms
@SuppressWarnings("restriction")
public class JavaTwoFormEx extends Application {
	private Button btn = new Button("Open new Window");
	private StackPane root = new StackPane();

	@Override
	public void start(Stage primaryStage) {
		primaryStage.setTitle("Change button text");
		btn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				Stage stage = new Stage();
				((Button) event.getSource()).setText("Open new Window");
				Button btn2 = new Button("Change the parent button text");
				btn2.setUserData(btn);
				btn.setText("Change button text");
				btn2.setOnAction(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent event) {
						Button bnt2 = (Button) event.getTarget();
						Button btn3 = (Button) btn2.getUserData();
						btn3.setText("Text changed: " + btn2.hashCode());
					}
				});

				StackPane root2 = new StackPane();
				root2.getChildren().add(btn2);
				stage.setScene(new Scene(root2, 600, 400));
				stage.show();
			}
		});

		root.getChildren().add(btn);
		primaryStage.setScene(new Scene(root, 800, 600));
		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}
