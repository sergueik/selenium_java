package com.mycompany.app;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)

// origin:
// https://github.com/junit-team/junit4/wiki/Parameterized-tests
public class App1Test {

	private int input;
	private int result;

	public App1Test(int input, int result) {
		this.input = input;
		this.result = result;
	}

	@Test
	public void test() {
		System.err.println(
				String.format("Test: input = %d, expected result = %d", input, result));
		assertEquals(result, computeFibonacci(input));
	}

	// inline static disconnected data provider
	@Parameters(name = "{index}: fib({0})={1}")
	public static Iterable<Object[]> data() {
		return Arrays.asList(new Object[][] { { 0, 0 }, { 1, 1 }, { 2, 1 },
				{ 3, 2 }, { 4, 3 }, { 5, 5 }, { 6, 8 } });
	}

	// inline static disconnected data provider, different signature
	@Parameters
	public static Iterable<Object> dataStatic() {
		return Arrays.asList("first test", "second test");
	}

	public int computeFibonacci(int n) {
		int result = 0;

		if (n <= 1) {
			result = n;
		} else {
			result = computeFibonacci(n - 1) + computeFibonacci(n - 2);
		}
		return result;
	}
}