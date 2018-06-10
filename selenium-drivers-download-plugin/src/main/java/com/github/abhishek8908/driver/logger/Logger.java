package com.github.abhishek8908.driver.logger;

import org.apache.maven.plugin.logging.Log;
import org.apache.maven.plugin.logging.SystemStreamLog;

public class Logger {

	public Log getLog() {
		if (this.log == null) {
			this.log = new SystemStreamLog();
		}

		return this.log;
	}

	public void setLog(Log log) {
		this.log = log;
	}

	private Log log;

}
