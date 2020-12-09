package application;

import java.io.IOException;

// import application.model.Person;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

public class DataForm extends Application {
	private Stage primaryStage;
	private BorderPane rootLayout;

	// private ObservableList<Person> personData = FXCollections.observableArrayList();
	// private ObservableList<Person> movieData =
	// FXCollections.observableArrayList();

	public DataForm() {

	}

	@Override
	public void start(Stage primaryStage) {
		try {
			this.primaryStage = primaryStage;
			this.primaryStage.setTitle("Pubs Datenbank GUI");

			initRootLayout();
			showUserDataView();
		} catch (IOException e) {
			System.out.println("fxml-Datei konnte nicht geladen werden!");
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}

	/**
	 * Initialisiert das root layout
	 */
	public void initRootLayout() throws IOException {

		// lade root layout aus xml Datei
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(DataForm.class.getResource("/view/RootLayout.fxml"));
		rootLayout = (BorderPane) loader.load();

		// zeige das scene mit dem root layout
		primaryStage.setScene(new Scene(rootLayout));
		primaryStage.show();

	}

	public void showUserDataView() throws IOException {

		// lade userdataview
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(DataForm.class.getResource("/view/UserDataView.fxml"));
		AnchorPane userDataView = (AnchorPane) loader.load();

		// setzte das userdataview in die Mitte des root layout
		rootLayout.setCenter(userDataView);
	}

	public Stage getPrimaryStage() {
		return primaryStage;
	}
}
