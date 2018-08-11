package com.github.abhishek8908.plugin;

/*
 * Copyright 2001-2005 The Apache Software Foundation.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import com.github.abhishek8908.driver.ChromeDriver;
import com.github.abhishek8908.driver.DriverSettings;
import com.github.abhishek8908.driver.EdgeDriver;
import com.github.abhishek8908.driver.GeckoDriver;
import com.github.abhishek8908.driver.InternetExplorerDriver;
import com.github.abhishek8908.driver.enums.Drivers;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;

import java.io.IOException;
import java.util.List;

/**
 * Goal which touches a timestamp file.
 *
 * @goal touch
 * @phase process-sources
 */
@Mojo(name = "generateDrivers", defaultPhase = LifecyclePhase.COMPILE)

public class GenerateDriverMojo extends AbstractMojo {

	@Parameter(required = true)
	private List<Driver> drivers;

	@Parameter(required = true)
	private String driverPath;

	public String getDriverPath() {
		return driverPath;
	}

	public void setDriverPath(String driverPath) {
		this.driverPath = driverPath;
	}

	@Parameter(defaultValue = "${project}")
	private MavenProject project;

	public MavenProject getProject() {
		return project;
	}

	public void setProject(MavenProject project) {
		this.project = project;
	}
	// @Parameter
	// private Logger logger;

	public void execute() {
		System.out.println("Driver size =" + drivers.size());
		// logger.setLog(getLog());

		DriverSettings settings = new DriverSettings();
		getLog().info("Get Drivers: " + drivers);

		for (Driver driver : drivers) {
			settings.setVer(driver.getVer());
			settings.setOs(driver.getOs());
			settings.setDriverDir(driverPath);
			if (driver.getName().equalsIgnoreCase(Drivers.CHROMEDRIVER.toString())) {
				try {
					new ChromeDriver(settings).getDriver().setDriverInSystemProperty();
				} catch (IOException e) {
					e.printStackTrace();
				} catch (ConfigurationException e) {
					e.printStackTrace();
				}
			}
			if (driver.getName().equalsIgnoreCase(Drivers.GECKODRIVER.toString())) {
				try {
					new GeckoDriver(settings).getDriver().setDriverInSystemProperty();
				} catch (IOException e) {
					e.printStackTrace();
				} catch (ConfigurationException e) {
					e.printStackTrace();
				}

			}
			if (driver.getName().equalsIgnoreCase(Drivers.EDGEDRIVER.toString())) {
				try {
					new EdgeDriver(settings).getDriver().setDriverInSystemProperty();
				} catch (IOException e) {
					e.printStackTrace();
				} catch (ConfigurationException e) {
					e.printStackTrace();
				}

			}
			if (driver.getName().equalsIgnoreCase(Drivers.IEDRIVER.toString())) {
				try {
					new InternetExplorerDriver(settings).getDriver()
							.setDriverInSystemProperty();
				} catch (IOException e) {
					e.printStackTrace();
				} catch (ConfigurationException e) {
					e.printStackTrace();
				}

			}
		}
	}
}