package example.exception;

public class StaleElementException extends NihoniumException {

	public StaleElementException(String message) {
		super(message);
	}

	public StaleElementException(String message, Throwable cause) {
		super(message, cause);
	}
}
