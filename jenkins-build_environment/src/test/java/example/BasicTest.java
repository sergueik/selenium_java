package example;

/**
 * Copyright 2021 Serguei Kouzmine
 */

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.collection.IsArrayContaining.hasItemInArray;
import static org.hamcrest.Matchers.arrayContainingInAnyOrder;
import static org.hamcrest.Matchers.hasItems;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

/**
 * Unit Tests for Jenkins
 * 
 * @author: Serguei Kouzmine (kouzmine_serguei@yahoo.com)
 */

@SuppressWarnings("deprecation")
public class BasicTest {

	private static boolean debug = true;

	// @Ignore
	// requires explicit -Dbuild.number=$BUILD_NUMBER argument to pass
	@Test
	public void test1() {
		final String buildNumber = System.getProperty("build.number", null);
		assertThat(buildNumber, notNullValue());
		System.err.println("Property build.number=" + buildNumber);
	}

	@Test
	public void test2() {
		final String buildNumber = System.getenv("BUILD_NUMBER");
		assertThat(buildNumber, notNullValue());
		System.err.println("enviroment BUILD_NUMBER=" + buildNumber);
	}

	// @Ignore
	@Test
	public void test3() {
		final String buildUrl = System.getenv("BUILD_URL");
		assertThat(buildUrl, notNullValue());
		System.err.println("enviroment BUILD_URL=" + buildUrl);
	}

}
