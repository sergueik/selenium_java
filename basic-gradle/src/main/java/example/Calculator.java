package example;

import java.lang.ArithmeticException;

public class Calculator {

	private double state;

	public Calculator add(double value) {
		state += value;
		return this;
	}

	public Calculator subtract(double value) {
		state -= value;
		return this;
	}

	public Calculator multiply(double value) {
		state = state * value;
		return this;
	}

	public Calculator divide(double value) {
		state = state / value;
		return this;
	}

	public Calculator divideWithException(double value) {
		if (value == 0) {
			throw new ArithmeticException("divide by zero detected");
		}
		state = state / value;
		return this;
	}

	public void resetCalc() {
		state = 0.0;
	}

	public double value() {
		return state;
	}
}
