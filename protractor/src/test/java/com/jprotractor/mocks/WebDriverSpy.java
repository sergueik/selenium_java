package com.jprotractor.mocks;

import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.logging.Logs;

public class WebDriverSpy implements WebDriver {
	private long pageLoadTimeout = 0;
	private long implicitWait = 0;
	private long scriptTimeout = 0;
	private boolean enchanced;

	public long getPageLoadTimeout() {
		return pageLoadTimeout;
	}

	public long getImplicitTimeout() {
		return implicitWait;
	}

	public long getScriptTimeout() {
		return scriptTimeout;
	}

	@Override
	public void close() {
	}

	@Override
	public WebElement findElement(By arg0) {
		return null;
	}

	@Override
	public List<WebElement> findElements(By arg0) {
		return null;
	}

	@Override
	public void get(String arg0) {

	}

	@Override
	public String getCurrentUrl() {
		return null;
	}

	@Override
	public String getPageSource() {
		return null;
	}

	@Override
	public String getTitle() {
		return null;
	}

	@Override
	public String getWindowHandle() {
		return null;
	}

	@Override
	public Set<String> getWindowHandles() {
		return null;
	}

	@Override
	public Options manage() {
		return new Options() {

			@Override
			public Window window() {
				return null;
			}

			@Override
			public Timeouts timeouts() {
				return new Timeouts() {

					@Override
					public Timeouts setScriptTimeout(long time, TimeUnit unit) {
						scriptTimeout = time;
						enchanced = true;
						return this;
					}

					@Override
					public Timeouts pageLoadTimeout(long time, TimeUnit unit) {
						pageLoadTimeout = time;
						enchanced = true;
						return this;
					}

					@Override
					public Timeouts implicitlyWait(long time, TimeUnit unit) {
						implicitWait = time;
						enchanced = true;
						return this;
					}
				};
			}

			@Override
			public Logs logs() {
				return null;
			}

			@Override
			public ImeHandler ime() {
				return null;
			}

			@Override
			public Set<Cookie> getCookies() {
				return null;
			}

			@Override
			public Cookie getCookieNamed(String name) {
				return null;
			}

			@Override
			public void deleteCookieNamed(String name) {
			}

			@Override
			public void deleteCookie(Cookie cookie) {
			}

			@Override
			public void deleteAllCookies() {
			}

			@Override
			public void addCookie(Cookie cookie) {
			}
		};
	}

	@Override
	public Navigation navigate() {
		return null;
	}

	@Override
	public void quit() {
	}

	@Override
	public TargetLocator switchTo() {
		return null;
	}

	public boolean hasBeenEnchanced() {
		return enchanced;
	}
}
