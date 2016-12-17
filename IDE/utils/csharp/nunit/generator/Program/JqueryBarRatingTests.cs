using System;
using System.Text;
using System.Text.RegularExpressions;
using System.Collections.Generic;
using System.Globalization;
using System.Collections.ObjectModel;
using System.IO;
using System.Threading;
using System.Linq;
using System.Drawing;
using System.Windows.Forms;

using NUnit.Framework;

using OpenQA.Selenium;
using OpenQA.Selenium.Interactions;
using OpenQA.Selenium.Support.UI;
using OpenQA.Selenium.Chrome;
using OpenQA.Selenium.Firefox;
using OpenQA.Selenium.IE;
using OpenQA.Selenium.PhantomJS;

namespace SeleniumTests
{
	[TestFixture]
	public class JqueryBarRatingTests
	{
		private IWebDriver driver;
		private StringBuilder verificationErrors;
		private const int window_width = 800;
		private const int window_height = 600;
		private const int script_wait_seconds = 60;
		private const int wait_seconds = 3;
		private const int highlight_timeout = 1000;
		private Actions actions;
		private WebDriverWait wait;
		private String baseUrl = "http://antenna.io/demo/jquery-bar-rating/examples/";

		[SetUp]
		public void SetupTest()
		{
			verificationErrors = new StringBuilder();
			// driver = new FirefoxDriver();
			driver = new ChromeDriver(System.IO.Directory.GetCurrentDirectory());

			driver.Manage().Cookies.DeleteAllCookies();
			driver.Manage().Window.Size = new System.Drawing.Size(window_width, window_height);
			driver.Manage().Timeouts().SetScriptTimeout(TimeSpan.FromSeconds(script_wait_seconds));
			driver.Manage().Timeouts().ImplicitlyWait(TimeSpan.FromSeconds(20));
			driver.Manage().Timeouts().SetPageLoadTimeout(TimeSpan.FromSeconds(50));
			driver.Navigate().GoToUrl(baseUrl);
			wait = new WebDriverWait(driver, TimeSpan.FromSeconds(wait_seconds));
			actions = new Actions(driver);
		}

		[TearDown]
		public void TeardownTest()
		{
			try {
				driver.Quit();
			} catch (Exception) {
				// Ignore errors if unable to close the browser
			}
			Assert.AreEqual("", verificationErrors.ToString());
		}

		[TestFixtureSetUp]
		public void TestFixtureSetUpMethod()
		{

		}

		[TestFixtureTearDown]
		public void TestFixtureTearDownMethod()
		{
		}

		// explore Selenium WebDriver testing  practice site from https://groups.google.com/forum/#!topic/selenium-users/JXggllPayGE
		[Test]
		public void Test1()
		{
			// Arrange
			String barSelector = "div.examples div.row div.col div.box-example-reversed";
			wait.Until(ExpectedConditions.ElementIsVisible(By.CssSelector(barSelector)));
			
			IWebElement barElement = driver.FindElement(By.CssSelector(barSelector));
			actions
				.MoveToElement(barElement);
			Highlight(driver, barElement);
			Assert.IsTrue(barElement
				.FindElements(By.XPath("//a[@data-rating-value]")).Count > 7);
		
			
			ReadOnlyCollection<IWebElement> ratingElements = 
				barElement.FindElements(By.XPath(".//a[@data-rating-value]"));
			Assert.IsTrue(ratingElements.Count > 0);

			Dictionary<String, IWebElement> ratings = ratingElements.ToDictionary(o => o.GetAttribute("data-rating-text"), 
				                                          o => o);

			
			foreach (var item in ratings) {
				// Act
				actions.MoveToElement(item.Value).Build().Perform();
				Highlight(driver, item.Value);
				Thread.Sleep(1000);
				// Assert			
				StringAssert.AreEqualIgnoringCase(barElement.FindElement(By.XPath(".//*[contains(@class, 'br-current-rating') and contains(@class ,'br-selected')]")).Text, item.Key);
			}
		}

		public void Highlight(IWebDriver driver, IWebElement element)
		{
			((IJavaScriptExecutor)driver).ExecuteScript("arguments[0].style.border='3px solid yellow'", element);
			Thread.Sleep(highlight_timeout);
			((IJavaScriptExecutor)driver).ExecuteScript("arguments[0].style.border=''", element);
		}
	}
}
