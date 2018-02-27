package demo;

import org.testng.TestListenerAdapter;
import org.testng.TestNG;

public class EntryPoint {
	@SuppressWarnings({ "rawtypes", "deprecation" })
	public static void main(String[] args) {
		TestListenerAdapter testListenerAdapter = new TestListenerAdapter();
		TestNG testng = new TestNG();
		testng.setTestClasses(new Class[] { TestWithData.class });
		testng.addListener(testListenerAdapter);
		testng.run();
	}
}
