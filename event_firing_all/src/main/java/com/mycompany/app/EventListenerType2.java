package com.mycompany.app;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.events.AbstractWebDriverEventListener;

public class EventListenerType2 extends 
AbstractWebDriverEventListener {

	@Override
	public void onException(Throwable ex, WebDriver arg1) {
		System.err.println(
				"There is an exception in the script, please find the below error description");
		ex.printStackTrace();
	}

}
