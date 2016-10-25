using System;
using System.Linq.Expressions;
using System.Text;
using System.Collections.Generic;
using System.Linq;
using Microsoft.VisualStudio.TestTools.UnitTesting;
using Microsoft.Activities.UnitTesting;
using Moq;

using OpenQA.Selenium;
using OpenQA.Selenium.Remote;
using OpenQA.Selenium.Support.UI;
using OpenQA.Selenium.Firefox;
using OpenQA.Selenium.IE;

namespace SeleniumTests
{
    [TestClass]
    public class SeleniumTest
    {
        private RemoteWebDriver driver;
        private StringBuilder verificationErrors;

        [TestCleanup()]
        public void MyTestCleanup()
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

        [TestInitialize()]
        public void MyTestInitialize()
        {
            verificationErrors = new StringBuilder();
            DesiredCapabilities capability = DesiredCapabilities.Firefox();
            driver = new RemoteWebDriver(new Uri("http://localhost:4444/wd/hub"), capability);
            driver.Manage().Timeouts().ImplicitlyWait(TimeSpan.FromSeconds(20));
            driver.Manage().Timeouts().SetPageLoadTimeout(TimeSpan.FromSeconds(50));
            driver.Manage().Window.Maximize();
            driver.Navigate();
        }

        [TestMethod]
        public void SkeletonTestMethod()
        {
            // Arrange
            driver.Url = "http://www.wikipedia.org";
            driver.Navigate().GoToUrl(driver.Url);
            // Act
            // WebDriverWait wait = new WebDriverWait(driver, TimeSpan.FromSeconds(10)) ;
            //Find the Element and create an object so we can use it
            IWebElement queryBox = driver.FindElement(By.Id("searchInput"));
            //Work with the Element that's on the page
            queryBox.SendKeys("Carnival Cruise Lines");
            queryBox.SendKeys(Keys.ArrowDown);
            queryBox.Submit();
            // Assert
            Assert.IsTrue(driver.Title.IndexOf("Carnival Cruise Lines") > -1, driver.Title);
        }
    }
}
