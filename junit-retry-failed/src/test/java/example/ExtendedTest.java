package example;

import static org.junit.jupiter.api.Assertions.fail;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.CoreMatchers.is;

import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ExtensionContext.Store;
import org.junit.jupiter.api.extension.ParameterResolutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ExtendWith(TestResultLoggerExtension.class)
class ExtendedTest {

	private Integer value = 42;

	public Integer getValue() {
		return value;
	}

	public void setValue(Integer value) {
		this.value = value;
	}

	private static final Logger LOG = LoggerFactory.getLogger(ExtendedTest.class);

	// https://junit.org/junit5/docs/5.3.0/api/org/junit/jupiter/api/extension/ExtensionContext.Namespace.html
	// org.junit.platform.commons.PreconditionViolationException: Namespace must
	// not be null
	private final ExtensionContext.Namespace namespace = ExtensionContext.Namespace
			.create(ExtendedTest.class);

	// see also:
	// https://www.programcreek.com/java-api-examples/?api=org.junit.jupiter.api.extension.ExtensionContext.Store
	// https://stackoverflow.com/questions/49532885/with-junit-5-how-to-share-information-in-extensioncontext-store-between-test
	// https://github.com/manovotn/YetAnotherMinimalReproducer
	@BeforeEach
	public void beforeEach(ExtensionContext context) {
		LOG.info("Test in: beforeEach with context: " + context.getRoot());

		Store store = context.getRoot().getStore(namespace);
		try {
			store.put("arg", value);
			LOG.info("Test sends: " + value);
		} catch (ParameterResolutionException e) {
			// org.junit.jupiter.api.extension.ParameterResolutionException:
			// No ParameterResolver registered for parameter
			// [org.junit.jupiter.api.extension.ExtensionContext arg0]
			// does not get caughtf
			LOG.info("Test fails to send data");
		}

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
