package simplefxbg;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

@SuppressWarnings("restriction")
public class SimpleFXBG extends Application {
	private static final String layout = "/SimpleFXBG.fxml";

	@Override
	public void start(Stage stage) throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource(layout));

		Scene scene = new Scene(root);

		stage.setScene(scene);
		stage.setTitle("Simple FX BG");
		stage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}
