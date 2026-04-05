package io.github.ashwithpoojary98.exception;

/**
 * Thrown when an element reference is no longer valid (e.g., DOM has changed).
 */
public class StaleElementException extends NihoniumException {

    public StaleElementException(String message) {
        super(message);
    }

    public StaleElementException(String message, Throwable cause) {
        super(message, cause);
    }
}
