package example.applications.notaskbaricon;
/*
 * Copyright (c) 2021 Michael Sims, Dustin Redmond and contributors
 */

import javafx.application.Application;
import javafx.application.Platform;
import java.lang.IllegalAccessError;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import java.net.URL;

import com.sun.javafx.application.PlatformImpl;
// example.applications.notaskbaricon -> com.sun.javafx.application.PlatformImpl 
// JDK internal API (JDK removed internal API)
import example.FXTrayIcon;
import example.applications.RunnableTest;

@SuppressWarnings("restriction")
public class Main extends Application {

	@Override
	public void start(Stage stage) {
		URL iconFile = new RunnableTest().getIcon();
		stage.initStyle(StageStyle.UTILITY);
		stage.setHeight(0);
		stage.setWidth(0);
		Stage mainStage = new Stage();
		mainStage.initOwner(stage);
		mainStage.initStyle(StageStyle.UNDECORATED);

		Label label = new Label("No TaskBar Icon");
		Label label2 = new Label("Type a message and click the button");
		label2.setAlignment(Pos.CENTER_LEFT);
		TextField tfInput = new TextField();
		Button button = new Button("Show Alert");
		button.setOnAction(e -> showMessage(tfInput.getText()));

		VBox vbox = new VBox();
		vbox.setPadding(new Insets(10, 20, 10, 20));
		vbox.setAlignment(Pos.CENTER);
		vbox.setSpacing(20);
		vbox.getChildren().addAll(label, label2, tfInput, button);

		StackPane root = new StackPane();
		root.getChildren().add(vbox);
		mainStage.setScene(new Scene(root, 250, 200));
		mainStage.initStyle(StageStyle.UTILITY); // This is what makes the icon
																							// disappear in Windows.
		if (FXTrayIcon.isSupported()) {
			icon = new FXTrayIcon(stage, iconFile);

			// toggle taskbarapplication

			MenuItem menuShowStage = new MenuItem("Show Stage");
			MenuItem menuHideStage = new MenuItem("Hide Stage");
			MenuItem menuShowMessage = new MenuItem("Show Message");
			MenuItem menuExit = new MenuItem("Exit");
			// Exception in thread "JavaFX Application Thread"
			// java.lang.IllegalAccessError:
			// module javafx.graphics does not export com.sun.javafx.application
			// https://code.yawk.at/org.openjfx/javafx-graphics/11/com/sun/javafx/application/PlatformImpl.java

			menuShowStage.setOnAction(event -> {

				Platform.runLater(() -> {
					try {
						PlatformImpl.setTaskbarApplication(false);
					} catch (Exception exception) {
						// ignore
						// mainStage.initStyle(StageStyle.UTILITY);
						// mainStage.setMaxHeight(0);
						// mainStage.setMaxWidth(0);
						// mainStage.setX(Double.MAX_VALUE);
					}
				});
				System.err.println("Show mainstage");
				mainStage.show();
			});
			menuHideStage.setOnAction(event -> {
				Platform.runLater(() -> {
					try {

						PlatformImpl.setTaskbarApplication(true);
					} catch (Exception exception) {
						// IllegalAccessError?
						// ignore
					}
				});
				System.err.println("Hide mainstage");
				mainStage.hide();
			});

			menuShowMessage.setOnAction(e -> showMessage());
			menuExit.setOnAction(e -> System.exit(0));
			icon.addMenuItem(menuShowStage);
			icon.addMenuItem(menuHideStage);
			icon.addMenuItem(menuShowMessage);
			icon.addMenuItem(menuExit);
			icon.show();
		}

	}

	private FXTrayIcon icon;

	private void showMessage() {
		icon.showInfoMessage("Check It Out!", "Look Ma, No Taskbar Icon!");
	}

	private void showMessage(String message) {
		icon.showInfoMessage("Message For You!", message);
	}
}