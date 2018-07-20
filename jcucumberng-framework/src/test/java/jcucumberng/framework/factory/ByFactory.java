package jcucumberng.framework.factory;

import java.io.IOException;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;

import com.paulhammant.ngwebdriver.ByAngular;

import jcucumberng.framework.api.ConfigLoader;
import jcucumberng.framework.enums.ByMethod;
import jcucumberng.framework.exceptions.InvalidPatternException;
import jcucumberng.framework.exceptions.UnsupportedByMethodException;
import jcucumberng.framework.strings.Messages;

/**
 * {@code ByFactory} handles actions for manipulating the Selenium {@code By}
 * object.
 * 
 * @author Kat Rollo <rollo.katherine@gmail.com>
 */
public final class ByFactory {

	private ByFactory() {
		// Prevent instantiation
	}

	/**
	 * Gets the Selenium {@code By} object.
	 * 
	 * @param key the key from {@code ui-map.properties}
	 * @return By - the {@code By} object
	 * @throws IOException
	 */
	public static By getBy(String key) throws IOException {
		String value = ConfigLoader.uiMap(key);
		if (!value.matches(".+:.+")) {
			throw new InvalidPatternException(Messages.INVALID_PATTERN + value);
		}

		String text = null;
		String method = StringUtils.substringBefore(value, ":").toUpperCase();
		String selector = StringUtils.substringAfter(value, ":");
		if (selector.contains("|")) {
			text = StringUtils.substringAfter(selector, "|");
			selector = StringUtils.substringBefore(selector, "|");
		}

		By by = null;
		try {
			ByMethod byMethod = ByMethod.valueOf(method);
			switch (byMethod) {
			case ID:
				by = By.id(selector);
				break;
			case NAME:
				by = By.name(selector);
				break;
			case LINK_TEXT:
				by = By.linkText(selector);
				break;
			case PARTIAL_LINK_TEXT:
				by = By.partialLinkText(selector);
				break;
			case TAG:
				by = By.tagName(selector);
				break;
			case CLASS:
				by = By.className(selector);
				break;
			case CSS:
				by = By.cssSelector(selector);
				break;
			case XPATH:
				by = By.xpath(selector);
				break;
			case BINDING:
				by = ByAngular.binding(selector);
				break;
			case MODEL:
				by = ByAngular.model(selector);
				break;
			case BUTTON_TEXT:
				by = ByAngular.buttonText(selector);
				break;
			case CSS_CONTAINING_TEXT:
				by = ByAngular.cssContainingText(selector, text);
				break;
			case EXACT_BINDING:
				by = ByAngular.exactBinding(selector);
				break;
			case EXACT_REPEATER:
				by = ByAngular.exactRepeater(selector);
				break;
			case OPTIONS:
				by = ByAngular.options(selector);
				break;
			case PARTIAL_BUTTON_TEXT:
				by = ByAngular.partialButtonText(selector);
				break;
			case REPEATER:
				by = ByAngular.repeater(selector);
				break;
			default:
				// Handled in try-catch
				break;
			}
		} catch (IllegalArgumentException iae) {
			throw new UnsupportedByMethodException(Messages.UNSUPPORTED_BY_METHOD + method);
		}

		return by;
	}

}
