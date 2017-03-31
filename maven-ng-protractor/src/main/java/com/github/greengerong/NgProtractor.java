package com.github.greengerong;

import org.apache.commons.io.*;
import org.apache.commons.lang.StringUtils;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.logging.Log;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import java.io.File;
import java.io.IOException;

import static com.google.common.base.Preconditions.checkNotNull;

@Mojo(name = "run", defaultPhase = LifecyclePhase.TEST)
public class NgProtractor extends AbstractMojo {

	@Parameter(property = "protractor", defaultValue = "protractor")
	private String protractor;

	@Parameter(property = "configFile", defaultValue = "${basedir}/protractor.conf.js", required = true)
	private File configFile;

	@Parameter(required = false)
	private boolean debug;

	@Parameter(required = false)
	private boolean debugBrk;

	@Parameter(property = "skipTests", required = false, defaultValue = "false")
	private Boolean skipTests;

	@Parameter(property = "skipProtractor", required = false, defaultValue = "false")
	private Boolean skipProtractor;

	@Parameter(property = "arguments", required = false)
	private String arguments;

	@Parameter(property = "ignoreFailed", required = false)
	private boolean ignoreFailed;

	@Parameter(property = "beforeRunning", required = false)
	private String beforeRunning;

	public void execute() throws MojoExecutionException {
		final Log log = getLog();

		log.info(String.format("protractor:%s", protractor));
		log.info(String.format("configFile:%s", configFile));

		if (StringUtils.isNotBlank(beforeRunning)) {
			execBeforeRunning();
		}

		if (skipProtractor || skipTests) {
			log.info("Skipping protractor test.");
			return;
		}
		try {
			checkNotNull(protractor, "Protractor should not be empty.");
			checkFileExists(configFile, "Protractor should be exists.");

			new ProtractorService(ignoreFailed, log).exec(
					new Command(protractor, configFile, debug, debugBrk, arguments));
		} catch (Exception e) {
			throw new MojoExecutionException(
					"There were exceptions when run protractor test.", e);
		}
	}

	private void execBeforeRunning() {
		final Log log = getLog();

		try {
			log.info(String.format("execute before running: %s", beforeRunning));
			final ProcessBuilder processBuilder = OSUtils.isWindows()
					? new ProcessBuilder("cmd.exe", "/C", beforeRunning)
					: new ProcessBuilder(beforeRunning);

			final Process process = processBuilder.start();
			final String beforeRunningInfo = IOUtils
					.toString(process.getInputStream(), "UTF-8");
			log.info(beforeRunningInfo);
		} catch (IOException e) {
			log.warn("execute before running script error", e);
		}
	}

	private File checkFileExists(File configFile, String message) {
		if (!configFile.exists()) {
			throw new RuntimeException(message);
		}
		return configFile;
	}
}
