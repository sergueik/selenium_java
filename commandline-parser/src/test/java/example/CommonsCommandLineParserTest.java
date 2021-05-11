package example;

/**
 * Copyright Copyright 2020,2021 Serguei Kouzmine
 */

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.io.EOFException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.file.Paths;
import java.security.Permission;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import junit.framework.TestCase;

import example.CommonsCommandLineParser;

/**
 * Unit Tests for Apache Commons CLI CommandLineParser
 * see https://stackoverflow.com/questions/309396/java-how-to-test-methods-that-call-system-exit
 * 
 * @author: Serguei Kouzmine (kouzmine_serguei@yahoo.com)
 */

@SuppressWarnings("deprecation")
public class CommonsCommandLineParserTest extends TestCase {

	protected static class ExitException extends SecurityException {
		public final int status;

		public ExitException(int status) {
			super("intercepted exit");
			this.status = status;
		}
	}

	private static class NoExitSecurityManager extends SecurityManager {
		@Override
		public void checkPermission(Permission perm) {
		}

		@Override
		public void checkPermission(Permission perm, Object context) {
		}

		@Override
		public void checkExit(int status) {
			super.checkExit(status);
			throw new ExitException(status);
		}
	}

	private CommonsCommandLineParser sut;

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		System.setSecurityManager(new NoExitSecurityManager());
		sut = new CommonsCommandLineParser();
	}

	@Override
	protected void tearDown() throws Exception {
		System.setSecurityManager(null);
		super.tearDown();
	}

	@Test
	public void test1() throws Exception {
		System.out.println("test passes");
	}

	@Test
	public void test2() throws Exception {
		final int setStatus = 43;
		try {
			sut.help(setStatus);
		} catch (ExitException e) {
			final int status = e.status;
			assertThat(String.format("Unexpected Exit status", status), status,
					is(setStatus));
		}
	}

	@Test
	public void test3() throws Exception {
		try {
			sut.run("-d", "data here");
		} catch (ExitException e) {
			final int status = e.status;
			assertThat(String.format("Unexpected Exit status", status), status,
					is(0));
		}
	}

	@Test
	public void test4() throws Exception {
		try {
			sut.run("-a", "wrong option");
		} catch (ExitException e) {
			final int status = e.status;
			assertThat(String.format("Unexpected Exit status", status), status,
					is(CommonsCommandLineParser.INVALID_OPTION));
		}
	}

}
