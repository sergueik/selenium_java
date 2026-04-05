package example.exception;

public class ElementNotFoundException extends NihoniumException {

	public ElementNotFoundException(String message) {
		super(message);
	}

	public ElementNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}
}
