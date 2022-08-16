package example;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.AfterAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ExtensionContext.Store;
import org.junit.jupiter.api.extension.TestWatcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestResultLoggerExtension
		implements TestWatcher, AfterAllCallback {

	private final static ExtensionContext.Namespace namespace = ExtensionContext.Namespace.GLOBAL;

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

	// https://github.com/eugenp/tutorials/blob/master/spring-5/src/main/java/com/baeldung/jupiter/SpringExtension.java
	@Override
	public void testFailed(ExtensionContext context, Throwable cause) {
		Store store = context.getRoot().getStore(namespace);
		System.err.println(store.toString());
		// NOTE: NPE if store.get("arg")
		int cnt = (int) store.getOrDefault("arg", Integer.TYPE, -1);
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

}