package org.henrrich.jpagefactory;

/* 
 * updated by sergueik on 07/31/2016.
 */

/**
 * <code>How</code> attribute defines the locator strategy for finding web
 * elements. <br/>
 * It supports both Selenium WebDriver original locators, but also Protrator
 * locators.
 */
public enum How {

	/**
	 * default value
	 */
	UNSET,

	/* JProtractor locators */

	/**
	 * Finding element by Angular binding name, <code>using</code> attribute in
	 * <code>@FindBy</code> annotation specifies the binding name.
	 */
	BINDING,

	/**
	 * Finding element by its css attribute specified by <code>using</code>
	 * attribute and partial text in the element specified by <code>text</code>
	 * attribute in <code>@FindBy</code> annotation
	 */
	CSS_CONTAINING_TEXT,

	/**
	 * Finding button element by full button text, <code>using</code> attribute in
	 * <code>@FindBy</code> annotation specifies the full button text string.
	 */
	BUTTON_TEXT,

	/**
	 * Finding input element by Angular <code>ng-model</code> directive,
	 * <code>using</code> attribute in <code>@FindBy</code> annotation specifies
	 * the <code>ng-model</code> value.
	 */
	INPUT,

	/**
	 * Finding element by Angular <code>ng-model</code> directive,
	 * <code>using</code> attribute in <code>@FindBy</code> annotation specifies
	 * the <code>ng-model</code> value.
	 */
	MODEL,

	/**
	 * Finding list elements by Angular <code>ng-repeat</code> directive,
	 * <code>using</code> attribute in <code>@FindBy</code> annotation specifies
	 * the <code>ng-repeat</code> string.
	 */
	REPEATER,

	/**
	 * Finding selected option from select web element, <code>using</code>
	 * attribute in <code>@FindBy</code> annotation specifies the
	 * <code>ng-model</code> value of the select element.
	 */
	SELECTED_OPTION,

	/**
	 * TODO: example to be added
	 */
	REPEATER_SELECTED_OPTION,

	/**
	 * Finding child element in the repeater by Angular <code>ng-repeat</code>
	 * attribute specified by <code>using</code> attribute, and binding row number
	 * specified by <code>column</code> attribute in <code>@FindBy</code>
	 * annotation
	 */
	REPEATER_COLUMN,

	/**
	 * Finding child element in the repeater by Angular <code>ng-repeat</code>
	 * attribute specified by <code>using</code> attribute, row number specified
	 * by <code>row</code> attribute, and binding row number specified by
	 * <code>column</code> attribute
	 */
	REPEATER_ELEMENT,

	/**
	 * Finding child element in the repeater by by Angular <code>ng-repeat</code>
	 * attribute specified by <code>using</code> attribute and row number
	 * specified by <code>row</code> attribute.
	 */
	REPEATER_ROW,

	/**
	 * Finding select web element options by Angular <code>ng-options</code>
	 * directive, <code>using</code> attribute in <code>@FindBy</code> annotation
	 * specifies the <code>ng-options</code> string.
	 */
	OPTIONS,

	/**
	 * Finding button element by partial button text, <code>using</code> attribute
	 * in <code>@FindBy</code> annotation specifies the partial button text
	 * string.
	 */
	PARTIAL_BUTTON_TEXT,

	/* core Selenium locators */
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
	 * Same to <code>PARTIAL_LINK_TEXT</code> in Selenium WebDriver original
	 * locator
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
