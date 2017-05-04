package com.stackoverflow;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
// import java.util.logging.Logger;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ByIdOrName;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.FindBys;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.pagefactory.Annotations;
import org.openqa.selenium.support.pagefactory.ByAll;
import org.openqa.selenium.support.pagefactory.ByChained;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

public class DynamicAnnotations extends Annotations {
	private static final Logger log = LoggerFactory
			.getLogger(DynamicAnnotations.class.getCanonicalName());

	private final Field field;
	private final Map<String, String> substitutions;

	public DynamicAnnotations(final Field field,
			final Map<String, String> substitutions) {
		super(field);
		// log.entry(field, substitutions);
		this.field = field;
		this.substitutions = substitutions;
		// log.debug("Successful completion of the dynamic annotations
		// constructor");
		// log.exit();
	}

	public boolean isLookupCached() {
		// log.entry();
		// return log.exit((field.getAnnotation(CacheLookup.class) != null));
		return (field.getAnnotation(CacheLookup.class) != null);
	}

	public By buildBy() {
		// log.entry();
		assertValidAnnotations();

		By ans = null;

		FindBys findBys = field.getAnnotation(FindBys.class);
		if (findBys != null) {
			// log.debug("Building a chained locator");
			ans = buildByFromFindBys(findBys);
		}

		FindAll findAll = field.getAnnotation(FindAll.class);
		if (ans == null && findAll != null) {
			// log.debug("Building a find by one of locator");
			ans = buildBysFromFindByOneOf(findAll);
		}

		FindBy findBy = field.getAnnotation(FindBy.class);
		if (ans == null && findBy != null) {
			// log.debug("Building an ordinary locator");
			ans = buildByFromFindBy(findBy);
		}

		if (ans == null) {
			// log.debug("No locator annotation specified, so building a locator for
			// id or name based on field name");
			ans = buildByFromDefault();
		}

		if (ans == null) {
			// throw log.throwing(new IllegalArgumentException("Cannot determine how
			// to locate element " + field));
		}
		return ans;
		// return log.exit(ans);
	}

	protected By buildByFromDefault() {
		// log.entry();
		// return log.exit(new ByIdOrName(field.getName()));
		return new ByIdOrName(field.getName());
	}

	protected By buildByFromFindBys(final FindBys findBys) {
		// log.entry(findBys);
		assertValidFindBys(findBys);

		FindBy[] findByArray = findBys.value();
		By[] byArray = new By[findByArray.length];
		for (int i = 0; i < findByArray.length; i++) {
			byArray[i] = buildByFromFindBy(findByArray[i]);
		}

		// return log.exit(new ByChained(byArray));
		return new ByChained(byArray);
	}

	protected By buildBysFromFindByOneOf(final FindAll findBys) {
		// log.entry(findBys);
		assertValidFindAll(findBys);

		FindBy[] findByArray = findBys.value();
		By[] byArray = new By[findByArray.length];
		for (int i = 0; i < findByArray.length; i++) {
			byArray[i] = buildByFromFindBy(findByArray[i]);
		}

		// return log.exit(new ByAll(byArray));
		return new ByAll(byArray);
	}

	protected By buildByFromFindBy(final FindBy findBy) {
		// log.entry(findBy);
		assertValidFindBy(findBy);

		By ans = buildByFromShortFindBy(findBy);
		if (ans == null) {
			ans = buildByFromLongFindBy(findBy);
		}

		// return log.exit(ans);
		return ans;
	}

	// The only thing that is different from the default Selenium implementation
	// is that the locator string is processed for substitutions by the
	// processForSubstitutions(using) method, which I have added
	protected By buildByFromLongFindBy(final FindBy findBy) {
		// log.entry(findBy);
		How how = findBy.how();
		String using = findBy.using();

		switch (how) {
		case CLASS_NAME:
			/* log.debug(
					"Long FindBy annotation specified lookup by class name, using {}",
					using);
					*/
			String className = processForSubstitutions(using);
			// return log.exit(By.className(className));
			return By.className(className);
		case CSS:
			// log.debug("Long FindBy annotation specified lookup by css name, using
			// {}", using);
			String css = processForSubstitutions(using);
			// return log.exit(By.cssSelector(css));
			return By.cssSelector(css);
		case ID:
			// log.debug("Long FindBy annotation specified lookup by id, using {}",
			// using);
			String id = processForSubstitutions(using);
			// return log.exit(By.id(id));
			return By.id(id);
		case ID_OR_NAME:
			// log.debug(
			// "Long FindBy annotation specified lookup by id or name, using {}",
			// using);
			String idOrName = processForSubstitutions(using);
			// return log.exit(new ByIdOrName(idOrName));
			return new ByIdOrName(idOrName);
		case LINK_TEXT:
			// log.debug(
			// "Long FindBy annotation specified lookup by link text, using {}",
			// using);
			String linkText = processForSubstitutions(using);
			// return log.exit(By.linkText(linkText));
			return By.linkText(linkText);
		case NAME:
			// log.debug("Long FindBy annotation specified lookup by name, using {}",
			// using);
			String name = processForSubstitutions(using);
			// return log.exit(By.name(name));
			return By.name(name);
		case PARTIAL_LINK_TEXT:
			// log.debug(
			// "Long FindBy annotation specified lookup by partial link text, using
			// {}",
			// using);
			String partialLinkText = processForSubstitutions(using);
			// return log.exit(By.partialLinkText(partialLinkText));
			return By.partialLinkText(partialLinkText);
		case TAG_NAME:
			// log.debug("Long FindBy annotation specified lookup by tag name, using
			// {}",
			// using);
			String tagName = processForSubstitutions(using);
			// return log.exit(By.tagName(tagName));
			return By.tagName(tagName);
		case XPATH:
			// log.debug("Long FindBy annotation specified lookup by xpath, using {}",
			// using);
			String xpath = processForSubstitutions(using);
			// return log.exit(By.xpath(xpath));
			return By.xpath(xpath);
		default:
			// Note that this shouldn't happen (eg, the above matches all
			// possible values for the How enum)
			// throw log.throwing(new IllegalArgumentException(
			// "Cannot determine how to locate element " + field));
			throw new IllegalArgumentException(
					"Cannot determine how to locate element " + field);
		}
	}

	// The only thing that differs from the default Selenium implementation is
	// that the locator string is processed for substitutions by
	// processForSubstitutions(using), which I wrote
	protected By buildByFromShortFindBy(final FindBy findBy) {
		// log.entry(findBy);
		// log.debug("Building from a short FindBy annotation");

		if (!"".equals(findBy.className())) {
			// log.debug("Short FindBy annotation specifies lookup by class name: {}",
			// findBy.className());
			String className = processForSubstitutions(findBy.className());
			// return log.exit(By.className(className));
			return By.className(className);
		}

		if (!"".equals(findBy.css())) {
			// log.debug("Short FindBy annotation specifies lookup by css");
			String css = processForSubstitutions(findBy.css());
			// return log.exit(By.cssSelector(css));
			return By.cssSelector(css);
		}

		if (!"".equals(findBy.id())) {
			// log.debug("Short FindBy annotation specified lookup by id");
			String id = processForSubstitutions(findBy.id());
			// return log.exit(By.id(id));
			return By.id(id);
		}

		if (!"".equals(findBy.linkText())) {
			// log.debug("Short FindBy annotation specified lookup by link text");
			String linkText = processForSubstitutions(findBy.linkText());
			// return log.exit(By.linkText(linkText));
			return By.linkText(linkText);
		}

		if (!"".equals(findBy.name())) {
			// log.debug("Short FindBy annotation specified lookup by name");
			String name = processForSubstitutions(findBy.name());
			// return log.exit(By.name(name));
			return By.name(name);
		}

		if (!"".equals(findBy.partialLinkText())) {
			// log.debug(
			// "Short FindBy annotation specified lookup by partial link text");
			String partialLinkText = processForSubstitutions(
					findBy.partialLinkText());
			// return log.exit(By.partialLinkText(partialLinkText));
			return By.partialLinkText(partialLinkText);
		}

		if (!"".equals(findBy.tagName())) {
			// log.debug("Short FindBy annotation specified lookup by tag name");
			String tagName = processForSubstitutions(findBy.tagName());
			// return log.exit(By.tagName(tagName));
			return By.tagName(tagName);
		}

		if (!"".equals(findBy.xpath())) {
			// log.debug("Short FindBy annotation specified lookup by xpath");
			String xpath = processForSubstitutions(findBy.xpath());
			// return log.exit(By.xpath(xpath));
			return By.xpath(xpath);
		}

		// Fall through
		// log.debug("Locator does not match any expected locator type");
		// return log.exit(null);
		return null;
	}

	// This method is where I find and do replacements. The method looks
	// for instances of ${key} and if there is a key in the substitutions
	// map that is equal to 'key', the substring ${key} is replaced by the
	// value mapped to 'key'
	private String processForSubstitutions(final String locator) {
		// log.entry(locator);
		// log.debug("Processing locator '{}' for substitutions");
		List<String> subs = Arrays
				.asList(StringUtils.substringsBetween(locator, "${", "}"));
		// log.debug(
		// "List of substrings in locator which match substitution pattern: {}",
		// subs);
		String processed = locator;

		for (String sub : subs) {
			// log.debug("Processing substring {}", sub);
			// If there is no matching key, the substring "${ ..}" is treated as a
			// literal
			if (substitutions.get(sub) != null) {
				// log.debug("Replacing with {}", substitutions.get(sub));
				processed = StringUtils.replace(locator, "${" + sub + "}",
						substitutions.get(sub));
				// log.debug("Locator after substitution: {}", processed);
			}
		}

		// return log.exit(processed);
		return processed;
	}

	public void assertValidAnnotations() {
		// log.entry();

		FindBys findBys = field.getAnnotation(FindBys.class);
		FindAll findAll = field.getAnnotation(FindAll.class);
		FindBy findBy = field.getAnnotation(FindBy.class);

		if (findBys != null && findBy != null) {
			// throw log.throwing(
			// new IllegalArgumentException("If you use a '@FindBys' annotation, "
			// + "you must not also use a '@FindBy' annotation"));
			throw new IllegalArgumentException("If you use a '@FindBys' annotation, "
					+ "you must not also use a '@FindBy' annotation");
		}
		if (findAll != null && findBy != null) {
			// throw log.throwing(
			// new IllegalArgumentException("If you use a '@FindAll' annotation, "
			// + "you must not also use a '@FindBy' annotation"));
			throw new IllegalArgumentException("If you use a '@FindAll' annotation, "
					+ "you must not also use a '@FindBy' annotation");
		}
		if (findAll != null && findBys != null) {
			// throw log.throwing(
			// new IllegalArgumentException("If you use a '@FindAll' annotation, "
			// + "you must not also use a '@FindBys' annotation"));
			throw new IllegalArgumentException("If you use a '@FindAll' annotation, "
					+ "you must not also use a '@FindBys' annotation");
		}
	}

	private void assertValidFindBys(final FindBys findBys) {
		// log.entry(findBys);
		for (FindBy findBy : findBys.value()) {
			assertValidFindBy(findBy);
		}
		// log.exit();
	}

	private void assertValidFindAll(final FindAll findBys) {
		// log.entry(findBys);
		for (FindBy findBy : findBys.value()) {
			assertValidFindBy(findBy);
		}
		// log.exit();
	}

	private void assertValidFindBy(final FindBy findBy) {
		// log.entry();
		if (findBy.how() != null) {
			if (findBy.using() == null) {
				// throw log.throwing(new IllegalArgumentException(
				// "If you set the 'how' property, you must also set 'using'"));
				throw new IllegalArgumentException(
						"If you set the 'how' property, you must also set 'using'");
			}
		}

		Set<String> finders = new HashSet<>();
		if (!"".equals(findBy.using())) {
			// log.debug("Locator string is: {}", findBy.using());
			finders.add("how: " + findBy.using());
		}
		if (!"".equals(findBy.className())) {
			// log.debug("Class name locator string is {}", findBy.className());
			finders.add("class name:" + findBy.className());
		}

		if (!"".equals(findBy.css())) {
			// log.debug("Css locator string is {}", findBy.css());
			finders.add("css:" + findBy.css());
		}

		if (!"".equals(findBy.id())) {
			// log.debug("Id locator string is {}", findBy.id());
			finders.add("id: " + findBy.id());
		}

		if (!"".equals(findBy.linkText())) {
			// log.debug("Link text locator string is {}", findBy.linkText());
			finders.add("link text: " + findBy.linkText());
		}

		if (!"".equals(findBy.name())) {
			// log.debug("Name locator string is {}", findBy.name());
			finders.add("name: " + findBy.name());
		}

		if (!"".equals(findBy.partialLinkText())) {
			// log.debug("Partial text locator string is {}",
			// findBy.partialLinkText());
			finders.add("partial link text: " + findBy.partialLinkText());
		}

		if (!"".equals(findBy.tagName())) {
			// log.debug("Tag name locator string is {}", findBy.tagName());
			finders.add("tag name: " + findBy.tagName());
		}
		if (!"".equals(findBy.xpath())) {
			// log.debug("Xpath locator string is {}", findBy.xpath());
			finders.add("xpath: " + findBy.xpath());
		}

		// A zero count is okay: it means to look by name or id.
		if (finders.size() > 1) {
			// throw log.throwing(new IllegalArgumentException(String.format(
			// "You must specify at most one location strategy. Number found: %d
			// (%s)",
			// finders.size(), finders.toString())));
			throw new IllegalArgumentException(String.format(
					"You must specify at most one location strategy. Number found: %d (%s)",
					finders.size(), finders.toString()));
		}
	}
}
