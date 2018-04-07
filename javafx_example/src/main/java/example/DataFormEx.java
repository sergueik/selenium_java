package example;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Category;

// import application.model.Person;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

public class DataFormEx extends Application {
	@SuppressWarnings("deprecation")
	static final Category logger = Category.getInstance(DataFormEx.class);

	private FXMLLoader loader = new FXMLLoader();
	private Stage primaryStage;
	private BorderPane rootLayout;
	private Scene callerScene;
	private String title = "Element Data";
	private Map<String, Object> inputs = new HashMap<>();
	private Map<String, String> inputData = new HashMap<>();

	public DataFormEx() {

	}

	public DataFormEx(Scene callerScene) {
		this.callerScene = callerScene;
	}

	@SuppressWarnings("restriction")
	ToggleGroup group;

	@Override
	public void start(Stage primaryStage) {
		try {
			this.primaryStage = primaryStage;
			this.primaryStage.setTitle(title);
			initRootLayout();
			showUserDataView();
		} catch (IOException e) {
			System.out.println("Exception: " + e.toString());
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}

	public void init() {
		if (callerScene != null) {
			try {
				inputs = (Map<String, Object>) callerScene.getUserData();
				inputData = (Map<String, String>) inputs.get("inputs");
				logger.info("Loaded " + inputData.toString());
			} catch (ClassCastException e) {
				logger.info("Exception (ignored) " + e.toString());
			} catch (Exception e) {
				logger.info("Exception (rethrown) " + e.toString());
				throw e;
			}
		} else {
			inputData = new HashMap<>();
			inputData.put("dummy", "42");
			inputData.put("title", "Element details");
		}
		if (inputData.keySet().size() == 0) {
			throw new IllegalArgumentException("You must provide data");
		}
		logger.info("Loaded " + inputData);

	}

	public void initRootLayout() throws IOException {

		loader.setLocation(getClass().getResource("/view/RootLayout.fxml"));
		rootLayout = (BorderPane) loader.load();
		primaryStage.setScene(new Scene(rootLayout));
		primaryStage.getScene().setUserData(inputs);
		primaryStage.show();

	}

	@SuppressWarnings("restriction")
	public void showUserDataView() throws IOException {
		loader = new FXMLLoader();
		// Exception: javafx.fxml.LoadException: Root value already specified.
		loader.setLocation(getClass().getResource("/view/UserDataView.fxml"));
		rootLayout.setCenter((AnchorPane) loader.load());

		DataFormControllerEx controller = (DataFormControllerEx) loader
				.getController();
		logger.info("Setting main stage " + primaryStage.toString());
		controller.setMainStage(primaryStage); // or what you want to do
		Map<String, String> resultData = new HashMap<>();
		resultData.put("field1", "value1");
		resultData.put("dummy", "42");
		resultData.put("title", "placeholder");
		Map<String, Map> results = new HashMap<>();
		results.put("results", resultData); // TODO: JSON

		logger.info("Set data for Controller to update " + resultData.toString());
		primaryStage.getScene().setUserData(results);
		controller.setInputData(inputData);
	}

	public Stage getPrimaryStage() {
		return primaryStage;
	}

	public void setInputData(Map<String, String> inputData) {
		this.inputData = inputData;
	}

	public Map<String, String> getInputData() {
		return this.inputData;
	}
}
