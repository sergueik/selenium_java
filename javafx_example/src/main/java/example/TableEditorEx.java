package example;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.scene.control.*;

public class TableEditorEx extends Application {
	@SuppressWarnings("restriction")
	@Override
	public void start(Stage primaryStage) throws Exception {

		FXMLLoader fxmlLoader = new FXMLLoader();
		fxmlLoader.setLocation(getClass().getResource("/main.fxml"));
		Parent fxmlMain = fxmlLoader.load();
		Controller controller = fxmlLoader.getController();
		controller.setMainStage(primaryStage);
		primaryStage.setTitle("Password Keeper for WP");
		primaryStage.setScene(new Scene(fxmlMain, 700, 600));
		// primaryStage.setResizable(false);
		// CheckConnection();
		primaryStage.show();
		primaryStage.getIcons().add(new Image("/ico.png"));

	}

	public static void main(String[] args) {
		launch(args);
	}

}
