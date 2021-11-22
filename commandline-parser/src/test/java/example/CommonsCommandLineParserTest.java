package example;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.security.Permission;

import org.junit.Test;

import junit.framework.TestCase;

/**
 * Unit Tests for Apache Commons CLI CommandLineParser
 * see https://stackoverflow.com/questions/309396/java-how-to-test-methods-that-call-system-exit
 * https://gist.github.com/nickname55/880addec70a8303b2359680376d5d066
 * @author: Serguei Kouzmine (kouzmine_serguei@yahoo.com)
 */

@SuppressWarnings("deprecation")
public class CommonsCommandLineParserTest extends TestCase {

	private CommonsCommandLineParser sut;

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

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		System.setSecurityManager(new NoExitSecurityManager());
		sut = new CommonsCommandLineParser();
		sut.saveFlagValue("d", "data");
		sut.saveFlagValue("o", "operation");
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
			sut.run("-d", "data (required) here", "-o", "operation (required) here");
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

	@Test
	// missing required option
	public void test5() throws Exception {
		try {
			sut.run("-d", "data (required) here");
		} catch (ExitException e) {
			final int status = e.status;
			assertThat(String.format("Unexpected Exit status %d", status), status,
					is(CommonsCommandLineParser.INVALID_OPTION));
		}
	}

	@Test
	// handling url
	public void test6() throws Exception {
		try {
			String data = "http://www.google.com";
			sut.run("-d", data, "-o", "operation");
			String value = sut.getFlagValue("d");
			assertThat(String.format("Unexpected value %s", value), value, is(data));
		} catch (ExitException e) {
			final int status = e.status;
			assertThat(String.format("Unexpected Exit status", status), status,
					is(0));
		}
	}

	@Test
	// handling long option names
	// NOTE: the test has to catch exitexception, even there is no real exception
	// TODO: the option is not working
	public void test7() throws Exception {
		try {
			sut.run("--data", "data", "--o", "operation");
			String value = sut.getFlagValue("d");
			assertThat(String.format("Unexpected value %s", value), value,
					is("data"));
		} catch (ExitException e) {
			final int status = e.status;
			assertThat(String.format("Unexpected Exit status %d", status), status,
					is(42));
		}
	}

	@Test
	// handling long option names
	// NOTE: the test has to catch exitexception, even there is no real exception
	public void test8() throws Exception {
		try {
			sut.run("--data", "data", "-d", "different data");
			String value = sut.getFlagValue("d");
			assertThat(String.format("Unexpected value %s", value), value,
					is("data"));
		} catch (ExitException e) {
			final int status = e.status;
			assertThat(String.format("Unexpected Exit status %d", status), status,
					is(42));
		}
	}
}
