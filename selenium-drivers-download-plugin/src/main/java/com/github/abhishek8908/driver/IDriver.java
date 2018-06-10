package com.github.abhishek8908.driver;

import org.apache.commons.configuration.ConfigurationException;

import java.io.IOException;

public interface IDriver {

    IDriver getDriver() throws IOException, ConfigurationException;

    void setDriverInSystemProperty();

}

