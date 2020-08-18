package com.sample;

import java.io.IOException;
import java.net.URL;
import java.time.chrono.Chronology;
import java.time.format.TextStyle;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

/**
 *
 * @author tomo
 */
public class LocalizationInfoTitle extends HBox implements Initializable {
	private LocalizationInfo localizationInfo;
	@FXML
	private Label selectedLocale;
	@FXML
	private Label selectedChronology;

	public LocalizationInfoTitle(LocalizationInfo info) {
		this.localizationInfo = info;
		loadFxml();
	}

	private void loadFxml() {
		URL url = getClass().getResource("/LocalizationInfoTitle.fxml");
		ResourceBundle bundle = ResourceBundle.getBundle("Label",
				localizationInfo.getLocale());
		FXMLLoader loader = new FXMLLoader(url, bundle);
		loader.setRoot(this);
		loader.setController(this);
		try {
			loader.<HBox> load();
		} catch (IOException ex) {
			Logger.getLogger(LocalizationInfoTitle.class.getName()).log(Level.SEVERE,
					null, ex);
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		Locale locale = this.localizationInfo.getLocale();
		this.selectedLocale.setText(String.format("%s(%s)",
				locale.getDisplayCountry(locale), locale.getDisplayLanguage(locale)));
		Chronology chronolory = this.localizationInfo.getChronolory();
		this.selectedChronology
				.setText(chronolory.getDisplayName(TextStyle.FULL, locale));
	}
}
