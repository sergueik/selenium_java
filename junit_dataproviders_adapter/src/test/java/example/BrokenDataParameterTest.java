package example;

/**
 * Copyright 2020 Serguei Kouzmine
 */
import org.junit.jupiter.params.ParameterizedTest;

import org.junit.jupiter.params.provider.*;

import java.lang.annotation.Annotation;
import java.util.Collections;

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
import java.lang.reflect.Method;

public class BrokenDataParameterTest {

	private final static String filepath = "classpath:data_2007.xlsx";
	private final static String sheetName = "";
	private final static String type = "Excel 2007";
	private final static boolean debug = false;
	private final static String controlColumn = "";
	private final static String withValue = "";

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
				return controlColumn;
			}

			@Override
			public String withValue() {
				return withValue;
			}
		};
		ExcelParametersProvider provider = new ExcelParametersProvider();

		try {
			Class<?> _class = Class
					.forName("example.BrokenDataParameterTest");
			Method _method = _class.getMethod("test", Object.class);
			FrameworkMethod _frameworkMedhod = new FrameworkMethod(_method);
			provider.initialize(parametersAnnotation, _frameworkMedhod);
		} catch (ClassNotFoundException | NoSuchMethodException
				| SecurityException e) {
			System.err.println("Exception (ignored): " + e.getMessage());
			e.printStackTrace();
		} catch (java.lang.NullPointerException e) {
			// for unsatisfied Excel Parameter properties
			e.printStackTrace();
		}
		Object[] parameters = provider.getParameters();
		if (debug) {
			System.err
					.println(String.format("Received %d parameters", parameters.length));
		}
		if (debug) {
			for (int cnt = 0; cnt != parameters.length; cnt++) {
				Object[] row = (Object[]) parameters[cnt];
				System.err.println(
						String.format("parameter # %d: %s", cnt, String.valueOf(row[0])));
			}
		}
		return Collections.singleton(parameters);
		// TODO:
		// return Stream.of(parameters);

	}

	static public void dummy(String data) {

	}

	@ParameterizedTest
	@MethodSource("testData")
	public void test(Object param) {
		// TODO: debug being called
		Object[] row = (Object[]) param;
		System.err.println(String.format("Received %d parameters", row.length));
		for (int cnt = 0; cnt != row.length; cnt++) {
			System.err.println("Parameter: " + row[cnt]);
		}
	}

}
