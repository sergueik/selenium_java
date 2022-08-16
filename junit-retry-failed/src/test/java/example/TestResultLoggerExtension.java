package example;

import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.AfterAllCallback;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ExtensionContext.Store;
import org.junit.jupiter.api.extension.TestWatcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestResultLoggerExtension
		implements TestWatcher, AfterAllCallback, BeforeAllCallback {

	private final ExtensionContext.Namespace namespace = ExtensionContext.Namespace
			.create(ExtendedTest.class);
	/* ExtensionContext.Namespace.GLOBAL */ ;

	private static final Logger LOG = LoggerFactory
			.getLogger(TestResultLoggerExtension.class);

	private List<TestResultStatus> results = new ArrayList<>();

	private enum TestResultStatus {
		SUCCESSFUL, ABORTED, FAILED, DISABLED;
	}

	@Override
	public void testDisabled(ExtensionContext context, Optional<String> reason) {
		LOG.info("Test Disabled for test {}: with reason :- {}",
				context.getDisplayName(), reason.orElse("No reason"));

		results.add(TestResultStatus.DISABLED);
	}

	@Override
	public void testSuccessful(ExtensionContext context) {
		LOG.info("Test Successful for test {}: ", context.getDisplayName());

		results.add(TestResultStatus.SUCCESSFUL);
	}

	@Override
	public void testAborted(ExtensionContext context, Throwable cause) {
		LOG.info("Test Aborted for test {}: ", context.getDisplayName());

		results.add(TestResultStatus.ABORTED);
	}

	private Store store;
	private int cnt;
	private ExtendedTest test;
	private Integer value = -1;

	// https://github.com/eugenp/tutorials/blob/master/spring-5/src/main/java/com/baeldung/jupiter/SpringExtension.java
	@Override
	public void testFailed(ExtensionContext context, Throwable cause) {

		Object testClass = context.getRequiredTestInstance();
		String methodName = "getValue";
		try {
			Method takeScreenshot = testClass.getClass().getMethod(methodName);
			takeScreenshot.setAccessible(true);
			value = (int) takeScreenshot.invoke(testClass);
		} catch (InvocationTargetException | NoSuchMethodException
				| SecurityException | IllegalAccessException
				| IllegalArgumentException e) {
			LOG.info("failed to invoke method");
		}
		LOG.info("value is " + value);
		// https://www.programcreek.com/java-api-examples/?api=org.junit.jupiter.api.extension.ExtensionContext.Store
		Optional<AnnotatedElement> optionalAnnotatedElement = context.getElement();
		if (optionalAnnotatedElement.isPresent()) {

			AnnotatedElement element = optionalAnnotatedElement.get();
			store = context.getStore(namespace);
			cnt = (int) store.getOrDefault("arg", Integer.TYPE, -1);
			test = store.getOrDefault(ExtendedTest.class, ExtendedTest.class, null);
			LOG.info("Test (1) is " + ((test != null) ? test.hashCode() : "null"));
			LOG.info("cnt (1) is " + cnt);
		}

		store = context.getRoot().getStore(namespace);
		System.err.println(store.toString());
		// NOTE: NPE if store.get("arg")
		cnt = (int) store.getOrDefault("arg", Integer.TYPE, -1);
		Test test = store.getOrDefault(Test.class, Test.class, null);
		LOG.info("Test is " + ((test != null) ? test.hashCode() : "null"));
		// int cnt = (int) store.get("arg");
		LOG.info("Test Failed for test {}: cnt {}", context.getDisplayName(), cnt);
		// NOTE: when absent logback.xml, logging may get reduced
		// System.err.println("**************");
		results.add(TestResultStatus.FAILED);
	}

	@Override
	public void afterAll(ExtensionContext context) throws Exception {
		Map<TestResultStatus, Long> summary = results.stream().collect(
				Collectors.groupingBy(Function.identity(), Collectors.counting()));

		LOG.info("Test result summary for {} {}", context.getDisplayName(),
				summary.toString());
	}

	@Override
	public void beforeAll(ExtensionContext context) throws Exception {
		LOG.info("Logger Extension class processes context " + context.getRoot());
		if (context.getRoot().getStore(namespace).get("arg1",
				String.class) == null) {
			context.getRoot().getStore(namespace).put("arg1", "val1");
		} else {
			// this is never executed
			LOG.info("Read cached data!");
		}
	}
	// https://stackoverflow.com/questions/64523276/in-junit-5-how-can-i-call-a-test-class-method-from-an-extension
}