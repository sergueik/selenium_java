package com.github.sergueik.junit5params;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class ParameterizedDemoTest {

	@ParameterizedTest
	@ValueSource(ints = { 1, 2, 3 })
	void test(int argument) {
		assertTrue(argument > 0 && argument < 4);
		System.err.println("Test run with argument: " + argument);
	}

}
