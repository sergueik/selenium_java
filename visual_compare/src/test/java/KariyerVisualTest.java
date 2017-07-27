import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;
import ru.yandex.qatools.ashot.Screenshot;

/**
 * Created by onurb on 28-Aug-16.
 */
public class KariyerVisualTest extends BaseTest {

    @Test
    public void kariyerUzmanCssTest () throws Exception {
        //Handle popup
        handlePopup(".ui-dialog-titlebar-close");

        //Close banner
        closeBanner();

        //Declare UZMAN photo section
        WebElement uzmanPhotoSection = driver.findElement(By.cssSelector(".item.uzman>a"));

        //Unhide Text (It is Changing A lot)
        unhideElement("document.getElementsByClassName('count')[0].style.display='none';");

        //Move To Operation
        moveToElement(uzmanPhotoSection);

        //Wait for 2 second for violet color animation
        Thread.sleep(2000);

        //Take ScreenShot with AShot
        Screenshot uzmanScreenShot = takeScreenshot(uzmanPhotoSection);

        //Write actual screenshot to the actual screenshot path
        writeScreenshotToFolder(uzmanScreenShot);

        
        //Do image comparison
        doComparison(uzmanScreenShot);
    }

    //Close Driver
    @AfterClass
    public void quitDriver() {
        driver.quit();
    }
}
