package example.exception;

public class NoSuchFrameException extends NihoniumException {

	public NoSuchFrameException(String message) {
		super(message);
	}

	public NoSuchFrameException(String message, Throwable cause) {
		super(message, cause);
	}
}
