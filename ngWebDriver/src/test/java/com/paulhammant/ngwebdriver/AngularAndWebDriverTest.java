package com.paulhammant.ngwebdriver;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.startsWith;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.endsWith;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItems;

import static org.openqa.selenium.By.id;
import static org.openqa.selenium.By.tagName;
import static org.openqa.selenium.By.xpath;

import static org.testng.Assert.assertEquals;
import static org.testng.AssertJUnit.fail;

import java.io.File;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.HttpConnectionFactory;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.server.handler.DefaultHandler;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.MovedContextHandler;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.util.log.Log;
import org.eclipse.jetty.util.log.StdErrLog;
import org.eclipse.jetty.util.thread.QueuedThreadPool;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.seleniumhq.selenium.fluent.FluentBy;
import org.seleniumhq.selenium.fluent.FluentExecutionStopped;
import org.seleniumhq.selenium.fluent.FluentMatcher;
import org.seleniumhq.selenium.fluent.FluentWebDriver;
import org.seleniumhq.selenium.fluent.FluentWebElement;
import org.seleniumhq.selenium.fluent.FluentWebElementMap;
import org.seleniumhq.selenium.fluent.FluentWebElements;

import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

public class AngularAndWebDriverTest {

	private ChromeDriver driver;
	private static final Map<String, String> browserDrivers = new HashMap<>();
	static {
		browserDrivers.put("chrome", "chromedriver.exe");
		browserDrivers.put("firefox", "geckodriver.exe");
	}
	private Server webServer;
	private NgWebDriver ngWebDriver;

	@SuppressWarnings("deprecation")
	private void setupBrowser(String browser) {
		System.setProperty("webdriver.chrome.driver",
				(new File("c:/java/selenium/chromedriver.exe")).getAbsolutePath());
		DesiredCapabilities capabilities = DesiredCapabilities.chrome();
		ChromeOptions chromeOptions = new ChromeOptions();

		Map<String, Object> chromePrefs = new HashMap<>();
		chromePrefs.put("profile.default_content_settings.popups", 0);
		String downloadFilepath = System.getProperty("user.dir")
				+ System.getProperty("file.separator") + "target"
				+ System.getProperty("file.separator");
		chromePrefs.put("download.prompt_for_download", "false");
		chromePrefs.put("download.directory_upgrade", "true");
		chromePrefs.put("plugins.always_open_pdf_externally", "true");

		chromePrefs.put("download.default_directory", downloadFilepath);
		chromePrefs.put("enableNetwork", "true");
		chromeOptions.setExperimentalOption("prefs", chromePrefs);

		for (String optionAgrument : (new String[] {
				"--user-agent=Mozilla/5.0 (Windows NT 6.1; WOW64; rv:33.0) Gecko/20120101 Firefox/33.0",
				"--allow-running-insecure-content", "--allow-insecure-localhost",
				"--enable-local-file-accesses", "--disable-notifications",
				"--disable-save-password-bubble",
				/* "start-maximized" , */
				"--browser.download.folderList=2", "--disable-web-security",
				"--no-proxy-server",
				"--browser.helperApps.neverAsk.saveToDisk=image/jpg,text/csv,text/xml,application/xml,application/vnd.ms-excel,application/x-excel,application/x-msexcel,application/excel,application/pdf",
				String.format("--browser.download.dir=%s", downloadFilepath)
				/* "--user-data-dir=/path/to/your/custom/profile"  , */

		})) {
			chromeOptions.addArguments(optionAgrument);
		}

		capabilities.setBrowserName(DesiredCapabilities.chrome().getBrowserName());
		capabilities.setCapability(
				org.openqa.selenium.chrome.ChromeOptions.CAPABILITY, chromeOptions);
		capabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
		driver = new ChromeDriver(capabilities);
	}

	@BeforeSuite
	public void before_suite() throws Exception {

		// Launch Protractor's own test app on http://localhost:8080
		((StdErrLog) Log.getRootLogger()).setLevel(StdErrLog.LEVEL_OFF);
		webServer = new Server(new QueuedThreadPool(6));
		ServerConnector connector = new ServerConnector(webServer,
				new HttpConnectionFactory());
		connector.setPort(8080);
		webServer.addConnector(connector);
		ResourceHandler resource_handler = new ResourceHandler();
		resource_handler.setDirectoriesListed(true);
		resource_handler.setWelcomeFiles(new String[] { "index.html" });
		resource_handler.setResourceBase("src/test/webapp");
		HandlerList handlers = new HandlerList();
		MovedContextHandler effective_symlink = new MovedContextHandler(webServer,
				"/lib/angular", "/lib/angular_v1.2.9");
		handlers.setHandlers(new Handler[] { effective_symlink, resource_handler,
				new DefaultHandler() });
		webServer.setHandler(handlers);
		webServer.start();
		setupBrowser("chrome");

		driver.manage().timeouts().setScriptTimeout(30, TimeUnit.SECONDS);
		ngWebDriver = new NgWebDriver(driver);
	}

	@AfterSuite
	public void after_suite() throws Exception {
		driver.quit();
		webServer.stop();
	}

	@BeforeMethod
	public void resetBrowser() {
		driver.get("about:blank");
	}

	@Test(enabled = false)
	public void find_by_angular_model() {

		// driver.get("http://www.angularjshub.com/code/examples/basics/02_TwoWayDataBinding_HTML/index.demo.php");
		driver.get("http://localhost:8080/");
		ngWebDriver.waitForAngularRequestsToFinish();

		WebElement firstname = driver.findElement(ByAngular.model("username"));
		firstname.clear();
		firstname.sendKeys("Mary");
		assertEquals(driver.findElement(xpath("//input")).getAttribute("value"),
				"Mary");

	}

	@Test(enabled = false)
	public void find_all_for_an_angular_options() {

		driver.get("http://localhost:8080/#/form");
		ngWebDriver.waitForAngularRequestsToFinish();

		List<WebElement> weColors = driver
				.findElements(ByAngular.options("fruit for fruit in fruits"));
		assertThat(weColors.get(0).getText(), containsString("apple"));
		assertThat(weColors.get(3).getText(), containsString("banana"));

	}

	@Test(enabled = false)
	public void find_by_angular_buttonText() {

		driver.get("http://localhost:8080/#/form");
		ngWebDriver.waitForAngularRequestsToFinish();

		driver.findElement(ByAngular.buttonText("Open Alert")).click();
		Alert alert = driver.switchTo().alert();
		assertThat(alert.getText(), containsString("Hello"));
		alert.accept();
	}

	@Test(enabled = false)
	public void find_by_angular_partialButtonText() {

		driver.get("http://localhost:8080/#/form");
		ngWebDriver.waitForAngularRequestsToFinish();

		driver.findElement(ByAngular.partialButtonText("Ale")).click();
		Alert alert = driver.switchTo().alert();
		assertThat(alert.getText(), containsString("Hello"));
		alert.accept();
	}

	@Test(enabled = false)
	public void find_by_angular_cssContainingText() {

		driver.get("http://localhost:8080/#/form");
		ngWebDriver.waitForAngularRequestsToFinish();

		List<WebElement> wes = driver
				.findElements(ByAngular.cssContainingText("#animals ul .pet", "dog"));
		assertThat(wes.size(), is(2));
		assertThat(wes.get(1).getText(), containsString("small dog"));
	}

	@Test(enabled = false)
	public void find_by_angular_cssContainingTextRegexp() {

		driver.get("http://localhost:8080/#/form");
		ngWebDriver.waitForAngularRequestsToFinish();

		List<WebElement> wes = driver
				.findElements(ByAngular.cssContainingText("#animals ul .pet",
						"__REGEXP__/(?:BIG|small) *(?:CAT|dog)(something else)*/i"));
		assertThat(wes.size(), is(4));
		// wes.stream().map(o -> o.getText()).forEach(System.err::println);
		Object[] results = new Object[] { "big dog", "small dog", "big cat",
				"small cat" };
		assertThat(wes.stream().map(o -> o.getText()).collect(Collectors.toSet()),
				hasItems(results));
	}

	@Test(enabled = false)
	public void find_multiple_hits_for_ng_repeat_in_page() {

		driver.get(
				"http://www.angularjshub.com/code/examples/collections/01_Repeater/index.demo.php");
		ngWebDriver.waitForAngularRequestsToFinish();

		List<WebElement> wes = driver.findElement(tagName("table"))
				.findElements(ByAngular.repeater("person in people"));

		assertThat(wes.size(), is(4));
		assertThat(wes.get(0).findElement(tagName("td")).getText(),
				containsString("John"));
		assertThat(wes.get(1).findElement(tagName("td")).getText(),
				containsString("Bob"));
		assertThat(wes.get(2).findElement(tagName("td")).getText(),
				containsString("Jack"));
		assertThat(wes.get(3).findElement(tagName("td")).getText(),
				containsString("Michael"));

	}

	@Test(enabled = false)
	public void find_multiple_hits_for_ng_repeat_and_subset_to_first_matching_predicate_for_fluent_selenium_example() {

		// As much as anything, this is a test of FluentSelenium

		driver.get(
				"http://www.angularjshub.com/code/examples/collections/01_Repeater/index.demo.php");
		ngWebDriver.waitForAngularRequestsToFinish();

		new FluentWebDriver(driver)
				.lis(ByAngular.repeater("(propName, propValue) in person"))
				.first(new TextContainsTerm("name =")).getText().shouldContain("John");

	}

	public static class TextContainsTerm implements FluentMatcher {

		private String term;

		public TextContainsTerm(String term) {
			this.term = term;
		}

		public boolean matches(FluentWebElement webElement, int ix) {
			return webElement.getWebElement().getText().indexOf(term) > -1;
		}

		@Override
		public String toString() {
			return "TextContainsTerm{term='" + term + "'}";
		}
	}

	@Test(enabled = false)
	public void find_second_row_in_ng_repeat() {

		driver.get(
				"http://www.angularjshub.com/code/examples/collections/01_Repeater/index.demo.php");
		ngWebDriver.waitForAngularRequestsToFinish();

		// find the second person
		// index starts withRootSelector 0 of course

		assertThat(driver.findElement(ByAngular.repeater("person in people").row(1))
				.getText(), is("Bob Smith"));

	}

	// TODO
	@Test(enabled = false)
	public void find_specific_cell_in_ng_repeat() {

		driver.get(
				"http://www.angularjshub.com/code/examples/collections/01_Repeater/index.demo.php");
		ngWebDriver.waitForAngularRequestsToFinish();

		driver.findElement(ByAngular.repeater("person in selectablePeople").row(2)
				.column("person.isSelected")).click();

		assertThat(driver
				.findElement(xpath("//tr[@byNg-repeat='person in selectablePeople']"))
				.getText(), is("x y z"));
	}

	@Test(enabled = false)
	public void find_specific_cell_in_ng_repeat_the_other_way() {

		driver.get(
				"http://www.angularjshub.com/code/examples/collections/01_Repeater/index.demo.php");
		ngWebDriver.waitForAngularRequestsToFinish();

		// find the second address' city
		driver.findElement(ByAngular.repeater("person in selectablePeople")
				.column("person.isSelected").row(2)).click();

		assertThat(driver
				.findElement(xpath("//tr[@byNg-repeat='person in selectablePeople']"))
				.getText(), is("x y z"));
	}

	@Test(enabled = false)
	public void find_all_of_a_column_in_an_ng_repeat() {

		driver.get(
				"http://www.angularjshub.com/code/examples/collections/01_Repeater/index.demo.php");
		ngWebDriver.waitForAngularRequestsToFinish();

		List<WebElement> we = driver.findElements(ByAngular
				.repeater("person in selectablePeople").column("person.isSelected"));

		assertThat(we.get(0).getText(), is("unselcted"));
		we.get(1).click();
		assertThat(we.get(1).getText(), is("selected"));
		assertThat(we.get(2).getText(), is("unselected"));
	}

	@Test(enabled = false)
	public void find_by_angular_binding() {

		driver.get("http://localhost:8080/#/form");
		ngWebDriver.waitForAngularRequestsToFinish();

		// An example showcasing ByBinding, where we give a substring of the binding
		// attribute
		List<WebElement> wes = driver.findElements(ByAngular.binding("user"));
		assertThat(wes.get(0).getText(), is("Anon"));
		// An exmple showcasing ByExactBinding, where it strictly matches the given
		// Biding attribute name.
		List<WebElement> weeb = driver
				.findElements(ByAngular.exactBinding("username"));
		assertThat(weeb.get(0).getText(), is("Anon"));
	}

	@Test(enabled = false)
	public void find_all_for_an_angular_binding() {

		driver.get(
				"http://www.angularjshub.com/code/examples/forms/04_Select/index.demo.php");
		ngWebDriver.waitForAngularRequestsToFinish();

		List<WebElement> wes = driver
				.findElements(ByAngular.binding("peopleArrayValue4"));

		assertThat(wes.get(0).getTagName(), is("textarea"));

		// really need an example that return more than one.

	}

	// Model interaction

	@SuppressWarnings("unchecked")
	@Test(enabled = false)
	public void model_mutation_and_query_is_possible() {

		driver.get(
				"http://www.angularjshub.com/code/examples/forms/08_FormSubmission/index.demo.php");
		ngWebDriver.waitForAngularRequestsToFinish();

		WebElement fn = driver.findElement(id("firstNameEdit1"));
		fn.sendKeys("Fred");
		WebElement ln = driver.findElement(id("lastNameEdit1"));
		ln.sendKeys("Flintstone");

		WebElement wholeForm = driver
				.findElement(FluentBy.attribute("name", "personForm1"));

		NgWebDriver ngModel = new NgWebDriver(driver);

		// change something via the $scope model
		ngModel.mutate(wholeForm, "person1.firstName", "'Wilma'");
		// assert the change happened via regular WebDriver.
		assertThat(fn.getAttribute("value"), containsString("Wilma"));

		// retrieve the JSON for the location via the $scope model
		String tv = ngModel.retrieveJson(wholeForm, "person1");
		assertThat(tv, is("{\"firstName\":\"Wilma\",\"lastName\":\"Flintstone\"}"));

		// retrieve a single field as JSON
		String v = ngModel.retrieveJson(wholeForm, "person1.firstName");

		// assert that it comes back indicating its type (presence of quotes)
		assertThat(v, is("\"Wilma\""));

		// retrieve it again, but directly as String
		v = ngModel.retrieveAsString(wholeForm, "person1.firstName");

		// assert it is still what we expect
		assertThat(v, is("Wilma"));

		// WebDriver naturally hands back as a Map if it is not one
		// variable..
		Object rv = ngModel.retrieve(wholeForm, "person1");
		assertThat(((Map<String, String>) rv).get("firstName").toString(),
				is("Wilma"));

		// If something is numeric, WebDriver hands that back
		// naturally as a long.
		// long id = ngModel.retrieveAsLong(wholeForm, "location.Id");
		// assertThat(id, is(1675L));

		// Can't process scoped variables that don't exist
		try {
			ngModel.retrieve(wholeForm, "person1.Cityyyyyyy");
			fail("should have barfed");
		} catch (WebDriverException e) {
			assertThat(e.getMessage(), startsWith(
					"$scope variable 'person1.Cityyyyyyy' not found in same scope as the element passed in."));
		}
		// Can't process scoped variables that don't exist
		try {
			ngModel.retrieveJson(wholeForm, "locationnnnnnnnn");
		} catch (WebDriverException e) {
			assertThat(e.getMessage(), startsWith(
					"$scope variable 'locationnnnnnnnn' not found in same scope as the element passed in."));
		}
		// Can't process scoped variables that don't exist
		try {
			ngModel.retrieveAsLong(wholeForm, "person1.Iddddddd");
		} catch (WebDriverException e) {
			assertThat(e.getMessage(), startsWith(
					"$scope variable 'person1.Iddddddd' not found in same scope as the element passed in."));
		}

		// You can set whole parts of the tree within the scope..
		ngModel.mutate(wholeForm, "person1",
				"{" + "  firstName: 'Barney'," + "  lastName: 'Rubble'" + "}");

		assertEquals(fn.getAttribute("value"), "Barney");
		assertEquals(ln.getAttribute("value"), "Rubble");

		// Keys can be in quotes (single or double) or not have quotes at all.
		// Values can be in quotes (single or double) or not have quotes if
		// they are not of type string.

	}

	// All the failure tests

	@Test(enabled = false)
	public void findElement_should_barf_with_message_for_bad_repeater() {

		driver.get(
				"http://www.angularjshub.com/code/examples/forms/04_Select/index.demo.php");
		ngWebDriver.waitForAngularRequestsToFinish();

		try {
			driver.findElement(ByAngular.repeater("location in Locationssss"));
			fail("should have barfed");
		} catch (NoSuchElementException e) {
			assertThat(e.getMessage(), startsWith(
					"repeater(location in Locationssss) didn't have any matching elements at this place in the DOM"));
		}

	}

	@Test(enabled = false)
	public void findElement_should_barf_with_message_for_bad_repeater_and_row() {

		driver.get(
				"http://www.angularjshub.com/code/examples/forms/04_Select/index.demo.php");
		ngWebDriver.waitForAngularRequestsToFinish();

		try {
			driver.findElement(
					ByAngular.repeater("location in Locationssss").row(99999));
			fail("should have barfed");
		} catch (NoSuchElementException e) {
			assertThat(e.getMessage(), startsWith(
					"repeater(location in Locationssss).row(99999) didn't have any matching elements at this place in the DOM"));
		}

	}

	@Test(enabled = false)
	public void findElements_should_barf_with_message_for_any_repeater_and_row2() {

		driver.get(
				"http://www.angularjshub.com/code/examples/forms/04_Select/index.demo.php");
		ngWebDriver.waitForAngularRequestsToFinish();

		try {
			driver.findElements(
					ByAngular.repeater("location in Locationssss").row(99999));
			fail("should have barfed");
		} catch (UnsupportedOperationException e) {
			assertThat(e.getMessage(), startsWith(
					"This locator zooms in on a single row, findElements() is meaningless"));
		}

	}

	@Test(enabled = false)
	public void findElement_should_barf_with_message_for_bad_repeater_and_row_and_column() {

		try {
			driver.findElement(ByAngular.repeater("location in Locationssss")
					.row(99999).column("blort"));
			fail("should have barfed");
		} catch (NoSuchElementException e) {
			assertThat(e.getMessage(), startsWith(
					"repeater(location in Locationssss).row(99999).column(blort) didn't have any matching elements at this place in the DOM"));
		}
	}

	@Test(enabled = false)
	public void findElements_should_barf_with_message_for_any_repeater_and_row_and_column() {

		driver.get(
				"http://www.angularjshub.com/code/examples/forms/04_Select/index.demo.php");
		ngWebDriver.waitForAngularRequestsToFinish();

		try {
			driver.findElements(ByAngular.repeater("location in Locationssss")
					.row(99999).column("blort"));
			fail("should have barfed");
		} catch (UnsupportedOperationException e) {
			assertThat(e.getMessage(), startsWith(
					"This locator zooms in on a single cell, findElements() is meaningless"));
		}
	}

	@Test(enabled = false)
	public void findElement_should_barf_when_element_not_in_the_dom() {

		driver.get(
				"http://www.angularjshub.com/code/examples/forms/04_Select/index.demo.php");
		ngWebDriver.waitForAngularRequestsToFinish();

		try {
			driver.findElement(
					ByAngular.repeater("location in Locationssss").column("blort"));
			fail("should have barfed");
		} catch (NoSuchElementException e) {
			assertThat(e.getMessage(), startsWith(
					"repeater(location in Locationssss).column(blort) didn't have any matching elements at this place in the DOM"));
		}
	}

	@Test(enabled = false)
	public void findElements_should_barf_with_message_for_bad_repeater_and_column() {

		driver.get(
				"http://www.angularjshub.com/code/examples/forms/04_Select/index.demo.php");
		ngWebDriver.waitForAngularRequestsToFinish();

		try {
			driver.findElements(
					ByAngular.repeater("location in Locationssss").column("blort"));
			fail("should have barfed");
		} catch (NoSuchElementException e) {
			assertThat(e.getMessage(), startsWith(
					"repeater(location in Locationssss).column(blort) didn't have any matching elements at this place in the DOM"));
		}
	}

	@SuppressWarnings("deprecation")
	@Test(enabled = true)
	public void waitTests() {

		driver.get("http://juliemr.github.io/protractor-demo/");
		ngWebDriver.waitForAngularRequestsToFinish();

		driver.findElement(ByAngular.model("first")).sendKeys("40");
		driver.findElement(ByAngular.model("second")).sendKeys("2");
		driver.findElement(ByAngular.buttonText("Go!")).click();
		By locator = ByAngular.exactBinding("latest");
		WebDriverWait wait = new WebDriverWait(driver, 120);
		wait.pollingEvery(1000, TimeUnit.MILLISECONDS);
		try {
			// should work
			wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
			System.err.println("After wait");
		} catch (TimeoutException e) {
			System.err.println("Timeout Exception");
		} catch (Exception e) {
			System.err.println("Exception (ignored) " + e.toString());
		}

		try {
			wait.until(new ExpectedCondition<Boolean>() {
				@Override
				public Boolean apply(WebDriver d) {
					WebElement e = d.findElement(locator);
					Boolean result = e.isDisplayed();
					System.err
							.println("In apply: Element = " + e.getAttribute("outerHTML")
									+ "\nresult = " + result.toString());
					return result;
				}
			});
		} catch (Exception e) {
			System.err.println("Exception: " + e.toString());
			// throw new RuntimeException(e);
		}
		try {
			Thread.sleep(10000);

		} catch (Exception e) {
		}
	}

	/*
	  Ported from protractor/stress/spec.js
	 */
	@Test(enabled = false)
	public void stress_test() {
		FluentWebDriver fwd = new FluentWebDriver(driver);
		for (int i = 0; i < 20; ++i) {
			resetBrowser();

			driver.get("http://localhost:8080/index.html#/form");
			FluentWebElement usernameInput = fwd.input(ByAngular.model("username"));
			FluentWebElement name = fwd.span(ByAngular.binding("username"));

			ngWebDriver.waitForAngularRequestsToFinish();

			name.getText().shouldBe("Anon");
			usernameInput.clearField().sendKeys("B");
			name.getText().shouldBe("B");
		}
	}

	/*
	  Ported from protractor/spec/altRoot/findelements_spec.js
	 */
	@Test(enabled = false)
	public void altRoot_find_elements() {
		FluentWebDriver fwd = new FluentWebDriver(driver);
		driver.get("http://localhost:8080/alt_root_index.html#/form");
		ngWebDriver.waitForAngularRequestsToFinish();

		fwd.span(ByAngular.binding("{{greeting}}")).getText().shouldBe("Hiya");

		fwd.div(id("outside-ng")).getText().shouldBe("{{1 + 2}}");
		fwd.div(id("inside-ng")).getText().shouldBe("3");
	}

	/*
	  Ported from protractor/spec/basic/action_spec.js
	 */
	@Test(enabled = true)
	public void basic_actions() {
		FluentWebDriver fwd = new FluentWebDriver(driver);
		driver.get("http://localhost:8080/index.html#/form");
		ngWebDriver.waitForAngularRequestsToFinish();

		FluentWebElement sliderBar = fwd.input(By.name("points"));

		sliderBar.getAttribute("value").shouldBe("1");

		new Actions(driver).dragAndDropBy(sliderBar.getWebElement(), 400, 20)
				.build().perform();

		sliderBar.getAttribute("value").shouldBe("10");
	}

	/*
	  Ported from protractor/spec/basic/elements_spec.js
	  TODO - many more specs in here
	 */
	@Test(enabled = true)
	public void basic_elements_should_chain_with_index_correctly() {
		FluentWebDriver fwd = new FluentWebDriver(driver);
		driver.get("http://localhost:8080/index.html");

		fwd.inputs(By.cssSelector("#checkboxes input")).last(new IsIndex2Or3())
				.click();

		fwd.span(By.cssSelector("#letterlist")).getText().shouldBe("'x'");

	}

	private static class IsIndex2Or3 implements FluentMatcher {
		public boolean matches(FluentWebElement webElement, int ix) {
			return ix == 2 || ix == 3;
		}
	}

	/*
	  Ported from protractor/spec/basic/elements_spec.js
	  TODO - many more specs in here
	 */
	@Test(enabled = false)
	public void basic_elements_chained_call_should_wait_to_grab_the_WebElement_until_a_method_is_called() {
		FluentWebDriver fwd = new FluentWebDriver(driver);

		driver.get("http://localhost:8080/index.html#/conflict");

		FluentWebElement reused = fwd.div(id("baz"))
				.span(ByAngular.binding("item.reusedBinding"));

		reused.getText().shouldBe("Inner: inner");

	}

	/*
	  Ported from protractor/spec/basic/elements_spec.js
	  TODO - many more specs in here
	 */
	@SuppressWarnings("serial")
	@Test(enabled = false)
	public void basic_elements_should_allow_using_repeater_locator_within_map() {
		FluentWebDriver fwd = new FluentWebDriver(driver);

		driver.get("http://localhost:8080/index.html#/repeater");

		Map<String, String> expected = new HashMap<String, String>() {
			{
				put("M", "Monday");
				put("T", "Tuesday");
				put("W", "Wednesday");
				put("Th", "Thursday");
				put("F", "Friday");
			}
		};

		Map<String, String> days = fwd.lis(ByAngular.repeater("allinfo in days"))
				.map(new FluentWebElementMap<String, String>() {
					public void map(FluentWebElement elem, int ix) {
						put(elem.element(ByAngular.binding("allinfo.initial")).getText()
								.toString(),
								elem.element(ByAngular.binding("allinfo.name")).getText()
										.toString());
					}
				});

		assertThat(days.entrySet(), equalTo(expected.entrySet()));

	}

	/*
	  Ported from protractor/spec/basic/locators_spec.js
	  TODO - many more specs in here
	 */
	@Test(enabled = false)
	public void basic_locators_by_repeater_should_find_by_partial_match() {
		FluentWebDriver fwd = new FluentWebDriver(driver);

		driver.get("http://localhost:8080/index.html#/repeater");

		fwd.span(ByAngular.repeater("baz in days | filter:'T'").row(0)
				.column("baz.initial")).getText().shouldBe("T");

		fwd.span(
				ByAngular.repeater("baz in days | fil").row(0).column("baz.initial"))
				.getText().shouldBe("T");

		try {
			fwd.li(ByAngular.exactRepeater("baz in days | fil").row(0)
					.column("baz.initial")).getText().shouldBe("T");
			fail("should have barfed");
		} catch (FluentExecutionStopped e) {
			assertThat(e.getMessage(), startsWith(
					"NoSuchElementException during invocation of: ?.li(exactRepeater(baz in days | fil).row(0).column(baz.initial))"));
			assertThat(e.getCause().getMessage(), startsWith(
					"exactRepeater(baz in days | fil).row(0).column(baz.initial) didn't have any matching elements at this place in the DOM"));
		}

	}

	/*
	  Ported from protractor/spec/basic/locators_spec.js
	  TODO - many more specs in here
	 */
	@Test(enabled = false)
	public void basic_locators_by_repeater_should_find_many_rows_by_partial_match() {
		FluentWebDriver fwd = new FluentWebDriver(driver);

		driver.get("http://localhost:8080/index.html#/repeater");

		FluentWebElements spans = fwd.spans(
				ByAngular.repeater("baz in days | filter:'T'").column("baz.initial"));
		spans.getText().shouldBe("TTh");
		spans.get(0).getText().shouldBe("T");
		spans.get(1).getText().shouldBe("Th");

		spans = fwd
				.spans(ByAngular.repeater("baz in days | fil").column("baz.initial"));
		spans.getText().shouldBe("TTh");
		spans.get(0).getText().shouldBe("T");
		spans.get(1).getText().shouldBe("Th");

		try {
			fwd.li(ByAngular.exactRepeater("baz in days | fil").column("baz.initial"))
					.getText().shouldBe("TTh");
			fail("should have barfed");
		} catch (FluentExecutionStopped e) {
			assertThat(e.getMessage(), startsWith(
					"NoSuchElementException during invocation of: ?.li(exactRepeater(baz in days | fil).column(baz.initial))"));
			assertThat(e.getCause().getMessage(), startsWith(
					"exactRepeater(baz in days | fil).column(baz.initial) didn't have any matching elements at this place in the DOM"));
		}

	}

	/*
	  Ported from protractor/spec/basic/locators_spec.js
	  TODO - many more specs in here
	 */
	@Test(enabled = false)
	public void basic_locators_by_repeater_should_find_one_row_by_partial_match() {
		FluentWebDriver fwd = new FluentWebDriver(driver);

		driver.get("http://localhost:8080/index.html#/repeater");

		fwd.li(ByAngular.repeater("baz in days | filter:'T'").row(0)).getText()
				.shouldBe("T");
		fwd.li(ByAngular.repeater("baz in days | filter:'T'").row(1)).getText()
				.shouldBe("Th");

		fwd.li(ByAngular.repeater("baz in days | filt").row(1)).getText()
				.shouldBe("Th");

		try {
			fwd.li(ByAngular.exactRepeater("baz in days | filt").row(1)).getText()
					.shouldBe("T");
			fail("should have barfed");
		} catch (FluentExecutionStopped e) {
			assertThat(e.getMessage(), startsWith(
					"NoSuchElementException during invocation of: ?.li(exactRepeater(baz in days | filt).row(1))"));
			assertThat(e.getCause().getMessage(), startsWith(
					"exactRepeater(baz in days | filt).row(1) didn't have any matching elements at this place in the DOM"));
		}

	}

	/*
	  Ported from protractor/spec/basic/locators_spec.js
	  TODO - many more specs in here
	 */
	@Test(enabled = false)
	public void basic_locators_by_repeater_should_find_many_rows_by_partial_match2() {
		FluentWebDriver fwd = new FluentWebDriver(driver);

		driver.get("http://localhost:8080/index.html#/repeater");

		FluentWebElements lis = fwd
				.lis(ByAngular.repeater("baz in days | filter:'T'"));
		lis.getText().shouldBe("TTh");
		lis.get(0).getText().shouldBe("T");
		lis.get(1).getText().shouldBe("Th");

		lis = fwd.lis(ByAngular.repeater("baz in days | fil"));
		lis.getText().shouldBe("TTh");
		lis.get(0).getText().shouldBe("T");
		lis.get(1).getText().shouldBe("Th");

		try {
			fwd.lis(ByAngular.exactRepeater("baz in days | filt")).getText()
					.shouldBe("T");
			fail("should have barfed");
		} catch (FluentExecutionStopped e) {
			assertThat(e.getMessage(), startsWith(
					"NoSuchElementException during invocation of: ?.lis(exactRepeater(baz in days | filt))"));
			assertThat(e.getCause().getMessage(), startsWith(
					"exactRepeater(baz in days | filt) didn't have any matching elements at this place in the DOM"));
		}

	}

	/*
	  Ported from protractor/spec/basic/locators_spec.js
	  TODO - many more specs in here
	 */
	@Test(enabled = false)
	public void basic_locators_by_repeater_should_find_single_rows_by_partial_match() {
		FluentWebDriver fwd = new FluentWebDriver(driver);

		driver.get("http://localhost:8080/index.html#/repeater");

		fwd.li(ByAngular.repeater("baz in days | filter:'T'")).getText()
				.shouldBe("T");

		fwd.li(
				ByAngular.withRootSelector("[ng-app]").repeater("baz in days | filt"))
				.getText().shouldBe("T");

		try {
			fwd.li(ByAngular.exactRepeater("baz in days | filt")).getText()
					.shouldBe("T");
			fail("should have barfed");
		} catch (FluentExecutionStopped e) {
			assertThat(e.getMessage(), startsWith(
					"NoSuchElementException during invocation of: ?.li(exactRepeater(baz in days | filt))"));
			assertThat(e.getCause().getMessage(), startsWith(
					"exactRepeater(baz in days | filt) didn't have any matching elements at this place in the DOM"));
		}

	}

	/*
	  Ported from protractor/spec/basic/lib_spec.js
	  TODO - many more specs in here
	 */
	@Test(enabled = false)
	public void basic_lib_getLocationAbsUrl_gets_url() {
		FluentWebDriver fwd = new FluentWebDriver(driver);

		driver.get("http://localhost:8080/index.html");

		assertThat(ngWebDriver.getLocationAbsUrl(), endsWith("/form"));

		fwd.link(By.linkText("repeater")).click();

		assertThat(ngWebDriver.getLocationAbsUrl(), endsWith("/repeater"));

	}

}
