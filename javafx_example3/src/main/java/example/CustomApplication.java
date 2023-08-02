package example;

import java.io.IOException;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 *
 * origin: https://stackoverflow.com/questions/52382778/how-do-i-add-a-splash-screen-to-a-main-program-in-javafx
 */
public class CustomApplication extends Application {

	@Override
	public void start(Stage stage) throws Exception {
		Splash splash = new Splash();
		splash.show();
		stage.setScene(splash.getSplashScene());
		splash.getSequentialTransition().setOnFinished(e -> {
			Timeline timeline = new Timeline();
			KeyFrame key = new KeyFrame(Duration.millis(800),
					new KeyValue(splash.getSplashScene().getRoot().opacityProperty(), 0));
			timeline.getKeyFrames().add(key);
			timeline.setOnFinished((event) -> {
				try {
					//
					Parent root = FXMLLoader
							.load(getClass().getResource("/helloWorld.fxml"));
					//
					// NOTE: "/splash.fxml"
					// at javafx.fxml.FXMLLoader.constructLoadException
					// Caused by: java.lang.ClassNotFoundException:
					// example.controller.SplashController
					Scene scene = new Scene(root);

					stage.setScene(scene);
				} catch (IOException ex) {
					ex.printStackTrace();
				}
			});
			timeline.play();
		});
		//
		stage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}

}
