package io.github.ashwithpoojary98.exception;

public class TimeoutException extends NihoniumException {

    public TimeoutException(String message) {
        super(message);
    }

    public TimeoutException(String message, Throwable cause) {
        super(message, cause);
    }
}
