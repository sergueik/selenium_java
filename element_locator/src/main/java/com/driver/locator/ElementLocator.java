package com.driver.locator;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Attribute;
import org.jsoup.nodes.Attributes;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

import com.driver.locator.model.IgnoreAttribute;
import com.driver.locator.model.LocatorModel;
import com.driver.locator.utility.NullRemove;
import com.driver.locator.writer.FileType;
import com.driver.locator.writer.FileWrite;
import com.driver.locator.writer.ObjectFactory;

public class ElementLocator {

	private PropertyFileReader rReader;
	private WebDriver dDriver;
	private Document dDocument;

	private static final String TAG_LINK = "a";
	private static final String TAG_BUTTON = "button";
	private static final String TAG_INPUT = "input";
	private static final String TAG_DROP_DOWN = "select";
	private static final String TAG_TEXT_AREA = "textarea";
	private static final String TAG_SPAN = "span";

	private static int REC_COUNT = 1;
	private static final String LOCATOR_ID = "id";
	private static final String LOCATOR_CLASS = "class";
	private static final String LOCATOR_NAME = "name";

	private LocatorModel getXpath(WebDriver aDriver, Element bElement,
			List<String> locatorList) {
		boolean flag = true;
		String locator = "";
		Iterator<Attribute> attIterator = bElement.attributes().iterator();
		List<String> locatorArrayList = new ArrayList<>();

		if (REC_COUNT == 5 || bElement == null || locatorList.isEmpty())
			return null;

		REC_COUNT++;

		while (attIterator.hasNext()) {
			Attribute attribute = (Attribute) attIterator.next();
			if (IgnoreAttribute.ignoreAttribute.contains(attribute.getKey())
					|| attribute.getValue().isEmpty())
				continue;
			for (String s : locatorList) {
				locator = "//" + bElement.nodeName() + "[@" + attribute.getKey() + "='"
						+ attribute.getValue() + "']" + s;
				locatorArrayList.add(locator);
				if (isUnique(aDriver, By.xpath(locator))) {
					flag = false;
					break;
				}
				locator = "";
			}
			if (!flag)
				break;
		}

		if (locatorArrayList.isEmpty()) {
			for (String s : locatorList) {
				locator = "//" + bElement.nodeName() + s;
				locatorArrayList.add(locator);
				if (isUnique(aDriver, By.xpath(locator)))
					break;
				locator = "";
			}
		}

		return locator.length() == 0
				? getXpath(aDriver, bElement.parent(), locatorArrayList)
				: new LocatorModel("Xpath", locator);
	}

	private LocatorModel getXpath(WebDriver aDriver, Element bElement) {
		REC_COUNT = 1;
		String locator = "";
		Iterator<Attribute> attIterator = bElement.attributes().iterator();
		List<String> locatorList = new ArrayList<>();

		while (attIterator.hasNext()) {
			Attribute attribute = (Attribute) attIterator.next();
			if (IgnoreAttribute.ignoreAttribute.contains(attribute.getKey())
					|| attribute.getValue().isEmpty())
				continue;
			locator = "//" + bElement.nodeName() + "[@" + attribute.getKey() + "='"
					+ attribute.getValue() + "']";
			locatorList.add(locator);
			if (isUnique(aDriver, By.xpath(locator)))
				break;
			locator = "";
		}

		return locator.length() == 0
				? getXpath(aDriver, bElement.parent(),
						locatorList.isEmpty() ? new ArrayList<String>(
								Arrays.asList("//" + bElement.nodeName())) : locatorList)
				: new LocatorModel("Xpath", locator);
	}

	private LocatorModel getUniqueLocator(WebDriver aDriver, Element bElement) {
		Attributes aAttributes = bElement.attributes();

		if (!aAttributes.get(LOCATOR_ID).isEmpty()
				&& isUnique(aDriver, By.id(aAttributes.get(LOCATOR_ID)))) {
			return new LocatorModel(LOCATOR_ID, aAttributes.get(LOCATOR_ID));
		} else if (!aAttributes.get(LOCATOR_CLASS).isEmpty()
				&& isUnique(aDriver, By.className(aAttributes.get(LOCATOR_CLASS)))) {
			return new LocatorModel(LOCATOR_CLASS, aAttributes.get(LOCATOR_CLASS));
		} else if (!aAttributes.get(LOCATOR_NAME).isEmpty()
				&& isUnique(aDriver, By.name(aAttributes.get(LOCATOR_NAME)))) {
			return new LocatorModel(LOCATOR_NAME, aAttributes.get(LOCATOR_NAME));
		} else if (bElement.hasText()) {
			String xPath = "//" + bElement.nodeName() + "[normalize-space(text())='"
					+ bElement.ownText().trim() + "']";
			if (isUnique(aDriver, By.xpath(xPath))) {
				return new LocatorModel("Xpath", xPath);
			}
		}
		return getXpath(dDriver, bElement);

	}

	private List<LocatorModel> getElementsByTag(String tagName,
			WebDriver aDriver) {
		Elements eleList = dDocument.getElementsByTag(tagName);
		List<LocatorModel> locator = new ArrayList<>();
		for (int i = 0; i < eleList.size(); i++) {
			locator.add(getUniqueLocator(aDriver, eleList.get(i)));
		}
		return locator;
	}

	private boolean isUnique(WebDriver driver, By locator) {
		try {
			return driver.findElements(locator).size() == 1;
		} catch (Exception e) {
		}
		return false;

	}

	public List<LocatorModel> getLocator(WebDriver aDriver) {
		List<LocatorModel> locatorList = new ArrayList<LocatorModel>();
		locatorList.addAll(getElementsByTag(TAG_LINK, aDriver));
		locatorList.addAll(getElementsByTag(TAG_BUTTON, aDriver));
		locatorList.addAll(getElementsByTag(TAG_DROP_DOWN, aDriver));
		locatorList.addAll(getElementsByTag(TAG_INPUT, aDriver));
		locatorList.addAll(getElementsByTag(TAG_TEXT_AREA, aDriver));
		locatorList.addAll(getElementsByTag(TAG_SPAN, aDriver));
		locatorList.removeIf(new NullRemove());
		return locatorList;

	}

	private void openPage(String url) {
		try {
			dDocument = Jsoup.connect(url).get();
			dDriver.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);
			dDriver.get(url);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public ElementLocator() {
		rReader = new PropertyFileReader();
		dDriver = new HtmlUnitDriver();
		dDriver.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);
	}

	/**
	 * Generate the locator and write to the file
	 * @param type
	 */
	public void writeToFile(FileType type) {
		FileWrite writer = ObjectFactory.getObject(type);
		Map<String, String> urlMap = rReader.getWebsiteNames();
		for (String websiteKey : urlMap.keySet()) {
			openPage(urlMap.get(websiteKey));
			writer.writeToFile(this.dDriver.getTitle().isEmpty() ? websiteKey
					: this.dDriver.getTitle().replaceAll("[^\\w\\s]", "")
							.replaceAll("\\s", ""),
					getLocator(this.dDriver));
		}
		if (this.dDriver != null)
			dDriver.quit();
	}

}
