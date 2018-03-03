package passwordkeeper;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.sql.*;

/**
 *
 * ToDo - Add Apache POI (import / Export data to xls/xlsx)
 * ToDo - Repare addpassw.fxml
 * ToDo - Add password protection (md5)
 * ToDo - Add sync options drive || && yandex
 * ToDo - Create exe file
 */

public class Main extends Application {

	public static final String DBPATH = "";

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
		CheckConnection();
		primaryStage.show();
		primaryStage.getIcons().add(new Image("/ico.png"));

	}

	public void CheckConnection() {
		if (DB.getInstance().getConnection() == null) {
			System.out.println("Connection failure");
			System.exit(1);
		}
	}

	private void SQLdata() {
		SQLCollectionEditable editable = new SQLCollectionEditable();
		editable.printFromSql();
	}

	public static void main(String[] args) {
		launch(args);
	}

}
