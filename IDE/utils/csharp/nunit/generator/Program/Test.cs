using System;
using System.Text;
using System.Text.RegularExpressions;
using System.Threading;
using System.Linq.Expressions;
using System.Collections.Generic;
using System.Linq;

using NUnit.Framework;

using OpenQA.Selenium;
using OpenQA.Selenium.Remote;
using OpenQA.Selenium.Support.UI;
using OpenQA.Selenium.Firefox;
using OpenQA.Selenium.IE;

namespace SeleniumTests
{
    [TestFixture]
    public class Test
    {
        private RemoteWebDriver driver;
        private StringBuilder verificationErrors;

        [SetUp]
        public void SetupTest()
        {
            verificationErrors = new StringBuilder();
            DesiredCapabilities capability = DesiredCapabilities.Firefox();
            driver = new RemoteWebDriver(new Uri("http://localhost:4444/wd/hub"), capability);

            driver.Manage().Timeouts().ImplicitlyWait(TimeSpan.FromSeconds(20));
            driver.Manage().Timeouts().SetPageLoadTimeout(TimeSpan.FromSeconds(50));
            driver.Manage().Window.Maximize();
            driver.Navigate();
        }

        [TearDown]
        public void TeardownTest()
        {
            try
            {
                driver.Quit();
            }
            catch (Exception)
            {
                // Ignore errors if unable to close the browser
            }
            Assert.AreEqual("", verificationErrors.ToString());
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
            element.SendKeys(Keys.ArrowDown);
            element.Submit();
            // Assert
            Assert.IsTrue(driver.Title.IndexOf(searchTest) > -1, driver.Title);
        }
    }
}
