package com.sample;

import java.time.chrono.Chronology;
import java.util.Locale;

public class LocalizationInfo {
	private Locale locale;
	private Chronology chronolory;

	public Locale getLocale() {
		return locale;
	}

	public void setLocale(Locale locale) {
		this.locale = locale;
	}

	public Chronology getChronolory() {
		return chronolory;
	}

	public void setChronolory(Chronology chronolory) {
		this.chronolory = chronolory;
	}
}
