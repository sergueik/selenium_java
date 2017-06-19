package org.app;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.util.Properties;

/**
 * @author twistezo (31.03.2017)
 */
class PropertiesManager {
    private static final Logger LOG = LogManager.getLogger(PropertiesManager.class);
    private static PropertiesManager instance = null;
    private Properties properties = new Properties();
    private OutputStream outputStream = null;
    private InputStream inputStream = null;
    static final String PROP_FILE_NAME = "config.properties";
    static final String LOGIN = "login";
    static final String PASSWORD = "password";
    static final String DOWNLOAD_PATH = "downloadPath";

    private PropertiesManager() { }

    static PropertiesManager getInstance() {
        if(instance == null) {
            instance = new PropertiesManager();
        }
        return instance;
    }

    void createPropFile() {
        try {
            outputStream = new FileOutputStream(PROP_FILE_NAME);
            properties.setProperty(LOGIN, "");
            properties.setProperty(PASSWORD, "");
            properties.setProperty(DOWNLOAD_PATH, "C:\\Users\\...\\Desktop\\Java books");
            properties.store(outputStream, null);
        } catch (IOException e) {
            LOG.error(e.getMessage());
        } finally {
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    LOG.error(e.getMessage());
                }
            }
        }
    }

    boolean checkPropertiesFileExist() {
        File file = new File(PROP_FILE_NAME);
        return file.exists();
    }

    void getPropertiesFromFile() {
        try {
            inputStream = new FileInputStream(PROP_FILE_NAME);
            properties.load(inputStream);
            inputStream.close();
        } catch (IOException e) {
            LOG.error(e.getMessage());
        }
        Settings settings = Settings.getInstance();
        settings.setLogin(properties.getProperty(LOGIN));
        settings.setPass(properties.getProperty(PASSWORD));
        settings.setDownloadFolder(properties.getProperty(DOWNLOAD_PATH));
        LOG.info("Getting data from config.properties file:");
        LOG.info("Login: " + properties.getProperty(LOGIN));
        LOG.info("Password: " + properties.getProperty(PASSWORD));
        LOG.info("DownloadPath: " + properties.getProperty(DOWNLOAD_PATH));
    }

    Properties getProperties() {
        return this.properties;
    }
}
