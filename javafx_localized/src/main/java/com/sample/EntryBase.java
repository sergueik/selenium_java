package com.sample;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.GridPane;

public abstract class EntryBase extends GridPane implements Initializable {
	protected LocalizationInfo localizationInfo;

	protected void loadFxml(LocalizationInfo localizationInfo, String fxmlPath) {
		this.localizationInfo = localizationInfo;
		URL url = getClass().getResource(fxmlPath);
		ResourceBundle bundle = ResourceBundle.getBundle("Label",
				localizationInfo.getLocale());
		FXMLLoader loader = new FXMLLoader(url, bundle);
		loader.setRoot(this);
		loader.setController(this);
		try {
			loader.<GridPane> load();
		} catch (IOException ex) {
			Logger.getLogger(LocalizationInfoTitle.class.getName()).log(Level.SEVERE,
					null, ex);
		}
	}

	public abstract boolean goNext();
}
