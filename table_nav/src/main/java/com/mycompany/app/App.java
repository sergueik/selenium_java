package com.mycompany.app;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;


import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import org.openqa.selenium.interactions.Actions;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


import java.lang.RuntimeException;


import java.util.concurrent.TimeUnit;


import org.junit.Assert;

import static org.junit.Assert.*;
/*
   import java.io.File;
   import java.io.InputStream;
   import java.io.StringWriter;
   import java.lang.StringBuilder;
   import java.lang.RuntimeException;


   import java.util.concurrent.TimeUnit;
   import java.util.NoSuchElementException;
   import java.util.Set;
   import java.util.Hashtable;

   import org.apache.http.Header;
   import org.apache.http.HttpEntity;
   import org.apache.http.HttpHost;
   import org.apache.http.HttpResponse;
   import org.apache.commons.io.FileUtils;
   import org.openqa.selenium.TakesScreenshot;
   import org.openqa.selenium.OutputType;
   import org.apache.commons.io.FilenameUtils;
   import javax.servlet.ServletException;
   import javax.servlet.http.HttpServletRequest;
   import javax.servlet.http.HttpServletResponse;


   import org.openqa.selenium.interactions.Actions;


   import org.apache.commons.io.FileUtils;


   import org.openqa.selenium.By;
   import org.openqa.selenium.JavascriptExecutor;
   import org.openqa.selenium.Keys;
   import org.openqa.selenium.OutputType;
   import org.openqa.selenium.TakesScreenshot;
   import org.openqa.selenium.WebDriver;
   import org.openqa.selenium.WebElement;
   import org.openqa.selenium.chrome.ChromeDriver;
   import org.openqa.selenium.firefox.FirefoxDriver;
   import org.openqa.selenium.interactions.Action;
   import org.openqa.selenium.interactions.Actions;
   import org.openqa.selenium.JavascriptExecutor;
   import org.openqa.selenium.support.ui.ExpectedConditions;
   import org.openqa.selenium.support.ui.WebDriverWait;

   import    org.openqa.selenium.Dimension;

   import org.openqa.selenium.Platform;
   import org.apache.http.message.BasicHttpEntityEnclosingRequest;

   import org.openqa.selenium.interactions.Actions;
   import org.apache.http.impl.client.DefaultHttpClient;
   import org.json.JSONArray;
   import org.json.JSONException;
   import org.json.JSONObject;

   import org.openqa.selenium.By;
   import org.openqa.selenium.remote.DesiredCapabilities;
   import org.openqa.selenium.chrome.ChromeDriver;
   import org.openqa.selenium.remote.HttpCommandExecutor;
   import org.openqa.selenium.remote.RemoteWebDriver;

   import org.openqa.selenium.firefox.FirefoxDriver;
   // import org.openqa.selenium.firefox.ProfileManager;
   import org.openqa.selenium.firefox.internal.ProfilesIni;
   import org.openqa.selenium.firefox.FirefoxProfile;
   import org.openqa.selenium.OutputType;
   import org.openqa.selenium.support.ui.ExpectedConditions;
   import org.openqa.selenium.support.ui.WebDriverWait;
   import org.openqa.selenium.TakesScreenshot;
   import org.openqa.selenium.WebDriver;


   import java.io.IOException;
   import java.io.UnsupportedEncodingException;
   import java.net.BindException;
   import java.net.MalformedURLException;
   import java.net.URI;
   import java.net.URISyntaxException;
   import java.net.URL;
   import java.nio.charset.Charset;

   import org.junit.Assert;
   import static org.junit.Assert.*;


 */
public class App
{


static WebDriver driver;

public static void main(String[] args) throws InterruptedException,java.io.IOException {

	String base_url =  "https://datatables.net/examples/api/highlight.html";

	// Launch embedded driver
	driver = new FirefoxDriver();

	// Wait For Page To Load
	driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

	// Go to base url
	driver.get(base_url);

	// Maximize Window
	driver.manage().window().maximize();

	String table_id="example";
	WebElement table_element;
	int flexible_wait_interval = 5;
	long wait_polling_interval = 500;
	WebDriverWait wait = new WebDriverWait(driver, flexible_wait_interval );
	wait.pollingEvery(wait_polling_interval,TimeUnit.MILLISECONDS);

	try {
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(table_id)));
	} catch (RuntimeException timeoutException) {
		return;
	}


	table_element =  driver.findElement(By.id(table_id));
	// NOTE: no leading slash in XPath
	List<WebElement> table_row_collection = table_element.findElements(By.xpath("tbody/tr"));
	System.out.println("NUMBER OF ROWS IN THIS TABLE = "+table_row_collection.size());
	int row_num,cell_num;
	row_num=1;
	Actions actions = new Actions(driver);
	String row_role_attribute = "row";
	for(WebElement table_row : table_row_collection)
	{



		// http://junit.sourceforge.net/javadoc/org/junit/Assert.html
		// assertEquals(200, response.getStatusLine().getStatusCode());
		assertTrue(String.format("Unexpected title '%s'", table_row.getAttribute("role")), table_row.getAttribute("role").matches(row_role_attribute) );
		List<WebElement> table_cell_collection=table_row.findElements(By.xpath("td"));
		System.out.println("NUMBER OF COLUMNS="+table_cell_collection.size());
		cell_num=1;
		for(WebElement table_cell : table_cell_collection)
		{
			// Hover over cell

			actions.moveToElement(table_cell).build().perform();
			System.out.println("row # "+row_num+", col # "+cell_num+ "text="+table_cell.getText());
			cell_num++;
		}
		row_num++;
	}

	// Close browser window
	driver.close();

}
}

