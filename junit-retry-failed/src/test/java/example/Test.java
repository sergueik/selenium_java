package example;

import static org.junit.jupiter.api.Assertions.fail;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.CoreMatchers.is;

import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ExtensionContext.Store;

@ExtendWith(TestResultLoggerExtension.class)
class TestWatcherAPIUnitTest {

	// https://junit.org/junit5/docs/5.3.0/api/org/junit/jupiter/api/extension/ExtensionContext.Namespace.html
	// org.junit.platform.commons.PreconditionViolationException: Namespace must
	// not be null
	private final static ExtensionContext.Namespace namespace = ExtensionContext.Namespace.GLOBAL;

	// @Override
	public void beforeEach(ExtensionContext context) {
		Integer value = 42;
		Store store = context.getRoot().getStore(namespace);
		store.put("arg", value);
		// https://www.programcreek.com/java-api-examples/nl/?api=org.apache.meecrowave.Meecrowave
		store.put(Test.class, this);
	}

	@Test
	void test1() {
		assertThat(true, is(false));
	}

	@Disabled
	@Test
	void test2() {
		assertThat(true, is(true));
	}

	@Test
	void test3() {
		assertThat(true, is(true));
	}

	@Disabled("This test is disabled")
	@Test
	void givenFailure_whenTestDisabledWithReason_ThenCaptureResult() {
		fail("Not yet implemented");
	}

}
