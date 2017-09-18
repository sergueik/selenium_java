package org.utils;

import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.utils.ShareDataTest.Element;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.lang.reflect.Method;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;

// testNG sharing data
// based on: 
// https://groups.google.com/forum/#!topic/selenium-users/4Mdwvr9hRTw

public class ShareDataTest extends BaseTest {
	@BeforeMethod
	public void beforeMethod(Method method, ITestResult testresult) {
		// alternative: ITestContext context
		// super.beforeMethod(method, testresult);
		Inject inject = method.getAnnotation(Inject.class);
		if (inject != null) {
			Element data = new Element("//", 1);
			testresult.setAttribute("element", data);
		}
	}

	@Test
	@Inject
	public void dataConsumingTest() {
		ITestResult testresult = Reporter.getCurrentTestResult();
		Object data = testresult.getAttribute("element");
		if (data instanceof Element) {
			Assert.assertNotNull(((Element) data).getName(),
					"Data element must provide the name");
			Assert.assertTrue(((Element) data).getCount() != 0,
					"Data element must provide nonzero count");
		}
	}

	/**
	 * An inner POJO class.
	 */
	public static class Element {
		private String name;
		private int count;

		Element(String name, int count) {
			this.name = name;
			this.count = count;
		}

		int getCount() {
			return this.count;
		}

		String getName() {
			return this.name;
		}
	}

	/**
	 * Interface which indicates that the annotated Test method needs to be injected with
	 * something.
	 */
	@Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
	@Target({ METHOD, TYPE })
	@interface Inject {
	}
}
