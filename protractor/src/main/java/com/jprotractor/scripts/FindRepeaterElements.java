package com.jprotractor.scripts;

/**
 * @author Serguei Kouzmine (kouzmine_serguei@yahoo.com)
 */
public final class FindRepeaterElements implements Script {
    @Override
    public String content() {
        return new Loader("repeaterElement").content();
    }
}
