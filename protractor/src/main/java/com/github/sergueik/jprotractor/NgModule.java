package com.github.sergueik.jprotractor;

import com.github.sergueik.jprotractor.scripts.Script;

public class NgModule {
    public final transient String name;
    public final transient Script script;

    public NgModule(final String name, final Script script) {
        this.name = name;
        this.script = script;
    }
}
