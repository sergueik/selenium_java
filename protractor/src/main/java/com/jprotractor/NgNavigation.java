package com.jprotractor;

import java.net.URL;

import org.openqa.selenium.WebDriver.Navigation;
import org.openqa.selenium.WebDriverException;

public class NgNavigation implements Navigation 
{
	@SuppressWarnings("unused")
	private NgWebDriver NgDriver;
	private Navigation Nav;
	public Navigation WrappedNavigation;
	
	public NgNavigation(NgWebDriver ngDriver, Navigation navigation)
	{
		this.NgDriver = ngDriver;
		this.Nav = navigation;
		this.WrappedNavigation = this.Nav;
	}

	
	public void back() {
		this.Nav.forward();
	}

	public void forward() {
		this.Nav.forward();
	}

	public void refresh() {
		this.Nav.refresh();
	}

	public void to(String arg0) {
		if(arg0 == null)
		{
			throw new WebDriverException("URL cannot be null.");
		}
		
		this.Nav.to(arg0.toString());
	}

	public void to(URL arg0) {
		this.Nav.to(arg0.toString());
		
	}
	
	
}
