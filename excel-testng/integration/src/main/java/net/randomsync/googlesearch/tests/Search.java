package net.randomsync.googlesearch.tests;

import net.randomsync.googlesearch.BaseTest;
import net.randomsync.googlesearch.pageobjects.HomePage;

import org.openqa.selenium.By;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class Search extends BaseTest{

	@Parameters({ "query" })
	@Test
	public void testGoogleSearch(String query) {
		System.out.println("query=" + query);
		driver.get(testUrl);
		HomePage homePage = PageFactory.initElements(driver, HomePage.class);
		homePage.search(query);
		
		//wait until the search results exist
		driver.findElement(By.id("ires"));

		try {
			Assert.assertEquals(driver.getTitle(),
					query + " - Google Search");
		} catch (Error e) {
			verificationErrors.append(e.toString());
		}

	}

}
