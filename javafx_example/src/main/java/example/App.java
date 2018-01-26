package example;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;

@SuppressWarnings("restriction")
public class App extends Application {

	@SuppressWarnings("restriction")
	@Override
	public void start(Stage stage) {
		Button btn = new Button(">> Click <<");
		btn.setOnAction(e -> System.out.println("Hello JavaFX 8"));
		StackPane root = new StackPane();
		root.getChildren().add(btn);
		stage.setScene(new Scene(root));
		stage.setWidth(300);
		stage.setHeight(300);
		stage.setTitle("JavaFX 8 app");
		stage.show();
	}

}
