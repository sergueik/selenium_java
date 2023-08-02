package com.github.sergueik.jprotractor.scripts;

/**
 * @author Kouzmine Serguei (kouzmine_serguei@yahoo.com)
 */
public final class FindSelectedRepeaterOption implements Script {
	@Override
	public String content() {
		return new Loader("selectedRepeaterOption").content();
	}
}
