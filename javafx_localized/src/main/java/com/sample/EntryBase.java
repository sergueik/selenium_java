package com.sample;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.GridPane;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@SuppressWarnings("restriction")
public abstract class EntryBase extends GridPane implements Initializable {
	protected LocalizationInfo localizationInfo;
	private static final Logger log = LogManager.getLogger(EntryBase.class);

	protected void loadFxml(LocalizationInfo localizationInfo, String fxmlPath) {
		this.localizationInfo = localizationInfo;
		URL url = getClass().getResource(fxmlPath);
		ResourceBundle bundle = ResourceBundle.getBundle("Label", localizationInfo.getLocale());

		log.info("Loading resource bundle: " + bundle.getBaseBundleName() + " "
				+ bundle.getLocale().getDisplayLanguage());
		FXMLLoader loader = new FXMLLoader(url, bundle);
		loader.setRoot(this);
		loader.setController(this);
		try {
			loader.<GridPane>load();
		} catch (IOException e) {
			// original:
			// Logger.getLogger(LocalizationInfoTitle.class.getName()).log(Level.SEVERE,
			// null, e);
			log.info("Exception (ignored) : " + e.toString());
		}
	}

	public abstract boolean goNext();
}
