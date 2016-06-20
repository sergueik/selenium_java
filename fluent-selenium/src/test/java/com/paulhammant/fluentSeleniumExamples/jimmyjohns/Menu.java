package com.paulhammant.fluentSeleniumExamples.jimmyjohns;

import com.paulhammant.fluentSeleniumExamples.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.seleniumhq.selenium.fluent.FluentMatcher;

import static com.paulhammant.fluentSeleniumExamples.jimmyjohns.Home.ngWait;
import static org.seleniumhq.selenium.fluent.FluentBy.attribute;

public class Menu extends BasePage {
    public Menu(FirefoxDriver wd) {
        super(wd);
        div(ngWait(attribute("ng-controller", "MenuController"))).getText().shouldContain("JJ'S MENU");
    }

    public void selectItem(final String name) {
        spans(By.className("catHeadCopy")).first(new HasText(name)).click();
        links(By.className("menuItem")).first(new HasText(name)).click();
    }

    private static class HasText implements FluentMatcher {
        private final String name;

        public HasText(String name) {
            this.name = name;
        }

        public boolean matches(WebElement webElement) {
            return webElement.getText().contains(name);
        }

        @Override
        public String toString() {
            return "HasText: " + name;
        }
    }
}
