package io.github.ashwithpoojary98.exception;

/**
 * Base exception for all Nihonium-related errors.
 */
public class NihoniumException extends RuntimeException {

    public NihoniumException(String message) {
        super(message);
    }

    public NihoniumException(String message, Throwable cause) {
        super(message, cause);
    }

    public NihoniumException(Throwable cause) {
        super(cause);
    }
}
