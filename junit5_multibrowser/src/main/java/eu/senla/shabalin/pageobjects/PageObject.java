package eu.senla.shabalin.pageobjects;

import static com.codeborne.selenide.WebDriverRunner.url;

public interface PageObject {
	default String getPageUrl() {
		return url();
	}
}
