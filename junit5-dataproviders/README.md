### Info

This directory contains an [JUnit-DataProviders](https://github.com/sergueik/junit-dataproviders) adapter example of the to 
[Junit 5]((https://junit.org/junit5/docs/current/user-guide/)) annotations.
Original project was designed for __Junit 4__ (there is also [TestNg](https://github.com/sergueik/testng-dataproviders) variation of the same data provider) and uses [JUnitParams](https://github.com/Pragmatists/JUnitParam) Junit plugin therefore implements some specfic iterfaces and the easiest way to use it with Junit 5 appears through an adapter.

### Usage

Embed an [adapter](https://www.baeldung.com/java-adapter-pattern)
into the `@MethodSource` method (for simlicity the needed arguments made class-level static)
```

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
		for (int cnt = 0; cnt != parameters.length; cnt++) {
			Object[] row = (Object[]) parameters[cnt];
			result.add(row);
		}
		Object[][] items = new Object[result.size()][];
		return Stream.of(result.toArray(items));
	}

```

Then annotate the test method and inject paramers as [usual](https://www.baeldung.com/parameterized-tests-junit-5):
```java
@ParameterizedTest
@MethodSource("testData")
public void test(Object param1, Object param2) {
  assertThat(param1, notNullValue());
  System.err.println("Parameter 1: " + param1.toString());
  assertThat(param2, notNullValue());
  System.err.println("Parameter 2: " + param2.toString());
}
```

the run will produce:
```sh
Parameter 1: 1.0
Parameter 2: junit
Parameter 1: 2.0
Parameter 2: testng
Parameter 1: 3.0
Parameter 2: spock
```


### See Also

  * [guide](https://www.baeldung.com/parameterized-tests-junit-5) to Junit 5 `@ParameterizedTest` 
  * general [user guide](https://junit.org/junit5/docs/current/user-guide/)


### Author
[Serguei Kouzmine](kouzmine_serguei@yahoo.com)
