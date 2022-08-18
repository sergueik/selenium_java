package example;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import java.util.function.Function;
import java.util.stream.Collectors;

import org.junit.jupiter.api.extension.AfterAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ExtensionContext.Store;
import org.junit.jupiter.api.extension.TestWatcher;

import org.openqa.selenium.WebDriver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestResultLoggerExtension
		implements TestWatcher, AfterAllCallback /*, BeforeAllCallback */ {
	private Store store;
	private String arg;
	private Integer value = -1;
	private WebDriver driver = null;
	private final Utils utils = Utils.getInstance();
	private final ExtensionContext.Namespace namespace = ExtensionContext.Namespace
			.create(ExtendedTest.class);
	/* ExtensionContext.Namespace.GLOBAL */ ;

	private static final Logger logger = LoggerFactory
			.getLogger(TestResultLoggerExtension.class);

	private List<TestResultStatus> results = new ArrayList<>();

	private enum TestResultStatus {
		SUCCESSFUL, ABORTED, FAILED, DISABLED;
	}

	@Override
	public void testDisabled(ExtensionContext context, Optional<String> reason) {
		logger.info("Test Disabled for test {}: with reason :- {}",
				context.getDisplayName(), reason.orElse("No reason"));

		results.add(TestResultStatus.DISABLED);
	}

	@Override
	public void testSuccessful(ExtensionContext context) {
		logger.info("Test Successful for test {}: ", context.getDisplayName());

		results.add(TestResultStatus.SUCCESSFUL);
	}

	@Override
	public void testAborted(ExtensionContext context, Throwable cause) {
		logger.info("Test Aborted for test {}: ", context.getDisplayName());

		results.add(TestResultStatus.ABORTED);
	}

	// https://github.com/eugenp/tutorials/blob/master/spring-5/src/main/java/com/baeldung/jupiter/SpringExtension.java
	// https://www.programcreek.com/java-api-examples/?api=org.junit.jupiter.api.extension.ExtensionContext.Store
	@Override
	public void testFailed(ExtensionContext context, Throwable cause) {

		store = context.getStore(namespace);
		arg = store.getOrDefault("arg1", String.class, "");
		logger.info("arg is " + arg);

		// way to pass additional arguments
		Object testClass = context.getRequiredTestInstance();
		String methodName = "getValue";
		try {
			Method getValue = testClass.getClass().getMethod(methodName);
			getValue.setAccessible(true);
			value = (int) getValue.invoke(testClass);
		} catch (InvocationTargetException | NoSuchMethodException
				| SecurityException | IllegalAccessException
				| IllegalArgumentException e) {
			logger.info("failed to invoke method");
		}
		logger.info("value is " + value);

		// https://www.baeldung.com/java-invoke-static-method-reflection
		methodName = "getDriver";
		try {
			Method getDriver = testClass.getClass().getMethod(methodName);
			getDriver.setAccessible(true);
			driver = (WebDriver) getDriver.invoke(null);
			logger.info("driver url is " + driver.getCurrentUrl());
			// NOTE:
			// when driver is destroyed in @AfterEach, the extension os failed
			// WARNING: Failed to invoke TestWatcher
			// [example.TestResultLoggerExtension]
			// for method [example.ExtendedTest#test4()] with display name [test4()]
			// org.openqa.selenium.WebDriverException: java.net.ConnectException:
			// Failed
			// to connect to localhost/127.0.0.1:47779
		} catch (InvocationTargetException | NoSuchMethodException
				| SecurityException | IllegalAccessException
				| IllegalArgumentException e) {
			logger.info("failed to invoke static method");
		}

		logger.info("Test Failed for test {}: arg {}", context.getDisplayName(),
				arg);

		if (driver != null)
			utils.takescreenshot(driver);

		results.add(TestResultStatus.FAILED);
	}

	@Override
	public void afterAll(ExtensionContext context) throws Exception {
		Map<TestResultStatus, Long> summary = results.stream().collect(
				Collectors.groupingBy(Function.identity(), Collectors.counting()));

		logger.info("Test result summary for {} {}", context.getDisplayName(),
				summary.toString());
	}

	// currently not used
	// https://stackoverflow.com/questions/64523276/in-junit-5-how-can-i-call-a-test-class-method-from-an-extension
	/*
	@Override
	public void beforeAll(ExtensionContext context) throws Exception {
		logger
				.info("Logger Extension class processes context " + context.getRoot());
		if (context.getRoot().getStore(namespace).get("arg1",
				String.class) == null) {
			context.getRoot().getStore(namespace).put("arg1", "val1");
		} else {
			logger.info("Read cached data!");
		}
	}
	*/
}