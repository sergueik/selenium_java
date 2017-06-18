package com.twistezo;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Properties;

public class SceneController {
	private static final Logger LOG = LogManager.getLogger(SceneController.class);
	private PropertiesManager propertiesManager = PropertiesManager.getInstance();
	private Properties properties = propertiesManager.getProperties();
	@FXML
	private TextField login;
	@FXML
	private TextField password;
	@FXML
	private TextField downloadPath;
	@FXML
	private TextArea stackField;

	@FXML
	private void initialize() {
		Console console = new Console(stackField);
		PrintStream ps = new PrintStream(console, true);
		System.setOut(ps);
		System.setErr(ps);

		afterStart();
	}

	@FXML
	private void handleButtonAction(ActionEvent event) throws IOException {
		Settings settings = Settings.getInstance();
		settings.setLogin(login.getText());
		settings.setPass(password.getText());
		settings.setDownloadFolder(downloadPath.getText());
		updatePropertiesFromUserInput();
		new Thread(() -> {
			BookDownloader bookDownloader = BookDownloader.getInstance();
			bookDownloader.startDownload();
		}).start();
	}

	private void afterStart() {
		if (!propertiesManager.checkPropertiesFileExist()) {
			LOG.info("File config.properties doesn't exists.");
			LOG.info("Create default config.properties file.");
			propertiesManager.createPropFile();
			propertiesManager.getPropertiesFromFile();
			setTextFieldsWithProperties();
		} else if (propertiesManager.checkPropertiesFileExist()) {
			LOG.info("File config.properties exists.");
			propertiesManager.getPropertiesFromFile();
			setTextFieldsWithProperties();
		}
	}

	private void setTextFieldsWithProperties() {
		login.setText(properties.getProperty(PropertiesManager.LOGIN));
		password.setText(properties.getProperty(PropertiesManager.PASSWORD));
		downloadPath
				.setText(properties.getProperty(PropertiesManager.DOWNLOAD_PATH));
	}

	private void updatePropertiesFromUserInput() {
		FileOutputStream out = null;
		try {
			out = new FileOutputStream(PropertiesManager.PROP_FILE_NAME);
			properties.setProperty(PropertiesManager.LOGIN, login.getText());
			properties.setProperty(PropertiesManager.PASSWORD, password.getText());
			properties.setProperty(PropertiesManager.DOWNLOAD_PATH,
					downloadPath.getText());
			properties.store(out, null);
			out.close();
		} catch (IOException e) {
			LOG.error(e.getMessage());
		}

	}
}