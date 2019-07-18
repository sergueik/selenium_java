package example;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

@SuppressWarnings("restriction")
public class SimpleFXBG extends Application {
	@Override
	public void start(Stage stage) throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource("/SimpleFXBG.fxml"));

		Scene scene = new Scene(root);

		stage.setScene(scene);
		stage.setTitle("Simple FX BG");
		stage.show();
	} // public void start(...) throws Exception

	public static void main(String[] args) {
		launch(args);
	} // public static void main(...)
} // public class SimpleFXBG extends Application
