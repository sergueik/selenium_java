package example;

import example.messaging.CDPClient;
import example.messaging.MessageBuilder;
import example.messaging.ServiceWorker;

import example.utils.UIUtils;
import example.utils.Utils;

import org.apache.commons.io.FileUtils;
import org.json.JSONObject;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.openqa.selenium.By;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriverService;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.*;

public class BaseTest {
	protected WebDriver driver;
	protected String wsURL;
	protected Utils utils;
	protected UIUtils uiUtils;
	protected CDPClient CDPClient;
	protected int id;
	private boolean debug = false;
	private boolean headless = false;

	public void setDebug(boolean value) {
		this.debug = value;
	}

	public void setHeadless(boolean value) {
		this.headless = value;
	}

	protected ChromeDriverService chromeDriverService;

	@Before
	public void beforeTest() throws IOException {
		this.utils = Utils.getInstance();
		this.uiUtils = UIUtils.getInstance();

		this.driver = utils.launchBrowser(headless);
		this.wsURL = utils.getWebSocketDebuggerUrl();
		this.CDPClient = new CDPClient(wsURL);
		this.id = Utils.getInstance().getDynamicID();

	}

	@After
	public void afterTest() {
		if (!Objects.isNull(CDPClient))
			CDPClient.disconnect();
		utils.stopChrome();
		if (!Objects.isNull(chromeDriverService))
			chromeDriverService.stop();
	}

}
