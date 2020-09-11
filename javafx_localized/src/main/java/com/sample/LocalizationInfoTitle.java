package com.sample;

import java.io.IOException;
import java.net.URL;
import java.time.chrono.Chronology;
import java.time.format.TextStyle;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@SuppressWarnings("restriction")
public class LocalizationInfoTitle extends HBox implements Initializable {
	private LocalizationInfo localizationInfo;
	@FXML
	private Label selectedLocale;
	@FXML
	private Label selectedChronology;
	private final URL url = getClass().getResource("/LocalizationInfoTitle.fxml");
	private static final Logger log = LogManager.getLogger(LocalizationInfoTitle.class);

	public LocalizationInfoTitle(LocalizationInfo data) {
		localizationInfo = data;
		loadFxml();
	}

	private void loadFxml() {
		ResourceBundle bundle = ResourceBundle.getBundle("Label", localizationInfo.getLocale());
		FXMLLoader loader = new FXMLLoader(url, bundle);
		loader.setRoot(this);
		loader.setController(this);
		try {
			loader.<HBox>load();
		} catch (IOException e) {
			// Logger.getLogger(LocalizationInfoTitle.class.getName()).log(Level.SEVERE,
			// null, e);
			log.info("Exception (ignored) : " + e.toString());
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		Locale locale = this.localizationInfo.getLocale();
		selectedLocale
				.setText(String.format("%s(%s)", locale.getDisplayCountry(locale), locale.getDisplayLanguage(locale)));
		Chronology chronolory = localizationInfo.getChronolory();
		selectedChronology.setText(chronolory.getDisplayName(TextStyle.FULL, locale));
	}
}
