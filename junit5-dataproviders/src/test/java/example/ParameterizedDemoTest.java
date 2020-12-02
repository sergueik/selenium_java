package example;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
// origin: 
// https://github.com/reportportal/examples-java/blob/master/example-junit5/src/test/java/com/epam/reportportal/example/junit5/ParametrizedTest.java

public class ParameterizedDemoTest {

	@ParameterizedTest
	@ValueSource(ints = { 1, 2, 3 })
	void test(int argument) {
		assertTrue(argument > 0 && argument < 4);
		System.err.println("Test run with argument: " + argument);
	}

	@ParameterizedTest
	@MethodSource("stringProvider")
	void testWithExplicitLocalMethodSource(String argument) {
		assertThat(argument, notNullValue());
	}

	static Stream<String> stringProvider() {
		return Stream.of("apple", "banana");
	}


}

