import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import ru.yandex.qatools.ashot.Screenshot;

public class VisualTest extends BaseTest {

	@Test
	public void kariyerUzmanCssTest() throws Exception {
		// Handle popup
		handlePopup(".ui-dialog-titlebar-close");

		// Close banner
		closeBanner();

		WebElement uzmanPhotoSection = driver
				.findElement(By.cssSelector(".item.uzman>a"));

		// Unhide Text which is changing A lot
		unhideElement(
				"document.getElementsByClassName('count')[0].style.display='none';");

		// Move To Operation
		moveToElement(uzmanPhotoSection);

		// Wait for 2 second for violet color animation
		Thread.sleep(2000);

		Screenshot uzmanScreenShot = takeScreenshot(uzmanPhotoSection);

		writeScreenshotToFolder(uzmanScreenShot);

		doComparison(uzmanScreenShot);
	}

	@AfterClass
	public void quitDriver() {
		driver.quit();
	}
}
