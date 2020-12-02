package com.github.sergueik.junit5params;

/**
 * Copyright 2018,2019 Serguei Kouzmine
 */
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.notNullValue;

import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

// origin:
// https://github.com/reportportal/examples-java/blob/master/example-junit5/src/test/java/com/epam/reportportal/example/junit5/ParametrizedTest.java
public class InlineParameterTest {

	@ParameterizedTest
	@ValueSource(ints = { 1, 2, 3 })
	void test(int param) {
		System.err.println("Test run with param: " + param);
		assertThat(param > 0, is(true));
	}

	@ParameterizedTest
	@MethodSource("stringProvider")
	void testWithExplicitLocalMethodSource(String param) {
		System.err.println("Test run with param: " + param);
		assertThat(param, notNullValue());
	}

	static Stream<String> stringProvider() {
		return Stream.of("apple", "banana");
	}

}
