package demo;

import org.testng.TestListenerAdapter;
import org.testng.TestNG;

public class EntryPoint {
	@SuppressWarnings("deprecation")
	public static void main(String[] args) {
		TestListenerAdapter tla = new TestListenerAdapter();
		// @SuppressWarnings("rawtypes")
		TestNG testng = new TestNG();
		testng.setTestClasses(new Class[] { TestNGDD.class });
		testng.addListener(tla);
		testng.run();
	}
}
