package com.github.sergueik.jprotractor.scripts;

/**
 * @author Carlos Alexandro Becker (caarlos0@gmail.com)
 */
public final class GetLocationAbsoluteURL implements Script {
    @Override
    public String content() {
        return new Loader("getLocationAbsUrl").content();
    }
}
