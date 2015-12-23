package com.jprotractor.unit;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;

import com.jprotractor.NgBy;
import com.jprotractor.mocks.JavascriptExecutorMock;

public class NgByTest {
	private static JavascriptExecutorMock driver;

	@BeforeClass
	public static void init() {
		driver = new JavascriptExecutorMock();
	}

	@Test
	public void testModel() throws Exception {
		By by = NgBy.model("person.name", driver);
		assertThat(by, notNullValue());
		assertThat(by.toString(),
				equalTo("By.model: person.name"));
	}

	@Test
	public void testButtonText() throws Exception {
		By by = NgBy.buttonText("go!", driver);
		assertThat(by, notNullValue());
		assertThat(by.toString(),
				equalTo("By.buttonText: go!"));
	}

	@Test
	public void testRepeater() throws Exception {
		By by = NgBy.repeater("cust in Customers", driver);
		assertThat(by, notNullValue());
		assertThat(by.toString(),
				equalTo("By.repeater: cust in Customers"));
	}


	@Test
	public void testOptions() throws Exception {
	        By by = NgBy.options("option in options", driver);
		assertThat(by, notNullValue());
		assertThat(by.toString(),
				equalTo("By.options: option in options"));
	}

	public void testBindingModel() throws Exception {
		By by = NgBy.binding("person.address", driver);
		assertThat(by, notNullValue());
		assertThat(by.toString(),
				equalTo("By.binding: person.address"));
	}
}
