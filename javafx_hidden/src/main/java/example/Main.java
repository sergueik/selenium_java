package example;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.application.Platform;

@SuppressWarnings({ "restriction" })
public class Main extends Application {

	@SuppressWarnings("restriction")
	@Override
	public void start(Stage stage) throws Exception {
		FXMLLoader fxmlLoader = new FXMLLoader();
		Platform.setImplicitExit(false);
		fxmlLoader.setLocation(getClass().getResource("/sample.fxml"));
		Parent root = fxmlLoader.load();
		// Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
		stage.setTitle("Calculator");
		stage.setScene(new Scene(root, 529, 414));
		Controller controller = (Controller) fxmlLoader.getController();
		System.err.println("Setting main stage " + stage.toString());
		controller.setMainStage(stage);
		/*
		 stage.hide(); 
		 try { 
		 	System.err.println("Sleep"); 
		 	Thread.sleep(5000); 
	 	 } catch (InterruptedException e) {
	 	 } 
	 	 System.err.println("Done");
		 */
		stage.show();
		// does not wait

		// Platform.exit();
	}

	public static void main(String[] args) {
		launch(args);
	}
}
