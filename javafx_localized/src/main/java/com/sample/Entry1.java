package com.sample;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;

@SuppressWarnings("restriction")
public class Entry1 extends EntryBase {
	@FXML
	private DatePicker datePicker;

	@Override
	public boolean goNext() {
		// TODO do something about localization
		return true;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		this.datePicker.setChronology(this.localizationInfo.getChronolory());
	}

}
