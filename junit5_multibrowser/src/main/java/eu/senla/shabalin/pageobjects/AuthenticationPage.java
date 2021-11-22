package eu.senla.shabalin.pageobjects;

import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.WebDriverRunner.url;

public class AuthenticationPage implements PageObject{
    protected String authenticationFailedUrl = "index.php?controller=authentication";
    protected String accountPage = "index.php?controller=my-account";
    private SelenideElement alreadyRegisteredEmailField = $("#login_form #email");
    private SelenideElement alreadyRegisteredPasswordField = $("#login_form #passwd");
    private SelenideElement alreadyRegisteredSignInButton = $(By.id("SubmitLogin"));
    private SelenideElement authenticationFailedAlert = $(By.xpath("//div[contains(@class, 'alert')]//li[text()='Authentication failed.']"));

    public PageObject authenticationWithCredetials(String email, String password) {
        alreadyRegisteredEmailField.sendKeys(email);
        alreadyRegisteredPasswordField.sendKeys(password);
        alreadyRegisteredSignInButton.click();
        if (url().contains(authenticationFailedUrl)) {
            return this;
        } else if(url().contains(accountPage)){
            return new MyAccountPage();
        } else {
            return new CreateOrderPage();
        }
    }

    public boolean isAuthenticationFailedAlertPresent() {
        return authenticationFailedAlert.exists();
    }
}
