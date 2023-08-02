package com.github.sergueik.jprotractor.scripts;

/**
 * @author Carlos Alexandro Becker (caarlos0@gmail.com)
 */
public final class FindModel implements Script {
	@Override
	public String content() {
		return new Loader("model").content();
	}
}
