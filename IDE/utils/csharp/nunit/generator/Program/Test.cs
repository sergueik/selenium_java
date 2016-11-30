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
    public class Test
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
        private String baseUrl = "http://suvian.in/selenium";

        [SetUp]
        public void SetupTest() {
            verificationErrors = new StringBuilder();
            // driver = new ChromeDriver();
            driver = new FirefoxDriver();

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
        public void TeardownTest() {
            try {
                driver.Quit();
            } catch (Exception) {
                // Ignore errors if unable to close the browser
            }
            Assert.AreEqual("", verificationErrors.ToString());
        }

        [TestFixtureSetUp]
        public void TestFixtureSetUpMethod() {

        }

        [TestFixtureTearDown]
        public void TestFixtureTearDownMethod() {
        }

        // explore Selenium WebDriver testing  practice site from https://groups.google.com/forum/#!topic/selenium-users/JXggllPayGE
        [Test]
        public void Test1()
        {
            // Arrange
            driver.Navigate().GoToUrl("http://suvian.in/selenium/1.1link.html");
            // Act
            try {
                wait.Until(ExpectedConditions.ElementIsVisible(By.CssSelector("div.container div.row div.col-lg-12 div.intro-message a")));
            } catch (Exception e) {
                verificationErrors.Append(e.Message);
            }

	    	// use http://csslint.net/ to write better css selectors 
            IWebElement element = driver.FindElement(By.CssSelector(".container .row .col-lg-12 .intro-message a"));
            // Assert
            Assert.IsTrue(element.Text.IndexOf("Click Here") > -1, element.Text);
            // Act
            element.Click();
            // Wait page to load
            string linkText = "Link Successfully clicked";
            try {
                wait.Until(d => {
                    IWebElement e = d.FindElement(By.ClassName("intro-message"));
                    return (e.Text.IndexOf(linkText) > -1);
                });
            } catch (Exception e) {
                verificationErrors.Append(e.Message);
            }

            // Assert
            element = driver.FindElement(By.ClassName("intro-message"));
            Assert.IsTrue(element.Text.IndexOf(linkText) > -1, element.Text);
            // Asserts of case-insensitive link search - many will fail
            element = null;
            String matcher = "(?i:" + "программа на c#" + "|" + linkText + ")";
            try {
                wait.Until(d => {
                    element = d.FindElements(By.XPath("//div[@class='intro-message']/h3")).First(o =>
                        Regex.IsMatch(o.Text, matcher, RegexOptions.IgnoreCase)
                    );
                    return (element != null);
                });
            } catch (Exception e) {
                verificationErrors.Append(e.Message);
            }
            Assert.IsTrue(element.Text.IndexOf(linkText) > -1, element.Text);

            // the following attempts would all fail
            try {
                wait.Until(d => {
                    IWebElement e = d.FindElement(By.XPath(
                        String.Format("//div[@class='intro-message']/h3[contains(translate(text(), 'ABCDEFGHIJKLMNOPQRSTUVWXYZАБВГДЕЁЖЗИКЛМНОПРСТУФХЦЧШЩЬЫЪЭЮЯ', 'abcdefghijklmnopqrstuvwxyzабвгдеёжзиклмнопрстуфхцчшщьыъэюя'), '{0}')]", linkText)));
                    return (e.Text.IndexOf(linkText) > -1);
                });
            } catch (Exception e) {
                verificationErrors.Append(e.Message);
            }

            try {
                wait.Until(d => {
                    ReadOnlyCollection<IWebElement> e = d.FindElements(By.XPath(String.Format("//div[@class='intro-message']/h3[contains(translate(text(), 'ABCDEFGHIJKLMNOPQRSTUVWXYZАБВГДЕЁЖЗИКЛМНОПРСТУФХЦЧШЩЬЫЪЭЮЯ', 'abcdefghijklmnopqrstuvwxyzабвгдеёжзиклмнопрстуфхцчшщьыъэюя'), '{0}')]", linkText)));
                    return (e.Count > 0);
                });
            } catch (Exception e) {
                verificationErrors.Append(e.Message);
            }
            element = driver.FindElement(By.XPath("//div[@class='intro-message']/h3"));
            Highlight(driver,element);
            Assert.IsTrue(element.Text.IndexOf(linkText) > -1, element.Text);
            ReadOnlyCollection<IWebElement> elements;
            elements = driver.FindElements(By.XPath(String.Format("//div[@class='intro-message']/h3[contains(text(), 'Link Successfully clicked')]", linkText)));
            Assert.IsTrue((elements.Count > 0));
            elements = driver.FindElements(By.XPath("//div[@class='intro-message']/h3[contains(translate(text(), 'ABCDEFGHIJKLMNOPQRSTUVWXYZАБВГДЕЁЖЗИКЛМНОПРСТУФХЦЧШЩЬЫЪЭЮЯ', 'abcdefghijklmnopqrstuvwxyzабвгдеёжзиклмнопрстуфхцчшщьыъэюя'), 'Link Successfully clicked')]"));
            try {
                // would fail            
                Assert.IsTrue((elements.Count > 0));
            } catch (Exception e) {
                verificationErrors.Append(e.Message);
            }
        }

        [Test]
        public void Test2()
        {
        	String text = "test";
            // Arrange
            driver.Navigate().GoToUrl("http://suvian.in/selenium/1.2text_field.html");
            // Wait to load
            try {
                wait.Until(ExpectedConditions.ElementIsVisible(By.Id("namefield")));
            } catch (Exception e) {
                verificationErrors.Append(e.Message);
            }
            // Act
            IWebElement element = driver.FindElement(By.Id("namefield"));
            element.SendKeys(text);
            Highlight(driver, element);
            // Assert
            Assert.IsTrue(element.GetAttribute("value").IndexOf(text) > -1, element.GetAttribute("value"));
        }

        [Test]
        public void Test3() {
            // Arrange
            driver.Navigate().GoToUrl("http://suvian.in/selenium/1.3age_plceholder.html");
            // Wait to load
            try {
                wait.Until(ExpectedConditions.ElementIsVisible(By.Id("agefield")));
            } catch (Exception e) {
                verificationErrors.Append(e.Message);
            }
            // Act
        	String text = "4242424242";
        	IWebElement element = driver.FindElement(By.Id("agefield"));
        	int maxlength = 0;
            int.TryParse(element.GetAttribute("maxlength"), out maxlength);
            element.Clear();
            element.SendKeys(text.Substring(0, maxlength));
            // Assert
            Assert.IsTrue(text.IndexOf(element.GetAttribute("value")) > -1, element.GetAttribute("value"));
        }

        [Test]
        public void Test4()
        {
            // Arrange
            driver.Navigate().GoToUrl("http://suvian.in/selenium/1.4gender_dropdown.html");
            try {
                wait.Until(ExpectedConditions.ElementIsVisible(By.CssSelector(".intro-header .container .row .col-lg-12 .intro-message select")));
            } catch (Exception e) {
                verificationErrors.Append(e.Message);
            }
            // Act
            IWebElement element = driver.FindElement(By.CssSelector(".intro-header .container .row .col-lg-12 .intro-message select"));
            SelectElement selectElement = new SelectElement(element);
            // Assert
            try {
	            selectElement.SelectByText("Male"); // case sensitive
	            Assert.IsNotNull(selectElement.SelectedOption);
            } catch (Exception e) {
                verificationErrors.Append(e.Message);
            }
            IWebElement option = null;
        	try {
            	option = selectElement.Options.First( o => o.Text.Equals("male", StringComparison.InvariantCultureIgnoreCase));
            } catch (Exception e) {
                verificationErrors.Append(e.Message);
            }

            Assert.IsNotNull(option);
            selectElement.SelectByIndex(1);
            StringAssert.AreEqualIgnoringCase("male", selectElement.SelectedOption.Text );
        }

        [Test]
        public void Test5()
        {
            // Arrange
            driver.Navigate().GoToUrl("http://suvian.in/selenium/1.5married_radio.html");
            try {            	
                wait.Until(ExpectedConditions.ElementIsVisible(By.XPath("//div[@class='intro-header']/div[@class='container']/div[@class='row']/div[@class='col-lg-12']/div[@class='intro-message']/h3")));
                // Are you married ?
            } catch (Exception e) {
                verificationErrors.Append(e.Message);
            }

            // Act..
            // NOTE: Exercise page lacks formatting to allow one distinguish yes from no options by label text in a "Selenium way"
            // inspect the raw form text to determine option value to select
            String status = "Yes";
            String line =  Regex.Split(driver.FindElement(By.XPath("//div[@class='intro-header']/div[@class='container']/div[@class='row']/div[@class='col-lg-12']/div[@class='intro-message']/form")).GetAttribute("outerHTML"), "<br/?>").First(o => o.Contains(status));
        	String matcher = "value=\\\"([^\"]*)\\\"";
        	// NOTE: groups index starts with a 1
        	String value = null;
        	if ( Regex.IsMatch(line, matcher, RegexOptions.IgnoreCase)) {
        		MatchCollection Matches = new Regex(matcher).Matches(line);
	            foreach (Match Match in Matches) {
	                if (Match.Length != 0) {
	                    foreach (Capture Capture in Match.Groups[1].Captures) {
	                        if (value == null) {
	                    		value = Capture.ToString();
	            			}
	            		}
	            	}
            	}

        	}
        	Console.Error.WriteLine(String.Format("value=>\"{0}\"", value));
            // locate the needed radio button option specifying the option value
            IWebElement element = driver.FindElement(By.XPath(String.Format("//div[@class='intro-header']/div[@class='container']/div[@class='row']/div[@class='col-lg-12']/div[@class='intro-message']/form/input[@name='married'][@value='{0}']", value)));
            Highlight(driver, element);
            element.SendKeys(OpenQA.Selenium.Keys.Space);
            Assert.IsTrue(element.Selected);
        }

        [Test]
        public void Test6()
        {
            // Arrange
            driver.Navigate().GoToUrl("http://suvian.in/selenium/1.6checkbox.html");
            // Act
            try
            {
                // wait.Until(ExpectedConditions.ElementIsVisible(By.Id("searchInput")));
            }   catch (Exception e) {
                verificationErrors.Append(e.Message);
            }

            // IWebElement element = driver.FindElement(By.Id("searchInput"));
            // Assert
            // Assert.IsTrue(driver.Title.IndexOf(searchTest) > -1, driver.Title);
        }

        [Test]
        public void Test7()
        {
            // Arrange
            driver.Navigate().GoToUrl("http://suvian.in/selenium/1.7button.html");
            // Act
            try
            {
                // wait.Until(ExpectedConditions.ElementIsVisible(By.Id("searchInput")));
            }   catch (Exception e) {
                verificationErrors.Append(e.Message);
            }

            // IWebElement element = driver.FindElement(By.Id("searchInput"));
            // Assert
            // Assert.IsTrue(driver.Title.IndexOf(searchTest) > -1, driver.Title);
        }

        [Test]
        public void Test8()
        {
            // Arrange
            driver.Navigate().GoToUrl("http://suvian.in/selenium/1.8copyAndPasteText.html");
            // Act
            try
            {
                // wait.Until(ExpectedConditions.ElementIsVisible(By.Id("searchInput")));
            }   catch (Exception e) {
                verificationErrors.Append(e.Message);
            }

            // IWebElement element = driver.FindElement(By.Id("searchInput"));
            // Assert
            // Assert.IsTrue(driver.Title.IndexOf(searchTest) > -1, driver.Title);
        }

        [Test]
        public void Test9()
        {
            // Arrange
            driver.Navigate().GoToUrl("http://suvian.in/selenium/1.9copyAndPasteTextAdvanced.html");
            // Act
            try
            {
                // wait.Until(ExpectedConditions.ElementIsVisible(By.Id("searchInput")));
            }   catch (Exception e) {
                verificationErrors.Append(e.Message);
            }

            // IWebElement element = driver.FindElement(By.Id("searchInput"));
            // Assert
            // Assert.IsTrue(driver.Title.IndexOf(searchTest) > -1, driver.Title);
        }

        [Test]
        public void Test10() {
            // Arrange
            driver.Navigate().GoToUrl("http://suvian.in/selenium/1.10selectElementFromDD.html");
            // Act
            try {
                wait.Until(ExpectedConditions.ElementIsVisible(By.CssSelector("div.intro-header div.container div.row div.col-lg-12 div.intro-message div.dropdown button.dropbtn")));
            } catch (Exception e) {
                verificationErrors.Append(e.Message);
            }

            IWebElement element = driver.FindElement(By.CssSelector(".intro-header .container .row .col-lg-12 .intro-message .dropdown button.dropbtn"));
            // Assert
            StringAssert.AreEqualIgnoringCase( "Click Me to open a Menu" , element.Text);
            element.Click();
            
            try {
                wait.Until(ExpectedConditions.ElementIsVisible(By.Id("myDropdown")));
            } catch (Exception e) {
                verificationErrors.Append(e.Message);
            }
            
            IWebElement  option = null;
            String matcher = "(?i:option 2)";
            option = driver.FindElement(By.Id("myDropdown")).FindElements(By.TagName("a")).First(o => Regex.IsMatch(o.Text, matcher, RegexOptions.IgnoreCase));
            Assert.IsNotNull(option);
        }

        [Test]
        public void Test11()
        {
            // Arrange
            driver.Navigate().GoToUrl("http://suvian.in/selenium/2.1alert.html");
            // Act
            try
            {
                // wait.Until(ExpectedConditions.ElementIsVisible(By.Id("searchInput")));
            }
            catch (Exception e)
            {
                verificationErrors.Append(e.Message);
            }

            // IWebElement element = driver.FindElement(By.Id("searchInput"));
            // Assert
            // Assert.IsTrue(driver.Title.IndexOf(searchTest) > -1, driver.Title);
        }

        [Test]
        public void Test12()
        {
            // Arrange
            driver.Navigate().GoToUrl("http://suvian.in/selenium/2.2browserPopUp.html");
            // Act
            try
            {
                // wait.Until(ExpectedConditions.ElementIsVisible(By.Id("searchInput")));
            }
            catch (Exception e)
            {
                verificationErrors.Append(e.Message);
            }

            // IWebElement element = driver.FindElement(By.Id("searchInput"));
            // Assert
            // Assert.IsTrue(driver.Title.IndexOf(searchTest) > -1, driver.Title);
        }

        [Test]
        public void Test13()
        {
            // Arrange
            driver.Navigate().GoToUrl("http://suvian.in/selenium/2.3frame.html");
            // Act
            try
            {
                // wait.Until(ExpectedConditions.ElementIsVisible(By.Id("searchInput")));
            }
            catch (Exception e)
            {
                verificationErrors.Append(e.Message);
            }

            // IWebElement element = driver.FindElement(By.Id("searchInput"));
            // Assert
            // Assert.IsTrue(driver.Title.IndexOf(searchTest) > -1, driver.Title);
        }

        [Test]
        public void Test14()
        {
            // Arrange
            driver.Navigate().GoToUrl("http://suvian.in/selenium/2.4mouseHover.html");
            // Act
            try
            {
                // wait.Until(ExpectedConditions.ElementIsVisible(By.Id("searchInput")));
            }
            catch (Exception e)
            {
                verificationErrors.Append(e.Message);
            }

            // IWebElement element = driver.FindElement(By.Id("searchInput"));
            // Assert
            // Assert.IsTrue(driver.Title.IndexOf(searchTest) > -1, driver.Title);
        }

        [Test]
        public void Test15()
        {
            // Arrange
            driver.Navigate().GoToUrl("http://suvian.in/selenium/2.5resize.html");
            // Act
            try
            {
                // wait.Until(ExpectedConditions.ElementIsVisible(By.Id("searchInput")));
            }
            catch (Exception e)
            {
                verificationErrors.Append(e.Message);
            }

            // IWebElement element = driver.FindElement(By.Id("searchInput"));
            // Assert
            // Assert.IsTrue(driver.Title.IndexOf(searchTest) > -1, driver.Title);
        }

        [Test]
        public void Test16()
        {
            // Arrange
            driver.Navigate().GoToUrl("http://suvian.in/selenium/2.6liCount.html");
            // Act
            try
            {
                // wait.Until(ExpectedConditions.ElementIsVisible(By.Id("searchInput")));
            }
            catch (Exception e)
            {
                verificationErrors.Append(e.Message);
            }

            // IWebElement element = driver.FindElement(By.Id("searchInput"));
            // Assert
            // Assert.IsTrue(driver.Title.IndexOf(searchTest) > -1, driver.Title);
        }

        [Test]
        public void Test17() {
            // Arrange
            driver.Navigate().GoToUrl("http://suvian.in/selenium/2.7waitUntil.html");
            // Act
            try  {
                // wait.Until(ExpectedConditions.ElementIsVisible(By.Id("searchInput")));
            }   catch (Exception e) {
                verificationErrors.Append(e.Message);
            }

            // IWebElement element = driver.FindElement(By.Id("searchInput"));
            // Assert
            // Assert.IsTrue(driver.Title.IndexOf(searchTest) > -1, driver.Title);
        }

        [Test]
        public void Test18()
        {
            // Arrange
            driver.Navigate().GoToUrl("http://suvian.in/selenium/2.8progressBar.html");
            // Act
            try
            {
                // wait.Until(ExpectedConditions.ElementIsVisible(By.Id("searchInput")));
            }   catch (Exception e) {
                verificationErrors.Append(e.Message);
            }

            // IWebElement element = driver.FindElement(By.Id("searchInput"));
            // Assert
            // Assert.IsTrue(driver.Title.IndexOf(searchTest) > -1, driver.Title);
        }

        [Test]
        public void Test19()
        {
            // Arrange
            driver.Navigate().GoToUrl("http://suvian.in/selenium/2.9greenColorBlock.html");
            // Act
            try
            {
                // wait.Until(ExpectedConditions.ElementIsVisible(By.Id("searchInput")));
            }   catch (Exception e) {
                verificationErrors.Append(e.Message);
            }

            // IWebElement element = driver.FindElement(By.Id("searchInput"));
            // Assert
            // Assert.IsTrue(driver.Title.IndexOf(searchTest) > -1, driver.Title);
        }

        [Test]
        public void Test20()
        {
            // Arrange
            driver.Navigate().GoToUrl("http://suvian.in/selenium/2.10dragAndDrop.html");
            // Act
            try
            {
                // wait.Until(ExpectedConditions.ElementIsVisible(By.Id("searchInput")));
            }   catch (Exception e) {
                verificationErrors.Append(e.Message);
            }

            // IWebElement element = driver.FindElement(By.Id("searchInput"));
            // Assert
            // Assert.IsTrue(driver.Title.IndexOf(searchTest) > -1, driver.Title);
        }

        [Test]
        public void Test21()
        {
            // Arrange
            driver.Navigate().GoToUrl("http://suvian.in/selenium/3.1fileupload.html");
            // Act
            try
            {
                // wait.Until(ExpectedConditions.ElementIsVisible(By.Id("searchInput")));
            }   catch (Exception e) {
                verificationErrors.Append(e.Message);
            }

            // IWebElement element = driver.FindElement(By.Id("searchInput"));
            // Assert
            // Assert.IsTrue(driver.Title.IndexOf(searchTest) > -1, driver.Title);
        }

        [Test]
        public void Test22()
        {
            // Arrange
            driver.Navigate().GoToUrl("http://suvian.in/selenium/3.2dragAndDrop.html");
            // Act
            try
            {
                // wait.Until(ExpectedConditions.ElementIsVisible(By.Id("searchInput")));
            }   catch (Exception e) {
                verificationErrors.Append(e.Message);
            }

            // IWebElement element = driver.FindElement(By.Id("searchInput"));
            // Assert
            // Assert.IsTrue(driver.Title.IndexOf(searchTest) > -1, driver.Title);
        }

        [Test]
        public void Test23()
        {
            // Arrange
            driver.Navigate().GoToUrl("http://suvian.in/selenium/3.3javaemail.html");
            // Act
            try
            {
                // wait.Until(ExpectedConditions.ElementIsVisible(By.Id("searchInput")));
            }   catch (Exception e) {
                verificationErrors.Append(e.Message);
            }

            // IWebElement element = driver.FindElement(By.Id("searchInput"));
            // Assert
            // Assert.IsTrue(driver.Title.IndexOf(searchTest) > -1, driver.Title);
        }

        [Test]
        public void Test24()
        {
            // Arrange
            driver.Navigate().GoToUrl("http://suvian.in/selenium/3.4readWriteExcel.html");
            // Act
            try
            {
                // wait.Until(ExpectedConditions.ElementIsVisible(By.Id("searchInput")));
            }   catch (Exception e) {
                verificationErrors.Append(e.Message);
            }

            // IWebElement element = driver.FindElement(By.Id("searchInput"));
            // Assert
            // Assert.IsTrue(driver.Title.IndexOf(searchTest) > -1, driver.Title);
        }

        [Test]
        public void Test25()
        {
            // Arrange
            driver.Navigate().GoToUrl("http://suvian.in/selenium/3.5cricketScorecard.html");
            // Act
            try
            {
                // wait.Until(ExpectedConditions.ElementIsVisible(By.Id("searchInput")));
            }   catch (Exception e) {
                verificationErrors.Append(e.Message);
            }

            // IWebElement element = driver.FindElement(By.Id("searchInput"));
            // Assert
            // Assert.IsTrue(driver.Title.IndexOf(searchTest) > -1, driver.Title);
        }

        [Test]
        public void Test26()
        {
            // Arrange
            driver.Navigate().GoToUrl("http://suvian.in/selenium/3.6copyTextFromTextField.html");
            // Act
            try
            {
                // wait.Until(ExpectedConditions.ElementIsVisible(By.Id("searchInput")));
            }   catch (Exception e) {
                verificationErrors.Append(e.Message);
            }

            // IWebElement element = driver.FindElement(By.Id("searchInput"));
            // Assert
            // Assert.IsTrue(driver.Title.IndexOf(searchTest) > -1, driver.Title);
        }

        [Test]
        public void Test27()
        {
            // Arrange
            driver.Navigate().GoToUrl("http://suvian.in/selenium/3.7correspondingRadio.html");
            // Act
            try
            {
                // wait.Until(ExpectedConditions.ElementIsVisible(By.Id("searchInput")));
            }   catch (Exception e) {
                verificationErrors.Append(e.Message);
            }

            // IWebElement element = driver.FindElement(By.Id("searchInput"));
            // Assert
            // Assert.IsTrue(driver.Title.IndexOf(searchTest) > -1, driver.Title);
        }

        [Test]
        public void Test28()
        {
            // Arrange
            driver.Navigate().GoToUrl("http://suvian.in/selenium/3.8screeshotToEmail.html");
            // Act
            try
            {
                // wait.Until(ExpectedConditions.ElementIsVisible(By.Id("searchInput")));
            }   catch (Exception e) {
                verificationErrors.Append(e.Message);
            }

            // IWebElement element = driver.FindElement(By.Id("searchInput"));
            // Assert
            // Assert.IsTrue(driver.Title.IndexOf(searchTest) > -1, driver.Title);
        }

        [Test]
        public void Test29()
        {
            // Arrange
            driver.Navigate().GoToUrl("http://suvian.in/selenium/3.9FacebookTest.html");
            // Act
            try
            {
                // wait.Until(ExpectedConditions.ElementIsVisible(By.Id("searchInput")));
            }   catch (Exception e) {
                verificationErrors.Append(e.Message);
            }

            // IWebElement element = driver.FindElement(By.Id("searchInput"));
            // Assert
            // Assert.IsTrue(driver.Title.IndexOf(searchTest) > -1, driver.Title);
        }

        [Test]
        public void Test30()
        {
            // Arrange
            driver.Navigate().GoToUrl("http://suvian.in/selenium/3.10select1stFriday.html");
            // Act
            try
            {
                // wait.Until(ExpectedConditions.ElementIsVisible(By.Id("searchInput")));
            }   catch (Exception e) {
                verificationErrors.Append(e.Message);
            }

            // IWebElement element = driver.FindElement(By.Id("searchInput"));
            // Assert
            // Assert.IsTrue(driver.Title.IndexOf(searchTest) > -1, driver.Title);
        }

        [Test]
        public void SkeletonTest()
        {
            // Arrange
            String url = "http://www.wikipedia.org";
            String searchTest = "Carnival Cruise Lines";
            driver.Navigate().GoToUrl(url);
            // Act
            WebDriverWait wait = new WebDriverWait(driver, TimeSpan.FromSeconds(10));
            try
            {
                wait.Until(ExpectedConditions.ElementIsVisible(By.Id("searchInput")));
            }   catch (Exception e) {
                verificationErrors.Append(e.Message);
            }

            IWebElement element = driver.FindElement(By.Id("searchInput"));
            element.SendKeys(searchTest);
            element.SendKeys(OpenQA.Selenium.Keys.ArrowDown);
            element.Submit();
            // Assert
            Assert.IsTrue(driver.Title.IndexOf(searchTest) > -1, driver.Title);
        }
        
        public void Highlight( IWebDriver driver, IWebElement element) {
	        ((IJavaScriptExecutor)driver).ExecuteScript("arguments[0].style.border='3px solid yellow'", element);
            Thread.Sleep(highlight_timeout);
            ((IJavaScriptExecutor)driver).ExecuteScript("arguments[0].style.border=''", element);
        }
    }
}
