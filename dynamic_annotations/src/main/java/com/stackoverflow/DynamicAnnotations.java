package com.stackoverflow;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
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
		this.field = field;
		this.substitutions = substitutions;
	}

	public boolean isLookupCached() {
		return (field.getAnnotation(CacheLookup.class) != null);
	}

	public By buildBy() {
		assertValidAnnotations();

		By ans = null;

		FindBys findBys = field.getAnnotation(FindBys.class);
		if (findBys != null) {
			// building a chained locator
			ans = buildByFromFindBys(findBys);
		}

		FindAll findAll = field.getAnnotation(FindAll.class);
		if (ans == null && findAll != null) {
			// building a find by one of locator
			ans = buildBysFromFindByOneOf(findAll);
		}

		FindBy findBy = field.getAnnotation(FindBy.class);
		if (ans == null && findBy != null) {
			// building an ordinary locator
			ans = buildByFromFindBy(findBy);
		}

		if (ans == null) {
			// without locator annotation will build a locator based on field name
			// id or name
			ans = buildByFromDefault();
		}

		if (ans == null) {
			throw new IllegalArgumentException(
					"Cannot determine how to locate element " + field);
		}
		return ans;
	}

	protected By buildByFromDefault() {
		return new ByIdOrName(field.getName());
	}

	protected By buildByFromFindBys(final FindBys findBys) {
		assertValidFindBys(findBys);

		FindBy[] findByArray = findBys.value();
		By[] byArray = new By[findByArray.length];
		for (int i = 0; i < findByArray.length; i++) {
			byArray[i] = buildByFromFindBy(findByArray[i]);
		}
		return new ByChained(byArray);
	}

	protected By buildBysFromFindByOneOf(final FindAll findBys) {
		assertValidFindAll(findBys);

		FindBy[] findByArray = findBys.value();
		By[] byArray = new By[findByArray.length];
		for (int i = 0; i < findByArray.length; i++) {
			byArray[i] = buildByFromFindBy(findByArray[i]);
		}
		return new ByAll(byArray);
	}

	protected By buildByFromFindBy(final FindBy findBy) {
		assertValidFindBy(findBy);

		By ans = buildByFromShortFindBy(findBy);
		if (ans == null) {
			ans = buildByFromLongFindBy(findBy);
		}
		return ans;
	}

	// The only thing that is different from the default Selenium implementation
	// is that the locator string is processed for substitutions by the
	// processForSubstitutions(using) method, which I have added
	protected By buildByFromLongFindBy(final FindBy findBy) {
		How how = findBy.how();
		String using = findBy.using();

		switch (how) {
		case CLASS_NAME:
			return By.className(processForSubstitutions(using));
		case CSS:
			return By.cssSelector(processForSubstitutions(using));
		case ID:
			return By.id(processForSubstitutions(using));
		case ID_OR_NAME:
			return new ByIdOrName(processForSubstitutions(using));
		case LINK_TEXT:
			return By.linkText(processForSubstitutions(using));
		case NAME:
			return By.name(processForSubstitutions(using));
		case PARTIAL_LINK_TEXT:
			return By.partialLinkText(processForSubstitutions(using));
		case TAG_NAME:
			return By.tagName(processForSubstitutions(using));
		case XPATH:
			return By.xpath(processForSubstitutions(using));
		default:
			// this shouldn't happen
			throw new IllegalArgumentException(
					"Cannot determine how to locate element " + field);
		}
	}

	// The only thing that differs from the default Selenium implementation is
	// that the locator string is processed for substitutions by
	// processForSubstitutions(using), which I wrote
	protected By buildByFromShortFindBy(final FindBy findBy) {
		// building from a short FindBy annotation");

		if (!findBy.className().isEmpty()) {
			return By.className(processForSubstitutions(findBy.className()));
		}

		if (!findBy.css().isEmpty()) {
			return By.cssSelector(processForSubstitutions(findBy.css()));
		}

		if (!findBy.id().isEmpty()) {
			return By.id(processForSubstitutions(findBy.id()));
		}

		if (!findBy.linkText().isEmpty()) {
			return By.linkText(processForSubstitutions(findBy.linkText()));
		}

		if (!findBy.name().isEmpty()) {
			return By.name(processForSubstitutions(findBy.name()));
		}

		if (!findBy.partialLinkText().isEmpty()) {
			return By
					.partialLinkText(processForSubstitutions(findBy.partialLinkText()));
		}

		if (!findBy.tagName().isEmpty()) {
			return By.tagName(processForSubstitutions(findBy.tagName()));
		}

		if (!findBy.xpath().isEmpty()) {
			return By.xpath(processForSubstitutions(findBy.xpath()));
		}

		// Fall through
		// log.debug("Locator does not match any expected locator type");
		return null;
	}

	// This method is where I find and do replacements. The method looks
	// for instances of ${key} and if there is a key in the substitutions
	// map that is equal to 'key', the substring ${key} is replaced by the
	// value mapped to 'key'
	private String processForSubstitutions(final String locator) {
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
			}
		}
		return processed;
	}

	public void assertValidAnnotations() {

		FindBys findBys = field.getAnnotation(FindBys.class);
		FindAll findAll = field.getAnnotation(FindAll.class);
		FindBy findBy = field.getAnnotation(FindBy.class);

		if (findBys != null && findBy != null) {
			throw new IllegalArgumentException("If you use a '@FindBys' annotation, "
					+ "you must not also use a '@FindBy' annotation");
		}
		if (findAll != null && findBy != null) {
			throw new IllegalArgumentException("If you use a '@FindAll' annotation, "
					+ "you must not also use a '@FindBy' annotation");
		}
		if (findAll != null && findBys != null) {
			throw new IllegalArgumentException("If you use a '@FindAll' annotation, "
					+ "you must not also use a '@FindBys' annotation");
		}
	}

	private void assertValidFindBys(final FindBys findBys) {
		for (FindBy findBy : findBys.value()) {
			assertValidFindBy(findBy);
		}
	}

	private void assertValidFindAll(final FindAll findBys) {
		for (FindBy findBy : findBys.value()) {
			assertValidFindBy(findBy);
		}
	}

	private void assertValidFindBy(final FindBy findBy) {
		if (findBy.how() != null) {
			if (findBy.using() == null) {
				throw new IllegalArgumentException(
						"If you set the 'how' property, you must also set 'using'");
			}
		}

		Set<String> finders = new HashSet<>();
		if (!findBy.using().isEmpty()) {
			finders.add("how: " + findBy.using());
		}
		if (!findBy.className().isEmpty()) {
			finders.add("class name:" + findBy.className());
		}

		if (!findBy.css().isEmpty()) {
			finders.add("css:" + findBy.css());
		}

		if (!findBy.id().isEmpty()) {
			finders.add("id: " + findBy.id());
		}

		if (!findBy.linkText().isEmpty()) {
			finders.add("link text: " + findBy.linkText());
		}

		if (!findBy.name().isEmpty()) {
			finders.add("name: " + findBy.name());
		}

		if (!findBy.partialLinkText().isEmpty()) {
			finders.add("partial link text: " + findBy.partialLinkText());
		}

		if (!findBy.tagName().isEmpty()) {
			finders.add("tag name: " + findBy.tagName());
		}
		if (!findBy.xpath().isEmpty()) {
			finders.add("xpath: " + findBy.xpath());
		}

		// A zero count is okay: it means to look by name or id.
		if (finders.size() > 1) {
			throw new IllegalArgumentException(String.format(
					"You must specify at most one location strategy. Number found: %d (%s)",
					finders.size(), finders.toString()));
		}
	}
}
