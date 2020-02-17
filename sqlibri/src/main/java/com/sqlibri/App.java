package com.sqlibri;

import com.sqlibri.presenter.AppPresenter;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.net.URL;

/** Main class - launches the application */
public class App extends Application {

	// Application Icon
	@SuppressWarnings("restriction")
	private final Image ROOT_ICON = new Image(this.getClass().getClassLoader()
			.getResource("image/SQLibri-icon.png").toExternalForm());

	// Application title
	private final String TITLE = "SQLibri";

	// Main Layout
	private final URL MAIN_LAYOUT = this.getClass().getClassLoader()
			.getResource("layout/main-layout.fxml");

	@Override
	public void start(Stage primaryStage) {
		try {
			FXMLLoader loader = new FXMLLoader(MAIN_LAYOUT);
			Parent root = loader.load();

			Scene scene = new Scene(root);
			primaryStage.setScene(scene);

			AppPresenter controller = loader.getController();
			controller.init(primaryStage);

			primaryStage.setTitle(TITLE);
			primaryStage.getIcons().add(ROOT_ICON);
			primaryStage.setMaximized(true);
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}
