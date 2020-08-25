package example;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.arrayContainingInAnyOrder;
import static org.hamcrest.Matchers.hasItems;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;

import java.lang.ArithmeticException;

import example.Calculator;

public class CalculatorTest {

	private Calculator calculator = new Calculator();

	@BeforeMethod(alwaysRun = true)
	public void resetCalc() {
		calculator.resetCalc();
	}

	@Test(groups = { "disabled" })
	public void testAdd() {
		calculator.add(5);
		assertThat(calculator.value(), equalTo(5.0));
	}

	@Test(groups = { "test", "broken" })
	public void testSubtract() {
		calculator.subtract(5);
		assertThat(calculator.value(), equalTo(-5.0));

	}

	@Test(groups = { "test" })
	public void testMultiply() {
		calculator.add(5).multiply(2);
		assertThat(calculator.value(), equalTo(10.0));
	}

	@Test
	public void testDivide() {
		calculator.add(10).divide(2);
		assertThat(calculator.value(), equalTo(5.0));
	}

	@Test
	public void testInitial() {
		assertThat(calculator.value(), is(0.0));
	}

	// see also:
	// http://stackoverflow.com/questions/14137989/java-division-by-zero-doesnt-throw-an-arithmeticexception-why"
	@Test
	public void testDivideByZero() {
		calculator.add(1).divide(0);
		System.err.println("Divide by zero results in " + calculator.value());
		assertThat(calculator.value(), is(Double.POSITIVE_INFINITY));
	}

	@Test(expectedExceptions = ArithmeticException.class)
	public void testDivideByZeroWithException() {
		calculator.add(1).divideWithException(0);
	}

}
