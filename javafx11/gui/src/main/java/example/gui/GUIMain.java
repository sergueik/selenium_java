package example.gui;

import example.logic.CoolLogic;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class GUIMain extends Application {

	public static void main(String[] args) {
		Application.launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		primaryStage.setTitle("JavaFX on JDK 11");

		Label label = new Label(
				"The Cool Logic Output is: " + CoolLogic.doSomeCoolStuff());
		Scene scene = new Scene(label, 400, 200);

		primaryStage.setScene(scene);
		primaryStage.show();
	}

}