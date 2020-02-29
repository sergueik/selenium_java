package example;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Window;
import javafx.stage.Stage;

public class Controller {
	@SuppressWarnings("unused")
	private Stage stage;

	@SuppressWarnings("restriction")
	@FXML
	private Button buttonSave;

	@SuppressWarnings("restriction")
	@FXML
	private TextField textField;

	private Boolean done;

	public void setMainStage(@SuppressWarnings("restriction") Stage stage) {
		this.stage = stage;
	}

	@SuppressWarnings("restriction")
	public void actionButtonPressed(ActionEvent event) {

		Object source = event.getSource();

		if (!(source instanceof Button)) {
			return;
		}

		Button button = (Button) source;
		Window parentWindow = ((Node) event.getSource()).getScene().getWindow();
		System.err.println("Data: " + textField.getText() + " clicked:" + button.getId());
		stage.hide();
		Platform.exit();

	}

}
