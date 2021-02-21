package com.github.sergueik.swet_javafx;

import java.time.LocalDate;

public class SelectionDTO {

	// private static final SimpleDateFormat dateFormat = new
	// SimpleDateFormat("yyyy-MM-dd HH:mm");

	private Long id;
	private boolean longRun;
	private String quickRange;
	private String username;
	private String password;
	private LocalDate tillDate;
	private LocalDate fromDate;

	public boolean isLongRun() {
		return longRun;
	}

	public void setLongRun(boolean value) {
		longRun = value;
	}

	public LocalDate getTillDate() {
		return tillDate;
	}

	public void setTillDate(LocalDate value) {
		tillDate = value;
	}

	public String getQuickRange() {
		return quickRange;
	}

	public void setQuickRange(String value) {
		quickRange = value;
	}

	public LocalDate getFromDate() {
		return fromDate;
	}

	public void setFromDate(LocalDate value) {
		fromDate = value;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String value) {
		username = value;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String value) {
		password = value;
	}

}

