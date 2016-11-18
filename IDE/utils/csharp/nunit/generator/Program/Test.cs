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
        private int highlight_timeout = 1000;
        private Actions actions;
        private WebDriverWait wait;
        private String baseUrl = "http://suvian.in/selenium";

        [SetUp]
        public void SetupTest()
        {
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
        public void TestFixtureTearDownMethod()
        {
        }

        // explore Selenium WebDriver testing  practice site from https://groups.google.com/forum/#!topic/selenium-users/JXggllPayGE
        [Test]
        public void Test1()
        {
            // Arrange
            driver.Navigate().GoToUrl("http://suvian.in/selenium/1.1link.html");
            // Act
            try
            {
                wait.Until(OpenQA.Selenium.Support.UI.ExpectedConditions.ElementIsVisible(By.CssSelector("div.container div.row div.col-lg-12 div.intro-message a")));
            }
            catch (Exception e)
            {
                verificationErrors.Append(e.Message);
            }

            IWebElement element = driver.FindElement(By.CssSelector("div.container div.row div.col-lg-12 div.intro-message a"));
            // Assert
            Assert.IsTrue(element.Text.IndexOf("Click Here") > -1, element.Text);
            // Act
            element.Click();
            // Wait page to load
            try {
                wait.Until(d => {
                    IWebElement e = d.FindElement(By.ClassName("intro-message"));
                    return (e.Text.IndexOf("Link Successfully clicked") > -1);
                });
            } catch (Exception e) {
                verificationErrors.Append(e.Message);
            }
            
            // Assert
            IWebElement element2 = null;
            String matcher = "(?i:" + "программа на c#" + "|" + "Link Successfully clicked" + ")";
            try {
                wait.Until(d => {
                    ReadOnlyCollection<IWebElement> a = d.FindElements(By.XPath("//div[@class='intro-message']/h3"));
                    element2 = a.First(o =>
                        Regex.IsMatch(o.Text, matcher, RegexOptions.IgnoreCase)
                    );
                    return (element2 != null);
                });
            } catch (Exception e) {
                verificationErrors.Append(e.Message);
            }
            Assert.IsTrue(element2.Text.IndexOf("Link Successfully clicked") > -1, element2.Text);
            
            // the following attempts would all fail
            try {
                wait.Until(d => {
                    IWebElement e = d.FindElement(By.XPath("//div[@class='intro-message']/h3[contains(translate(text(), 'ABCDEFGHIJKLMNOPQRSTUVWXYZАБВГДЕЁЖЗИКЛМНОПРСТУФХЦЧШЩЬЫЪЭЮЯ', 'abcdefghijklmnopqrstuvwxyzабвгдеёжзиклмнопрстуфхцчшщьыъэюя'), 'Link Successfully clicked')]"));
                    return (e.Text.IndexOf("Link Successfully clicked") > -1);
                });
            } catch (Exception e) {
                verificationErrors.Append(e.Message);
            }

            try {
              wait.Until(d => { 
                  ReadOnlyCollection<IWebElement> e = d.FindElements(By.XPath("//div[@class='intro-message']/h3[contains(translate(text(), 'ABCDEFGHIJKLMNOPQRSTUVWXYZАБВГДЕЁЖЗИКЛМНОПРСТУФХЦЧШЩЬЫЪЭЮЯ', 'abcdefghijklmnopqrstuvwxyzабвгдеёжзиклмнопрстуфхцчшщьыъэюя'), 'Link Successfully clicked')]")); 
                  return(e.Count > 0 );
              });
            } catch (Exception e) {
                verificationErrors.Append(e.Message);
            }

            element = driver.FindElement(By.ClassName("intro-message"));
            Assert.IsTrue(element.Text.IndexOf("Link Successfully clicked") > -1, element.Text);
            // 
            element = driver.FindElement(By.XPath("//div[@class='intro-message']/h3")); 
            Assert.IsTrue(element.Text.IndexOf("Link Successfully clicked") > -1, element.Text);
            ReadOnlyCollection<IWebElement>elements;
            elements = driver.FindElements(By.XPath("//div[@class='intro-message']/h3[contains(text(), 'Link Successfully clicked')]"));
            Assert.IsTrue((elements.Count > 0));
            elements = driver.FindElements(By.XPath("//div[@class='intro-message']/h3[contains(translate(text(), 'ABCDEFGHIJKLMNOPQRSTUVWXYZАБВГДЕЁЖЗИКЛМНОПРСТУФХЦЧШЩЬЫЪЭЮЯ', 'abcdefghijklmnopqrstuvwxyzабвгдеёжзиклмнопрстуфхцчшщьыъэюя'), 'Link Successfully clicked')]"));
			// would fail            
			//  Assert.IsTrue((elements.Count > 0));
        }

        [Test]
        public void Test2()
        {
            // Arrange
            driver.Navigate().GoToUrl("http://suvian.in/selenium/1.2text_field.html");
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
        public void Test3()
        {
            // Arrange
            driver.Navigate().GoToUrl("http://suvian.in/selenium/1.3age_plceholder.html");
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
        public void Test4()
        {
            // Arrange
            driver.Navigate().GoToUrl("http://suvian.in/selenium/1.4gender_dropdown.html");
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
        public void Test5()
        {
            // Arrange
            driver.Navigate().GoToUrl("http://suvian.in/selenium/1.5married_radio.html");
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
        public void Test6()
        {
            // Arrange
            driver.Navigate().GoToUrl("http://suvian.in/selenium/1.6checkbox.html");
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
        public void Test7()
        {
            // Arrange
            driver.Navigate().GoToUrl("http://suvian.in/selenium/1.7button.html");
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
        public void Test8()
        {
            // Arrange
            driver.Navigate().GoToUrl("http://suvian.in/selenium/1.8copyAndPasteText.html");
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
        public void Test9()
        {
            // Arrange
            driver.Navigate().GoToUrl("http://suvian.in/selenium/1.9copyAndPasteTextAdvanced.html");
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
        public void Test10()
        {
            // Arrange
            driver.Navigate().GoToUrl("http://suvian.in/selenium/1.10selectElementFromDD.html");
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
        public void Test17()
        {
            // Arrange
            driver.Navigate().GoToUrl("http://suvian.in/selenium/2.7waitUntil.html");
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
        public void Test18()
        {
            // Arrange
            driver.Navigate().GoToUrl("http://suvian.in/selenium/2.8progressBar.html");
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
        public void Test19()
        {
            // Arrange
            driver.Navigate().GoToUrl("http://suvian.in/selenium/2.9greenColorBlock.html");
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
        public void Test20()
        {
            // Arrange
            driver.Navigate().GoToUrl("http://suvian.in/selenium/2.10dragAndDrop.html");
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
        public void Test21()
        {
            // Arrange
            driver.Navigate().GoToUrl("http://suvian.in/selenium/3.1fileupload.html");
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
        public void Test22()
        {
            // Arrange
            driver.Navigate().GoToUrl("http://suvian.in/selenium/3.2dragAndDrop.html");
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
        public void Test23()
        {
            // Arrange
            driver.Navigate().GoToUrl("http://suvian.in/selenium/3.3javaemail.html");
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
        public void Test24()
        {
            // Arrange
            driver.Navigate().GoToUrl("http://suvian.in/selenium/3.4readWriteExcel.html");
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
        public void Test25()
        {
            // Arrange
            driver.Navigate().GoToUrl("http://suvian.in/selenium/3.5cricketScorecard.html");
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
        public void Test26()
        {
            // Arrange
            driver.Navigate().GoToUrl("http://suvian.in/selenium/3.6copyTextFromTextField.html");
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
        public void Test27()
        {
            // Arrange
            driver.Navigate().GoToUrl("http://suvian.in/selenium/3.7correspondingRadio.html");
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
        public void Test28()
        {
            // Arrange
            driver.Navigate().GoToUrl("http://suvian.in/selenium/3.8screeshotToEmail.html");
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
        public void Test29()
        {
            // Arrange
            driver.Navigate().GoToUrl("http://suvian.in/selenium/3.9FacebookTest.html");
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
        public void Test30()
        {
            // Arrange
            driver.Navigate().GoToUrl("http://suvian.in/selenium/3.10select1stFriday.html");
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
            }
            catch (Exception e)
            {
                verificationErrors.Append(e.Message);
            }

            IWebElement element = driver.FindElement(By.Id("searchInput"));
            element.SendKeys(searchTest);
            element.SendKeys(OpenQA.Selenium.Keys.ArrowDown);
            element.Submit();
            // Assert
            Assert.IsTrue(driver.Title.IndexOf(searchTest) > -1, driver.Title);
        }
    }
}
