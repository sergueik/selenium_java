package gui.start;

import gui.ViewHelper;
import gui.child.ChildPlace;
import gui.child.ChildPresenter;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

@SuppressWarnings({ "restriction" })
public class StartPresenter implements Initializable {
	@FXML
	public Label label;

	@FXML
	private void clicked() {
		label.setText(
				((ChildPresenter) ViewHelper.PLACE_CHILD.view.getPresenter()).textfield
						.getText());
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		new ChildPlace().start();
	}
}
