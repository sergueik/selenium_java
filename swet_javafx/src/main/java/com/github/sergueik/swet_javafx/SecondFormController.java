package com.github.sergueik.swet_javafx;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

@SuppressWarnings("restriction")
public class SecondFormController {

	@FXML
	public TextField txtField;

	@FXML
	private Button btnOk;

	public FirstFormController getController() {
		return controller;
	}

	public void setController(FirstFormController controller) {
		this.controller = controller;
	}

	public TextField getTxtField() {
		return txtField;
	}

	private FirstFormController controller;

	@FXML
	void initialize() {
		final FXMLLoader fxmlLoader = new FXMLLoader();
		fxmlLoader.setLocation(getClass().getResource("/sample.fxml"));
		try {
			Parent fxmlEdit = fxmlLoader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}
		controller = fxmlLoader.getController();
		// controller = this.controller;

		btnOk.setOnAction(event -> {
			try {
				Button btn = controller.btn1;
				// https://stackoverflow.com/questions/40248973/javafx-starter-modifying-label-text-on-other-window-from-main-window
				System.err.println(btn.getText());
				btn.setVisible(false);
				btn.setText(txtField.getText());
				btn.resize(100, 20);
				controller.getNewScene();
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
				}
				btn.setVisible(true);
			} catch (NullPointerException e) {

			}
		});
	}

	public void btnOkAction(ActionEvent actionEvent) {
	}
}