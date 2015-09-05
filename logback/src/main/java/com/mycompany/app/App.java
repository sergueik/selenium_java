package com.mycompany.app;

import java.io.File;
import java.io.InputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;

import java.lang.StringBuilder;

import java.net.BindException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import java.nio.charset.Charset;

import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHttpEntityEnclosingRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.firefox.internal.ProfilesIni;
// import org.openqa.selenium.firefox.ProfileManager;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Platform;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.HttpCommandExecutor;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
public class App
{

private static final Logger slf4jLogger = LoggerFactory.getLogger(App.class);
    /**
     * Print hello in log using log4j log implementation
     * 
     * @param name
     */
    public void sayHello(String name) {
        slf4jLogger.info("Hi, {}", name);
        slf4jLogger.info("Welcome to the HelloWorld example of Log4j using SLF4J");
    }
 
    /**
     * @param args
     */
    public static void main(String[] args) {
slf4jLogger.info("Welcome to the HelloWorld example of Log4j using SLF4J");

    }
}


