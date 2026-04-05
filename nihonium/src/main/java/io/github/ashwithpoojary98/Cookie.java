package io.github.ashwithpoojary98;

import java.util.Date;

public class Cookie {
    private final String name;
    private final String value;
    private final String domain;
    private final String path;
    private final Date expiry;
    private final boolean isSecure;
    private final boolean isHttpOnly;

    public Cookie(String name, String value) {
        this(name, value, null, null, null, false, false);
    }

    public Cookie(String name, String value, String domain, String path, Date expiry, boolean isSecure, boolean isHttpOnly) {
        this.name = name;
        this.value = value;
        this.domain = domain;
        this.path = path;
        this.expiry = expiry;
        this.isSecure = isSecure;
        this.isHttpOnly = isHttpOnly;
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }

    public String getDomain() {
        return domain;
    }

    public String getPath() {
        return path;
    }

    public Date getExpiry() {
        return expiry;
    }

    public boolean isSecure() {
        return isSecure;
    }

    public boolean isHttpOnly() {
        return isHttpOnly;
    }
}
