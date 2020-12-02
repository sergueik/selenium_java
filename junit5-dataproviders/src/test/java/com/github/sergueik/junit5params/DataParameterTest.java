package com.github.sergueik.junit5params;

/**
 * Copyright 2018,2019 Serguei Kouzmine
 */
import org.junit.jupiter.params.ParameterizedTest;

import org.junit.jupiter.params.provider.*;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
// NOTE: departure from harmcrest assertions
import java.util.stream.Stream;

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

public class DataParameterTest {
	private final static String filepath = "classpath:data_2007.xlsx";
	private final static String sheetName = "";
	private final static String type = "Excel 2007";
	private final static boolean debug = true;
	private final static String controlColumn = "";
	private final static String withValue = "";
	private static final ExcelParameters parametersAnnotation = new ExcelParameters() {
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
			return controlColumn;
		}

		@Override
		public String withValue() {
			return withValue;
		}
	};

	// NOTE: auto-suggested type is very wrong:
	// private static Iterable<Object[]> testData() {
	// ...
	// return Collections.singleton(parameters);
	// }

	public static Stream<Object[]> testData() {

		ExcelParametersProvider provider = new ExcelParametersProvider();
		try {
			provider.initialize(parametersAnnotation,
					new FrameworkMethod(Class.forName("com.github.sergueik.junit5params.DataParameterTest")
							.getMethod("test", Object.class, Object.class)));
			// TODO: alternatively, can
			// new DataParameterTest().getClass().getMethod("test", Object.class,
			// Object.class)
		} catch (NoSuchMethodException | SecurityException | ClassNotFoundException e) {
			System.err.println("Exception in initialize (ignored): " + e.getMessage());
			e.printStackTrace();
		} catch (java.lang.NullPointerException e) {
			// for unsatisfied Excel Parameter properties
			e.printStackTrace();
		}
		// unwind the packaging
		Object[] parameters = provider.getParameters();
		List<Object[]> result = new ArrayList<>();
		if (debug) {
			System.err.println(String.format("Received %d parameters", parameters.length));
		}
		for (int cnt = 0; cnt != parameters.length; cnt++) {
			Object[] row = (Object[]) parameters[cnt];
			result.add(row);
			if (debug) {
				for (int cnt2 = 0; cnt2 != parameters.length; cnt2++) {
					System.err.println(
							String.format("parameter row: %d col: %d data: %s", cnt, cnt2, String.valueOf(row[cnt2])));
				}
			}
		}
		Object[][] items = new Object[result.size()][];
		return Stream.of(result.toArray(items));
	}

	@ParameterizedTest
	@MethodSource("testData")
	public void test(Object param1, Object param2) {
		assertThat(param1, notNullValue());
		System.err.println("Parameter 1: " + param1.toString());
		assertThat(param2, notNullValue());
		System.err.println("Parameter 2: " + param2.toString());
	}

}
