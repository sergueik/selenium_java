package example;

/**
 * Copyright 2020 Serguei Kouzmine
 */
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.params.ParameterizedTest;

import org.junit.jupiter.params.provider.*;

import java.lang.annotation.Annotation;
import java.util.Collections;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.core.AnyOf.anyOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.Matchers.equalTo;

import com.github.sergueik.junitparams.ExcelParameters;
import com.github.sergueik.junitparams.ExcelParametersProvider;

import org.junit.runners.model.FrameworkMethod;
import java.lang.reflect.Method;

public class NullMethodDataTest {

	private final static String filepath = "classpath:data_2007.xlsx";
	private final static String sheetName = "";
	private final static String type = "Excel 2007";
	private final static boolean debug = true;

	// adapter
	//
	private static Iterable<Object[]> testData() {
		ExcelParameters parametersAnnotation = new ExcelParameters() {

			@Override
			public String filepath() {
				return filepath;
			}

			@Override
			public Class<? extends Annotation> annotationType() {
				// NOTE: the method needed for the interface is Junit 4 legacy:
				// Returns the annotation type of this annotation.
				return null;
			}

			@Override
			public String sheetName() {
				return sheetName;
			}

			@Override
			public String type() {
				return type;
			}

			@Override
			public boolean loadEmptyColumns() {
				return false;
			}

			@Override
			public boolean debug() {
				return debug;
			}

			@Override
			public String controlColumn() {
				return null;
			}

			@Override
			public String withValue() {
				return null;
			}
		};
		String className = "DataTest";
		ExcelParametersProvider provider = new ExcelParametersProvider();
		Method method = null;
		try {
			provider.initialize(parametersAnnotation, new FrameworkMethod(method));
		} catch (SecurityException e) {
			e.printStackTrace();
		}
		// NOTE: the frameworkMethod argument is a legacy
		// Represents a method on a test class to be invoked at the appropriate point in
		// test execution. These methods are usually marked with an annotation (such as
		// @Test, @Before, @After, @BeforeClass, @AfterClass, etc.)

		return Collections.singleton(provider.getParameters());
	}

	@Disabled
	@ParameterizedTest
	@MethodSource("testData")
	void test(String value) {
		System.err.println("Parameter: " + value);
	}

}

