package example;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import example.Example;

public class ExampleTest {

	private Example sut;

	@Before
	public void setup() {
		sut = new Example();
	}

	@Test
	public void test() {
		assertEquals("Hello World!", sut.getMessage(false));
	}

}
