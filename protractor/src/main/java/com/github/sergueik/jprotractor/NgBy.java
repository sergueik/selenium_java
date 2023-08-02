package com.github.sergueik.jprotractor;

import java.util.regex.Pattern;

import org.openqa.selenium.By;

import com.github.sergueik.jprotractor.scripts.FindAllRepeaterColumns;
import com.github.sergueik.jprotractor.scripts.FindAllRepeaterRows;
import com.github.sergueik.jprotractor.scripts.FindBindings;
import com.github.sergueik.jprotractor.scripts.FindButtonText;
import com.github.sergueik.jprotractor.scripts.FindCssSelectorContainingText;
import com.github.sergueik.jprotractor.scripts.FindModel;
import com.github.sergueik.jprotractor.scripts.FindOptions;
import com.github.sergueik.jprotractor.scripts.FindPartialButtonText;
import com.github.sergueik.jprotractor.scripts.FindRepeaterElements;
import com.github.sergueik.jprotractor.scripts.FindRepeaterRows;
import com.github.sergueik.jprotractor.scripts.FindSelectedOption;
import com.github.sergueik.jprotractor.scripts.FindSelectedRepeaterOption;

public final class NgBy {
	private NgBy() {
	}

	public static By binding(final String binding) {
		return new JavaScriptBy(new FindBindings(), binding);
	}

	public static By binding(final String binding, String rootSelector) {
		return new JavaScriptBy(new FindBindings(), binding, rootSelector);
	}

	public static By buttonText(final String text) {
		return new JavaScriptBy(new FindButtonText(), text);
	}

	public static By cssContainingText(final String cssSelector, String text) {
		return new JavaScriptBy(new FindCssSelectorContainingText(), cssSelector,
				text);
	}

	public static By cssContainingText(final String cssSelector,
			Pattern pattern) {
		int patternFlags = pattern.flags();
		String patternText = String
				.format("__REGEXP__/%s/%s%s",
						pattern.pattern(),
						((patternFlags
								& Pattern.CASE_INSENSITIVE) == Pattern.CASE_INSENSITIVE) ? "i"
										: "",
						((patternFlags & Pattern.MULTILINE) == Pattern.MULTILINE) ? "m"
								: "");

		return new JavaScriptBy(new FindCssSelectorContainingText(), cssSelector,
				patternText);
	}

	public static By input(final String input) {
		return new JavaScriptBy(new FindModel(), input);
	}

	public static By model(final String model) {
		return new JavaScriptBy(new FindModel(), model);
	}

	public static By model(final String model, String rootSelector) {
		return new JavaScriptBy(new FindModel(), model, rootSelector);
	}

	public static By partialButtonText(final String text) {
		return new JavaScriptBy(new FindPartialButtonText(), text);
	}

	public static By repeater(final String repeat) {
		return new JavaScriptBy(new FindAllRepeaterRows(), repeat);
	}

	public static By repeaterColumn(final String repeat, String binding) {
		return new JavaScriptBy(new FindAllRepeaterColumns(), repeat, binding);
	}

	public static By repeaterElement(final String repeat, Integer index,
			String binding) {
		return new JavaScriptBy(new FindRepeaterElements(), repeat, index, binding);
	}

	public static By repeaterRows(final String repeat, Integer index) {
		return new JavaScriptBy(new FindRepeaterRows(), repeat, index);
	}

	public static By options(final String options) {
		return new JavaScriptBy(new FindOptions(), options);
	}

	public static By selectedOption(final String model) {
		return new JavaScriptBy(new FindSelectedOption(), model);
	}

	public static By selectedRepeaterOption(final String repeat) {
		return new JavaScriptBy(new FindSelectedRepeaterOption(), repeat);
	}
}
