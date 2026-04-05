package example.exception;

public class CDPException extends NihoniumException {

	public CDPException(String message) {
		super(message);
	}

	public CDPException(String message, Throwable cause) {
		super(message, cause);
	}
}
