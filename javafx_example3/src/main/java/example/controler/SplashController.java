package example.controler;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

/**
 *
 * origin: https://stackoverflow.com/questions/52382778/how-do-i-add-a-splash-screen-to-a-main-program-in-javafx
 *
 */
public class SplashController implements Initializable {

	@FXML
	private Label label;

	@FXML
	private void handleButtonAction(ActionEvent event) {
		System.out.println("You clicked me!");
		label.setText("Hello World!");
	}

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		// TODO
	}

}
