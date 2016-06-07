package org.henrrich.jpagefactory;

/**
 * <code>How</code> attribute defines the locator strategy for finding web elements. <br/>
 * It supports both Selenium WebDriver original locators, but also Protrator locators.
 */
public enum How {

    /**
     * default value
     */
    UNSET,

    /* JProtractor locators */
    /**
     * Finding element by Angular binding name, <code>using</code> attribute in <code>@FindBy</code> annotation specifies the binding name.
     */
    BINDING,

    /**
     * Finding input element by Angular <code>ng-model</code> directive, <code>using</code> attribute in <code>@FindBy</code> annotation specifies the <code>ng-model</code> value.
     */
    INPUT,

    /**
     * Finding element by Angular <code>ng-model</code> directive, <code>using</code> attribute in <code>@FindBy</code> annotation specifies the <code>ng-model</code> value.
     */
    MODEL,

    /**
     * Finding list elements by Angular <code>ng-repeat</code> directive, <code>using</code> attribute in <code>@FindBy</code> annotation specifies the <code>ng-repeat</code> string.
     */
    REPEATER,

    /**
     * Finding selected option from select web element, <code>using</code> attribute in <code>@FindBy</code> annotation specifies the <code>ng-model</code> value of the select element.
     */
    SELECTED_OPTION,

    /**
     * TODO: example to be added
     */
    REPEATER_SELECTED_OPTION,

    /**
     * Finding select web element options by Angular <code>ng-options</code> directive, <code>using</code> attribute in <code>@FindBy</code> annotation specifies the <code>ng-options</code> string.
     */
    OPTIONS,

    /**
     * Finding button element by full button text, <code>using</code> attribute in <code>@FindBy</code> annotation specifies the full button text string.
     */
    BUTTON_TEXT,

    /**
     * Finding button element by partial button text, <code>using</code> attribute in <code>@FindBy</code> annotation specifies the partial button text string.
     */
    PARTIAL_BUTTON_TEXT,

    /* classical selenium locators */
    /**
     * Same to <code>CLASS_NAME</code> in Selenium WebDriver original locator
     */
    CLASS_NAME,

    /**
     * Same to <code>CSS</code> in Selenium WebDriver original locator
     */
    CSS,

    /**
     * Same to <code>ID</code> in Selenium WebDriver original locator
     */
    ID,

    /**
     * Same to <code>ID_OR_NAME</code> in Selenium WebDriver original locator
     */
    ID_OR_NAME,

    /**
     * Same to <code>LINK_TEXT</code> in Selenium WebDriver original locator
     */
    LINK_TEXT,

    /**
     * Same to <code>NAME</code> in Selenium WebDriver original locator
     */
    NAME,

    /**
     * Same to <code>PARTIAL_LINK_TEXT</code> in Selenium WebDriver original locator
     */
    PARTIAL_LINK_TEXT,

    /**
     * Same to <code>TAG_NAME</code> in Selenium WebDriver original locator
     */
    TAG_NAME,

    /**
     * Same to <code>XPATH</code> in Selenium WebDriver original locator
     */
    XPATH
}
