import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Created by onurb on 06-Feb-17.
 */
public class JSWaiter {

    public WebDriverWait wait;

    public JSWaiter (WebDriverWait wait) {
        this.wait = wait;
    }

    public void waitJS () {
        //Wait for Javascript to load
		ExpectedCondition<Boolean> jsLoad = driver -> ((JavascriptExecutor) driver)
				.executeScript("return document.readyState").toString()
				.equals("complete");

		// JQuery Wait
		ExpectedCondition<Boolean> jQueryLoad = driver -> ((Long) ((JavascriptExecutor) driver)
				.executeScript("return jQuery.active") == 0);

		wait.until(jsLoad);
		wait.until(jQueryLoad);
    }
}
