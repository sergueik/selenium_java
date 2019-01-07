### Info

This directory contains a custom Randomize Test class suggested in the forum [Automatically select 10% round-robin subset of the tests during run](https://automated-testing.info/t/testng-zapusk-10-testov-s-randomnoj-vyborkoj/22059/7) (in Russian).

### Usage

It turns out that the advice provided in the forum is incorrect at least witn __TestNg__ version __6.14.3__.

Throwing the `new` `org.testng.SkipException` from the method annotated with `@BeforeMethod` has a horrible side effect: not only one specic test will be skipped but exception would completely abort the flow of test execution. In other words all tests following the intended to skip in their normal (alphabetical, unless specified otherwise) order won't be executed. None of methods annotated with various `@After` scopes will be executed either.

This scenario is illustrated by the class `SkipRestOfTestSetTest` in the current project. The class contains a set of dummy tests named `testTwentyOne`, `testTwentyTwo`, through `testTwentyNine`, and exercises the special method `skipTestFour` in the designated class`TestRandomizer` class.

Notably the `skipTestFour` would better be named `skipAllStartingFromTestFour` since the exception thrown from that method tha is intended to have one specific test named `testTwentyFour` skipped,
```java
public void skipTestFour(String methodName) {
  if (debug) {
    System.err.println("Examine method:   " + methodName);
  }

  if (methodName.matches("(?i).*four.*")) {
    if (debug) {
      System.err.println("Decided to skip" + methodName);
    }
    throw new SkipException("Decided to skip " + methodName);
  }
  // ... rest of the method
}
```
also affects e.g. `testTwentySix` and `testTwentyTwo` but not `testTwentyEight` or `testTwentyFive` thus confirming the default invcation order of test methods is alphabetical.

The better design allowing one e.g. skip 90% of the test set and only execute 10% randomly selected tests, is shown in example class `RandomizedSetsTest` that also utilizes `TestRandomizer`.

The tests in `RandomizedSetsTest` know their name by extending the approproate `BaseTest` (this is useful for inventory). The `TestRanomizer`'s method `decide` is now called from the every `@Test` explicitly rather then from the `@BeforeMethod`:

```java
public class RandomizedSetsTest extends BaseTest {
  @BeforeMethod
  private void setName(Method m) {
    setTestName(m.getName());
  }
  @Test(enabled = true)
  public void testOne() {
    doTest(getTestName());
  }

  public void doTest(String testName) {
    if (debug) {
      System.err.println("Called Test Ramdomizer from method.");
    }
    if (!testRandomizer.decide(testName)) {
      if (debug) {
        System.err.println(String.format("will skip %s", testName));
      }
      throw new SkipException("skipping " + testName);
    }
    assertTrue(true);
  }
```
The logic of `decide` method is currently very elementary:
```java
public boolean decide(String methodName) {
  return (new Random().nextInt() % 10 != 0 & !runAll) ? false : true;
  // inventory action not shown
}
```
This strategy leads toroughly 10% of tests chosen randomly to run in every run:
```sh
-------------------------------------------------------
 T E S T S
-------------------------------------------------------
Running TestSuite
Configuring TestNG with: org.apache.maven.surefire.testng.conf.TestNG652Configur
ator@1175e2db
Inventory tests run:
testNine
```
subsequent run:
```sh
-------------------------------------------------------
 T E S T S
-------------------------------------------------------
Running TestSuite
Configuring TestNG with: org.apache.maven.surefire.testng.conf.TestNG652Configur
ator@1175e2db
Inventory tests run:
testOne
```
### Work in Progress

With the realistic number of tests, and a random `decide` method, inventory  is critical: scanning test log or Alure report does not scale too well.

Persistent inventory into a YAML/csv/spreadsheet/ELK is a work in progress.

### See Also
 * discussion of `SkipException` in [stackoverflow](https://stackoverflow.com/questions/21591712/how-do-i-use-testng-skipexception)
 * customized TestNG [report](https://github.com/djangofan/testng-custom-report-example)illustrating skip techniques

### License
This project is licensed under the terms of the MIT license.

### Author
[Serguei Kouzmine](kouzmine_serguei@yahoo.com)
