package io.github.ashwithpoojary98.exception;

/**
 * Thrown when attempting to switch to a frame that doesn't exist.
 */
public class NoSuchFrameException extends NihoniumException {

    public NoSuchFrameException(String message) {
        super(message);
    }

    public NoSuchFrameException(String message, Throwable cause) {
        super(message, cause);
    }
}
