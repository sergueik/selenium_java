package com.jprotractor.scripts;

/**
 * @author Carlos Alexandro Becker (caarlos0@gmail.com)
 */
public final class TestForAngular implements Script {
    @Override
    public String content() {
        return new Loader("testForAngular").content();
    }
}
